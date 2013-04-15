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

import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

/**
 * The the similarity of trees.
 * 
 * @author Arjan
 */
public class ParseTreeSimilarityTest {
	private ParseTree left;
	private ParseTree right;
	
	@Before
	public void setUp() {
		left = createMock(ParseTree.class);
		right = createMock(ParseTree.class);
	}
	
	/**
	 * Test the similarity of a similar parse tree.
	 */
	@Test
	public void similarTest() {
		ParseTreeSimilarity similarity;
		
		// Build left parse tree.
		MemberDeclContext leftChild = createMock(MemberDeclContext.class);
		
		expect(left.getChildCount()).andReturn(1).anyTimes();
		expect(left.getChild(0)).andReturn(leftChild);
		
		expect(leftChild.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(leftChild);
		
		// Build right parse tree.
		MemberDeclContext rightChild = createMock(MemberDeclContext.class);
		
		expect(right.getChildCount()).andReturn(1).anyTimes();
		expect(right.getChild(0)).andReturn(rightChild);
		
		expect(rightChild.getChildCount()).andReturn(0).anyTimes();
		
		replay(right);
		replay(rightChild);
		
		similarity = new ParseTreeSimilarity(left, right);
		
		assertEquals(0,0f, similarity.getSimilarity());
		
		verify(left);
		verify(leftChild);
		verify(right);
		verify(rightChild);
	}
	
	/**
	 * Left tree has no children. Right tree has one child.
	 * 
	 * 0.66 		= 2 x 1 / (2 x 1 + 0 + 1)
	 */
	@Test
	public void different1Test() {
		ParseTreeSimilarity similarity;
		
		// Build left parse tree.
		expect(left.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		
		// Build right parse tree.
		MemberDeclContext rightChild = createMock(MemberDeclContext.class);
		
		expect(right.getChildCount()).andReturn(1).anyTimes();
		expect(right.getChild(0)).andReturn(rightChild);
		
		expect(rightChild.getChildCount()).andReturn(0).anyTimes();
		
		replay(right);
		replay(rightChild);
		
		similarity = new ParseTreeSimilarity(left, right);
		
		assertEquals(0.66f, similarity.getSimilarity(), 0.01);
		
		verify(left);
		verify(right);
		verify(rightChild);
	}
	
	/**
	 * Left tree has no children. Right tree has two children.
	 * 
	 * 0.5 			= 2 x 1 / (2 x 1 + 0 + 2)
	 */
	@Test
	public void different2Test() {
		ParseTreeSimilarity similarity;
		
		// Build left parse tree.
		expect(left.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		
		// Build right parse tree.
		MemberDeclContext right1Child = 
				createMock(MemberDeclContext.class);
		VariableDeclaratorIdContext right2Child = 
				createMock(VariableDeclaratorIdContext.class);
		
		expect(right.getChildCount()).andReturn(2).anyTimes();
		expect(right.getChild(0)).andReturn(right1Child);
		expect(right.getChild(1)).andReturn(right2Child);
		
		expect(right1Child.getChildCount()).andReturn(0).anyTimes();
		expect(right2Child.getChildCount()).andReturn(0).anyTimes();
		
		replay(right);
		replay(right1Child);
		replay(right2Child);
		
		similarity = new ParseTreeSimilarity(left, right);
		
		assertEquals(0.5f, similarity.getSimilarity(), 0.01);
		
		verify(left);
		verify(right);
		verify(right1Child);
		verify(right2Child);
	}
	
	/**
	 * Left tree has 1 child. Right tree has two children.
	 * 
	 * 0.4 			= 2 x 1 / (2 x 1 + 1 + 2)
	 */
	@Test
	public void different3Test() {
		ParseTreeSimilarity similarity;
		
		// Build left parse tree.
		VariableDeclaratorIdContext left1Child = 
				createMock(VariableDeclaratorIdContext.class);
		
		expect(left.getChildCount()).andReturn(1).anyTimes();
		expect(left.getChild(0)).andReturn(left1Child);
		
		expect(left1Child.getChildCount()).andReturn(0).anyTimes();
		
		replay(left);
		replay(left1Child);
		
		// Build right parse tree.
		MemberDeclContext right1Child = 
				createMock(MemberDeclContext.class);
		VariableDeclaratorIdContext right2Child = 
				createMock(VariableDeclaratorIdContext.class);
		
		expect(right.getChildCount()).andReturn(2).anyTimes();
		expect(right.getChild(0)).andReturn(right1Child);
		expect(right.getChild(1)).andReturn(right2Child);
		
		expect(right1Child.getChildCount()).andReturn(0).anyTimes();
		expect(right2Child.getChildCount()).andReturn(0).anyTimes();
		
		replay(right);
		replay(right1Child);
		replay(right2Child);
		
		similarity = new ParseTreeSimilarity(left, right);
		
		assertEquals(0.4f, similarity.getSimilarity(), 0.01);
		
		verify(left);
		verify(left1Child);
		verify(right);
		verify(right1Child);
		verify(right2Child);
	}
}
