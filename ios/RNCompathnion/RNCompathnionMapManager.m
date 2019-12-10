#import "RNCompathnionMapManager.h"
#import "RNCompathnionMap.h"
#import <React/RCTUIManager.h>
#import <Maps/Maps.h>
#import <React/RCTUIManager.h>
#import "RNMapSetupObserver.h"

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

- (NSArray<NSString *> *)supportedEvents {
    return @[];
}

- (dispatch_queue_t)methodQueue
{
  return self.bridge.uiManager.methodQueue;
}

RCT_EXPORT_MODULE(MapViewModule);

RCT_EXPORT_METHOD(initMap:(NSString *)baseHostUrl venueCode:(NSString *)venueCode credUsername:(NSString *)credUsername credPassword:(NSString *)credPassword credClientId:(NSString *)credClientId credClientSecret:(NSString *)credClientSecret)
{
    [RNMapSetupObserver sharedInstance];
    ServerCredential *serverCerdential = [[ServerCredential alloc] initWithUsername:credUsername
                                                                           password:credPassword
                                                                           clientId:credClientId
                                                                       clientSecret:credClientSecret];
    SDKConfig *config = [[SDKConfig alloc] initWithBaseHostUrl:baseHostUrl
                                                     venueCode:venueCode
                                                    credential:serverCerdential];

    [MapManager.shared setupWithConfig:config];
}


RCT_EXPORT_METHOD(focusPOI:(nonnull NSNumber *)reactTag poiCode:(nonnull NSString *)poiCode)
{
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RNCompathnionMap *> *viewRegistry) {
          RNCompathnionMap *view = viewRegistry[reactTag];
          if (![view isKindOfClass:[RNCompathnionMap class]]) {
            RCTLogError(@"Invalid view returned from registry, expecting RCTWebView, got: %@", view);
          } else {
            [view focusPOI:poiCode];
          }
    }];
}

RCT_EXPORT_METHOD(unfocusPOI:(nonnull NSNumber *)reactTag)
{
    NSLog(@"### UNFOCUS POI");
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RNCompathnionMap *> *viewRegistry) {
          RNCompathnionMap *view = viewRegistry[reactTag];
          if (![view isKindOfClass:[RNCompathnionMap class]]) {
            RCTLogError(@"Invalid view returned from registry, expecting RCTWebView, got: %@", view);
          } else {
            [view unFocusPOI];
          }
    }];
}

RCT_EXPORT_METHOD(navigatePOIToPOI:(nonnull NSNumber *)reactTag
                  startPOI:(nonnull NSString *)startPOI
                  endPOI:(nonnull NSString *)endPOI
                  disabledPath:(BOOL) disabledPath)
{
    NSLog(@"### START NAVIGATION");
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RNCompathnionMap *> *viewRegistry) {
          RNCompathnionMap *view = viewRegistry[reactTag];
          if (![view isKindOfClass:[RNCompathnionMap class]]) {
            RCTLogError(@"Invalid view returned from registry, expecting RCTWebView, got: %@", view);
          } else {
            [view navigatePOIToPOI:startPOI endPOI:endPOI disabledPath:disabledPath];
          }
    }];
}

RCT_EXPORT_METHOD(navigateCurrentToPOI:(nonnull NSNumber *)reactTag destinationPOI:(nonnull NSString *)destinationPOI disabledPath:(BOOL) disabledPath)
{
    NSLog(@"### START NATIVAGAION FROM CURRENT");
   [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RNCompathnionMap *> *viewRegistry) {
         RNCompathnionMap *view = viewRegistry[reactTag];
         if (![view isKindOfClass:[RNCompathnionMap class]]) {
           RCTLogError(@"Invalid view returned from registry, expecting RCTWebView, got: %@", view);
         } else {
           [view navigateCurrentToPOI:destinationPOI disabledPath:disabledPath];
         }
   }];
}



@end
