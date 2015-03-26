package com.organisation.pramati.webCrawler.resources;

public final class Constants {
	
	
 public static final String URL_TO_CRAWL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
	
 
 
 
 // PRIVATE //

 /**
  The caller references the constants using <Constants.URL_TO_CRAWL> 
  and so on. Thus, the caller should be prevented from constructing objects of 
  this class, by declaring this private constructor. 
 */
	private Constants()
	{
		
		//this prevents even the native class from 
	    //calling this constructor as well
	  throw	new AssertionError();
	}

}
