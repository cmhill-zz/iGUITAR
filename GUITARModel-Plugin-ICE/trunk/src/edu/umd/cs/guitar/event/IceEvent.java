package edu.umd.cs.guitar.event;

import java.util.List;
import java.util.Hashtable;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.IceComponent;

public class IceEvent implements GEvent {
    private guitar.event.ActionPrx event;

    public IceEvent(guitar.event.ActionPrx event)
    {
        this.event = event;
    }

    public void perform(GComponent gComponent, Object parameters,
                        Hashtable<String, List<String>> optionalData) {
        perform(gComponent, null, optionalData);
    }

    public void perform(GComponent gComponent,
                        Hashtable<String, List<String>> optionalData) {
        IceComponent target = (IceComponent) gComponent;
        event.perform(target.getProxy());
    }

    public boolean isSupportedBy(GComponent gComponent) {
        IceComponent target = (IceComponent) gComponent;
        return event.isSupportedBy(target.getProxy());
    }

    public String getEventType() {
        return event.getEventType();
    }
}
