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

import org.antlr.v4.runtime.tree.ParseTree;

import nl.han.ica.ap.purify.App;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.NormalClassDeclarationContext;
import nl.han.ica.ap.purify.language.java.callgraph.ClassNode;
import nl.han.ica.ap.purify.language.java.callgraph.MethodNode;

/**
 * Tools for the call graph.
 * 
 * @author Arjan
 */
public class CallGraphUtil {
	private CallGraphUtil() {
	}
	
	/**
	 * Get call graph method id.
	 * 
	 * @param ctx Method
	 * @return String with method id for the call graph.
	 */
	public static String getCallGraphMethodId(MemberDeclContext ctx) {
		CallGraphMethodIdVisitor visitor = new CallGraphMethodIdVisitor();
		visitor.visit(ctx);
		
		return visitor.getMethodId();
	}
	
	/**
	 * Get the method node by the method.
	 * 
	 * @param context Method parse tree.
	 * @return MethodNode
	 * @throws If context is null. 
	 */
	public static MethodNode getMethodNode(MemberDeclContext context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		
		String className = getClassName(context);
		String methodName = CallGraphUtil.getCallGraphMethodId(context);
		
		if (className != null && methodName != null) {
			ClassNode classNode = App.getCallGraph().getNode(className);
			
			if (classNode != null) {
				return classNode.getMethod(methodName);
			}
		}
		
		return null;
	}
	
	/**
	 * Temporary method to get the class.
	 * 
	 * @param tree ParseTree node
	 * @return Class name if found.
	 */
	private static String getClassName(ParseTree tree) {
		if (tree instanceof NormalClassDeclarationContext) {
			return ((NormalClassDeclarationContext)tree).Identifier().getText();
		}
		
		if (tree.getParent() == null) {
			return null;
		}
		
		return getClassName(tree.getParent());
	}
}
