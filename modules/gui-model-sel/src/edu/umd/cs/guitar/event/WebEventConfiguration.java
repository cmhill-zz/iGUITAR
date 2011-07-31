package edu.umd.cs.guitar.event;

/**
 * {@link GEventConfiguration} for the web ripper.
 * 
 * @author Scott McMaster (scott.d.mcmaster@gmail.com)
 */
public class WebEventConfiguration implements GEventConfiguration {
	private boolean noNavigateHrefs;

	public boolean isNoNavigateHrefs() {
		return noNavigateHrefs;
	}

	public void setNoNavigateHrefs(boolean noNavigateHrefs) {
		this.noNavigateHrefs = noNavigateHrefs;
	}
}
