//
//  RNMapEventEmitter.h
//  RNCompathnionMapview
//
//  Created by danghuuduong on 11/18/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <React/RCTEventEmitter.h>
#import <React/RCTBridge.h>
#import <Maps/Maps.h>

NS_ASSUME_NONNULL_BEGIN

@interface RNMapEventEmitter : RCTEventEmitter <RCTBridgeModule>

+ (void)mapViewDidSelectPoi:(NSString *)poiID;
+ (void)mapViewDidDeselectPoi:(NSString *)poiID;

@end

NS_ASSUME_NONNULL_END
