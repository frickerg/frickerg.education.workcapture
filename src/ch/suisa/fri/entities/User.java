/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.entities;

/**
 * The User Class has all necessary Methods to create an User
 * It also has the possibility to return Values for the UserManager Class
 */
public class User {
	
    private String id;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    
    /**
     * Constructor creates a new User Object with all required parameters
     * The parameters have no values by default, the parameters define the values!
     */
    public User(String id, String firstName, String lastName, boolean isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    /**
     * Returns an Id
     */
	public String getId() {
		return id;
	}
	
	/**
     * Returns a First Name
     */
	public String getFirstName() {
		return firstName;
	}

	/**
     * Returns a Last Name
     */
	public String getLastName() {
		return lastName;
	}
	
	/**
     * Returns if the User has Admin rights or not
     */
	public boolean isAdmin() {
		return isAdmin;
	}
	
	/**
     * Switches the Admin Rights for an User
     * ("Admin to User" and "User to Admin")
     */
	public boolean setAdmin() {
		if(isAdmin() == false) {
			this.isAdmin = true;
		} else {
			this.isAdmin = false;
		}
		return isAdmin;
	}

	/**
	 * This Override Method is pretty cool because you can directly print out the User Object
	 * Usually, a System.out.println(user) would show the address of the User Object
	 * In this case, a System.out.println(user) shows you more useful Informations
	 */
	@Override
	public String toString() {
		if(isAdmin == true) {
			return id + " " + firstName + " " + lastName + " (Admin)";
		}
		return id + " " + firstName + " " + lastName;
    }
}
