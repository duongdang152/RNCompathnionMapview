//
//  CPNMapManager.h
//  CPNMaps
//
//  Created by danghuuduong on 11/3/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//


#import <React/RCTViewManager.h>

@class CPNMapManager;

@protocol CPNMapManagerDelegate <NSObject>

- (void)didFinishMapSetup:(CPNMapManager *)map;
- (void)mapview:(CPNMapManager *)map didFailMapSetup:(NSString*)error;
- (void)didFinishAmenitiesSetup:(CPNMapManager *)map;
- (void)mapview:(CPNMapManager *)map didFailAmenitiesSetup:(NSString*)error;
- (void)didFinishOccupantsSetup:(CPNMapManager *)map;
- (void)mapview:(CPNMapManager *)map didFailOccupantsSetup:(NSString*)error;
- (void)didFinishPathsSetup:(CPNMapManager *)map;
- (void)mapview:(CPNMapManager *)map didFailPathsSetup:(NSString*)error;
- (void)didFinishLevelsSetup:(CPNMapManager *)map;
- (void)mapview:(CPNMapManager *)map didFailLevelsSetup:(NSString*)error;

@end

@interface CPNMapManager: NSObject

- (void)setup;

@end



