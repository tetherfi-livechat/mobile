package com.tetherfi.testapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import tetherfi.livechat.DeviceInfo;
import tetherfi.livechat.ExtCallHandler;
import tetherfi.livechat.RTCSessionManager;
import tetherfi.livechat.audio.AudioPlay;
import tetherfi.livechat.Configs;
import tetherfi.livechat.Log;
import tetherfi.livechat.RTCSession;
import tetherfi.livechat.Signalling;
import tetherfi.livechat.SignallingDelegate;
import tetherfi.livechat.service.AvService;
import tetherfi.livechat.video.VideoEventsListener;
import tetherfi.livechat.video.VideoSessionFactory;

public class AppIntegration
{
    public static boolean init( Activity userActivity )
    {
        RTCSessionManager.Instance.Initialize( userActivity );
        activity = userActivity;
        return true;
    }

    /**
     *
     * @param sessionId, session id from application
     * @return true for in-progress , false for failure.
     *
     */
    public static boolean startav(String sessionId)
    {
        if( AppIntegration.activeSession != null )
        {
            Log.e( "startav called while there is an active session already" );
            return false;
        }

        if( AppIntegration.activity == null )
        {
            Log.e( "activity is null, set android.app.Activity object before calling startav" );
            return false;
        }

        passAppMessageToServer( sessionId, "{ \"type\":\"devinfo\", \"info\":" + DeviceInfo.getInfo() + "}" );

        activeSession = new RTCSession( activity.getApplicationContext() );

        RTCSession.RTCEventsHandler handler = new RTCSession.RTCEventsHandler()
        {
            @Override
            public void onStartSuccess() {
                Log.i( "Call connected" );
            }

            @Override
            public void onStartFailure(Exception if_any)
            {
                Log.i( "Could not connect" );
            }

            @Override
            public void onDisconnect() {

            }

            @Override
            public void onReconnect() {

            }

            @Override
            public void onReconnectableEnd() {

            }

            @Override
            public void onEnd(EndType type, String reasonDesc)
            {
                /*

                Commented, in order to allow 'reconnect' attempts

                passAppMessageToServer(
                        activeSession.getSessionId(),
                        "{ \"type\":\"endav\", \"reason\":\"" + type.toString() + "\" }");

                activeSession = null;

                */
            }
        };

        activeSession.start(new PrivateSigImpl(), sessionId, handler );

        return true;
    }


    /**
     *
     * End current active audio/video session
     *
     * @param desc - indicate the reason for the end in a short phrase
     *
     */
    public static void endav(String desc)
    {
        if( activeSession == null )
        {
            Log.w( "endav called while no active session to end" );
            return;
        }

        passAppMessageToServer(
                activeSession.getSessionId(),
                "{ \"type\":\"endav\", \"reason\":\"" + desc.toString() + "\" }");

        activeSession.end( desc );

        activeSession = null;
    }


    /**
     *
     * Set user's response for the prompts
     *
     * @param sessionid  session id from application
     * @param answer  true == proceed with call, false == abort
     * @param reqParam  audio / video
     *
     */
    public static void setUserResponse( String sessionid, boolean answer, String reqParam )
    {
        Log.i( "setting userResponse as answer=" + answer + " rqparam=" + reqParam );

        if( answer )
        {
            passAppMessageToServer( "test",
                    "{ \"type\":\"avtstatus\", \"param\":\"" + reqParam + "\" }" );

            AppIntegration.startav( "test" );
        }
        else
        {
            passAppMessageToServer( "test",
                    "{ \"type\":\"avtstatus\", \"param\":\"false\" }" );
        }
    }

    /**
     *
     * Listener interface for setUserResponseInvoker
     *
     */
    public interface IUserResponseInvoker
    {
        void invoke( String sessionid, String reqParam );
    }


    /**
     *
     * @param invoker - Function to be used for prompting user
     */
    public static void setUserResponseInvoker( IUserResponseInvoker invoker  )
    {
        Log.i( "setting userResponseInvoker" );
        userResponseInvoker = invoker;
    }


    /**
     *
     * @param obj - An optional function to store an object by the application
     *
     */
    public static void setSessionObject( Object obj )
    {
        Log.d( "setObject invoked with "  + obj.toString() );
        userObject = obj;
    }


    /**
     *
     * Function to be invoked for incoming signalling messages from server
     *
     * @param sessionId an optional session id to identify session
     * @param message incoming signalling message from server
     *
     */
    public static void processAppMessageFromServer( String sessionId, String message )
    {
        Log.i( "AppMessageReceived for " + sessionId + ", message: " + message );

        JSONObject obj;

        try {
            obj = new JSONObject( message );
        } catch (JSONException e) {
            Log.e( "Sigmessage is in invalid JSON format" ,  e );
            return;
        }

        String type;

        try {
            type  = obj.getString( "type" );
        } catch (JSONException e) {
            Log.e( "'type' attribute is missing ", e );
            return;
        }

        try {
            if (type.equals("requestav")) {
                String rqParam = obj.getString("param");

                if (userResponseInvoker == null) {
                    Log.d("userResponseInvoker has not been set");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            passAppMessageToServer(sessionId, "{ \"type\":\"avtstatus\", \"param\":\"audio\" }");
                            AppIntegration.startav(sessionId);
                        }
                    });

                    return;
                }

                userResponseInvoker.invoke(sessionId, rqParam);
                // we can't proceed without customer's approval, hence returning for now.
                //We'll continue this in setUserResponse.
                return;
            }

            if (type.equals("avtstatus")) {
                String rqParam = obj.getString("param");
                if (rqParam.equals("audio")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppIntegration.startav(sessionId);
                        }
                    });
                }

                return;
            }

            if( type.equals( "bye" ) || type.equals( "endav" )  )
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppIntegration.endav("ended by tmac");
                    }
                });
                return;
            }

            if( type.equals( "onend" ) )
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppIntegration.endav("ended by server");
                    }
                });
                return;
            }

            if( type.equals( "configs" ) )
            {
                Configs.Instance.configByJSON( obj );
                return;
            }

            if( type.equals( "callqueued" ) )
            {
                String avtype = obj.getString( "avtype" );
                if( avtype.equals("audio") || ( avtype.equals("video")  ) )
                {
                    AudioPlay.createAndPlay( activity.getApplicationContext()  );
                }
                return;
            }

            if( type.equals( "agt1stconnect" ) )
            {
                String avtype = obj.getString( "avtype" );
                if( avtype.equals("audio") || ( avtype.equals("video")  ) )
                {
                    AudioPlay.stopAndDestroy();
                }
                return;
            }

            if( activeSession == null )
            {
                Log.w( "No active session, message ignored" );
                return;
            }

            if( type.equals( "avcontrol" ) )
            {
                String value  = obj.getString( "value" );

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        if( value.equals( "muteaudio" ) )
                        {
                            activeSession.pause( true );
                        }
                        else if( value.equals( "unmuteaudio" ) )
                        {
                            activeSession.resume();
                        }
                        else
                        {
                            Log.w( "Unknown avcontrol value = '" + value + "', ignored " );
                        }
                    }
                });
                return;
            }

            Log.d( "Passing message to active RTCSession" );
            activeSession.onSigMessage( message );

        }
        catch ( JSONException ex )
        {
            Log.e( "Invalid message", ex );
            return;
        }
        catch ( Exception ex )
        {
            Log.e( "Unexpected error in processing message", ex );
            return;
        }
    }

    public static void setActivity( android.app.Activity activity )
    {
        AppIntegration.activity = activity;
    }

    private  static  Object userObject;
    private  static android.app.Activity activity;
    private static IUserResponseInvoker userResponseInvoker;
    private static RTCSession activeSession;

    private static class PrivateSigImpl implements Signalling
    {
        @Override
        public void sendSigMessage(String message)
        {
            passAppMessageToServer( activeSession.getSessionId(), message );
        }

        @Override
        public void init(SignallingDelegate delegate, String sid)
        {
            // not required
        }

        @Override
        public void close() {
            // not required
        }
    }


    /**
     *
     * Function to send messages to server
     *
     * @param sessionId an optional id to indicate session
     * @param message signaling message
     *
     */
    private static void passAppMessageToServer( String sessionId, String message )
    {
        Log.i( "Sending AppMessage for " + sessionId + ", message : " + message );

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // call correct function to send it out, below line is for tetherfi testapp
            }
        });
    }


    public static RTCSession getActiveRTCSession()
    {
        return activeSession;
    }


    private static void runOnUiThread( Runnable runner )
    {
        // there are otherways to run on main thread
        activity.runOnUiThread( runner );
    }


    /**
     *
     * Video related functions
     *
     * @param listener listener for video related events. null == AudioMode
     *
     */
    public static void setVideoEnabled( VideoEventsListener listener )
    {
        if( listener == null )
        {
            Configs.Instance.avmode = Configs.AVMode.AUDIO;
        }
        else
        {
            Configs.Instance.avmode = Configs.AVMode.VIDEO;
            VideoSessionFactory.Instance.initialize( listener,  activity );
        }
    }


    public static void tryReconnect()
    {
        if( activeSession != null )
        {
            activeSession.reNegotiate();
        }
    }


}

