#import "RNCompathnionMapManager.h"
#import "RNCompathnionMap.h"
#import <React/RCTUIManager.h>
#import <Maps/Maps.h>

@interface RNCompathnionMapManager () // <MapManagerDelegate>

@property (nonatomic, strong) MapManager *mapManager;

@end

@implementation RNCompathnionMapManager

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (instancetype)init
{
    if ((self = [super init])) {
        _mapManager = [MapManager shared];
        //    _mapManager.delegate = self;
    }
    return self;
}


@end
