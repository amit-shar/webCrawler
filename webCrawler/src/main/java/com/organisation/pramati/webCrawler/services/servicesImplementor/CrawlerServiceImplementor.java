package com.organisation.pramati.webCrawler.services.servicesImplementor;


import java.util.Set;

import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.serviceProcessor.CrawlerServiceProcessor;

/**
 * @author amits
 *
 */
public class CrawlerServiceImplementor implements CrawlerService{

	public Set<String> getHyperlinksOfGivenYearService(String mailYear)

	{
		CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();

		Set<String> hyperLinksOfMonths = crawlerServiceProcessorObj.getHyperlinksOfGivenYearService(mailYear);	
		return hyperLinksOfMonths;

	}

	public Set<FileMetaData> getHyperLinksOfAllMonthsMails(String hyperLinksOfMonths)
	{
		CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();

		Set<FileMetaData> hyperLinksOfAllMails= crawlerServiceProcessorObj.getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);

		return hyperLinksOfAllMails;

	}

	public void downloadMailService(Set<FileMetaData> hyperLinksOfAllMails,String mailYear) {

		CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();
		crawlerServiceProcessorObj.downloadMailService(hyperLinksOfAllMails,mailYear);
	}

}
