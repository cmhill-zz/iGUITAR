
#include "model.ice"

module guitar {
    module ripper {
        sequence<model::Window*> WindowList;

        interface RipperMonitor {
            WindowList getRootWindows();
            void setUp();
            void cleanUp();
            WindowList getOpenedWindowCache();
            void resetWindowCache();
            void closeWindow(model::Window* window);
            bool isExpandable(model::Component* component,
                              model::Window* window);
            WindowList getClosedWindowCache();
            void expandGUI(model::Component* component);
        };
    };
};
