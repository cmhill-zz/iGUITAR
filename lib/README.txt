This folder resides at
http://guitar.svn.sourceforge.net/viewvc/guitar/shared/

It is not a part of your project's SVN. Rather, we use svn:externals to
link all project trunks to this folder. An SVN CHECKOUT will
automatically get this folder.

Note that an SVN COMMIT at your root level will not update the folder.
If you need to update it, do an explicit SVN COMMIT for shared/

Please see
http://svnbook.red-bean.com/en/1.5/svn-book.html#svn.advanced.externals
for more details.

Use this folder to share library files.

