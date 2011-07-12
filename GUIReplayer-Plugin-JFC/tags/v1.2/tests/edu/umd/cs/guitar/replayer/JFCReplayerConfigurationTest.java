package edu.umd.cs.guitar.replayer;
import junit.framework.*;
import java.io.File;
//import edu.umd.cs.guitar.replayer.*;

public class JFCReplayerConfigurationTest extends TestCase {

	public void testJFCReplayerConfiguration() {
        JFCReplayerConfiguration configuration = new JFCReplayerConfiguration();
		assertNotNull(configuration);
	}
}
