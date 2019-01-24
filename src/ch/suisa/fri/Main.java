/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri;

import ch.suisa.fri.control.Controller;

/**
 * The Main Class has the task to create an usable User Interface in a console
 * You should login with a First Name and a Last Name, or as Admin
 * Some Test Users are added first in the WriteData Class
 */
public class Main {
		
	/**
	 * The Main Method is not very useful in this code, because it's static
	 * The only task of the main Method is to call non-static Methods by creating an Object main of this Class
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.start();
	}
}
