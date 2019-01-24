/**
 * COPYRIGHT 2013, Guillaume Fricker
 * This Code is Open-Source and can be viewed by everyone
 * Please respect the author and don't copy-paste without mentioning it in your codes!
 * Stay Tasty!
 */

package ch.suisa.fri.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class formats the Date to a String and a String to Date
 * It's important for accurate Time Stamps and a user-friendly output
 */
public class TimeFormatter {

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * Converts a String to a Date with the parsing Method
	 */
	public static Date stringToDate(String s) {
		if(s == null || s.trim().equals("")) {
			return null;
		} else {
			try {
				return simpleDateFormat.parse(s);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Converts a String to a SimpleDateFormat
	 */
	public static String dateToString(Date d) {
		if(d == null) {
			return "";
		} else {
			return simpleDateFormat.format(d);
		}
	
	}
	
	/**
	 * Formats a set of milliseconds to an usable Time Stamp (HH:mm)
	 */
	public static String formatTime(long milliseconds) {
		String formatedTotalTime = String.format("%1$02d:%2$02d", //%[argument_index$][flags][width]conversion
		        TimeUnit.MILLISECONDS.toHours(milliseconds),
		        TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)));
		return formatedTotalTime;
	}
}
