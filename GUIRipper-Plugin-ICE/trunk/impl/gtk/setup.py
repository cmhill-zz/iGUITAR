
import sys, os, subprocess

class environment:
    def __init__(self):
        self.root = os.path.abspath(
            os.path.join(os.path.dirname(__file__), '..', '..', '..'))
        self.dest = os.path.join(self.root, 'python')
        self.slice_dir = os.path.join(self.root, 'GUIRipper-Plugin-ICE', 'slice')

def generate():
    env = environment()

    setup_parent(env)
    generate_ripper(env)
    slice2py(env, os.path.join(env.slice_dir, 'ripper.ice'))
    write_config(env)
    write_main(env)

def setup_parent(env):
    cmd = [sys.executable, os.path.join(env.root, 'GUITARModel-Plugin-ICE', 'impl', 'gtk', 'setup.py')]
    retcode = subprocess.call(cmd)

    if retcode != 0:
        print 'error code %d: %s' % (retcode, ''.join(cmd))

def generate_ripper(env):
    try:
        os.makedirs(os.path.join(env.dest, 'pyguitar'))
    except OSError:
        pass
    path = os.path.join(env.dest, 'pyguitar', 'ripper.py')
    with open(path, 'w') as f:
        f.write("""
import sys
sys.path.append('%s/GUIRipper-Plugin-ICE/impl/gtk/src')

from pyguitar_ripper import *
""" % env.root)

def slice2py(env, path):
    cmd = ['slice2py', path,
           '-I%s' % os.path.join(env.root, 'GUITARModel-Plugin-ICE', 'slice'),
           '--output-dir', env.dest]
    retcode = subprocess.call(cmd)
    if retcode != 0:
        print 'error code %d: %s' % (retcode, ''.join(cmd))

def write_config(env):
    path = os.path.join(env.dest, 'ripper.cfg')
    with open(path, 'w') as f:
        f.write("""
Ice.ThreadPool.Server.Size=4
Ice.Trace.Network=2
""")

def write_main(env):
    path = os.path.join(env.dest, 'ripper.py')
    with open(path, 'w') as f:
        f.write("""
import os, sys
import pyguitar.ripper as ripper

if __name__ == '__main__':
    config = os.path.join(os.path.dirname(__file__), 'ripper.cfg')
    ripper.main(sys.argv, configFile = config)
""")

if __name__ == '__main__':
    generate()
