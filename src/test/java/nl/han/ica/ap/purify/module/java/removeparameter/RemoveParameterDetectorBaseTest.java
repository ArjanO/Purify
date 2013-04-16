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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionPrimaryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.PrimaryContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

/**
 * Shared tests for {@link RemoveParameterDetectorVoidTest} and
 * {@link RemoveParameterDetectorReturnTest}.
 * 
 * @author Arjan
 */
public abstract class RemoveParameterDetectorBaseTest {
	protected RemoveParameterDetector detector;
	
	protected MemberDeclContext ctx;
	
	@Test
	public void param1Test() {
		detector.enterMemberDecl(ctx);
		
		addParamter("name");
		
		detector.exitMemberDecl(ctx);
		
		List<Method> result = detector.getDetected();
		
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).getUnusedPrametersSize());
		assertEquals("name", result.get(0).getUnusedParameters().get(0));
	}
	
	@Test
	public void param2Test() {
		detector.enterMemberDecl(ctx);
		
		addParamter("name");
		callExpressionPrimary("name");
		
		detector.exitMemberDecl(ctx);
		
		List<Method> result = detector.getDetected();
		
		assertEquals(1, result.size());
		assertEquals(0, result.get(0).getUnusedPrametersSize());
	}
	
	@Test
	public void param3Test() {
		detector.enterMemberDecl(ctx);
		
		addParamter("name");
		addParamter("id");
		callExpressionPrimary("name");
		
		detector.exitMemberDecl(ctx);
		
		List<Method> result = detector.getDetected();
		
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).getUnusedPrametersSize());
		assertEquals("id", result.get(0).getUnusedParameters().get(0));
	}
	
	/**
	 * Simulate a set parameter call.
	 * 
	 * @param name Name of the parameter.
	 */
	private void addParamter(String name) {
		FormalParameterDeclsRestContext param =
				createMock(FormalParameterDeclsRestContext.class);
		VariableDeclaratorIdContext paramId =
				createMock(VariableDeclaratorIdContext.class);
		TerminalNode paramIdentifier = 
				createMock(TerminalNode.class);
		
		expect(param.variableDeclaratorId()).andReturn(paramId).anyTimes();
		expect(paramId.Identifier()).andReturn(paramIdentifier).anyTimes();
		expect(paramIdentifier.getText()).andReturn(name).anyTimes();
		
		replay(param);
		replay(paramId);
		replay(paramIdentifier);
		
		detector.enterFormalParameterDeclsRest(param);
		
		verify(param);
		verify(paramId);
		verify(paramIdentifier);
	}
	
	/**
	 * Simulate a expression primary call.
	 * 
	 * @param literal Text in the expression.
	 */
	private void callExpressionPrimary(String literal) {
		ExpressionPrimaryContext expression = 
				createMock(ExpressionPrimaryContext.class);
		PrimaryContext primary = 
				createMock(PrimaryContext.class);
		
		expect(expression.primary()).andReturn(primary).anyTimes();
		expect(primary.literal()).andReturn(null).anyTimes();
		expect(primary.getText()).andReturn(literal).anyTimes();
		
		replay(expression);
		replay(primary);
		
		detector.enterExpressionPrimary(expression);
		
		verify(expression);
		verify(primary);
	}
}
