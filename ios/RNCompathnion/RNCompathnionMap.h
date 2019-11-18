#import <UIKit/UIKit.h>
#import <Maps/Maps.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTView.h>
//@class CPNMapView, CPNPOI, CPNPathRequest, CPNLevel, CPNSection, CPNConfig;
//
//extern NSString *const RCTJSNavigationScheme;
//
//@protocol CPNMapsDelegate <NSObject>
//
//- (void)mapviewDidFinishLoadingMap:(CPNMapView *)mapview;
//- (void)mapview:(CPNMapView *)mapview didSeselectPOI:(CPNPOI*)poi;
//- (void)mapview:(CPNMapView *)mapview didDeselectPOI:(CPNPOI*)poi;
//- (void)mapview:(CPNMapView *)mapview didUpdateLevel:(CPNLevel*)level;
//- (void)mapview:(CPNMapView *)mapview didUpdateSection:(CPNLevel*)level;
//- (void)mapviewDidEndNavigation:(CPNMapView *)mapview;
//- (void)mapview:(CPNMapView *)mapview willStartNavigation:(CPNPathRequest*)pathRequest;
//
//@end

@interface RNCompathnionMap : RCTView <RCTBridgeModule>

@property (nullable, strong, nonatomic) Maps *mapViewcontroller;


-(void)focusPOI:(NSString *_Nullable)poiCode;
-(void)unFocusPOI;
-(void)navigatePOIToPOI:(NSString *_Nonnull)startPOI endPOI:(NSString *_Nullable)endPOI disabledPath:(BOOL) disabledPath;
-(void)navigateCurrentToPOI:(NSString *_Nullable)destinationPOI disabledPath:(BOOL) disabledPath;

@end
