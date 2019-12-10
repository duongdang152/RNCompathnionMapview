//
//  RNMapSetupObserver.h
//  RNCompathnionMapview
//
//  Created by danghuuduong on 12/10/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface RNMapSetupObserver : NSObject

+ (instancetype)sharedInstance;

- (BOOL)isFullSetupCompleted;

@end


NS_ASSUME_NONNULL_END
