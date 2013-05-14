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
package nl.han.ica.ap.purify.language.java.callgraph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains the data structure of our call graph.
 * It contains an ArrayList with ClassNodes which in turn contains an ArrayList with MethodNodes.
 * It also contains an ArrayList with directed Edges.
 * Edges consist of a source MethodNode (the caller method) and a target MethodNode (the called method).
 * All nodes also contain variables in their proper scope. 
 * ClassNodes have global variables and MethodNodes have local variables.
 * 
 * @author Tim
 */
public class CallGraph {
	/** nodes is an ArrayList containing all ClassNodes of this graph */
	private ArrayList<ClassNode> nodes;
	
	/** edges is an ArrayList containing all Edges of this graph */
	private ArrayList<Edge> edges;
	
	public CallGraph() {
		nodes = new ArrayList<ClassNode>();
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * Adds a new ClassNode to the graph and any MethodNodes to the new ClassNode.
	 * 
	 * @param classID The name of the class
	 * @param methods HashMap with all methods this class has.
	 */
	public void addNode(String classID, HashMap<String, ArrayList<String>> methods) {
		ClassNode n = new ClassNode(classID);
		for(String methodID : methods.keySet()) {
			n.addMethodNode(methodID, methods.get(methodID));
		}
		nodes.add(n);
	}
	
	/**
	 * Gets the ClassNode with the specified name.
	 * 
	 * @param classID The name of the ClassNode you want to find.
	 * @return Returns the ClassNode with the specified name or null if it doesn't exist.
	 */
	public ClassNode getNode(String classID) {
		for(ClassNode n : nodes) {
			if(n.getClassID().equals(classID)) {
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Adds variables to a ClassNode.
	 * 
	 * @param classID The name of the class to add the variables to.
	 * @param variables HashMap with the scopes and variables.
	 */
	public void mapVariables(String classID,HashMap<String,HashMap<String,String>> variables) {
		ClassNode n = getNode(classID);
		if(n != null) {
			n.mapVariables(variables);
		}
	}
	
	/**
	 * Get the type of a specified variable
	 * 
	 * @param classID The name of the class the variable is in.
	 * @param scope The scope of the variable ('this' for global or the method name for local).
	 * @param variableID The name of the variable.
	 * @return Returns the type of the specified variable.
	 */
	public String getVariableType(String classID, String scope, String variableID) {
		ClassNode n = getNode(classID);
		if(n != null) {
			return n.getVariableType(scope, variableID);
		}
		return null;
	}
	
	/**
	 * Adds an Edge to the graph.
	 * 
	 * @param srcClass The name of the class the caller method is in.
	 * @param srcMethod The name of the caller method.
	 * @param trgClass The name of the class the called method is in.
	 * @param trgMethod The name of the called method.
	 */
	public void addEdge(String srcClass, String srcMethod, String trgClass, String trgMethod) {
		ClassNode src = getNode(srcClass);
		ClassNode trg = getNode(trgClass);
		if(src == null) {
			return;
			//throw new RuntimeException("Could not find ClassNode: " + srcClass);
		} else if (trg == null) {
			return;
			//throw new RuntimeException("Could not find ClassNode: " + trgClass);
		}
		edges.add(new Edge(src.getMethod(srcMethod),trg.getMethod(trgMethod)));
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	/**
	 * Gets all MethodNodes in this graph.
	 * 
	 * @return Returns an ArrayList containing all MethodNodes in this graph.
	 */
	public ArrayList<MethodNode> getAllMethods() {
		ArrayList<MethodNode> allnodes = new ArrayList<MethodNode>();
		for(ClassNode n : nodes) {
			for(MethodNode mn : n.getAllMethods()) {
				allnodes.add(mn);
			}
		}
		return allnodes;
	}
	
	/**
	 * Gets all methods containing the specified modifier.
	 * 
	 * @param modifier The modifier to check on.
	 * @return Returns an ArrayList containing all MethodNodes with the specified modifier.
	 */
	public ArrayList<MethodNode> getAllMethodsWithModifier(String modifier) {
		ArrayList<MethodNode> allnodes = new ArrayList<MethodNode>();
		for(ClassNode n : nodes) {
			for(MethodNode mn : n.getAllMethods()) {
				if(mn.hasModifier(modifier)) {
					allnodes.add(mn);
				}
			}
		}
		return allnodes;
	}

	/**
	 * Gets the return type of a method.
	 * 
	 * @param currentCallClass The class of the specified method.
	 * @param currentCall The specified method.
	 * @return Returns the return type of the specified method.
	 */
	public String getMethodReturnType(String currentCallClass, String currentCall) {
		ClassNode n = getNode(currentCallClass);
		if(n != null) {
			return n.getMethodReturnType(currentCall);
		}
		return null;
	}

	/**
	 * @param classID The name of the class the method is in.
	 * @param methodID The name of the method to find.
	 * @return Returns a MethodNode if one was found else returns null.
	 */
	public MethodNode getMethod(String classID, String methodID) {
		ClassNode n = getNode(classID);
		if(n != null) {
			return n.getMethod(methodID);
		}
		return null;
	}
	
	/**
	 * Looks through all edges to see if their target was really called or not.
	 */
	public void checkIfTruelyCalled() {
		for(Edge e : edges) {
			if(!e.src.called) {
				e.target.called = false;
			}
		}
	}
}
