package edu.umd.cs.guitar.replayer;

import edu.umd.cs.guitar.model.GIDGenerator;
import edu.umd.cs.guitar.model.WebDefaultIDGenerator;
import edu.umd.cs.guitar.replayer.sel.NewGReplayerConfiguration;

public class WebReplayerMain extends ReplayerMain {

    public WebReplayerMain(NewGReplayerConfiguration config) {
        super(config);
    }

    protected GReplayerMonitor createMonitor() {
        return new WebReplayerMonitor(config);
    }

    protected GIDGenerator getIdGenerator() {
        return WebDefaultIDGenerator.getInstance();
    }
}
