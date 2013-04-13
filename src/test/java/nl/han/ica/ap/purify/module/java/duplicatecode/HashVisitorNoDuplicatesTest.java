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
package nl.han.ica.ap.purify.module.java.duplicatecode;

import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.test.tools.ParserTools;

/**
 * A hash don't have to be unique! It is only a fast way to find candidate 
 * duplicated code.
 * 
 * This test is evil because it tests if methods that are not the 
 * same don't have the same hash. 
 * 
 * @author Arjan
 */
public class HashVisitorNoDuplicatesTest {
	private static final String TEST_WHILE_FILE = 
			"/duplicatecode/WhileTest.java";
	
	private static final String TEST_IF_FILE = 
			"/duplicatecode/IfTest.java";
	
	private static final String TEST_FOR_FILE = 
			"/duplicatecode/ForTest.java";
	
	private static final String TEST_EXPRESSION_FILE = 
			"/duplicatecode/ExpressionTest.java";
	
	private static final int MAX_SAME_HASH = 0;
	
	private HashMap<MethodBodyContext, TreeSet<String>> methods;	

	@Before
	public void setUp() {		
		methods = new HashMap<MethodBodyContext, TreeSet<String>>();
		
		addMethod(TEST_WHILE_FILE, "method1", new String[] { "nr", "i" });
		addMethod(TEST_WHILE_FILE, "method5", new String[] { "nr", "i" });
		addMethod(TEST_WHILE_FILE, "method6", new String[] { "nr", "j" });
		
		addMethod(TEST_IF_FILE, "method1", new String[] { "nr", "i" });
		addMethod(TEST_IF_FILE, "method3", new String[] { "nr", "i" });
		addMethod(TEST_IF_FILE, "method5", new String[] { "nr", "i" });
		addMethod(TEST_IF_FILE, "method7", new String[] { "nr", "i" });
		addMethod(TEST_IF_FILE, "method9", new String[] { "retrn", "i" });
		addMethod(TEST_IF_FILE, "method11", new String[] { "retrn", "d" });
		
		addMethod(TEST_FOR_FILE, "method1", 
				new String[] { "nr", "i", "result" });
		addMethod(TEST_FOR_FILE, "method3", 
				new String[] { "number", "j", "result" });
		
		addMethod(TEST_EXPRESSION_FILE, "method1", new String[] { "nr" });
		addMethod(TEST_EXPRESSION_FILE, "method3", new String[] { "nr" });
	}
	
	/**
	 * Test if not to match methods return the same hash. 
	 */
	@Test
	public void hashNoDuplicatesTest() {
		HashMap<Integer, Integer> bucket = new HashMap<Integer, Integer>();
		
		for(Entry<MethodBodyContext, TreeSet<String>> h : methods.entrySet()) {
			int hash;
			
			HashVisitor hashVisitor = new HashVisitor(h.getValue());
			hash = hashVisitor.visit(h.getKey());
			
			if (bucket.containsKey(hash)) {
				bucket.put(hash, bucket.get(hash) + 1);
			} else {
				bucket.put(hash, 1);
			}
		}
		
		// Get all number of hashes that are duplicated.
		int sameHash = 0;
		
		Iterator<Integer> it = bucket.keySet().iterator();
		
	    while(it.hasNext()) {
	    	int key = it.next();
	    	
	    	if (bucket.get(key) > 1) {
	    		sameHash += bucket.get(key);
	    	}
	    }
	    
	    assertThat(sameHash, lessThanOrEqualTo(MAX_SAME_HASH));
	}
	
	/**
	 * Add a method to the methods list.
	 * 
	 * @param file Filename that contains the method.
	 * @param methodName Method name.
	 * @param vars Local variables and parameters used in the method.
	 */
	private void addMethod(String file, String methodName, String[] vars) {
		MethodBodyContext method;
		TreeSet<String> localVariables;
		
		ParseTree tree = ParserTools.getParseTree(file);
		
		method = ParserTools.getMethodBody(tree, methodName);
		assertNotNull(method);
		
		localVariables = new TreeSet<String>();
		for (int i = 0; i < vars.length; i++) {
			localVariables.add(vars[i]);
		}
		
		methods.put(method, localVariables);
	}
}
