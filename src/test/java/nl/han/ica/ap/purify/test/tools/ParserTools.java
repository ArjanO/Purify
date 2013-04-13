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
package nl.han.ica.ap.purify.test.tools;

import java.io.IOException;
import java.io.InputStream;

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;
import nl.han.ica.ap.purify.language.java.JavaLexer;
import nl.han.ica.ap.purify.language.java.JavaParser;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Tools to use the ANTLR parser in tests.
 * 
 * @author Arjan
 */
public class ParserTools {
	private ParserTools() {
	}

	/**
	 * Parse a file and get the parse tree.
	 * 
	 * @param filename File to parse.
	 * @return Parse tree
	 */
	public static ParseTree getParseTree(String filename) {
		ANTLRInputStream input = null;
		InputStream is = null;

		is = ParserTools.class.getResourceAsStream(filename);

		try {
			input = new ANTLRInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		return parser.compilationUnit();
	}

	/**
	 * Extract a method from the parse tree.
	 * 
	 * @param tree Parse tree.
	 * @param methodname Method name.
	 * @return Method if found or null.
	 */
	public static MethodBodyContext getMethodBody(ParseTree tree,
			final String methodname) {
		JavaBaseVisitor<MethodBodyContext> visitor = new 
				JavaBaseVisitor<MethodBodyContext>() {
			private MethodBodyContext result;
			
			@Override
			public MethodBodyContext visitMemberDecl(
					MemberDeclContext ctx) {
				if (ctx.Identifier() != null) {
					if (ctx.Identifier().getText().equals(methodname)) {
						return super.visitMemberDecl(ctx);
					}
				}
				
				if (ctx.memberDeclaration() != null) {
					return super.visitChildren(ctx);
				}

				return null;
			}
			
			@Override
			public MethodBodyContext visitMethodDeclaration(
					MethodDeclarationContext ctx) {
				if (ctx.Identifier().getText().equals(methodname)) {
					return super.visitMethodDeclaration(ctx);
				}

				return null;
			}

			@Override
			public MethodBodyContext visitMethodBody(MethodBodyContext ctx) {
				result = ctx;
				return null;
			}
			
			@Override
			public MethodBodyContext visit(ParseTree tree) {
				super.visit(tree);
				
				return result;
			}
		};
		
		return visitor.visit(tree);
	}
}
