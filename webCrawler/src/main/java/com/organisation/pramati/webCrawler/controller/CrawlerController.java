package com.organisation.pramati.webCrawler.controller;


import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.servicesImplementor.CrawlerServiceImplementor;
import com.organisation.pramati.webCrawler.workerThreads.WebCrawlerWorker;

public class CrawlerController {
	
	
	private static final int NTHREDS = 10;
	static Logger logger = Logger.getLogger(CrawlerController.class);
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("target/classes/log4j.properties");
		//Log in console in and log file
		logger.debug("Log4j appender configuration is successful !!");
		
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		
		String mailYear= args[0];
		String urlCrawled;
		
		CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();
		Set<String> hyperLinks=crawlerServiceObj.getHyperlinksOfGivenYearService(mailYear);
		Iterator <String> it=hyperLinks.iterator();
		 
		
		if(hyperLinks!=null && hyperLinks.size()>0)
		{	
		    while(it.hasNext()) {
		    	urlCrawled=it.next();
		    	
		    if(urlCrawled!=null)	
		    	
		    { 
		      Runnable worker = new WebCrawlerWorker(urlCrawled,mailYear);
		      System.out.println(worker.toString()+" thread name");
		      executor.execute(worker);
		    }
		   
        }	     
		    executor.shutdown(); // This will make the executor accept no new threads
		      // and finish all existing threads in the queue
		}	     
		     
		else
		{
			System.out.println("No mails exists for the entered year" +mailYear);
		    logger.error("In CrawlerController :No mails exists for the entered year" +mailYear);
		}
			
	}
}
