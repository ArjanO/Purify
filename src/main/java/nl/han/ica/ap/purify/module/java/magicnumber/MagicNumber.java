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
import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaParser.ClassBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralContext;

/**
 * Stores magic number detections.
 * 
 * @author Arjan
 */
public class MagicNumber {
	private String literal;
	private ClassBodyContext classBodyContext;
	private List<LiteralContext> contexts;
	
	public MagicNumber(String literal, ClassBodyContext classBody) {
		if (literal == null || classBody == null) {
			throw new NullPointerException();
		}
		
		this.literal = literal;
		this.classBodyContext = classBody;
		this.contexts = new ArrayList<LiteralContext>();
	}
	
	/**
	 * Get the literal.
	 * 
	 * @return Literal.
	 */
	public String getLiteral() {
		return this.literal;
	}
	
	/**
	 * Get method body context
	 * 
	 * @return Class body context.
	 */
	public ClassBodyContext getClassBodyContext() {
		return this.classBodyContext;
	}
	
	/**
	 * Add a literal to the context.
	 * 
	 * @param context LIteralContext to add.
	 */
	public void addLiteral(LiteralContext context) {
		if (context == null) {
			throw new NullPointerException();
		}
		
		this.contexts.add(context);
	}

	/**
	 * Get the context at the index.
	 * 
	 * @param index Index of the item.
	 * @return Context at the index.
	 */
	public LiteralContext getContext(int index) {
		return this.contexts.get(index);
	}
	
	/**
	 * Get the number of detections.
	 * 
	 * @return Number of detections.
	 */
	public int size() {
		return this.contexts.size();
	}
}
