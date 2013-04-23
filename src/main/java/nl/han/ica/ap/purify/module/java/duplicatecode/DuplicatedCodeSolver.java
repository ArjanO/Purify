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

import java.util.List;

import nl.han.ica.ap.purify.language.java.util.Method;
import nl.han.ica.ap.purify.modles.ISolver;
import nl.han.ica.ap.purify.modles.SourceFile;

/**
 * Solve the duplicated code issues.
 * 
 * @author Arjan
 */
public class DuplicatedCodeSolver implements ISolver {
	/**
	 * Solve the files issues.
	 * 
	 * @param file File to solve.
	 */
	@Override
	public void solve(SourceFile file) {
		if (file == null) {
			throw new NullPointerException();
		}
		
		for (int i = file.getIssuesSize() - 1; i >= 0; i++) {
			if (file.getIssue(i) instanceof DuplicatedCodeIssue) {
				solve((DuplicatedCodeIssue)file.getIssue(i));
			}
		}
	}
	
	private void solve(DuplicatedCodeIssue issue) {
		int methodNumber = numberOfMethodClones(issue.getClones());
		
		if (methodNumber == 0) {
			// No methods so create a new method.
		} else if (methodNumber == 1) {
			// One method. So let the other code call that method.
		} else {
			// More methods. Remove one of the methods.
		}
	}
	
	/**
	 * Get the number of methods that is a clone.
	 * 
	 * @param clones Clones.
	 * @return Number of methods that are a clone.
	 */
	private int numberOfMethodClones(List<Clone> clones) {
		int count = 0;
		
		for (Clone c : clones) {
			if (Method.isParseTreeMethodBody(c.getParseTree())) {
				count++;
			}
		}
		
		return count;
	}
}
