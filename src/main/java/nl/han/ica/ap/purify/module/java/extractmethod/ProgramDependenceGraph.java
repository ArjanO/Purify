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

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Program Dependence Graph (PDG).
 * 
 * @author Arjan
 */
public class ProgramDependenceGraph {
	private static final String DOT_CONTROL_STYLE = "[style=dotted]";
	
	private PDGNode entryNode;
	
	/**
	 * Program Dependence Graph (PDG).
	 * 
	 * @param tree Method parse tree
	 * @throws IllegalArgumentException if tree is null.
	 */
	public ProgramDependenceGraph(ParseTree tree) {
		if (tree == null) {
			throw new IllegalArgumentException();
		}
		
		ProgramDependenceGraphVisitor pdgVisitor = 
				new ProgramDependenceGraphVisitor();
		
		pdgVisitor.visit(tree);
		
		entryNode = pdgVisitor.getEntryNode();
	}
	
	/**
	 * Get the entry node of the graph.
	 * 
	 * @return Entry node.
	 */
	public PDGNode getEntryNode() {
		return entryNode;
	}
	
	/**
	 * Create a DOT graph.
	 * 
	 * @return DOT graph as string.
	 */
	public String toDOTGraph() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		
		sb.append(String.format("\t%s;\n", entryNode.getName()));
		
		// Add dependencies (dotted lines).
		buildDOTControl(sb, entryNode);
		
		sb.append("}");
		
		return sb.toString();
	}
	
	/**
	 * Build DOT graph for nodes.
	 * 
	 * @param sb StringBuffer with builded graph.
	 * @param node Current node.
	 */
	private void buildDOTControl(StringBuilder sb, PDGNode node) {
		for (PDGNode dependence : node.getControlDependencies()) {					
			sb.append(String.format("\t%s -> %s%s;\n", 
					node.getName(), dependence.getName(), DOT_CONTROL_STYLE));
			
			buildDOTControl(sb, dependence);
		}
	}
}
