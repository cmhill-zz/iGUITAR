//
//  CircleView.h
//  ThreeViews
//
//  Created by Chakra on 12/05/10.
//  Copyright 2010 Chakra Interactive Pvt Ltd. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface CircleOrSquareView : UIView {
	Boolean redOrBlue;
	Boolean circleOrSquare;
	Boolean drawing;
}

@property (nonatomic) Boolean redOrBlue;
@property (nonatomic) Boolean circleOrSquare;
@property (nonatomic) Boolean drawing;

- (void)drawRedOrBlue:(Boolean)redOrBlue withCircleOrSquare:(Boolean)circleOrSquare;

@end
