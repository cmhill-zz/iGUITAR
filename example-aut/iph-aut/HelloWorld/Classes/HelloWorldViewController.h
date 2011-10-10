//
//  HelloWorldViewController.h
//  HelloWorld
//
//  Created by Will on 10/6/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HelloWorldViewController : UIViewController {

	//IBOutlet UIImageView* circleOrSquareImage;
	IBOutlet UISegmentedControl *segment;
}

- (IBAction)changeSeg;

@end

