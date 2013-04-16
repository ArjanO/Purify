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
package nl.han.ica.ap.purify.module.java.duplicatecode;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the storing of detected clones.
 * 
 * @author Arjan
 */
public class ClonesTest {	
	private Clones clones;
	
	private ParseTree left;
	private ParseTree right;
	
	@Before
	public void setUp() {
		clones = new Clones();
		
		left = createMock(ParseTree.class);
		right = createMock(ParseTree.class);
	}
	
	@Test
	public void addPairToEmptySetTest() {
		clones.addClonePair(left, right);
		
		replay(left);
		replay(right);
		
		assertEquals(1, clones.size());
		
		verify(left);
		verify(right);
	}
	
	/**
	 * Add a tree to the clone set. The clone set contains a subtree of the
	 * tree that is added.
	 * 
	 * Expected: subtree is removed.
	 */
	@Test
	public void addPairToSet1Test() {
		ParseTree subLeft = createMock(ParseTree.class);
		ParseTree subRight = createMock(ParseTree.class);
		
		expect(left.getChildCount()).andReturn(1).anyTimes();
		expect(left.getChild(0)).andReturn(subLeft).anyTimes();
		
		expect(right.getChildCount()).andReturn(1).anyTimes();
		expect(right.getChild(0)).andReturn(subRight).anyTimes();
		
		expect(subLeft.getChildCount()).andReturn(0).anyTimes();
		expect(subRight.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(subLeft);
		replay(right);
		replay(subRight);
		
		clones.addClonePair(subLeft, subRight);
		
		assertEquals(1, clones.size());
		
		clones.addClonePair(left, right);
		
		assertEquals(1, clones.size());
		
		assertTrue(clones.getItem(0).contains(left));
		assertTrue(clones.getItem(0).contains(right));
		
		assertFalse(clones.getItem(0).contains(subLeft));
		assertFalse(clones.getItem(0).contains(subRight));
		
		verify(left);
		verify(subLeft);
		verify(right);
		verify(subRight);
	}
	
	/**
	 * Add a tree to the clone set. The clone set contains a subtree of the
	 * tree that is added. The subtree is also a clone of a other tree.
	 * 
	 * Expected: subtree left is removed.
	 */
	@Test
	public void addPairToSet2Test() {
		ParseTree subLeft = createMock(ParseTree.class);
		ParseTree subRight = createMock(ParseTree.class);
		
		ParseTree otherTree = createMock(ParseTree.class);
		
		expect(left.getChildCount()).andReturn(1).anyTimes();
		expect(left.getChild(0)).andReturn(subLeft).anyTimes();
		
		expect(right.getChildCount()).andReturn(1).anyTimes();
		expect(right.getChild(0)).andReturn(subRight).anyTimes();
		
		expect(subLeft.getChildCount()).andReturn(0).anyTimes();
		expect(subRight.getChildCount()).andReturn(0).anyTimes();
		
		expect(otherTree.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(subLeft);
		replay(right);
		replay(subRight);
		replay(otherTree);
		
		clones.addClonePair(subLeft, subRight);
		clones.addClonePair(subRight, otherTree);
		
		assertEquals(1, clones.size());
		
		clones.addClonePair(left, right);
		
		assertEquals(2, clones.size());
		
		assertEquals(2, clones.getItem(0).size());
		assertEquals(2, clones.getItem(1).size());
		
		verify(left);
		verify(subLeft);
		verify(right);
		verify(subRight);
		verify(otherTree);
	}
	
	/**
	 * Add a tree to the clone set. The clone set contains a subtree of the
	 * tree that is added. The subtree is also a clone of two other trees.
	 * 
	 * Expected: subtree left and right is removed.
	 */
	@Test
	public void addPairToSet3Test() {
		ParseTree subLeft = createMock(ParseTree.class);
		ParseTree subRight = createMock(ParseTree.class);
		
		ParseTree other1Tree = createMock(ParseTree.class);
		ParseTree other2Tree = createMock(ParseTree.class);
		
		expect(left.getChildCount()).andReturn(1).anyTimes();
		expect(left.getChild(0)).andReturn(subLeft).anyTimes();
		
		expect(right.getChildCount()).andReturn(1).anyTimes();
		expect(right.getChild(0)).andReturn(subRight).anyTimes();
		
		expect(subLeft.getChildCount()).andReturn(0).anyTimes();
		expect(subRight.getChildCount()).andReturn(0).anyTimes();
		
		expect(other1Tree.getChildCount()).andReturn(0).anyTimes();
		expect(other2Tree.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(subLeft);
		replay(right);
		replay(subRight);
		replay(other1Tree);
		replay(other2Tree);
		
		clones.addClonePair(subLeft, subRight);
		clones.addClonePair(subRight, other1Tree);
		clones.addClonePair(other1Tree, other2Tree);
		
		assertEquals(1, clones.size());
		assertEquals(4, clones.getItem(0).size());
		
		clones.addClonePair(left, right);
		
		assertEquals(2, clones.size());
		
		assertEquals(2, clones.getItem(0).size());
		assertEquals(2, clones.getItem(1).size());
		
		if (clones.getItem(0).contains(left)) {
			assertTrue(clones.getItem(0).contains(left));
			assertTrue(clones.getItem(0).contains(right));
		} else if (clones.getItem(0).contains(other1Tree)) {
			assertTrue(clones.getItem(0).contains(other1Tree));
			assertTrue(clones.getItem(0).contains(other2Tree));
		} else if (clones.getItem(1).contains(left)) {
			assertTrue(clones.getItem(1).contains(left));
			assertTrue(clones.getItem(1).contains(right));
		} else if (clones.getItem(1).contains(other1Tree)) {
			assertTrue(clones.getItem(1).contains(other1Tree));
			assertTrue(clones.getItem(1).contains(other2Tree));
		} else {
			fail("Clones does not contains trees");
		}
		
		verify(left);
		verify(subLeft);
		verify(right);
		verify(subRight);
		verify(other1Tree);
		verify(other2Tree);
	}
}