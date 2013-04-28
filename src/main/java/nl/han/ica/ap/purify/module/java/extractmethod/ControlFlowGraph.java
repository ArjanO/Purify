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

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Build a control flow graph (CFG).
 * 
 * @author Arjan
 */
public class ControlFlowGraph {
	private ControlFlowGraph() {
	}
	
	/**
	 * Build a control flow graph (CFG) from the ParseTree).
	 * 
	 * @param tree Method parse tree.
	 * @return Start node of the CFG.
	 */
	public static Node buildGraph(ParseTree tree) {
		ControlFlowGraphVisitor cfgVisitor = new ControlFlowGraphVisitor();
		
		cfgVisitor.visit(tree);
		
		return cfgVisitor.getGraph();
	}
	
	/**
	 * Get basic blocks from a control flow graph (CFG).  
	 * 
	 * @param startNode CFG start node.
	 * @return List with BasicBlocks.
	 */
	public static List<BasicBlock> getBasicBlocks(Node startNode) {
		List<BasicBlock> blocks = new ArrayList<BasicBlock>();
		List<Node> seen = new ArrayList<Node>();
		
		for (Node child : startNode.getChilderen()) {
			BasicBlock block = new BasicBlock();
			blocks.add(block);
			basicBlock(child, block, blocks, seen);
		}
		
		return blocks;
	}
	
	/**
	 * Detect basic blocks
	 * 
	 * @param n Node to check
	 * @param block Current basic block
	 * @param blocks Founded basic blocks
	 * @param seen Nodes that are seen by this method.
	 */
	private static void basicBlock(Node n, BasicBlock block, 
			List<BasicBlock> blocks, List<Node> seen) {
		seen.add(n);
		
		BasicBlock nBlock = block;
		
		if (n.getParents().size() > 1 || n.getFlowTo().size() > 0) {
			// Node n is a join node. Node n gets a new block.
			nBlock = new BasicBlock();
			blocks.add(nBlock);
		}
		
		nBlock.add(n);
		
		if (n.getChilderen().size() > 1) {
			// If n has more than one child the child nodes are leaders 
			// of a new block.
			for (Node child : n.getChilderen()) {
				if (!seen.contains(child)) {
					BasicBlock childBlock = new BasicBlock();
					blocks.add(childBlock);
				
					basicBlock(child, childBlock, blocks, seen);
				}
			}
		} else {
			for (Node child : n.getChilderen()) {
				if (!seen.contains(child)) {
					basicBlock(child, nBlock, blocks, seen);
				}
			}
		}
	}
	
	/**
	 * Build a DOT graph. The generated string allows to generate a DOT image.
	 * 
	 * @param n Start node of the CFG. 
	 * @return In DOT format a graph.
	 */
	public static String toDOTGraph(Node n) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		
		List<Node> seen = new ArrayList<Node>();
		
		buildDOTGraph(n, null, sb, seen);
		
		sb.append("}");
		
		return sb.toString();
	}
	
	private static void buildDOTGraph(Node n, Node parent, 
			StringBuilder sb, List<Node> seen) {		
		if (parent != null) {
			sb.append("\t" + parent.getName() + " -> " + n.getName() + ";\n");
		} else {
			sb.append("\t" + n.getName() + ";\n");
		}
		
		if (seen.contains(n)) {
			return;
		} else {
			seen.add(n);
		}
		
		for (Node child : n.getChilderen()) {
			buildDOTGraph(child, n, sb, seen);
		}
		
		for (Node flow : n.getFlowBack()) {
			sb.append("\t" + n.getName() + " -> " + flow.getName() + ";\n");
		}
	}
}
