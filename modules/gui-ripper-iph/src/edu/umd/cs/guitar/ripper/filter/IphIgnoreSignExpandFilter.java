package edu.umd.cs.guitar.ripper.filter;

import java.util.List;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;

/**
 * 
 * Ignore component while ripping by a subset of its attributes
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class IphIgnoreSignExpandFilter extends GComponentFilter{

    List<FullComponentType> lIgnoreFullComponent;
    ComponentListType sIgnoreWidgetSignList;

    /**
     * @param sIgnoreWidgetSignList
     */
    public IphIgnoreSignExpandFilter(List<FullComponentType> lIgnoredComps) {
        super();
        this.lIgnoreFullComponent = lIgnoredComps;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.ripper.ComponentFilter#isProcess(edu.umd.cs.guitar.
     * model.GXComponent)
     */
    //@Override
    public boolean isProcess(GComponent gComponent, GWindow gWindow) {

        // GUITARLog.log.debug("Start: " + this.getClass().getName());

        ComponentType dComponent = gComponent.extractProperties();
        ComponentType dWindow = gWindow.extractWindow().getWindow();

        AttributesTypeWrapper compAttributesAdapter = new AttributesTypeWrapper(
                dComponent.getAttributes());
        AttributesTypeWrapper winAttributesAdapter = new AttributesTypeWrapper(
                dWindow.getAttributes());

        ComponentType signComp;
        ComponentType signWin;

        for (FullComponentType sign : lIgnoreFullComponent) {
            signComp = sign.getComponent();
            signWin = sign.getWindow();

            AttributesTypeWrapper dCompSignAttributes = new AttributesTypeWrapper(
                    signComp.getAttributes());

            if (signWin != null) {
                AttributesTypeWrapper signWinAttributes = new AttributesTypeWrapper(
                        signWin.getAttributes());

                if (!winAttributesAdapter.containsAll(signWinAttributes)) {
                    continue;
                }
            }

            if (compAttributesAdapter.containsAll(dCompSignAttributes))
                return true;
        }

        // GUITARLog.log.debug("End: " + this.getClass().getName());
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.ripper.ComponentMonitor#ripComponent(edu.umd.cs.guitar
     * .model.GXComponent)
     */
    //@Override
    public ComponentType ripComponent(GComponent component, GWindow window) {
        return component.extractProperties();
    }
}
