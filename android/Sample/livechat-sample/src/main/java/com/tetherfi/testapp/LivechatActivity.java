package com.tetherfi.testapp;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import tetherfi.livechat.Configs;
import tetherfi.livechat.Livechat;
import tetherfi.livechat.Log;
import tetherfi.livechat.RTCSession;
import tetherfi.livechat.SdpMung;
import tetherfi.livechat.Version;
import tetherfi.livechat.logger.LvLogger;



public class LivechatActivity extends Activity
{
    Livechat lvSession;
    ViewGroup lvchat, logs, video, vactive;
    EditText m_LogsView;
    TextView m_LvchatVer;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        lvchat = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_livechat, null );
        logs = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_logs, null );
        video = (ViewGroup)getLayoutInflater().inflate( R.layout.activity_video, null );

        switchView( lvchat );
        addContentView( logs, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
        addContentView( video, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
        logs.setVisibility( View.GONE );
        m_LogsView = (EditText)findViewById( R.id.txtLogs );
        m_LvchatVer = ( TextView )findViewById( R.id.lvhatversion );
        m_LvchatVer.setText( Version.getVersionStr() );

        initApp();
    }


    private void switchView(ViewGroup v)
    {
        if( vactive != null ) {
            vactive.setVisibility(View.GONE);
        }
        setContentView( v );
        vactive = v;
        v.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed()
    {
        if( vactive == logs )
        {
            switchView( lvchat );
            return;
        }

        super.onBackPressed();
    }


    public void AddLogMessage( String msg )
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    m_LogsView.append(msg + "\n");
            }
        });
    }

    public void onClickClear(View v)
    {
        m_LogsView.setText( "" );
    }

    public void onClickLogs( View v )
    {
        switchView( logs );
    }

    public void onClickTest( View v )
    {
        //long id = sdp.testCreate();
        System.out.println( "instance id " + SdpMung.Instance().nativeInstance );
        SdpMung.Instance().finalize();
        System.out.println( "post fin instance id " + SdpMung.Instance().nativeInstance );
        SdpMung.Instance().finalize();
    }


    public void onClickVideo( View view )
    {
        switchView( video );
    }



    private void initNativeSig()
    {
        Configs.Instance.sigType = "tetherfi.livechat.signalling.NativeSig";
        Configs.Instance.sigDetail = "wss://192.168.1.102:8443/ws-chat-proxy/ws/chat";
    }

    private void initApp()
    {
        initNativeSig();

        Configs.Instance.setPauseAudio( R.raw.andi );

        Configs.Instance.mediaConnectionMode = Configs.MediaConnMode.MediaConnModeAll;
        lvSession = new Livechat( this.getApplicationContext() );
        lvSession.SetAvEventsHandler( avhandlers );
        lvSession.SetSessionEventsHandler( handlers );
        lvSession.SetLogger( logger );
        Configs.Instance.addAvProxy( "turn:demo.tetherfi.com:3579", "tetherfi", "nuwan" );
        Configs.Instance.addAvProxy( "turn:sit.tetherfi.com:3585", "tetherfi", "nuwan" );
    }


    public void onClickOpen( View v )
    {
        lvSession.Open();
    }


    public void onClickStart( View v )
    {
        String custDetails = "{\"pChannel\":\"\",\"pIntent\":\"demoappchat\",\"pNationality\":\"\",\"pLang\":\"\",\"pChatBotSid\":\"\",\"pAuthType\":\"\",\"pVerificationType\":\"\",\"pUserId\":\"\",\"pCIF\":\"\",\"pName\":\"Nu\",\"pGender\":\"Male\",\"pDOB\":\"\",\"pMobileNumber\":\"\",\"pCustomerTag\":\"\",\"pPIBissueDate\":\"\",\"pOTPPref\":\"\",\"pTokenSerialNumber\":\"\",\"pInvalidLogonAttempt\":\"\",\"pCustIdStatus\":\"\",\"pTokenActivated\":\"\",\"pTokenStatus\":\"\",\"pFailedTwoFACount\":\"\",\"pFirstLogiDate\":\"\",\"pfirstLoginTime\":\"\",\"plastLoginDate\":\"\",\"plastLoginTime\":\"\",\"pLeadID\":\"\",\"pApplicationID\":\"\",\"pOnboardingStatus\":\"\"}";

        try
        {
            Livechat.SessionDetails sessDetails = new Livechat.SessionDetails( custDetails );
            lvSession.Start( sessDetails, "[]" );
        }
        catch ( JSONException e )
        {
            Log.e( "Invalid JSON format" );
        }
    }


    public void onClickEnd( View v )
    {
        lvSession.End( "User clicked end" );
    }


    public void onClickReconnect( View v )
    {
        lvSession.TryReconnect();
    }

    private Livechat.SessionEventsHandler handlers = new Livechat.SessionEventsHandler()
    {
        @Override
        public void OnStartSuccess(String sid, String tchatid, String param)
        {
            Log.d( "OnStartSuccess" );
        }

        @Override
        public void OnMessageReceived(String agent, String msg)
        {
            Log.d( "OnMessageReceived" );
        }

        @Override
        public void OnAppMessageReceived(String agent, String msg)
        {
            Log.d( "OnAppMessageReceived" );
        }

        @Override
        public void OnStartFailed(String errorCode)
        {
            Log.d( "OnStartFailed" );
        }

        @Override
        public void OnOpenSuccess(String sessionDesc)
        {
            Log.d( "OnOpenSuccess" );
        }

        @Override
        public void OnOpenFailed(String errorCode)
        {
            Log.d( "OnOpenFailed" );
        }

        @Override
        public void OnEnd(String initiator, String reason)
        {
            Log.d( "OnEnd" );
        }

        @Override
        public void OnRemoteUserConnected(String name, String agentId, String sessionId)
        {
            Log.d( "OnRemoteUserConnected" );
        }

        @Override
        public void OnCallQueued(String position, String extra)
        {
            Log.d( "OnCallQueued" );
        }

        @Override
        public void OnStatusNotification(String notification)
        {
            Log.d( "OnStatusNotification" );
        }

        @Override
        public void OnSignallingStateChanged(String state)
        {
            Log.d( "OnSignallingStateChanged" );
        }

        @Override
        public void OnConferenceUserLeft(String agent, String msg)
        {
            Log.d( "OnConferenceUserLeft" );
        }

        @Override
        public void OnTypingStateChanged(String agent, int state)
        {
            Log.d( "OnTypingStateChanged" );
        }

        @Override
        public void OnCallbackRequestStatus(boolean isCustomReq, boolean status)
        {
            Log.d( "OnTypingStateChanged" );
        }
    };

    private Livechat.AvCallEventsHandler avhandlers = new Livechat.AvCallEventsHandler()
    {
        @Override
        public void OnRequestAvCall(String avmode)
        {
            Log.d( "OnRequestAvCall" );
            lvSession.AvResponse( avmode );


            if( avmode.equals( "video" ) )
                lvSession.StartVideoCall();
            else if( avmode.equals( "audio" ) )
                lvSession.StartAudioCall();
            else if( avmode.equals( "false" ) )
                ; // nothing to do
            else
                Log.e( "Unexpected avmode(1) : " + avmode );
        }

        @Override
        public void OnRespondAvCall(String avmode)
        {
            Log.d( "OnRespondAvCall" );

            if( avmode.equals( "video" ) )
                lvSession.StartVideoCall();
            else if( avmode.equals( "audio" ) )
                lvSession.StartAudioCall();
            else if( avmode.equals( "false" ) )
                ; // nothing to do
            else
                Log.e( "Unexpected avmode(2) : " + avmode );
        }

        @Override
        public void OnLocalVideo(SurfaceView localVideoView)
        {
            Log.d( "OnLocalVideo" );
            lvchat.addView( localVideoView );
        }

        @Override
        public void OnRemoteVideo(SurfaceView remoteVideoView)
        {
            Log.d( "OnRemoteVideo" );
            lvchat.addView( remoteVideoView );
        }

        @Override
        public void OnAvCallStartSuccess()
        {
            Log.d( "OnAvCallStartSuccess" );
        }

        @Override
        public void OnAvCallStartFailure(Exception if_any)
        {
            Log.d( "OnAvCallStartFailure" );
        }

        @Override
        public void OnMediaDisconnect()
        {
            Log.d( "OnMediaDisconnect" );
        }

        @Override
        public void OnMediaReconnect()
        {
            Log.d( "OnMediaReconnect" );
        }

        @Override
        public void OnMediaReconnectableEnd()
        {
            Log.d( "OnMediaReconnectableEnd" );
        }

        @Override
        public void OnAvCallEnd(RTCSession.RTCEventsHandler.EndType type, String reasonDesc)
        {
            Log.d( "OnAvCallEnd" );
        }

    };

    // End of Application Logic

    LvLogger logger  = new LvLogger() {
        @Override
        public void i(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " INFO {" + sid + "} " + tag + ": " + msg);
            super.i(tag, sid, msg, time);
        }

        @Override
        public void d(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " DEBUG {" + sid + "} " + tag + ": " + msg);
        }

        @Override
        public void e(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " ERROR {" + sid + "} " + tag + ": " + msg);
        }

        @Override
        public void w(String tag, String sid, String msg, String time) {
            AddLogMessage(time + " WARN {" + sid + "} " + tag + ": " + msg);
        }

    };

}

