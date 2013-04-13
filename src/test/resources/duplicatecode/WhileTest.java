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
 * This code file is used by HashVisitorWhileTest.
 * 
 * @author Arjan
 */
public class WhileTest {
	/**
	 * Expected: duplicated code (method 2, 3 and 4).
	 */
	public void method1(int nr) {
		int i = 0;
		while(i < nr) {
			System.out.println("I = " + i);
			i++;
		}
	}
	
	/**
	 * Expected: duplicated code (method 1, 3 and 4).
	 */
	public void method2(int nr) {
		int j = 0;
		while(j < nr) {
			System.out.println("I = " + j);
			j++;
		}
	}
	
	/**
	 * Expected: duplicated code (method 1, 2 and 4).
	 */
	public void method3(int number) {
		int j = 0;
		while(j < number) {
			System.out.println("I = " + j);
			j++;
		}
	}
	
	/**
	 * Expected: duplicated code (method 1, 2 and 3).
	 */
	public void method4(int number) {
		int j = 0;
		while(j < number) {
			System.out.println("I = " + j);
			
			// Added some spaces and this comment.
			
			j++;
		}
	}
	
	/**
	 * Expected: NOT duplicated code.
	 */
	public void method5(int nr) {
		for (int i = 0; i < nr; i++) {
			System.out.println("I = " + i);
		}
	}
	
	/**
	 * Expected: NOT duplicated code.
	 */
	public void method6(int nr) {
		int j = nr;
		while(j > 0) {
			System.out.println("I = " + j);
			j--;
		}
	}
}
