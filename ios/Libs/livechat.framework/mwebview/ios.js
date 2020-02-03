
livechat.OnConferenceUserLeft = function(agent, msg)
{
    livechat.postMessage( {event: "OnConferenceUserLeft", param: agent} );
}
    
livechat.OnStartSuccess = function(sid, tchatid, param)
{
    livechat.postMessage( {event: "OnStartSuccess", param: [sid, tchatid, param] } );
}
    
livechat.OnStartFailed = function(errCode)
{
    livechat.postMessage( {event: "OnStartFailed", param: errCode } );
}
    
livechat.OnOpenSuccess = function(session)
{
   livechat.postMessage( {event: "OnOpenSuccess", param: JSON.stringify(session) } );
}
    
livechat.OnOpenFailed = function(errCode)
{
    livechat.postMessage( {event: "OnOpenFailed", param: errCode } );
}
    
livechat.OnRemoteUserConnected = function(name, agentId, sessionId)
{
    livechat.postMessage( {event:"OnRemoteUserConnected",
                             param: [name , agentId ,sessionId ] } );
}
    
livechat.OnStatusNotification = function(reason)
{
   livechat.postMessage( {event:"OnStatusNotification" , param:reason } );
}
    
livechat.OnCallQueued = function( position, param )
{
    livechat.postMessage( { event:"OnCallQueued", param:[ position, null ] } );
}

livechat.OnMessageReceived = function( agent, msg, mid )
{
    livechat.postMessage( { event:"OnMessageReceived", param:[ agent, msg] } );
}

livechat.OnAppMessageReceived = function( agent, msg )
{
    livechat.postMessage( { event:"OnAppMessageReceived", param:[ agent, msg ] } );
}

livechat.OnTypingStateChanged = function( agent, state )
{
    livechat.postMessage( { event:"OnTypingStateChanged", param:[ agent, state ] } );
}

livechat.OnServerStateChanged = function( state )
{
    livechat.postMessage( { event:"OnServerStateChanged", param:state } );
}

log = {
 
    debug : function(str)
    {
        livechat.postMessage( { event:'jslog', param:['debug', str] } );
    },
    
    info : function(str)
    {
        livechat.postMessage( { event:'jslog', param:['info', str] } );
    },
    
    warn : function(str)
    {
        livechat.postMessage( { event:'jslog', param:['warn', str] } );
    },
    
    err : function(str)
    {
        livechat.postMessage( { event:'jslog', param:['err', str] } );
    }
}

window.onerror = function(error)
{
    log.err( error );
}

