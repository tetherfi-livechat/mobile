//
//  Audio.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 19/7/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AudioPlay : NSObject

-(instancetype) init;

-(instancetype) initwithLocation:(NSString*)location;

-(void) play;

-(void) stop;

-(BOOL) isPlaying;

+(void) createAndPlay;

+(void) stopAndDestroy;

@end
