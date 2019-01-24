/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.control;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.suisa.fri.entities.User;
import ch.suisa.fri.entities.WorkCapture;
import ch.suisa.fri.model.DataHandler;
import ch.suisa.fri.model.SessionManager;
import ch.suisa.fri.model.UserManager;
import ch.suisa.fri.model.WorkCaptureManager;
import ch.suisa.fri.reporting.Reporter;
import ch.suisa.fri.utils.TimeFormatter;
import ch.suisa.fri.view.Console;

/**
 * The Controller Class generates the User Interface in the Console
 * There are two different types of User Consoles (Admin and Normal User)
 * Everything the Program User sees as a Program is in this Class
 */
public class Controller {

	private Console console;
	private UserManager userManager;
	private WorkCaptureManager workCaptureManager;
	private SessionManager sessionManager;
	private DataHandler dataHandler;
	private Reporter reporter;

	/**
	 * Constructor sets all standard Objects
	 * Those are used throughout the whole program, to avoid redundancy or errors
	 * They are NEVER changed!
	 */
	public Controller() {
		console = new Console();
		userManager = new UserManager(); //Only one userManager should be used per program execution
		workCaptureManager = new WorkCaptureManager(); //Same as the userManager
		sessionManager = new SessionManager();
		dataHandler = new DataHandler(userManager, workCaptureManager);
		reporter = new Reporter(userManager, workCaptureManager, console);
	}

	/**
	 * Loads File into the Array Lists
	 * (Work Captures & Users)
	 */
	public void start() {
		dataHandler.loadData();
		
		control();
	}
	
	/**
	 * Entirely ends the Program
	 * Data gets saved into File
	 * Can only be run by an Admin User 
	 */
	private void end() {
		dataHandler.saveData();
		
		console.print("Shutdown successful!");
		System.exit(0);
	}
	
	/**
	 * Controls the loggedinUser Variable
	 * If the Variable is null, the login Method is called
	 * The Method also checks, if the loggedinUser has Admin rights
	 */
	private void control() {
		if(sessionManager.getCurrentUser() == null) {
			login();
		} else {
			if(sessionManager.getCurrentUser().isAdmin()) {
				showAdminMenu();
			} else {
				showUserMenu();
			}
		}
	}
	
	/**
	 * This Method compares the User Input with the User Id's in the Array List
	 * The loggedinUser Variable gets the User as Value
	 */
	private void login() {

		console.clear();
		console.print("Booking System");
		console.printLine();
		console.print("2013, By Guillaume Fricker"); // Yeah, thats me!
		console.printLine();
		
		
		String userId = console.promt("Please enter your ID:");
		
		//Checks for an existing User with the typed String
		User u = userManager.getUserById(userId);
		if(u == null) {	
			console.print(	"This ID is not in our system.\n" +
							"Please check your spelling or login as Admin to add a new user.");
			console.pause();
			login(); //UI starts over again
		} else {
			console.print("Hello " + u.getFirstName() + " " + u.getLastName() +"!");
			console.pause();
		}
		
		sessionManager.setCurrentUser(u);
		control();
	}
	
	/**
	 * Logout Method
	 * Sets the loggedinUser Variable back to null
	 */
	private void logout() {
		sessionManager.setCurrentUser(null);
		control();
	}

	/**
	 * This is an Advanced UI for a normal User
	 * The User can choose between multiple Options
	 * At the end of the Method, the User always gets thrown back to the control Method
	 */
	private void showUserMenu() {
		
		console.clear();
		String input = console.promt("Choose an option:\n" +
				"\t1 - Make a Stamp\n" +
				"\t2 - View your Stamps\n" +
				"\t3 - Logout");
		console.clear();
		
		switch(input)
		{
			case "1": //Make a stamp
				stamp();
				break;
				
			case "2": //View your stamps
				printUserWorkCapture(sessionManager.getCurrentUser());
				break;
							
			case "3": //Logout
				logout();
				break;
				
            default:
            	control();
        }
		
		control();
	}
	
	/**
	 * Same Advanced UI for an Administrator
	 * The functionality is the same as the User UI (Except for the Admin Functions)
	 */
	private void showAdminMenu() {
		console.clear();
		String input = console.promt("Choose an option:\n" +
				"\t1 - Make a Stamp\n" +
				"\t2 - View your Stamps\n" +
				"\t3 - Admin Functions\n" +
				"\t4 - Logout");
		console.clear();
		
		switch(input)
        {
			case "1": //Make a stamp
				stamp();
				break;
				
			case "2": //View your stamps
				printUserWorkCapture(sessionManager.getCurrentUser());
				break;
				
			case "3": //Admin Functions
				adminBox();
				break;
				
			case "4": //Logout
				logout();
				break;
			
            default:
            	control();
        }
		control();
	}
	
	/**
	 * The Admin Box contains all the features which are intended for the Admin
	 */
	private void adminBox() {
		console.clear();
		String input = console.promt("Choose an option:\n" +
				"\t1 - See all Users\n" +
				"\t2 - Add an User\n" +
				"\t3 - Remove an User\n" +
				"\t4 - Change Admin rights of an User\n" +
				"\t5 - Reporting\n" +
				"\t6 - Go Back\n" +
				"\t7 - Shutdown");
		console.clear();
		
		switch(input)
        {
			
        	case "1": //See all users
        		showUsers();
        		break;
			
			case "2": //Add an User
				adminAddUser();
				break;
			
			case "3": //Remove an User
				adminRemoveUser();
				break;
				
			case "4": //Give Admin Rights
				adminGiveRights();
				break;
				
			case "5": //Reporting
				reportingBox();
				break;
				
			case "6": //Back to Menu
				control();
				break;
				
			case "7": //Shutdown
				end();
				break;

			default:
            	adminBox();
        }
		adminBox();
		
	}
	
	/**
	 * The Reporting Box gives reported Stamps to the Admin User
	 * You can either call a Reporting by User (How much which User has worked on which Project)
	 * Or you can call a Reporting by Activity (How much time has been spent on total on a project by all Users)
	 */
	private void reportingBox() {
		console.clear();
		String input = console.promt("Choose an option:\n" +
				"\t1 - Reporting By User\n" +
				"\t2 - Reporting By Activity\n" +
				"\t3 - Back");
		console.clear();
		
		switch(input)
        {
        	case "1": //Reporting By User
        		reporter.reportingByUser();
        		break;
        	case "2": //Reporting By Activity
        		reporter.reportingByActivity();
        		break;
        	case "3":
        		adminBox();
        		break;
        		
        	default:
        		reportingBox();
        }
		reportingBox();
	}
	
	/**
	 * An Admin can change the Admin rights of other Users, including Admins, but he can't change his own rights
	 */
	private void adminGiveRights() {
		String input = console.promt("Enter the ID of the User you want to grant Admin access");
		
		User user = userManager.getUserById(input);
		if(user == null) {
			console.print("This Name is not in our system");
		} else {
			if(sessionManager.getCurrentUser().equals(user)) {
				console.print("You can't change your own Admin rights!");
			} else {
				if(user.setAdmin() == true) { //setAdmin() returns a boolean
					console.print("The User " + input + " has now Admin rights");
				} else {
					console.print("The Admin rights of the User " + input + " were taken");
				}
			}
		}
		console.pause();
	}
	
	/**
	 * An Admin can add another User
	 * He must enter a First Name and a Last Name
	 * The Id is unique and is generated automatically in another Method
	 */
	private void adminAddUser() {
		console.print("Add an User");
		String firstName = console.promt("Please enter a First Name:");
		String lastName = console.promt("Please enter a last Name:");
		String id = userManager.generateId(firstName, lastName); // Awesome stuff, though
		
		User createdUser = new User(id, firstName, lastName, false); //The Users has no Admin rights by default

		if(!userManager.isExistingUser(createdUser)){
			userManager.addUser(createdUser);
            console.print("The User " + createdUser.getFirstName() + " " + createdUser.getLastName() + " has been added with the ID \"" + createdUser.getId() + "\"");
        } else {
        	console.print("The User " + createdUser.getId() + " exists already");
        }
		
		console.pause();
	}
	
	/**
	 * An Admin can delete other Users, including Admins, but he can't remove himself
	 * The Method checks the entered ID
	 */
	private void adminRemoveUser() {
		console.print("Remove an User");
		String input = console.promt("Please enter the User ID:");
		
		//Checks if the User exists
		User u = userManager.getUserById(input);
		if(u == null) {
			console.print("This Name is not in our system");
		} else {
			if(u.equals(sessionManager.getCurrentUser())) {
				console.print("You can't remove yourself!");
			} else {
				if(u != null){
		            userManager.removeUser(u);
		            console.print("The User " + u.getFirstName() + " " + u.getLastName() + " was removed");
		        }
			}
		}
		console.pause();
	}
	
	/**
     * Shows all Users registered in the Array List
     * If there are no Users registered, the method will only show a little message
     */
    private void showUsers() {
        console.printLine();
        console.print("Registered Users:\n");
        if(userManager.getAllUsers().size() > 0) {	
        	for(User u : userManager.getAllUsers()){
        		console.print(u.toString()); //Override Method
        	}
        } else {
        	console.print("No Users added yet.");
        }
        console.printLine();
        console.pause();
    }
    
	/**
	 * Prints all Work Captures of one specific User with the getFullWorkCapture Method
	 */
	public void printUserWorkCapture(User user) {
		boolean found = false;
		for(WorkCapture workCapture : workCaptureManager.getAllWorkCaptures()) {
			if(workCapture.getUser().equals(user)) {
				getFullWorkCapture(workCapture);
				found = true;
			}
        }
		if(found == false) {
			console.print("No Work Captures yet");
		}
		console.printLine();
		console.pause();
	}
	

	/**
	 * prints one full Work Capture of one User
	 */
	public void getFullWorkCapture(WorkCapture wc) {
		console.printLine();
		
		String endTime;
		if(wc.getEndTime() == null) {
			endTime = "Incomplete"; 
		} else {
			endTime = TimeFormatter.dateToString(wc.getEndTime()); 
		}
		
		console.print("User:\t\t" + wc.getUser().getFirstName() + " " + wc.getUser().getLastName() + "\n" +
				"Start:\t\t" + TimeFormatter.dateToString(wc.getStartTime()) + "\n" +
				"End:\t\t" + endTime + "\n" +
				"Activity:\t" + wc.getActivity());
		
		if(wc.getEndTime() != null && wc.getStartTime().compareTo(wc.getEndTime()) > 0){
			console.print("\nWARNING: Start time is greater than end time!");
		}
	}
	
	/**
	 * Checks if there is an old WorkCapture which has not been finished yet
	 * If there is no Unfinished Work Capture, a new Work Capture gets created
	 */
	public void stamp() {
		WorkCapture workCapture = workCaptureManager.getUnfinishedWorkCapture(sessionManager.getCurrentUser()); //Checks the last Work Capture
		
		if(workCapture == null) {
			//Create new Work Capture
			String input = console.promt("Please enter a new activity:");
			
			workCapture = new WorkCapture(sessionManager.getCurrentUser(), input); //New Stamp with current User and Activity
			workCaptureManager.addWorkCapture(workCapture);
		} else if(workCaptureManager.isStartDateToday(workCapture)) {
				//End Time
				workCapture.setEndTime(new Date());
				
		} else {
			setManualTime(workCapture);
		}
	}

	/**
	 * Allows the user to set a manual End Time if he forgot to stamp the day before
	 * The User has to enter a time. The yyy-MM-dd is filled automatically, compared to the Start Time
	 */
	private void setManualTime(WorkCapture workCapture) {
		String inputPrefix = new SimpleDateFormat("yyyy-MM-dd ").format(workCapture.getStartTime());
		
		String input = console.promt("Please enter an Ending Time for this activity:\n" +
				"\"" + workCapture.getActivity() + "\"\n" +
				"Last Stamp was at: " + TimeFormatter.dateToString(workCapture.getStartTime()));
		try {
			Date endDate = TimeFormatter.stringToDate(inputPrefix + input); //End Date
			
			if(workCapture.getStartTime().before(endDate)){
				workCapture.setEndTime(endDate);
			} else {
				console.clear();
				console.print("\nStartDate after EndDate\n");
				stamp();
			}
		} catch(Exception e) {
			console.clear();
			console.print("\nPlease enter the time in this format:\nHH:mm\n");
			stamp();
		}
		
	}
}
