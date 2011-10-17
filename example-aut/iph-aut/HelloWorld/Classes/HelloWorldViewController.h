//
//  HelloWorldViewController.h
//  HelloWorld
//
//  Created by Will on 10/6/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CircleOrSquareView.h"

@interface HelloWorldViewController : UIViewController {

	//IBOutlet UIImageView* circleOrSquareImageView;
	IBOutlet CircleOrSquareView* circleOrSquareView;
	Boolean redOrBlue;
	Boolean circleOrSquare;
	// IBOutlet UISegmentedControl *circleOrSquareSegment;
	// IBOutlet UISegmentedControl *redOrBlueSegment;
	
	IBOutlet UIButton *rectangleButton;
	IBOutlet UIButton *circleButton;
	IBOutlet UIButton *redButton;
	IBOutlet UIButton *blueButton;
}

// - (IBAction)changeColorSeg:(id)sender;
// - (IBAction)changeShapeSeg:(id)sender;
- (IBAction)resetShape;
- (IBAction)createShape;

- (IBAction)selectRedButton:(id)sender;
- (IBAction)selectBlueButton:(id)sender;
- (IBAction)selectCircleButton:(id)sender;
- (IBAction)selectRectangleButton:(id)sender;

@end

