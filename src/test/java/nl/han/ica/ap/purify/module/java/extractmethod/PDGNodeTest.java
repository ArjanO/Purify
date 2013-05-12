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
package nl.han.ica.ap.purify.module.java.extractmethod;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link PDGNode}
 * 
 * @author Arjan
 */
public class PDGNodeTest {
	private PDGNode node;
	private ParseTree parseTree;
	
	@Before
	public void before() {
		parseTree = createMock(ParseTree.class);
		
		replay(parseTree);
		
		node = new PDGNode(parseTree);
	}
	
	@After
	public void after() {
		verify(parseTree);
	}
	
	@Test
	public void getTreeTest() {
		assertEquals(parseTree, node.getParseTree());
	}
	
	@Test
	public void addControlDependenceTest() {
		PDGNode node1 = new PDGNode(parseTree);
		PDGNode node2 = new PDGNode(parseTree);
		
		assertEquals(0, node.getControlDependencies().size());
		
		node.addControlDependence(node1);
		node.addControlDependence(node2);
		
		assertEquals(2, node.getControlDependencies().size());
		assertTrue(node.getControlDependencies().contains(node1));
		assertTrue(node.getControlDependencies().contains(node2));
	}
	
	@Test
	public void nameTest() {
		node.setName("3");
		
		assertEquals("3", node.getName());
	}
}