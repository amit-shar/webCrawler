package com.organisation.pramati.webCrawler.services.servicesImplementor;


import java.util.Set;

import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.services.serviceProcessor.CrawlerServiceProcessor;

public class CrawlerServiceImplementor implements CrawlerService{
	
public Set<String> getHyperlinksOfGivenYearService(String mailYear)
	
	{
		CrawlerServiceProcessor crawlerServiceProcessorObj = new CrawlerServiceProcessor();
		
		Set<String> hyperLinksOfMonths = crawlerServiceProcessorObj.getHyperlinksOfGivenYearService(mailYear);
		
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
