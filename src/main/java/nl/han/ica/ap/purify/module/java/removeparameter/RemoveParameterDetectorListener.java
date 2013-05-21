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

import nl.han.ica.ap.purify.language.java.JavaBaseListener;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionPrimaryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PrimaryContext;

/**
 * Detect unused method parameters.
 * 
 * @author Arjan
 */
public class RemoveParameterDetectorListener extends JavaBaseListener {
	private Method currentMethod;
	private List<Method> detected;
	
	/**
	 * Detect unused method parameters.
	 */
	public RemoveParameterDetectorListener() {
		detected = new ArrayList<Method>();
	}
	
	/**
	 * Get all the methods with undetected parameters.
	 * 
	 * @return List with methods.
	 */
	public List<Method> getDetected() {
		return detected;
	}
	
	/**
	 * When something in the class is declared this method is called.
	 */
	@Override
	public void enterMemberDecl(MemberDeclContext ctx) {		
		/*
		 * voidMethodDeclaratorRest() 
		 * 		= void myFunc { }
		 * 
		 * memberDeclaration().methodDeclaration() 
		 * 		= Object myFunc { return null; }
		 */
		if (ctx.voidMethodDeclaratorRest() != null) {
			if (ctx.Identifier() != null) {
				String name = ctx.Identifier().getText();
			
				currentMethod = new Method(name);
			}
		} else if (ctx.memberDeclaration() != null &&
				ctx.memberDeclaration().methodDeclaration() != null) {
			MethodDeclarationContext method;
			
			method = ctx.memberDeclaration().methodDeclaration();
			
			if (method.Identifier() != null) {
				String name = method.Identifier().getText();
				
				currentMethod = new Method(name);
			}
		}
	}
	
	/**
	 * Called when the member declaration is node is leaved.	
	 */
	@Override
	public void exitMemberDecl(MemberDeclContext ctx) {
		if (currentMethod != null) {
			detected.add(currentMethod);
			currentMethod = null;
		}
	}
	
	/**
	 * Called for every parameter in the current method.
	 */
	@Override
	public void enterFormalParameterDeclsRest(
			FormalParameterDeclsRestContext ctx) {
		currentMethod.addParameter(new Parameter(ctx));
	}
	
	/**
	 * Called when a candidate variable (or parameter) is used in an 
	 * expression.
	 */
	@Override
	public void enterExpressionPrimary(ExpressionPrimaryContext ctx) {
		if (ctx.primary() != null && 
				ctx.primary() instanceof PrimaryContext) { 
			String name = ctx.primary().getText();
			
			if (name != null && currentMethod != null) {
				currentMethod.usedVariable(name);
			}
		}
	}
}
