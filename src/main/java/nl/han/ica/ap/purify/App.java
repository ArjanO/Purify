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
package nl.han.ica.ap.purify;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import nl.han.ica.ap.purify.language.java.JavaLexer;
import nl.han.ica.ap.purify.language.java.JavaParser;
import nl.han.ica.ap.purify.module.java.duplicatecode.Clones;
import nl.han.ica.ap.purify.module.java.duplicatecode.DuplicatedCodeDetector;
import nl.han.ica.ap.purify.module.java.magicnumber.MagicNumber;
import nl.han.ica.ap.purify.module.java.magicnumber.MagicNumberDetector;

/**
 * Example magic numbers runner. 
 */
public class App {
	private static final String COMMAND_LINE_PARAM_MISSING =
			"Add at least one file as command line parameter.";
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println(COMMAND_LINE_PARAM_MISSING);
			return;
		}
		
		DuplicatedCodeDetector duplicatedCode = new DuplicatedCodeDetector();
		
		for (int i = 0; i < args.length; i++) {
			ANTLRInputStream input = null;
			InputStream is = null;
			
			if (args[i] != null) {
				try {
					is = new FileInputStream(args[i]);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					continue;
				}
			}
			
			try {
				input = new ANTLRInputStream(is);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			JavaLexer lexer = new JavaLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			JavaParser parser = new JavaParser(tokens);
			ParseTree tree = parser.compilationUnit();
			
			ParseTreeWalker waker = new ParseTreeWalker();
			MagicNumberDetector magicNumberDetector = new MagicNumberDetector();
			waker.walk(magicNumberDetector, tree);
			
			duplicatedCode.visit(tree);
			
			List<MagicNumber> magicNumbers = magicNumberDetector
					.getMagicNumbers();
			
			for (MagicNumber magicNumber : magicNumbers) {
				System.out.println(String.format(
						"Found '%s' %d times in file '%s'", 
						magicNumber.getLiteral(), magicNumber.size(),
						args[i]));
			}
		}
		
		Clones clones = duplicatedCode.getClones();
		
		System.out.println(String.format(
				"Detected %d code clones", clones.size()));
		
		for (int i = clones.size() - 1; i >= 0; i--) {
			System.out.println(clones.getItem(i).get(0).getText());
		}
	}
}
