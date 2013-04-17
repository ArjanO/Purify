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

import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaParser.LiteralIntegerContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralNullContext;

import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test code for the magic number detector. 
 * 
 * @author Arjan
 */
public class MagicNumberDetectorTest {
	private MagicNumberDetector detector;
	
	@Before
	public void setUp() {
		detector = new MagicNumberDetector();
	}
	
	@Test
	public void enterLiteralTest() {
		LiteralIntegerContext literalMock = 
				createMock(LiteralIntegerContext.class);
		
		expect(literalMock.getText()).andReturn("25").anyTimes();
		replay(literalMock);
		
		detector.enterLiteralInteger(literalMock);
		
		verify(literalMock);
	}
	
	@Test
	public void enterLiteralNullTest() {
		LiteralNullContext literalMock = 
				createMock(LiteralNullContext.class);
		replay(literalMock);
		
		detector.enterLiteralNull(literalMock);
		
		verify(literalMock);
	}
	
	@Test
	public void addTreeLiterals() {
		LiteralIntegerContext literal1Mock = 
				createMock(LiteralIntegerContext.class);
		LiteralIntegerContext literal2Mock = 
				createMock(LiteralIntegerContext.class);
		LiteralIntegerContext literal3Mock = 
				createMock(LiteralIntegerContext.class);
		
		expect(literal1Mock.getText()).andReturn("25").anyTimes();
		expect(literal2Mock.getText()).andReturn("10").anyTimes();
		expect(literal3Mock.getText()).andReturn("25").anyTimes();
		
		replay(literal1Mock);
		replay(literal2Mock);
		replay(literal3Mock);
		
		detector.enterLiteralInteger(literal1Mock);
		detector.enterLiteralInteger(literal2Mock);
		detector.enterLiteralInteger(literal3Mock);
		
		verify(literal1Mock);
		verify(literal2Mock);
		verify(literal3Mock);
		
		List<MagicNumber> result = detector.getMagicNumbers();
		
		assertEquals("25", result.get(0).getLiteral());
		assertEquals(2, result.get(0).size());
	}
}
