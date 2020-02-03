//
//  VERSION.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 7/1/19.
//  Copyright © 2019 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>

extern NSString* g_TARGET_WRTCVER;

@interface Version : NSObject

+(int*)getVersionInt;

+(NSString*)getVersionStr;

@end
