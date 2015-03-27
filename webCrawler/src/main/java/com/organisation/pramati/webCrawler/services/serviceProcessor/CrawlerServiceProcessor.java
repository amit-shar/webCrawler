package com.organisation.pramati.webCrawler.services.serviceProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.organisation.pramati.webCrawler.resources.Constants;

public class CrawlerServiceProcessor {
	
	
public Set<String> getHyperlinksOfGivenYearService(String mailYear){
	
	URL url;
    InputStream is = null;
    Matcher m;
    BufferedReader br;
    String line;
    Set<String> hyperLinksSet = new HashSet<String>();
    
    
    Pattern p = Pattern.compile("href=\"(.*?)\"");
    
    try {
        url = new URL(Constants.URL_TO_CRAWL);
        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));

        while ((line = br.readLine()) != null) {
        	
            if(line.contains("<a href="+"\""+mailYear))
            {  
            	//int start =line.indexOf("href=")+6; 
        		//hyperLinksSet.add(Constants.URL_TO_CRAWL+"/"+line.substring(start,start+11 ));
            	 m = p.matcher(line);
            	 if (m.find()) 
            		 hyperLinksSet.add(Constants.URL_TO_CRAWL+m.group(1).substring(0,12));
             }
            
            
        }
        if(hyperLinksSet!=null && hyperLinksSet.size()>0)
        	return hyperLinksSet;
        
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
    
   
    
    	return null;
	
	}

public Set<String> getHyperLinksOfAllMonthsMails(Set<String> hyperLinksOfMonths) {
	
	if(hyperLinksOfMonths!=null && hyperLinksOfMonths.size()>0)
	{
		
		URL url;
	    InputStream is = null;
	    Matcher m;
	    BufferedReader br;
	    String line;
	    String link;
	  
	    Pattern p = Pattern.compile("href=\"(.*?)\"");
		 Set<String> hyperLinksSet = new HashSet<String>();
		Iterator<String> it = hyperLinksOfMonths.iterator();
		
		while (it.hasNext()) {
		   // System.out.println(it.next());
		    link=it.next();
		    try {
		        url = new URL(link);
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));

		        while ((line = br.readLine()) != null) {
		        	
		            if(line.contains("subject"))
		            {  
		            	//int start =line.indexOf("href=")+6; 
		        		//hyperLinksSet.add(Constants.URL_TO_CRAWL+"/"+line.substring(start,start+11 ));
		            	 m = p.matcher(line);
		            	 if (m.find()) 
		            		 hyperLinksSet.add(link+"raw/"+m.group(1));
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
		    
		    if(hyperLinksSet!=null && hyperLinksSet.size()>0)
		    	return hyperLinksSet;
	

		}
		
		
	}
	
	return null;
}



}
