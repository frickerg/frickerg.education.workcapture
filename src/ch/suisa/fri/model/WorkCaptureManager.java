/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.suisa.fri.entities.User;
import ch.suisa.fri.entities.WorkCapture;

/**
 * The WorkCaptureManager Class manages the Objects of the WorkCapture Class
 * It has the possibilities to make a Stamp and to check what kind of Stamps have been done before
 * In the end, all Stamps can be displayed in the Console
 */
public class WorkCaptureManager {		
		
	private ArrayList<WorkCapture> workCaptureList = new ArrayList<WorkCapture>();
	
	/**
	 * When the last Stamp has no End Time, the Method checks if the Start Time is actually the same day as it is Today
	 * If this is not true, the User must enter an own End Time and the Stamp can finally be closed
	 */
	public boolean isStartDateToday(WorkCapture workCapture) {
		Calendar todayCal = Calendar.getInstance();
		Calendar workCaptureStartDayCal = Calendar.getInstance();
		workCaptureStartDayCal.setTime(workCapture.getStartTime());
		/*String fullDate = calendar.getTime().toString();
		System.out.println(fullDate);*/
		
		int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
		int workCaptureStartDateDay = workCaptureStartDayCal.get(Calendar.DAY_OF_MONTH);
		int todayMonth = todayCal.get(Calendar.MONTH);
		int workCaptureStartDateMonth = workCaptureStartDayCal.get(Calendar.MONTH);
		int todayYear = todayCal.get(Calendar.YEAR);
		int workCaptureStartDateYear = workCaptureStartDayCal.get(Calendar.YEAR); //Even if you don't Stamp for one year you can't trick the program!
				
		if(todayDay != workCaptureStartDateDay || todayMonth != workCaptureStartDateMonth || todayYear != workCaptureStartDateYear) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Writes a Work Capture into the Array List
	 */
	public void addWorkCapture(WorkCapture workCapture) {
		workCaptureList.add(workCapture);
	}
	
	/**
	 * Checks if there is an Unfinished Work Capture
	 * If the Work Capture has no End Time the Work Capture gets closed in another Method
	 */
	public WorkCapture getUnfinishedWorkCapture(User user) {
		for(WorkCapture workCapture : workCaptureList) {
        	if(workCapture.getUser().getId() == user.getId()) {
        		if(workCapture.getEndTime() == null) {
        			return workCapture; //Unfinished Work Capture
        		}
        	}
        }
        return null; //Work Capture finished
	}

	/**
	 * Returns a List with all the Work Captures of one specific User
	 */
	public List<WorkCapture> getWorkCapturesByUser(User user) {
		List<WorkCapture> userWorkCaptures = new ArrayList<WorkCapture>();
		
		for(WorkCapture workCapture : workCaptureList) {
			if(workCapture.getUser().equals(user)) {
				userWorkCaptures.add(workCapture);
			}
		}

		return userWorkCaptures;
	}
	
	/**
	 * Returns a List with all the Work Captures of every User
	 */
	public List<WorkCapture> getAllWorkCaptures() {
		return workCaptureList;
	}
}