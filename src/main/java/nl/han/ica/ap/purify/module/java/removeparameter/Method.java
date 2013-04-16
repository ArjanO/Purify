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
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Store method data.
 * 
 * @author Arjan
 */
public class Method {
	private String name;
	private HashMap<String, Boolean> parameters;
	
	/**
	 * Store method data.
	 * 
	 * @param name Name of the method.
	 */
	public Method(String name) {
		this.name = name;
		this.parameters = new HashMap<String, Boolean>();
	}
	
	/**
	 * Get the method name.
	 * 
	 * @return Name of the method.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Add a parameter.
	 * 
	 * @param name Name of the parameter.
	 */
	public void addParameter(String name) {
		if (!parameters.containsKey(name)) {
			parameters.put(name, false);
		}
	}
	
	/**
	 * Check if the candidate variable is used. 
	 * 
	 * @param name Name of the variable.
	 */
	public void usedVariable(String name) {
		if (parameters.containsKey(name)) {
			parameters.put(name, true);
		}
	}
	
	/**
	 * Get the parameters that are not used. This means that the variable
	 * is not seen by {@link #usedVariable(String)}.
	 * 
	 * @return List of parameters that ar'nt used.
	 */
	public List<String> getUnusedParameters() {
		List<String> result = new ArrayList<String>();
		
		for (Entry<String, Boolean> item : parameters.entrySet()) { 
			if (item.getValue() == false) {
				result.add(item.getKey());
			}
		}
		
		return result;
	}
	
	/**
	 * Get the number of parameters that aren't used. 
	 * 
	 * @return Number of unused parameters.
	 */
	public int getUnusedPrametersSize() {
		int unused = 0;
		
		for(Boolean value : parameters.values()) {
			if (!value) {
				unused++;
			}
		}
		
		return unused;
	}
}
