package com.pramati.webcrawler.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.pramati.webcrawler.services.CrawlerService;
import com.pramati.webcrawler.services.implementor.CrawlerServiceImplementor;

/**
 * @author amits
 *
 */
public class CrawlerController {


	private static final int NTHREDS = 12;
	static Logger logger = Logger.getLogger(CrawlerController.class);

	/*
	 * This is the entry for the application. We are passing 
	 * command line arguments to this method.
	 * 
	 */
	public static void main(String[] args) throws NumberFormatException, MalformedURLException, IOException {

		BasicConfigurator.configure();

		String mailYear= args[0];
		CrawlerController crawlerControllerObj= new CrawlerController();

		System.out.println("Year entered"+mailYear);
		logger.debug("year entered"+mailYear );

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


	/*
	 * This method will call the implementor layer methods and returns 
	 * set of hyper-links for the entered year.
	 * If entered year is not a valid year it will throw numberFormatException
	 * 
	 */
	protected  Set<String> mainHelper(String mailYear) throws NumberFormatException, MalformedURLException, IOException
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



	/*
	 * This method will check the 'year' is valid or not and returns 
	 * true/false accordingly.
	 */
	protected boolean isYearNumeric(String mailYear)
	{
		if(mailYear!=null && mailYear.length()==4 && !mailYear.equals("0000") && mailYear.matches("[0-9]+") )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/*
	 * This method uses execution framework for creating multiple thread to execute task.
	 * It will call 'run' method from workerThread layer.
	 * Currently thread pool size is set to 12.
	 * 
	 */
	protected  void createExecutorThreadPool(Set<String> hyperLinks,String mailYear)
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
		executor.shutdown(); /* This will make the executor accept no new threads
		 and finish all existing threads in the queue*/

	}
}
