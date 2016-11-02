package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Kayne
 * Abstract class used to define method names for opening/ closing different file types 
 * and retrieving list of users from files.
 * 
 * Defines own method to sort list of users by ID
 */

public abstract class FileParser{
	
	private List<User> users=new ArrayList<User>();
	
	/**
	 * Opens file type
	 * @param String fileName: name of file to open
	 * @return boolean: returns true or false if file successfully opened
	 */
	public abstract boolean openFile(String fileName);
	
	/**
	 * Retrieves list of different User objects from file type
	 * @return List<User>: returns list of User objects
	 */
	public abstract List<User> getListOfUsers();
	
	/**
	 * Writes output file types
	 * @param List<User> users: list of User objects to output
	 * @param String outputFileName: Name of output file to write
	 */
	public abstract void writeFile(List<User> users,String outputFileName);
	
	/**
	 * @param List<User> users: List of User
	 * @return List<User> : sorted list of User 
	 */
	public List<User> sortUsersByID(List<User> users){
		this.users=users;
		
		//Use collections class to sort and create comparator to sort by userID element
		Collections.sort(users, new Comparator<User>() {		
			/**
			 * @param User user1:First User to compare
			 * @param User user2:Second User to compare
			 * @return int: int for comparison for sorting
			 */
			   public int compare(User user1, User user2) {
				   return user1.userID.compareTo(user2.userID);
			   }
			});
		return users;
	}
}
