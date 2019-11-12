#import <UIKit/UIKit.h>
#import <Maps/Maps.h>

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

@interface RNCompathnionMap : UIView

@property (nullable, strong, nonatomic) Maps *mapViewcontroller;
//@property (nonatomic, weak) id<CPNMapsDelegate> delegate;

@end
