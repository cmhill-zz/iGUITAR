//
//  ButtonTestAppDelegate.h
//  ButtonTest
//
//  Created by Christopher Hill on 10/3/11.
//  Copyright 2011 University of Maryland. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ButtonTestViewController;

@interface ButtonTestAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    ButtonTestViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet ButtonTestViewController *viewController;

@end

