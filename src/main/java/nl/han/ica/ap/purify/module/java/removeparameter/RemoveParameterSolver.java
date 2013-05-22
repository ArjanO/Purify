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
package nl.han.ica.ap.purify.module.java.removeparameter;

import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.modles.ISolver;
import nl.han.ica.ap.purify.modles.SourceFile;

/**
 * Remove detected unused parameters.
 * 
 * @author Arjan
 */
public class RemoveParameterSolver implements ISolver {
	@Override
	public void solve(SourceFile file) {
		for (int i = file.getIssuesSize() - 1; i >= 0; i--) {
			if (file.getIssue(i) instanceof RemoveParameterIssue) {
				solveIssue(file, (RemoveParameterIssue)file.getIssue(i));
			}
		}
	}
	
	private void solveIssue(SourceFile file, RemoveParameterIssue issue) {
		for (FormalParameterDeclsRestContext param : issue.getParameters()) {
			FormalParameterDeclsContext parent = 
					(FormalParameterDeclsContext)param.parent;
			
			if (parent.parent instanceof FormalParameterDeclsRestContext) {
				FormalParameterDeclsRestContext parentPartent =
					(FormalParameterDeclsRestContext)parent.parent;
				
				removeSeparator(file, parentPartent);
			} else {
				removeSeparator(file, param);
			}
			
			file.getRewriter().delete(parent.start);
			file.getRewriter().delete(param.start);
		}
	}
	
	/**
	 * Remove separator (,).
	 * 
	 * @param file Source file of the parse tree.
	 * @param context Parameter with ,
	 */
	private void removeSeparator(SourceFile file,
			FormalParameterDeclsRestContext context) {
		for (int i = context.getChildCount() - 1; i >= 0; i--) {
			if (context.getChild(i) instanceof TerminalNodeImpl &&
					context.getChild(i).getText().equals(",")) {
				TerminalNodeImpl node = 
						(TerminalNodeImpl)context.getChild(i);
				
				 file.getRewriter().delete(node.getPayload());
			}
		}
	}
}
