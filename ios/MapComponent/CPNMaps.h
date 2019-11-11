//
//  CPNMaps.h
//  CPNMaps
//
//  Created by danghuuduong on 11/3/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//

#import <React/RCTView.h>
#import <Maps/Maps.h>
#import <UIKit/UIKit.h>

@class CPNMapView, CPNPOI, CPNPathRequest, CPNLevel, CPNSection, CPNConfig;

/**
 * Special scheme used to pass messages to the injectedJavaScript
 * code without triggering a page load. Usage:
 *
 *   window.location.href = RCTJSNavigationScheme + '://hello'
 */
extern NSString *const RCTJSNavigationScheme;

@protocol CPNMapsDelegate <NSObject>

- (void)mapviewDidFinishLoadingMap:(CPNMapView *)mapview;
- (void)mapview:(CPNMapView *)mapview didSeselectPOI:(CPNPOI*)poi;
- (void)mapview:(CPNMapView *)mapview didDeselectPOI:(CPNPOI*)poi;
- (void)mapview:(CPNMapView *)mapview didUpdateLevel:(CPNLevel*)level;
- (void)mapview:(CPNMapView *)mapview didUpdateSection:(CPNLevel*)level;
- (void)mapviewDidEndNavigation:(CPNMapView *)mapview;
- (void)mapview:(CPNMapView *)mapview willStartNavigation:(CPNPathRequest*)pathRequest;

@end

@interface CPNMaps : UIView

@property (nullable, strong, nonatomic) Maps *mapViewcontroller;
@property (nonatomic, weak) id<CPNMapsDelegate> delegate;

@end
