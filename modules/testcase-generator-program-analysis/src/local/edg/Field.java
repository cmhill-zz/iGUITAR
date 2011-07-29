package local.edg;

import java.util.HashSet;
import java.util.Set;

public class Field {

	private Class owner;
	private String name;
	private String desc;
	private Set<MethodDescriptor> readBy = new HashSet<MethodDescriptor>();
	private Set<MethodDescriptor> writeBy = new HashSet<MethodDescriptor>();

	public Class getOwner() {
		return owner;
	}

	public void setOwner(Class owner) {
		this.owner = owner;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void readBy(MethodDescriptor m) {
		readBy.add(m);
	}

	public void writeBy(MethodDescriptor m) {
		writeBy.add(m);
	}

	public Set<MethodDescriptor> getAllReadBy() {
		return readBy;
	}
	
	@Override
	public String toString(){
		return owner +"#" + name;
	}
	
}
