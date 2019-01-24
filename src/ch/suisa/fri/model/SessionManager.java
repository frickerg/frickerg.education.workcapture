/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.model;

import ch.suisa.fri.entities.User;

/**
 * The Session Manager only contains a getter and a setter Method for the Current User
 * This is important for the control() Method in the Controller Class
 */
public class SessionManager {

	private User currentUser;
	
	/**
	 * Returns the current User
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Changes the current User to another User
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
