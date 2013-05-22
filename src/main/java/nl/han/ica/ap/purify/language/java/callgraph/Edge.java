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
package nl.han.ica.ap.purify.language.java.callgraph;

import nl.han.ica.ap.purify.language.java.JavaParser.ExpressionMethodExpressionListContext;
import nl.han.ica.ap.purify.modles.SourceFile;

/**
 * Links one MethodNode to another in the CallGraph.
 * 
 * @author Tim
 */
public class Edge {
	private SourceFile file;
	private ExpressionMethodExpressionListContext statement;
	
	public MethodNode src;
	public MethodNode target;
	
	/**
	 * Creates a new Edge.
	 * 
	 * @param src The method that performs the call.
	 * @param target The method that is called.
	 * @param file Source file that contains the calling method.
	 * @param statement Statement that calls the method.
	 */
	public Edge(MethodNode src, MethodNode target, 
			SourceFile file, ExpressionMethodExpressionListContext statement) {
		this.src = src;
		this.target = target;
		this.target.called = true;
		
		this.file = file;
		this.statement = statement;
	}
	
	/**
	 * Get the source file of the statement.
	 * 
	 * @return Source file.
	 */
	public SourceFile getSourceFile() {
		return file;
	}
	
	/**
	 * Get the statement that calls the method.
	 * 
	 * @return Statement that calls the method.
	 */
	public ExpressionMethodExpressionListContext getStatement() {
		return statement;
	}
}
