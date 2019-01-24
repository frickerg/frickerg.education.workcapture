/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.reporting;

import java.util.ArrayList;
import java.util.List;

import ch.suisa.fri.entities.User;
import ch.suisa.fri.entities.WorkCapture;
import ch.suisa.fri.model.UserManager;
import ch.suisa.fri.model.WorkCaptureManager;
import ch.suisa.fri.utils.TimeFormatter;
import ch.suisa.fri.view.Console;

/**
 * This Class prints different kinds of Reportings
 * There are two types of Reportings:
 * Reporting by User: Counts how much time an User spends on one Activity
 * Reporting by Activity: Shows the time spent on one Activity by all Users 
 */
public class Reporter {
	private UserManager userManager;
	private WorkCaptureManager workCaptureManager;
	private Console console;
	
	/**
	 * Requires some of the standard Objects defined in the Controller Class
	 * These are used in the whole class and are never changed
	 */
	public Reporter(UserManager userManager, WorkCaptureManager workCaptureManager, Console console) {
		this.userManager = userManager;
		this.workCaptureManager = workCaptureManager;
		this.console = console;
	}

	/**
	 * Prints a simple Reporting of all registered Users
	 * It shows which User has spent how much time on one Project
	 * Users without Work Captures or unfinished Work Captures are not shown
	 */
	public void reportingByUser() {
		console.print("Reporting By User:");
		
        for(User u : userManager.getAllUsers()) {
        	List<WorkCapture> userWorkCaptures = workCaptureManager.getWorkCapturesByUser(u);
        	
        	if(userWorkCaptures.size() == 0 || (userWorkCaptures.size() == 1  && userWorkCaptures.get(0).getEndTime() == null)) {
        		continue;
        	}
        	
			console.printLine();
			console.print("User: " + u);

			printActivities(userWorkCaptures);
        }
        console.printLine();
		console.pause();
	}
	

	/**
	 * Prints a simple Reporting of all Activities
	 * It shows how much time has been spent on one Project in total
	 * Work Captures without End Time don't count to the reporting
	 */
	public void reportingByActivity() {
		console.print("Reporting By Activity:");
		console.printLine();
				
		printActivities(workCaptureManager.getAllWorkCaptures());

		console.printLine();
		console.pause();
	}
	
	/**
	 * This method avoids code redundancy for the methods reportingByActivity and reportingByUser
	 * The method requires a List as a parameter.
	 * 
	 * There are two possibilities with the parameter:
	 * userWorkCaptures -> (Loops the Work Captures of one User)
	 * workCaptureManager.getAllWorkCaptures() -> (Loops all Work Captures)
	 */
	private void printActivities(List<WorkCapture> workCaptures) {
		
		ArrayList<String> alreadyPrintedActivities = new ArrayList<String>();
		long totalTime = 0;
		
		for(WorkCapture workCapture : workCaptures) {
			if(!alreadyPrintedActivities.contains(workCapture.getActivity())) {
				long totalActivityTime = 0;
				
				for(WorkCapture compareWorkCapture: workCaptures) {
			        if(compareWorkCapture.getActivity().equalsIgnoreCase(workCapture.getActivity())) {
			        	if(compareWorkCapture.getEndTime() != null) {
			        		totalActivityTime = totalActivityTime + (compareWorkCapture.getEndTime().getTime() - compareWorkCapture.getStartTime().getTime());
			        	}
			        }
				}
				
				if(totalActivityTime > 0) {
					console.print(workCapture.getActivity() + ": " + TimeFormatter.formatTime(totalActivityTime));
				}
				
				totalTime = totalTime + totalActivityTime;
				alreadyPrintedActivities.add(workCapture.getActivity());
			}
		}
		
		console.print("\nTotal Time: " + TimeFormatter.formatTime(totalTime));
	}
}