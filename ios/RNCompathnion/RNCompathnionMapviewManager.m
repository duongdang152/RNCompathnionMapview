#import <React/RCTViewManager.h>
#import "RNCompathnionMapManager.h"
#import "RNCompathnionMap.h"

@interface RNCompathnionMapviewManager : RCTViewManager
@end

@implementation RNCompathnionMapviewManager
{
    NSConditionLock *_shouldStartLoadLock;
    BOOL _shouldStartLoad;
}

RCT_EXPORT_MODULE()

- (UIView *)view
{
    RNCompathnionMap *mapView = [[RNCompathnionMap alloc] init];
    return mapView;
}

RCT_EXPORT_METHOD(setupWithConfig:(SDKConfig *)config)
{
    [MapManager.shared setupWithConfig:config];
}

@end
