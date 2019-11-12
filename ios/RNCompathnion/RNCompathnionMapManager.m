#import "RNCompathnionMapManager.h"
#import "RNCompathnionMap.h"
#import <Maps/Maps.h>

@interface RNCompathnionMapManager () <MapManagerDelegate>

@property (nonatomic, strong) MapManager *mapManager;

@end

@implementation RNCompathnionMapManager

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

@end
