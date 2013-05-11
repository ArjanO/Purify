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
	/**
	 * Method as node is the start node of the CFG.
	 */
	private Node entryNode;
	
	/**
	 * Basic blocks in this CFG graph.
	 */
	private List<BasicBlock> basicBlocks;
	
	/**
	 * Build a control flow graph (CFG) from the ParseTree. 
	 * 
	 * @param tree Method parse tree.
	 * @throws IllegalArgumentException if tree is null.
	 */
	public ControlFlowGraph(ParseTree tree) {
		if (tree == null) {
			throw new IllegalArgumentException();
		}
		
		ControlFlowGraphVisitor cfgVisitor = new ControlFlowGraphVisitor();
		
		cfgVisitor.visit(tree);
		
		entryNode = cfgVisitor.getGraph();
		
		basicBlocks = detectBasicBlocks();
	}
	
	/**
	 * Get the entry node of the CFG.
	 * 
	 * @return Entry node.
	 */
	public Node getEntryNode() {
		return entryNode;
	}
	
	/**
	 * Get the detected basic blocks.
	 * 
	 * @return Detected basic blocks.
	 */
	public List<BasicBlock> getBasicBlocks() {
		return basicBlocks;
	}
	
	/**
	 * Build a DOT graph. The generated string allows to generate a DOT image.
	 * 
	 * @return String in the DOT format.
	 */
	public String toDOTGraph() {
		return toDOTGraph(entryNode, basicBlocks);
	}
	
	/**
	 * Detect the basic blocks in this control flow graph (CFG).  
	 * 
	 * @return List with BasicBlocks.
	 */
	private List<BasicBlock> detectBasicBlocks() {
		List<BasicBlock> blocks = new ArrayList<BasicBlock>();
		List<Node> seen = new ArrayList<Node>();
		
		for (Node child : entryNode.getChilderen()) {
			BasicBlock block = new BasicBlock();
			block.setLeader(child);
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
	private void basicBlock(Node n, BasicBlock block, 
			List<BasicBlock> blocks, List<Node> seen) {
		seen.add(n);
		
		BasicBlock nBlock = block;
		
		if (n.getParents().size() > 1 || n.getFlowTo().size() > 0) {
			// Node n is a join node. Node n gets a new block.
			nBlock = new BasicBlock();
			nBlock.setLeader(n);
			blocks.add(nBlock);
		}
		
		nBlock.add(n);
		
		if (n.getChilderen().size() > 1) {
			// If n has more than one child the child nodes are leaders 
			// of a new block.
			for (Node child : n.getChilderen()) {
				if (!seen.contains(child)) {
					BasicBlock childBlock = new BasicBlock();
					childBlock.setLeader(child);
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
	 * @param blocks Block to display.
	 * @return In DOT format a graph.
	 */
	private String toDOTGraph(Node n, List<BasicBlock> blocks) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		
		if (blocks != null) {
			int nr = 0;
			for (BasicBlock b : blocks) {
				nr += 1;
				sb.append(String.format("\tsubgraph cluster%d {\n", nr));
				for (int i = b.size() - 1; i >= 0; i--) {
					String style = "";
					
					if (b.getLeader() == b.get(i)) {
						style = "[style=filled]";
					}
					
					sb.append(String.format("\t\t%s%s;\n", 
							b.get(i).getName(), style));
				}
				sb.append("\t}\n");
			}
		}
		
		List<Node> seen = new ArrayList<Node>();
		
		buildDOTGraph(n, null, sb, seen);
		
		sb.append("}");
		
		return sb.toString();
	}
	
	private void buildDOTGraph(Node n, Node parent, 
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
