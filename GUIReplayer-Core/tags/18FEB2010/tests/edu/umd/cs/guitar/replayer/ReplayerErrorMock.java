package edu.umd.cs.guitar.replayer;

import edu.umd.cs.guitar.exception.ComponentNotFound;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.replayer.Replayer;
/**
 * This is the mock object used to throw a ComponentNotFound exception.
 */
public class ReplayerErrorMock extends Replayer {
	/**
     * @throws ComponentNotFound
     */
	public ReplayerErrorMock(TestCase tc, String sGUIFile, String sEFGFile)
			throws ParserConfigurationException, SAXException, IOException, ComponentNotFound {
		super(tc, sGUIFile, sEFGFile);
		// TODO Auto-generated constructor stub
		throw new ComponentNotFound();
	}

	
}
