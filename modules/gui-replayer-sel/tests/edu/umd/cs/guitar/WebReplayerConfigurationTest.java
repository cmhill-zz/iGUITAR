package edu.umd.cs.guitar;

import edu.umd.cs.guitar.replayer.WebReplayerConfiguration;

import junit.framework.TestCase;
/**
 * Test WebReplayerConfiguration
 * 
 * @author brian
 *
 */
public class WebReplayerConfigurationTest extends TestCase {
	public void testWebReplayerConfiguration() {
        WebReplayerConfiguration configuration = new WebReplayerConfiguration();
		assertNotNull(configuration);
	}
}
