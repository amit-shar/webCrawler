package com.organisation.pramati.webCrawler.services.serviceProcessor;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.resources.Constants;
import com.organisation.pramati.webCrawler.services.CrawlerService;

public class CrawlerServiceProcessor implements CrawlerService{
	
	
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
        //int count=0;
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
            return hyperLinksSet;
        	
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
    
   
    
    	return null;
	
	}

private Set<String> addPaginationLink(Set<String> hyperLinksSet) throws IOException {
	
	URL url;
    InputStream is = null;
    Matcher m;
    BufferedReader br;
    String line;
    Set<String> hyperLinksPaginationSet = new HashSet<String>();
    String hyperLink;
    String href=null;
    
    Pattern p = Pattern.compile("href=\"(.*?)\"");
    Iterator<String> it = hyperLinksSet.iterator();
    
    	
    	
    while(it.hasNext())
    {  
    	hyperLink=it.next();
        url = new URL(hyperLink);
        is = url.openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
        	
            if(line.contains("<th class=\"pages"))
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
    
   /* it=hyperLinksSet.iterator();
    while(it.hasNext())
    {
    	System.out.println("urls in comp pageset"+it.next());
    }*/
    
    System.out.println("after pagination url counts"+hyperLinksSet.size());
    return hyperLinksSet;
     
    
    }   
	


public Set<FileMetaData> getHyperLinksOfAllMonthsMails(Set<String> hyperLinksOfMonths) {
	
	if(hyperLinksOfMonths!=null && hyperLinksOfMonths.size()>0)
	{
		
		URL url;
	    InputStream is = null;
	    Matcher m;
	    BufferedReader br;
	    String line;
	    String link;
	  
	    Pattern p = Pattern.compile("href=\"(.*?)\"");
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
		       /* Writer wr=new StringWriter();
		        wr.write(br.read());
		        wr.toString();*/
		        while ((line = br.readLine()) != null) {
		        	
		        	 if(count==0)
		        		 fileMetaDataObj= new FileMetaData();
		        	
		            if(line.contains("subject"))
		            {  
		            	
		            	 m = p.matcher(line);
		            	 if (m!=null && m.find()) 
		            	 {   if(m.group(1)!=null)
		            		 fileMetaDataObj.setHyperLinkOfMail(link+"raw/"+m.group(1)); 
		            	     count++;
		            	 }
		            	 
		            		 
		             }
		            
		            if(line.contains("<td class=\"author"))
		            {
		            	if(getAuthor(line)!=null)
		            	{ fileMetaDataObj.setAuthorName(getAuthor(line));
		            	 count++;
		            	 
		            	}
		            } 	 
		            	
		           if(line.contains("<td class=\"date"))
		        	   if(getDate(line)!=null)
		        	   { fileMetaDataObj.setDateOfMail(getDate(line));
		                 count++;
		        	   }
		           
		           if(line.contains("<td class=\"subject"))
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
		            //exception
		        }
		    }
		    
		   /**/
	

		}
		 if(hyperLinksSet!=null && hyperLinksSet.size()>0)	    	
		    return hyperLinksSet;
	}
	
	return null;
}

private String getSubject(String line) {
	String subject=null;
	
	if(line.contains("<td class=\"subject"))
	{	//System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
		if(line.indexOf("%3e\">")!=-1 && line.lastIndexOf("<")!=-1)
	      subject=line.substring(line.indexOf("%3e\">")+5,line.lastIndexOf("<")).replaceAll("</a>","");
	 
//		if(subject!=null)
//		  return subject;
	}


return subject;
	
}

private String getDate(String line) {
	String date=null;
	
	if(line.contains("<td class=\"date"))
	{	//System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
	
	if(line.indexOf(">")!=-1 && line.lastIndexOf("<")!=-1)
    	date= line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
//	if(date!=null)
//		return date;
		 
	}

	return date;
}

private String getAuthor(String line) throws IOException {
	String author=null;
	
		if(line.contains("<td class=\"author"))
		{
			
			
		if(line.indexOf(">")!=-1 && line.lastIndexOf("<")!=-1)	
		   author= line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
		
			
		}
		
		return author;
	
}

public void downloadMailService(String mailYear) {
	
Set <String> hyperLinksOfMonths=getHyperlinksOfGivenYearService(mailYear);

Set<FileMetaData> hyperLinkForAllEmails=getHyperLinksOfAllMonthsMails(hyperLinksOfMonths);

System.out.println("size of hyperlinks" +hyperLinkForAllEmails.size());

if(hyperLinkForAllEmails!=null && hyperLinkForAllEmails.size()>0)
	 saveEmails(hyperLinkForAllEmails,mailYear);

	
}

private void saveEmails(Set<FileMetaData> hyperLinkForAllEmails,String mailYear) {
	
	createDirectory(Constants.DIR_PATH);
	File directory=createDirectory(Constants.DIR_PATH+"/"+mailYear);
	
	String filePath;

	if(hyperLinkForAllEmails!=null && hyperLinkForAllEmails.size()>0){
		
	for(FileMetaData file : hyperLinkForAllEmails)
		
	{
		filePath=getFilePath(file);
		File messageRawFile= createFile(directory,filePath);	
		System.out.println("sending email hyperlink" +file.getHyperLinkOfMail());
		saveEmailToFile(messageRawFile,file.getHyperLinkOfMail());
		
		
	}
	
	
	}
		
}

private File createDirectory(String dirPath) {
	
	
	//String dirPath="Downloads";
	File directory= new File(dirPath);
	boolean success;
	
	if (directory.exists()) {
        System.out.println("Directory already exists ...");

    } else {
        System.out.println("Directory not exists, creating now");

        success = directory.mkdir();
        if (success) {
            System.out.printf("Successfully created new directory : %s%n", dirPath);
        } else {
            System.out.printf("Failed to create new directory: %s%n", dirPath);
        }
    }
	return directory;
}

private void saveEmailToFile(File messageRawFile, String hyperLinkOfMail) {

        URL url;
        try {
        	
        	System.out.println("saving mail" +hyperLinkOfMail);
            url = new URL(hyperLinkOfMail);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            Writer out = new StringWriter();
            for(int i=in.read();i!=-1;i=in.read()){
            	out.write(i);
            }
            //char[] cbuf=new char[255];
//            while ((in.read(cbuf)) != -1) {
//                out.write(cbuf);
//            }
            FileWriter fw=new FileWriter(messageRawFile);
            fw.write(out.toString());
            fw.flush();
            fw.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	
}

private File createFile(File directory,String filePath) {
	
	boolean success=false;
	
	File messageRawFile= new File(directory, filePath);
	
	if (messageRawFile.exists()) {
        System.out.println("File already exists");

    } else {
        System.out.println("No such file exists, creating now");
        try {
			success = messageRawFile.createNewFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        if (success) {
            System.out.printf("Successfully created new file: %s%n", messageRawFile);
            
        } else {
            System.out.printf("Failed to create new file: %s%n",messageRawFile);
            
        }
	
    }
	return messageRawFile;
}

private String getFilePath(FileMetaData fileObj) {
	
	String filePath="";
	
	if(fileObj!=null)
	{  
		if(fileObj.getDateOfMail()!=null)
		{
			String date[]=fileObj.getDateOfMail().split(" ");
		    filePath=date[2]+fileObj.getAuthorName().replaceAll("/","")+fileObj.getSubjectOfMail().replaceAll("/","")+fileObj.getDateOfMail()+".txt";
	 }
	
	}
	return filePath;


}

}
