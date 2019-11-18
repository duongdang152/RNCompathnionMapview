#import <React/RCTBridgeModule.h>
#import <React/RCTViewManager.h>

@class RNCompathnionMapManager;

@protocol RNCompathnionMapManagerDelegate <NSObject>

//- (void)didFinishMapSetup:(RNCompathnionMapManager *)map;
//- (void)mapview:(RNCompathnionMapManager *)map didFailMapSetup:(NSString*)error;
//- (void)didFinishAmenitiesSetup:(RNCompathnionMapManager *)map;
//- (void)mapview:(RNCompathnionMapManager *)map didFailAmenitiesSetup:(NSString*)error;
//- (void)didFinishOccupantsSetup:(RNCompathnionMapManager *)map;
//- (void)mapview:(RNCompathnionMapManager *)map didFailOccupantsSetup:(NSString*)error;
//- (void)didFinishPathsSetup:(RNCompathnionMapManager *)map;
//- (void)mapview:(RNCompathnionMapManager *)map didFailPathsSetup:(NSString*)error;
//- (void)didFinishLevelsSetup:(RNCompathnionMapManager *)map;
//- (void)mapview:(RNCompathnionMapManager *)map didFailLevelsSetup:(NSString*)error;

@end

@interface RNCompathnionMapManager: NSObject <RCTBridgeModule>


@end
