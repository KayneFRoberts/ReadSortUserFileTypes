package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kayne
 * 
 * Opens csv file types, sorts by user ID and then writes to new output file
 */
public class OpenCsv extends FileParser {

	private String csvInputFileName = null;
	private String csvOutputFileName = null;
	private BufferedReader br = null;
	private final String CVSSPLITBY = ",";
	private final String CSVNEWLINE = "\n";
	private List<User> users = null;
	private String csvRow = null;
	private String[] userAttributes = null;
	private String csvHeader = null;
	private FileWriter fileWriter = null;
	private Iterator<User> itr = null;
	private User user = null;

	/**
	 * @param String fileName: name of input file to open
	 * @return boolean: returns true or false if file successfully opened
	 * Opens csv file type flagging whether successful or not
	 */
	@Override
	public boolean openFile(String fileName) {
		csvInputFileName = fileName;

		try {
			br = new BufferedReader(new FileReader(csvInputFileName));
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * @return List<User>: returns list of User objects
	 * Generates list of User objects from input file
	 */
	@Override
	public List<User> getListOfUsers() {
		users = new ArrayList<User>();//List of user objects

		try {
			csvHeader = br.readLine();//Store header for outputfile 

			while ((csvRow = br.readLine()) != null){ 
				// Comma will separate each user attribute 
				userAttributes = csvRow.split(CVSSPLITBY);
				//Create user object and add to list
				users.add(new User(Integer.valueOf(userAttributes[0]), userAttributes[1], userAttributes[2],
						userAttributes[3], userAttributes[4], userAttributes[5]));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return users;
	}

	/**
	 * @param List<User> users: list of User objects to output
	 * @param String outputFileName: name of output file
	 * Creates output file and writes user attributes from list of User objects
	 **/
	@Override
	public void writeFile(List<User> users, String outputFileName) {
		csvOutputFileName = outputFileName;//Output file
		this.users = users;//List of User objects
		itr = users.iterator();//To iterate through list of User objects
		try {
			fileWriter = new FileWriter(csvOutputFileName);
			fileWriter.append(csvHeader);//Write header row
			fileWriter.append(CSVNEWLINE);//For writing new lines

			//Loop through users list and write to file
			while (itr.hasNext()) {
				user = (User) itr.next();
				fileWriter.append(Integer.toString(user.userID));
				fileWriter.append(CVSSPLITBY);
				fileWriter.append(user.firstName);
				fileWriter.append(CVSSPLITBY);
				fileWriter.append(user.secondName);
				fileWriter.append(CVSSPLITBY);
				fileWriter.append(user.userName);
				fileWriter.append(CVSSPLITBY);
				fileWriter.append(user.userType);
				fileWriter.append(CVSSPLITBY);
				fileWriter.append(user.lastLoginTime);
				fileWriter.append(CSVNEWLINE);
			}

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
