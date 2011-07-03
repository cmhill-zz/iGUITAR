package edu.umd.cs.guitar.replayer;
import edu.umd.cs.guitar.replayer.GReplayerConfiguration;
import junit.framework.*;

public class GReplayerConfigurationTest extends TestCase {
	/**
     * testcase for GReplayerConfiguration
     * assert configuration is not null
     */
	public void testGReplayerConfiguration() {
		GReplayerConfiguration config = new GReplayerConfiguration();
		assertNotNull(config);
	}
}