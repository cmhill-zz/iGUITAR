
import sys, gconf
import Ice

import pyguitar_model as model
import pyguitar_event as event

class _AdapterHolder(object):
    def __init__(self, name):
        self.this = Ice.Application.communicator().\
            createObjectAdapterWithEndpoints(name, 'default -p 10000')
        self.this.activate()

_adapter = None
def adapter():
    global _adapter
    if _adapter is None:
        # Delayed initialization of the adapter
        _adapter = _AdapterHolder('Guitar')
        model.initialize()
        event.initialize()
    return _adapter.this

####### REGISTRY FUNCTIONS ###########################################

_obj_id = 0
_registry = {}

def register(atspi_obj):
    global _obj_id, _registry
    _registry[_obj_id] = atspi_obj
    ret_val, _obj_id = _obj_id, _obj_id + 1
    return (ret_val, atspi_obj)

def retrieve(obj_id):
    assert _registry.has_key(obj_id),\
        'Object id %d does not exist in the registry' % obj_id
    return _registry[obj_id]

####### ACCESSIBILITY FUNCTIONS ####################################
def check_accessibility():
    cl = gconf.client_get_default()
    if not cl.get_bool('/desktop/gnome/interface/accessibility'):
        # Accessibility is off, prompt to turn it on
        continue_ = query_yes_no('Desktop accessibility is turned off.\n'
                                 'To continue, this program will enable '
                                 'desktop accessibility.\nDo you want to '
                                 'continue')
        if continue_:
            # Enable accessibility
            print 'Enabling accessibility interface'
            cl.set_bool('/desktop/gnome/interface/accessibility', True)
            print 'Note: Changes only take effect after logout.'

        # No matter what, the program needs to end since we can't continue
        return False
    return True

def query_yes_no(message):
    yes = set(['yes', 'y', 'ye', ''])
    no = set(['no', 'n'])
    
    choice = None
    while choice is None:
        choice = raw_input(message + ' [Y/n]? ').lower()
        if choice in yes:
            return True
        elif choice in no:
            return False
        else:
            choice = None
