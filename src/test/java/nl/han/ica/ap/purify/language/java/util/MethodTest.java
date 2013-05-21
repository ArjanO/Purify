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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.junit.Assert.*;

import java.util.TreeSet;

import nl.han.ica.ap.purify.language.java.JavaParser.BlockContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VoidMethodDeclaratorRestContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.easymock.IAnswer;
import org.junit.Test;

/**
 * Test method util.
 * 
 * @author Arjan
 */
public class MethodTest {
	@Test
	public void getMethodNameReturnTest() {
		MemberDeclContext ctx;
		MemberDeclarationContext memberDeclCtx;
		MethodDeclarationContext methodDeclCtx;
		TerminalNode identifier;

		ctx = createMock(MemberDeclContext.class);
		memberDeclCtx = createMock(MemberDeclarationContext.class);
		methodDeclCtx = createMock(MethodDeclarationContext.class);
		identifier = createMock(TerminalNode.class);
		
		expect(identifier.getText()).andReturn("test").anyTimes();
		
		expect(ctx.voidMethodDeclaratorRest()).andReturn(null).anyTimes();
		expect(ctx.memberDeclaration()).andReturn(
				memberDeclCtx).anyTimes();
		expect(memberDeclCtx.methodDeclaration()).andReturn(
				methodDeclCtx).anyTimes();
		
		expect(methodDeclCtx.Identifier()).andReturn(
				identifier).anyTimes();
		
		replay(ctx);
		replay(memberDeclCtx);
		replay(methodDeclCtx);
		replay(identifier);
	
		assertEquals("test", Method.getMethodName(ctx));
		
		verify(ctx);
		verify(memberDeclCtx);
		verify(methodDeclCtx);
		verify(identifier);
	}
	
	@Test
	public void getMethodNameVoidTest() {
		MemberDeclContext ctx;
		VoidMethodDeclaratorRestContext voidCtx;
		TerminalNode identifier;
		
		ctx = createMock(MemberDeclContext.class);
		voidCtx = createMock(VoidMethodDeclaratorRestContext.class);
		identifier = createMock(TerminalNode.class);
		
		expect(identifier.getText()).andReturn("test").anyTimes();
		
		expect(ctx.voidMethodDeclaratorRest()).andReturn(voidCtx).anyTimes();
		expect(ctx.Identifier()).andReturn(identifier).anyTimes();
		
		replay(ctx);
		replay(voidCtx);
		replay(identifier);
		
		assertEquals("test", Method.getMethodName(ctx));
		
		verify(ctx);
		verify(voidCtx);
		verify(identifier);
	}
	
	/**
	 * Test with a method. Expected: ParseTree is method.
	 */
	@Test
	public void isParseTreeMethodBodyTest() {
		MethodBodyContext methodBody = 
				createMock(MethodBodyContext.class);
		BlockContext block = 
				createMock(BlockContext.class);
		
		block.parent = methodBody;
		
		replay(block);
		replay(methodBody);
		
		assertTrue(Method.isParseTreeMethodBody(block));
		
		verify(block);
		verify(methodBody);
	}
	
	/**
	 * Test if variable id's are returned.
	 */
	@Test
	public void getLocalVariablesTest() {
		MemberDeclContext methodMock;
		VariableDeclaratorIdContext idMock;
		TerminalNode terminalNodeMock;
		
		methodMock = createMock(MemberDeclContext.class);
		
		idMock = createMock(VariableDeclaratorIdContext.class);
		
		expectAccept(methodMock);
		expect(methodMock.getChild(0)).andReturn(idMock).anyTimes();
		expect(methodMock.getChildCount()).andReturn(1).anyTimes();
		
		terminalNodeMock = createMock(TerminalNode.class);
		expect(terminalNodeMock.getText()).andReturn("test").anyTimes();
		
		expectAccept(idMock);
		expect(idMock.Identifier()).andReturn(terminalNodeMock).anyTimes();
		
		replay(methodMock);
		replay(idMock);
		replay(terminalNodeMock);
		
		TreeSet<String> result = Method.getLocalVariables(methodMock);
		
		assertEquals(1, result.size());
		assertTrue(result.contains("test"));
		
		verify(methodMock);
		verify(idMock);
		verify(terminalNodeMock);
	}
	
	/**
	 * AbstractParseTreeVisitor calls accept. This method class the visit 
	 * method.
	 * 
	 * @param mock Context instance (mocked).
	 */
	private void expectAccept(final MemberDeclContext mock) {
		expect(mock.accept(isA(LocalVariableVisitor.class))).andAnswer(
				new IAnswer<Void>() {
					@Override
					public Void answer() throws Throwable {
						LocalVariableVisitor visitor =
								(LocalVariableVisitor)getCurrentArguments()[0];
						
						return visitor.visitMemberDecl(
									(MemberDeclContext)mock);
					}
				}).anyTimes();
	}
	
	/**
	 * AbstractParseTreeVisitor calls accept. This method class the visit 
	 * method.
	 * 
	 * @param mock Context instance (mocked).
	 */
	private void expectAccept(final VariableDeclaratorIdContext mock) {
		expect(mock.accept(isA(LocalVariableVisitor.class))).andAnswer(
				new IAnswer<Void>() {
					@Override
					public Void answer() throws Throwable {
						LocalVariableVisitor visitor =
								(LocalVariableVisitor)getCurrentArguments()[0];
		
						return visitor.visitVariableDeclaratorId(mock);
					}
				}).anyTimes();
	}
}
