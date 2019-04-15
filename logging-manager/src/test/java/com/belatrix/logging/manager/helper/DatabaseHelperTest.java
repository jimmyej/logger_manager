package com.belatrix.logging.manager.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseHelperTest {
	
	private static final String insert_script = "insert into log_values (log_date, log_level, log_name, class_name, method_name, log_message, log_exception) values(?,?,?,?,?,?,?)";
	private static final String select_script = "select id,log_date,log_level,log_name,class_name,method_name,log_message,log_exception from log_values";
	private static final String scriptSchema = "create table if not exists log_values ( id int AUTO_INCREMENT PRIMARY KEY, log_date timestamp not null, log_level integer not null, log_name varchar(255) not null, class_name varchar(255), method_name varchar(255), log_message varchar(1000) not null, log_exception varchar(10000) );";
	
	private DatabaseHelper dbConnection;
	@Mock private DatabaseHelper mockDHConnection;
	@Mock private Connection mockConnection;
	@Mock private Statement mockStatement;
	@Mock private PreparedStatement mockPrepareStatement;
	@Mock private ResultSet mockResultset;
	  
	@Before
	public void setUp() throws SQLException {
		dbConnection = new DatabaseHelper();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetConnection() throws ClassNotFoundException, SQLException {
		Assert.assertNotNull(dbConnection);
		Connection conn = dbConnection.getConnection();
		Assert.assertEquals(conn!= null, true);
	}
	@Test
	public void testDbInitializer() throws SQLException {
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.execute(scriptSchema)).thenReturn(true);
		Mockito.when(mockDHConnection.dbInitializer()).thenReturn(true);
		
		boolean success = mockDHConnection.dbInitializer();
		Assert.assertEquals(true, success);
	}
	@Test
	public void testInsertRecordLog() throws SQLException {
		LogRecord record = new LogRecord(Level.INFO, "test message");
		Mockito.when(mockConnection.prepareStatement(insert_script)).thenReturn(mockPrepareStatement);
		Mockito.when(mockPrepareStatement.executeUpdate()).thenReturn(1);
		Mockito.when(mockDHConnection.insertRecordLog(record)).thenReturn(1);
		int counter = mockDHConnection.insertRecordLog(record);
		Assert.assertEquals(1, counter);
	}
	@Test
	public void testShowLogs() throws SQLException {
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(select_script)).thenReturn(mockResultset);
		Mockito.when(mockResultset.next()).thenReturn(true);
		Mockito.when(mockDHConnection.showLogs()).thenReturn(10);
		int counter = mockDHConnection.showLogs();
		Assert.assertEquals(10, counter);
		
	}
}
