#import "RNCompathnionMap.h"
#import <React/RCTView.h>
#import "UIView+Parent.h"
#import <Maps/Maps.h>
#import "RNMapEventEmitter.h"

@interface RNCompathnionMap () <MapsDelegate>

@property (nonatomic, strong) MapManager *mapManager;
@property (nonatomic) BOOL didEmbeded;

@end

@implementation RNCompathnionMap

+ (BOOL)requiresMainQueueSetup
 {
     return YES;
 }

- (instancetype)init {
   
    if (self = [super init]) {
         [self embedView];
        self.clipsToBounds = YES;
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
    map.delegate = self;
    map.enableNavigationViews = YES;
    map.disablePOIPreview = YES;;
    _mapViewcontroller = map;
    [parentVc addChildViewController:self.mapViewcontroller];
    [self addSubview:self.mapViewcontroller.view];
    self.mapViewcontroller.view.frame = self.bounds;
    [self.mapViewcontroller didMoveToParentViewController:parentVc];
    _didEmbeded = YES;
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(didSelectPOI:)
                                                 name:MapConstants.MAP_DID_SELECT_POI_NOTIFICATION
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(didDeselectPOI:)
                                                 name:MapConstants.MAP_DID_DESELECT_POI_NOTIFICATION
                                               object:nil];
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


- (void)didSelectPOI:(NSNotification *)notification
{
    NSString *poiID = (NSString *)notification.object;
    [RNMapEventEmitter mapViewDidSelectPoi:poiID];
}

- (void)didDeselectPOI:(NSNotification *)notification
{
    NSString *poiID = (NSString *)notification.object;
    [RNMapEventEmitter mapViewDidDeselectPoi:poiID];
}

@end


