
#ifndef MODEL_ICE
#define MODEL_ICE

module guitar {
    module model {
        interface Component;
    };

    module event {
        interface Action {
            void perform(model::Component* gComponent);
            bool isSupportedBy(model::Component* gComponent);

            // Internal method for use by implementations
            idempotent string getEventType();
        };
    };

    module model {
        module data {
            sequence<string> Values;

            interface PropertyType {
                idempotent string getName();
                idempotent Values getValue();
            };
        };

        sequence<data::PropertyType*> PropertyList;

        interface BaseObject {
            PropertyList getGUIProperties();
        };

        sequence<event::Action*> EventList;
        sequence<Component*> ComponentList;

        interface Component extends BaseObject {
            idempotent EventList getEventList();
            ComponentList getChildren();
            Component* getParent();
            idempotent string getTitle();
            idempotent int getX();
            idempotent int getY();
            idempotent bool isEnable();

            // Internal method for use by implementations
            idempotent int getId();
        };

        interface Window extends BaseObject {
            idempotent bool isValid();
            idempotent Component* getContainer();
            idempotent bool isModal();
        };

        sequence<string> Arguments;
        dictionary<int, Window*> WindowMap;

        interface Process {
            /* destruction function for processes */
            void disconnect();

            /* internal function for retrieving the process id */
            int pid();

            WindowMap getAllWindow();
        };

        interface Application {
            /* factory creation method for processes */
            Process* connect(Arguments args);
        };

        sequence<string> PropertyNames;

        interface Constants {
            PropertyNames getIdProperties();
        };
    };
};

#endif // MODEL_ICE
