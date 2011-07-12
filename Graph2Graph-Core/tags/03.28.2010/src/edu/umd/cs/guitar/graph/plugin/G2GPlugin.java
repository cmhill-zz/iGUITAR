/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.graph.plugin;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.Mapping;
import edu.umd.cs.guitar.model.data.MappingType;
import edu.umd.cs.guitar.model.data.ObjectFactory;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public abstract class G2GPlugin {
	EFG inputGraph;

	EFG outputGraph;
	Mapping map;

	/**
	 * @param inputGraph
	 */
	public G2GPlugin(EFG inputGraph) {
		super();
		this.inputGraph = inputGraph;
	}

	ObjectFactory factory = new ObjectFactory();

	/**
	 * Lazy parsing input graph
	 * 
	 * @param inputGraph
	 */
	public abstract void parseGraph();

	/**
	 * @return
	 */
	public EFG getGraph() {
		return this.outputGraph;
	}

	/**
	 * @return
	 */
	public Mapping getMap() {
		return this.map;
	}

}
