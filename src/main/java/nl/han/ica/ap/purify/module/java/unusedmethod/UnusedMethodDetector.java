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
package nl.han.ica.ap.purify.module.java.unusedmethod;

import java.util.ArrayList;

import nl.han.ica.ap.purify.language.java.callgraph.CallGraph;
import nl.han.ica.ap.purify.language.java.callgraph.Edge;
import nl.han.ica.ap.purify.language.java.callgraph.MethodNode;

/**
 * @author Tim
 *
 */
public class UnusedMethodDetector {
	CallGraph graph;
	public ArrayList<MethodNode> uncalledmethods;
	
	public UnusedMethodDetector(CallGraph graph) {
		this.graph = graph;
		detectIssues();
	}
	
	private void detectIssues() {
		getCalledMethods(graph.getEdges());
		uncalledmethods = getUnCalledMethods(graph.getAllMethods());
	}
	
	private void getCalledMethods(ArrayList<Edge> edges) {
		for(Edge e : edges) {
			if(!e.src.called) {
				e.target.called = false;
			}
		}
	}
	
	private ArrayList<MethodNode> getUnCalledMethods(ArrayList<MethodNode> methods) {
		ArrayList<MethodNode> uncalledmethods = new ArrayList<MethodNode>();
		for(MethodNode m : methods) {
			if(!m.called) {
				uncalledmethods.add(m);
			}
		}
		
		return uncalledmethods;
	}
}
