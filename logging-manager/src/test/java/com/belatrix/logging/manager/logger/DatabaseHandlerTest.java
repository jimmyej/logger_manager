package com.belatrix.logging.manager.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.belatrix.logging.manager.helper.DatabaseHelper;

import junit.framework.Assert;
import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseHandlerTest extends TestCase {
	@Mock private DatabaseHelper databaseHelper;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	public void testPublish() {
		Mockito.when(databaseHelper.dbInitializer()).thenReturn(true);
		boolean initializad = databaseHelper.dbInitializer();
		Assert.assertTrue(initializad);
		
		LogRecord record = new LogRecord(Level.INFO, "test message");
		
		Mockito.when(databaseHelper.insertRecordLog(record)).thenReturn(1);
		
		int success = databaseHelper.insertRecordLog(record);
		Assert.assertEquals(1, success);
	}
}
