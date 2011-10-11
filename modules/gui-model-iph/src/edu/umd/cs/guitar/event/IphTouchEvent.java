package edu.umd.cs.guitar.event;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.IphCommServer;

public class IphTouchEvent extends GThreadEvent{
	public IphTouchEvent(){
		
	}
	public String eventname = "Touch";

	@Override
	public void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		perform(gComponent, optionalData);
	}

	@Override
	public void performImpl(GComponent gComponent,Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		IphComponent iphComponent = (IphComponent) gComponent;
		String address = iphComponent.getAddress();
		String classname = iphComponent.getClassVal();
		String eventrequest = new String();
		eventrequest = "expand " + this.eventname + " " + address + " " + classname;
		IphCommServer.request(eventrequest);
	}

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		// TODO Auto-generated method stub
		List<GEvent> eventlist = ((IphComponent)gComponent).getEventList();
		for (GEvent event : eventlist){
			if (event instanceof IphTouchEvent){
				return true;
			}
		}
		return false;
	}
}
