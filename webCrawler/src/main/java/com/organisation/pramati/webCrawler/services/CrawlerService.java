package com.organisation.pramati.webCrawler.services;

import java.util.Set;

import com.organisation.pramati.webCrawler.model.FileMetaData;

public interface CrawlerService {
	
	Set<String> getHyperlinksOfGivenYearService(String mailYear);
	Set<FileMetaData> getHyperLinksOfAllMonthsMails(Set<String> hyperLinksOfMonths);
    void downloadMailService(String mailYear);	

}
