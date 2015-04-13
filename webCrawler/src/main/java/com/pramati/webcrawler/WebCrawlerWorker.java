package com.pramati.webcrawler;

import java.util.Set;

import org.apache.log4j.Logger;

import com.pramati.webcrawler.model.FileMetaData;
import com.pramati.webcrawler.services.CrawlerService;
import com.pramati.webcrawler.services.implementor.CrawlerServiceImplementor;

/**
 * @author amits
 *
 */
public class WebCrawlerWorker implements Runnable{

	private String hyperlinks;
	private String mailYear;

	static Logger logger = Logger.getLogger(WebCrawlerWorker.class);


	public WebCrawlerWorker(String hyperlinks,String mailYear)
	{

		this.hyperlinks=hyperlinks;
		this.mailYear=mailYear;
	}



	public void run() {
		CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();

		Set<FileMetaData> hyperLinkForAllEmails= crawlerServiceObj.getHyperLinksOfAllMonthsMails(hyperlinks);

		System.out.println("In run method"+hyperLinkForAllEmails.size());
		logger.info("In the run methd, count of mails for the current thread is"+hyperLinkForAllEmails.size());

		if(hyperLinkForAllEmails!=null && hyperLinkForAllEmails.size()>0)
		{
			crawlerServiceObj.downloadMailService(hyperLinkForAllEmails,mailYear);
		}


	}

}
