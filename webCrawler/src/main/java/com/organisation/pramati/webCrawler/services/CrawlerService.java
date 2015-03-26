package com.organisation.pramati.webCrawler.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.organisation.pramati.webCrawler.resources.Constants;

public class CrawlerService {
	
	public void getHyperlinksOfGivenYearService(String mailYear,String urlToCrawl){
		
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    Set<String> hyperLinksSet = new HashSet<String>();
	    try {
	        url = new URL(Constants.URL_TO_CRAWL);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));

	        while ((line = br.readLine()) != null) {
	            if(line.contains("<a href="+"\""+mailYear))
	            {  
	            	int start =line.indexOf("href=")+6; 
	        		System.out.println(line.substring(start,start+11 ));
	        		
	        		hyperLinksSet.add(Constants.URL_TO_CRAWL+"/"+line.substring(start,start+11 ));
	        		
	            	 //  new DownloadEmails().saveEmails("http://mail-archives.apache.org/mod_mbox/maven-users/201503.mbox");
                     // System.out.println(line);
	            }
	            
	            
	        }
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            //exception
	        }
	    }
	}

		
	

}
