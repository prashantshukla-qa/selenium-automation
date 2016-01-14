package com.qait.automation.coach.behavedemo.getstory;

import java.util.Arrays;
import java.util.List;

public final class Constants {

	public static final String JIRA_URL = "http://localhost:8080";
	public static final String JIRA_USERNAME = "automation-script";
	public static final String JIRA_PASSWORD = "Qait@123";


	public static final String JIRA_PROJECT_ID = "COACH";
	public static final String JIRA_PATH_RAPIDVIEW = "/rest/greenhopper/1.0/xboard/work/allData.json?rapidViewId=";
	public static final String JIRA_PATH_JBEHAVE_STORY = "rest/jbehave-for-jira/1.0/find/for-issue/";
	public static final String JIRA_PATH_BEHAVE_STORY = "/rest/cucumber/1.0/issue/";
	public static final String JIRA_ISSUE = "rest/api/latest/issue/";
	public static final String JIRA_Behave_API = "/rest/cucumber/1.0/project/";
	public static String feature = "feature";
	public static final String featureID = "1";
	public static final String Scenario = "/scenarios?";
	public static final String JIRA_SEARCH_ISSUE_JQL = "/rest/api/2/search?jql=issue=";
	// public static final String
	// JIRA_ISSUE_ENVIRONMENT_AND_DESCRIPTION="&fields=description,environment";

	public static final String JIRA_COMMENT = "comment/";
	public static final String JIRA_ASSIGNEE = "assignee/";
	public static final String JIRA_TRANSITION = "transitions/";

	public static final List<String> ALLOWED_STATUS = Arrays.asList("Ready For QA", "To Do", "In Progress");
	public static final List<String> ALLOWED_TYPE = Arrays.asList("Story", "Bug", "Epic", "Test", "Task");

	public static final String STORY_LOC = "./src/test/resources/stories/"; // DO
        
        
        public static final String STORY_XML_LOC = "target/jbehave/";
	public static final String JIRA_PATH_SPECIFIC_STORY = "/rest/api/2/search?";
	public static final String JQL_STORY_OF_TYPE = "jql=project = " + JIRA_PROJECT_ID.toLowerCase()
			+ " AND issuetype = \"story\" AND status in (Open, \"Ready for QA\") AND text ~ ";
	public static final String JQL_DEFAULT_STORY = "jql=project = " + JIRA_PROJECT_ID.toLowerCase()
			+ " AND issuetype = \"story\" AND status in (Open, \"Ready for QA\")";
	public static final String JQL_STORY_SUMMARY = "&fields=summary";
	public static final String JQL_STORY_COMPONENT = "AND component";
	public static final String JQL_TASK = "jql=project = " + JIRA_PROJECT_ID.toLowerCase()
			+ " AND issuetype = \"story\" AND status in (Open, \"Ready for QA\") AND text ~ ";
}
