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

import nl.han.ica.ap.purify.language.java.JavaParser.FormalParameterDeclsRestContext;

/**
 * Stores information about a parameter.
 * 
 * @author Arjan
 */
class Parameter {
	private String name;
	private FormalParameterDeclsRestContext parameter;

	/**
	 * Create a new parameter store.
	 * 
	 * @param parameter Parameter to store.
	 * @throws IllegalArgumentException If parameter is null.
	 */
	public Parameter(FormalParameterDeclsRestContext parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException();
		}
		
		this.parameter = parameter;
		
		if (parameter.variableDeclaratorId() != null 
				&& parameter.variableDeclaratorId().Identifier() != null) {
			this.name = parameter.variableDeclaratorId().Identifier().getText();
		}
	}
	
	/**
	 * Get the name of the parameter.
	 * 
	 * @return Name of the parameter.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the parameter context.
	 * 
	 * @return Parameter context.
	 */
	public FormalParameterDeclsRestContext getpPrameter() {
		return parameter;
	}
	
	/**
	 * Compare name of parameters.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Parameter)) {
			return super.equals(obj);
		}
		
		return name.equals(((Parameter)obj).getName());
	}
	
	/**
	 * Get the hash code of the name of the parameter.
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
