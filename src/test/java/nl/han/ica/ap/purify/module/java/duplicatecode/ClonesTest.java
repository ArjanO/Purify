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
	
	private ParseTree leftTree;
	private ParseTree rightTree;
	
	private Clone left;
	private Clone right;
	
	@Before
	public void setUp() {
		clones = new Clones();
		
		left = createMock(Clone.class);
		right = createMock(Clone.class);
		
		leftTree = createMock(ParseTree.class);
		rightTree = createMock(ParseTree.class);
		
		expect(left.getParseTree()).andReturn(leftTree).anyTimes();
		expect(right.getParseTree()).andReturn(rightTree).anyTimes();
	}
	
	@Test
	public void addPairToEmptySetTest() {
		clones.addClonePair(left, right);
		
		replay(left);
		replay(right);
		
		replay(leftTree);
		replay(rightTree);
		
		assertEquals(1, clones.size());
		
		verify(left);
		verify(right);
		
		verify(leftTree);
		verify(rightTree);
	}
	
	/**
	 * Add a tree to the clone set. The clone set contains a subtree of the
	 * tree that is added.
	 * 
	 * Expected: subtree is removed.
	 */
	@Test
	public void addPairToSet1Test() {
		Clone subLeft = createMock(Clone.class);
		Clone subRight = createMock(Clone.class);
		
		ParseTree subLeftTree = createMock(ParseTree.class);
		ParseTree subRightTree = createMock(ParseTree.class);
		
		expect(subLeft.getParseTree()).andReturn(subLeftTree).anyTimes();
		expect(subRight.getParseTree()).andReturn(subRightTree).anyTimes();
		
		expect(leftTree.getChildCount()).andReturn(1).anyTimes();
		expect(leftTree.getChild(0)).andReturn(subLeftTree).anyTimes();
		
		expect(rightTree.getChildCount()).andReturn(1).anyTimes();
		expect(rightTree.getChild(0)).andReturn(subRightTree).anyTimes();
		
		expect(subLeftTree.getChildCount()).andReturn(0).anyTimes();
		expect(subRightTree.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(leftTree);
		replay(subLeft);
		replay(subLeftTree);
		replay(right);
		replay(rightTree);
		replay(subRight);
		replay(subRightTree);
		
		clones.addClonePair(subLeft, subRight);
		
		assertEquals(1, clones.size());
		
		clones.addClonePair(left, right);
		
		assertEquals(1, clones.size());
		
		assertTrue(clones.getItem(0).contains(left));
		assertTrue(clones.getItem(0).contains(right));
		
		assertFalse(clones.getItem(0).contains(subLeftTree));
		assertFalse(clones.getItem(0).contains(subRightTree));
		
		verify(left);
		verify(leftTree);
		verify(subLeftTree);
		verify(subLeft);
		verify(right);
		verify(rightTree);
		verify(subRightTree);
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
		Clone subLeft = createMock(Clone.class);
		Clone subRight = createMock(Clone.class);
		
		ParseTree subLeftTree = createMock(ParseTree.class);
		ParseTree subRightTree = createMock(ParseTree.class);
		
		Clone other = createMock(Clone.class);
		ParseTree otherTree = createMock(ParseTree.class);
		
		expect(subLeft.getParseTree()).andReturn(subLeftTree).anyTimes();
		expect(subRight.getParseTree()).andReturn(subRightTree).anyTimes();
		expect(other.getParseTree()).andReturn(otherTree).anyTimes();
		
		expect(leftTree.getChildCount()).andReturn(1).anyTimes();
		expect(leftTree.getChild(0)).andReturn(subLeftTree).anyTimes();
		
		expect(rightTree.getChildCount()).andReturn(1).anyTimes();
		expect(rightTree.getChild(0)).andReturn(subRightTree).anyTimes();
		
		expect(subLeftTree.getChildCount()).andReturn(0).anyTimes();
		expect(subRightTree.getChildCount()).andReturn(0).anyTimes();
		
		expect(otherTree.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(leftTree);
		replay(subLeft);
		replay(subLeftTree);
		replay(right);
		replay(rightTree);
		replay(subRight);
		replay(subRightTree);
		replay(other);
		replay(otherTree);
		
		clones.addClonePair(subLeft, subRight);
		clones.addClonePair(subRight, other);
		
		assertEquals(1, clones.size());
		
		clones.addClonePair(left, right);
		
		assertEquals(2, clones.size());
		
		assertEquals(2, clones.getItem(0).size());
		assertEquals(2, clones.getItem(1).size());
		
		verify(left);
		verify(leftTree);
		verify(subLeft);
		verify(subLeftTree);
		verify(right);
		verify(rightTree);
		verify(subRight);
		verify(subRightTree);
		verify(other);
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
		Clone subLeft = createMock(Clone.class);
		Clone subRight = createMock(Clone.class);
		
		ParseTree subLeftTree = createMock(ParseTree.class);
		ParseTree subRightTree = createMock(ParseTree.class);
		
		Clone other1 = createMock(Clone.class);
		Clone other2 = createMock(Clone.class);
		
		ParseTree other1Tree = createMock(ParseTree.class);
		ParseTree other2Tree = createMock(ParseTree.class);
		
		expect(subLeft.getParseTree()).andReturn(subLeftTree).anyTimes();
		expect(subRight.getParseTree()).andReturn(subRightTree).anyTimes();
		
		expect(other1.getParseTree()).andReturn(other1Tree).anyTimes();
		expect(other2.getParseTree()).andReturn(other2Tree).anyTimes();
		
		expect(leftTree.getChildCount()).andReturn(1).anyTimes();
		expect(leftTree.getChild(0)).andReturn(subLeftTree).anyTimes();
		
		expect(rightTree.getChildCount()).andReturn(1).anyTimes();
		expect(rightTree.getChild(0)).andReturn(subRightTree).anyTimes();
		
		expect(subLeftTree.getChildCount()).andReturn(0).anyTimes();
		expect(subRightTree.getChildCount()).andReturn(0).anyTimes();
		
		expect(other1Tree.getChildCount()).andReturn(0).anyTimes();
		expect(other2Tree.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(leftTree);
		replay(subLeft);
		replay(subLeftTree);
		replay(right);
		replay(rightTree);
		replay(subRight);
		replay(subRightTree);
		replay(other1);
		replay(other1Tree);
		replay(other2);
		replay(other2Tree);
		
		clones.addClonePair(subLeft, subRight);
		clones.addClonePair(subRight, other1);
		clones.addClonePair(other1, other2);
		
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
		verify(leftTree);
		verify(subLeft);
		verify(subLeftTree);
		verify(right);
		verify(rightTree);
		verify(subRight);
		verify(subRightTree);
		verify(other1);
		verify(other1Tree);
		verify(other2);
		verify(other2Tree);
	}
}
