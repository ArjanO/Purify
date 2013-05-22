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

import org.antlr.v4.runtime.tree.ParseTree;

import nl.han.ica.ap.purify.language.java.JavaParser.BlockContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParametersContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;

/**
 * Method tools.
 * 
 * @author Arjan
 */
public class MethodUtil {
	private MethodUtil() {
	}
	
	/**
	 * Get the parameter index of a parameter.
	 * 
	 * @param param Parameter.
	 * @return Index of the parameter.
	 */
	public static int getParameterIndex(ParseTree param) {
		if (param == null) {
			return 0;
		}
		
		if (param.getParent() instanceof FormalParametersContext) {
			return 0;
		}
		
		if (param instanceof FormalParameterDeclsContext) {
			return getParameterIndex(param.getParent()) + 1;
		}
		
		return getParameterIndex(param.getParent());
	}
	
	/**
	 * Get the name of the method.
	 * 
	 * @param ctx MemberDelcContext.
	 * @return Method name
	 */
	public static String getMethodName(MemberDeclContext ctx) {
		if (ctx.voidMethodDeclaratorRest() != null) {
			if (ctx.Identifier() != null) {
				return ctx.Identifier().getText();
			}
		} else if (ctx.memberDeclaration() != null &&
				ctx.memberDeclaration().methodDeclaration() != null) {
			MethodDeclarationContext method;
			
			method = ctx.memberDeclaration().methodDeclaration();
			
			if (method.Identifier() != null) {
				return method.Identifier().getText();
			}
		}
		
		return "";
	}
	
	/**
	 * Get the local variables in the method. This are also the parameters.
	 * 
	 * @param ctx Method
	 * @return TreeSet with method variables.
	 */
	public static TreeSet<String> getLocalVariables(MemberDeclContext ctx) {
		LocalVariableVisitor visitor = new LocalVariableVisitor();
		visitor.visit(ctx);
		
		return visitor.getLocalVariables();
	}
	
	/**
	 * Check if the parse tree is a method body.
	 * 
	 * @param tree Parse tree
	 * @return true if the parse tree is a method body.
	 */
	public static boolean isParseTreeMethodBody(ParseTree tree) {
		if (tree == null) {
			throw new NullPointerException();
		}
		
		if (tree instanceof BlockContext) {
			BlockContext block = (BlockContext)tree;
			
			if (block.parent != null && 
					block.parent instanceof MethodBodyContext) {
				return true;
			}
		}
		
		return false;
	}
}
