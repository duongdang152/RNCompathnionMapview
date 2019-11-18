#import "RNCompathnionMap.h"
#import <React/RCTView.h>
#import "UIView+Parent.h"
#import <Maps/Maps.h>

@interface RNCompathnionMap () //<MapManagerDelegate>

@property (nonatomic, strong) MapManager *mapManager;
@property (nonatomic) BOOL didEmbeded;

@end

@implementation RNCompathnionMap

+ (BOOL)requiresMainQueueSetup
 {
     return YES;  // only do this if your module initialization relies on calling UIKit!
 }

- (instancetype)init {
   
    if (self = [super init]) {
         [self embedView];
    }
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    if (self.didEmbeded == NO) {
        [self embedView];
    } else {
        [self.mapViewcontroller view].frame = self.bounds;
    }
}

- (void)embedView {
    UIViewController *parentVc = [self parentViewController];
    if (!parentVc) {
        return;
    }
    Maps *map = [Maps new];
    _mapViewcontroller = map;
    [parentVc addChildViewController:self.mapViewcontroller];
    [self addSubview:self.mapViewcontroller.view];
    self.mapViewcontroller.view.frame = self.bounds;
    [self.mapViewcontroller didMoveToParentViewController:parentVc];
    _didEmbeded = YES;
}


- (void)focusPOI:(NSString *)poiCode {
    [self.mapViewcontroller focusWithPoiID:poiCode];
}

- (void)unFocusPOI {
    [self.mapViewcontroller unFocusPOI];
}

- (void)navigatePOIToPOI:(NSString *)startPOI endPOI :(NSString *)endPOI disabledPath:(BOOL)disabledPath {
    [self.mapViewcontroller startNavigationWithStartPOI:startPOI
                                                 endPOI:endPOI
                                            disablePath:disabledPath];
}

- (void)navigateCurrentToPOI:(NSString *)destinationPOI disabledPath:(BOOL)disabledPath {
    [self.mapViewcontroller startNavigationFromCurrentLocationWithDestinationPOI:destinationPOI
                                                                     disablePath:disabledPath];
}

@end


