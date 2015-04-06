package com.organisation.pramati.webCrawler.utility;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileOperationUtilityTest {

	private FileOperationUtility helper;
	
	@Before
	public void setUp() throws Exception {
		helper= new FileOperationUtility();
	}

	@After
	public void tearDown() throws Exception {
		helper=null;
	}

	@Test
	public void createDirectoryTest() {
		
		File obj =helper.createDirectory(null);
	        assertEquals(obj, null);
	}

}
