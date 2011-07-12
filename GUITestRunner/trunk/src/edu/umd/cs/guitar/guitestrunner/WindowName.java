package edu.umd.cs.guitar.guitestrunner;

public class WindowName {

	private String name;
	
	private String number;
	
	public WindowName(String rawName)
	{
		int lastDelim = rawName.lastIndexOf('_');
		name = rawName.substring(0, lastDelim );
		number = rawName.substring(lastDelim + 1);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		return name + '_' + number;
	}
}
