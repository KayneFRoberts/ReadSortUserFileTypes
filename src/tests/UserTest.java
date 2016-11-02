package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import main.User;

/**
 * @author Kayne
 * Tests User class
 *
 */
public class UserTest {
	private User user=null;
	
	//Dummy data to test with 
	private Integer id=3;
	private String firstName="David";
	private String secondName="Payne";
	private String userName="Dpayne";
	private String userType="Manager";
	private String lastLoginTime="2014-09-23";
	
	
	/**
	 * Tests that User object created successfully and all user attributes correct
	 **/
	@Test
	public void checkUserCreated() {
		user=new User(3,"David","Payne","Dpayne","Manager","23/09/2014 09:35");
		
		assertEquals("User ID populated",id,user.userID);
		assertEquals("First name populated",firstName,user.firstName);
		assertEquals("Second name populated",secondName,user.secondName);
		assertEquals("User name populated",userName,user.userName);
		assertEquals("User type populated",userType,user.userType);
		assertEquals("Last login time populated",lastLoginTime,user.lastLoginTime);
	}

	/**
	 * Tests that last login date only converted where in valid input format, otherwise exception caught
	 **/
	@Test
	public void lastLoginDateNotConverted(){
		user=new User(id,firstName,secondName,userName,userType,"WRONGDATE");
		assertEquals("Last login time populated",null,user.lastLoginTime);
	}
	
	/**
	 * Tests that last login date sconverted where in valid input format
	 **/
	@Test
	public void lastLoginDateConverted(){
		user=new User(3,"X","X","X","X","01/12/2080 00:00");
		assertEquals("Last login time populated","2080-12-01",user.lastLoginTime);
	}
}
