/*
 *  Copyright (c) 2009-@year@. The  GUITAR group  at the University of
 *  Maryland. Names of owners of this group may be obtained by sending
 *  an e-mail to atif@cs.umd.edu
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files
 *  (the "Software"), to deal in the Software without restriction,
 *  including without limitation  the rights to use, copy, modify, merge,
 *  publish,  distribute, sublicense, and/or sell copies of the Software,
 *  and to  permit persons  to whom  the Software  is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO  EVENT SHALL THE  AUTHORS OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR  OTHER LIABILITY,  WHETHER IN AN  ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package edu.umd.cs.guitar.graph;

import org.kohsuke.args4j.Option;

;

/**
 * Class contains the configurations of Graph2Graph Converter
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class
Graph2GraphConfiguration {

        // Help
	@Option(name = "-?",
                usage = "print this help message",
                aliases = "--help")
	static protected boolean HELP;

	// Common parameters
	@Option(name = "-p",
                usage = "plugin name",
                aliases = "--plugin")
	static public String PLUGIN = null;

	@Option(name = "-i",
                usage = "input graph file",
                aliases = "--input")
	static public String INPUT_FILE = null;

	@Option(name = "-o",
                usage = "optional output graph file",
                aliases = "--output")
	static public String OUTPUT_FILE = null;

	@Option(name = "-m",
                usage = "optional edge mapping file ",
                aliases = "--map")
	static public String MAPPING_FILE = null;

	@Option(name = "-x",
                usage = "optional extra argument ",
                aliases = "--extra")
	static public String EXTRA_ARG = null;
}
