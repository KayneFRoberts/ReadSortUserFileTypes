package main;
import java.util.List;

/**
 * @author Kayne
 * Main project file. Opens csv, xml and json file of users, re-orders by user ID and writes to new output files
 *
 */
public class ReadAndWriteFiles {

	public static String csvInputFileName="users.csv";
	public static String xmlFileName="users.xml";
	public static String jsonFileName="users.json";
	public static String csvOutputFileName="users_new.csv";
	public static String xmlOutputFileName="users_new.xml";
	public static String jsonOutputFileName="users_new.json";
	public static List<User> csvUsers=null;
	public static List<User> xmlUsers=null;
	public static List<User> jsonUsers=null;
	
	/**
	 * @param args
	 * main method to open, sort and write output files 
	 */
	public static void main(String[] args) {
		//Create csv, xml and json objects
		OpenCsv csv= new OpenCsv();
		OpenXml xml= new OpenXml();
		OpenJson json= new OpenJson();
		
		//Open csv, xml and json files
		csv.openFile(csvInputFileName);
		xml.openFile(xmlFileName);
		json.openFile(jsonFileName);

		//Retrieve list of users from files
		csvUsers=csv.getListOfUsers();
		xmlUsers=xml.getListOfUsers();
		jsonUsers=json.getListOfUsers();
		
		//Sorts users by user id
		csv.sortUsersByID(csvUsers);
		xml.sortUsersByID(xmlUsers);
		json.sortUsersByID(jsonUsers);
		
		//Write output files
		csv.writeFile(csvUsers,csvOutputFileName);
		xml.writeFile(xmlUsers,xmlOutputFileName);
		json.writeFile(jsonUsers, jsonOutputFileName);
		
	}

}
