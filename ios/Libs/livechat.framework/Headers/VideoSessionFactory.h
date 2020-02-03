//
//  VideoSessionFactory.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 12/7/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "VideoEventsListener.h"

@class VideoSession;

@interface VideoSessionFactory : NSObject
{
    VideoEventsListener* listener;
}

+(instancetype) Instance;

+(void)initialize;

// Internal function
-(VideoSession*) createVideoSession;

-(BOOL) initialize:(VideoEventsListener*)listener;

-(void) pauseVideo;

-(void) resumeVideo;

@end
