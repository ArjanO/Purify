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

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Hash bucket contains hashes and parse trees that match this hash.
 * 
 * @author Arjan
 */
public class HashBucket {
	private HashMap<Integer, HashBucketElement> bucket;
	
	public HashBucket() {
		bucket = new HashMap<Integer, HashBucketElement>();
	}
	
	/**
	 * Add a parse tree to the bucket.
	 * 
	 * @param hash Hash of the parse tree.
	 * @param candidate Candidate clone.
	 * @param mass The mass (number of nodes) of the tree.
	 */
	public void put(int hash, Clone candidate, int mass) {
		HashBucketElement element;
		
		if (!bucket.containsKey(hash)) {
			element = new HashBucketElement();
			
			bucket.put(hash, element);
		} else {
			element = bucket.get(hash);
		}
		
		element.put(candidate, mass);
	}
	
	/**
	 * Get all the {@link HashBucketElement} items with more than one Parse
	 * Tree.
	 * @return List of {@link HashBucketElement} with more than one Parse Tree.
	 */
	public TreeSet<HashBucketElement> getDuplicates() {
		TreeSet<HashBucketElement> result = new TreeSet<HashBucketElement>();
		
		Iterator<Integer> it = bucket.keySet().iterator();
		 
	    while(it.hasNext()) {
	    	Integer key = it.next();
	    	
	    	if (bucket.get(key).size() > 1) {
	    		result.add(bucket.get(key));
	    	}
	    }
	    
	    return result;
	}
}
