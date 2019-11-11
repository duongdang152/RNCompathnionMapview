//
//  CPNMapViewManager.m
//  CPNMaps
//
//  Created by danghuuduong on 11/4/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//

#import <React/RCTViewManager.h>
#import "CPNMapManager.h"
#import "CPNMaps.h"
#import "MapView.h"

@interface CPNMapViewManager : RCTViewManager
@end

@implementation CPNMapViewManager
{
  NSConditionLock *_shouldStartLoadLock;
  BOOL _shouldStartLoad;
}

RCT_EXPORT_MODULE(CPNMap)

- (UIView *)view
{
  CPNMaps *mapView = [[CPNMaps alloc] init];
  return mapView;
}

RCT_EXPORT_METHOD(setupWithConfig:(SDKConfig *)config)
{
    [MapManager.shared setupWithConfig:config];
}


@end
