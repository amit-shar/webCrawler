package com.organisation.pramati.webCrawler.services;


import java.util.Set;

import com.organisation.pramati.webCrawler.services.serviceProcessor.CrawlerServiceProcessor;

public class CrawlerService {
	
	public CrawlerServiceProcessor crawlerServiceProcessorObj;
	
	public void downloadMailService(String mailYear)
	
	{
		crawlerServiceProcessorObj = new CrawlerServiceProcessor();
		
		Set<String> hyperLinksOfMonths = crawlerServiceProcessorObj.getHyperlinksOfGivenYearService(mailYear);
		
		if(hyperLinksOfMonths!=null && hyperLinksOfMonths.size()>0)
		{
			
			Set<String> hyperLinksOfAllMails= crawlerServiceProcessorObj.getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);
			
			/*if(hyperLinksOfAllMails!=null && hyperLinksOfAllMails.size()>0)
				crawlerServiceProcessorObj.getViewRawMessageAndSaveEmails(hyperLinksOfAllMails);
			else
				System.out.println("No mails to save");*/
				
		}
		
		else
			
			System.out.println("No mails exist for the year");
		
		
	}
	
	
		
	

}
