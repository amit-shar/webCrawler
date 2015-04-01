package com.organisation.pramati.webCrawler.controller;


import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


	public static void main(String[] args) {

		//logger.debug("Log4j appender configuration is successful !!");

		String mailYear= args[0];

		System.out.println("year entered"+mailYear);

		Set<String> hyperLinks=mainHelper(mailYear);

		if(hyperLinks!=null && hyperLinks.size()>0)
		{	

			createExecutorThreadPool(hyperLinks,mailYear);
		}	     

		else
		{
			System.out.println("No mails exists for the entered year" +mailYear);
			logger.error("In CrawlerController :No mails exists for the entered year" +mailYear);
		}

	}

	private static Set<String> mainHelper(String mailYear)
	{

		CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();
		Set<String> hyperLinks=crawlerServiceObj.getHyperlinksOfGivenYearService(mailYear);

		return hyperLinks;


	}

	private static void createExecutorThreadPool(Set<String> hyperLinks,String mailYear)
	{
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);

		String urlCrawled;
		Iterator <String> it=hyperLinks.iterator();

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
}
