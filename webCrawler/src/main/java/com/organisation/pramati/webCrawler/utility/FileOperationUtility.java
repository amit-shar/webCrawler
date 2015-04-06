package com.organisation.pramati.webCrawler.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.log4j.Logger;

import com.organisation.pramati.webCrawler.model.FileMetaData;
import com.organisation.pramati.webCrawler.resources.Constants;

public class FileOperationUtility {

	static Logger logger = Logger.getLogger(FileOperationUtility.class);



	public void saveEmails(Set<FileMetaData> hyperLinkForAllEmails,String mailYear) {

		createDirectory(Constants.DIR_PATH);
		File directory=createDirectory(Constants.DIR_PATH+"/"+mailYear);

		String filePath;

		if(directory!=null){

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

		else
			logger.error("directory not created");

	}
	/*
	 * This method creates the directory.
	 * 
	 */

	public File createDirectory(String dirPath) {


		//String dirPath="Downloads";

		if(dirPath!=null){

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
		return null;
	}

	public void saveEmailToFile(File messageRawFile, String hyperLinkOfMail) {

		URL url;
		FileWriter fw=null;
		try {

			System.out.println("saving mail" +hyperLinkOfMail);
			url = new URL(hyperLinkOfMail);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			Writer out = new StringWriter();
			for(int i=in.read();i!=-1;i=in.read()){
				out.write(i);
			}

			fw=new FileWriter(messageRawFile);
			fw.write(out.toString());
			fw.flush();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (fw != null) fw.close();
			} catch (IOException ioe) {
				//exception
			}
		}

	}


	/*
	 * This method creates the file to store the email raw message.
	 */
	public File createFile(File directory,String filePath) {

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


	/*
	 * This method creates the file name and path for the mail.
	 */
	public String getFilePath(FileMetaData fileObj) {

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
