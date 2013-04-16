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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link Method}
 * 
 * @author Arjan
 */
public class MethodTest {
	private Method method;
	
	@Before
	public void setUp() {
		method = new Method("test");
	}
	
	@Test
	public void getNameTest() {
		assertEquals("test", method.getName());
	}
	
	@Test
	public void addParameterTest() {
		method.addParameter("number");
		
		// If a parameter is added this is default unused.
		assertEquals(1, method.getUnusedPrametersSize());
	}
	
	@Test
	public void addParametersTest() {
		method.addParameter("number");
		method.addParameter("name");
		
		// If a parameter is added this is default unused.
		assertEquals(2, method.getUnusedPrametersSize());
	}
	
	@Test
	public void usedVariable1Test() {
		method.addParameter("number");
		
		method.usedVariable("number");
		
		assertEquals(0, method.getUnusedPrametersSize());
		assertEquals(0, method.getUnusedParameters().size());
	}
	
	@Test
	public void usedVariable2Test() {
		method.addParameter("number");
		method.addParameter("name");
		
		method.usedVariable("number");
		
		assertEquals(1, method.getUnusedPrametersSize());
		assertEquals(1, method.getUnusedParameters().size());
		assertTrue(method.getUnusedParameters().contains("name"));
	}
}
