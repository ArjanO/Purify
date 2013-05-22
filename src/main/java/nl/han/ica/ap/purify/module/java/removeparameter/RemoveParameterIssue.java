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

import java.util.List;

import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;
import nl.han.ica.ap.purify.language.java.JavaParser.MemberDeclContext;
import nl.han.ica.ap.purify.language.java.util.MethodUtil;
import nl.han.ica.ap.purify.language.java.util.ParameterUtil;
import nl.han.ica.ap.purify.modles.IIssue;

/**
 * Detected unused parameter.
 * 
 * @author Arjan
 */
public class RemoveParameterIssue implements IIssue {
	private MemberDeclContext method;
	private List<FormalParameterDeclsRestContext> parameters;
	
	/**
	 * The parameter in method is not used.
	 * 
	 * @param method Method
	 * @param parameters List with unused parameters.
	 * @throws IllegalArgumentException If method or parameter is null.
	 */
	public RemoveParameterIssue(MemberDeclContext method, 
			List<FormalParameterDeclsRestContext> parameters) {
		if (method == null || parameters == null) {
			throw new IllegalArgumentException();
		}
		
		this.method = method;
		this.parameters = parameters;
	}
	
	/**
	 * Get the method with the unused parameter.
	 * 
	 * @return Method.
	 */
	public MemberDeclContext getMethod() {
		return method;
	}
	
	/**
	 * Get the unused parameters.
	 * 
	 * @return Unused parameters.
	 */
	public List<FormalParameterDeclsRestContext> getParameters() {
		return parameters;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Method: " + MethodUtil.getMethodName(method) + "\n");
		
		for (FormalParameterDeclsRestContext p : parameters) {
			sb.append("\t" + ParameterUtil.getParameterName(p) + "\n");
		}
		
		return sb.toString();
	}
}