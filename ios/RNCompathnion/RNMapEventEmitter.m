//
//  RNMapEventEmitter.m
//  RNCompathnionMapview
//
//  Created by danghuuduong on 11/18/19.
//  Copyright © 2019 Facebook. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "RNMapEventEmitter.h"

// Notification/Event Names
NSString *const konPOIClick = @"onPOIClick";
NSString *const konPOIUnClick = @"onPOIUnclick";
NSString *const konLocationMessageReceive = @"onLocationMessageReceive";

@implementation RNMapEventEmitter

RCT_EXPORT_MODULE(CustomMapView);

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

- (NSDictionary<NSString *, NSString *> *)constantsToExport {
  return @{ @"onPOIClick": konPOIClick,
            @"onPOIUnclick": konPOIUnClick,
            @"onLocationMessageReceive": konLocationMessageReceive
            };
}

- (NSArray<NSString *> *)supportedEvents {
  return @[konPOIClick,
           konPOIUnClick,
           konLocationMessageReceive];
}

- (void)startObserving {
  for (NSString *event in [self supportedEvents]) {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(handleNotification:)
                                                 name:event
                                               object:nil];
  }
}

- (void)stopObserving {
  [[NSNotificationCenter defaultCenter] removeObserver:self];
}


# pragma mark Public

+ (void)mapViewDidDeselectPoi:(NSString *)poiID {
    [self postNotificationName:@"onPOIClick" withPayload:poiID];
}

+ (void)mapViewDidSelectPoi:(NSString *)poiID {
     [self postNotificationName:@"onPOIUnclick" withPayload:poiID];
}

# pragma mark Private

+ (void)postNotificationName:(NSString *)name withPayload:(NSObject *)object {
  NSDictionary<NSString *, id> *payload = @{@"payload": object};
  
  [[NSNotificationCenter defaultCenter] postNotificationName:name
                                                      object:self
                                                    userInfo:payload];
}

- (void)handleNotification:(NSNotification *)notification {
  [self sendEventWithName:notification.name body:notification.userInfo];
}

@end
