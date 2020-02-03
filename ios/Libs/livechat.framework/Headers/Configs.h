//
//  Configs.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 20/3/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#import <Foundation/Foundation.h>


@class RTCIceServer;

enum AVMode
{
    AVModeVideo,
    AVModeAudio,
    AVModeScreen
};

enum MediaConnMode
{
    MediaConnModeAvProxyOnly,
    MediaConnModePeer2PeerOnly,
    MediaConnModeAll,
};

struct VideoProperties
{
    int width;
    int height;
    int fps;
};

struct SdpMungProps
{
    BOOL enabled;
    int sessionLimit;
    int audioChannelLimit;
    int videoChannelLimit;
    int vCodec_8_9;
};

@interface Configs : NSObject
{
}

+(instancetype) Instance;

+(void)initialize;

-(void)addIceServer:(NSString*)url user:(NSString*)username pass:(NSString*)passwd __deprecated_msg("Use addAvProxy instead.");;

-(void)addAvProxy:(NSString*)url user:(NSString*)username pass:(NSString*)passwd;

-(void)configByJSON:(NSDictionary*)json;

/**
* WSSignalling, ExternalSignalling
*/
@property(strong) NSString* sigType;

/**
 * Signalling details any as below
 *
 * ws://host:port
 * tcp://host:port
 * intl:
 */
@property(strong) NSString* sigDetail;

@property NSString* pauseAudioSoundFile;

@property NSMutableArray<RTCIceServer*>* iceServers;

@property NSDictionary* mediaConstraintsRequired;

@property NSDictionary* mediaConstraintsOptional;

@property struct VideoProperties videoProps;

@property enum MediaConnMode mediaConnectionMode;

@property enum AVMode avmode;

// Timeouts in Seconds
@property int startTimeout;
@property int reConnectableTimeout;
@property int disconnectedTimeout;
@property BOOL mirrorSelfview;
@property int rtcLogLevel;
@property NSDictionary* wrtcFwInfo;

@property BOOL enableCallkit;
@property NSString* avAppName;
@property NSString* nameOfRemoteParty;
@property BOOL detectGsmCall;

@property struct SdpMungProps sdpMung;

@end


