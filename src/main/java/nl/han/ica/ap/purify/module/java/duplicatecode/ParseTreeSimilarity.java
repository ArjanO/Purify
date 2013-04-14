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

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Compare the similarity of two parse trees
 * 
 * @author Arjan
 */
class ParseTreeSimilarity {
	private int sharedNodes;
	private int leftTreeDifferentNodes;
	private int rightTreeDifferentNodes;
	
	/**
	 * Get the similarity of the left and right parse tree.
	 * 
	 * @param left Left parse tree.
	 * @param right Right parse tree.
	 */
	public ParseTreeSimilarity(ParseTree left, ParseTree right) {
		sharedNodes = 0;
		leftTreeDifferentNodes = 0;
		rightTreeDifferentNodes = 0;
		
		compareTree(left, right);
	}
	
	public float getSimilarity() {
		/*
		 * Similarity = 2 x S /  (2 x S + L + R)
		 * where: 	S = number of shared nodes 
		 * 			L = number of different nodes in sub-tree 1
		 * 			R = number of different nodes in sub-tree 2
		 * 
		 * Source: 	Clone Detection Using Abstract Syntax Trees
		 * 
		 * 			Ira D. Baxter, Andrew Yahin, Leonardo Moura, 
		 * 			Marcelo Sant’Anna, Lorraine Bier
		 */
		
		float fS = (float)sharedNodes;
		float fL = (float)leftTreeDifferentNodes;
		float fR = (float)rightTreeDifferentNodes;
		
		return (2 * fS) / (2 * fS + fL + fR);
	}
	
	/**
	 * Compare the parse trees
	 * 
	 * @param left Left parse tree
	 * @param right Right parse tree
	 */
	private void compareTree(ParseTree left, ParseTree right) {
		if (left == null && right == null) {
			return;
		}
		
		if (left == null) {
			leftTreeDifferentNodes += 1;
		} else if (right == null) {
			rightTreeDifferentNodes += 1;
		} else if (left.getClass().getName()
				.equals(right.getClass().getName())) {
			sharedNodes += 1;
		} else {
			leftTreeDifferentNodes += 1;
			rightTreeDifferentNodes += 1;
		}
		
		compareChildren(left, right);
	}
	
	/**
	 * Compare the children.
	 * 
	 * @param left Left parse tree
	 * @param right Right parse tree
	 */
	private void compareChildren(ParseTree left, ParseTree right) {
		int leftCount = 0;
		int rightCount = 0;
		
		if (left != null) {
			leftCount = left.getChildCount();
		}
		
		if (right != null) {
			rightCount = right.getChildCount();
		}
		
		for (int i = Math.max(leftCount, rightCount); i >= 0; i--) {
			ParseTree leftSubtree = null;
			ParseTree rightSubtree = null;
			
			if (i < leftCount) {
				leftSubtree = left.getChild(i);
			}
			
			if (i < rightCount) {
				rightSubtree = right.getChild(i);
			}
			
			compareTree(leftSubtree, rightSubtree);
		}
	}
}
