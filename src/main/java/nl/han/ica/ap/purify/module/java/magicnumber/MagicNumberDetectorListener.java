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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaBaseListener;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralBooleanContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralCharacterContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralFloatContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralIntegerContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralStringContext;

/**
 * Detect magic numbers. Note: This detector is build for only one source file!
 * 
 * @author Arjan
 */
public class MagicNumberDetectorListener extends JavaBaseListener {
	public HashMap<String, MagicNumber> literals;
	
	public MagicNumberDetectorListener() {
		literals = new HashMap<String, MagicNumber>();
	}
	
	/**
	 * Get a list with the magic numbers found so far. 
	 * 
	 * @return List with magic numbers found so far.
	 */
	public List<MagicNumber> getMagicNumbers() {
		List<MagicNumber> result = new ArrayList<MagicNumber>();
		
		Iterator<String> it = literals.keySet().iterator();
		
		while(it.hasNext()) {
	    	String key = it.next();
	    	MagicNumber magicNumber = literals.get(key);
	    	
	    	if (magicNumber.size() > 1) {
	    		result.add(magicNumber);
	    	}
	    }
		
		return result;
	}
	
	/**
	 * enterLiteralBoolean is called every time the tree waker finds boolean.
	 */
	@Override
	public void enterLiteralBoolean(LiteralBooleanContext ctx) {
		enterLiteral(ctx);
	}
	
	/**
	 * enterLiteralFloat is called every time the tree waker finds floating 
	 * point. For example 25.0 or -2.0.
	 */
	@Override
	public void enterLiteralFloat(LiteralFloatContext ctx) {
		enterLiteral(ctx);
	}
	
	/**
	 * enterLiteralCharacter is called every time the tree waker finds 
	 * a character For example 'A'.
	 */
	@Override
	public void enterLiteralCharacter(LiteralCharacterContext ctx) {
		enterLiteral(ctx);
	}
	
	/**
	 * enterLiteralInteger is called every time the tree waker finds 
	 * a integer for example 15.
	 */
	@Override
	public void enterLiteralInteger(LiteralIntegerContext ctx) {
		enterLiteral(ctx);
	}

	/**
	 * enterLiteralNull is called every time the tree waker finds a string. 
	 * For example "test".
	 */
	@Override
	public void enterLiteralString(LiteralStringContext ctx) {
		enterLiteral(ctx);
	}
	
	/**
	 * enterLiteral is called every time the tree waker finds literals.
	 * Example of literals: 25, "test", 2.5.
	 */
	private void enterLiteral(LiteralContext ctx) {
		if (ctx != null && ctx.getText() != null) {
			MagicNumber magicNumber;
			
			if (!literals.containsKey(ctx.getText())) {
				magicNumber = new MagicNumber(ctx.getText());
				
				literals.put(ctx.getText(), magicNumber);
			} else {
				magicNumber = literals.get(ctx.getText());
			}
			
			magicNumber.addLiteral(ctx);
		}
	}
}
