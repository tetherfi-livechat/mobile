//
//  SignallingRTCSession.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 24/4/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <livechat/livechat.h>
#import "RTCSession.h"

@interface RTCSignallingSession : RTCSession

-(BOOL)start:(NSString*)sid completionHandler:(RTCEventsHandler * ) completionHandler;

-(void)end:(NSString*)reason;

@end
