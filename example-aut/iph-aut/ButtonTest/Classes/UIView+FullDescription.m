//
//  UIView+FullDescription.m
//  SelfTesting
//
//  Created by Matt Gallagher on 9/10/08.
//  Copyright 2008 Matt Gallagher. All rights reserved.
//
//  Permission is given to use this source code file, free of charge, in any
//  project, commercial or otherwise, entirely at your risk, with the condition
//  that any redistribution (in part or whole) of source code must retain
//  this copyright and permission notice. Attribution in compiled projects is
//  appreciated but not required.
//

#import "UIView+FullDescription.h"

@implementation UIView (FullDescription)

//
// fullDescription
//
// Builds a tree of data about all the views starting at this view
// and traversing all subviews. Data includes:
//	- className (name of the subclass of UIView)
//	- address (address in memory)
//	- tag
//	- text (if any)
//	- title (if any)
//	- subviews (recursive structures)
//
- (NSMutableDictionary *)fullDescriptionDict
{
	
	NSString* header = nil;
	if ([NSStringFromClass([self class]) isEqual:@"UIWindow"]) {
		
		// add Window tags...
		NSLog(@"Adding window tags.");
		//header = [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"utf-8\"?><GUIStructure><GUI><Window>"];
		header = [NSString stringWithFormat:@"<GUIStructure><GUI><Window>"];
		NSLog(@"Header: %@",header );
	}
	
	// Add current view attributes.
	NSString* attribute_list = @"<Attributes>";
	NSString* property_list = [NSString stringWithFormat:
							   @"<Property><Name>x</Name><Value>%@</Value></Property>"
							   "<Property><Name>y</Name><Value>%@</Value></Property>"
							   "<Property><Name>width</Name><Value>%@</Value></Property>"
							   "<Property><Name>height</Name><Value>%@</Value></Property>"
							   "<Property><Name>className</Name><Value>%@</Value></Property>", 
							[[NSNumber numberWithFloat:self.frame.origin.x] stringValue],
							[[NSNumber numberWithFloat:self.frame.origin.y] stringValue],
							[[NSNumber numberWithFloat:self.frame.size.width] stringValue],
							[[NSNumber numberWithFloat:self.frame.size.height] stringValue],
							NSStringFromClass([self class])];
	
	
	if ([self respondsToSelector:@selector(touchesBegan:withEvent:)])
	{
		NSString *eventAttributes = [NSString stringWithFormat:@"%@%@", property_list,
									 @"<Property><Name>INVOKE</Name><Value>TOUCH</Value></Property>"];
		
		property_list = eventAttributes;
		//[description setValue:@"INVOKE_TOUCH" forKey:@"touchesBegan:withEvent:"];
		
	}
	//tableView:willSelectRowAtIndexPath:
	if ([self respondsToSelector:@selector(tableView:willSelectRowAtIndexPath:)])
	{
		NSString *eventAttributes = [NSString stringWithFormat:@"%@%@", property_list,
						 @"<Property><Name>INVOKE</Name><Value>SELECT_ROW</Value></Property>"];
		property_list = eventAttributes;
		//[description setValue:@"INVOKE_TOUCH_TABLE" forKey:@"tableView:willSelectRowAtIndexPath:"];
		
	}
	
	NSString* xml_attribute_list = [NSString stringWithFormat:@"%@%@</Attributes>",
									attribute_list, property_list];

	NSDictionary* frame =
		[NSDictionary dictionaryWithObjectsAndKeys:
			[NSNumber numberWithFloat:self.frame.origin.x], @"x",
			[NSNumber numberWithFloat:self.frame.origin.y], @"y",
			[NSNumber numberWithFloat:self.frame.size.width], @"width",
			[NSNumber numberWithFloat:self.frame.size.height], @"height",
		nil];
	NSMutableDictionary* description =
		[NSMutableDictionary dictionaryWithObjectsAndKeys:
			[NSNumber numberWithInteger:(NSInteger)self], @"address",
			NSStringFromClass([self class]), @"className",
			frame, @"frame",
			xml_attribute_list, @"xml_list",
			[NSNumber numberWithInteger:[self tag]], @"tag",
			[self valueForKeyPath:@"subviews.fullDescription"], @"subviews",
		nil];

	
	if ([self respondsToSelector:@selector(text)])
	{
		[description setValue:[self performSelector:@selector(text)] forKey:@"text"];
	}
	if ([self respondsToSelector:@selector(title)])
	{
		[description setValue:[self performSelector:@selector(title)] forKey:@"title"];
	}
	if ([self respondsToSelector:@selector(currentTitle)])
	{
		[description setValue:[self performSelector:@selector(currentTitle)] forKey:@"currentTitle"];
	}
	
	NSLog(@"XML list = %@", [description objectForKey:@"xml_list"]);
	
	NSString* footer = nil;
	if ([NSStringFromClass([self class]) isEqual:@"UIWindow"]) {
		// Close window tags.
		//footer = [NSString stringWithFormat:@"</Window></GUI></GUIStructure>"];
		footer = [NSString stringWithFormat:@"</Window></GUI></GUIStructure>"];
		NSLog(@"Footer: %@", footer);
	}
	
	NSString* xml_entry = nil;
	if (!(header == nil)){ //&& footer != nil) {// && xml_attribute_list != nil) {
		NSLog(@"Header and footer are not null");
		NSLog(@"Description: %@", description);
		NSLog(@"%@.", header);
		
		NSLog(@"%@", [description objectForKey:@"xml_list"]);
		xml_entry = [NSString stringWithFormat:@"%@%@%@",
					 header,
					 xml_attribute_list, //[description objectForKey:@"xml_list"],
					 footer];
	} else { // if ([description objectForKey:@"xml_list"] != nil) {
		NSLog(@"No need to write header and footer.");
		xml_entry = [NSString stringWithFormat:@"%@",
					 [description objectForKey:@"xml_list"]];
	}
	
	[description setObject:xml_entry forKey:@"xml_entry"];
	return description;
}

- (NSString *)fullDescription
{
	
	NSString* header = nil;
	if ([NSStringFromClass([self class]) isEqual:@"UIWindow"]) {
		
		// add Window tags...
		NSLog(@"Adding window tags.");
		//header = [NSString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"utf-8\"?><GUIStructure><GUI><Window>"];
		header = [NSString stringWithFormat:@"<GUIStructure><GUI><Window>"];
		NSLog(@"Header: %@",header );
	} else {
		header = [NSString stringWithFormat:@"<Container>"];
	}
	
	// Add current view attributes.
	NSString* attribute_list = @"<Attributes>";
	NSString* property_list = [NSString stringWithFormat:
							   @"<Property><Name>x</Name><Value>%@</Value></Property>"
							   "<Property><Name>y</Name><Value>%@</Value></Property>"
							   "<Property><Name>width</Name><Value>%@</Value></Property>"
							   "<Property><Name>height</Name><Value>%@</Value></Property>"
							   "<Property><Name>className</Name><Value>%@</Value></Property>"
							   "<Property><Name>address</Name><Value>%@</Value></Property>",
							   //"<Property><Name>ID</Name><Value>%@</Value></Property>",
							   [[NSNumber numberWithFloat:self.frame.origin.x] stringValue],
							   [[NSNumber numberWithFloat:self.frame.origin.y] stringValue],
							   [[NSNumber numberWithFloat:self.frame.size.width] stringValue],
							   [[NSNumber numberWithFloat:self.frame.size.height] stringValue],
							   NSStringFromClass([self class]),
							   [[NSNumber numberWithInteger:(NSInteger)self] stringValue]
							   //[NSString stringWithFormat:@"%@_%@",
								//NSStringFromClass([self class]),
								//[[NSNumber numberWithInteger:(NSInteger)self] stringValue]]
							   ];
	
	if ([self respondsToSelector:@selector(touchesBegan:withEvent:)])
//		&& [NSStringFromClass([self class]) isEqual:@"UIRoundedRectButton"])
	{
		NSString *eventAttributes = [NSString stringWithFormat:@"%@%@", property_list,
									 @"<Property><Name>INVOKE</Name><Value>TOUCH</Value></Property>"];
		
		property_list = eventAttributes;
		//[description setValue:@"INVOKE_TOUCH" forKey:@"touchesBegan:withEvent:"];
		
	}
	//tableView:willSelectRowAtIndexPath:
	if ([self respondsToSelector:@selector(tableView:willSelectRowAtIndexPath:)])
	{
		NSString *eventAttributes = [NSString stringWithFormat:@"%@%@", property_list,
									 @"<Property><Name>INVOKE</Name><Value>SELECT_ROW</Value></Property>"];
		property_list = eventAttributes;
		//[description setValue:@"INVOKE_TOUCH_TABLE" forKey:@"tableView:willSelectRowAtIndexPath:"];
		
	}
	
	NSString* xml_attribute_list = [NSString stringWithFormat:@"%@%@</Attributes>",
									attribute_list, property_list];
	
	NSString* window_footer = nil;
	if ([NSStringFromClass([self class]) isEqual:@"UIWindow"]) {
		window_footer = [NSString stringWithFormat:@"%@</Window>",
						 xml_attribute_list];
		
		xml_attribute_list = window_footer;
	}
	
	NSDictionary* frame =
	[NSDictionary dictionaryWithObjectsAndKeys:
	 [NSNumber numberWithFloat:self.frame.origin.x], @"x",
	 [NSNumber numberWithFloat:self.frame.origin.y], @"y",
	 [NSNumber numberWithFloat:self.frame.size.width], @"width",
	 [NSNumber numberWithFloat:self.frame.size.height], @"height",
	 nil];
	NSMutableDictionary* description =
	[NSMutableDictionary dictionaryWithObjectsAndKeys:
	 [NSNumber numberWithInteger:(NSInteger)self], @"address",
	 NSStringFromClass([self class]), @"className",
	 frame, @"frame",
	 xml_attribute_list, @"xml_list",
	 [NSNumber numberWithInteger:[self tag]], @"tag",
	 [self valueForKeyPath:@"subviews.fullDescription"], @"subviews",
	 nil];
	
	NSString* child_xml_header = @"";// [self valueForKeyPath:@"subviews.fullDescription"], @"subviews"
	
	for (UIView *subview in self.subviews) {
        NSString* subview_xml = [subview fullDescription];
		
		if (subview_xml) {
			NSString* container_xml = [NSString stringWithFormat:@"%@%@",//@"%@<Container>%@</Container>",
									   child_xml_header, subview_xml];
			
			child_xml_header = container_xml;
		}
    }
	
	NSString* child_xml = @"";
	// Nothing in the contents, don't bother adding it.
	if ([child_xml_header length] != 0) {
		if ([NSStringFromClass([self class]) isEqual:@"UIWindow"]) {
			child_xml = child_xml_header;
		} else {
			child_xml = [NSString stringWithFormat:@"<Contents>%@</Contents>",
						 child_xml_header];
		}
	}
	
	// NSLog(@"Child xml = %@", child_xml);
	
	if ([self respondsToSelector:@selector(text)])
	{
		[description setValue:[self performSelector:@selector(text)] forKey:@"text"];
	}
	if ([self respondsToSelector:@selector(title)])
	{
		[description setValue:[self performSelector:@selector(title)] forKey:@"title"];
	}
	if ([self respondsToSelector:@selector(currentTitle)])
	{
		[description setValue:[self performSelector:@selector(currentTitle)] forKey:@"currentTitle"];
	}
	
	// NSLog(@"XML list = %@", [description objectForKey:@"xml_list"]);
	
	NSString* footer = nil;
	if ([NSStringFromClass([self class]) isEqual:@"UIWindow"]) {
		// Close window tags.
		//footer = [NSString stringWithFormat:@"</Window></GUI></GUIStructure>"];
		footer = [NSString stringWithFormat:@"</GUI></GUIStructure>"];
		// NSLog(@"Footer: %@", footer);
	} else {
		footer = [NSString stringWithFormat:@"</Container>"];
		// NSLog(@"Footer: %@", footer);
	}
	
	NSString* xml_entry = nil;
	if (!(header == nil)){ //&& footer != nil) {// && xml_attribute_list != nil) {
		/*NSLog(@"Header and footer are not null");
		NSLog(@"Description: %@", description);
		NSLog(@"%@.", header);
		
		NSLog(@"%@", [description objectForKey:@"xml_list"]);*/
		xml_entry = [NSString stringWithFormat:@"%@%@%@%@",
					 header,
					 xml_attribute_list, //[description objectForKey:@"xml_list"],
					 child_xml,
					 footer];
	} else { // if ([description objectForKey:@"xml_list"] != nil) {
		NSLog(@"No need to write header and footer.");
		xml_entry = [NSString stringWithFormat:@"%@",
					 [description objectForKey:@"xml_list"]];
	}
	
	[description setObject:xml_entry forKey:@"xml_entry"];
	return xml_entry;
}

- (UIView *)findViewWithClass:(NSString *)class andAddress:(NSNumber *)address {
	NSLog(@"Looking for view: %@, with address: %@.", class, address);
	
	// Check if this current view matches.
	if ([NSStringFromClass([self class]) isEqualToString:class]) {
		NSLog(@"...class names are equal");
		
		if (((NSInteger) self) == [address intValue]) {
			NSLog(@"...address is equal too!");
			return self;
		}
	}
	
	for (UIView *subview in [self subviews]) {
		UIView* response = [subview findViewWithClass:class andAddress:address];
		if (response) {
			return response;
		}
	}
	return nil;
}

@end
