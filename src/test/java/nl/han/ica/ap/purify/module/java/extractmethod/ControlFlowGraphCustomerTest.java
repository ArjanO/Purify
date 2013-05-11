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

import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.test.tools.ParserTools;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Unit test for {@link ControlFlowGraph}
 * 
 * This unit test test if the output is expected as descripted in:
 * 
 * Automated Method-Extraction Refactoring by Using Block-Based Slicing
 * Author: Katsuhisa Maruyama
 * 
 * @author Arjan
 */
public class ControlFlowGraphCustomerTest {
	private static final String FILE = "/extractmethod/Customer.java";
	
	private ControlFlowGraph cfg;
	private MethodBodyContext method;
	
	@Before
	public void before() {
		ParseTree tree = ParserTools.getParseTree(FILE);
		
		method = ParserTools.getMethodBody(tree, "statement");
		
		cfg = new ControlFlowGraph(method);
	}
	
	/**
	 * Expected 7 basic blocks (figure 3).
	 */
	@Test
	public void detectBlockTest() {
		assertEquals(7, cfg.getBasicBlocks().size());
	}
	
	/**
	 * Reach(B1) = {B1, B2, B3, B4, B5, B6, B7} (all the basic blocks).
	 */
	@Test
	public void reachB1Test() {
		List<BasicBlock> blcoks = cfg.getBasicBlocks();
		BasicBlock b1 = blcoks.get(0);
		
		List<BasicBlock> reachB1 = cfg.reach(b1);
		
		assertEquals(blcoks.size(), reachB1.size());
		
		for (BasicBlock b : blcoks) {
			assertTrue(reachB1.contains(b));
		}
	}
	
	/**
	 * Reach(B2) = {B2, B3, B4, B5, B6, B7}
	 */
	@Test
	public void reachB2Test() {
		List<BasicBlock> blocks = cfg.getBasicBlocks();
		List<BasicBlock> noReach = new ArrayList<BasicBlock>();
		noReach.add(blocks.get(0)); // B1
		
		BasicBlock b2 = blocks.get(1);
		
		List<BasicBlock> reachB2 = cfg.reach(b2);
		
		assertEquals(blocks.size() - noReach.size(), reachB2.size());
		
		for (BasicBlock b : blocks) {
			if (!noReach.contains(b)) {
				assertTrue(reachB2.contains(b));
			}
		}
	}
	
	/**
	 * Reach(B3) = {B3, B4, B5, B6}
	 */
	@Test
	public void reachB3Test() {
		List<BasicBlock> blocks = cfg.getBasicBlocks();
		List<BasicBlock> noReach = new ArrayList<BasicBlock>();
		noReach.add(blocks.get(0)); // B1
		noReach.add(blocks.get(1)); // B2
		noReach.add(blocks.get(6)); // B7
		
		BasicBlock b3 = blocks.get(2); // B3
		
		List<BasicBlock> reachB3 = cfg.reach(b3);
		
		assertEquals(blocks.size() - noReach.size(), reachB3.size());
		
		for (BasicBlock b : blocks) {
			if (!noReach.contains(b)) {
				assertTrue(reachB3.contains(b));
			}
		}
	}
	
	/**
	 * Reach(B4) = {B4, B6}
	 */
	@Test
	public void reachB4Test() {
		List<BasicBlock> blocks = cfg.getBasicBlocks();
		List<BasicBlock> noReach = new ArrayList<BasicBlock>();
		noReach.add(blocks.get(0)); // B1
		noReach.add(blocks.get(1)); // B2
		noReach.add(blocks.get(2)); // B3
		noReach.add(blocks.get(5)); // B5
		noReach.add(blocks.get(6)); // B7
		
		BasicBlock b4 = blocks.get(3); // B4
		
		List<BasicBlock> reachB4 = cfg.reach(b4);
		
		assertEquals(blocks.size() - noReach.size(), reachB4.size());
		
		for (BasicBlock b : blocks) {
			if (!noReach.contains(b)) {
				assertTrue(reachB4.contains(b));
			}
		}
	}
	
	/**
	 * Reach(B5) = {B5, B6}
	 */
	@Test
	public void reachB5Test() {
		List<BasicBlock> blocks = cfg.getBasicBlocks();
		List<BasicBlock> noReach = new ArrayList<BasicBlock>();
		noReach.add(getBasicBlockOfNode(blocks, "2")); // B1
		noReach.add(getBasicBlockOfNode(blocks, "6")); // B2
		noReach.add(getBasicBlockOfNode(blocks, "8")); // B3
		noReach.add(getBasicBlockOfNode(blocks, "10")); // B4
		noReach.add(getBasicBlockOfNode(blocks, "14")); // B7
		
		assertFalse(noReach.contains(null));
		
		BasicBlock b5 = getBasicBlockOfNode(blocks, "11"); // B5
		
		List<BasicBlock> reachB5 = cfg.reach(b5);
		
		assertEquals(blocks.size() - noReach.size(), reachB5.size());
		
		for (BasicBlock b : blocks) {
			if (!noReach.contains(b)) {
				assertTrue(reachB5.contains(b));
			}
		}
	}
	
	/**
	 * Reach(B6) = {B6}
	 */
	@Test
	public void reachB6Test() {
		List<BasicBlock> blocks = cfg.getBasicBlocks();
		
		BasicBlock b6 = getBasicBlockOfNode(blocks, "12"); // B6
		
		List<BasicBlock> reach = cfg.reach(b6);
		
		assertEquals(1, reach.size());
		assertTrue(reach.contains(b6));
	}
	
	/**
	 * Get the basic block of a node
	 *  
	 * @param blocks Basic blocks. 
	 * @param name Name of the node.
	 * @return Basic block of the node.
	 */
	private BasicBlock getBasicBlockOfNode(List<BasicBlock> blocks, 
			String name) {
		for (BasicBlock b : blocks) {
			for (int i = b.size() - 1; i >= 0; i--) {
				if (b.get(i).getName().equals(name)) {
					return b;
				}
			}
		}
		
		return null;
	}
}
