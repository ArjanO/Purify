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

/**
 * Basic block is a set of {@link Node} with start flow of control at the
 * start and leafs at the end. 
 * 
 * See Automated Method-Extraction Refactoring by Using Block-Based Slicing
 * from Katsuhisa Maruyama.
 * 
 * @author Arjan
 */
public class BasicBlock {
	private List<Node> nodes;
	
	/**
	 * Basic block
	 */
	public BasicBlock() {
		nodes = new ArrayList<Node>();
	}
	
	/**
	 * Get the number of nodes in this basic block.
	 * 
	 * @return Number of items in this basic block.
	 */
	public int size() {
		return nodes.size();
	}
	
	/**
	 * Add a node to this basic block.
	 * 
	 * @param node Node to add.
	 */
	public void add(Node node) {
		if (!nodes.contains(node)) {
			nodes.add(node);
		}
	}
	
	/**
	 * Get Node at index.
	 * 
	 * @param index Index of node.
	 * @return 
	 */
	public Node get(int index) {
		return nodes.get(index);
	}
}
