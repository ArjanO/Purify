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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nl.han.ica.ap.purify.language.java.JavaParser.LocalVariableDeclarationStatementContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementIfContext;
import nl.han.ica.ap.purify.language.java.JavaParser.StatementStatementExpressionContext;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Unit test for {@link ControlFlowGraphVisitor}
 * 
 * @author Arjan
 */
public class ControlFlowGraphVisitorTest {
	private ControlFlowGraphVisitor visitor;
	
	private MethodBodyContext methodCtx;
	
	@Before
	public void setUp() {
		visitor = new ControlFlowGraphVisitor();
		
		methodCtx = createMock(MethodBodyContext.class);
		expect(methodCtx.getChildCount()).andReturn(0).anyTimes();
		
		replay(methodCtx);
		
		visitor.visitMethodBody(methodCtx);
	}
	
	@After
	public void shutdown() {
		verify(methodCtx);
	}
	
	/**
	 * The method node is the first (and in this case the only) node.
	 */
	@Test
	public void methodNodeTest() {
		Node startNode = visitor.getGraph();
		
		assertNotNull(startNode);
		
		// The node does not have children, parents or flow back nodes.
		assertEquals(0, startNode.getChilderen().size());
		assertEquals(0, startNode.getParents().size());
		assertEquals(0, startNode.getFlowBack().size());
	}
	
	/**
	 * Test for the method visitLocalVariableDeclarationStatement.
	 */
	@Test
	public void localVariableDeclarationStatementTest() {
		LocalVariableDeclarationStatementContext ctx;
		
		ctx = createMock(LocalVariableDeclarationStatementContext.class);
		expect(ctx.getChildCount()).andReturn(0).anyTimes();
		
		replay(ctx);
		
		visitor.visitLocalVariableDeclarationStatement(ctx);
		Node startNode = visitor.getGraph();
		
		assertNotNull(startNode);
		assertEquals(1, startNode.getChilderen().size());
		
		Node localVarNode = startNode.getChilderen().get(0);
		assertEquals(0, localVarNode.getChilderen().size());
		assertEquals(localVarNode.getParseTree(), ctx);
		
		verify(ctx);
	}
	
	/**
	 * Test if with 2 statements.
	 */
	@Test
	public void visitStatementIfTest() {
		StatementIfContext ifCtx;
		StatementStatementExpressionContext statement1Ctx;
		StatementStatementExpressionContext statement2Ctx;
		
		ifCtx = createMock(StatementIfContext.class);
		
		statement1Ctx = createMock(StatementStatementExpressionContext.class);
		statement2Ctx = createMock(StatementStatementExpressionContext.class);
		
		expect(ifCtx.getChildCount()).andReturn(2).anyTimes();
		expect(ifCtx.getChild(0)).andReturn(statement1Ctx).anyTimes();
		expect(ifCtx.getChild(1)).andReturn(statement2Ctx).anyTimes();
		
		expect(statement1Ctx.getChildCount()).andReturn(0).anyTimes();
		expect(statement2Ctx.getChildCount()).andReturn(0).anyTimes();
		
		expectAccept(statement1Ctx);
		expectAccept(statement2Ctx);
		
		replay(ifCtx);
		replay(statement1Ctx);
		replay(statement2Ctx);
		
		visitor.visitStatementIf(ifCtx);
		
		Node startNode = visitor.getGraph();
		
		assertNotNull(startNode);
		assertEquals(1, startNode.getChilderen().size());
		
		Node ifNode = startNode.getChilderen().get(0);
		
		assertEquals(2, ifNode.getChilderen().size());
		
		Node statement1Node = ifNode.getChilderen().get(0);
		Node statement2Node = ifNode.getChilderen().get(1);
		
		assertEquals(0, statement1Node.getChilderen().size());
		assertEquals(0, statement2Node.getChilderen().size());
		
		assertEquals(statement1Ctx, statement1Node.getParseTree());
		assertEquals(statement2Ctx, statement2Node.getParseTree());
		
		verify(ifCtx);
		verify(statement1Ctx);
		verify(statement2Ctx);		
	}
	
	/**
	 * ControlFlowGraphVisitor calls accept. This method class the visit 
	 * method.
	 * 
	 * @param mock Context instance (mocked).
	 */
	private void expectAccept(final StatementStatementExpressionContext mock) {
		expect(mock.accept(isA(ControlFlowGraphVisitor.class))).andAnswer(
				new IAnswer<Void>() {
					@Override
					public Void answer() throws Throwable {
						ControlFlowGraphVisitor visitor = 
							(ControlFlowGraphVisitor)getCurrentArguments()[0];
		
						return visitor.visitStatementStatementExpression(mock);
					}
				}).anyTimes();
	}
}
