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


- (instancetype)initWithFrame:(CGRect)frame {
    if ((self = [super initWithFrame:frame])) {
        _mapManager = [MapManager shared];
        Maps *map = [Maps new];
        _mapViewcontroller = map;
        _didEmbeded = NO;
    //        _mapManager.delegate = self;
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
    
    ServerCredential *serverCerdential = [[ServerCredential alloc] initWithUsername:@"hkch@sagadigits.com"
                                                                           password:@"G^sx4;(yEV"
                                                                           clientId:@"0e8de7c60c1c"
                                                                       clientSecret:@"zlsYYDkWx6n9ph9BZPQVjlSU"];
    SDKConfig *config = [[SDKConfig alloc] initWithBaseHostUrl:@"https://hkch-staging.compathnion.com/"
                                                     venueCode:@"hkch"
                                                    credential:serverCerdential];
    [MapManager.shared setupWithConfig:config];
    [parentVc addChildViewController:self.mapViewcontroller];
    [self addSubview:self.mapViewcontroller.view];
    self.mapViewcontroller.view.frame = self.bounds;
    [self.mapViewcontroller didMoveToParentViewController:parentVc];
    _didEmbeded = YES;
}


- (void)focusPOI:(NSString *)poiCode {
    [self.mapViewcontroller focusWithPoiID:poiCode];
}

- (void)unfocusPOI {
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

RCT_EXPORT_MODULE(MapViewModule);

RCT_EXPORT_METHOD(initMap:(NSString *)baseHostUrl venueCode:(NSString *)venueCode credUsername:(NSString *)credUsername credPassword:(NSString *)credPassword credClientId:(NSString *)credClientId credClientSecret:(NSString *)credClientSecret)
{
//    ServerCredential *serverCerdential = [[ServerCredential alloc] initWithUsername:credUsername
//                                                                           password:credPassword
//                                                                           clientId:credClientId
//                                                                       clientSecret:credClientSecret];
//    SDKConfig *config = [[SDKConfig alloc] initWithBaseHostUrl:baseHostUrl
//                                                     venueCode:venueCode
//                                                    credential:serverCerdential];
//
//    [MapManager.shared setupWithConfig:config];
}


RCT_EXPORT_METHOD( focusPOI:(NSNumber *)reactTag poiCode:(NSString *)poiCode)
{
    NSLog(@"### FOCUS POI");
    [self.mapViewcontroller focusWithPoiID:poiCode];
}

RCT_EXPORT_METHOD( unfocusPOI:(NSNumber *)reactTag)
{
    NSLog(@"### UNFOCUS POI");
    [self.mapViewcontroller unFocusPOI];
}

RCT_EXPORT_METHOD( navigatePOIToPOI:(NSNumber *)reactTag startPOI:(NSString *)startPOI
                  endPOI:(NSString *)endPOI disabledPath:(BOOL) disabledPath)
{
    NSLog(@"### START NAVIGATION");
    [self.mapViewcontroller startNavigationWithStartPOI:startPOI
                                                 endPOI:endPOI
                                            disablePath:disabledPath];
}

RCT_EXPORT_METHOD( navigateCurrentToPOI:(NSNumber *)reactTag destinationPOI:(NSString *)destinationPOI disabledPath:(BOOL) disabledPath)
{
    NSLog(@"### START NATIVAGAION FROM CURRENT");
    [self.mapViewcontroller startNavigationFromCurrentLocationWithDestinationPOI:destinationPOI
                                                                     disablePath:disabledPath];
}

@end


