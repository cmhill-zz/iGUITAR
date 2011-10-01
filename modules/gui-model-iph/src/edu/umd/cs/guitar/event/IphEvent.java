package edu.umd.cs.guitar.event;

import java.util.Hashtable;
import java.util.List;

import edu.umd.cs.guitar.model.GComponent;

public class IphEvent implements GEvent{

	@Override
	public void perform(GComponent gComponent, List<String> parameters,
			Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perform(GComponent gComponent,
			Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		// TODO Auto-generated method stub
		return false;
	}

}
