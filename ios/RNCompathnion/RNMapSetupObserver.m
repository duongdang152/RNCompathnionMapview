//
//  RNMapSetupObserver.m
//  RNCompathnionMapview
//
//  Created by danghuuduong on 12/10/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RNMapSetupObserver.h"
#import <Maps/Maps.h>

static NSString *const MAP_SETUP = @"MAP_SETUP";
static NSString *const POIS_SETUP = @"POIS_SETUP";
static NSString *const LEVELS_SETUP = @"LEVELS_SETUP";
static NSString *const OCCUPANTS_SETUP = @"OCCUPANTS_SETUP";
static NSString *const AMENITIES_SETUP = @"AMENITIES_SETUP";

@interface RNMapSetupObserver()

@property (strong, nonatomic) NSMutableArray<NSNotificationName> *finishedProcess;

@end

@implementation RNMapSetupObserver

+ (instancetype)sharedInstance
{
  static RNMapSetupObserver *sharedInstance;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    sharedInstance = [self new];
  });

  return sharedInstance;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.finishedProcess = [NSMutableArray new];
        [self setupObserve];
    }
    return self;
}

- (BOOL)isFullSetupCompleted {
    return self.finishedProcess.count == 0;
}

- (void)setupObserve {
    [NSNotificationCenter.defaultCenter removeObserver:self];
    [self.finishedProcess addObjectsFromArray:@[MAP_SETUP, LEVELS_SETUP, POIS_SETUP, OCCUPANTS_SETUP, AMENITIES_SETUP]];
    for (NSString *notification in self.finishedProcess) {
        [NSNotificationCenter.defaultCenter addObserver:self selector:@selector(handleNotification:) name:notification object:nil];
    }
}

- (void)handleNotification:(NSNotification *)notification {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *name = notification.name;
        NSUInteger index = [self.finishedProcess indexOfObject:name];
        if (index != NSNotFound) {
            [self.finishedProcess removeObjectAtIndex:index];
        }
        if (self.finishedProcess.count == 0) {
            [NSNotificationCenter.defaultCenter removeObserver:self];
        }
    });
}

@end
