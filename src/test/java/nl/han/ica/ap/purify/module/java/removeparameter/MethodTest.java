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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link Method}
 * 
 * @author Arjan
 */
public class MethodTest {
	private List<Object> mocks;
	private Method method;
	
	private MemberDeclContext methodCtx;
	
	@Before
	public void before() {
		methodCtx = createMock(MemberDeclContext.class);
		
		replay(methodCtx);
		
		mocks = new ArrayList<Object>();
		method = new Method("test", methodCtx);
	}
	
	@After
	public void after() {
		verify(methodCtx);
		
		for (Object mock : mocks) {
			verify(mock);
		}
	}
	
	@Test
	public void getNameTest() {
		assertEquals("test", method.getName());
	}
	
	@Test
	public void addParameterTest() {
		method.addParameter(createParameter("number"));
		
		// If a parameter is added this is default unused.
		assertEquals(1, method.getUnusedPrametersSize());
	}
	
	@Test
	public void addParametersTest() {
		method.addParameter(createParameter("number"));
		method.addParameter(createParameter("name"));
		
		// If a parameter is added this is default unused.
		assertEquals(2, method.getUnusedPrametersSize());
	}
	
	@Test
	public void usedVariable1Test() {
		method.addParameter(createParameter("number"));
		
		method.usedVariable("number");
		
		assertEquals(0, method.getUnusedPrametersSize());
		assertEquals(0, method.getUnusedParameters().size());
	}
	
	@Test
	public void usedVariable2Test() {
		method.addParameter(createParameter("name"));
		method.addParameter(createParameter("name"));
		
		method.usedVariable("number");
		
		assertEquals(1, method.getUnusedPrametersSize());
		assertEquals(1, method.getUnusedParameters().size());
		
		boolean bName = false;
		
		for (Parameter param : method.getUnusedParameters()) {
			if (param.getName().equals("name")) {
				bName = true;
			}
		}
		
		assertTrue(bName);
	}
	
	private Parameter createParameter(String name) {
		FormalParameterDeclsRestContext ctx;
		VariableDeclaratorIdContext decl;
		TerminalNode identifier;
		
		ctx = createMock(FormalParameterDeclsRestContext.class);
		decl = createMock(VariableDeclaratorIdContext.class);
		identifier = createMock(TerminalNode.class);
		
		expect(identifier.getText()).andReturn(name).anyTimes();
		expect(decl.Identifier()).andReturn(identifier).anyTimes();
		expect(ctx.variableDeclaratorId()).andReturn(decl).anyTimes();
		
		replay(ctx);
		replay(decl);
		replay(identifier);
		
		mocks.add(ctx);
		mocks.add(decl);
		mocks.add(identifier);
		
		Parameter param = new Parameter(ctx);
		return param;
	}
}
