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
package nl.han.ica.ap.purify.module.java.removeparameter;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.modles.IDetector;
import nl.han.ica.ap.purify.modles.IIssue;
import nl.han.ica.ap.purify.modles.SourceFile;

/**
 * Detects unused parameters. 
 * 
 * @author Arjan
 */
public class RemoveParameterDetector implements IDetector {
	@Override
	public void analyze(SourceFile file) {
		RemoveParameterDetectorListener listener = 
				new RemoveParameterDetectorListener();
		
		ParseTreeWalker waker = new ParseTreeWalker();
		waker.walk(listener, file.getParseTree());
		
		for (Method detected : listener.getDetected()) {
			if (detected.getUnusedPrametersSize() > 0) {
				file.addIssue(addIssue(detected));
			}
		}
	}

	@Override
	public void detect() {
	}
	
	private IIssue addIssue(Method method) {
		List<FormalParameterDeclsRestContext> params = 
				new ArrayList<FormalParameterDeclsRestContext>();
		
		for (Parameter p : method.getUnusedParameters()) {
			params.add(p.getpPrameter());
		}
		
		return new RemoveParameterIssue(method.getMethod(), params);
	}
}
