package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAO {
	protected static Connection db;
	private String BASE_URL = "jdbc:postgresql://localhost/"; 
	private String PROJECT_NAME = "matchservice";
	public DAO(){
		if(db == null) {
			try {
				String URL =  BASE_URL + PROJECT_NAME;
				String USERNAME = "henrique";
				String PASSWD = "1234";
				
				Properties prop = new Properties();
				prop.setProperty("user", USERNAME);
				prop.setProperty("password", PASSWD);
				
				db = DriverManager.getConnection(URL, prop);
				
			} catch (SQLException e) {
				System.err.println("SQLException: " + e.getMessage());
			}
			
		}
	}
}
