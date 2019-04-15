package com.belatrix.logging.manager.logger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.belatrix.logging.manager.helper.DatabaseHelper;

public class DatabaseHandler extends Handler {

	private DatabaseHelper databaseHelper = null;
	
	public DatabaseHandler() {
		databaseHelper = new DatabaseHelper();
	}
	@Override
	public void publish(LogRecord record) {
		databaseHelper.insertRecordLog(record);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

}
