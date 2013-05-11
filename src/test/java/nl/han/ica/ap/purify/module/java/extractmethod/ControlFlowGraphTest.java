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
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;

import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ControlFlowGraph}
 * 
 * @author Arjan
 */
public class ControlFlowGraphTest {
	private MethodBodyContext methodCtx;
	
	@Before
	public void before() {
		methodCtx = createMock(MethodBodyContext.class);
		
		expectAccept(methodCtx);
	}
	
	@After
	public void afther() {
		verify(methodCtx);
	}
	
	/**
	 * Test with empty method.
	 */
	@Test
	public void emptyMethodTest() {
		expect(methodCtx.getChildCount()).andReturn(0).anyTimes();
		
		replay(methodCtx);
		
		ControlFlowGraph cfg = new ControlFlowGraph(methodCtx);
		
		assertNotNull(cfg.getEntryNode());
		assertEquals(methodCtx, cfg.getEntryNode().getParseTree());
	}
	
	/**
	 * ControlFlowGraphVisitor calls accept. This method class the visit 
	 * method.
	 * 
	 * @param mock Context instance (mocked).
	 */
	private void expectAccept(final MethodBodyContext mock) {
		expect(mock.accept(isA(ControlFlowGraphVisitor.class))).andAnswer(
				new IAnswer<Void>() {
					@Override
					public Void answer() throws Throwable {
						ControlFlowGraphVisitor visitor = 
							(ControlFlowGraphVisitor)getCurrentArguments()[0];
		
						return visitor.visitMethodBody(mock);
					}
				}).anyTimes();
	}
}
