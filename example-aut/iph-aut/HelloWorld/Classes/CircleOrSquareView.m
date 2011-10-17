//
//  CircleView.m
//  ThreeViews
//
//  Created by Chakra on 12/05/10.
//  Copyright 2010 Chakra Interactive Pvt Ltd. All rights reserved.
//

#import "CircleOrSquareView.h"


@implementation CircleOrSquareView

@synthesize redOrBlue;
@synthesize circleOrSquare;
@synthesize drawing;

- (id)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        // Initialization code
		[self setDrawing:true];
    }
    return self;
}


- (void)drawRect:(CGRect)rect {
	if (!drawing) {
		return;
	}
	NSLog(@"Drawing shape");
	CGContextRef contextRef = UIGraphicsGetCurrentContext();
	// Draw a circle (border only)
	//CGContextStrokeEllipseInRect(contextRef, CGRectMake(100, 100, 25, 25));          
	
	if (!redOrBlue) {
		CGContextSetRGBFillColor(contextRef, 255, 0, 0, 0.5);
		CGContextSetRGBStrokeColor(contextRef, 255, 0, 0, 1);
	} else {
		CGContextSetRGBFillColor(contextRef, 0, 0, 255, 0.5);
		CGContextSetRGBStrokeColor(contextRef, 0, 0, 255, 1);
	}
	
	// Draw a circle (border only)
	if (circleOrSquare) {
		NSLog(@"Drawing circle");
		
		CGContextStrokeEllipseInRect(contextRef, CGRectMake(10, 10, 150, 150));
		
		/*CGContextStrokeEllipseInRect(contextRef, CGRectMake([self frame].origin.x - ([self frame].origin.x / 2),
														[self frame].origin.y + ([self frame].origin.y / 2),
														25,
														25));*/
	} else {
		CGContextStrokeRect(contextRef, CGRectMake(10, 10, 150, 150));
	}
	
/*	
	
	CGContextRef contextRef = UIGraphicsGetCurrentContext();
	
	CGContextSetRGBFillColor(contextRef, 0, 0, 255, 0.1);
	CGContextSetRGBStrokeColor(contextRef, 0, 0, 255, 0.5);
	
	// Draw a circle (filled)
	CGContextFillEllipseInRect(contextRef, CGRectMake(100, 100, 25, 25));
	
	// Draw a circle (border only)
	CGContextStrokeEllipseInRect(contextRef, CGRectMake(100, 100, 25, 25));
	
	
	// Get the graphics context and clear it
	CGContextRef ctx = UIGraphicsGetCurrentContext();
	CGContextClearRect(ctx, rect);
	
	// Draw a green solid circle
	CGContextSetRGBFillColor(ctx, 0, 255, 0, 1);
	CGContextFillEllipseInRect(ctx, CGRectMake(100, 100, 25, 25));
	
	// Draw a yellow hollow rectangle
	CGContextSetRGBStrokeColor(ctx, 255, 255, 0, 1);
	CGContextStrokeRect(ctx, CGRectMake(195, 195, 60, 60));
	
	// Draw a purple triangle with using lines
	CGContextSetRGBStrokeColor(ctx, 255, 0, 255, 1);
	CGPoint points[6] = { CGPointMake(100, 200), CGPointMake(150, 250),
		CGPointMake(150, 250), CGPointMake(50, 250),
	CGPointMake(50, 250), CGPointMake(100, 200) };
	CGContextStrokeLineSegments(ctx, points, 6);
	*/
}


- (void)drawRedOrBlue:(Boolean)redOrBlue_ withCircleOrSquare:(Boolean)circleOrSquare_ {
	[self setRedOrBlue:redOrBlue_];
	[self setCircleOrSquare:circleOrSquare_];
}

- (void)dealloc {
    [super dealloc];
}


@end
