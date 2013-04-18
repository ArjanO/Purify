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
 * Tests methods of class ClassNodeTest.
 * 
 * @author Tim
 */
public class ClassNodeTest {

	ClassNode n;
	
	/**
	 * Initializes ClassNode n with classID "testclass".
	 */
	public ClassNodeTest() {
		n = new ClassNode("testclass");
	}
	
	/**
	 * Checks if n.getClassID() returns "testclass". 
	 */
	@Test
	public void getClassIDTest() {
		assertEquals("testclass", n.getClassID());
	}

	/**
	 * Checks if MethodNode is added to the methods list. 
	 */
	@Test
	public void addMethodNodeTest() {
		ArrayList<String> modifiers = new ArrayList<String>();
		
		modifiers.add("String");
		modifiers.add("public");
		n.addMethodNode("testmethod", modifiers);
		assertEquals(1, n.getAllMethods().size());
	}

	/**
	 * Checks if n.getGetMethod(String methodID) returns the proper MethodNode. 
	 */
	@Test
	public void getMethodTest() {
		ArrayList<String> modifiers = new ArrayList<String>();
		
		modifiers.add("String");
		modifiers.add("public");
		n.addMethodNode("testmethod", modifiers);
		assertTrue(n.getMethod("testmethod") != null);
		assertTrue(n.getMethod("nomethod") == null);
	}
	
	/**
	 * Checks if variables are mapped to the proper MethodNode.
	 */
	@Test
	public void mapVariablesTest() {
		ArrayList<String> modifiers = new ArrayList<String>();
		HashMap<String,HashMap<String,String>> variables = new HashMap<String,HashMap<String,String>>();
		modifiers.add("String");
		modifiers.add("public");
		n.addMethodNode("testmethod", modifiers);
		HashMap<String, String> variable = new HashMap<String, String>();
		variable.put("i", "int");
		variables.put("testmethod", variable);
		n.mapVariables(variables);
		assertEquals("int",n.getVariableType("testmethod", "i"));
		assertEquals(null,n.getVariableType("this", "s"));
		assertEquals(null,n.getGlobalVariables());
		
		n.addMethodNode("this", new ArrayList<String>());
		variable.put("s", "String");
		variables.put("this", variable);
		n.mapVariables(variables);
		assertEquals("int",n.getVariableType("testmethod", "i"));
		assertEquals("String",n.getVariableType("this", "s"));
		assertTrue(n.getGlobalVariables() != null);
	}
}
