
import sys, os, subprocess

class environment:
    def __init__(self):
        self.root = os.path.abspath(
            os.path.join(os.path.dirname(__file__), '..', '..', '..'))
        self.dest = os.path.join(self.root, 'python')
        self.slice_dir = os.path.join(self.root, 'GUITARModel-Plugin-ICE', 'slice')

def generate():
    # construct the environment
    env = environment()

    generate_init(env)
    generate_model(env)
    generate_event(env)
    slice2py(env, os.path.join(env.slice_dir, 'model.ice'))

def generate_init(env):
    try:
        os.makedirs(os.path.join(env.dest, 'pyguitar'))
    except OSError:
        pass
    path = os.path.join(env.dest, 'pyguitar', '__init__.py')
    with open(path, 'w') as f:
        f.write("""
import sys
sys.path.append('%s/GUITARModel-Plugin-ICE/impl/gtk/src')

from pyguitar_module import *
""" % env.root)

def generate_model(env):
    try:
        os.makedirs(os.path.join(env.dest, 'pyguitar'))
    except OSError:
        pass
    path = os.path.join(env.dest, 'pyguitar', 'model.py')
    with open(path, 'w') as f:
        f.write("""
import sys
sys.path.append('%s/GUITARModel-Plugin-ICE/impl/gtk/src')

from pyguitar_model import *
""" % env.root)

def generate_event(env):
    try:
        os.makedirs(os.path.join(env.dest, 'pyguitar'))
    except OSError:
        pass
    path = os.path.join(env.dest, 'pyguitar', 'event.py')
    with open(path, 'w') as f:
        f.write("""
import sys
sys.path.append('%s/GUITARModel-Plugin-ICE/impl/gtk/src')

from pyguitar_event import *
""" % env.root)

def slice2py(env, path):
    cmd = ['slice2py', path, '--output-dir', env.dest]
    retcode = subprocess.call(cmd)
    if retcode != 0:
        print 'error code %d: %s' % (retcode, ''.join(cmd))

if __name__ == '__main__':
    generate()
