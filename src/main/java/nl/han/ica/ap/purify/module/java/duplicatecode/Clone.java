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

import nl.han.ica.ap.purify.modles.SourceFile;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Stores information about a (candidate) clone.
 * 
 * @author Arjan
 */
public class Clone {
	/**
	 * Create a new store for a detected clone.
	 * 
	 * @param file Source file.
	 * @param tree ParseTree that is a clone.
	 */
	public Clone(SourceFile file, ParseTree tree) {
		
	}
	
	/**
	 * Get the set the source file.
	 * 
	 * @return Source file.
	 */
	public SourceFile getSourceFile() {
		return null;
	}
	
	/**
	 * Get the parse tree that is a clone.
	 * 
	 * @return Parse tree that is a clone.
	 */
	public ParseTree getParseTree() {
		return null;
	}
}
