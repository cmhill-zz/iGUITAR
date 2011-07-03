package edu.umd.cs.guitar.event;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.WebElement;


import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.WebComponent;

public class WebToggleCheckbox implements GEvent {
	private static Set<String> supported;
	
	static {
		supported = new TreeSet<String>();
		supported.add("checkbox");
		supported.add("radio");
	}
	
	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		if(gComponent instanceof WebComponent) {
			WebElement e = ((WebComponent) gComponent).getElement();
			
			if(e.getTagName().equals("input") && e.isEnabled() && supported.contains(e.getAttribute("type"))) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void perform(GComponent gComponent, Object parameters,
			Hashtable<String, List<String>> optionalData) {
		if(gComponent instanceof WebComponent) {
			WebComponent we = (WebComponent) gComponent;
			we.getElement().toggle();
		}
	}

	@Override
	public void perform(GComponent gComponent,
			Hashtable<String, List<String>> optionalData) {
		perform(gComponent, null, optionalData);
	}

}
