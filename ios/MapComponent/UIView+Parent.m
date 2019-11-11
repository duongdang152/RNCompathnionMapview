//
//  UIView+Parent.m
//  CPNMaps
//
//  Created by danghuuduong on 11/10/19.
//  Copyright © 2019 Compathnion. All rights reserved.
//

#import "UIView+Parent.h"

@implementation UIView (Parent)

- (UIViewController *)parentViewController {
    UIResponder *responder = self;
    while ([responder isKindOfClass:[UIView class]])
        responder = [responder nextResponder];
    return (UIViewController *)responder;
}

@end
