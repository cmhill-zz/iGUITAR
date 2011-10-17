//
//  ScriptRunner.m
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

#ifdef SCRIPT_DRIVEN_TEST_MODE_ENABLED

#import "ScriptRunner.h"
#import "UIView+FullDescription.h"
#import "XPathQuery.h"
#import "TouchSynthesis.h"
#import <stdio.h>

const float SCRIPT_RUNNER_INTER_COMMAND_DELAY = .5;

@implementation ScriptRunner

@synthesize delegate;
@synthesize callback;
@synthesize errorCallback;

//
// init
//
// Init method for the object.
// 
- (id)init
{
	self = [super init];
	if (self != nil)
	{
		// Connect to java server.
		
		NSString *urlStr = @"http://localhost:8081/"; // [sender stringValue];
		if (![urlStr isEqualToString:@""]) {
			NSURL *website = [NSURL URLWithString:urlStr];
			if (!website) {
				NSLog(@"%@ is not a valid URL");
				return;
			}
			
			CFReadStreamRef readStream;
			CFWriteStreamRef writeStream;
			CFStreamCreatePairWithSocketToHost(NULL, (CFStringRef)[website host], 8081, &readStream, &writeStream);
			
			inputStream_ = (NSInputStream *)readStream;
			outputStream_ = (NSOutputStream *)writeStream;
			
			[inputStream_ setDelegate:self];
			//[outputStream_ setDelegate:self];
			
			[inputStream_ scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
			//[outputStream_ scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
			
			[inputStream_ open];
			[outputStream_ open];
		}
		
		NSData *fileData =
			[NSData dataWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"TestScript" ofType:@"plist"]];
		scriptCommands =
			[[NSPropertyListSerialization
				propertyListFromData:fileData
				mutabilityOption:NSPropertyListMutableContainers
				format:nil
				errorDescription:nil]
			retain];
		NSAssert([scriptCommands count] > 0, @"TestScript was not an array as expected.");
		
		[self retain];
		[self performSelector:@selector(runCommand) withObject:nil afterDelay:1.0];
	}
	return self;
}

//
// dealloc
//
// Releases instance memory.
//
- (void)dealloc
{
	[scriptCommands release];
	[super dealloc];
}

- (void)sendText:(NSString *)message {
    NSString * stringToSend = [NSString stringWithFormat:@"%@\n", message];
	NSData * dataToSend = [stringToSend dataUsingEncoding:NSUTF8StringEncoding];
	if (outputStream_) {
		int remainingToWrite = [dataToSend length];
		void * marker = (void *)[dataToSend bytes];
		while (0 < remainingToWrite) {
			int actuallyWritten = 0;
			actuallyWritten = [outputStream_ write:marker maxLength:remainingToWrite];
			remainingToWrite -= actuallyWritten;
			marker += actuallyWritten;
		}
		NSLog(@"Done sending message.");
	}
}

- (void)stream:(NSStream *)aStream handleEvent:(NSStreamEvent)streamEvent {
    NSInputStream * istream;
	 NSLog(@"Handle event.");
    switch(streamEvent) {
        case NSStreamEventHasBytesAvailable:;
			istream = (NSInputStream *)aStream;
            NSLog(@"Received stream");
			
            uint8_t buffer[2048];
            int actuallyRead = [inputStream_ read:buffer maxLength:2048];
            if (actuallyRead > 0) {
                //[ostream write:buffer maxLength:actuallyRead];
				NSString *output = [[NSString alloc] initWithBytes:buffer length:actuallyRead encoding:NSASCIIStringEncoding];
				if (nil != output)
				{
					NSLog(@"%@", output);
					
					if ([output isEqualToString:@"invoke main method"]) {
						NSLog(@"In main method call.");
						[self sendText:@"Hello"];
						NSLog(@"Finishing main method call.");
					} else if ([output isEqualToString:@"get root window list"]) {
						NSString* keyWindowDescription =
						[[[UIApplication sharedApplication] keyWindow] fullDescription];
						NSLog(@"%@", keyWindowDescription);
						[self sendText:keyWindowDescription];
						NSLog(@"Finishing get root window list.");
						
					} else if ([output hasPrefix:@"INVOKE"]) {
						[self sendText:@"Received INVOKE request!"];
						NSArray *chunks = [output componentsSeparatedByString: @" "];
						
						// Handle command (just TOUCH for the time being).
						if ([[chunks objectAtIndex:1] isEqualToString:@"TOUCH"]) {
							NSLog(@"Received TOUCH request.");
							
							NSString* class = [chunks objectAtIndex:2];
							//Class NSClassFromString([chunks objectAtIndex:2])
							NSNumber* address = [NSNumber numberWithInt:[[chunks objectAtIndex:3] intValue]];
							
							// Find the desire subview.
							UIView* viewToTouch = [[[UIApplication sharedApplication] keyWindow]
												   findViewWithClass:class andAddress:address];
							
							if (viewToTouch) {
								NSLog(@"Found a good view to touch!");
								[self performTouchInView:viewToTouch];
							} else {
								NSLog(@"Found no good view to touch :(");
							}
							
							NSLog(@"Sending response back");
							[self sendText:[NSString stringWithFormat:@"Touch happened for %@!", class]];
						}					
						
					} else {
						NSLog(@"Unknown command: %@", output);
						[self sendText:@"Unknown command"];
					}					
					[output release];
				}
            }
			break;
        case NSStreamEventEndEncountered:;
			NSLog(@"NSStreamEventEndEncountered\n" );
			break;
        case NSStreamEventHasSpaceAvailable:
			NSLog(@"NSStreamEventHasSpaceAvailable\n" );
			break;
        case NSStreamEventErrorOccurred:
			NSLog(@"NSStreamEventErrorOccurred\n" );
			NSError *theError = [aStream streamError];
			NSLog(@"%@", [theError localizedDescription]);
			break;
        case NSStreamEventOpenCompleted:
			NSLog(@"NSStreamEventOpenCompleted\n" );
			break;
        case NSStreamEventNone:
			NSLog(@"NSStreamEventNone\n" );
        default:
            break;
    }
}

//
// performTouchInView:
//
// Synthesize a touch begin/end in the center of the specified view. Since there
// is no API to do this, it's a dirty hack of a job.
//
- (void)performTouchInView:(UIView *)view
{
	UITouch *touch = [[UITouch alloc] initInView:view];
	UIEvent *eventDown = [[UIEvent alloc] initWithTouch:touch];
	
	[touch.view touchesBegan:[eventDown allTouches] withEvent:eventDown];
	
	[touch setPhase:UITouchPhaseEnded];
	UIEvent *eventUp = [[UIEvent alloc] initWithTouch:touch];
	
	[touch.view touchesEnded:[eventUp allTouches] withEvent:eventUp];
	
	[eventDown release];
	[eventUp release];
	[touch release];
}

//
// viewsForXPath:
//
// Generates an XML document from the current view tree and runs the specified
// XPath query on the document. If the resulting nodes contain "address" values
// then these values are interrogated to determine if they are UIViews. All
// UIViews found in this way are returned in the array.
//
- (NSArray *)viewsForXPath:(NSString *)xpath
{
	NSDictionary *keyWindowDescription =
		[[[UIApplication sharedApplication] keyWindow] fullDescription];
	NSData *resultData =
		[NSPropertyListSerialization
			dataFromPropertyList:keyWindowDescription
			format:NSPropertyListXMLFormat_v1_0
			errorDescription:nil];

	NSArray *queryResults = PerformXMLXPathQuery(resultData, xpath);
	NSMutableArray *views =
		[NSMutableArray arrayWithCapacity:[queryResults count]];
	for (NSDictionary *result in queryResults)
	{
		int i;
		int count = [[result objectForKey:@"nodeChildArray"] count];
		for (i = 0; i < count; i++)
		{
			NSDictionary *childNode = [[result objectForKey:@"nodeChildArray"] objectAtIndex:i];
			if ([[childNode objectForKey:@"nodeName"] isEqualToString:@"key"] &&
				[[childNode objectForKey:@"nodeContent"] isEqualToString:@"address"])
			{	
				if (i < count - 1)
				{
					NSDictionary *nextNode = [[result objectForKey:@"nodeChildArray"] objectAtIndex:i + 1];
					UIView *view =
						(UIView *)[[nextNode objectForKey:@"nodeContent"] integerValue];
					NSAssert([view isKindOfClass:[UIView class]],
						@"XPath selected memory address did not contain a UIView");
					[views addObject:view];
					break;
				}
			}
		}
	}
	
	return views;
}

//
// runCommand
//
// Runs the first command in the scriptCommands array and then removes it from
// the array.
//
// Two commands are supported:
//	- outputView (writes the XML for a view hierarchy to a file)
//	- simulateTouch (selects a UIView by XPath and simulates a touch within it)
//
- (void)runCommand
{
	NSDictionary *command = [scriptCommands objectAtIndex:0];
	NSString *commandName = [command objectForKey:@"command"];
	
	while (1) { }
	/*[NSThread sleepForTimeInterval:10];
	NSLog(@"Shutting down");
	[self release];
	exit(0);*/
	//
	// outputView
	//
	// This command outputs the current view hierarchy, starting with the
	// keyWindow, to a file.
	//
	// Required parameter:
	//	outputPath (file path to save PropertyList description of hierarchy)
	//
	// Optional paramter:
	//	viewXPath (only output views matching this XPath)
	//
	if ([commandName isEqualToString:@"outputView"])
	{
		NSString *path = [command objectForKey:@"outputPath"];
		if (path == nil)
		{
			fprintf(stderr, "### Command 'outputView' requires 'outputPath' parameter.\n");
			exit(1);
		}
		path = [path stringByExpandingTildeInPath];
		
		NSString *viewXPath = [command objectForKey:@"viewXPath"];
		
		printf("=== outputView\n    outputPath:\n        %s\n    viewXPath:\n        %s\n",
			[path cStringUsingEncoding:NSUTF8StringEncoding],
			[viewXPath cStringUsingEncoding:NSUTF8StringEncoding]);

		NSData *resultData;
		if (viewXPath)
		{
			NSArray *views = [self viewsForXPath:viewXPath];
			resultData =
				[NSPropertyListSerialization
					dataFromPropertyList:[views valueForKey:@"fullDescription"]
					format:NSPropertyListXMLFormat_v1_0
					errorDescription:nil];
		}
		else
		{
			NSDictionary *keyWindowDescription =
				[[[UIApplication sharedApplication] keyWindow] fullDescription];
			resultData =
				[NSPropertyListSerialization
					dataFromPropertyList:keyWindowDescription
					format:NSPropertyListXMLFormat_v1_0
					errorDescription:nil];
		}
		
		[resultData writeToFile:path options:NSAtomicWrite error:nil];
	}

	//
	// checkMatchCount
	//
	// This command verifies that the specified number of nodes matching
	// the given XPath query are found.
	//
	// Required parameters:
	//	viewXPath (search for views matching this XPath)
	//	matchCount (number of nodes found must equal this number)
	//
	else if ([commandName isEqualToString:@"checkMatchCount"])
	{
		NSString *viewXPath = [command objectForKey:@"viewXPath"];
		if (viewXPath == nil)
		{
			fprintf(stderr, "### Command 'checkMatchCount' requires 'viewXPath' parameter.\n");
			exit(1);
		}

		NSNumber *matchCount = [command objectForKey:@"matchCount"];
		if (matchCount == nil)
		{
			fprintf(stderr, "### Command 'checkMatchCount' requires 'matchCount' parameter.\n");
			exit(1);
		}

		printf("=== checkMatchCount\n    viewXPath:\n        %s\n    matchCount: %ld\n",
			[viewXPath cStringUsingEncoding:NSUTF8StringEncoding],
			[matchCount integerValue]);

		NSArray *views = [self viewsForXPath:viewXPath];
		
		NSLog(@"%@", [[views objectAtIndex:0] fullDescription]);
		
		if ([views count] != [matchCount integerValue])
		{
			fprintf(
				stderr,
				"### 'checkMatchCount' wanted a matching count of %ld but encountered %ld\n",
				[matchCount integerValue],
				[views count]);
			exit(1);
		}
	}

	//
	// simulateTouch
	//
	// Performs a synthesized touch down and touch up in a single view selected
	// by a given XPath query.
	//
	// Required parameters:
	//	viewXPath (search for a view matching this XPath)
	//
	else if ([commandName isEqualToString:@"simulateTouch"])
	{
		NSString *viewXPath = [command objectForKey:@"viewXPath"];
		if (viewXPath == nil)
		{
			fprintf(stderr, "### Command 'simulateTouch' requires 'viewXPath' parameter.\n");
			exit(1);
		}
		
		printf("=== simulateTouch\n    viewXPath:\n        %s\n",
			[viewXPath cStringUsingEncoding:NSUTF8StringEncoding]);

		NSArray *views = [self viewsForXPath:viewXPath];
		if([views count] != 1)
		{
			fprintf(
				stderr,
				"### 'viewXPath' for command 'simulateTouch' selected %ld nodes, where exactly 1 is required.\n",
				[views count]);
			exit(1);
		}
		
		UIView *view = [views objectAtIndex:0];
		NSLog(@"%@", [[views objectAtIndex:0] fullDescription]);
		[self performTouchInView:view];
		
		
	}

	//
	// scrollToRow
	//
	// Scrolls a UITableView selected by an XPath query to the specified
	// rowIndex (and optionally sectionIndex).
	//
	// Required parameters:
	//	viewXPath (search for a table view matching this XPath)
	//	rowIndex (scroll the table view to this row)
	//
	// Optional parameter:
	//	sectionIndex (scroll the table view to the rowIndex in this section)
	//
	else if ([commandName isEqualToString:@"scrollToRow"])
	{
		NSString *viewXPath = [command objectForKey:@"viewXPath"];
		NSAssert(viewXPath != nil, @"Command 'scrollToRow' requires 'viewXPath' parameter");
		NSNumber *rowIndex = [command objectForKey:@"rowIndex"];
		NSAssert(viewXPath != nil, @"Command 'scrollToRow' requires 'rowIndex' parameter");
		NSNumber *sectionIndex = [command objectForKey:@"sectionIndex"];
		// sectionIndex is allowed to be nil -- will select section 0
		
		NSIndexPath *indexPath =
			[NSIndexPath
				indexPathForRow:[rowIndex integerValue]
				inSection:[sectionIndex integerValue]];
		
		printf("=== scrollToRow\n    viewXPath:\n        %s\n    indexPath: (section: %ld, row: %ld)\n",
			[viewXPath cStringUsingEncoding:NSUTF8StringEncoding],
			[indexPath section],
			[indexPath row]);

		NSArray *views = [self viewsForXPath:viewXPath];
		if([views count] != 1)
		{
			fprintf(
				stderr,
				"### 'viewXPath' for command 'scrollToRow' selected %ld nodes, where exactly 1 is required.\n",
				[views count]);
			exit(1);
		}
		if(![[views objectAtIndex:0] isKindOfClass:[UITableView class]])
		{
			fprintf(
				stderr,
				"### 'viewXPath' for command 'scrollToRow' selected a node but it wasn't a UITableView as required.\n",
				[views count]);
			exit(1);
		}
		
		UITableView *view = [views objectAtIndex:0];
		
		NSLog(@"%@", [[views objectAtIndex:0] fullDescription]);
		[view
			scrollToRowAtIndexPath:indexPath
			atScrollPosition:UITableViewScrollPositionNone
			animated:NO];
	}
	
	//
	// Remove each command after execution
	//
	[scriptCommands removeObjectAtIndex:0];

	//
	// Exit the program when complete
	//
	if ([scriptCommands count] == 0)
	{
		[self release];
		exit(0);
	}
	else
	{
		//
		// If further commands remain, queue the next one
		//
		[self
			performSelector:@selector(runCommand)
			withObject:nil
			afterDelay:SCRIPT_RUNNER_INTER_COMMAND_DELAY];
	}
}

@end

#endif

