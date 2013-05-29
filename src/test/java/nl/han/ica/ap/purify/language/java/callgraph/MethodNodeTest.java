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

import org.junit.Before;
import org.junit.Test;

/**
 * Tests methods of class MethodNode.
 * 
 * @author Tim
 */
public class MethodNodeTest {
	private MethodNode m;
	
	/**
	 * Initialize MethodNode m with classID "testclass", methodID "testmethod"
	 * and modifiers private static void.
	 */
	@Before
	public void before() {
		MethodInfo info = new MethodInfo();
		info.modifiers = new ArrayList<String>();
		info.modifiers.add("void");
		info.modifiers.add("static");
		info.modifiers.add("private");
		m = new MethodNode("testclass","testmethod", info);
	}
	
	/**
	 * Expect m.getMethodID() to return "testmethod".
	 */
	@Test
	public void getMethodIDTest() {
		assertEquals("testmethod", m.getMethodID());
	}
	
	/**
	 * Expect m.getClassID() to return "testclass".
	 */
	@Test
	public void getClassIDTest() {
		assertEquals("testclass", m.getClassID());
	}

	/**
	 * Expect m.getReturnType() to return "void".
	 */
	@Test
	public void getReturnTypeTest() {
		assertEquals("void", m.getReturnType());
	}
	
	/**
	 * Expect m.hasModifier("static") to return true.
	 * Expect m.hasModifier("private") to return true.
	 * Expect m.hasModifier("void") to return false since it is the return type.
	 */
	@Test
	public void hasModifierTest() {
		assertTrue(m.hasModifier("static"));
		assertTrue(m.hasModifier("private"));
		assertFalse(m.hasModifier("void"));
	}
	
	/**
	 * Expect m.getVariables to return the same HashMap as was used to set it.
	 */
	@Test
	public void setVariablesTest() {
		HashMap<String, String> variables = new HashMap<String,String>();
		variables.put("i", "int");
		m.setVariables(variables);
		assertEquals(variables,m.getVariables());
		m.getVariables().clear();
	}
	
	/**
	 * Expect m.getLocalVariable("i") to return type "int".
	 * Expect m.getLocalVariable("i") to return null.
	 */
	@Test
	public void getLocalVariableTest() {
		HashMap<String, String> variables = new HashMap<String,String>();
		variables.put("i", "int");
		m.setVariables(variables);
		assertEquals("int",m.getLocalVariable("i"));
		assertEquals(null,m.getLocalVariable("d"));
		m.getVariables().clear();
	}
}
