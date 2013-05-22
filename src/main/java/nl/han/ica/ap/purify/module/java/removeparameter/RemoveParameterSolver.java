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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import nl.han.ica.ap.purify.App;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionListContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionMethodExpressionListContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.callgraph.Edge;
import nl.han.ica.ap.purify.language.java.callgraph.MethodNode;
import nl.han.ica.ap.purify.language.java.util.CallGraphUtil;
import nl.han.ica.ap.purify.language.java.util.MethodUtil;
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
		List<Integer> remove = new ArrayList<Integer>();
		
		for (FormalParameterDeclsRestContext param : issue.getParameters()) {
			FormalParameterDeclsContext parent = 
					(FormalParameterDeclsContext)param.parent;
			
			if (parent.parent instanceof FormalParameterDeclsRestContext) {
				removeSeparator(file, 
						(FormalParameterDeclsRestContext)parent.parent);
			} else {
				removeSeparator(file, param);
			}
			
			file.getRewriter().delete(parent.start);
			file.getRewriter().delete(param.start);
			
			remove.add(MethodUtil.getParameterIndex(param));
		}
		
		MethodNode methodNode = CallGraphUtil.getMethodNode(issue.getMethod());
		if (methodNode != null) {
			for (Edge e : App.getCallGraph().getEdges()) {
				if (e.target == methodNode) {
					updateMethodCall(e.getSourceFile(), e.getStatement(), 
							remove);
				}
			}
		}
	}
	
	/**
	 * Remove arguments form the call to the method.
	 * 
	 * @param file Source file form the calling statement.
	 * @param statement Statement that cals.
	 * @param remove Index of the parameter that is removed.
	 */
	private void updateMethodCall(
			SourceFile file,
			ExpressionMethodExpressionListContext statement,
			List<Integer> remove) {
		if (statement.expressionList() == null) {
			return;
		}
		
		ExpressionListContext context = statement.expressionList();
		
		int iCount = context.getChildCount();
		int iParamId = 0;
		for (int i = 0; i < iCount; i++) {
			if (context.getChild(i) instanceof ExpressionContext) {
				if (remove.contains(iParamId)) {
					ExpressionContext argument = 
							(ExpressionContext)context.getChild(i);
				
					file.getRewriter().delete(argument.start);
					
					removeCallSeparator(file, statement.expressionList(), i);
				}
				
				iParamId++;
			}
		}
	}
	
	/**
	 * Remove the separator (,) between arguments. 
	 *  
	 * @param file Source file to edit.
	 * @param context ExpressionList context.
	 * @param index Parameter index of the argument ("a", "b") "a" = 0, "b" = 1
	 */
	private void removeCallSeparator(SourceFile file, 
			ExpressionListContext context, int index) {
		ParseTree item = null;
		
		// Remove separator after argument.
		if (index == 0 && context.getChildCount() >= index + 1) {
			item = context.getChild(index + 1);
		} else if (context.getChildCount() >= index - 1) {
			item = context.getChild(index - 1);
		}
		
		if (item instanceof TerminalNodeImpl && 
				item.getText().equals(",")) {
			file.getRewriter().delete(((TerminalNodeImpl)item).getPayload());
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
