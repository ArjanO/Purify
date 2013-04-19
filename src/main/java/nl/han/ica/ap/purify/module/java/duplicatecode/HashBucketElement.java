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

import java.util.ArrayList;
import java.util.List;

/**
 * Element of the {@link HashBucket}
 * 
 * @author Arjan
 */
public class HashBucketElement implements Comparable<HashBucketElement> {
	private List<Clone> elements;
	private int mass;
	
	public HashBucketElement() {
		elements = new ArrayList<Clone>();
		mass = 0;
	}
	
	/**
	 * Add a parse tree to the element.
	 * 
	 * @param candidate Clone candidate to add.
	 * @param mass Mass of the parse tree.
	 */
	public void put(Clone candidate, int mass) {
		elements.add(candidate);
		
		if (mass > this.mass) {
			this.mass = mass;
		}
	}
	
	/**
	 * Number of parse trees in this element.
	 * @return Number of parse trees.
	 */
	public int size() {
		return elements.size();
	}
	
	/**
	 * Get the clone candidate at index.
	 * 
	 * @param i Index.
	 * @return Clone candidate at the index.
	 */
	public Clone get(int i) {
		return elements.get(i);
	}

	/**
	 * Compare the HashBucketElement objects.
	 * 
	 * @param o other element
	 * @return < 0 if this is smaller, 0 if equal and > 0 if this is lager.
	 */
	@Override
	public int compareTo(HashBucketElement o) {
		return this.mass - o.mass;
	}
}
