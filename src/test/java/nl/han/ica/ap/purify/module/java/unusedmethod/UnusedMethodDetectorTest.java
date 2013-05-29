/**
 * Copyright (c) 2013 HAN University of Applied Sciences
 * Arjan Oortgiese
 * Boyd Hofman
 * Joëll Portier
 * Michiel Westerbeek
 * Tim Waalewijn
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package nl.han.ica.ap.purify.module.java.unusedmethod;

import static org.junit.Assert.*;

import java.util.ArrayList;

import nl.han.ica.ap.purify.language.java.callgraph.CallGraph;
import nl.han.ica.ap.purify.language.java.callgraph.MethodNode;
import nl.han.ica.ap.purify.language.java.callgraph.listeners.ClassNodeListener;
import nl.han.ica.ap.purify.language.java.callgraph.listeners.EdgeListener;
import nl.han.ica.ap.purify.test.tools.ParserTools;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

/**
 * @author Tim
 *
 */
public class UnusedMethodDetectorTest {

	private static final String FILE1 = "/unusedmethod/Demo1.java";
	private static final String FILE2 = "/unusedmethod/Demo2.java";
	private static final String FILE3 = "/unusedmethod/Demo3.java";
	private CallGraph graph;
	private ClassNodeListener classNodelistener;
	private EdgeListener edgelistener;
	private ParseTreeWalker walker;
	private ParseTree tree;
	private UnusedMethodDetector umd;
	
	public UnusedMethodDetectorTest() {
		graph = new CallGraph();
		classNodelistener = new ClassNodeListener(graph);
		edgelistener = new EdgeListener(graph);
		walker = new ParseTreeWalker();
		
		tree = ParserTools.getParseTree(FILE1);
		walker.walk(classNodelistener, tree);
		
		tree = ParserTools.getParseTree(FILE2);
		walker.walk(classNodelistener, tree);
		
		tree = ParserTools.getParseTree(FILE3);
		walker.walk(classNodelistener, tree);
		
		tree = ParserTools.getParseTree(FILE1);
		walker.walk(edgelistener, tree);
		
		tree = ParserTools.getParseTree(FILE2);
		walker.walk(edgelistener, tree);
		
		tree = ParserTools.getParseTree(FILE3);
		walker.walk(edgelistener, tree);
		
		umd = new UnusedMethodDetector();
		umd.setGraph(graph);
	}
	
	@Test
	public void unCalledMethodsTest() {
		umd.detect();
		
		ArrayList<MethodNode> expectedmethods = new ArrayList<MethodNode>();
		expectedmethods.add(graph.getMethod("Demo1","uncalledmethod1( )"));
		expectedmethods.add(graph.getMethod("Demo1","uncalledmethod2( )"));
		expectedmethods.add(graph.getMethod("Demo1","uncalledmethod3( )"));
		expectedmethods.add(graph.getMethod("Demo2","uncalledmethod1( )"));
		expectedmethods.add(graph.getMethod("Demo3","uncalledmethod( )"));
		
		assertEquals(expectedmethods.size(),umd.uncalledmethods.size());
		for(MethodNode m : expectedmethods) {
			assertTrue(umd.uncalledmethods.contains(m));
		}
	}

}
