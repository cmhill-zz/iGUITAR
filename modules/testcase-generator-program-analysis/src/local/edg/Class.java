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

import java.util.HashMap;
import java.util.Map;

public class Class {
	
	private String name;
	private String superName;
	private String[] interfaces;
	private Map<String, Method> methods = new HashMap<String, Method>();
	private Map<String, Field> fields = new HashMap<String, Field>();
	private boolean declared = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuperName() {
		return superName;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}

	public String[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(String[] interfaces) {
		this.interfaces = interfaces;
	}

	public Method getMethod(String name) {
		Method m = methods.get(name);
		return m;
	}

	public void addMethod(Method m) {
		methods.put(m.getName(), m);
		m.setOwner(this);
	}

	public Field getField(String name) {
		return fields.get(name);
	}

	public void addField(Field field) {
		this.fields.put(field.getName(), field);
		field.setOwner(this);
	}

	public boolean isDeclared() {
		return declared;
	}

	public void setDeclared(boolean declared) {
		this.declared = declared;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
