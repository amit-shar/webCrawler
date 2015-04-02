package com.organisation.pramati.webCrawler.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import com.organisation.pramati.webCrawler.model.FileMetaData;

/**
 * @author amits
 *
 */
public interface CrawlerService {

	Set<String> getHyperlinksOfGivenYearService(String mailYear) throws MalformedURLException, IOException;
	Set<FileMetaData> getHyperLinksOfAllMonthsMails(String hyperLinksOfMonths);
	void downloadMailService(Set<FileMetaData> hyperLinkForAllEmails,String mailYear);	

}
