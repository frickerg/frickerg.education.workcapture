/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;

import ch.suisa.fri.entities.User;
import ch.suisa.fri.entities.WorkCapture;
import ch.suisa.fri.utils.TimeFormatter;

/**
 * The Data Handler Saves to a File and loads from a File
 * All Credits for this Class go to my instructor Kevin Codemaster
 */
public class DataHandler {
	private UserManager userManager;
	private WorkCaptureManager workCaptureManager;
	
	private Path usersFile = FileSystems.getDefault().getPath(".", "users.txt");
	private Path workCapturesFile = FileSystems.getDefault().getPath(".", "workCaptures.txt");
	
	/**
	 * Needs a UserManager and a WorkCaptureManager as parameters
	 * (Standard objects)
	 */
	public DataHandler(UserManager userManager, WorkCaptureManager workCaptureManager) {
		this.userManager = userManager;
		this.workCaptureManager = workCaptureManager;
	}

	/**
	 * Loads the Users and the Work Captures and writes them into the ArrayList
	 */
	public void loadData() {
		loadUserData();
		loadWorkCaptureData();
	}

	/**
	 * Saves Users and Work Captures into File
	 */
	public void saveData() {
		saveUserData();
		saveWorkCaptureData();
	}
	
	/**
	 * Reads the Lines in the User File and adds them to the Array List
	 */
	private void loadUserData() {
		try {
			List<String> lines = Files.readAllLines(usersFile, Charset.defaultCharset());
			
			for(String line : lines) {
				String[] userParams = line.split(";");
				
				String id = userParams[0];
				String firstName = userParams[1];
				String lastName = userParams[2];
				boolean isAdmin = Boolean.valueOf(userParams[3]);
				
				userManager.addUser(new User(id, firstName, lastName, isAdmin));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the Lines in the Work Capture File and adds them to the Array List
	 */
	private void loadWorkCaptureData() {
		try {
			List<String> lines = Files.readAllLines(workCapturesFile, Charset.defaultCharset());
			
			for(String line : lines) {
				String[] workCaptureParams = line.split(";");
				
				User user = userManager.getUserById(workCaptureParams[0]);
				Date startDate = TimeFormatter.stringToDate(workCaptureParams[1]);
				Date endDate = TimeFormatter.stringToDate(workCaptureParams[2]);
				String activity = workCaptureParams[3];
				
				workCaptureManager.addWorkCapture(new WorkCapture(user, startDate, endDate, activity));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves all Users contained in the Array List and writes the Values in the File
	 */
	private void saveUserData() {
		StringBuffer userContent = new StringBuffer();
		
		for(User user : userManager.getAllUsers()) {
			userContent.append(user.getId()+";"+user.getFirstName()+";"+user.getLastName()+";"+user.isAdmin()+"\n");
		}
		
		try {
			Files.write(usersFile, userContent.toString().getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves all Work Captures contained in the Array List and writes the Values in the File
	 */
	private void saveWorkCaptureData() {
		StringBuffer workCaptureContent = new StringBuffer();
		
		for(WorkCapture workCapture : workCaptureManager.getAllWorkCaptures()) {
			
			String userId = workCapture.getUser().getId();
			String startDate = TimeFormatter.dateToString(workCapture.getStartTime());
			String endDate = TimeFormatter.dateToString(workCapture.getEndTime());
			String activity = workCapture.getActivity();
			
			workCaptureContent.append(userId+";"+startDate+";"+endDate+";"+activity+"\n");
		}
		
		try {
			Files.write(workCapturesFile, workCaptureContent.toString().getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
