package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.util.GUITARLog;

import edu.umd.cs.guitar.testcase.plugin.TCPlugin;


/**
 * This class is the mock object of TCPlugin class. The constructor is private.
 * <br/> <br/>
 * @author Yuening Hu
 */	
public class TCPluginPrivateStub extends TCPlugin{
	
	/**
     * This variable defines the boolean value of isValidArgs. 
     */	
    boolean m_isValidArgs;

    /**
     * This is the constructor method of this class, initializing the variables. 
     * It is private, so it can not be accessed.
     */	
    private TCPluginPrivateStub(){
    	m_isValidArgs = true;
    }
	
	
    /**
     * This method is the implementation of the abstract method isValidArgs 
     * in TCPlugin. It returns a boolean value.
     */	
    @Override
    public boolean isValidArgs(){
    	return m_isValidArgs;
    }

    /**
     * This method is the implementation of the abstract method generate 
     * in TCPlugin.
     */	
    @Override
    public void generate(EFG efg, String outputDir, int nMaxNumber){
    	return;
    }

}
