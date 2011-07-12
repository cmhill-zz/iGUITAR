
import subprocess, time

import Ice
import pyatspi

import guitar.model
import pyguitar
import pyguitar.event as pyevent

def initialize():
    Constants.initialize()

class Application(guitar.model.Application):
    def __init__(self, path, delay):
        self._path = path
        self._delay = delay / 1000.0
        self._process = None

        pyguitar.adapter().add(self, Ice.Application.communicator().\
                                   stringToIdentity('Application'))

    def connect(self, args, _ctx = None):
        self._process = subprocess.Popen([self._path] + args)
        time.sleep(self._delay)

        # Get the handle to the application
        reg = pyatspi.Registry
        desktop = reg.getDesktop(0)
        
        def find_application(desktop, function):
            for app in desktop:
                if function(app):
                    yield app

        try:
            self._app = find_application(desktop,
                                         lambda x: x.name == 'gedit').next()
        except StopIteration:
            # Could not connect to the application
            # TODO: Figure out how to raise this error on the client side
            print 'found no application named gedit'

    def disconnect(self, _ctx = None):
        self._process.terminate()

    def getAllWindow(self, _ctx = None):
        """
        Iterates through all top level windows and creates window proxies
        for each one of them.
        """
        def isWindow(w):
            try:
                print 'querying *%s* to check if it\'s a window' % w.name
                return w.queryComponent().getLayer() == pyatspi.LAYER_WINDOW
            except NotImplementedError:
                # Does not implement the component interface
                return False
        windows = [w for w in self._app if isWindow(w)]

        windowMap = {}
        for i, k in enumerate(windows):
            component = Component(k)
            windowMap[i] = Window(component).proxy
        return windowMap

class Constants(guitar.model.Constants):
    instance = None

    def __init__(self):
        pyguitar.adapter().add(self, Ice.Application.communicator().\
                                   stringToIdentity('Constants'))

    @staticmethod
    def initialize():
        if Constants.instance is None:
            Constants.instance = Constants()
        return Constants.instance

    def getIdProperties(self, _ctx = None):
        return []

class BaseObject(guitar.model.BaseObject):
    def getGUIProperties(self, _ctx = None):
        return []

class Window(BaseObject, guitar.model.Window):
    def __init__(self, container):
        self._container = container
        self._proxy = pyguitar.adapter().addWithUUID(self)

    @property
    def proxy(self):
        return guitar.model.WindowPrx.uncheckedCast(self._proxy)

    def isValid(self, _ctx = None):
        return False

    def getContainer(self, _ctx = None):
        return self._container.proxy

    def isModal(self, _ctx = None):
        return False

class Component(BaseObject, guitar.model.Component):
    def __init__(self, atspi_obj):
        # Query for the component interface
        self._id, self._atspi_obj = pyguitar.register(atspi_obj)
        # This query should never fail
        self._component = self._atspi_obj.queryComponent()

        # Save object properties
        self._title = self._atspi_obj.name
        self._x, self._y = self._component.getPosition(1)

        self._proxy = pyguitar.adapter().addWithUUID(self)

    @property
    def proxy(self):
        return guitar.model.ComponentPrx.uncheckedCast(self._proxy)

    def getEventList(self, _ctx = None):
        try:
            actionInterface = self._atspi_obj.queryAction()
            eventList = []
            for action in [actionInterface.getName(x)
                           for x in xrange(actionInterface.nActions)]:
                event = pyevent.translate(action)
                if event is not None:
                    eventList.append(event.proxy)
            return eventList
        except NotImplementedError:
            # Contains no actions
            return []

    def getChildren(self, _ctx = None):
        def isComponent(c):
            try:
                print 'querying *%s* to check if it\'s a component' % c.name
                c.queryComponent()
                return True
            except NotImplementedError:
                # Does not implement the component interface
                return False

        children = [Component(c).proxy
                    for c in self._atspi_obj if isComponent(c)]
        return children

    def getParent(self, _ctx = None):
        return guitar.model.ComponentPrx()

    def getTitle(self, _ctx = None):
        return self._atspi_obj.name

    def getX(self, _ctx = None):
        return self._x

    def getY(self, _ctx = None):
        return self._y

    def isEnable(self, _ctx = None):
        # Access the accessible interface
        try:
            accessible = self._atspi_obj.queryAccessible()
            # Check if 'enabled' is in the accessible state
            return accessible.getState().contains(pyatspi.STATE_ENABLED)
        except NotImplementedException:
            # Does not implement the accessible interface,
            # so assume it isn't enabled
            print 'cannot retrieve enabled state from *%s* because it does'\
                ' not implement the accessible interface'
            return False

    def getId(self, _ctx = None):
        return self._id
