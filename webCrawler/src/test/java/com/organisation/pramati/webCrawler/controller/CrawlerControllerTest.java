package com.organisation.pramati.webCrawler.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CrawlerControllerTest {
  
	
	private CrawlerController helper;

@Before
public void before()
{
		
helper= new CrawlerController();
		 
}
	@Test
	public void isYearNumerictest() {
		
		assertTrue(helper.isYearNumeric("2015"));
		assertTrue(helper.isYearNumeric("0000"));
		assertTrue(helper.isYearNumeric("20A1"));
		assertTrue(helper.isYearNumeric("Ab15"));
		assertTrue(helper.isYearNumeric("Ab@#1"));

	}
	

	
	

}
