package edu.umd.cs.guitar.testcase.plugin;

import junit.framework.TestCase;
import edu.umd.cs.guitar.model.data.*;
import edu.umd.cs.guitar.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.io.*;

/**
* Tests the RandomTestCase class.
*/
public class JUnitTests extends TestCase
{
	//Shared test objects.

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

	/**
	* Set up shared test objects.
	* This overwrites any previous instances, so tearDown() is unnecessary.
	* @see #efg0
	* @see #efg1
	* @see #efg2
	* @see #efg3
	* @see #efg4
	* @see #efg5
	* @see #efg6
	* @see #efg7
	* @see #efg8
	* @see #efg9
	*/
	public void setUp()
	{
		e1 = new EventType();
		e1.initial = true;
		e2 = new EventType();
		e2.initial = true;
		e3 = new EventType();
		e3.initial = false;
		e4 = new EventType();
		e4.initial = false;
		e5 = new EventType();
		e5.initial = false;
		e6 = new EventType();
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
		efg7.events.event.add(e2);
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
	}

	/**
	* Tests the constructor. 
	*
	* Tests that a RandomTestCase object can be instantiated and that
	* the default values are properly set.
	*/
	public void testConstructor()
	{
		RandomTestCase R = new RandomTestCase();
		assertTrue(R != null);
		assertTrue(R.listEvent != null);
            assertTrue(R.listPath != null);
            assertTrue(R.invokingEvents != null);
            assertTrue(R.path != null);
            assertTrue(R.treatedInvokingEvents != null);
	}

	/**
	* This tests that this method returns true (it should never return anything else).
	* 
	* The isValidArgs() method appears to do nothing useful.  It is not passed
	* any arguments, nor are the arguments given to the object by any other method
	* that is called before isValidArgs() is in the TestCaseGenerator class.
	*/
      public void testIsValidArgs()
	{
		RandomTestCase R = new RandomTestCase();
		assertTrue(R.isValidArgs());
	}

	/**
	* Tests getInitialEvent() with an EFG with no initial events.
	* @see #efg0
	*/
	public void testGetInitialEvent1()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg0;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 0);
	}

	/**
	* Tests getInitialEvent() with an empty EFG.
	* @see #efg1
	*/
	public void testGetInitialEvent2()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg1;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 0);
	}

	/**
	* Tests getInitialEvent() an EFG with 1 initial event.
	* @see #efg2
	*/
	public void testGetInitialEvent3()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg2;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 1);
		assertTrue(L.get(0) == 0);
	}

	/**
	* Tests getInitialEvent() an EFG with 1 initial event.
	* @see #efg3
	*/
	public void testGetInitialEvent4()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg3;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 1);
		assertTrue(L.get(0) == 0);
	}

	/**
	* Tests getInitialEvent() an EFG with 2 initial events.
	* @see #efg4
	*/
	public void testGetInitialEvent5()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg4;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 2);
		assertTrue(L.get(0) == 0 || L.get(0) == 1);
		assertTrue(L.get(1) == 0 || L.get(1) == 1);
		assertTrue(L.get(0) != L.get(1));
	}

	/**
	* Tests getInitialEvent() an EFG with 2 initial events.
	* @see #efg5
	*/
	public void testGetInitialEvent6()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg5;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 2);
		assertTrue(L.get(0) == 0 || L.get(0) == 1);
		assertTrue(L.get(1) == 0 || L.get(1) == 1);
		assertTrue(L.get(0) != L.get(1));
	}

	/**
	* Tests getInitialEvent() an EFG with 3 initial events.
	* @see #efgA
	*/
	public void testGetInitialEvent7()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efgA;
		List<Integer> L = R.getInitialEvent();
		assertTrue(L.size() == 3);
		assertTrue(L.get(0) == 0 || L.get(0) == 1 || L.get(0) == 5);
		assertTrue(L.get(1) == 0 || L.get(1) == 1 || L.get(1) == 5);
		assertTrue(L.get(2) == 0 || L.get(2) == 1 || L.get(2) == 5);
		assertTrue(L.get(0) != L.get(1));
		assertTrue(L.get(1) != L.get(2));
		assertTrue(L.get(0) != L.get(2));
	}

	/**
	* Tests getNextRandomStep() where there is only one possible choice.
	* The EFG has only one event.
	* @see #efg2
	*/
	public void testGetNextRandomStep1()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg2;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == 0); 
	}

	/**
	* Tests getNextRandomStep() where there is only one possible choice.
	* The EFG has two events.
	* @see #efg4
	*/
	public void testGetNextRandomStep2()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg4;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == 1); 
		int y = R.getNextRandomStep(1, new Random());
		assertTrue(y == 0); 
	}

	/**
	* Tests getNextRandomStep() where there is only one possible choice.
	* The EFG has three events.
	* @see #efg3
	*/
	public void testGetNextRandomStep3()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg3;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == 1); 
		int y = R.getNextRandomStep(1, new Random());
		assertTrue(y == 2); 
		int z = R.getNextRandomStep(2, new Random());
		assertTrue(z == 0);
	}

	/**
	* Tests getNextRandomStep() where there is only one possible choice.
	* The EFG has two events.
	* @see #efg5
	*/
	public void testGetNextRandomStep4()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg5;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == 0); 
		int y = R.getNextRandomStep(1, new Random());
		assertTrue(y == 1); 
	}

	/**
	* Tests getNextRandomStep() where there is no possible choice.
	* The EFG has one event.
	* @see #efg6
	*/
	public void testGetNextRandomStep5()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg6;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == -1); 
	}

	/**
	* Tests getNextRandomStep() where there are two possible choices.
	* The EFG has two events.
	* @see #efg7
	*/
	public void testGetNextRandomStep6()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg7;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == 0 || x == 1); 
		int y = R.getNextRandomStep(1, new Random());
		assertTrue(y == 0 || y == 1);
	}

	/**
	* Tests getNextRandomStep() where there are three possible choices.
	* The EFG has three events.
	* @see #efg8
	*/
	public void testGetNextRandomStep7()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg8;
		int x = R.getNextRandomStep(0, new Random(0));
		assertTrue(x == 0 || x == 1 || x == 2); 
		int y = R.getNextRandomStep(1, new Random(1));
		assertTrue(y == 0 || y == 1 || y == 2);
		int z = R.getNextRandomStep(1, new Random());
		assertTrue(z == 0 || z == 1 || z == 2);
	}

	/**
	* Tests getNextRandomStep() where there are two possible choices.
	* The EFG has three events.
	* @see #efg9
	*/
	public void testGetNextRandomStep8()
	{
		RandomTestCase R = new RandomTestCase();
		R.efg = efg9;
		int x = R.getNextRandomStep(0, new Random());
		assertTrue(x == 1 || x == 2); 
		int y = R.getNextRandomStep(1, new Random());
		assertTrue(y == 0 || y == 2);
		int z = R.getNextRandomStep(2, new Random());
		assertTrue(z == 0 || z == 1);
	}

	/**
	* Tests generate() for producing the correct number of test cases. 
	*/
	public void testGenerate1()
	{
		int x = (new Random()).nextInt(10) + 1;
		int y = x + 1;
		int z = x * y;
		RandomTestCase R = new RandomTestCase();
		R.names = new ArrayList<String>();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.generate(efg8, "TC", x);
		assertTrue(R.results.size() == x);

		R.results = new ArrayList<LinkedList<EventType>>();
		R.generate(efg8, "TC", y);
		assertTrue(R.results.size() == y);

		R.results = new ArrayList<LinkedList<EventType>>();
		R.generate(efg8, "TC", z);
		assertTrue(R.results.size() == z);
	}

	/**
	* Tests generate() where there is only one possible test case. 
	* The EFG has one event.
	* @see #efg2
	*/
	public void testGenerate2()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg2, "TC", 2);
		assertTrue(R.results.size() == 2);

		for(int i = 0; i < R.results.get(0).size(); i++)
		{
			assertTrue(R.results.get(0).get(i) == e1);
			assertTrue(R.results.get(1).get(i) == e1);
		}
	}

	/**
	* Tests generate() where there is only one possible test case. 
	* The EFG has two events.
	* @see #efg3
	*/
	public void testGenerate3()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg3, "TC", 12);
		assertTrue(R.results.size() == 12);

		for(int r = 0; r < R.results.size(); r++)
		{
			for(int i = 0; i < R.results.get(r).size(); i++)
			{
				if( i % 3 == 0)
					assertTrue(R.results.get(r).get(i) == e1);
				else if( i % 3 == 1)
					assertTrue(R.results.get(r).get(i) == e3);
				else
					assertTrue(R.results.get(r).get(i) == e4);
			}
		}
	}

	/**
	* Tests generate() where there are many possible test cases.
	* Each test case may not have the same event twice in a row. 
	* The EFG has three events.
	* @see #efg9
	*/
	public void testGenerate4()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg9, "TC", 12);
		assertTrue(R.results.size() == 12);

		for(int r = 0; r < R.results.size(); r++)
		{
			for(int i = 1; i < R.results.get(r).size(); i++)
			{
				assertTrue(R.results.get(r).get(i) != R.results.get(r).get(i - 1));
			}
		}
	}

	/**
	* Tests generate() where the only possible test case is length 1.
	* The EFG has one event.
	* @see #efg6
	*/
	public void testGenerate5()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg6, "TC", 2);
		assertTrue(R.results.size() == 2);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() == 1);
			assertTrue(R.results.get(r).get(0) == e1);
		}
	}

	/**
	* Tests generate() where there are no possible test cases.
	* The EFG is empty.
	* @see #efg1
	*/
	public void testGenerate6()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg1, "TC", 2);
		assertTrue(R.results.size() == 2);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() == 0);
		}
	}

	/**
	* Tests generate() where there are no possible test cases.
	* The EFG has no initial events.
	* @see #efg0
	*/
	public void testGenerate7()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg0, "TC", 2);
		assertTrue(R.results.size() == 2);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() == 0);
		}
	}

	/**
	* Tests generate() where there are two possible test cases.
	* The EFG has two events.
	* @see #efg5
	*/
	public void testGenerate8()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg5, "TC", 8);
		assertTrue(R.results.size() == 8);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).get(0) == e1 || R.results.get(r).get(0) == e2);
			for(int i = 1; i < R.results.get(r).size(); i++)
			{
				assertTrue(R.results.get(r).get(i) == R.results.get(r).get(i - 1));
			}
		}
	}

	/**
	* Tests generate() for producing the correct test names.
	*/
	public void testGenerate9()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg5, "TC", 20);
		assertTrue(R.results.size() == 20);
		assertTrue(R.names.size() == 20);

		assertTrue(R.names.get(0).equals("TC/t0.tst") || R.names.get(0).equals("TC\\t0.tst"));
		assertTrue(R.names.get(5).equals("TC/t5.tst") || R.names.get(5).equals("TC\\t5.tst"));
		assertTrue(R.names.get(9).equals("TC/t9.tst") || R.names.get(9).equals("TC\\t9.tst"));
		assertTrue(R.names.get(10).equals("TC/t10.tst") || R.names.get(10).equals("TC\\t10.tst"));
		assertTrue(R.names.get(11).equals("TC/t11.tst") || R.names.get(11).equals("TC\\t11.tst"));
		assertTrue(R.names.get(17).equals("TC/t17.tst") || R.names.get(17).equals("TC\\t17.tst"));
	}

	/**
	* Tests generate() where there are many possible test cases.
	* @see #efgA
	*/
	public void testGenerateA()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efgA, "TC", 100);
		assertTrue(R.results.size() == 100);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() > 0);
			assertTrue(R.results.get(r).get(0) == e1 || R.results.get(r).get(0) == e2 || R.results.get(r).get(0) == e6);

			if(R.results.get(r).get(0) == e6)
			{
				assertTrue(R.results.get(r).size() == 1);
			}
			else
			{
				assertTrue(R.results.get(r).size() > 1);
			}

			for(int i = 1; i < R.results.get(r).size(); i++)
			{
				if(R.results.get(r).get(i - 1) == e1)
				{
					assertTrue(R.results.get(r).get(i) == e3 || R.results.get(r).get(i) == e4);
				}
				else if(R.results.get(r).get(i - 1) == e2)
				{
					assertTrue(R.results.get(r).get(i) == e2 || R.results.get(r).get(i) == e5);
				}
				else if(R.results.get(r).get(i - 1) == e3)
				{
					assertTrue(R.results.get(r).get(i) == e1 || R.results.get(r).get(i) == e4);
				}
				else if(R.results.get(r).get(i - 1) == e4)
				{
					assertTrue(R.results.get(r).get(i) == e1);
				}
				else if(R.results.get(r).get(i - 1) == e5)
				{
					assertTrue(R.results.get(r).get(i) == e6);
				}
				else //e6
				{
					assertTrue(false); //this would suggest that there was an element after e6
				}
			}
		}
	}

	/**
	* Tests generate() where the only possible test cases are of length 2.
	* @see #efgB
	*/
	public void testGenerateB()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efgB, "TC", 20);
		assertTrue(R.results.size() == 20);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() == 2);
			assertTrue(R.results.get(r).get(0) == e1);
			assertTrue(R.results.get(r).get(1) == e3);
		}
	}

	/**
	* Tests generate() where there are many possible test cases.
	* @see #efgC
	*/
	public void testGenerateC()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efgC, "TC", 100);
		assertTrue(R.results.size() == 100);

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() > 0);
			assertTrue(R.results.get(r).get(0) == e1 || R.results.get(r).get(0) == e2 || R.results.get(r).get(0) == e6);

			for(int i = 1; i < R.results.get(r).size(); i++)
			{
				if(R.results.get(r).get(i - 1) == e1)
				{
					assertTrue(R.results.get(r).get(i) == e2 || R.results.get(r).get(i) == e3 || R.results.get(r).get(i) == e4);
				}
				else if(R.results.get(r).get(i - 1) == e2)
				{
					assertTrue(R.results.get(r).get(i) == e1 || R.results.get(r).get(i) == e3 || R.results.get(r).get(i) == e5);
				}
				else if(R.results.get(r).get(i - 1) == e3)
				{
					assertTrue(R.results.get(r).get(i) == e1 || R.results.get(r).get(i) == e2 || R.results.get(r).get(i) == e6);
				}
				else if(R.results.get(r).get(i - 1) == e4)
				{
					assertTrue(R.results.get(r).get(i) == e1);
				}
				else if(R.results.get(r).get(i - 1) == e5)
				{
					assertTrue(R.results.get(r).get(i) == e2);
				}
				else //e6
				{
					assertTrue(R.results.get(r).get(i) == e3);
				}
			}
		}
	}

	/**
	* Tests generate() for creating consistiently sized tests cases.
	*/
	public void testGenerateD()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efgC, "TC", 100);
		assertTrue(R.results.size() == 100);

		int x = R.results.get(0).size();

		for(int r = 0; r < R.results.size(); r++)
		{
			assertTrue(R.results.get(r).size() == x);
		}
	}

	/**
	* Tests generate() for creating the correct output directory.
	*/
	public void testGenerateE()
	{
		File dir = new File("TEST");
		int i = 0;
		while(dir.exists())
		{
			dir = new File("TEST" + i);
			i++;
		}

		RandomTestCase R = new RandomTestCase();
		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efg0, dir.getName(), 10);
		
		assertTrue(dir.exists());
	}

	/**
	* Tests generate() where there are many possible test cases.
	* Each test case may not have the same event twice in a row. 
	* The EFG uses values other that 1 to indicate presence of an edge,
	* some of which are used by other modules.
	* The EFG has three events.
	* @see #efgD
	*/
	public void testGenerateF()
	{
		RandomTestCase R = new RandomTestCase();

		R.results = new ArrayList<LinkedList<EventType>>();
		R.names = new ArrayList<String>();
		R.generate(efgD, "TC", 12);
		assertTrue(R.results.size() == 12);

		for(int r = 0; r < R.results.size(); r++)
		{
			for(int i = 1; i < R.results.get(r).size(); i++)
			{
				assertTrue(R.results.get(r).get(i) != R.results.get(r).get(i - 1));
			}
		}
	}
}