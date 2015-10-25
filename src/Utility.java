import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author Mike Simister
 * This class provides utility methods for 
 * assignments 1 and 2 from CPSC441 F2015
 *
 */
public class Utility {

	public Utility() {
	}

	/**
	 * @param in
	 *            - A date in the form of a String
	 * @return Date value which has been formatted
	 */
	public static Date convertToDate(String in) {
		Date date = null;
		DateFormat d = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		d.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			date = d.parse(in);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param lastModDate
	 *            A Time to be formatted
	 * @return Date value which has been formatted
	 */
	public static Date convertToDate(long lastModDate) {
		Date date = null;
		DateFormat d = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		d.setTimeZone(TimeZone.getTimeZone("GMT"));

		try {
			date = d.parse(d.format(lastModDate));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param lastMod
	 *            A Time value to be formatted
	 * @return String which has been converted to a date and formatted.
	 */
	public static String convertDateToString(long lastMod) {

		DateFormat d = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		d.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = d.format(lastMod);

		return date;
	}

	/**
	 * @param url
	 * string which may or may not represent the path to a locally stored file
	 * @return
	 * 		boolean value, true if the file exists, otherwise false
	 */
	public static boolean checkPath(String url) {

		boolean fileExists = false;
		return fileExists;
	}

	/**
	 * @param url
	 * 		String which is a header request	 * 		
	 * @return
	 *   A String in the format of a proper HTTP protocol response
	 */
	public static String checkRequest(String url) {
		// Set possible responses
		String ok = "200 OK";
		String bad = "400 Bad Request";
		String notFound = "404 Not Found";
		if (url.equals("")) {
			return bad;
		}

		// Split the request
		String[] arr = url.split("\r\n");

		// Parse the first 4 chars of request
		String test = arr[0].substring(0, 4);

		// check to see that the request begins with "GET "
		if (!(test.equals("GET "))) {
			return bad;
		}

		// verify proper HTTP protocol
		if (!(arr[0].contains("HTTP/1.0"))) {
			if (!(arr[0].contains("HTTP/1.1")))
				return bad;
		}

		// get the path
		String[] arr1 = arr[0].split(" ");
		String path = arr1[1];
		path = path.substring(1);

		File f = new File(path);
		if (f.exists()) {
			return ok;
		}
		return notFound;
	}

	/**
	 *  @param url
	 *     String which is a header request and contains
	 *     a file path 
	 * @return 
	 * 		The file which was specified by the path.
	 */
	public static File getFile(String url) {

		// Split the request
		String[] arr = url.split("\r\n");

		// get the path
		String[] arr1 = arr[0].split(" ");
		String path = arr1[1].substring(1);

		File f = new File(path);

		return f;

	}

}
