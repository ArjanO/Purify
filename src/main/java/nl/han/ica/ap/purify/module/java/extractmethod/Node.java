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
 * Nodes for the CFG control flow graph
 * 
 * @author Arjan
 */
public class Node {
	private List<Node> parents;
	private List<Node> childeren;
	private ParseTree parseTreeNode;
	private String name;
	
	public Node(ParseTree parseNode) {
		parents = new ArrayList<Node>();
		childeren = new ArrayList<Node>();
		parseTreeNode = parseNode;
	}
	
	/**
	 * Add a new child. This method also add this node as parent to child.
	 * 
	 * @param child Child to add.
	 */
	public void addChild(Node child) {
		if (!childeren.contains(child)) { // Don't add a child twice.
			childeren.add(child);
			
			child.addParent(this);
		}
	}
	
	/**
	 * Set the name of the node.
	 * 
	 * @param name Name of the node.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of the node.
	 * 
	 * @return Name of the node.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the parse tree (statement) associated with this node.
	 * 
	 * @return Parse tree associated with this node.
	 */
	public ParseTree getParseTree() {
		return parseTreeNode;
	}
	
	/**
	 * Get all the parents of this node.
	 * 
	 * @return Parent nodes.
	 */
	public List<Node> getParents() {
		return parents;
	}
	
	/**
	 * Get all the children of this node.
	 * 
	 * @return Child nodes.
	 */
	public List<Node> getChilderen() {
		return childeren;
	}
	
	/**
	 * Add a parent node.
	 * 
	 * @param parent Parent to add.
	 */
	private void addParent(Node parent) {
		if (!parents.contains(parent)) { // Don't add parent twice.
			parents.add(parent);
		}
	}
}
