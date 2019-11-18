#import "RNCompathnionMapManager.h"
#import "RNCompathnionMap.h"
#import <React/RCTUIManager.h>


@interface RNCompathnionMapviewManager : RCTViewManager
@end

@implementation RNCompathnionMapviewManager
{
    NSConditionLock *_shouldStartLoadLock;
    BOOL _shouldStartLoad;
}

RCT_EXPORT_MODULE(MapViewComponent)

- (UIView *)view
{
    RNCompathnionMap *mapView = [[RNCompathnionMap alloc] init];
    return mapView;
}


@end
