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
 * This code file is used by HashVisitorSwitchTest.
 * 
 * @author Arjan
 */
public class SwitchTest {
	/**
	 * Expected: duplicated code (method 2).
	 */
	public String method1(int nr) {
		switch (nr) {
		case 0:
			return "Dirk";
		case 1:
			return "Jan";
		case 2:
			return "Piet";
		case 3:
			return "Klaas";
		case 4:
			return "Guus";
		default:
			return "";
		}
	}
	
	/**
	 * Expected: duplicated code (method 3).
	 */
	public String method2(int number) {
		switch (number) {
		case 0:
			return "Dirk";
		case 1:
			return "Jan";
		case 2:
			return "Piet";
		case 3:
			return "Klaas";
		case 4:
			return "Guus";
		default:
			return "";
		}
	}
	
	/**
	 * Expected: duplicated code (method 4).
	 */
	public String method3(int nr) {
		String result;
		
		switch (nr) {
		case 0:
			result = "Dirk";
			break;
		case 1:
			result = "Jan";
			break;
		case 2:
			result = "Piet";
			break;
		case 3:
			result = "Klaas";
			break;
		case 4:
			result = "Guus";
			break;
		default:
			result = "";
			break;
		}
		
		return result;
	}
	
	/**
	 * Expected: duplicated code (method 3).
	 */
	public String method4(int number) {
		String result;
		
		switch (number) {
		case 0:
			result = "Dirk";
			break;
		case 1:
			result = "Jan";
			break;
		case 2:
			result = "Piet";
			break;
		case 3:
			result = "Klaas";
			break;
		case 4:
			result = "Guus";
			break;
		default:
			result = "";
			break;
		}
		
		return result;
	}
}
