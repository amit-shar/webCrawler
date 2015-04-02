package com.organisation.pramati.webCrawler.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.servicesImplementor.CrawlerServiceImplementor;
import com.organisation.pramati.webCrawler.workerThreads.WebCrawlerWorker;

/**
 * @author amits
 *
 */
public class CrawlerController {


	private static final int NTHREDS = 10;
	static Logger logger = Logger.getLogger(CrawlerController.class);


	public static void main(String[] args) throws NumberFormatException, MalformedURLException, IOException {

		BasicConfigurator.configure();

		logger.debug("Log4j appender configuration is successful !!");

		String mailYear= args[0];
		CrawlerController crawlerControllerObj= new CrawlerController();
		System.out.println("Year entered"+mailYear);

		Set<String> hyperLinks=crawlerControllerObj.mainHelper(mailYear);

		if(hyperLinks!=null && hyperLinks.size()>0)
		{	
			crawlerControllerObj.createExecutorThreadPool(hyperLinks,mailYear);
		}	     

		else
		{
			System.out.println("No mails exists for the entered year" +mailYear);
			logger.error("In CrawlerController :No mails exists for the entered year" +mailYear);
		}

	}

	public  Set<String> mainHelper(String mailYear) throws NumberFormatException, MalformedURLException, IOException
	{
		
		
       if(isYearNumeric(mailYear))
       {	
    	    CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();
            Set<String> hyperLinks=crawlerServiceObj.getHyperlinksOfGivenYearService(mailYear);
            logger.info("Count of year hyperlinks are "+hyperLinks.size());
		    return hyperLinks;
       }
      
       else
    	   
       {
    	   System.out.println("Year must contains numbers only");
		   logger.error("In CrawlerController :mainHelper(): Year must contains numbers only"); 
		   throw new NumberFormatException();
    	   
       }
	

	}
	
	public boolean isYearNumeric(String mailYear)
	{
		if(mailYear!=null && mailYear.length()==4 && !mailYear.equals("0000") && mailYear.matches("[0-9]+") )
			return true;
		else
			return false;
	}

	public  void createExecutorThreadPool(Set<String> hyperLinks,String mailYear)
	{
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);

		String urlCrawled;
		Iterator <String> it=hyperLinks.iterator();

		while(it.hasNext()) {
			urlCrawled=it.next();

			if(urlCrawled!=null)	

			{ 
				Runnable worker = new WebCrawlerWorker(urlCrawled,mailYear);
				System.out.println("Thread name"+worker.toString());
				logger.info("In CrawlerController :createExecutorThreadPool():Thread name "+worker.toString());
				executor.execute(worker);
			}

		}	     
		executor.shutdown(); // This will make the executor accept no new threads
		// and finish all existing threads in the queue

	}
}
