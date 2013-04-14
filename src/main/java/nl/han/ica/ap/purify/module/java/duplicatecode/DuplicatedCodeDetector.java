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

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;

/**
 * Detect duplicated code. A visitor is used because the visit is handled 
 * self for a method's body.  
 * 
 * @author Arjan
 */
public class DuplicatedCodeDetector extends JavaBaseVisitor<Void> {
	/**
	 * Duplicated code detector.
	 */
	public DuplicatedCodeDetector() {
	}
	
	/**
	 * Called when a method body is the visited. This is the point where 
	 * the visit is handled by this class. This is done by not calling 
	 * {@code super.visitMethodBody(ctx);} or {@code visitChildren(ctx);}
	 */
	@Override
	public Void visitMethodBody(MethodBodyContext ctx) {
		return null; // The type Void require a return. 
	}
	
	/**
	 * Get the tree mass this is the number nodes.
	 * 
	 * @param tree ParseTree
	 * @return Number of nodes in the tree.
	 */
	private int mass(ParseTree tree) {		
		int iChilderen = tree.getChildCount();
		
		if (iChilderen > 0) {
			for (int i = tree.getChildCount() - 1; i >= 0; i--) {
				iChilderen += mass(tree.getChild(i));
			}
		}
		
		return iChilderen;
	}
}
