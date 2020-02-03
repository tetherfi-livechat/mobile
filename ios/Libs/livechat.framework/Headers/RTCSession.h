//
//  RTCSession.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 21/3/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Signalling.h"

//typedef void(^CompletionHandlerType)(NSError * _Nullable error);

@class AVAudioSession;

@interface RTCEventsHandler : NSObject
{
    enum EndType
    {
        EXT_CALL, COMMON, ERROR, NET_ERROR
    };
    
    @private
    void(^__onStartSuccess)(void);
    void(^__onStartFaliure)(NSError * _Nullable error);
    void(^__onDisconnect)(void);
    void(^__onReconnect)(void);
    void(^__onReconnectableEnd)(void);
    void(^__onEnd)( enum EndType endType, NSString* _Nonnull reasonDesc );
    
    @public
        void(^onStartSuccess)(void);
        void(^onStartFaliure)(NSError * _Nullable error);
        void(^onDisconnect)(void);
        void(^onReconnect)(void);
        void(^onReconnectableEnd)(void);
        void(^onEnd)( enum EndType endType, NSString* _Nullable reasonDesc );
    
}


+(NSString*_Nonnull) endTypeToStr:(enum EndType)type;

-(instancetype _Nonnull ) initWithHandlers:(void(^_Nonnull)(void))onsuccess
                                    onfail:(void(^_Nonnull)(NSError * _Nullable))onfail
                              onDisconnect:( void(^_Nonnull)(void) )onDisconnect
                               onReconnect:( void(^_Nonnull)(void) )onReconnect
                        onReconnectableEnd:( void(^_Nonnull)(void) )onReconnEnd
                                     onEnd:(void(^_Nonnull)(enum EndType, NSString*_Nonnull))onEnd;

@end


@interface RTCSession : NSObject
{
    BOOL status_sigchannel;
}

@property id<Signalling> _Nonnull signalling;
@property(readonly) NSString* _Nonnull sessionId;

-(id _Nonnull )init;

-(BOOL)start:(id<Signalling> _Nonnull)signalling forSession:(NSString*_Nullable)sid eventsHandler:( RTCEventsHandler*_Nonnull)eventsHandler;

-(void)end:(NSString*_Nullable)desc;
-(void)onSigMessage:(NSString* _Nonnull)msg;
-(void)changeSpeaker:(BOOL)isLoud;
-(void)muteMic:(BOOL)set;
-(void)reNegotiate;

-(void)pause:(BOOL)withMusic;
-(void)resume;

-(void)pauseVideo;
-(void)resumeVideo;

-(void)processSigMessage:(NSDictionary*)dict;

-(AVAudioSession*_Nullable)getAudioSession;

@end

