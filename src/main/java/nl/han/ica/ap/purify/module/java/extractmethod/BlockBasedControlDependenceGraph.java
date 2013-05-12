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

import java.util.HashMap;
import java.util.Map;

/**
 * Block-based control dependence graph (BCDG) build by combining information
 * from PDG and CFG.
 * 
 * @author Arjan
 */
public class BlockBasedControlDependenceGraph {
	private static final String DOT_CONTROL_STYLE = "[style=dotted]";
	
	private BCDGNode entryNode;
	
	private ControlFlowGraph cfg;
	
	/**
	 * Block-based control dependence graph (BCDG) build by combining 
	 * the PDG and CFG.
	 * 
	 * @param cfg Control flow graph
	 * @param pdg Program dependence graph.
	 */
	public BlockBasedControlDependenceGraph(ControlFlowGraph cfg,
			ProgramDependenceGraph pdg) {
		entryNode = new BCDGNode(null);
		
		this.cfg = cfg;
		
		Map<BasicBlock, BCDGNode> mapping = new HashMap<BasicBlock, BCDGNode>();
		for (PDGNode dependency : pdg.getEntryNode().getControlDependencies()) {
			buildGraph(entryNode, dependency, mapping);
		}
	}
	
	/**
	 * Build the BCDG by getting information form the CFG and PDG.
	 * 
	 * @param parent Current BCDG node.
	 * @param currentPDG Current PDG node
	 * @param mapping Basic blocks that are mapped to BCDG node.
	 */
	private void buildGraph(BCDGNode parent, PDGNode currentPDG, 
			Map<BasicBlock, BCDGNode> mapping) {
		Node cfgNode = cfg.getNodeByStatement(currentPDG.getParseTree());
		
		if (cfgNode == null) {
			return;
		}
		
		BasicBlock basicBlock = cfgNode.getBasicBlock();
		
		if (basicBlock == null) {
			return;
		}
		
		BCDGNode node = null;
		if (!mapping.containsKey(basicBlock)) {
			node = new BCDGNode(basicBlock);
			
			parent.addDependence(node);
			
			mapping.put(basicBlock, node);
		} else {
			node = mapping.get(basicBlock);
		}
		
		for (PDGNode dependency : currentPDG.getControlDependencies()) {
			buildGraph(node, dependency, mapping);
		}
	}
	
	/**
	 * Create a DOT graph.
	 * 
	 * @return DOT graph as string.
	 */
	public String toDOTGraph() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		
		// Add dependencies (dotted lines).
		buildDOTNode(sb, entryNode);

		sb.append("}");
		
		return sb.toString();
	}
	
	/**
	 * Build node for the DOT graph.
	 * 
	 * @param sb StringBuilder for the DOT graph.
	 * @param node Node to add to the DOT graph.
	 */
	private void buildDOTNode(StringBuilder sb, BCDGNode node) {
		String parentName = "";
		
		if (node.getBasicBlock() != null) {
			parentName = node.getBasicBlock().getName();
		} else {
			parentName = "ROOT";
		}
		
		sb.append(String.format("\t%s;\n", parentName));
		
		for (BCDGNode dependency : node.getDependencies()) {
			sb.append(String.format("\t%s -> %s %s;\n", 
					parentName, 
					dependency.getBasicBlock().getName(),
					DOT_CONTROL_STYLE));
			
			buildDOTNode(sb, dependency);
		}
	}
}
