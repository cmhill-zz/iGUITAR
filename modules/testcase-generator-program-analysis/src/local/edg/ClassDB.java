/*	
 *  Copyright (c) 2011. The GREYBOX group at the University of Freiburg, Chair of Software Engineering.
 *  Names of owners of this group may be obtained by sending an e-mail to arlt@informatik.uni-freiburg.de
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package local.edg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassReader;

public class ClassDB {
	
	/**
	 * Generates a data base with class informations about a given application
	 * 
	 * @param appClasses
	 *            The classes of the application as directories to class-files or as path to jar-files.
	 *            
	 * @return Data base with class information.
	 */
	public static Map<String, Class> create(String[] appClasses) {
		ClassDbVisitor cv = new ClassDbVisitor();
		for (String s : appClasses) {
			File f = new File(s);
			if (f.isDirectory()) {
				Collection<File> files = FileUtils.listFiles(f,
						new String[] { "class" }, true);
				for (File cf : files) {
					try {
						ClassReader cr = new ClassReader(
								new FileInputStream(cf));
						cr.accept(cv, 0);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (f.isFile() && s.toLowerCase().endsWith(".jar")) {
				try {
					ZipFile zf = new ZipFile(f);
					Enumeration<? extends ZipEntry> en = zf.entries();
					while (en.hasMoreElements()) {
						ZipEntry e = en.nextElement();
						String name = e.getName();
						if (name.endsWith(".class")) {
							new ClassReader(zf.getInputStream(e)).accept(cv, 0);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return cv.getClassDb();
	}
	
}
