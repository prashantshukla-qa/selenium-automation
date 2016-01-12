package com.qait.automation.coach.behavedemo.storyrunners;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.qait.automation.coach.behavedemo.utils.report.PublishJiraReport;

public class JiraReportIT {
	@Test
	public void test() throws IOException, ParseException {
		System.out.println("*************************************");
		System.out.println("********Generating Report*************");
		System.out.println("*************************************");

		PublishJiraReport jirareport = new PublishJiraReport();
		jirareport.pushJiraComment();
	}
}
