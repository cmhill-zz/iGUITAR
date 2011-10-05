package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.GIDGenerator;


import edu.umd.cs.guitar.model.WebDefaultIDGenerator;


public class WebRipperMain extends RipperMain {
    public WebRipperMain(NewGRipperConfiguration config) {
        super(config);
    }

    protected GRipperMonitor createMonitor() {
        return new WebRipperMonitor(config);
    }

    protected GIDGenerator getIdGenerator() {
        return WebDefaultIDGenerator.getInstance();
    }
}
