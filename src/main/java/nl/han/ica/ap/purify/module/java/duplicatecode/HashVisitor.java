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
import org.antlr.v4.runtime.tree.TerminalNode;

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;
import nl.han.ica.ap.purify.language.java.JavaParser.BoolLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.BooleanLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.CharLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassOrInterfaceTypeContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionArithmeticContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionEqualToNotEqualToContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FloatLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.IntLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.IntegerLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.NullLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.ParExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PrimaryIdentifierContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementIfContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StringLiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

/**
 * Get the hash of a parse tree. 
 * 
 * @author Arjan
 */
public class HashVisitor extends JavaBaseVisitor<Integer> {
	private static final int PRIME = 31;
	
	private static final int VARIABLE_HASH = 342;
	
	private static final int KEYWORD_TRUE = 800;
	private static final int KEYWORD_FALSE = 801;
	
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
	
	/**
	 * Called for variable declaration.
	 * 
	 * For example:
	 * {@code int myVar;}
	 */
	@Override
	public Integer visitVariableDeclaratorId(VariableDeclaratorIdContext ctx) {
		if (ctx.Identifier() != null) {
			String identifier = ctx.Identifier().getText();
			
			if (identifier != null) {
				if (localVariables.contains(ctx.Identifier().getText())) {
					return VARIABLE_HASH;
				}
				
				// Unknown variable name. Hash the name.
				return identifier.hashCode();
			}
		}		
		
		return super.visitVariableDeclaratorId(ctx);
	}
	
	/**
	 * Called if a variable or literal (number or text) is 
	 * used in a expression.
	 * 
	 * For example:
	 * {@code myVar = myVar * 2;}
	 */
	@Override
	public Integer visitPrimaryIdentifier(PrimaryIdentifierContext ctx) {
		String identifier = ctx.Identifier().getText();
		
		if (identifier == null) {
			return defaultResult();
		}
		
		if (localVariables.contains(identifier)) {
			return VARIABLE_HASH;
		}
		
		// Unknown variable or it is a literal. Hash the text.
		return identifier.hashCode();
	}
	
	/**
	 * Called if a class or interface is used.
	 * 
	 * For example:
	 * {@code MyClass myClass = null;}
	 */
	@Override
	public Integer visitClassOrInterfaceType(ClassOrInterfaceTypeContext ctx) {
		if (ctx.getText() != null) {
			// Hash the class name.
			return ctx.getText().hashCode();
		}		
		
		return defaultResult();
	}
	
	/**
	 * Called by a integer value.
	 * 
	 * For example:
	 * {@code int myVar = 2;}
	 */
	@Override
	public Integer visitIntegerLiteral(IntegerLiteralContext ctx) {
		if (ctx.getText() != null) {
			// Use the value a the hash.
			return Integer.parseInt(ctx.getText());
		}
		
		return defaultResult();
	}
	
	/**
	 * Called by a boolean value. 
	 * 
	 * For example:
	 * {@code boolean myVar = false;}
	 */
	@Override
	public Integer visitBooleanLiteral(BooleanLiteralContext ctx) {
		if (ctx.getText() != null) {
			if (ctx.getText().equalsIgnoreCase("true")) {
				return KEYWORD_TRUE;
			} else if (ctx.getText().equalsIgnoreCase("false")) {
				return KEYWORD_FALSE;
			}
		}
		
		return defaultResult();
	}
	
	/**
	 * Called when a Integer literal is detected. 
	 */
	@Override
	public Integer visitIntLiteral(IntLiteralContext ctx) {
		// Get result of this literal.
		Integer result = super.visitIntLiteral(ctx);
		
		if (result != null) {
			return result; // Use found hash. 
		}
		
		if (ctx.getText() != null) {
			// No hash found. Create a hash of the text. 
			return ctx.getText().hashCode();
		}
		
		return defaultResult();
	}
	
	/**
	 * Called when a float literal is detected. 
	 */
	@Override
	public Integer visitFloatLiteral(FloatLiteralContext ctx) {
		// Get result of this literal.
		Integer result = super.visitFloatLiteral(ctx);
		
		if (result != null) {
			return result; // Use found hash. 
		}
				
		if (ctx.getText() != null) {
			// No hash found. Create a hash of the text. 
			return ctx.getText().hashCode();
		}
				
		return defaultResult();
	}
	
	/**
	 * Called when a char literal is detected. 
	 */
	@Override
	public Integer visitCharLiteral(CharLiteralContext ctx) {
		// Get result of this literal.
		Integer result = super.visitCharLiteral(ctx);
		
		if (result != null) {
			return result; // Use found hash. 
		}
				
		if (ctx.getText() != null) {
			// No hash found. Create a hash of the text. 
			return ctx.getText().hashCode();
		}
				
		return defaultResult();
	}
	
	/**
	 * Called when a string literal is detected. 
	 */
	@Override
	public Integer visitStringLiteral(StringLiteralContext ctx) {
		// Get result of this literal.
		Integer result = super.visitStringLiteral(ctx);
		
		if (result != null) {
			return result; // Use found hash. 
		}
				
		if (ctx.getText() != null) {
			// No hash found. Create a hash of the text. 
			return ctx.getText().hashCode();
		}
				
		return defaultResult();
	}
	
	/**
	 * Called when a boolean literal is detected. 
	 */
	@Override
	public Integer visitBoolLiteral(BoolLiteralContext ctx) {
		// Get result of this literal.
		Integer result = super.visitBoolLiteral(ctx);
		
		if (result != null) {
			return result; // Use found hash. 
		}
				
		if (ctx.getText() != null) {
			// No hash found. Create a hash of the text. 
			return ctx.getText().hashCode();
		}
				
		return defaultResult();
	}
	
	/**
	 * Called when a null literal is detected. 
	 */
	@Override
	public Integer visitNullLiteral(NullLiteralContext ctx) {
		// Get result of this literal.
		Integer result = super.visitNullLiteral(ctx);
		
		if (result != null) {
			return result; // Use found hash. 
		}
				
		if (ctx.getText() != null) {
			// No hash found. Create a hash of the text. 
			return ctx.getText().hashCode();
		}
				
		return defaultResult();
	}
	
	/**
	 * Called for expression wits compare something.
	 * 
	 * For example:
	 * {@code myVar == 4}
	 */
	@Override
	public Integer visitExpressionEqualToNotEqualTo(
			ExpressionEqualToNotEqualToContext ctx) {
		int iHash = 1;
		boolean bVisit = false;
		
		for (int i = ctx.getChildCount() - 1; i >= 0; i--) {
			ParseTree item = ctx.getChild(i);
			
			if (item instanceof ExpressionContext) {
				Integer result = visit(item);
				
				if (result != null) {
					iHash = iHash * PRIME + result;
					bVisit = true;
				}
			} else if (item.getText() != null) {
				iHash = iHash * PRIME + item.getText().hashCode();
				bVisit = true;
			}
		}
		
		// Don't visit children. This method handles the visit.
		if (bVisit) {
			return iHash;
		} else {
			return defaultResult();
		}
	}
	
	
	@Override
	public Integer visitStatementIf(StatementIfContext ctx) {
		int iHash = 1;
		boolean bVisit = false;
		
		for (int i = ctx.getChildCount() - 1; i >= 0; i--) {
			ParseTree item = ctx.getChild(i);
			
			if (item instanceof StatementContext) {
				Integer result = visit(item);
				
				if (result != null) {
					iHash = iHash * PRIME + result;
					bVisit = true;
				}
			} else if (item instanceof ParExpressionContext) {
				Integer result = visit(item);
				
				if (result != null) {
					iHash = iHash * PRIME + result;
					bVisit = true;
				}
			} else if (item.getText() != null) {
				// Hash the text.
				iHash = iHash * PRIME + item.getText().hashCode();
				bVisit = true;
			}
		}
		
		// Don't visit children. This method handles the visit.
		if (bVisit) {
			return iHash;
		} else {
			return defaultResult();
		}
	}
	
	/**
	 * Called for calculations.
	 * 
	 * For example:
	 * {@code float f = 25.0f / 3.0f;}
	 */
	@Override
	public Integer visitExpressionArithmetic(ExpressionArithmeticContext ctx) {
		int iHash = 1;
		boolean bVisit = false;
		
		for (int i = ctx.getChildCount() - 1; i >= 0; i--) {
			ParseTree item = ctx.getChild(i);
			
			if (item instanceof TerminalNode) {
				bVisit = true;
				iHash = iHash * PRIME + item.getText().hashCode();
			} else {
				Integer result = visit(item);
				
				if (result != null) {
					iHash = iHash * PRIME + result;
					bVisit = true;
				}
			}
		}
		
		// Don't visit children. This method handles the visit.
		if (bVisit) {
			return iHash;
		} else {
			return defaultResult();
		}
	}
}
