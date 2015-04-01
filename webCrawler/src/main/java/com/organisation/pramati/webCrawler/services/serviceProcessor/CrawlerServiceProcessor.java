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
import org.apache.log4j.Logger;
import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.resources.Constants;
import com.organisation.pramati.webCrawler.services.CrawlerService;
import com.organisation.pramati.webCrawler.utility.FileOperationUtility;

public class CrawlerServiceProcessor implements CrawlerService{
	
	static Logger logger = Logger.getLogger(CrawlerServiceProcessor.class);
	
	
public Set<String> getHyperlinksOfGivenYearService(String mailYear){
	
	URL url;
    InputStream is = null;
    Matcher m;
    BufferedReader br;
    String line;
    Set<String> hyperLinksSet = new HashSet<String>();
    
    
    Pattern p = Pattern.compile(Constants.PATTERN_FOR_HREF);
    
    try {
    	
        url = new URL(Constants.URL_TO_CRAWL);
        is = url.openStream();  // throws an IOException
        br= new BufferedReader(new InputStreamReader(is));
    	
       
        while ((line = br.readLine()) != null) {
        	
            if(line.contains("<a href="+"\""+mailYear))
            {  
            	//System.out.println(line);
            	 m = p.matcher(line);
            	 if (m.find()) 
            		 hyperLinksSet.add(Constants.URL_TO_CRAWL+m.group(1).substring(0,12));
             }
            
            
        }
        
       
        if(hyperLinksSet!=null && hyperLinksSet.size()>0)
        {	hyperLinksSet=addPaginationLink(hyperLinksSet);
            System.out.println("After pagination logic"+hyperLinksSet.size());	
        	
        }
        
     } catch (MalformedURLException mue) {
         mue.printStackTrace();
    } catch (IOException ioe) {
         ioe.printStackTrace();
    } finally {
        try {
            if (is != null) is.close();
        } catch (IOException ioe) {
        	System.out.println("Exception ocurred while closing the file in method: getHyperlinksOfGivenYearService");
        	logger.error("Exception ocurred while closing the file in method: getHyperlinksOfGivenYearService");
        }
    }
    
   
    
    return hyperLinksSet;
	
	}

private Set<String> addPaginationLink(Set<String> hyperLinksSet) throws MalformedURLException,IOException {
	
	URL url;
    InputStream is = null;
    Matcher m;
    BufferedReader br;
    String line;
    Set<String> hyperLinksPaginationSet = new HashSet<String>();
    String hyperLink;
    String href=null;
    
    Pattern p = Pattern.compile(Constants.PATTERN_FOR_HREF); //("href=\"(.*?)\"");
    Iterator<String> it = hyperLinksSet.iterator();
    
    	
    	
    while(it.hasNext())
    {  
    	hyperLink=it.next();
        url = new URL(hyperLink);
        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
        	
            if(line.contains(Constants.PAGINATION_CHECK))
            {  
            	//System.out.println(line);
            	 m = p.matcher(line);
            	 while (m.find()) {
             	    href = m.group(1);
             	    if(href.contains("date"))
             	    {	//System.out.println("in pagination logic"+href);
             	    	hyperLinksPaginationSet.add(Constants.URL_PREFIX+href);}
             }
            
            
            }
            
        }
        
    }
    
    hyperLinksSet.addAll(hyperLinksPaginationSet);
    
    System.out.println("after pagination url counts"+hyperLinksSet.size());
    return hyperLinksSet;
     
    
    }   
	


/*public Set<FileMetaData> getHyperLinksOfAllMonthsMails(Set<String> hyperLinksOfMonths) {
	
	if(hyperLinksOfMonths!=null && hyperLinksOfMonths.size()>0)
	{
		
		URL url;
	    InputStream is = null;
	    Matcher m;
	    BufferedReader br;
	    String line;
	    String link;
	  
	    Pattern p = Pattern.compile(Constants.PATTERN_FOR_HREF);
	    Set<FileMetaData> hyperLinksSet = new HashSet<FileMetaData>();
		Iterator<String> it = hyperLinksOfMonths.iterator();
		FileMetaData fileMetaDataObj=null;
		int count =0;
		
		while (it.hasNext()) {
		    link=it.next();
		   // System.out.println(link);
		    try {
		        url = new URL(link);
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
		   
		        while ((line = br.readLine()) != null) {
		        	
		        	 if(count==0)
		        		 fileMetaDataObj= new FileMetaData();
		        	
		            if(line.contains("subject"))
		            {  
		            	
		            	 m = p.matcher(line);
		            	 if (m!=null && m.find()) 
		            	 {   if(m.group(1)!=null)
		            		 fileMetaDataObj.setHyperLinkOfMail(link+Constants.VIEW_RAW_MESSAGE+m.group(1)); 
		            	     count++;
		            	 }
		            	 
		            		 
		             }
		            
		            if(line.contains(Constants.AUTHOR_NAME_SEARCH))
		            {
		            	if(getAuthor(line)!=null)
		            	{ fileMetaDataObj.setAuthorName(getAuthor(line));
		            	 count++;
		            	 
		            	}
		            } 	 
		            	
		           if(line.contains(Constants.MAIL_DATE_TAG_SEARCH))
		        	   if(getDate(line)!=null)
		        	   { fileMetaDataObj.setDateOfMail(getDate(line));
		                 count++;
		        	   }
		           
		           if(line.contains(Constants.MAIL_SUBJECT_SEARCH))
		        	   if(getSubject(line)!=null) 
		        	   {     fileMetaDataObj.setSubjectOfMail(getSubject(line));
		                     count++;
		                 
		        	   }
		        if(count==4)
		        {
		        	hyperLinksSet.add(fileMetaDataObj);
		        	count=0;
		        }
  
		        }
		        System.out.println("no of subject urls"+hyperLinksSet.size());
		        
		        }
		        
		     catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		            if (is != null) is.close();
		        } catch (IOException ioe) {
		           System.out.println("Exception ocurred while closing the file in method: getHyperLinksOfAllMonthsMails");
		           logger.error("Exception ocurred while closing the file in method: getHyperlinksOfGivenYearService");
		        }
		    }
		    
		}
		 if(hyperLinksSet!=null && hyperLinksSet.size()>0)	    	
		    return hyperLinksSet;
	}
	
	return null;
}*/

public Set<FileMetaData> getHyperLinksOfAllMonthsMails(String hyperLinksOfMonths) {
	
	if(hyperLinksOfMonths!=null )
	{
		
		URL url;
	    InputStream is = null;
	    Matcher m;
	    BufferedReader br;
	    String line;
	    String link=hyperLinksOfMonths;
	  
	    Pattern p = Pattern.compile(Constants.PATTERN_FOR_HREF);
	    Set<FileMetaData> hyperLinksSet = new HashSet<FileMetaData>();
		
		FileMetaData fileMetaDataObj=null;
		int count =0;
		
		
		    try {
		        url = new URL(link);
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
		   
		        while ((line = br.readLine()) != null) {
		        	
		        	 if(count==0)
		        		 fileMetaDataObj= new FileMetaData();
		        	
		            if(line.contains("subject"))
		            {  
		            	
		            	 m = p.matcher(line);
		            	 if (m!=null && m.find()) 
		            	 {   if(m.group(1)!=null)
		            		 fileMetaDataObj.setHyperLinkOfMail(link+Constants.VIEW_RAW_MESSAGE+m.group(1)); 
		            	     count++;
		            	 }
		            	 
		            		 
		             }
		            
		            if(line.contains(Constants.AUTHOR_NAME_SEARCH))
		            {
		            	if(getAuthor(line)!=null)
		            	{ fileMetaDataObj.setAuthorName(getAuthor(line));
		            	 count++;
		            	 
		            	}
		            } 	 
		            	
		           if(line.contains(Constants.MAIL_DATE_TAG_SEARCH))
		        	   if(getDate(line)!=null)
		        	   { fileMetaDataObj.setDateOfMail(getDate(line));
		                 count++;
		        	   }
		           
		           if(line.contains(Constants.MAIL_SUBJECT_SEARCH))
		        	   if(getSubject(line)!=null) 
		        	   {     fileMetaDataObj.setSubjectOfMail(getSubject(line));
		                     count++;
		                 
		        	   }
		        if(count==4)
		        {
		        	hyperLinksSet.add(fileMetaDataObj);
		        	count=0;
		        }
  
		        }
		        System.out.println("no of subject urls"+hyperLinksSet.size());
		        
		        }
		        
		     catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		            if (is != null) is.close();
		        } catch (IOException ioe) {
		           System.out.println("Exception ocurred while closing the file in method: getHyperLinksOfAllMonthsMails");
		           logger.error("Exception ocurred while closing the file in method: getHyperlinksOfGivenYearService");
		        }
		    }
		    
		
		 if(hyperLinksSet!=null && hyperLinksSet.size()>0)	    	
		    return hyperLinksSet;
	}
	
	return null;
}

private String getSubject(String line) {
	String subject=null;
	
	if(line.contains(Constants.MAIL_SUBJECT_SEARCH))
	{	
		if(line.indexOf("%3e\">")!=-1 && line.lastIndexOf("<")!=-1)
	      subject=line.substring(line.indexOf("%3e\">")+5,line.lastIndexOf("<")).replaceAll("</a>","");
	 

	}


return subject;
	
}

private String getDate(String line) {
	String date=null;
	
	if(line.contains(Constants.MAIL_DATE_TAG_SEARCH))
	{	
	
	if(line.indexOf(">")!=-1 && line.lastIndexOf("<")!=-1)
    	date= line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
		 
	}

	return date;
}

private String getAuthor(String line) throws IOException {
	
	  String author=null;
	
		if(line.contains(Constants.AUTHOR_NAME_SEARCH))
		{
				
		if(line.indexOf(">")!=-1 && line.lastIndexOf("<")!=-1)	
		   author= line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));	
		}
		
		return author;
}



/*public void downloadMailService(String mailYear) {
	
/*FileOperationUtility fileOperationUtilityObj=new FileOperationUtility();
	
Set <String> hyperLinksOfMonths=getHyperlinksOfGivenYearService(mailYear);
Set<FileMetaData> hyperLinkForAllEmails=getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);

System.out.println("size of hyperlinks" +hyperLinkForAllEmails.size());

if(hyperLinkForAllEmails!=null && hyperLinkForAllEmails.size()>0)
	fileOperationUtilityObj.saveEmails(hyperLinkForAllEmails,mailYear);
	
}*/

public void downloadMailService(Set<FileMetaData> hyperLinkForAllEmails,String mailYear) {
	
FileOperationUtility fileOperationUtilityObj=new FileOperationUtility();

if(hyperLinkForAllEmails!=null && hyperLinkForAllEmails.size()>0)
	fileOperationUtilityObj.saveEmails(hyperLinkForAllEmails,mailYear);
	
}

}


