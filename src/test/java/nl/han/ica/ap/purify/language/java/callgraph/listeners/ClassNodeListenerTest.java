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
package nl.han.ica.ap.purify.language.java.callgraph.listeners;

import static org.junit.Assert.*;

import java.util.HashMap;
import nl.han.ica.ap.purify.language.java.callgraph.CallGraph;
import nl.han.ica.ap.purify.language.java.callgraph.ClassNode;
import nl.han.ica.ap.purify.language.java.callgraph.MethodNode;
import nl.han.ica.ap.purify.test.tools.ParserTools;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

/**
 * This test runs a few files through the ClassNodeListener
 * and verifies the resulting CallGraph.
 * @author Tim
 */
public class ClassNodeListenerTest {
	private static final String FILE1 = "/callgraph/Demo1.java";
	private static final String FILE2 = "/callgraph/Demo2.java";
	private static final String FILE3 = "/callgraph/Demo3.java";
	private static final String FILE4 = "/callgraph/IDemo1.java";
	private CallGraph graph;
	private ClassNodeListener classNodelistener;
	private ParseTreeWalker walker;
	private ParseTree tree;
	
	public ClassNodeListenerTest() {
		graph = new CallGraph();
		classNodelistener = new ClassNodeListener(graph);
		walker = new ParseTreeWalker();
		
		tree = ParserTools.getParseTree(FILE1);
		walker.walk(classNodelistener, tree);
		
		tree = ParserTools.getParseTree(FILE2);
		walker.walk(classNodelistener, tree);
		
		tree = ParserTools.getParseTree(FILE3);
		walker.walk(classNodelistener, tree);
		
		tree = ParserTools.getParseTree(FILE4);
		walker.walk(classNodelistener, tree);
	}
	
	/**
	 * Check if all test ClassNodes were correctly added to the graph.
	 */
	@Test
	public void ClassNodeTest() {
		int nrofmethods;
		ClassNode n;
		
		n = graph.getNode("Demo1");	
		assertFalse(n == null);
		nrofmethods = n.getAllMethods().size();
		assertEquals(4, nrofmethods);
		
		n = graph.getNode("Demo2");
		nrofmethods = n.getAllMethods().size();
		assertFalse(n == null);
		assertEquals(3,nrofmethods);
		
		n = graph.getNode("Demo3");
		assertFalse(n == null);
		nrofmethods = n.getAllMethods().size();
		assertEquals(2,nrofmethods);
		
		n = graph.getNode("IDemo1");
		assertFalse(n != null);
	}
	
	/**
	 * Check if first test MethodNode was correctly added to the graph.
	 */
	@Test
	public void MethodNodeDemo1Test() {
		ClassNode n = graph.getNode("Demo1");
		HashMap<String,String> variables = new HashMap<String,String>();
		MethodNode m = n.getMethod("this");
		assertFalse(m == null);
		variables.put("d2", "Demo2");
		variables.put("i", "double");
		assertEquals(variables,m.getVariables());
		variables.clear();
		
		m = n.getMethod("Demo1( )");
		assertFalse(m == null);
		variables.put("d4", "Demo2");
		assertEquals(variables,m.getVariables());
		variables.clear();
		
		m = n.getMethod("test1( int )");
		assertFalse(m == null);
		assertTrue(m.getReturnType().equals("void"));
		assertTrue(m.hasModifier("public"));
		variables.put("i", "int");
		assertEquals(variables,m.getVariables());
		variables.clear();
		
		m = n.getMethod("test2( String )");
		assertFalse(m == null);
		assertTrue(m.getReturnType().equals("String"));
		assertTrue(m.hasModifier("public"));
		variables.put("s", "String");
		variables.put("d", "Demo2");
		variables.put("c", "char");
		assertEquals(variables,m.getVariables());
		variables.clear();
	}
	
	/**
	 * Check if second test MethodNode was correctly added to the graph.
	 */
	@Test
	public void MethodNodeDemo2Test() {
		ClassNode n = graph.getNode("Demo2");
		HashMap<String,String> variables = new HashMap<String,String>();
		MethodNode m = n.getMethod("this");
		assertFalse(m == null);
		variables.put("i", "int");
		assertEquals(variables,m.getVariables());
		variables.clear();
		
		m = n.getMethod("Demo2( String )");
		assertFalse(m == null);
		variables.put("s", "String");
		assertEquals(variables,m.getVariables());
		variables.clear();
		
		m = n.getMethod("dummymethod( )");
		assertFalse(m == null);
		assertTrue(m.getReturnType().equals("Demo3"));
		assertTrue(m.hasModifier("public"));
		assertEquals(variables,m.getVariables());
		variables.clear();
	}

	/**
	 * Check if second test MethodNode was correctly added to the graph.
	 */
	@Test
	public void MethodNodeDemo3Test() {
		ClassNode n = graph.getNode("Demo3");
		HashMap<String,String> variables = new HashMap<String,String>();
		MethodNode m = n.getMethod("this");
		assertFalse(m != null);
		
		m = n.getMethod("getDemo( )");
		assertFalse(m == null);
		assertEquals(variables,m.getVariables());
		assertTrue(m.getReturnType().equals("String"));
		assertFalse(m.hasModifier("public"));
		assertTrue(m.hasModifier("private"));
	}
}
