package edu.umd.cs.guitar.testcase.plugin;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;
import junit.framework.Test;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.plugin.MaxEventTestCase;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;


/** Test class to test the MaxEventTestCaseConfiguration */
public class MaxEventTestCaseConfigurationTest extends junit.framework.TestCase {

	public void testConfigurationFile() {
		MaxEventTestCaseConfiguration config = new MaxEventTestCaseConfiguration();
		
		Assert.assertTrue(config != null);
	}

}