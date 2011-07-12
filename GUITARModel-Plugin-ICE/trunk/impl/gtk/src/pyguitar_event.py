
import pyatspi
import Ice
import guitar.event

import pyguitar

action_table = None
def initialize():
    global action_table
    action_table = {
        'click' : ClickEvent()
        }

def translate(name):
    return action_table.get(name, None)

class ClickEvent(guitar.event.Action):
    def __init__(self):
        # Register this in the adapter
        self._proxy = pyguitar.adapter().add(
            self, Ice.Application.communicator().stringToIdentity(
                self.getEventType()))

    @property
    def proxy(self):
        return guitar.event.ActionPrx.uncheckedCast(self._proxy)

    def perform(self, component, _ctx = None):
        # Retrieve the at-spi object for this
        atspi_obj = pyguitar.retrieve(component.getId())

        # Retrieve the click event from the action interface
        interface = atspi_obj.queryAction()
        for x in xrange(interface.nActions):
            if interface.getName(x) == self.getEventType():
                # Perform the action
                interface.doAction(x)
                return
        assert False, 'could not perform action'

    def isSupportedBy(self, component, _ctx = None):
        atspi_obj = pyguitar.retrieve(component.getId())

        try:
            interface = atspi_obj.queryAction()
            for x in xrange(interface.nActions):
                if interface.getName(x) == self.getEventType():
                    return True
        except:
            # Does not support the action interface
            pass
        return False

    def getEventType(self, _ctx = None):
        return 'click'
