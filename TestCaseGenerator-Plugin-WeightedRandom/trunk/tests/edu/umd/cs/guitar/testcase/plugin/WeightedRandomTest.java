package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.lang.reflect.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import junit.framework.Test;

import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.model.data.*;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.plugin.TCPlugin;
import edu.umd.cs.guitar.testcase.plugin.WeightedRandom;
import edu.umd.cs.guitar.testcase.plugin.WeightedRandomConfiguration;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;

/**
 * <p> This test class tests the WeightedRandom plugin.
 * 
 * @author Joseph Naegele, Thomas Egan
 *
 */
public class WeightedRandomTest extends junit.framework.TestCase {

	/**
	* EFG with no initial (one event).
	* <pre>
	*     +-----+
	*     |     |
	*     v     |
	*   (e1)----+
	* </pre>
	* 
	* Initial Events: None
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*  </tr>
	* </table>
	*/
	public EFG efg0;

	/**
	* Empty EFG.
	* <pre>
	* Empty
	* </pre>
	*
	* Initial Events: None
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp&nbsp&nbsp&nbsp</th>
	*  </tr>
	* </table>
	*/
	public EFG efg1;

	/**
	* Single self loop.
	* <pre>
	*     +-----+
	*     |     |
	*     v     |
	* ->(e1)----+
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*  </tr>
	* </table>
	*/
	public EFG efg2;

	/**
	* Triple loop.
	* <pre>
	*   (e4)<--(e3)
	*     |     ^
	*     v     |
	* ->(e1)----+
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <th style="background-color:#FFFFFF">e4</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e4</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efg3;

	/**
	* Double loop (both initial).
	* <pre>
	* ->(e2)<---+
	*     |     |
	*     v     |
	* ->(e1)----+
	* </pre>
	*
	* Initial Events: e1, e2
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e2</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e2</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efg4;

	/**
	* Two independant loops.
	* <pre>
	*     +-----+
	*     |     |
	*     v     |
	* ->(e1)----+
	* 
	*     +-----+
	*     |     |
	*     v     |
	* ->(e2)----+
	* </pre>
	*
	* Initial Events: e1, e2
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e2</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e2</th>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	* </table>
	*/
	public EFG efg5;

	/**
	* Single event with no loop.
	* <pre>
	* ->(e1)
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efg6;

	/**
	* Full 2 nodes.
	* <pre>
	*     +-----+
	*     |     |
	*     v     |
	* ->(e1)----+
	*    |^
	*    ||
	*    v|
	*   (e3)<---+
	*     |     |
	*     |     |
	*     +-----+
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e3</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	* </table>
	*/
	public EFG efg7;

	/**
	* Full 3 nodes.
	* <pre>
	*           +-----+
	*           |     |
	*           v     |
	*     ->(  e1  )--+
	*        |^  ^|
	*        ||  |+-----+
	*        ||  +-----+|
        *        v|        |v
	*  +--->(e4)----->(e3)<---+
	*  |     |^        ||     |
	*  |     |+--------+|     |
	*  +-----+          +-----+
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <th style="background-color:#FFFFFF">e4</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e4</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*  </tr>
	* </table>
	*/
	public EFG efg8;

	/**
	* 3 nodes no self loops
	* <pre>
	* ->(  e1  )
	*    |^  ^|
	*    ||  |+-----+
	*    ||  +-----+|
        *    v|        |v
	*   (e4)----->(e3)
	*     ^        |
	*     +--------+
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <th style="background-color:#FFFFFF">e4</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e4</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efg9;

	/**
	* Complex EFG.
	* <pre>
	*    +--------+
	*    |        |
	*    v        |
	* ->(e1)---->(e3)
	*    |^       |
	*    ||       |
	*    v|       |
	*   (e4)<-----+
	*
	*     +-----+
	*     |     |
	*     v     |
	* ->(e2)----+
	*     |
	*     |
	*     v
	*   (e5)
	*     |
	*     |
	*     v
	* ->(e6)
	* </pre>
	*
	* Initial Events: e1, e2, e6
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e2</th>
	*   <th style="background-color:#FFFFFF">e3</th>
	*   <th style="background-color:#DDDDDD">e4</th>
	*   <th style="background-color:#FFFFFF">e5</th>
	*   <th style="background-color:#DDDDDD">e6</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e2</th>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e3</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e4</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e5</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e6</th>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efgA;

	/**
	* Dead end with two nodes.
	* <pre>
	* ->(e1)---->(e3)
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e3</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efgB;

	/**
	* Complex EFG.
	* <pre>
	*           +----(e4)
	*           |     ^
	*           v     |
	*     ->(  e1  )--+
	*        |^  ^|
	*     --+||  |+-----+
	*       |||  +-----+|
        *       vv|        |v
	*  +-->( e2 )---->(e3)<---+
	*  |     |^        ||     |
	*  |     |+--------+|     |
	* (e5)<--+          +--->(e6)<--
	* </pre>
	*
	* Initial Events: e1, e2, e6
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e2</th>
	*   <th style="background-color:#FFFFFF">e3</th>
	*   <th style="background-color:#DDDDDD">e4</th>
	*   <th style="background-color:#FFFFFF">e5</th>
	*   <th style="background-color:#DDDDDD">e6</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e2</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e3</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e4</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e5</th>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e6</th>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efgC;

	/**
	* Full 3 nodes with strange values.
	* <pre>
	* ->(  e1  )
	*    |^  ^|
	*    ||  |+-----+
	*    ||  +-----+|
        *    v|        |v
	*   (e4)----->(e3)
	*     ^        |
	*     +--------+
	* </pre>
	*
	* Initial Events: e1
	* <p>
	*
	* Adjacency Matrix:
	* <table frame="box" cellspacing="0">
	*  <tr>
	*   <th style="background-color:#FFFFFF">&nbsp</th>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <th style="background-color:#FFFFFF">e4</th>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e1</th>
	*   <td style="background-color:#FFFFFF;text-align:center">1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#FFFFFF;text-align:center">3</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#DDDDDD">e3</th>
	*   <td style="background-color:#DDDDDD;text-align:center">1000</td>
	*   <td style="background-color:#DDDDDD;text-align:center">0</td>
	*   <td style="background-color:#DDDDDD;text-align:center">65536</td>
	*  </tr>
	*  <tr>
	*   <th style="background-color:#FFFFFF">e4</th>
	*   <td style="background-color:#FFFFFF;text-align:center">-1</td>
	*   <td style="background-color:#DDDDDD;text-align:center">-2</td>
	*   <td style="background-color:#FFFFFF;text-align:center">0</td>
	*  </tr>
	* </table>
	*/
	public EFG efgD;

	EventType e1;
	EventType e2;

	EventType e3;
	EventType e4;

	EventType e5;
	EventType e6;


	private static final String outputDir = "." + File.separator + "test";

	/**
	 * Setup the test by deleting the output directory.
	 */
	
	public void setup() {
		
		e1 = new EventType();
		e1.setEventId("e1");
		e1.initial = true;
		e2 = new EventType();
		e2.setEventId("e2");
		e2.initial = true;
		e3 = new EventType();
		e3.setEventId("e3");
		e3.initial = false;
		e4 = new EventType();
		e4.setEventId("e4");
		e4.initial = false;
		e5 = new EventType();
		e5.setEventId("e5");
		e5.initial = false;
		e6 = new EventType();
		e6.setEventId("e6");
		e6.initial = true;

		efg0 = new EFG();
		efg1 = new EFG();
		efg2 = new EFG();
		efg3 = new EFG();
		efg4 = new EFG();
		efg5 = new EFG();
		efg6 = new EFG();
		efg7 = new EFG();
		efg8 = new EFG();
		efg9 = new EFG();		
		efgA = new EFG();	
		efgB = new EFG();	
		efgC = new EFG();	
		efgD = new EFG();	

		efg0.events = new EventsType();
		efg0.eventGraph = new EventGraphType();
		efg0.events.event = new ArrayList<EventType>();
		efg0.eventGraph.row = new ArrayList<RowType>();
		efg0.events.event.add(e4);
		efg0.eventGraph.row.add(new RowType());
		efg0.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg0.eventGraph.row.get(0).e.add(1);

		efg1.events = new EventsType();
		efg1.eventGraph = new EventGraphType();
		efg1.events.event = new ArrayList<EventType>();
		efg1.eventGraph.row = new ArrayList<RowType>();

		efg2.events = new EventsType();
		efg2.eventGraph = new EventGraphType();
		efg2.events.event = new ArrayList<EventType>();
		efg2.eventGraph.row = new ArrayList<RowType>();
		efg2.events.event.add(e1);
		efg2.eventGraph.row.add(new RowType());
		efg2.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg2.eventGraph.row.get(0).e.add(1);

		efg3.events = new EventsType();
		efg3.eventGraph = new EventGraphType();
		efg3.events.event = new ArrayList<EventType>();
		efg3.eventGraph.row = new ArrayList<RowType>();
		efg3.events.event.add(e1);
		efg3.events.event.add(e3);
		efg3.events.event.add(e4);
		efg3.eventGraph.row.add(new RowType());
		efg3.eventGraph.row.add(new RowType());
		efg3.eventGraph.row.add(new RowType());
		efg3.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg3.eventGraph.row.get(1).e = new ArrayList<Integer>();
		efg3.eventGraph.row.get(2).e = new ArrayList<Integer>();

		efg3.eventGraph.row.get(0).e.add(0);
		efg3.eventGraph.row.get(0).e.add(1);
		efg3.eventGraph.row.get(0).e.add(0);

		efg3.eventGraph.row.get(1).e.add(0);
		efg3.eventGraph.row.get(1).e.add(0);
		efg3.eventGraph.row.get(1).e.add(1);

		efg3.eventGraph.row.get(2).e.add(1);
		efg3.eventGraph.row.get(2).e.add(0);
		efg3.eventGraph.row.get(2).e.add(0);

		efg4.events = new EventsType();
		efg4.eventGraph = new EventGraphType();
		efg4.events.event = new ArrayList<EventType>();
		efg4.eventGraph.row = new ArrayList<RowType>();
		efg4.events.event.add(e1);
		efg4.events.event.add(e2);
		efg4.eventGraph.row.add(new RowType());
		efg4.eventGraph.row.add(new RowType());
		efg4.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg4.eventGraph.row.get(1).e = new ArrayList<Integer>();

		efg4.eventGraph.row.get(0).e.add(0);
		efg4.eventGraph.row.get(0).e.add(1);

		efg4.eventGraph.row.get(1).e.add(1);
		efg4.eventGraph.row.get(1).e.add(0);

		efg5.events = new EventsType();
		efg5.eventGraph = new EventGraphType();
		efg5.events.event = new ArrayList<EventType>();
		efg5.eventGraph.row = new ArrayList<RowType>();
		efg5.events.event.add(e1);
		efg5.events.event.add(e2);
		efg5.eventGraph.row.add(new RowType());
		efg5.eventGraph.row.add(new RowType());
		efg5.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg5.eventGraph.row.get(1).e = new ArrayList<Integer>();

		efg5.eventGraph.row.get(0).e.add(1);
		efg5.eventGraph.row.get(0).e.add(0);

		efg5.eventGraph.row.get(1).e.add(0);
		efg5.eventGraph.row.get(1).e.add(1);

		efg6.events = new EventsType();
		efg6.eventGraph = new EventGraphType();
		efg6.events.event = new ArrayList<EventType>();
		efg6.eventGraph.row = new ArrayList<RowType>();
		efg6.events.event.add(e1);
		efg6.eventGraph.row.add(new RowType());
		efg6.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg6.eventGraph.row.get(0).e.add(0);

		efg7.events = new EventsType();
		efg7.eventGraph = new EventGraphType();
		efg7.events.event = new ArrayList<EventType>();
		efg7.eventGraph.row = new ArrayList<RowType>();
		efg7.events.event.add(e1);
		efg7.events.event.add(e3);
		efg7.eventGraph.row.add(new RowType());
		efg7.eventGraph.row.add(new RowType());
		efg7.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg7.eventGraph.row.get(1).e = new ArrayList<Integer>();

		efg7.eventGraph.row.get(0).e.add(1);
		efg7.eventGraph.row.get(0).e.add(1);

		efg7.eventGraph.row.get(1).e.add(1);
		efg7.eventGraph.row.get(1).e.add(1);

		efg8.events = new EventsType();
		efg8.eventGraph = new EventGraphType();
		efg8.events.event = new ArrayList<EventType>();
		efg8.eventGraph.row = new ArrayList<RowType>();
		efg8.events.event.add(e1);
		efg8.events.event.add(e3);
		efg8.events.event.add(e4);
		efg8.eventGraph.row.add(new RowType());
		efg8.eventGraph.row.add(new RowType());
		efg8.eventGraph.row.add(new RowType());
		efg8.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg8.eventGraph.row.get(1).e = new ArrayList<Integer>();
		efg8.eventGraph.row.get(2).e = new ArrayList<Integer>();

		efg8.eventGraph.row.get(0).e.add(1);
		efg8.eventGraph.row.get(0).e.add(1);
		efg8.eventGraph.row.get(0).e.add(1);

		efg8.eventGraph.row.get(1).e.add(1);
		efg8.eventGraph.row.get(1).e.add(1);
		efg8.eventGraph.row.get(1).e.add(1);

		efg8.eventGraph.row.get(2).e.add(1);
		efg8.eventGraph.row.get(2).e.add(1);
		efg8.eventGraph.row.get(2).e.add(1);

		efg9.events = new EventsType();
		efg9.eventGraph = new EventGraphType();
		efg9.events.event = new ArrayList<EventType>();
		efg9.eventGraph.row = new ArrayList<RowType>();
		efg9.events.event.add(e1);
		efg9.events.event.add(e3);
		efg9.events.event.add(e4);
		efg9.eventGraph.row.add(new RowType());
		efg9.eventGraph.row.add(new RowType());
		efg9.eventGraph.row.add(new RowType());
		efg9.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg9.eventGraph.row.get(1).e = new ArrayList<Integer>();
		efg9.eventGraph.row.get(2).e = new ArrayList<Integer>();

		efg9.eventGraph.row.get(0).e.add(0);
		efg9.eventGraph.row.get(0).e.add(1);
		efg9.eventGraph.row.get(0).e.add(1);

		efg9.eventGraph.row.get(1).e.add(1);
		efg9.eventGraph.row.get(1).e.add(0);
		efg9.eventGraph.row.get(1).e.add(1);

		efg9.eventGraph.row.get(2).e.add(1);
		efg9.eventGraph.row.get(2).e.add(1);
		efg9.eventGraph.row.get(2).e.add(0);

		efgA.events = new EventsType();
		efgA.eventGraph = new EventGraphType();
		efgA.events.event = new ArrayList<EventType>();
		efgA.eventGraph.row = new ArrayList<RowType>();
		efgA.events.event.add(e1);
		efgA.events.event.add(e2);
		efgA.events.event.add(e3);
		efgA.events.event.add(e4);
		efgA.events.event.add(e5);
		efgA.events.event.add(e6);
		efgA.eventGraph.row.add(new RowType());
		efgA.eventGraph.row.add(new RowType());
		efgA.eventGraph.row.add(new RowType());
		efgA.eventGraph.row.add(new RowType());
		efgA.eventGraph.row.add(new RowType());
		efgA.eventGraph.row.add(new RowType());
		efgA.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efgA.eventGraph.row.get(1).e = new ArrayList<Integer>();
		efgA.eventGraph.row.get(2).e = new ArrayList<Integer>();
		efgA.eventGraph.row.get(3).e = new ArrayList<Integer>();
		efgA.eventGraph.row.get(4).e = new ArrayList<Integer>();
		efgA.eventGraph.row.get(5).e = new ArrayList<Integer>();

		efgA.eventGraph.row.get(0).e.add(0);
		efgA.eventGraph.row.get(0).e.add(0);
		efgA.eventGraph.row.get(0).e.add(1);
		efgA.eventGraph.row.get(0).e.add(1);
		efgA.eventGraph.row.get(0).e.add(0);
		efgA.eventGraph.row.get(0).e.add(0);

		efgA.eventGraph.row.get(1).e.add(0);
		efgA.eventGraph.row.get(1).e.add(1);
		efgA.eventGraph.row.get(1).e.add(0);
		efgA.eventGraph.row.get(1).e.add(0);
		efgA.eventGraph.row.get(1).e.add(1);
		efgA.eventGraph.row.get(1).e.add(0);

		efgA.eventGraph.row.get(2).e.add(1);
		efgA.eventGraph.row.get(2).e.add(0);
		efgA.eventGraph.row.get(2).e.add(0);
		efgA.eventGraph.row.get(2).e.add(1);
		efgA.eventGraph.row.get(2).e.add(0);
		efgA.eventGraph.row.get(2).e.add(0);

		efgA.eventGraph.row.get(3).e.add(1);
		efgA.eventGraph.row.get(3).e.add(0);
		efgA.eventGraph.row.get(3).e.add(0);
		efgA.eventGraph.row.get(3).e.add(0);
		efgA.eventGraph.row.get(3).e.add(0);
		efgA.eventGraph.row.get(3).e.add(0);

		efgA.eventGraph.row.get(4).e.add(0);
		efgA.eventGraph.row.get(4).e.add(0);
		efgA.eventGraph.row.get(4).e.add(0);
		efgA.eventGraph.row.get(4).e.add(0);
		efgA.eventGraph.row.get(4).e.add(0);
		efgA.eventGraph.row.get(4).e.add(1);

		efgA.eventGraph.row.get(5).e.add(0);
		efgA.eventGraph.row.get(5).e.add(0);
		efgA.eventGraph.row.get(5).e.add(0);
		efgA.eventGraph.row.get(5).e.add(0);
		efgA.eventGraph.row.get(5).e.add(0);
		efgA.eventGraph.row.get(5).e.add(0);

		efgB.events = new EventsType();
		efgB.eventGraph = new EventGraphType();
		efgB.events.event = new ArrayList<EventType>();
		efgB.eventGraph.row = new ArrayList<RowType>();
		efgB.events.event.add(e1);
		efgB.events.event.add(e3);
		efgB.eventGraph.row.add(new RowType());
		efgB.eventGraph.row.add(new RowType());
		efgB.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efgB.eventGraph.row.get(1).e = new ArrayList<Integer>();

		efgB.eventGraph.row.get(0).e.add(0);
		efgB.eventGraph.row.get(0).e.add(1);

		efgB.eventGraph.row.get(1).e.add(0);
		efgB.eventGraph.row.get(1).e.add(0);

		efgC.events = new EventsType();
		efgC.eventGraph = new EventGraphType();
		efgC.events.event = new ArrayList<EventType>();
		efgC.eventGraph.row = new ArrayList<RowType>();
		efgC.events.event.add(e1);
		efgC.events.event.add(e2);
		efgC.events.event.add(e3);
		efgC.events.event.add(e4);
		efgC.events.event.add(e5);
		efgC.events.event.add(e6);
		efgC.eventGraph.row.add(new RowType());
		efgC.eventGraph.row.add(new RowType());
		efgC.eventGraph.row.add(new RowType());
		efgC.eventGraph.row.add(new RowType());
		efgC.eventGraph.row.add(new RowType());
		efgC.eventGraph.row.add(new RowType());
		efgC.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efgC.eventGraph.row.get(1).e = new ArrayList<Integer>();
		efgC.eventGraph.row.get(2).e = new ArrayList<Integer>();
		efgC.eventGraph.row.get(3).e = new ArrayList<Integer>();
		efgC.eventGraph.row.get(4).e = new ArrayList<Integer>();
		efgC.eventGraph.row.get(5).e = new ArrayList<Integer>();

		efgC.eventGraph.row.get(0).e.add(0);
		efgC.eventGraph.row.get(0).e.add(1);
		efgC.eventGraph.row.get(0).e.add(1);
		efgC.eventGraph.row.get(0).e.add(1);
		efgC.eventGraph.row.get(0).e.add(0);
		efgC.eventGraph.row.get(0).e.add(0);

		efgC.eventGraph.row.get(1).e.add(1);
		efgC.eventGraph.row.get(1).e.add(0);
		efgC.eventGraph.row.get(1).e.add(1);
		efgC.eventGraph.row.get(1).e.add(0);
		efgC.eventGraph.row.get(1).e.add(1);
		efgC.eventGraph.row.get(1).e.add(0);

		efgC.eventGraph.row.get(2).e.add(1);
		efgC.eventGraph.row.get(2).e.add(1);
		efgC.eventGraph.row.get(2).e.add(0);
		efgC.eventGraph.row.get(2).e.add(0);
		efgC.eventGraph.row.get(2).e.add(0);
		efgC.eventGraph.row.get(2).e.add(1);

		efgC.eventGraph.row.get(3).e.add(1);
		efgC.eventGraph.row.get(3).e.add(0);
		efgC.eventGraph.row.get(3).e.add(0);
		efgC.eventGraph.row.get(3).e.add(0);
		efgC.eventGraph.row.get(3).e.add(0);
		efgC.eventGraph.row.get(3).e.add(0);

		efgC.eventGraph.row.get(4).e.add(0);
		efgC.eventGraph.row.get(4).e.add(1);
		efgC.eventGraph.row.get(4).e.add(0);
		efgC.eventGraph.row.get(4).e.add(0);
		efgC.eventGraph.row.get(4).e.add(0);
		efgC.eventGraph.row.get(4).e.add(0);

		efgC.eventGraph.row.get(5).e.add(0);
		efgC.eventGraph.row.get(5).e.add(0);
		efgC.eventGraph.row.get(5).e.add(1);
		efgC.eventGraph.row.get(5).e.add(0);
		efgC.eventGraph.row.get(5).e.add(0);
		efgC.eventGraph.row.get(5).e.add(0);

		efgD.events = new EventsType();
		efgD.eventGraph = new EventGraphType();
		efgD.events.event = new ArrayList<EventType>();
		efgD.eventGraph.row = new ArrayList<RowType>();
		efgD.events.event.add(e1);
		efgD.events.event.add(e3);
		efgD.events.event.add(e4);
		efgD.eventGraph.row.add(new RowType());
		efgD.eventGraph.row.add(new RowType());
		efgD.eventGraph.row.add(new RowType());
		efgD.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efgD.eventGraph.row.get(1).e = new ArrayList<Integer>();
		efgD.eventGraph.row.get(2).e = new ArrayList<Integer>();

		efgD.eventGraph.row.get(0).e.add(0);
		efgD.eventGraph.row.get(0).e.add(2);
		efgD.eventGraph.row.get(0).e.add(3);

		efgD.eventGraph.row.get(1).e.add(1000);
		efgD.eventGraph.row.get(1).e.add(0);
		efgD.eventGraph.row.get(1).e.add(65536);

		efgD.eventGraph.row.get(2).e.add(-1);
		efgD.eventGraph.row.get(2).e.add(-2);
		efgD.eventGraph.row.get(2).e.add(0);

		
		try {
			File dir = new File(outputDir);

			File[] listFiles = dir.listFiles();

			for (File file : listFiles) {
				file.delete();
			}
		}catch(Exception e) {

		}
	}

	/**
	 * <p> Calling isValidArgs() without setting up a configuration should return false.
	 */
	public void testIsValidArgs1() {
		WeightedRandom coverage = new WeightedRandom();

		Assert.assertTrue(!coverage.isValidArgs());
	}
	
	/**
	 * <p> Calling isValidArgs() with positive length and valid weighted and reference EFGs.
	 * <p> Tests that isValidArgs() returns true.
	 */
	public void testIsValidArgs2() {
		String sep = File.separator;
		WeightedRandom coverage = new WeightedRandom();
		WeightedRandomConfiguration.LENGTH = 3;
		// valid weighted efg in this test class's directory
		WeightedRandomConfiguration.WEIGHTS = 
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep + 
			"testcase" + sep + "plugin" + sep + "Project.EFG.xml";
		WeightedRandomConfiguration.EFG_FILE = 
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep +
			"testcase" + sep + "plugin" + sep + "Project.EFG.xml"; // NECESSARY


		Assert.assertTrue(coverage.isValidArgs());

	}
	
	/**
	 * <p> Calling isValidArgs() with NO weighted EFG specified should return false.
	 */
	public void testIsValidArgs3() {
		WeightedRandom coverage = new WeightedRandom();
		WeightedRandomConfiguration.LENGTH = 3;
		WeightedRandomConfiguration.WEIGHTS = new String();
		WeightedRandomConfiguration.EFG_FILE = WeightedRandomConfiguration.WEIGHTS;
		
		Assert.assertTrue(!coverage.isValidArgs());
	}
	
	/**
	 * <p> Calling isValidArgs() with NO reference EFG specified should return false.
	 */
	public void testIsValidArgs4() {
		setup();
		String sep = File.separator;
		WeightedRandom coverage = new WeightedRandom();
		WeightedRandomConfiguration.LENGTH = 3;
		WeightedRandomConfiguration.WEIGHTS =
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep + 
			"testcase" + sep + "plugin" + sep + "Project.EFG.xml";
		WeightedRandomConfiguration.EFG_FILE = "";
		
		Assert.assertTrue(!coverage.isValidArgs());
	}

	/**
	 * <p> Calling isValidArgs() with a weights argument to a non-existent file should be false.
	 */
	public void testIsValidArgs5() {
		String sep = File.separator;
		WeightedRandom coverage = new WeightedRandom();
		WeightedRandomConfiguration.LENGTH = 3;
		// valid weighted efg in this test class's directory
		WeightedRandomConfiguration.WEIGHTS = 
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep + 
			"testcase" + sep + "plugin" + sep + "IDontExist.EFG.xml";
		WeightedRandomConfiguration.EFG_FILE = 
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep +
			"testcase" + sep + "plugin" + sep + "Project.EFG.xml"; // NECESSARY


		Assert.assertFalse(coverage.isValidArgs());

	}

	/**
	 * <p> Check that getConfiguration does return a WeightedRandomConfiguration.
	 * <p> This configuration should extend TestCaseGeneratorConfiguration.
	 */
	public void testGetConfiguration() {
		WeightedRandom coverage = new WeightedRandom();
		WeightedRandomConfiguration configuration = 
			(WeightedRandomConfiguration) coverage.getConfiguration();
		Assert.assertTrue(configuration.PLUGIN != null);
		Assert.assertTrue(configuration.WEIGHTS != null);	
		// Not necessarily .equals("") because of previous tests
	}

	
	/**
	 * <p> Test that the generateShortestPaths() method produces the correct amount of testcases.
	 */
	public void testGenerateShortestPath1() {
		setup();
		String sep = File.separator;
		WeightedRandom coverage = new WeightedRandom();
		WeightedRandomConfiguration.OUTPUT_DIR = outputDir;
		WeightedRandomConfiguration.LENGTH = 3;
		WeightedRandomConfiguration.WEIGHTS =
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep + "testcase"
			+ sep + "plugin" + sep + "Project.EFG.xml";
		WeightedRandomConfiguration.EFG_FILE =
			"." + sep + "edu" + sep + "umd" + sep + "cs" + sep + "guitar" + sep + "testcase"
			+ sep + "plugin" + sep + "Project.EFG.xml";
		WeightedRandomConfiguration.MAX_NUMBER = 0; // generate all shortest paths
		
		Assert.assertTrue(coverage.isValidArgs());
		
		EFG efg = (EFG) IO.readObjFromFile(WeightedRandomConfiguration.EFG_FILE, EFG.class);
		coverage.generate(efg, outputDir, 0);
		
		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);

		Assert.assertTrue(listFiles.length == 29);

		TestCase tc = (TestCase) readObjFromFile(outputDir + sep + "t_shortestPath_e54.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);
	}
	
	/**
	 * <p> Test the plugin's generateShortestPaths() method for correctness.
	 * <p> This test uses an EFG with a single initial event, whose shortest path should be itself.
	 */
	public void testGenerateShortestPath2() {
		setup();
		
		WeightedRandomConfiguration.OUTPUT_DIR = outputDir;
		WeightedRandomConfiguration.LENGTH = 5;
		WeightedRandomConfiguration.MAX_NUMBER = 0;
		
		WeightedRandom generator = createGenerator(efg2, efg2.getEventGraph());
		generator.generate(efg2, outputDir, 0);

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		Assert.assertTrue(listFiles.length == 1);
		
		TestCase tc = (TestCase) readObjFromFile(outputDir + File.separator + "t_shortestPath_e1.tst", TestCase.class);
		Assert.assertTrue(tc.getStep().size() == 1);
		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e1"));
	}
	
	/**
	 * <p> Test the plugin's generateShortestPaths() method for correctness.
	 * <p> This test checks that generateShortestPaths() complies with a given nMaxNumber.
	 */
	public void testGenerateShortestPath3() {
		setup();
		
		WeightedRandomConfiguration.OUTPUT_DIR = outputDir;
		WeightedRandomConfiguration.LENGTH = 5;
		WeightedRandomConfiguration.MAX_NUMBER = 2;
		
		// There are three reachable events in efg8.
		WeightedRandom generator = createGenerator(efg8, efg8.getEventGraph());
		generator.generate(efg8, outputDir, 2);

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		Assert.assertTrue(listFiles.length == 2);
	}
	
	/**
	 * <p> Test the plugin's generateShortestPaths() method for correctness.
	 * <p> This test checks that each shortest path generated for a toy EFG is correct.
	 */
	public void testGenerateShortestPath4() {
		setup();
		
		// Modifying a property of EFG efgC to make the shortest paths more complex.
		EFG efg = efgC;
		e2.initial = false;
		e6.initial = false;
		efg.events.event.set(1, e2);
		efg.events.event.set(5, e6);
		
		// Set up a new weights file.
		EventGraphType weights = new EventGraphType();
		weights.row = new ArrayList<RowType>();
		weights.row.add(new RowType());
		weights.row.add(new RowType());
		weights.row.add(new RowType());
		weights.row.add(new RowType());
		weights.row.add(new RowType());
		weights.row.add(new RowType());
		weights.row.get(0).e = new ArrayList<Integer>();
		weights.row.get(1).e = new ArrayList<Integer>();
		weights.row.get(2).e = new ArrayList<Integer>();
		weights.row.get(3).e = new ArrayList<Integer>();
		weights.row.get(4).e = new ArrayList<Integer>();
		weights.row.get(5).e = new ArrayList<Integer>();

		weights.row.get(0).e.add(0);
		weights.row.get(0).e.add(1);
		weights.row.get(0).e.add(9001);
		weights.row.get(0).e.add(1);
		weights.row.get(0).e.add(0);
		weights.row.get(0).e.add(0);

		weights.row.get(1).e.add(1);
		weights.row.get(1).e.add(0);
		weights.row.get(1).e.add(1);
		weights.row.get(1).e.add(0);
		weights.row.get(1).e.add(1);
		weights.row.get(1).e.add(0);

		weights.row.get(2).e.add(1);
		weights.row.get(2).e.add(50);
		weights.row.get(2).e.add(0);
		weights.row.get(2).e.add(0);
		weights.row.get(2).e.add(0);
		weights.row.get(2).e.add(0);

		weights.row.get(3).e.add(1);
		weights.row.get(3).e.add(0);
		weights.row.get(3).e.add(0);
		weights.row.get(3).e.add(0);
		weights.row.get(3).e.add(0);
		weights.row.get(3).e.add(0);

		weights.row.get(4).e.add(0);
		weights.row.get(4).e.add(1);
		weights.row.get(4).e.add(0);
		weights.row.get(4).e.add(0);
		weights.row.get(4).e.add(0);
		weights.row.get(4).e.add(0);

		weights.row.get(5).e.add(0);
		weights.row.get(5).e.add(0);
		weights.row.get(5).e.add(1);
		weights.row.get(5).e.add(0);
		weights.row.get(5).e.add(0);
		weights.row.get(5).e.add(0);

		WeightedRandomConfiguration.OUTPUT_DIR = outputDir;
		WeightedRandomConfiguration.LENGTH = 5;
		WeightedRandomConfiguration.MAX_NUMBER = 0;

		WeightedRandom generator = createGenerator(efg, weights);
		generator.generate(efg, outputDir, 0);

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		Assert.assertTrue(listFiles.length == 5);
		
		TestCase tc = (TestCase) readObjFromFile(outputDir + File.separator + "t_shortestPath_e1.tst", TestCase.class);
		Assert.assertTrue(tc.getStep().size() == 1);
		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e1"));
		
		tc = (TestCase) readObjFromFile(outputDir + File.separator + "t_shortestPath_e2.tst", TestCase.class);
		Assert.assertTrue(tc.getStep().size() == 2);
		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e1"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e2"));
		
		tc = (TestCase) readObjFromFile(outputDir + File.separator + "t_shortestPath_e3.tst", TestCase.class);
		Assert.assertTrue(tc.getStep().size() == 3);
		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e1"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e2"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e3"));

		tc = (TestCase) readObjFromFile(outputDir + File.separator + "t_shortestPath_e4.tst", TestCase.class);
		Assert.assertTrue(tc.getStep().size() == 2);
		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e1"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));

		tc = (TestCase) readObjFromFile(outputDir + File.separator + "t_shortestPath_e5.tst", TestCase.class);
		Assert.assertTrue(tc.getStep().size() == 3);
		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e1"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e2"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e5"));
	}

	/**
	 * <p> Assert that the weighted random traversal of a graph produces valid paths.
	 * <p> Use EFG efg2 with a length of 2, max number of 1.
	 * <p> Because there is only one event looping to itself, uses just a single weight {1}.
	 */
	public void testGenerateRandom1() {
		setup();
		EFG efg = efg2;
		
		ArrayList<EventType> initialEvents = new ArrayList<EventType>();
		initialEvents.add(e1);
		
		ArrayList<String> initialEventIDs = new ArrayList<String>();
		initialEventIDs.add("e1");
		
		Hashtable<EventType, List<Integer>> weights = new Hashtable<EventType, List<Integer>>();
		ArrayList<Integer> e1_weights = new ArrayList<Integer>();
		e1_weights.add(new Integer(1));
		weights.put(e1, e1_weights);
		
		int nMaxNumber = 1;
		
		WeightedRandom generator = createGenerator(efg, initialEvents, weights, nMaxNumber);
		
		Hashtable<String, List<String>> successors = new Hashtable<String, List<String>>();
		ArrayList<String> e1_succs = new ArrayList<String>();
		e1_succs.add("e1");
		successors.put("e1", e1_succs);
		
		int length = 2;
		
		Assert.assertTrue(runRandomGenerateTemplate(generator, successors, initialEventIDs, length, nMaxNumber));
	}

	/**
	 * <p> Assert that the weighted random traversal of a graph produces valid paths.
	 * <p> Use EFG efg3 with a length of 4, max number of 1.
	 * <p> The weights file places a zero along the edge from e4 to e1, so all possible paths are finite.
	 * Checks that the traversal doesn't go from e4 to e1.
	 */
	public void testGenerateRandom2() {
		setup();
		EFG efg = efg3;
		
		ArrayList<EventType> initialEvents = new ArrayList<EventType>();
		initialEvents.add(e1);
		
		ArrayList<String> initialEventIDs = new ArrayList<String>();
		initialEventIDs.add("e1");
		
		Hashtable<EventType, List<Integer>> weights = new Hashtable<EventType, List<Integer>>();
		ArrayList<Integer> e1_weights = new ArrayList<Integer>();
		ArrayList<Integer> e3_weights = new ArrayList<Integer>();
		ArrayList<Integer> e4_weights = new ArrayList<Integer>();
		e1_weights.add(new Integer(0));
		e1_weights.add(new Integer(1));
		e1_weights.add(new Integer(0));
		e3_weights.add(new Integer(0));
		e3_weights.add(new Integer(0));
		e3_weights.add(new Integer(1));
		e4_weights.add(new Integer(0));
		e4_weights.add(new Integer(0));
		e4_weights.add(new Integer(0));
		weights.put(e1, e1_weights);
		weights.put(e3, e3_weights);
		weights.put(e4, e4_weights);
		
		int nMaxNumber = 1;
		
		WeightedRandom generator = createGenerator(efg, initialEvents, weights, nMaxNumber);
		
		Hashtable<String, List<String>> successors = new Hashtable<String, List<String>>();
		ArrayList<String> e1_succs = new ArrayList<String>();
		ArrayList<String> e3_succs = new ArrayList<String>();
		e1_succs.add("e3");
		e3_succs.add("e4");
		successors.put("e1", e1_succs);
		successors.put("e3", e3_succs);
		
		int length = 4;
		
		Assert.assertTrue(runRandomGenerateTemplate(generator, successors, initialEventIDs, length, nMaxNumber));
	}

	/**
	 * <p> Assert that the weighted random traversal of a graph produces valid paths.
	 * <p> Use EFG efg8 with a length of 10, max number of 10.
	 * <p> The weights make the graph go through path e1 -> e3 -> e4 (cycle at e4).
	 */
	public void testGenerateRandom3() {
		setup();
		EFG efg = efg8;
		
		ArrayList<EventType> initialEvents = new ArrayList<EventType>();
		initialEvents.add(e1);
		
		ArrayList<String> initialEventIDs = new ArrayList<String>();
		initialEventIDs.add("e1");

		
		Hashtable<EventType, List<Integer>> weights = new Hashtable<EventType, List<Integer>>();
		ArrayList<Integer> e1_weights = new ArrayList<Integer>();
		ArrayList<Integer> e3_weights = new ArrayList<Integer>();
		ArrayList<Integer> e4_weights = new ArrayList<Integer>();
		e1_weights.add(new Integer(0));
		e1_weights.add(new Integer(1));
		e1_weights.add(new Integer(0));
		e3_weights.add(new Integer(0));
		e3_weights.add(new Integer(0));
		e3_weights.add(new Integer(1));

		e4_weights.add(new Integer(0));
		e4_weights.add(new Integer(0));
		e4_weights.add(new Integer(1));
		weights.put(e1, e1_weights);
		weights.put(e3, e3_weights);
		weights.put(e4, e4_weights);
		
		int nMaxNumber = 10;
		
		WeightedRandom generator = createGenerator(efg, initialEvents, weights, nMaxNumber);
		
		Hashtable<String, List<String>> successors = new Hashtable<String, List<String>>();
		ArrayList<String> e1_succs = new ArrayList<String>();
		ArrayList<String> e3_succs = new ArrayList<String>();
		ArrayList<String> e4_succs = new ArrayList<String>();
		e1_succs.add("e3");
		e3_succs.add("e4");
		e4_succs.add("e4");
		successors.put("e1", e1_succs);
		successors.put("e3", e3_succs);
		successors.put("e4", e4_succs);
		
		int length = 10;
		
		Assert.assertTrue(runRandomGenerateTemplate(generator, successors, initialEventIDs, length, nMaxNumber));
	}

	/**
	 * <p> Assert that the weighted random traversal of a graph produces valid paths.
	 * <p> Use EFG efg2 with a length of 2, max number of 1.
	 * <p> Uses weights to turn EFG into a dead end after initial event.
	 */
	public void testGenerateRandom4() {
		setup();
		EFG efg = efg2;
		
		ArrayList<EventType> initialEvents = new ArrayList<EventType>();
		initialEvents.add(e1);
		
		ArrayList<String> initialEventIDs = new ArrayList<String>();
		initialEventIDs.add("e1");
		
		Hashtable<EventType, List<Integer>> weights = new Hashtable<EventType, List<Integer>>();
		ArrayList<Integer> e1_weights = new ArrayList<Integer>();
		e1_weights.add(new Integer(0));
		weights.put(e1, e1_weights);
		
		int nMaxNumber = 1;
		
		WeightedRandom generator = createGenerator(efg, initialEvents, weights, nMaxNumber);
		
		Hashtable<String, List<String>> successors = new Hashtable<String, List<String>>();
		
		int length = 2;
		
		Assert.assertTrue(runRandomGenerateTemplate(generator, successors, initialEventIDs, length, nMaxNumber));
	}

	/**
	 * Assert that the weighted random traversal of a graph produces valid paths.
	 * Use EFG efg2 with a length of 0, max number of 1.
	 * The generateRandomPaths() method should do nothing due to length = 0.
	 */
	public void testGenerateRandom5() {
		setup();
		EFG efg = efg2;
		
		ArrayList<EventType> initialEvents = new ArrayList<EventType>();
		initialEvents.add(e1);
		
		ArrayList<String> initialEventIDs = new ArrayList<String>();
		initialEventIDs.add("e1");
		
		Hashtable<EventType, List<Integer>> weights = new Hashtable<EventType, List<Integer>>();
		ArrayList<Integer> e1_weights = new ArrayList<Integer>();
		e1_weights.add(new Integer(0));
		weights.put(e1, e1_weights);
		
		int nMaxNumber = 1;
		
		WeightedRandom generator = createGenerator(efg, initialEvents, weights, nMaxNumber);
		
		Hashtable<String, List<String>> successors = new Hashtable<String, List<String>>();
		
		int length = 0;
		
		Assert.assertTrue(runRandomGenerateTemplate(generator, successors, initialEventIDs, length, nMaxNumber));
	}

	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test attempts to verify a null weights field against a null efg field, which should be false.
	 * Next attempts to verify a null weights field against a non-null efg field, which should be false.
	 * Finally attempts to verify a non-null weights field against a null efg field, which should be false.
	 */
	public void testVerifyWeights1() {
		setup();
		WeightedRandom generator = new WeightedRandom();
		
		Assert.assertFalse(runVerifyWeights(generator, null));
		
		Assert.assertFalse(runVerifyWeights(generator, efg0));
		
		generator = createGenerator(null, efg0.getEventGraph());
		Assert.assertFalse(runVerifyWeights(generator, null));
	}
	
	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test attempts to verify an efg with a null EventGraphType, which should be false.
	 * Then attempts every using an efg and weights whose EventGraphType has uninstantiated row.
	 * The getRow() method in EventGraphType() never returns null, so this should compare two empty graphs.
	 * An empty graph should be considered a valid weighted graph for an empty graph, so this returns true.
	 */
	public void testVerifyWeights2() {
		setup();
		WeightedRandom generator = createGenerator(new EFG(), new EventGraphType());
		
		Assert.assertFalse(runVerifyWeights(generator, new EFG()));
		
		// Set up an efg with an empty event graph.
		EFG efg = new EFG();
		EventGraphType graph = new EventGraphType();
		efg.setEventGraph(graph);
		
		// weights and efg fields EventGraphType fields with undefined rows.
		// the getRow() method of EventGraphType instantiates rows if null, preventing failure.
		// Comparison is of two empty graphs, which should be considered similar.
		generator = createGenerator(efg, efg.getEventGraph());
		Assert.assertTrue(runVerifyWeights(generator, efg));
	}
	
	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test attempts to verify an efg against a weights with a different amount of events.
	 */
	public void testVerifyWeights3() {
		setup();
		WeightedRandom generator = createGenerator(efg0, efgC.getEventGraph());
		
		// efg0 has just one event, while efgC has six.
		Assert.assertFalse(runVerifyWeights(generator, efg0));
	}
	
	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test attempts to verify a weights matrix that isn't n-by-n (malformed).
	 */
	public void testVerifyWeights4() {
		setup();
		
		// Set up a 1-by-2 efg.
		EFG efg = new EFG();
		efg.events = new EventsType();
		efg.eventGraph = new EventGraphType();
		efg.events.event = new ArrayList<EventType>();
		efg.eventGraph.row = new ArrayList<RowType>();
		efg.events.event.add(e4);
		efg.eventGraph.row.add(new RowType());
		efg.eventGraph.row.get(0).e = new ArrayList<Integer>();
		efg.eventGraph.row.get(0).e.add(1);
		efg.eventGraph.row.get(0).e.add(1);
		
		WeightedRandom generator = createGenerator(efg0, efg.getEventGraph());
		Assert.assertFalse(runVerifyWeights(generator, efg0));
	}
	
	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test attempts to verify a weights matrix that has negative weights.
	 */
	public void testVerifyWeights5() {
		setup();
		
		WeightedRandom generator = createGenerator(efg8, efgD.getEventGraph());
		Assert.assertFalse(runVerifyWeights(generator, efg8));
	}
	
	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test attempts to verify a weights matrix that has edges where the EFG doesn't.
	 */
	public void testVerifyWeights6() {
		setup();
		
		// Compare an EFG that is a 3-cycle against a fully-connected 3 event weights graph.
		WeightedRandom generator = createGenerator(efg3, efg8.getEventGraph());
		Assert.assertFalse(runVerifyWeights(generator, efg3));
	}
	
	/**
	 * Test that the plugin correctly verifies a weighted graph against its given EFG.
	 * This test verifies weights graphs that are valid for their given EFG.
	 */
	public void testVerifyWeights7() {
		setup();
		
		// Test weights which are the same as the EFG.
		WeightedRandom generator = createGenerator(efg4, efg4.getEventGraph());
		Assert.assertTrue(runVerifyWeights(generator, efg4));
		
		generator = createGenerator(efgC, efgC.getEventGraph());
		Assert.assertTrue(runVerifyWeights(generator, efgC));
		
		// Use weights which reduce the number of traversable edges in the EFG graph.
		generator = createGenerator(efg8, efg3.getEventGraph());
		Assert.assertTrue(runVerifyWeights(generator, efg8));
	}

	/**
	 * Creates and returns a WeightedRandom object filled in with fields according to parameters.
	 * Instantiates a new object, and then sets its fields via reflection.
	 *
	 * @param efg		The EFG graph file the WeightedRandom object should use in its "efg" field.
	 * @param weights	The EventGraphType object the WeightedRandom object should use in its "weights" field.
	 * @param initialEvents	The list of initial events
	 * @param weights	The map of each event to its row of Integer weights.
	 *
	 * @return		The created WeightedRandom object.
	 */
	private WeightedRandom createGenerator(EFG efg, EventGraphType weights) {
		WeightedRandom generator = new WeightedRandom();
		
		try {
			Field obj_efg = TCPlugin.class.getDeclaredField("efg");
			obj_efg.setAccessible(true);
			obj_efg.set(generator, efg);
			
			Field obj_weights = WeightedRandom.class.getDeclaredField("weights");
			obj_weights.setAccessible(true);
			obj_weights.set(generator, weights);
		} catch (NoSuchFieldException e) {
			System.out.println("Unable to access a field of WeightedRandom.");
			return null;
		} catch (IllegalArgumentException e) {
			System.out.println("Attempted to set a field on an invalid object.");
			return null;
		} catch (Exception e) {
			System.out.println("Caught generic exception:");
			System.err.println(e.getMessage());
			return null;
		}
		
		return generator;
	}
	
	/**
	 * Creates and returns a WeightedRandom object filled in with fields according to parameters.
	 * Instantiates a new object, and then sets its fields via reflection.
	 *
	 * @param efg		The EFG graph file the WeightedRandom object should use in its "efg" field.
	 * @param initialEvents	The list of initial events
	 * @param weights	The map of each event to its row of Integer weights.
	 * @param nMaxNumber	The number of testcases to generate.
	 *
	 * @return		The created WeightedRandom object.
	 */
	private WeightedRandom createGenerator(EFG efg, List<EventType> initialEvents, Hashtable<EventType,
						List<Integer>> weights, int nMaxNumber) {
		WeightedRandom generator = new WeightedRandom();
		
		try {
			Field obj_efg = TCPlugin.class.getDeclaredField("efg");
			obj_efg.setAccessible(true);
			obj_efg.set(generator, efg);
			
			Field obj_maxN = WeightedRandom.class.getDeclaredField("nMaxNumber");
			obj_maxN.setAccessible(true);
			obj_maxN.set(generator, nMaxNumber);
			
			Field obj_weights = WeightedRandom.class.getDeclaredField("eventWeights");
			obj_weights.setAccessible(true);
			obj_weights.set(generator, weights);
			
			Field obj_initial = TCPlugin.class.getDeclaredField("initialEvents");
			obj_initial.setAccessible(true);
			obj_initial.set(generator, initialEvents);
		} catch (NoSuchFieldException e) {
			System.out.println("Unable to access a field of WeightedRandom.");
			return null;
		} catch (IllegalArgumentException e) {
			System.out.println("Attempted to set a field on an invalid object.");
			return null;
		} catch (Exception e) {
			System.out.println("Caught generic exception:");
			System.err.println(e.getMessage());
			return null;
		}
		
		return generator;
	}
	
	/**
	 * Template to run the private verifyWeights(EFG efg) method via reflection.
	 *
	 * @param generator	The WeightedRandom object that will verify weights.
	 * @param efg		The EFG that weights are referenced against.
	 *
	 * @return		The result of verifyWeights(EFG efg).
	 */
	private boolean runVerifyWeights(WeightedRandom generator, EFG efg) {
		try {	
			Method m = WeightedRandom.class.getDeclaredMethod("verifyWeights", EFG.class);
			m.setAccessible(true);
			Boolean verify = (Boolean) m.invoke(generator, efg);
			return verify.booleanValue();
		} catch (NoSuchMethodException e) {
			System.out.println("Method generateRandomPaths(int, int) is undefined.");
			return false;
		} catch (IllegalAccessException e) {
			System.out.println("Access is not allowed.");
			return false;
		} catch (InvocationTargetException e) {
			System.out.println("Invocation through reflection failed.");
			return false;
		}
	}
	
	/**
	 * Template to run and verify correctness of random test generation.
	 * The private generateRandomPaths method is accessed via reflection.
	 *
	 * @param generator	The WeightedRandom object that will generate testcases.
	 * @param successors	Mapping of event IDs to valid following event IDs in a path.
	 * @param initialEvents	List of valid initial event IDs for a testcase.
	 * @param length	The maximum length of a testcase (shorter tests possible in event of a dead end).
	 * @param nMaxNumber	The number of testcases to be produced by random traversal.
	 *
	 * @return		Validity of the generated testcases.
	 */	
	private boolean runRandomGenerateTemplate(WeightedRandom generator, Hashtable<String, List<String>> successors,
						 List<String> initialEvents, int length, int nMaxNumber) {
		WeightedRandomConfiguration.OUTPUT_DIR = outputDir;
		
		// Run the method to generate nMaxNumber tests.
		try {	
			Method m = WeightedRandom.class.getDeclaredMethod("generateRandomPaths", int.class, int.class);
			m.setAccessible(true);
			m.invoke(generator, 0, length);
		} catch (NoSuchMethodException e) {
			System.out.println("Method generateRandomPaths(int, int) is undefined.");
			return false;
		} catch (IllegalAccessException e) {
			System.out.println("Access is not allowed.");
			return false;
		} catch (InvocationTargetException e) {
			System.out.println("Invocation through reflection failed.");
			return false;
		}
		
		
		// Verify that created tests are valid and follow arguments.
		File dir = new File(outputDir);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		};

		// Assert that the right number of files are created.
		File[] listFiles = dir.listFiles(filter);
		System.out.println(listFiles.length + " files produced.");
		if (length == 0 && listFiles.length == 0)
			return true;
		
		if(!(listFiles.length == nMaxNumber))
			return false;
		
		// Assert that each testcase is valid.
		for (int i = 0; i < nMaxNumber; i++) {
			String tcName = "t_random" + i + ".tst";
			TestCase tc = (TestCase) readObjFromFile(outputDir + File.separator + tcName, TestCase.class);

			// The first step must be an initial event.			
			List<StepType> tcSteps = tc.getStep();
			StepType current = tcSteps.get(0);
			if (!initialEvents.contains(current.getEventId())) {
				System.out.println(current.getEventId() + " not an initial event.");
				return false;
			}
			
			// Each following step must be reachable from the current step.
			for (int j = 1; j < tcSteps.size(); j++) {
				StepType next = tcSteps.get(j);
				if (!successors.get(current.getEventId()).contains(next.getEventId())) {
					System.out.println(next.getEventId() + " not a valid step after " + current.getEventId());
					return false;
				}
				
				current = next;
			}
			
			// The testcase must be of correct length, or have reached a dead end prior.
			if (tcSteps.size() > length) {
				System.out.println("Test is too long.");
				return false;
			} else if (tcSteps.size() < length) {
				if (successors.get(current.getEventId()) != null) {
					System.out.println("Test is too short.");
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static Object readObjFromFile(String sFileName, Class<?> cls) {
        Object retObj = null;
        try {
            retObj = readObjFromFile(new FileInputStream(sFileName), cls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return retObj;
    	}
	
	 private static Object readObjFromFile(FileInputStream is, Class<?> cls) {

	        Object retObj = null;
	        try {

	            String packageName = cls.getPackage().getName();
	            JAXBContext jc = JAXBContext.newInstance(packageName);
	            Unmarshaller u = jc.createUnmarshaller();
	            retObj = u.unmarshal(is);
		    
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return retObj;
	    }

}
