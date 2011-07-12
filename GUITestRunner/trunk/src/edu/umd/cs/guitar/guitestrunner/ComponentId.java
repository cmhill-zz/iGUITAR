package edu.umd.cs.guitar.guitestrunner;

public class ComponentId {

	private String name;
	
	private int number;
	
	private String type;
	
	public ComponentId(String rawName)
	{
		int lastDelim = rawName.lastIndexOf('_');
		String nameAndOther = rawName.substring(0, lastDelim );
		String numberStr = rawName.substring(lastDelim + 1);
		number = Integer.parseInt(numberStr);
		
		int otherDelim = nameAndOther.lastIndexOf('_');
		name = nameAndOther.substring(0, otherDelim);
		type = nameAndOther.substring(otherDelim + 1);
	}

	@Override
	public String toString() {
		return name + "_" + type + "_" + number;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIndex(int number) {
		this.number = number;
	}

	public int getIndex() {
		return number;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
