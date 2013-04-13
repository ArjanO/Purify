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

import java.util.TreeSet;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;

/**
 * Get the hash of a parse tree. 
 * 
 * @author Arjan
 */
public class HashVisitor extends JavaBaseVisitor<Integer> {
	private static final int PRIME = 31;
	
	private TreeSet<String> localVariables;
	
	/**
	 * Get the hash of a method (subtree) parse tree.
	 * 
	 * @param localVariables local variables in the method.
	 */
	public HashVisitor(TreeSet<String> localVariables) {
		this.localVariables = localVariables;
	}
	
	/**
	 * The visit returns a default value if no method returns anything else.
	 */
	@Override
	protected Integer defaultResult() {
		return null;
	}
	
	@Override
	public Integer visitChildren(RuleNode arg0) {
		int iHash = 1;
		boolean bVisit = false;
		
		for (int i = arg0.getChildCount() - 1; i >= 0; i--) {
			Integer iVisit = visit(arg0.getChild(i));
			
			// If the visit resulted no hash ignore the hash.
			if (iVisit != null) {
				iHash = iHash * PRIME + iVisit;
				bVisit = true;
			}
		}
		
		if (bVisit) {
			return iHash;
		} else {
			return defaultResult(); // No result.
		}
	}
	
	/**
	 * Every node that is visited is seen by this method.
	 */
	@Override
	public Integer visit(ParseTree tree) {
		/*
		 * Call for example visitVariableDeclaratorId if the type of tree is
		 * VariableDeclaratorId.
		 */
		Integer result = super.visit(tree);
		
		if (result != null) {
			return result;
		}
		
		// There is no hash. Try to get a hash code from the ParseTree node.
		int iHash = HashCode.getHashCode(tree);
		
		if (iHash != -1) {
			return iHash;
		}
		
		return defaultResult();
	}
}
