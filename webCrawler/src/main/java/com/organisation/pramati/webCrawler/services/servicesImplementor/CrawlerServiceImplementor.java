package com.organisation.pramati.webCrawler.services.servicesImplementor;

import java.util.ArrayList;

import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.serviceProcessor.CrawlerServiceProcessor;

public class CrawlerServiceImplementor implements CrawlerService{
	
public ArrayList<String> getHyperlinksOfGivenYearService(String mailYear)
	
	{
		CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();
		
		ArrayList<String> hyperLinksOfMonths = crawlerServiceProcessorObj.getHyperlinksOfGivenYearService(mailYear);
		
		if(hyperLinksOfMonths!=null && hyperLinksOfMonths.size()>0)
		{
			
			//ArrayList<String> hyperLinksOfAllMails= crawlerServiceProcessorObj.getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);
			//System.out.println("all urls" +hyperLinksOfAllMails.size()); 
			/*if(hyperLinksOfAllMails!=null && hyperLinksOfAllMails.size()>0)
				crawlerServiceProcessorObj.getViewRawMessageAndSaveEmails(hyperLinksOfAllMails);
			else		
			System.out.println("No mails to save");*/
				
		}
		
		else
			
			System.out.println("No mails exist for the year");
		
		
	
return hyperLinksOfMonths;

	}

public ArrayList<FileMetaData> getHyperLinksOfAllMonthsMails(ArrayList<String> hyperLinksOfMonths)
{
	CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();
	
	ArrayList<FileMetaData> hyperLinksOfAllMails= crawlerServiceProcessorObj.getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);
	
	return hyperLinksOfAllMails;

}

public void downloadMailService(String mailYear) {
	
	CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();

	crawlerServiceProcessorObj.downloadMailService(mailYear);
}

}
