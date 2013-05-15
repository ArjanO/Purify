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
package nl.han.ica.ap.purify.module.java.magicnumber;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;
import nl.han.ica.ap.purify.language.java.JavaParser.ClassBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the magic number storage class.
 * 
 * @author Arjan
 */
public class MagicNumberTest {
	private ClassBodyContext classBodyContext;
	private MagicNumber magicNumber;
	
	@Before
	public void before() {
		classBodyContext = createMock(ClassBodyContext.class);
		
		replay(classBodyContext);
	}
	
	@After
	public void after() {
		verify(classBodyContext);
	}
	
	@Test
	public void getContextTest() {
		magicNumber = new MagicNumber("25", classBodyContext);
		
		assertEquals(magicNumber.getClassBodyContext(), classBodyContext);
	}
	
	@Test
	public void addZeroItemsTest() {
		magicNumber = new MagicNumber("25", classBodyContext);
		
		assertEquals(0, magicNumber.size());
	}
	
	@Test
	public void addOneItemTest() {
		magicNumber = new MagicNumber("25", classBodyContext);
		
		LiteralContext literalMock = createMock(LiteralContext.class);
		
		magicNumber.addLiteral(literalMock);
		
		replay(literalMock); // Expect no method calls.
		assertEquals(1, magicNumber.size());
	}
	
	@Test
	public void getLiteralNameTest() {
		magicNumber = new MagicNumber("\"test\"", classBodyContext);
		
		assertEquals("\"test\"", magicNumber.getLiteral());
	}
	
	@Test(expected = NullPointerException.class)
	public void nullTest() {
		magicNumber = new MagicNumber(null, classBodyContext);
	}
	
	@Test(expected = NullPointerException.class)
	public void nullClassBodyContextTest() {
		magicNumber = new MagicNumber("25", null);
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullTest() {
		magicNumber = new MagicNumber("26", classBodyContext);
		magicNumber.addLiteral(null);
	}
}
