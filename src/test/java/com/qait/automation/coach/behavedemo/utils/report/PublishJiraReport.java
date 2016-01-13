/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.coach.behavedemo.utils.report;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.qait.automation.coach.behavedemo.getstory.Constants;
import com.qait.automation.utils.FileHandler;
import com.qait.automation.utils.HttpClient;
import com.qait.automation.utils.StoryXMLParser;
import com.qait.automation.utils.TestResultsJsonParser;
import com.sun.jersey.api.client.UniformInterfaceException;
import static com.qait.automation.utils.TestStates.FAIL;
import static com.qait.automation.utils.TestStates.PASS;
import static com.qait.automation.utils.TestStates.PENDING;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

/**
 *
 * @author prashantshukla
 */

public class PublishJiraReport {

	//private StoryXMLParser xml;

	private Map<String, String> storyStatus = new LinkedHashMap<String, String>();

	public PublishJiraReport() {
	}

	TestResultsJsonParser jsonParser = new TestResultsJsonParser();
	StoryXMLParser xmlparser;
	
	/**
	 * @param jiraStoryId
	 * @return
	 */
	public String createJiraCommentJson(String jiraStoryId) {
		String scenarioTitle = "";
		int scenarioCount = xmlparser.getScenarioCount();
		
		System.out.println("================================");
		System.out.println("JIRA Story Id: " + jiraStoryId);

		String jsonResultText = "{ \"body\": \"";
		jsonResultText = jsonResultText + "**Test Results For :- " + jiraStoryId + "**\\n\\n";
		for (int i = 0; i < scenarioCount; i++) {
			scenarioTitle = xmlparser.getScenarioTitle(xmlparser.getScenario(i));

			String scenarioStatus = xmlparser.getScenarioResult(xmlparser.getScenario(i)).get(scenarioTitle);

			System.out.println("Scenario: " + scenarioTitle + " - " + scenarioStatus);

			Map<String, String> stepResults = xmlparser.getStepResults(xmlparser.getScenario(i));
			if (scenarioStatus.equalsIgnoreCase("passed")) {
				jsonResultText = jsonResultText + "Scenario::" + "{color:green}" + scenarioTitle + " - *Passed*"
						+ "{color}" + "\\n\\n";
				for (String step : stepResults.keySet()) {
					if (stepResults.get(step).equalsIgnoreCase("successful")) {
						jsonResultText = jsonResultText + "\\t{color:green}" + step + " - *Passed*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("failed")) {
						jsonResultText = jsonResultText + "\\t{color:red}" + step + " - *Failed*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("notPerformed")) {
						jsonResultText = jsonResultText + "\\t{color:blue}" + step + " - *Skipped*" + "{color}" + "\\n";
					}
				}
				this.storyStatus.put(jiraStoryId + ":" + "Scenario " + i, PASS);
			} else if (scenarioStatus.equalsIgnoreCase("failed")) {
				jsonResultText = jsonResultText + "Scenario::" + "{color:red}" + scenarioTitle + " - *Failed*"
						+ "{color}" + "\\n\\n";
				for (String step : stepResults.keySet()) {

					if (stepResults.get(step).equalsIgnoreCase("successful")) {
						jsonResultText = jsonResultText + "\\t{color:green}" + step + " - *Passed*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("failed")) {
						jsonResultText = jsonResultText + "\\t{color:red}" + step + " - *Failed*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("notPerformed")) {
						jsonResultText = jsonResultText + "\\t{color:blue}" + step + " - *Skipped*" + "{color}" + "\\n";
					}
				}
				this.storyStatus.put(jiraStoryId + ":" + "Scenario " + i, FAIL);

			} else if (scenarioStatus.equalsIgnoreCase("pending")) {
				jsonResultText = jsonResultText + "Scenario::" + "{color:blue}" + scenarioTitle + " - *PASSED*"
						+ "{color}" + "\\n\\n";

				for (String step : stepResults.keySet()) {

					if (stepResults.get(step).equalsIgnoreCase("pending")) {
						jsonResultText = jsonResultText + "\\t{color:green}" + step + " - *Passed*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("failed")) {
						jsonResultText = jsonResultText + "\\t{color:red}" + step + " - *Failed*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("skipped")) {
						jsonResultText = jsonResultText + "\\t{color:blue}" + step + " - *Skipped*" + "{color}" + "\\n";
					} else if (stepResults.get(step).equalsIgnoreCase("pending")) {
						jsonResultText = jsonResultText + "\\t{color:blue}" + step + " - *Pending*" + "{color}" + "\\n";
					}
				}
				this.storyStatus.put(jiraStoryId + ":" + "Scenario " + i, PENDING);
			}
			jsonResultText = jsonResultText + "\\n";
		}
		jsonResultText = jsonResultText + "\"}";
		System.out.println("================================");
		return jsonResultText;
	}

	public String pushJiraComment() {

		String response = "";
		try {
			HttpClient client = new HttpClient();
			

			for (String storyFilename : FileHandler.getFileNames(Constants.STORY_LOC, ".story")) {
				String jiraStoryId = storyFilename.split(".story")[0];
				xmlparser=new StoryXMLParser(jiraStoryId);
				
				String jiracommenturl = Constants.JIRA_URL + "/" + Constants.JIRA_ISSUE + jiraStoryId + "/"
						+ Constants.JIRA_COMMENT;

				// update JIRA only if execEnv is QA
				
					response = response
							+ client.postHttpResponse(jiracommenturl, this.createJiraCommentJson(jiraStoryId))
									.getEntity(String.class)
							+ "\n";
					// moveJiraTicket(jiraStoryId, this.storyStatus);
				
					System.out.println("=================================================");
					System.out.println("UPDATED THE JIRA TICKET");
					System.out.println("=================================================");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public String moveJiraTicket(String _jiraStoryId, Map<String, String> _storystatus) {
		String response = "";

		String jiratransitionurl = Constants.JIRA_URL + "/" + Constants.JIRA_ISSUE + _jiraStoryId + "/"
				+ Constants.JIRA_TRANSITION;
		System.out.println("jiratransitionurl::" + jiratransitionurl);

		if (getstoryStatus(_storystatus.values()).equalsIgnoreCase(PENDING)) {
			System.out.println("NO JIRA ACTION");
			getChangeAssigneeJson("rohitsingh@qainfotech.com");
			return "";
		} else if (getstoryStatus(_storystatus.values()).equalsIgnoreCase(FAIL)) {

			try {

				response = new HttpClient().postHttpResponse(jiratransitionurl, getReopenJiraTicketJson())
						.getEntity(String.class);
				getChangeAssigneeJson("rohitsingh@qainfotech.com");
			} catch (UniformInterfaceException e) {
			}
			System.out.println("\nREOPENING JIRA TICKET:- " + _jiraStoryId + "\n");
			return response;
		} else if (getstoryStatus(_storystatus.values()).equalsIgnoreCase(PASS)) {
			try {
				response = new HttpClient().postHttpResponse(jiratransitionurl, getCloseTicketJson())
						.getEntity(String.class);
				changeJiraAssignee(_jiraStoryId, "-1");
			} catch (UniformInterfaceException e) {

				// TODO: handle exception
			}
			System.out.println("\nCLOSING JIRA TICKET:- " + _jiraStoryId + "\n");
			return response;
		}
		return response;
	}

	private String getstoryStatus(Collection<String> storyvalues) {
		String returnValue = PASS;
		for (String value : storyvalues) {
			if (value.equalsIgnoreCase(FAIL)) {
				return FAIL;
			} else if (value.equalsIgnoreCase(PENDING)) {
				returnValue = PENDING;
			}
		}
		return returnValue;
	}

	private String changeJiraAssignee(String _jiraStoryId, String jiraUserName) {
		String response = "";
		String jiraassgineeurl = Constants.JIRA_URL + "/" + Constants.JIRA_ISSUE + _jiraStoryId + "/"
				+ Constants.JIRA_ASSIGNEE;

		try {
			response = new HttpClient().putHttpResponse(jiraassgineeurl, getChangeAssigneeJson(jiraUserName))
					.getEntity(String.class);
		} catch (UniformInterfaceException e) {
			// TODO: handle exception
		}
		return response;
	}

	private String getChangeAssigneeJson(String jiraUsername) {
		return "{ \"name\":\"" + jiraUsername + "\"}";
	}

	private String getCloseTicketJson() {
		return "{ \"transition\": { \"id\": \"71\" }}";
	}

	private String getReopenJiraTicketJson() {
		return "{ \"transition\": { \"id\": \"121\" }}";

	}

}
