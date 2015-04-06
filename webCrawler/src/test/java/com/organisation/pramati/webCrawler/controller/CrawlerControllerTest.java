package com.organisation.pramati.webCrawler.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CrawlerControllerTest {


	private CrawlerController helper;

	@Before
	public void setUp()
	{

		helper= new CrawlerController();

	}

	@Test
	public void isYearNumerictest() {

		assertTrue(helper.isYearNumeric("2015"));
		/*assertFalse(helper.isYearNumeric("0000"));
		assertFalse(helper.isYearNumeric("20A1"));
		assertFalse(helper.isYearNumeric("Ab15"));
		assertFalse(helper.isYearNumeric("Ab@#1"));
		assertFalse(helper.isYearNumeric(""));*/

	}


	@After
	public void tear()
	{
		helper=null;
	}



}
