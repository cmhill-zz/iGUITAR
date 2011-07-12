This repository hosts many many projects, not just a single project.

Each project has its own trunk/ tags/ and branches/

To build a project foo, checkout foo/trunk and run Ant on build.xml in the 
install folder. For example, to build the module GUITARModel/ do:

svn co https://guitar.svn.sourceforge.net/svnroot/guitar/GUITARModel/trunk GUITARModel
ant -v -f GUITARModel/install/build.xml dist

Several modules are combined to create a tool. Look in the .tool folders for details. 

Get more information at:
http://sourceforge.net/apps/mediawiki/guitar/index.php?title=Developers

