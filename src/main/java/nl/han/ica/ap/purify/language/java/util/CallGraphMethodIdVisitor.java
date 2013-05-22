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

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;

/**
 * Visitor to build method id for the call graph.
 * 
 * @author Arjan
 */
class CallGraphMethodIdVisitor extends JavaBaseVisitor<Void> {
	private StringBuilder sb;
	
	public CallGraphMethodIdVisitor() {
		sb = new StringBuilder();
	}
	
	public String getMethodId() {
		return sb.toString();
	}
	
	@Override
	public Void visitMemberDecl(MemberDeclContext ctx) {
		String name = "";
		
		if (ctx.voidMethodDeclaratorRest() != null) {
			if (ctx.Identifier() != null) {
				name = ctx.Identifier().getText();
			}
		} else if (ctx.memberDeclaration() != null &&
				ctx.memberDeclaration().methodDeclaration() != null) {
			MethodDeclarationContext method;
			
			method = ctx.memberDeclaration().methodDeclaration();
			
			if (method.Identifier() != null) {
				name = method.Identifier().getText();
			}
		}
		
		sb.append(name);
		
		sb.append("( ");
		super.visitMemberDecl(ctx);
		sb.append(")");
		
		return null;
	}
	
	@Override
	public Void visitMethodBody(MethodBodyContext ctx) {
		return null; // Don't visit childeren.
	}
	
	@Override
	public Void visitFormalParameterDecls(FormalParameterDeclsContext ctx) {
		if (ctx.type() != null) {
			if (ctx.type().classOrInterfaceType() != null) {
				sb.append(ctx.type().classOrInterfaceType().getText() + " ");
			} else if (ctx.type().primitiveType() != null) {
				sb.append(ctx.type().primitiveType().getText() + " ");
			}
		}
		
		return super.visitFormalParameterDecls(ctx);
	}
}
