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

import nl.han.ica.ap.purify.modles.IDetector;
import nl.han.ica.ap.purify.modles.SourceFile;

/**
 * Find duplicated code.
 * 
 * @author Arjan
 */
public class DuplicatedCodeDetector implements IDetector {
	private DuplicatedCodeDetectorVisitor detector;
	
	public DuplicatedCodeDetector() {
		detector = new DuplicatedCodeDetectorVisitor();
	}
	
	/**
	 * Collect information but don't create issues.
	 * 
	 * @param file File to analyze.
	 */
	@Override
	public void analyze(SourceFile file) {
		detector.setSourceFile(file);
		detector.visit(file.getParseTree());
	}

	/**
	 * Detect all the issues and add them to file.
	 */
	@Override
	public void detect() {
		Clones clones = detector.getClones();
		
		for (int i = clones.size() - 1; i >= 0; i--) {
			addIssues(clones.getItem(i));
		}
	}
	
	/**
	 * Assign clones to files that contains the clones.
	 * 
	 * @param clones Clones to add to the files.
	 */
	private void addIssues(List<Clone> clones) {
		DuplicatedCodeIssue issue = new DuplicatedCodeIssue(clones);
		
		for (int i = clones.size() - 1; i >= 0; i--) {
			SourceFile file = clones.get(i).getSourceFile();
			
			file.addIssue(issue);
		}
	}
}
