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

import static org.junit.Assert.*;

import java.util.ArrayList;

import nl.han.ica.ap.purify.language.java.callgraph.CallGraph;
import nl.han.ica.ap.purify.language.java.callgraph.MethodNode;
import nl.han.ica.ap.purify.language.java.callgraph.listeners.ClassNodeListener;
import nl.han.ica.ap.purify.language.java.callgraph.listeners.EdgeListener;
import nl.han.ica.ap.purify.modles.SourceFile;
import nl.han.ica.ap.purify.test.tools.ParserTools;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tim
 *
 */
public class UnusedMethodDetectorTest {

	private static final String FILE1 = "/unusedmethod/Demo1.java";
	private static final String FILE2 = "/unusedmethod/Demo2.java";
	private static final String FILE3 = "/unusedmethod/Demo3.java";
	private CallGraph graph;
	private ClassNodeListener classNodelistener;
	private EdgeListener edgelistener;
	private ParseTreeWalker walker;
	private UnusedMethodDetector umd;
	
	private SourceFile file1;
	private SourceFile file2;
	private SourceFile file3;
	
	@Before
	public void before() {
		graph = new CallGraph();
		classNodelistener = new ClassNodeListener(graph);
		edgelistener = new EdgeListener(graph);
		walker = new ParseTreeWalker();
		
		file1 = ParserTools.getParseTreeSourceFile(FILE1);
		file2 = ParserTools.getParseTreeSourceFile(FILE2);
		file3 = ParserTools.getParseTreeSourceFile(FILE3);
		
		classNodelistener.setCurrentSourceFile(file1);
		walker.walk(classNodelistener, file1.getParseTree());
		
		classNodelistener.setCurrentSourceFile(file2);
		walker.walk(classNodelistener, file2.getParseTree());
		
		classNodelistener.setCurrentSourceFile(file3);
		walker.walk(classNodelistener, file3.getParseTree());
		
		edgelistener.setSourceFile(file1);
		walker.walk(edgelistener, file1.getParseTree());
		
		edgelistener.setSourceFile(file2);
		walker.walk(edgelistener, file2.getParseTree());
		
		edgelistener.setSourceFile(file3);
		walker.walk(edgelistener, file3.getParseTree());
		
		umd = new UnusedMethodDetector();
		umd.setGraph(graph);
	}
	
	@Test
	public void unCalledMethodsTest() {
		umd.detect();
		
		ArrayList<MethodNode> expectedmethods = new ArrayList<MethodNode>();
		expectedmethods.add(graph.getMethod("Demo1","uncalledmethod1( )"));
		expectedmethods.add(graph.getMethod("Demo1","uncalledmethod2( )"));
		expectedmethods.add(graph.getMethod("Demo1","uncalledmethod3( )"));
		expectedmethods.add(graph.getMethod("Demo2","uncalledmethod1( )"));
		expectedmethods.add(graph.getMethod("Demo3","uncalledmethod( )"));
		
		int iIssueSize = file1.getIssuesSize();
		iIssueSize += file2.getIssuesSize();
		iIssueSize += file3.getIssuesSize();
		
		assertEquals(expectedmethods.size(), iIssueSize);
	}
}
