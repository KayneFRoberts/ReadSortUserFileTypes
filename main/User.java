package main;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Kayne
 * Class to hold user attributes for each user encountered in input files
 */
public class User {

	public Integer userID;
	public String firstName;
	public String secondName;
	public String userName;
	public String userType; 
	public String lastLoginTime;
	private String oldFormat = "dd-MM-yyyy HH:mm";
	private String newFormat = "yyyy-MM-dd";
	private String convertedDate=null;
	
	/**
	 * @param int userID 
	 * @param String firstName
	 * @param String secondName 
	 * @param String userName 
	 * @param String userType 
	 * @param String lastLoginTime
	 * Instantiate new user with specified attributes
	 **/
	public User(int userID,String firstName,String secondName,String userName, String userType, String lastLoginTime){
		this.userID=userID;
		this.firstName=firstName;
		this.secondName=secondName;
		this.userName=userName;
		this.userType=userType;
		this.lastLoginTime=convertDate(lastLoginTime.replace("/", "-"));
	}
	
	/**
	 * @param String date: date in String format to convert
	 * @return String: returns converted date in String format
	 **/
	private String convertDate(String date){
	    SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
	    SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);

	    try {
	    	convertedDate=sdf2.format(sdf1.parse(date));

	    } catch (ParseException e) {
	        e.printStackTrace();
	    }	    
	    return convertedDate;
	}
}
