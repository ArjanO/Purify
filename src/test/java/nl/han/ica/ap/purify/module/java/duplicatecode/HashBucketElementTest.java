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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@link HashBucketElement}
 * 
 * @author Arjan
 */
public class HashBucketElementTest {
	private HashBucketElement element;
	
	@Before
	public void setUp() {
		element = new HashBucketElement();
	}
	
	/**
	 * Test adding an element.
	 */
	@Test
	public void putElementTest() {
		Clone candidate = createMock(Clone.class);
		
		replay(candidate);
		
		element.put(candidate, 25);
		
		assertEquals(1, element.size());
		assertEquals(candidate, element.get(0));
		
		verify(candidate);
	}
	
	/**
	 * Test adding two elements.
	 */
	@Test
	public void put2ElementsTest() {		
		Clone candidate1 = createMock(Clone.class);
		Clone candidate2 = createMock(Clone.class);
		
		replay(candidate1);
		replay(candidate2);
		
		element.put(candidate1, 25);
		element.put(candidate2, 25);
		
		assertEquals(2, element.size());
		assertEquals(candidate1, element.get(0));
		assertEquals(candidate2, element.get(1));
		
		verify(candidate1);
		verify(candidate2);
	}
	
	/**
	 * Compare equal instances.
	 */
	@Test
	public void compare1Test() {
		// Build element.
		Clone candidate1 = createMock(Clone.class);
		
		replay(candidate1);
		
		element.put(candidate1, 10);
		
		// Build other element.
		HashBucketElement other = new HashBucketElement();
		
		Clone candidate2 = createMock(Clone.class);
		replay(candidate2);
		
		other.put(candidate2, 10);
		
		assertEquals(0, element.compareTo(other));
		
		verify(candidate1);
		verify(candidate2);
	}
	
	/**
	 * Compare other bigger then element.
	 */
	@Test
	public void compare2Test() {
		// Build element.
		Clone candidate1 = createMock(Clone.class);
		
		replay(candidate1);
		
		element.put(candidate1, 10);
		
		// Build other element.
		HashBucketElement other = new HashBucketElement();
		
		Clone candidate2 = createMock(Clone.class);
		replay(candidate2);
		
		other.put(candidate1, 11);
		other.put(candidate2, 9);
		
		assertEquals(-1, element.compareTo(other));
		
		verify(candidate1);
		verify(candidate2);
	}
}
