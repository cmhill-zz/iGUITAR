package edu.umd.cs.guitar.event;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.IphCommServer;
import edu.umd.cs.guitar.model.data.PropertyType;

public class IphTouchEventThread extends GThreadEvent{
	public IphTouchEventThread(){
		
	}
	public String eventname = "Touch";

	@Override
	public void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		perform(gComponent, optionalData);
	}

	@Override
	public void performImpl(GComponent gComponent,Hashtable<String, List<String>> optionalData) {
		System.out.println("Send TOUCH message to iphone client.");
		if (!(gComponent instanceof IphComponent)) {
			System.out.println("Error: expected IphComponent but received: " + gComponent.getClass());
			return;
		}
		
		IphComponent iphComponent = (IphComponent) gComponent;
		String address = iphComponent.getAddress();
		String classname = iphComponent.getClassVal();
		//String eventrequest = "expand " + this.eventname + " " + address + " " + classname;
		String eventrequest = "INVOKE TOUCH " + classname + " " + address;
		
		//IphCommServer.request(eventrequest);
		// Get address and UIView name.
		IphCommServer.requestAndHear(eventrequest);
	}

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		/*
		List<GEvent> eventlist = ((IphComponent)gComponent).getEventList();
		for (GEvent event : eventlist){
			if (event instanceof IphTouchEvent){
				return true;
			}
		}
		return false;
		*/
		if (!(gComponent instanceof IphComponent)) {
			return false;
		}
		
		IphComponent comp = (IphComponent) gComponent;
		
		// Go through the properties and find if one contains
		// INVOKE, TOUCH.
		for (PropertyType property : comp.getGUIProperties()) {
			if (property.getName().equals("INVOKE") &&
					property.getValue().get(0).equals("TOUCH")) {
				System.out.println("IphComponent : " + comp.getClassVal()
						+ ", supports TOUCH");
				return true;
			}
		}
		return false;
	}
}
