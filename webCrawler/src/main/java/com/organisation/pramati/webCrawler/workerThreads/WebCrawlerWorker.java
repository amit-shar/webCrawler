package com.organisation.pramati.webCrawler.workerThreads;

import java.util.Set;

import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.servicesImplementor.CrawlerServiceImplementor;

/**
 * @author amits
 *
 */
public class WebCrawlerWorker implements Runnable{

	private String hyperlinks;
	private String mailYear;

	public WebCrawlerWorker(String hyperlinks,String mailYear)
	{

		this.hyperlinks=hyperlinks;
		this.mailYear=mailYear;
	}



	public void run() {
		CrawlerService crawlerServiceObj= new CrawlerServiceImplementor();
		Set<FileMetaData> hyperLinkForAllEmails= crawlerServiceObj.getHyperLinksOfAllMonthsMails(hyperlinks);
		System.out.println("in run method"+hyperLinkForAllEmails.size());
		crawlerServiceObj.downloadMailService(hyperLinkForAllEmails,mailYear);


	}

}
