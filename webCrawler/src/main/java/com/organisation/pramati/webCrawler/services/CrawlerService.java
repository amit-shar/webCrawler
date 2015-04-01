package com.organisation.pramati.webCrawler.services;

import java.util.Set;

import com.organisation.pramati.webCrawler.model.FileMetaData;

public interface CrawlerService {
	
	Set<String> getHyperlinksOfGivenYearService(String mailYear);
	Set<FileMetaData> getHyperLinksOfAllMonthsMails(String hyperLinksOfMonths);
    void downloadMailService(Set<FileMetaData> hyperLinkForAllEmails,String mailYear);	

}
