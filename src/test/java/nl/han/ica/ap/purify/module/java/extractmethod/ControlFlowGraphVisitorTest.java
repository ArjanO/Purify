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
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;

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
}
