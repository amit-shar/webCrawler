package com.organisation.pramati.webCrawler.services;

import java.util.ArrayList;

import com.organisation.pramati.webCrawler.model.FileMetaData;

public interface CrawlerService {
	
	ArrayList<String> getHyperlinksOfGivenYearService(String mailYear);
	ArrayList<FileMetaData> getHyperLinksOfAllMonthsMails(ArrayList<String> hyperLinksOfMonths);
    void downloadMailService(String mailYear);	

}
