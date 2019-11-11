//
//  MapView.h
//  CPNMaps
//
//  Created by danghuuduong on 11/10/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Maps/Maps.h>

NS_ASSUME_NONNULL_BEGIN

@interface MapView : UIView

@property (nullable, strong, nonatomic) Maps *mapViewcontroller;

@end

NS_ASSUME_NONNULL_END
