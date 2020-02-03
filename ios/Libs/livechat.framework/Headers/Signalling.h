//
//  Signalling.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 20/3/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol SignallingDelegate
-(void)onReady: (nullable NSString*)sid;
-(void)onFail;
-(void)onSigMessage: (NSString* _Nonnull)msg;
-(void)onClose;
@end

@protocol Signalling

/**
 nil of initialization fail
 */

@required
-(void)sendSigMessage:(NSString*_Nonnull)message;

@optional
-(void)close;

@optional
@property(nullable) id<SignallingDelegate> sigDelegate;

@optional
+(id _Nullable)createInstanceWithDelegate:(id<SignallingDelegate> _Nullable) delegate forSession:(NSString* _Nullable)sid;

@end

