package com.organisation.pramati.webCrawler.services.serviceProcessor;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CrawlerServiceProcessorTest {

	private CrawlerServiceProcessor helper;

	@Before
	public void setUp() {
		helper= new CrawlerServiceProcessor();


	}


	@Test
	public void getDateTest()
	{
		String expectedDate = helper.getDate("<td class=\"date\">Fri, 13 Mar, 17:39</td>");
		String actualDate="Fri, 13 Mar, 17:39";
		assertEquals(expectedDate, actualDate);

	}



	@Test
	public void getSubjectTest()

	{
		String expectedSubject = helper.getSubject(" <td class=\"subject\"><a href=\"%3c55028E62.3090604@gmx.de%3e\">Re: What is going on with maven-javadoc-plugin?</a>     </td>");
		String actualSubject="Re: What is going on with maven-javadoc-plugin?";
		assertEquals(expectedSubject.trim(), actualSubject);

	}


	@Test
	public void getAuthorTest()
	{
		String expectedAuthor = helper.getAuthor("<td class=\"author\">Philipp Kraus</td>");
		String actualAuthor="Philipp Kraus";
		assertEquals(expectedAuthor, actualAuthor);

	}
	


	@After
	public void tear()
	{
		helper=null;
	}


}
