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
package nl.han.ica.ap.purify.modles;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link SourceFile}
 * 
 * @author Arjan
 */
public class SourceFileTest {
	private SourceFile file;
	
	CommonTokenStream tokensMock;
	ParseTree parseTree;
	
	@Before
	public void setUp() {
		tokensMock = createMock(CommonTokenStream.class);
		parseTree = createMock(ParseTree.class);
		
		replay(tokensMock);
		replay(parseTree);
		
		file = new SourceFile("Test.java", tokensMock, parseTree);
	}
	
	@After
	public void shutdown() {
		verify(tokensMock);
		verify(parseTree);
	}
	
	@Test
	public void getPathTest() {
		assertEquals("Test.java", file.getPath());
	}
	
	@Test
	public void getTokenStreamTest() {
		assertEquals(tokensMock, file.getTokenStream());
	}
	
	@Test
	public void getParseTree() {
		assertEquals(parseTree, file.getParseTree());
	}
	
	@Test
	public void addIssue() {
		IIssue issue = createMock(IIssue.class);
		
		replay(issue);
		
		assertEquals(0, file.getIssuesSize());
		
		file.addIssue(issue);
		
		assertEquals(1, file.getIssuesSize());
		assertEquals(issue, file.getIssue(0));
		
		verify(issue);
	}
}
