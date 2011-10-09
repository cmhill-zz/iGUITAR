package edu.umd.cs.guitar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.ObjectFactory;

public class IphIDGenerator extends DefaultIDGenerator {

	static IphIDGenerator instance = null;
	static ObjectFactory factory = new ObjectFactory();
	static final int prime = 31;
	
	public static IphIDGenerator getInstance() {
		if (instance == null)
			instance = new IphIDGenerator();
		return instance;
		
	}
	/*@Override
	public void generateID(GUIStructure gs) {
		// TODO Auto-generated method stub
		
	}*/

	private IphIDGenerator() {
		super(ID_PROPERTIES);
	}

	static List<String> ID_PROPERTIES = new ArrayList<String>(
			Arrays.asList("className", "address"));

}
