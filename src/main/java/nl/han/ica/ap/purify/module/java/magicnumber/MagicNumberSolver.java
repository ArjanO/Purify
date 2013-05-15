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

import java.util.HashMap;
import java.util.Map;

import nl.han.ica.ap.purify.language.java.JavaParser.ClassBodyContext;
import nl.han.ica.ap.purify.language.java.JavaParser.LiteralContext;
import nl.han.ica.ap.purify.modles.ISolver;
import nl.han.ica.ap.purify.modles.SourceFile;

/**
 * Solve the magic number issues.
 * 
 * @author Arjan
 */
public class MagicNumberSolver implements ISolver {
	private int magicNumberNr;
	private Map<ClassBodyContext, Map<String, String>> literals;
	
	public MagicNumberSolver() {
		magicNumberNr = 0;
		literals = new HashMap<ClassBodyContext, Map<String,String>>();
	}
	
	/**
	 * Solve all the issues of the file.
	 */
	@Override
	public void solve(SourceFile file) {
		for (int i = file.getIssuesSize() - 1; i >= 0; i--) {
			if (file.getIssue(i) instanceof MagicNumberIssue) {
				solveMagicNumber(file, (MagicNumberIssue)file.getIssue(i));
			}
		}
	}
	
	private void solveMagicNumber(SourceFile file, MagicNumberIssue issue) {
		ClassBodyContext classBodyContext =
				issue.getMagicNumber().getClassBodyContext();
		
		Map<String, String> classLiterals;
		
		if (!literals.containsKey(classBodyContext)) {
			classLiterals = new HashMap<String, String>();
			
			literals.put(classBodyContext, classLiterals);
		} else {
			classLiterals = literals.get(classBodyContext);
		}
		
		String literal = issue.getMagicNumber().getLiteral();
		String literalName;
		
		if (classLiterals.containsKey(literal)) {
			literalName = classLiterals.get(literal);
		} else {
			magicNumberNr++;
			literalName = String.format("MAGIC_NUMBER_%d", magicNumberNr);
			
			file.getRewriter().insertAfter(classBodyContext.start, 
					String.format("\n\tprivate static final %s %s = %s;",
							getType(literal), literalName, literal));
			
			classLiterals.put(literal, literalName);
		}
		
		for (int i = issue.getMagicNumber().size() - 1; i >= 0; i--) {
			LiteralContext ctx = issue.getMagicNumber().getContext(i);
			
			file.getRewriter().replace(ctx.start, literalName);
		}
	}
	
	private String getType(String value) {
		if (value.contains("\"")) {
			return "String";
		} else if (value.contains(".")) {
			return "double";
		} else {
			return "int";
		}
	}
}
