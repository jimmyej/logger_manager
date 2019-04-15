package com.belatrix.logging.manager.logger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MyLoggerHandler {
	
	private static Logger logger = Logger.getLogger("com.belatrix.logging.manager.logger");
	private static ResourceBundle bundle = ResourceBundle.getBundle("logging"); // getting logging properties

	private static Level level = null;
	
	public MyLoggerHandler() {
		level = Level.parse(bundle.getString("logging.level"));
		logger.setLevel(level); // setting the logging level
	}
	
	/**
	 * Configuring the console handler
	 */
	public static void myConsoleHandler() {
		Handler handler = new ConsoleHandler();
        handler.setFormatter(new MyCustomFormatter());
        logger.addHandler(handler);
	}
	
	/**
	 * Configuring the file handler
	 */
	public static void myFileHandler() {
		boolean append = false;
		Handler handler = null;
		try {
			createFile();
			handler = new FileHandler("./logs/logFile.log", append);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        handler.setFormatter(new MyCustomFormatter());
        logger.addHandler(handler);
	}
	/**
	 * Configuring the custom database handler
	 */
	public static void myDatabaseHandler() {
		Handler handler = new DatabaseHandler();
        logger.addHandler(handler);
	}
	
	/**
	 * Method to create the log directory if doesn't exists
	 */
	public static void createFile() {
		File logFile = new File("./logs/logFile.log");	
		try {
			if (!logFile.getParentFile().exists())
				logFile.getParentFile().mkdirs();
			if (!logFile.exists())
				logFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 
	 * @author jimmy
	 * Custom formatter for database handler
	 */
	private static class MyCustomFormatter extends Formatter {
		private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(df.format(new Date(record.getMillis()))).append(" - ");
            builder.append("[").append(record.getSourceClassName()).append("] - ");
            builder.append("[").append(record.getSourceMethodName()).append("] - ");
            builder.append("[").append(record.getLevel()).append("] - ");
            builder.append(formatMessage(record));
            builder.append("\n");
            return builder.toString();
        }
    }
}
