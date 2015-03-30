package com.organisation.pramati.webCrawler.services.serviceProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.organisation.pramati.webCrawler.Model.FileMetaData;
import com.organisation.pramati.webCrawler.resources.Constants;
import com.organisation.pramati.webCrawler.services.CrawlerService;

public class CrawlerServiceProcessor implements CrawlerService{
	
	
public ArrayList<String> getHyperlinksOfGivenYearService(String mailYear){
	
	URL url;
    InputStream is = null;
    Matcher m;
    BufferedReader br;
    String line;
    ArrayList<String> hyperLinksSet = new ArrayList<String>();
    
    
    Pattern p = Pattern.compile("href=\"(.*?)\"");
    
    try {
        url = new URL(Constants.URL_TO_CRAWL);
        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));
int count=0;
        while ((line = br.readLine()) != null) {
        	
            if(line.contains("<a href="+"\""+mailYear))
            {  count++;
            	//int start =line.indexOf("href=")+6; 
        		//hyperLinksSet.add(Constants.URL_TO_CRAWL+"/"+line.substring(start,start+11 ));
            	System.out.println(line);
            	 m = p.matcher(line);
            	 if (m.find()) 
            		 hyperLinksSet.add(Constants.URL_TO_CRAWL+m.group(1).substring(0,12));
             }
            
            
        }
        
        System.out.println("no of months urls"+hyperLinksSet.size()+"   "+count );
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

public ArrayList<FileMetaData> getHyperLinksOfAllMonthsMails(ArrayList<String> hyperLinksOfMonths) {
	
	if(hyperLinksOfMonths!=null && hyperLinksOfMonths.size()>0)
	{
		
		URL url;
	    InputStream is = null;
	    Matcher m;
	    BufferedReader br;
	    String line;
	    String link;
	  
	    Pattern p = Pattern.compile("href=\"(.*?)\"");
	    ArrayList<FileMetaData> hyperLinksSet = new ArrayList<FileMetaData>();
		Iterator<String> it = hyperLinksOfMonths.iterator();
		FileMetaData fileMetaDataObj;
		
		while (it.hasNext()) {
		   // System.out.println(it.next());
		    link=it.next();
		    try {
		        url = new URL(link);
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
		       /* Writer wr=new StringWriter();
		        wr.write(br.read());
		        wr.toString();*/
		        while ((line = br.readLine()) != null) {
		        	
		        	 fileMetaDataObj= new FileMetaData();
		        	
		            if(line.contains("subject"))
		            {  
		            	//int start =line.indexOf("href=")+6; 
		        		//hyperLinksSet.add(Constants.URL_TO_CRAWL+"/"+line.substring(start,start+11 ));
		            	 m = p.matcher(line);
		            	 if (m.find()) 
		            		 fileMetaDataObj.setHyperLinkOfMail(link+"raw/"+m.group(1)); 
		            		 
		             }
		            
		            if(line.contains("<td class=\"author"))
		            	fileMetaDataObj.setAuthorName(getAuthor(line));
		           if(line.contains("<td class=\"date"))
		             fileMetaDataObj.setDateOfMail(getDate(line));
		           
		           if(line.contains("<td class=\"subject"))
		        	   fileMetaDataObj.setSubjectOfMail(getSubject(line));
		           
           		 hyperLinksSet.add(fileMetaDataObj);
  
		        }
		        System.out.println("no of subject urls"+hyperLinksSet.size());
		        
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
		    	System.out.println("no of subject urls"+hyperLinksSet.size());
		    	return hyperLinksSet;
	

		}
		
		
	}
	
	return null;
}

private String getSubject(String line) {
	
	if(line.contains("<td class=\"subject"))
	{	System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
	return line.substring(line.indexOf("%3e\">")+5,line.lastIndexOf("<")).replaceAll("</a>","");
	}

		return null;
}

private String getDate(String line) {
	
	
	if(line.contains("<td class=\"date"))
	{	System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
	return line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
	}

		return null;
}

private String getAuthor(String line) throws IOException {
	
		if(line.contains("<td class=\"author"))
		{	System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
		return line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
		}
		
	
	
		
		return null;
}

public void downloadMailService(String mailYear) {
	
ArrayList <String> hyperLinksOfMonths=getHyperlinksOfGivenYearService(mailYear);
ArrayList<FileMetaData> hyperLinkForAllEmails=getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);
saveEmails(hyperLinkForAllEmails);

	

	
	
}

private void saveEmails(ArrayList<FileMetaData> hyperLinkForAllEmails) {
	
	
	
		
}



}
