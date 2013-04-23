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
package nl.han.ica.ap.purify.language.java.callgraph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests methods for class CallGraph.
 * 
 * @author Tim
 */
public class CallGraphTest {
	CallGraph graph;
	
	
	public CallGraphTest() {
		graph = new CallGraph();
	}
	
	/**
	 * Tests if a node is added to the graph.
	 */
	@Test
	public void addNodeTest() {
		HashMap<String,ArrayList<String>> methods = new HashMap<String, ArrayList<String>>();
		ArrayList<String> modifiers = new ArrayList<String>();
		modifiers.add("void");
		modifiers.add("private");
		methods.put("testmethod", modifiers);
		graph.addNode("testclass", methods);
		assertTrue(graph.getNode("testclass") != null);
		assertTrue(graph.getNode("testclass").getAllMethods().size() == 1);
	}
	
	/**
	 * Tests if getNode(String classID) returns the proper ClassNode.
	 */
	@Test
	public void getNodeTest() {
		HashMap<String,ArrayList<String>> methods = new HashMap<String, ArrayList<String>>();
		graph.addNode("testclass", methods);
		assertTrue(graph.getNode("testclass") != null);
		assertTrue(graph.getNode("t") == null);
	}
	
	/**
	 * Tests if edge is added to the graph.
	 */
	@Test
	public void addEdgeSuccesTest() {
		HashMap<String,ArrayList<String>> methods = new HashMap<String, ArrayList<String>>();
		ArrayList<String> modifiers = new ArrayList<String>();
		modifiers.add("void");
		modifiers.add("private");
		methods.put("testmethod", modifiers);
		graph.addNode("testclass", methods);
		graph.addNode("testclass2", methods);
		graph.addEdge("testclass", "testmethod", "testclass2", "testmethod");
		assertTrue(graph.getEdges().size() == 1);
	}
	
	/**
	 * Tests if getMethodsWithModifier(String modifier) returns all MethodNodes with specified modifier.
	 */
	@Test
	public void getMethodsWithModifierTest() {
		HashMap<String,ArrayList<String>> methods = new HashMap<String, ArrayList<String>>();
		ArrayList<String> modifiers = new ArrayList<String>();
		modifiers.add("void");
		modifiers.add("private");
		methods.put("testmethod", modifiers);
		graph.addNode("testclass", methods);
		graph.addNode("testclass2", methods);
		assertTrue(graph.getAllMethodsWithModifier("private").size() == 2);
		assertTrue(graph.getAllMethodsWithModifier("public").size() == 0);
	}
	
	/**
	 * Tests if getAllMethods() returns all MethodNodes.
	 */
	@Test
	public void getAllMethodsTest() {
		HashMap<String,ArrayList<String>> methods = new HashMap<String, ArrayList<String>>();
		ArrayList<String> modifiers = new ArrayList<String>();
		modifiers.add("void");
		modifiers.add("private");
		methods.put("testmethod", modifiers);
		graph.addNode("testclass", methods);
		graph.addNode("testclass2", methods);
		assertTrue(graph.getAllMethods().size() == 2);
	}
}
