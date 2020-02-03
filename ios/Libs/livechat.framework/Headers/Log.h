//
//  Log.h
//  livechat
//
//  Created by Nuwan Abeysinghe on 22/3/18.
//  Copyright © 2018 ___NUWAN_ABEYSINGHE___. All rights reserved.
//

#ifndef Log_h
#define Log_h


void NSLogDbgT( NSString *cls, NSString* msg );
void NSLogWarnT( NSString *cls, NSString* msg );
void NSLogInfoT( NSString *cls, NSString* msg );
void NSLogErrT( NSString *cls, NSString* msg );

/*
static inline void NSLogEx(NSString *format, ...)
{
    va_list li;
    va_start(li, format);
    NSLogv( format, li );
    va_end(li);
}
*/

void set_livechat_logger( void(*func)( NSString* format, ... ), BOOL includeTime );

void set_livechat_current_sid( NSString* sid );

void init_for_swift_logging(void);

enum livechat_log_level
{
    ll_debug,
    ll_info,
    ll_warn,
    ll_error,
    ll_absent
};

void set_livechat_log_level ( enum livechat_log_level lvl );

NSString* getLastXLogLines( int x );


#define logd(fmt) NSLogDbgT( NSStringFromClass([self classForCoder]), fmt );
#define logdp(fmt,...) NSLogDbgT( NSStringFromClass([self classForCoder]), [NSString stringWithFormat:fmt, __VA_ARGS__] );
#define logi(fmt) NSLogInfoT( NSStringFromClass([self classForCoder]), fmt );
#define logip(fmt,...) NSLogInfoT( NSStringFromClass([self classForCoder]), [NSString stringWithFormat:fmt, __VA_ARGS__] );
#define logw(fmt) NSLogWarnT( NSStringFromClass([self classForCoder]), fmt );
#define logwp(fmt,...) NSLogWarnT( NSStringFromClass([self classForCoder]), [NSString stringWithFormat:fmt, __VA_ARGS__] );
#define loge(fmt) NSLogErrT( NSStringFromClass( [self classForCoder]), fmt );
#define logep(fmt,...) NSLogErrT(  NSStringFromClass( [self classForCoder]), [NSString stringWithFormat:fmt, __VA_ARGS__] );

#define logfd(fmt) NSLogDbgT( [NSString stringWithUTF8String:__FUNCTION__], fmt );
#define logfdp(fmt,...) NSLogDbgT( [NSString stringWithUTF8String:__FUNCTION__], [NSString stringWithFormat:fmt, __VA_ARGS__] );
#define logfi(fmt) NSLogInfoT( [NSString stringWithUTF8String:__FUNCTION__], fmt );
#define logfip(fmt,...) NSLogInfoT( [NSString stringWithUTF8String:__FUNCTION__] , [NSString stringWithFormat:fmt, __VA_ARGS__] );
#define logfw(fmt) NSLogWarnT( [NSString stringWithUTF8String:__FUNCTION__], fmt );
#define logfwp(fmt,...) NSLogWarnT( [NSString stringWithUTF8String:__FUNCTION__] , [NSString stringWithFormat:fmt, __VA_ARGS__] );
#define logfe(fmt) NSLogErrT( [NSString stringWithUTF8String:__FUNCTION__] , fmt );
#define logfep(fmt,...) NSLogErrT( [NSString stringWithUTF8String:__FUNCTION__] , [NSString stringWithFormat:fmt, __VA_ARGS__] );


#endif /* Log_h */

