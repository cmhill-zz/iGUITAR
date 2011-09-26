package edu.umd.cs.guitar.event;

import java.util.Hashtable;
import java.util.List;

import edu.umd.cs.guitar.model.GComponent;

public class IphThreadEvent extends GThreadEvent{

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void performImpl(GComponent gComponent,
			Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void performImpl(GComponent gComponent, Object parameters,
			Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub
		
	}

}
