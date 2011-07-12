
import os
import Ice
import guitar.ripper

import pyguitar
import pyguitar.model as pymodel

class RipperMonitor(guitar.ripper.RipperMonitor):
    def __init__(self, path, args, delay):
        pyguitar.adapter().add(self, Ice.Application.communicator().\
                                   stringToIdentity('RipperMonitor'))
        self._app = pymodel.Application(path, delay)
        self._args = args

    def getRootWindows(self, _ctx = None):
        return self._app.getAllWindow().values()

    def setUp(self, _ctx = None):
        # TODO: Set up window handler

        # Connect to the application
        self._app.connect(self._args)

    def cleanUp(self, _ctx = None):
        # Shut down the application
        self._app.disconnect()

    def getOpenedWindowCache(self, _ctx = None):
        pass

    def resetWindowCache(self, _ctx = None):
        pass

    def closeWindow(self, window, _ctx = None):
        pass

    def isExpandable(self, component, window, _ctx = None):
        return len(component.getEventList()) > 0

    def getClosedWindowCache(self, _ctx = None):
        pass

    def expandGUI(self, component, _ctx = None):
        actions = component.getEventList()

        assert len(actions) > 0, 'no actions to perform when expanding the gui'
        actions[0].perform(component)

def main(argv, configFile = 'ripper.cfg'):
    assert pyguitar.check_accessibility(), 'Accessibility is not enabled'

    if not os.path.exists(configFile):
        print "warning: could not locate config file '%s'" % configFile
        configFile = ''

    class server(Ice.Application):
        def run(self, argv):
            if len(argv) < 2:
                print 'Must specify an application to run'
                return 1

            monitor = pyguitar.ripper.RipperMonitor(argv[1], argv[2:], 5000)
            self.communicator().waitForShutdown()
    prgm = server()
    return prgm.main(argv, configFile = configFile)
