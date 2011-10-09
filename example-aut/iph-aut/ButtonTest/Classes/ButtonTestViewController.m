//
//  ButtonTestViewController.m
//  ButtonTest
//
//  Created by Christopher Hill on 10/3/11.
//  Copyright 2011 University of Maryland. All rights reserved.
//

#import "ButtonTestViewController.h"

@implementation ButtonTestViewController

//
// nibName
//
// Hard-code the nibName for this view controller.
//
- (NSString *)nibName
{
	return @"ButtonTestViewController";
}

//
// buttonClicked:
//
// Sets the label text to the view name when the button is clicked.
//
- (IBAction)buttonClicked:(id)sender
{
	NSLog(@"Wow, someone touched me!");
	label.text = [NSString stringWithFormat:@"Button clicked on: %@", self.title];
}


/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
*/

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
