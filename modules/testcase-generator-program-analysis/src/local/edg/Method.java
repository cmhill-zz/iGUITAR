package local.edg;

import java.util.HashMap;
import java.util.Map;

public class Method {

	private String name;
	private Class owner;
	private Map<String, MethodDescriptor> descs = new HashMap<String, MethodDescriptor>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Class getOwner() {
		return owner;
	}

	public void setOwner(Class owner) {
		this.owner = owner;
	}
	
	public MethodDescriptor getDescriptor(String id){
		return descs.get(id);
	}
	
	public void addDescriptor(MethodDescriptor md){
		descs.put(md.getDesc(), md);
		md.setMethod(this);
	}
	
	@Override
	public String toString(){
		return owner +"#" + name;
	}
	
}
