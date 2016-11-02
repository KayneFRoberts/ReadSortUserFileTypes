package tests;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import main.OpenJson;
import main.User;

/**
 * @author Kayne
 * Tests OpenCsv class
 */
public class OpenJsonTest {

	private OpenJson json= new OpenJson();
	private String inputFileName=null;
	private String outputFileName=null;
	private List<User> users=null;
	private Iterator<User> itr=null;
	private File outputFile=null;
	
	//Dummy data from first entry in json intput file
	private Integer id=4;
	private String firstName="Joe";
	private String secondName="Public";
	private String userName="joey99";
	private String userType="Employee";
	private String lastLoginTime="2014-09-22";
	private User user=null;

	/**
	 * Tests that openFile flags false if file does not exist
	 **/
	@Test
	public void cannotOpenFiletest() {
		inputFileName="FileDoesNotExist";
		assertTrue("File not found",(json.openFile(inputFileName))==false);
	}

	/**
	 * Tests that openFile flags true if file exists
	 **/
	@Test
	public void canOpenFiletest() {
		inputFileName="users.json";
		assertTrue("File found",json.openFile(inputFileName));
	}
	
	/**
	 * Tests that first user is returned from input file as expected when getListOfUsers called
	 **/
	@Test
	public void firstUserReturned() {
		openFile();
		itr=users.iterator();
		
		if(itr.hasNext()){
			user=(User) itr.next();
			assertEquals("ID in list is correct",id,user.userID);
			assertEquals("First name in list is correct",firstName,user.firstName);
			assertEquals("Second name in list is correct",secondName,user.secondName);
			assertEquals("User name in list is correct",userName,user.userName);
			assertEquals("User type in list is correct",userType,user.userType);
			assertEquals("Last login time in list is correct",lastLoginTime,user.lastLoginTime);
		}
	}
	
	/**
	 * Tests that list of User objects is correctly sorted by User ID via sortUsersByID
	 **/
	@Test
	public void usersSortedByID() {
		openFile();
		
		itr=users.iterator();
		//Unsorted list
		if(itr.hasNext()){
			user=(User) itr.next();
			assertEquals("ID at top of list is 4 as expected",id,user.userID);
		}
		json.sortUsersByID(users);//sort
		
		itr=users.iterator();
		//Check expected item at top of list
		if(itr.hasNext()){
			user=(User) itr.next();
			assertEquals("ID at top of sorted list is now 1",Integer.valueOf(1),user.userID);
		}
	}
	
	/**
	 * Tests that output file has been written
	 **/
	@Test
	public void checkJsonOutputFileExists(){
		openFile();
		
		outputFileName="users_new.json";
		json.sortUsersByID(users);
		json.writeFile(users,outputFileName);
		outputFile=new File(outputFileName);
		
		assertTrue("Output file found",outputFile.exists());
	}

	//Open file and get users as repeared throughout tests
	private void openFile(){
		inputFileName="users.json";
		json.openFile(inputFileName);
		users=json.getListOfUsers();
	}
}
