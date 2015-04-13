package com.pramati.webcrawler.services.implementor;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.pramati.webcrawler.model.FileMetaData;
import com.pramati.webcrawler.services.CrawlerService;


/**
 * @author amits
 *
 */
public class CrawlerServiceImplementor implements CrawlerService{

	private CrawlerServiceProcessor crawlerServiceProcessorObj=new CrawlerServiceProcessor();
	static Logger logger = Logger.getLogger(CrawlerServiceImplementor.class);

	public Set<String> getHyperlinksOfGivenYearService(String mailYear) throws MalformedURLException, IOException

	{

		Set<String> hyperLinksOfMonths = crawlerServiceProcessorObj.getHyperlinksOfGivenYearService(mailYear);	
		return hyperLinksOfMonths;

	}

	public Set<FileMetaData> getHyperLinksOfAllMonthsMails(String hyperLinksOfMonths)
	{

		Set<FileMetaData> hyperLinksOfAllMails=null;
		if(hyperLinksOfMonths!=null)
		{
			hyperLinksOfAllMails= crawlerServiceProcessorObj.getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);
			return hyperLinksOfAllMails;
		}

		else
		{
			System.out.println("No mails exist for this link");
			logger.error("No mails exist for this link or given hyperlink is invalid/empty");
		}

		return hyperLinksOfAllMails;
	}

	public void downloadMailService(Set<FileMetaData> hyperLinksOfAllMails,String mailYear) {

		if(hyperLinksOfAllMails!=null && hyperLinksOfAllMails.size()>0)
		{
			crawlerServiceProcessorObj.downloadMailService(hyperLinksOfAllMails,mailYear);
		}

		else 
		{
			System.out.println("Nothing to download");
			logger.error("No mails exist for download");

		}

	}

}
