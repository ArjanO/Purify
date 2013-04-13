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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import nl.han.ica.ap.purify.language.java.JavaParser.MethodBodyContext;
import nl.han.ica.ap.purify.test.tools.ParserTools;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * A hash don't have to be unique! It is only a fast way to find candidate 
 * duplicated code. This test only test cases that are duplicated code.
 * 
 * @author Arjan
 */
@RunWith(Parameterized.class)
public class HashVisitorExpressionTest {
private static final String FILE = "/duplicatecode/ExpressionTest.java";
	
	private MethodBodyContext method1;
	private MethodBodyContext method2;
	
	private TreeSet<String> localVariablesMethod1;
	private TreeSet<String> localVariablesMethod2;
	
	public HashVisitorExpressionTest(String method1Name, String[] localVars1, 
			String method2Name, String[] localVars2) {
		ParseTree tree = ParserTools.getParseTree(FILE);
		
		method1 = ParserTools.getMethodBody(tree, method1Name);
		method2 = ParserTools.getMethodBody(tree, method2Name);
		
		localVariablesMethod1 = new TreeSet<String>();
		for (int i = 0; i < localVars1.length; i++) {
			localVariablesMethod1.add(localVars1[i]);
		}
		
		localVariablesMethod2 = new TreeSet<String>();
		for (int i = 0; i < localVars2.length; i++) {
			localVariablesMethod2.add(localVars2[i]);
		}
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] result = new Object[][] {
				{
					"method1", new String[] { "nr" },
					"method2", new String[] { "n" }
				},
				{
					"method3", new String[] { "nr" },
					"method4", new String[] { "n" }
				}
		};
		
		return Arrays.asList(result);
	}
	
	@Test
	public void hasTest() {
		HashVisitor hashVisitor;
		
		int iHash1;
		int iHash2;
		
		hashVisitor = new HashVisitor(localVariablesMethod1);
		iHash1 = hashVisitor.visit(method1);
		
		hashVisitor = new HashVisitor(localVariablesMethod2);
		iHash2 = hashVisitor.visit(method2);
		
		assertEquals(iHash1, iHash2);
	}
}