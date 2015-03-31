package com.organisation.pramati.webCrawler.controller;


import org.apache.log4j.Logger;


import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.servicesImplementor.CrawlerServiceImplementor;

public class CrawlerController {
	
	static Logger logger = Logger.getLogger(CrawlerController.class);
	public static void main(String[] args) {
		
		//PropertyConfigurator.configure("log4j.properties");
		//Log in console in and log file
		logger.debug("Log4j appender configuration is successful !!");
		
		String mailYear= "2015"; //args[1];
		CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();
		crawlerServiceObj.downloadMailService(mailYear);
		
	}
}
