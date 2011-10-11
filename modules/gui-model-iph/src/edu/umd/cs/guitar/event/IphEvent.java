package edu.umd.cs.guitar.event;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.IphCommServer;

public class IphEvent implements GEvent{

	@Override
	public void perform(GComponent gComponent, List<String> parameters,
			Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		perform(gComponent, optionalData);
	}

	@Override
	public void perform(GComponent gComponent,
			Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		IphComponent iphComponent = (IphComponent) gComponent;
		//"expand INVOKE_TOUCH 78942000 UIRoundedRectButton"
		String address = iphComponent.getAddress();//iphComponent.getaddress();
		String classname = iphComponent.getClassVal();
		String eventname = new String();
		List<GEvent> eventlist = iphComponent.getEventList();
		for (GEvent event:eventlist){
			if (event instanceof IphTouchEvent){
				eventname += "INVOKE_TOUCH";
			}
			if(event instanceof IphSwipeEvent){
				eventname += "INVOKE_SWIPE";
			}
		}
		String eventrequest = new String();
		eventrequest = "expand" + eventname + " " + address + " " + classname;
		IphCommServer.request(eventrequest);
	}

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		// TODO Auto-generated method stub
		return false;
	}

}
