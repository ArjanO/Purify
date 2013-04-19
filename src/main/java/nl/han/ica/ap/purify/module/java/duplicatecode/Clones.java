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

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Stores detected clones.
 * 
 * @author Arjan
 */
public class Clones {
	private List<List<Clone>> clones; 
	
	/**
	 * Stores detected clones.
	 */
	public Clones() {
		clones = new ArrayList<List<Clone>>();
	}
	
	/**
	 * Add a clone pair. If the clone pair already exists as subtree the 
	 * clone of the subtree is removed.
	 * 
	 * @param left First subtree
	 * @param right Second subtree
	 */
	public void addClonePair(Clone left, Clone right) {
		removeSubtreeClones(left, right);
		
		// Add to a existing pair if left or right is already in a pair.
		for (List<Clone> pair : clones) {
			for (Clone cloneItem : pair) {
				if (cloneItem.getParseTree().equals(left.getParseTree())) {
					pair.add(right);
					return;
				} else if (cloneItem.getParseTree().equals(
						right.getParseTree())) {
					pair.add(left);
					return;
				}
			}
		}
		
		// The clone does not exists, add a new pair.
		List<Clone> clone = new ArrayList<Clone>();
		clone.add(left);
		clone.add(right);
		
		clones.add(clone);
	}
	
	/**
	 * Get the number of clones.
	 * 
	 * @return number of clones.
	 */
	public int size() {
		return clones.size();
	}
	
	/**
	 * Get clone pairs by index.
	 * 
	 * @param index Index
	 * @return Clone pairs at the index.
	 */
	public List<Clone> getItem(int index) {
		return clones.get(index);
	}
	
	/**
	 * Remove subtree clones of the subtree left and right.
	 * 
	 * @param left Left subtree
	 * @param right Right subtree
	 */
	private void removeSubtreeClones(Clone left, Clone right) {
		for (int i = clones.size() -1; i >= 0; i--) {
			removeSubtreeClones(clones.get(i), left, right);
		}
	}
	
	/**
	 * Remove subtree clones of the subtree.
	 * 
	 * @param items Clone pair items to test.
	 * @param left Left subtree
	 * @param right Right subtree
	 */
	private void removeSubtreeClones(List<Clone> items, 
			Clone left, Clone right) {
		Clone matchLeft = null;
		Clone matchRight = null;
		
		for (int i = items.size() - 1; i >= 0; i--) {
			if (isSubtree(left.getParseTree(), items.get(i).getParseTree())) {
				matchLeft = items.get(i);
			} else if (isSubtree(right.getParseTree(), 
					items.get(i).getParseTree())) {
				matchRight = items.get(i);
			}
		}
		
		if (matchLeft != null && matchRight != null) {
			if (items.size() == 2) {
				clones.remove(items);
			} else if (items.size() > 3) {
				/*
				 * There are more clones (for example 4). The remove of left
				 * and right keep the other clones.
				 */
				items.remove(matchLeft);
				items.remove(matchRight);
			} else {
				/* 
				 * There are more clones (for example 3). Remove the left so
				 * that the other clone stile exists.
				 */
				items.remove(matchLeft);
			}
		}
	}
	
	/**
	 * Check if subtree is a subtree of tree.
	 * 
	 * @param tree Tree
	 * @param subtree Tree
	 * @return true if subtree is a subtree of tree.
	 */
	private boolean isSubtree(ParseTree tree, ParseTree subtree) {		
		if (tree.equals(subtree)) {
			return true;
		}
		
		for (int i = tree.getChildCount() - 1; i >= 0; i--) {
			if (isSubtree(tree.getChild(i), subtree)) {
				return true;
			}
		}
		
		return false;
	}
}
