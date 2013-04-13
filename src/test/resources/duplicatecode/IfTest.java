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
public class IfTest {
	/**
	 * Expected: duplicated code (method 2).
	 */
	public void method1(int nr) {
		if (nr == 2) {
			System.out.println("NR = 2");
		}
	}
	
	/**
	 * Expected: duplicated code (method 1).
	 */
	public void method2(int number) {
		if (number == 2) {
			System.out.println("NR = 2");
		}
	}
	
	/**
	 * Expected: duplicated code (method 4).
	 */
	public void method3(int nr) {
		if (nr == 2) {
			System.out.println("NR = 2");
		} else if (nr == 3) {
			System.out.println("NR = 3");
		}
	}
	
	/**
	 * Expected: duplicated code (method 3).
	 */
	public void method4(int number) {
		if (number == 2) {
			System.out.println("NR = 2");
		} else if (number == 3) {
			System.out.println("NR = 3");
		}
	}
	
	/**
	 * Expected: duplicated code (method 6).
	 */
	public void method5(int nr) {
		if (nr == 2) {
			System.out.println("NR = 2");
		} else {
			System.out.println(String.format("NR = %d", nr));
		}
	}
	
	/**
	 * Expected: duplicated code (method 5).
	 */
	public void method6(int number) {
		if (number == 2) {
			System.out.println("NR = 2");
		} else {
			System.out.println(String.format("NR = %d", number));
		}
	}
	
	/**
	 * Expected: duplicated code (method 8).
	 */
	public void method7(int nr) {
		if (nr == 2) {
			System.out.println("NR = 2");
		} else if (nr == 3) {
			System.out.println("NR = 3");
		} else {
			System.out.println(String.format("NR = %d", nr));
		}
	}
	
	/**
	 * Expected: duplicated code (method 7).
	 */
	public void method8(int number) {
		if (number == 2) {
			System.out.println("NR = 2");
		} else if (number == 3) {
			System.out.println("NR = 3");
		} else {
			System.out.println(String.format("NR = %d", number));
		}
	}
	
	/**
	 * Expected: duplicated code (method 10).
	 */
	public boolean method9(int i) {
		boolean retrn = false;
		
		if (i == 3)
			retrn = true;
		
		if (i != 3)
			retrn = true;
		
		return retrn;
	}
	
	/**
	 * Expected: duplicated code (method 9).
	 */
	public boolean method10(int d) {
		boolean retrn = false;
		
		if (d == 3)
			retrn = true;
		
		if (d != 3)
			retrn = true;
		
		return retrn;
	}
	
	/**
	 * Expected: duplicated code (method 12).
	 */
	public boolean method11(int d) {
		boolean retrn = false;
		
		if (d == 3)
			retrn = true;
		
		if (d != 3)
			retrn = false;
		
		return retrn;
	}
	
	/**
	 * Expected: duplicated code (method 11).
	 */
	public boolean method12(int i) {
		boolean retrn = false;
		
		if (i == 3)
			retrn = true;
		
		if (i != 3)
			retrn = false;
		
		return retrn;
	}
}
