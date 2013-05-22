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
import static org.junit.Assert.assertEquals;
import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.VariableDeclaratorIdContext;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Test;

/**
 * Unit test for {@link ParameterUtil}
 * 
 * @author Arjan
 */
public class ParameterUtilTest {
	@Test
	public void getParameterNameTest() {
		FormalParameterDeclsRestContext ctx;
		VariableDeclaratorIdContext decl;
		TerminalNode identifier;
		
		ctx = createMock(FormalParameterDeclsRestContext.class);
		decl = createMock(VariableDeclaratorIdContext.class);
		identifier = createMock(TerminalNode.class);
			
		expect(identifier.getText()).andReturn("test").anyTimes();
		expect(decl.Identifier()).andReturn(identifier).anyTimes();
		expect(ctx.variableDeclaratorId()).andReturn(decl).anyTimes();
		
		replay(ctx);
		replay(decl);
		replay(identifier);
		
		assertEquals("test", ParameterUtil.getParameterName(ctx));
		
		verify(ctx);
		verify(decl);
		verify(identifier);
	}
}
