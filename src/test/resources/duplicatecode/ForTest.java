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

/**
 * This code file is used by HashVisitorIfTest.
 * 
 * @author Arjan
 */
public class ForTest {
	/**
	 * Expected: duplicated code (method 2).
	 */
	public String method1(int nr) {
		String result = "";
		
		for (int i = 0; i < nr; i++) {
			result += i;
			
			if (nr == i) {
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Expected: duplicated code (method 1).
	 */
	public String method2(int number) {
		String result = "";
		
		for (int j = 0; j < number; j++) {
			result += j;
			
			if (number == j) {
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Expected: no duplicated code.
	 */
	public void method3(int number) {
		String result = "";
		
		for (int j = 0; j < number; j++) {
			result += j;
			
			if (number == j) {
				break;
			}
		}
		
		return;
	}
}
