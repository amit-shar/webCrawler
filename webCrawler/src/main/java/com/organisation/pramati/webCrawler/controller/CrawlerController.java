package com.organisation.pramati.webCrawler.controller;


import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.CrawlerServiceClass;
import com.organisation.pramati.webCrawler.services.ServicesImplementor.CrawlerServiceImplementor;
import com.organisation.pramati.webCrawler.services.serviceProcessor.CrawlerServiceProcessor;

public class CrawlerController {
	
	//private CrawlerService crawlerServiceObj=new CrawlerService();

	public static void main(String[] args) {
		
		//String urlToCrawl=""; //args[0];
		String mailYear= "2015"; //args[1];
		CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();
		
		crawlerServiceObj.downloadMailService(mailYear);
		
	}
}
