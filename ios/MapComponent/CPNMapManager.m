//
//  CPNMapManager.m
//  CPNMaps
//
//  Created by danghuuduong on 11/3/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//

//#import "RCTBridge.h"
#import "CPNMapManager.h"
#import "CPNMaps.h"
#import <Maps/Maps.h>

@interface CPNMapManager () <MapManagerDelegate>

@property (nonatomic, strong) MapManager *mapManager;

@end

@implementation CPNMapManager

- (instancetype)init
{
  if ((self = [super init])) {
    _mapManager = [MapManager shared];
    _mapManager.delegate = self;
  }
  return self;
}

RCT_EXPORT_METHOD(setupWithConfig:(SDKConfig *)config)
{
  [self.mapManager setupWithConfig:config];
}

- (void)didFinishMapSetup
{
    
}

@end
