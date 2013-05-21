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
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link Parameter}
 * 
 * @author Arjan
 */
public class ParameterTest {
	private FormalParameterDeclsRestContext ctx;
	private VariableDeclaratorIdContext decl;
	private TerminalNode identifier;
	
	@Before
	public void before() {		
		ctx = createMock(FormalParameterDeclsRestContext.class);
		decl = createMock(VariableDeclaratorIdContext.class);
		identifier = createMock(TerminalNode.class);
		
		expect(identifier.getText()).andReturn("test").anyTimes();
		expect(decl.Identifier()).andReturn(identifier).anyTimes();
		expect(ctx.variableDeclaratorId()).andReturn(decl).anyTimes();
		
		replay(ctx);
		replay(decl);
		replay(identifier);
	}
	
	@After
	public void after() {
		verify(ctx);
		verify(decl);
		verify(identifier);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullParameterTest() {
		new Parameter(null);
	}
	
	@Test
	public void parameterNameTest() {
		Parameter parameter = new Parameter(ctx);
		
		assertEquals("test", parameter.getName());
		assertEquals(ctx, parameter.getpPrameter());
	}
	
	@Test
	public void equalsTest() {
		Parameter parameter1 = new Parameter(ctx);
		Parameter parameter2 = new Parameter(ctx);

		assertEquals("test", parameter1.getName());
		assertEquals("test", parameter2.getName());
		
		assertTrue(parameter1.equals(parameter2));
	}
}
