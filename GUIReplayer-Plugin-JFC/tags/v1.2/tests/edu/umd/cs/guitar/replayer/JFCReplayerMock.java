package edu.umd.cs.guitar.replayer;

import org.kohsuke.args4j.CmdLineException;

public class JFCReplayerMock extends JFCReplayer{
	
    /**
     * @param configuration (do nothing with it)
     */
    public JFCReplayerMock(JFCReplayerConfiguration configuration) {
    	super(configuration);
    }
    public void execute() throws CmdLineException {
    	super.execute();
    }

    /**
     * 
     */
    private void printInfo() {
    }

    /**
     * 
     * Check for command-line arguments
     * 
     * @throws CmdLineException
     * 
     */
    private void checkArgs() throws CmdLineException {
    	//do nothing
    }
    
    /**
     * 
     */
    private void setupEnv() {
    	//do nothing
    }
    
}
