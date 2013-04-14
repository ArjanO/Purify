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
package nl.han.ica.ap.purify.language.java.util;

import java.util.TreeSet;

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

/**
 * Get all the local variables in the parse tree.
 * 
 * @author Arjan
 */
class LocalVariableVisitor extends JavaBaseVisitor<Void> {
	private TreeSet<String> localVariables;
	
	public LocalVariableVisitor() {
		localVariables = new TreeSet<String>();
	}
	
	/**
	 * Get the local variables found.
	 * 
	 * @return Local variables.
	 */
	public TreeSet<String> getLocalVariables() {
		return localVariables;
	}
	
	/**
	 * If a variable is declared (parameter or in the method) this method is
	 * called.
	 */
	@Override
	public Void visitVariableDeclaratorId(VariableDeclaratorIdContext ctx) {
		if (ctx.Identifier() != null && ctx.Identifier().toString() != null) {
			localVariables.add(ctx.Identifier().getText());
		}
		
		return null;
	}
}
