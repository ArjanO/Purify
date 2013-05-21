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
package nl.han.ica.ap.purify.module.java.removeparameter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MethodDeclarationContext;

/**
 * Unit test for {@link RemoveParameterIssue}
 * 
 * @author Arjan
 */
public class RemoveParameterIssueTest {
	private MethodDeclarationContext method;
	private List<FormalParameterDeclsRestContext> params;
	
	@Before
	public void before() {
		method = createMock(MethodDeclarationContext.class);
		params = new ArrayList<FormalParameterDeclsRestContext>();
		
		replay(method);
	}
	
	@After
	public void after() {
		verify(method);
		
		for (FormalParameterDeclsRestContext param : params) {
			verify(param);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void null1Test() {
		new RemoveParameterIssue(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void null2Test() {
		new RemoveParameterIssue(method, null);
	}
	
	@Test
	public void getMethodTest() {
		createParameter();
		
		RemoveParameterIssue issue = new RemoveParameterIssue(method, params);
		
		assertEquals(method, issue.getMethod());
		assertEquals(1, issue.getParameters().size());
	}
	
	private void createParameter() {
		FormalParameterDeclsRestContext param =
				createMock(FormalParameterDeclsRestContext.class);
		
		replay(param);
		
		params.add(param);
	}
}
