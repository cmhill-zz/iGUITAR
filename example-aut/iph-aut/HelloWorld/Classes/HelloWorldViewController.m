//
//  HelloWorldViewController.m
//  HelloWorld
//
//  Created by Will on 10/6/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "HelloWorldViewController.h"

@implementation HelloWorldViewController

/*- (IBAction)changeColorSeg:(id)sender{
	NSLog(@"Changing color.");
	if(redOrBlueSegment.selectedSegmentIndex == 0){
		NSLog(@"Changing color to blue.");
		// self.view.backgroundColor = [UIColor redColor];
		redOrBlue = false;
		[sender setSelectedSegmentIndex:0];
	}
	if(redOrBlueSegment.selectedSegmentIndex == 1){
		NSLog(@"Changing color to red.");
		// self.view.backgroundColor = [UIColor blueColor];
		[sender setSelectedSegmentIndex:1];
		redOrBlue = true;
	}
}

- (IBAction)changeShapeSeg:(id)sender{
	NSLog(@"Changing shape.");
	if(circleOrSquareSegment.selectedSegmentIndex == 0){
		NSLog(@"Changing shape to square.");
		// self.view.backgroundColor = [UIColor redColor];
		circleOrSquare = false;
		[sender setSelectedSegmentIndex:0];
	}
	if(circleOrSquareSegment.selectedSegmentIndex == 1){
		NSLog(@"Changing shape to circle.");
		// self.view.backgroundColor = [UIColor blueColor];
		[sender setSelectedSegmentIndex:1];
		circleOrSquare = true;
	}
}*/

- (IBAction)resetShape {
	NSLog(@"Resetting shape.");
	[circleButton setTitle:@"" forState:UIControlStateNormal];
	[rectangleButton setTitle:@"0" forState:UIControlStateNormal];
	[redButton setTitle:@"0" forState:UIControlStateNormal];
	[blueButton setTitle:@"" forState:UIControlStateNormal];
	
	[circleOrSquareView setDrawing:false];
	[circleOrSquareView setNeedsDisplay];
	// [redOrBlueSegment setSelectedSegmentIndex:0];
	// [circleOrSquareSegment setSelectedSegmentIndex:0];
}

- (IBAction)createShape {
	NSLog(@"Trying to draw shape");
	//circleOrSquareView = [[CircleOrSquareView alloc] initWithFrame:[self.view frame]];
	[circleOrSquareView setDrawing:true];
	[circleOrSquareView drawRedOrBlue:redOrBlue withCircleOrSquare:circleOrSquare];
	[circleOrSquareView setNeedsDisplay];
	//[circleOrSquareView drawRect:[circleOrSquareView frame]];
	//[circleOrSquareView drawRect:[circleOrSquareView frame]];
}

- (IBAction)selectRedButton:(id)sender {
	NSLog(@"Selected red button.");
	
	[redButton setTitle:@"0" forState:UIControlStateNormal];
	[blueButton setTitle:@"" forState:UIControlStateNormal];
	
	redOrBlue = false;
}
- (IBAction)selectBlueButton:(id)sender {
	NSLog(@"Selected blue button.");
	
	[redButton setTitle:@"" forState:UIControlStateNormal];
	[blueButton setTitle:@"0" forState:UIControlStateNormal];
	
	redOrBlue = true;
}
- (IBAction)selectCircleButton:(id)sender {
	NSLog(@"Selected circle button.");
	
	[circleButton setTitle:@"0" forState:UIControlStateNormal];
	[rectangleButton setTitle:@"" forState:UIControlStateNormal];
	
	// circleButton.backgroundColor = [UIColor purpleColor];
	// rectangleButton.backgroundColor = [UIColor whiteColor];
	circleOrSquare = true;
}
- (IBAction)selectRectangleButton:(id)sender {
	NSLog(@"Selected rectangle button.");
	
	[circleButton setTitle:@"" forState:UIControlStateNormal];
	[rectangleButton setTitle:@"0" forState:UIControlStateNormal];
	
	// rectangleButton.backgroundColor = [UIColor purpleColor];
	circleOrSquare = false;
}

/*
- (void)drawRect:(CGRect)rect {
	NSLog(@"Drawing shape");
	CGContextRef contextRef = UIGraphicsGetCurrentContext();
	CGContextSetRGBFillColor(contextRef, 0, 0, 255, 0.1);
	CGContextSetRGBStrokeColor(contextRef, 0, 0, 255, 0.5);
	// Draw a circle (border only)
	CGContextStrokeEllipseInRect(contextRef, CGRectMake(100, 100, 25, 25));          

}*/


// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
		//circleOrSquareView = [[CircleOrSquareView alloc] initWithFrame:[self.view frame]]; //initWithFrame:[circleOrSquareImageView frame]];
		[circleButton setTitle:@"" forState:UIControlStateNormal];
		[rectangleButton setTitle:@"0" forState:UIControlStateNormal];
		[redButton setTitle:@"0" forState:UIControlStateNormal];
		[blueButton setTitle:@"" forState:UIControlStateNormal];
    }
    return self;
}

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}

@end
