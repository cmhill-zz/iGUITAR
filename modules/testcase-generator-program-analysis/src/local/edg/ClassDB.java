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
