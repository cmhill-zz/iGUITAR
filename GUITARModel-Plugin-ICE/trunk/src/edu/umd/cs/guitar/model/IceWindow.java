package edu.umd.cs.guitar.model;

import java.util.List;

import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;

public class IceWindow extends GWindow {
    private guitar.model.WindowPrx window;

    public IceWindow(guitar.model.WindowPrx window)
    {
        this.window = window;
    }

    public guitar.model.WindowPrx getProxy()
    {
        return window;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        GWindow other = (GWindow) obj;
        return getTitle().equals(other.getTitle());
    }

    @Override
    public GUIType extractGUIProperties()
    {
        return null;
    }

    @Override
    public boolean isValid()
    {
        return window.isValid();
    }

    @Override
    public GComponent getContainer()
    {
        guitar.model.ComponentPrx component = window.getContainer();
        return new IceComponent(component, this);
    }

    @Override
    public boolean isModal()
    {
        return window.isModal();
    }

    @Override
    public String getTitle()
    {
        return getContainer().getTitle();
    }

    @Override
    public int getX()
    {
        return getContainer().getX();
    }

    @Override
    public int getY()
    {
        return getContainer().getY();
    }

    @Override
    public List<PropertyType> getGUIProperties()
    {
        return getContainer().getGUIProperties();
    }
}
