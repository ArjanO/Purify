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

import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.test.tools.ParserTools;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
}
