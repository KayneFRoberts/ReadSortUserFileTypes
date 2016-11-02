package tests;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import main.OpenCsv;
import main.User;

/**
 * @author Kayne
 * Tests OpenCsv class
 */
public class OpenCsvTest {

	private OpenCsv csv= new OpenCsv();
	private String inputFileName=null;
	private String outputFileName=null;
	private File outputFile=null;
	private List<User> users=null;
	private Iterator<User> itr=null;
	
	//Dummy data from first entry in csv intput file
	private Integer id=3;
	private String firstName="David";
	private String secondName="Payne";
	private String userName="Dpayne";
	private String userType="Manager";
	private String lastLoginTime="2014-09-23";
	private User user=null;
	
	/**
	 * Tests that openFile flags false if file does not exist
	 **/
	@Test
	public void cannotOpenFiletest() {
		inputFileName="FileDoesNotExist";
		assertTrue("File not found",(csv.openFile(inputFileName))==false);
	}

	/**
	 * Tests that openFile flags true if file exists
	 **/
	@Test
	public void canOpenFiletest() {
		inputFileName="users.csv";
		assertTrue("File found",csv.openFile(inputFileName));
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
			assertEquals("ID at top of list is 3 as expected",id,user.userID);
		}
		csv.sortUsersByID(users);//Sort
		
		itr=users.iterator();
		//Check expected item at top of list
		if(itr.hasNext()){
			user=(User) itr.next();
			assertEquals("ID at top of sorted list is now 2",Integer.valueOf(2),user.userID);
		}
	}
	
	/**
	 * Tests that output file has been written
	 **/
	@Test
	public void checkCsvOutputFileExists(){
		openFile();
		
		outputFileName="users_new.csv";
		csv.sortUsersByID(users);
		csv.writeFile(users,outputFileName);
		outputFile=new File(outputFileName);
		
		assertTrue("Output file found",outputFile.exists());
	}
	
	//Open file and get users as repeared throughout tests
	private void openFile(){
		inputFileName="users.csv";
		csv.openFile(inputFileName);
		users=csv.getListOfUsers();
	}
	
}
