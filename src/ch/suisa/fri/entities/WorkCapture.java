/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.entities;

import java.util.Date;

/**
 * The Work Capture Class has all necessary Methods to create a Work Capture
 * It also has the possibility to return Values for the WorkCaptureManager Class
 */
public class WorkCapture {
	
	private User user;
	private Date startTime;
	private Date endTime;
	private String activity;
	
	
	/**
	 * Two Constructors can be used in this Class
	 * This Constructor is for automatic Stamps
	 * The Start Time is an automatic Time Stamp
	 */
	public WorkCapture(User user, String activity) {
		this.user = user;
		this.startTime = new Date();
		this.activity = activity;
	}
	
	/**
	 * Two Constructors can be used in this Class
	 * This Constructor is for manual Stamps in the WriteData Class
	 * He needs all parameters for the Work Capture Object
	 */
	public WorkCapture(User user, Date startTime, Date endTime, String activity) {
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activity = activity;
	}
	
	/**
	 * Returns an User
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Returns a Start Time
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Returns an End Time
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Sets an End Time in real time
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Returns an Activity
	 */
	public String getActivity() {
		return activity;
	}
	
}
