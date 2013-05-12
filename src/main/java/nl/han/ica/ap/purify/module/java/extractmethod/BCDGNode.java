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
 * Block-based control dependence graph (BCDG) node
 * 
 * @author Arjan
 */
public class BCDGNode {
	private BasicBlock basicBlock;
	private List<BCDGNode> dependencies;
	
	/**
	 * Block-based control dependence graph (BCDG) node.
	 * 
	 * @param basicBlock associated basic block.
	 */
	public BCDGNode(BasicBlock basicBlock) {
		this.basicBlock = basicBlock;
		
		dependencies = new ArrayList<BCDGNode>();
	}
	
	/**
	 * Get the associated basic block.
	 * 
	 * @return BasicBlock
	 */
	public BasicBlock getBasicBlock() {
		return basicBlock;
	}
	
	/**
	 * Get the dependencies.
	 * 
	 * @return Dependencies (on this node).
	 */
	public List<BCDGNode> getDependencies() {
		return dependencies;
	}
	
	/**
	 * Add dependence (from node) to this node.
	 * 
	 * @param node Node
	 * @throws IllegalArgumentException if node is null.
	 */
	public void addDependence(BCDGNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		
		if (!dependencies.contains(node)) {
			dependencies.add(node);
		}
	}
}
