package main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * @author Kayne
 * Opens json file types, sorts by user ID and then writes to new output file
 */
public class OpenJson extends FileParser {

	private String jsonFileName = null;
	private String jsonOuputFileName = null;
	private JSONArray jsonArray=null;
	private JSONParser parser=null;
	
	private List<User> users=null;
	private JSONObject jsonRow = null;
	private Long userID=null;
	private FileWriter fileWriter = null;
	private Iterator itr = null;
	private User user = null;
	private Map jsonRows;

	/**
	 * @param String fileName: name of input file to open
	 * @return boolean: returns true or false if file successfully opened
	 * Opens json file type flagging whether successful or not
	 */
	@Override
	public boolean openFile(String fileName) {
		jsonFileName = fileName;
		parser = new JSONParser();

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(jsonFileName));//JSON array of JSON objects
			
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return List<User>: returns list of User objects
	 * Generates list of User objects from input file
	 */
	@Override
	public List<User> getListOfUsers(){
		users= new ArrayList<User>();
		
        for (Object object : jsonArray)
        {//Loop through each JSON object in JSON array
            jsonRow = (JSONObject) object;          
            userID=(long)jsonRow.get("user_id");
           //Create user object and add to list
            users.add(new User(userID.intValue(),(String)jsonRow.get("first_name"),
            		(String)jsonRow.get("last_name"),(String)jsonRow.get("username"),
            		(String)jsonRow.get("user_type"),(String)jsonRow.get("last_login_time")));
        }
		return users;
	}

	/**
	 * @param List<User> users: list of User objects to output
	 * @param String outputFileName: name of output file
	 * Creates output file and writes user attributes from list of User objects
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public void writeFile(List<User> users, String outputFileName) {
		jsonOuputFileName = outputFileName;//Output file
		this.users = users;//List of User objects
		itr = users.iterator();//To iterate through list of User objects
		jsonArray= new JSONArray();//JSON array to add each map row to
		
		try {
			fileWriter = new FileWriter(jsonOuputFileName);//Create new output file

			//Loop through users list and write to file
			while (itr.hasNext()) {
				user = (User) itr.next();				
				jsonRows = new LinkedHashMap();
				jsonRows.put("user_id",user.userID);
				jsonRows.put("first_name",user.firstName);
				jsonRows.put("last_name",user.secondName);
				jsonRows.put("username",user.userName);
				jsonRows.put("user_type",user.userType);
				jsonRows.put("last_login_time",user.lastLoginTime);
				jsonArray.add(jsonRows);
			}
			fileWriter.write(jsonArray.toJSONString());//Add JSON array to output file
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//Flush and close output file
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
