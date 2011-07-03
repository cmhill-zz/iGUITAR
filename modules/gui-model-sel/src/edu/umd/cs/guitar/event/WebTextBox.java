package edu.umd.cs.guitar.event;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebElement;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.WebComponent;
import edu.umd.cs.guitar.model.WebConstants;

public class WebTextBox implements GEvent {

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		if(gComponent instanceof WebComponent) {
			if( ((WebComponent) gComponent).getElement() instanceof RenderedWebElement ) {
				if(((RenderedWebElement) ((WebComponent) gComponent).getElement()).isDisplayed()) {
					if(((WebComponent) gComponent).getElement().getTagName().equals("input") && 
							(((WebComponent) gComponent).getElement().getAttribute("type").equals("textbox") || ((WebComponent) gComponent).getElement().getAttribute("type").equals("textbox")) )
						return true; 
				}
			}
		}
		return false;
	}

	@Override
	public void perform(GComponent gComponent, Object parameters,
			Hashtable<String, List<String>> optionalData) {
		perform(gComponent, optionalData);
	}

	@Override
	public void perform(GComponent gComponent,
			Hashtable<String, List<String>> optionalData) {

		if(gComponent instanceof WebComponent) { 
			WebElement el = ((WebComponent) gComponent).getElement();
			
			if(!(el instanceof RenderedWebElement)) {
				return;
			} else {
				if(!((RenderedWebElement) el).isDisplayed())
					return;
			}
			
			el.sendKeys(WebConstants.KEYS_TO_SEND);
		}
	}

}
