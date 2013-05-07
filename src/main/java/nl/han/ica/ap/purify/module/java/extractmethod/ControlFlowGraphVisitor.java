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
package nl.han.ica.ap.purify.module.java.extractmethod;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import nl.han.ica.ap.purify.language.java.JavaBaseVisitor;
import nl.han.ica.ap.purify.language.java.JavaParser.LocalVariableDeclarationStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementAssertContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementBlockContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementBreakContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementContinueContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementDoContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementForContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementIdentifierStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementIfContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementReturnContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementStatementExpressionContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementSwitchContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementSynchronizedContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementThrowContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementTryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementWhileContext;

/**
 * Build a control flow graph (CFG).
 * 
 * @author Arjan
 */
class ControlFlowGraphVisitor extends JavaBaseVisitor<Void> {
	private int nodeNr;
	
	private Node firstNode;
	private Node lastNode;
	
	private List<Node> join;
	
	public ControlFlowGraphVisitor() {
		firstNode = null;
		lastNode = null;
		
		join = null;
	}
	
	/**
	 * Get the builded graph.
	 * 
	 * @return Builded graph (start node).
	 */
	public Node getGraph() {
		return firstNode;
	}
	
	@Override
	public Void visitMethodBody(MethodBodyContext ctx) {
		nodeNr = 1;
		
		firstNode = createNode(ctx);
		
		super.visitMethodBody(ctx);
		
		return null;
	}
	
	/**
	 * When a variable is declared.
	 */
	@Override
	public Void visitLocalVariableDeclarationStatement(
			LocalVariableDeclarationStatementContext ctx) {
		createNode(ctx);
		
		return super.visitLocalVariableDeclarationStatement(ctx);
	}
	
	@Override
	public Void visitStatementAssert(StatementAssertContext ctx) {
		createNode(ctx);
		
		return super.visitStatementAssert(ctx);
	}
	
	@Override
	public Void visitStatementBlock(StatementBlockContext ctx) {
		/*
		 * Don't do anything. Block arn't nodes.
		 */
		return super.visitStatementBlock(ctx);
	}
	
	@Override
	public Void visitStatementBreak(StatementBreakContext ctx) {
		createNode(ctx);
		
		return super.visitStatementBreak(ctx);
	}
	
	@Override
	public Void visitStatementContinue(StatementContinueContext ctx) {
		createNode(ctx);
		
		return super.visitStatementContinue(ctx);
	}
	
	@Override
	public Void visitStatementDo(StatementDoContext ctx) {
		createNode(ctx);
		
		return super.visitStatementDo(ctx);
	}
	
	@Override
	public Void visitStatementFor(StatementForContext ctx) {
		createNode(ctx);
		
		return super.visitStatementFor(ctx);
	}
	
	@Override
	public Void visitStatementIdentifierStatement(
			StatementIdentifierStatementContext ctx) {
		createNode(ctx);
		
		return super.visitStatementIdentifierStatement(ctx);
	}
	
	/**
	 * If is a branch node.
	 */
	@Override
	public Void visitStatementIf(StatementIfContext ctx) {
		Node ifNode = createNode(ctx);
		Node backup = null;
		boolean elseDetected = false;
		
		List<Node> branch = new ArrayList<Node>();
		
		for (int i = 0; i < ctx.getChildCount(); i++) {
			if (ctx.getChild(i) instanceof StatementContext) {
				lastNode = ifNode;
				
				super.visit(ctx.getChild(i));
				
				branch.add(lastNode);
				
				if (backup == null) {
					backup = lastNode;
				}
			} else if (ctx.getChild(i) instanceof TerminalNode &&
					ctx.getChild(i).getText().equals("else")) {
				elseDetected = true;
			}
		}
		
		if (elseDetected) {
			lastNode = backup;
		} else {
			lastNode = ifNode;
		}

		join = branch;
		return null;
	}
	
	@Override
	public Void visitStatementReturn(StatementReturnContext ctx) {
		createNode(ctx);
		
		return super.visitStatementReturn(ctx);
	}
	
	@Override
	public Void visitStatementStatement(StatementStatementContext ctx) {
		createNode(ctx);
		
		return super.visitStatementStatement(ctx);
	}
	
	@Override
	public Void visitStatementStatementExpression(
			StatementStatementExpressionContext ctx) {
		createNode(ctx);
		
		return super.visitStatementStatementExpression(ctx);
	}
	
	@Override
	public Void visitStatementSwitch(StatementSwitchContext ctx) {
		createNode(ctx);
		
		return super.visitStatementSwitch(ctx);
	}
	
	@Override
	public Void visitStatementSynchronized(StatementSynchronizedContext ctx) {
		createNode(ctx);
		
		return super.visitStatementSynchronized(ctx);
	}
	
	@Override
	public Void visitStatementThrow(StatementThrowContext ctx) {
		createNode(ctx);
		
		return super.visitStatementThrow(ctx);
	}
	
	@Override
	public Void visitStatementTry(StatementTryContext ctx) {
		createNode(ctx);
		
		return super.visitStatementTry(ctx);
	}
	
	/**
	 * While is a branch node.
	 */
	@Override
	public Void visitStatementWhile(StatementWhileContext ctx) {
		Node backUp = createNode(ctx);
		
		super.visitStatementWhile(ctx);
		
		if (lastNode != null) {
			lastNode.addFlowBack(backUp);
		}
		lastNode = backUp;
		return null;
	}
	
	/**
	 * Create a new node for the ParseTree node.
	 * 
	 * @param tree ParseTree node.
	 * @return Created node.
	 */
	private Node createNode(ParseTree tree) {
		Node node = new Node(tree);
		node.setName(nodeNr + "");
		nodeNr += 1;
		
		if (lastNode != null) {
			lastNode.addChild(node);
		}
		
		lastNode = node;
		
		if (join != null) { // There are branches to join.
			for (Node joinNode : join) {
				joinNode.addChild(node); // Join the branch.
			}
			join = null; // Reset the join nodes.
		}
		
		return node;
	}
}