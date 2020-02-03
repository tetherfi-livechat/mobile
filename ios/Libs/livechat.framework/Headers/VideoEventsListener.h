//
//  VideoEventsListener.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 12/7/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#ifndef VideoEventsListener_h
#define VideoEventsListener_h

//#import <WebRTC/RTCVideoTrack.h>
#import <UIKit/UIView.h>

@class RTCVideoTrack;

@interface ViewEvent : NSObject
{
    
}

-(instancetype) initWith:( RTCVideoTrack* ) track;
-(UIView*) createView;

@end

typedef void(^OnLocalViewEvent)( UIView* view );
typedef void(^OnRemoteViewEvent)( ViewEvent* viewEvent );
typedef void(^OnResetTracksEvent)( );

@interface VideoEventsListener : NSObject
{
    // Not required for ios
    //void(^onScreenPermission)();
    //void(^onScreenPermissionFailed)();
}

@property OnLocalViewEvent onLocalVideo;
@property OnRemoteViewEvent onRemoteVideoAdded;
@property OnLocalViewEvent onRemoteVideoRemoved;
@property OnResetTracksEvent onResetTracks;

@end


@interface VideoEventsListenerInternal
{
    
}

@end


#endif /* VideoEventsListener_h */


