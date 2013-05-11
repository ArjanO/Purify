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
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link BasicBlock}
 * 
 * @author Arjan
 */
public class BasicBlockTest {
	private BasicBlock block;
	
	@Before
	public void setUp() {
		block = new BasicBlock();
	}
	
	@After
	public void shutdown() {
	}
	
	@Test
	public void emptyBlockTest() {
		assertEquals(0, block.size());
	}
	
	@Test
	public void addBlockTest() {
		Node node = createMock(Node.class);
		node.setBasicBlock(EasyMock.eq(block));
		
		replay(node);
		
		block.add(node);
		
		assertEquals(1, block.size());
		assertEquals(node, block.get(0));
		
		verify(node);
	}
	
	@Test
	public void addSameNodeBlockTest() {
		Node node = createMock(Node.class);
		
		node.setBasicBlock(EasyMock.eq(block));
		
		replay(node);
		
		block.add(node);
		block.add(node);
		
		assertEquals(1, block.size());
		assertEquals(node, block.get(0));
		
		verify(node);
	}
	
	@Test
	public void setLeaderTest() {
		Node node = createMock(Node.class);
		
		replay(node);
		
		block.setLeader(node);
		
		assertEquals(0, block.size());
		assertEquals(node, block.getLeader());
		
		verify(node);
	}
}
