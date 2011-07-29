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
