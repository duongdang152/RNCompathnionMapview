//
//  CPNMaps.m
//  CPNMaps
//
//  Created by danghuuduong on 11/3/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//

#import <React/RCTView.h>
#import "CPNMaps.h"
#import "MapView.h"
#import "UIView+Parent.h"
#import <Maps/Maps.h>

@implementation CPNMaps

- (instancetype)initWithFrame:(CGRect)frame {
    if ((self = [super initWithFrame:frame])) {
        
    }
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    if (self.mapViewcontroller == nil ) {
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
    ServerCredential *serverCerdential = [[ServerCredential alloc] initWithUsername:@"yohoapp@sagadigits.com"
                                                                           password:@"Sck@32ls"
                                                                           clientId:@"93ad3e123657"
                                                                       clientSecret:@"XmWNdO2E5gabsiLKI9UPxcny"];
    SDKConfig *config = [[SDKConfig alloc] initWithBaseHostUrl:@"https://yoho.compathnion.com"
                                                     venueCode:@"yoho"
                                                    credential:serverCerdential];
    [MapManager.shared setupWithConfig:config];
    [parentVc addChildViewController:map];
    [self addSubview:map.view];
    map.view.frame = self.bounds;
    [map didMoveToParentViewController:parentVc];
    self.mapViewcontroller = map;
}

@end
