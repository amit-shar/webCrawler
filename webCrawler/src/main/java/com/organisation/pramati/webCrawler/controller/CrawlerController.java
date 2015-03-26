package com.organisation.pramati.webCrawler.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.organisation.pramati.webCrawler.services.CrawlerService;

public class CrawlerController {
	
	public CrawlerService crawlerServiceObj= new CrawlerService();

	public static void main(String[] args) {
		
		// String urlToCrawl= args[0];
		String mailYear= args[1];
		
		crawlerServiceObj.
		
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;

	    try {
	        url = new URL("http://mail-archives.apache.org/mod_mbox/maven-users/");
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));

	        while ((line = br.readLine()) != null) {
	            if(line.contains("<a href="+"\"2015"))
	            {  line.replaceAll(""","\"");
	            	 
	            	 //  new DownloadEmails().saveEmails("http://mail-archives.apache.org/mod_mbox/maven-users/201503.mbox");
                      
	            		System.out.println(line.substring(line.indexOf(".mbox")-6,-2)); 
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
