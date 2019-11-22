#import <UIKit/UIKit.h>
#import <Maps/Maps.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTView.h>

@interface RNCompathnionMap : RCTView

@property (nullable, strong, nonatomic) Maps *mapViewcontroller;

-(void)focusPOI:(NSString *_Nullable)poiCode;
-(void)unFocusPOI;
-(void)navigatePOIToPOI:(NSString *_Nonnull)startPOI endPOI:(NSString *_Nullable)endPOI disabledPath:(BOOL) disabledPath;
-(void)navigateCurrentToPOI:(NSString *_Nullable)destinationPOI disabledPath:(BOOL) disabledPath;

@end
