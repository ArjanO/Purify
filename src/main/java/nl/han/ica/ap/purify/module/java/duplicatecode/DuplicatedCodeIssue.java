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

import nl.han.ica.ap.purify.modles.IIssue;

/**
 * Stores information about detected duplicated code.
 * 
 * @author Arjan
 */
class DuplicatedCodeIssue implements IIssue {
	private List<Clone> clones;
	
	/**
	 * Create a new duplicated code issue.
	 * 
	 * @param clones Duplicated code clones.
	 * @throws NullPointerException if clone is null.
	 */
	public DuplicatedCodeIssue(List<Clone> clones) {
		if (clones == null) {
			throw new NullPointerException();
		}
		
		this.clones = clones;
	}
	
	/**
	 * ToString method for the {@link App}. In the future reporting of issues
	 * is needed.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("-----------------------------------\n");
		sb.append(clones.get(0).getParseTree().getText());
		sb.append("\n");
		
		for (int i = clones.size() - 1; i >= 0; i--) {
			sb.append(clones.get(i).getSourceFile().getPath());
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
