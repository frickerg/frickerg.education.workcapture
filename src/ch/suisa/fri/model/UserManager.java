/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.model;

import java.util.ArrayList;
import java.util.List;

import ch.suisa.fri.entities.User;

/**
 * The UserManager Class manages the Objects of the User Class
 * All Objects from the User Class can be registered into the Array List
 * User Objects aren't written into the Array List automatically, to avoid errors
 */
public class UserManager {
    private ArrayList<User> userList = new ArrayList<User>();
    
    /**
     * Adds an User to the Array List with its Id
     */
    public void addUser(User user) {
    	userList.add(user);
    }

    /**
     * Removes an User from the Array List with its Id
     */
    public void removeUser(User user) {
    	userList.remove(user);
    }
    
    /**
     * Checks if there is already an User with the same UserId (Important if there are errors in the User File)
     */
    public boolean isExistingUser(User user) {
        if(getUserById(user.getId()) != null) {
        	return true;
        } 
        
        return false; 
    }
    
    /**
     * Returns an User by its Id
     * The returned User can be used in other Methods
     */
    public User getUserById(String id) {
        for(User u : userList) {
        	if(u.getId().equalsIgnoreCase(id)) {
        		return u; // Exists
        	}
        }
        
        return null; // Doesn't exist
    }

    /**
     * Can generate an Id for every User
     * It takes the first letters of the User and counts the amount of Users with the same initials
     * If the Id is already taken, an additional Loop looks for the next avalaible number 
     */
    public String generateId(String firstName, String lastName) {
    	int counter = 1;
    	String initials = firstName.substring(0, 1) + lastName.substring(0, 1);
    	
    	String id = initials + counter;
    	
    	while(getUserById(id) != null) {
    		counter++;
    		id = initials + counter;
    	}
    	
    	return id;
    }
    
    /**
     * returns the whole ArrayList with all Users
     */
	public List<User> getAllUsers() {
		return userList;
	}
}
