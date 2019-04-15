package com.belatrix.logging.manager;

import java.util.logging.Logger;

import com.belatrix.logging.manager.helper.DatabaseHelper;
import com.belatrix.logging.manager.logger.MyLoggerHandler;


public class LoggerApplication {
	private static Logger logger = Logger.getLogger("com.belatrix.logging.manager.logger");
	
	public static void main(String[] args) {
		DatabaseHelper databaseHelper = new DatabaseHelper();
		
		databaseHelper.dbInitializer();		// Initializing database
		
		MyLoggerHandler.myConsoleHandler(); // Enabling console handler
		MyLoggerHandler.myFileHandler();	// Enabling file handler
		MyLoggerHandler.myDatabaseHandler();// Enabling database handler
		
		logger.info("testing info");
		logger.severe("testing severe");
		logger.warning("testing error");
		
		databaseHelper.showLogs();			// showing logs stored in the database
	}
}
