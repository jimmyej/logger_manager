package com.belatrix.logging.manager.logger;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class MyLoggerHandlerTest {

	private File logFile = new File("./logs/logFile.log");	
	
	@Test
	public void testcreateFile() {
		boolean pathExists = logFile.getParentFile().exists();
		Assert.assertEquals(true, pathExists);
		
		boolean fileExists = logFile.exists();
		Assert.assertEquals(true, fileExists);
	}

}
