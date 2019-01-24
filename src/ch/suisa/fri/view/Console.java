/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.view;

import java.util.Scanner;

/**
 * This Class implements Methods for the User Interface which are used very often
 * The code gets smaller and clearer
 */
public class Console {
	Scanner sc = new Scanner(System.in); // Must only be opened one time
	
	/**
	 * Prints a normal String
	 * Instead of "System.out.println(string) the code only contains "console.print(string)"
	 * The code gets smaller
	 */
	public void print(String s) {
		System.out.println(s);
	}
	
	/**
	 * This method makes the code a lot lighter
	 * Instead of asking for an Input every time, the code gets directly the input back
	 */
	public String promt(String s) {

		System.out.println(s);
		String input = sc.nextLine();	
		
		return input;
	}
	
	/**
	 * This Method "clears" the console
	 * It just adds a few empty lines, so the text doesn't get visible (but it's still there...)
	 */
	public void clear() {
		for (int i = 0; i < 50; ++i) {
			System.out.println();
		}
		//XXX System("cls"); Alternative?
	}
	
	/**
	 * Just prints a line, so the lines in the console are always the same width
	 */
	public void printLine() {
		System.out.println("---------------------------");
	}
	
	/**
	 * Waits for User input
	 * "pauses" the program
	 */
	public void pause() {
		sc.nextLine();	
	}

}
