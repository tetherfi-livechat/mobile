//
//  Factory.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 20/3/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Signalling.h"


@interface Factory : NSObject
{
    
}
+(void)initialize;
+(id<Signalling>) CreateSignalling: (id<SignallingDelegate>)del forSession:(NSString*)sid;


@end
