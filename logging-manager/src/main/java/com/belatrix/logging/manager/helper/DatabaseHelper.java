package com.belatrix.logging.manager.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.LogRecord;

/**
 * 
 * @author jimmy
 *
 */
public class DatabaseHelper {
	
	private static final String insert_script = "insert into log_values (log_date, log_level, log_name, class_name, method_name, log_message, log_exception) values(?,?,?,?,?,?,?)";
	private static final String select_script = "select id,log_date,log_level,log_name,class_name,method_name,log_message,log_exception from log_values";
	private static final ResourceBundle bundle = ResourceBundle.getBundle("database"); // Getting database properties
	
	/**
	 * 
	 * @return a connection instance
	 */
	public Connection getConnection() {
		
		Connection conn = null;
	    String driver = bundle.getString("driverClassName");
	    try {
			Class.forName(driver);
			String url = bundle.getString("url");
			String username = bundle.getString("username");
			String password = bundle.getString("password");
			
			Properties connProperties = new Properties();
			connProperties.put("user", username);
			connProperties.put("password", password);
			
			connProperties.put("autoReconnect", "true");
			conn = DriverManager.getConnection(url, connProperties);
		} catch (ClassNotFoundException e) {
			//TODO ignore this exception
			e.printStackTrace();
		} catch (SQLException e) {
			//TODO ignore this exception
			e.printStackTrace();
		}
	    return conn;
	}
	
	/**
	 * 
	 * @return true if the database was initialized
	 */
	public boolean dbInitializer() {
		boolean success = false;
		String scriptFilePath = "./resources/schema.sql";
		BufferedReader reader = null;
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			// initialize file reader
			reader = new BufferedReader(new FileReader(scriptFilePath));
			String line = null;
			// read script line by line
			while ((line = reader.readLine()) != null) {
				// execute query
				success = stmt.execute(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close file reader
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	/**
	 * 
	 * @param record
	 * @return 1 if the transaction was successfully
	 */
	public int insertRecordLog(LogRecord record) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(insert_script);
			
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, record.getLevel().intValue());
			pstmt.setString(3, record.getLoggerName());
			pstmt.setString(4, record.getSourceClassName());
			pstmt.setString(5, record.getSourceMethodName());
			pstmt.setString(6, record.getMessage());
			
			if (record.getThrown() != null) {
				pstmt.setString(7, record.getThrown().toString());
			} else {
				pstmt.setString(7, null);
			}
			count = pstmt.executeUpdate();
			
			
			System.out.println("inserted");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				//TODO ignore this exception
				e.printStackTrace();
			}	
	    }
		return count;
	}
	/**
	 * 
	 * @return counter: number of records
	 * created just to verify the records saved in database
	 */
	public int showLogs() {
		int count = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(select_script);
			while (rs.next()) {
				count++;
				System.out.println(
						rs.getInt("id") + ", "+
						rs.getTimestamp("log_date") + ", "+
						rs.getInt("log_level") + ", "+
						rs.getString("log_name") + ", "+
						rs.getString("class_name") + ", "+
						rs.getString("method_name") + ", "+
						rs.getString("log_message") + ", "+
						rs.getString("log_exception")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
}
