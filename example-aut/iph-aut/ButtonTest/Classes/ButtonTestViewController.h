//
//  ButtonTestViewController.h
//  ButtonTest
//
//  Created by Christopher Hill on 10/3/11.
//  Copyright 2011 University of Maryland. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ButtonTestViewController : UIViewController {
	IBOutlet UILabel *label;
}

- (IBAction)buttonClicked:(id)sender;

@end