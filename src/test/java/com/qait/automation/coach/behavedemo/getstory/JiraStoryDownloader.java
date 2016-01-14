/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.coach.behavedemo.getstory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.jbehave.core.model.TableTransformers.Replacing;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.qait.automation.coach.behavedemo.utils.report.email.Emailer;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.HttpClient;

import gherkin.lexer.Fi;
import net.minidev.json.JSONArray;

/**
 *
 * @author prashantshukla
 */
public class JiraStoryDownloader {

	@SuppressWarnings("unused")
	private static final String storyLoc = Constants.STORY_LOC;
	private final JiraJbehaveStoryFinder jiraStory;
	private final String jiraStoryId;
	public String completeScenario = "";
	public String completedScenario = "";
	public StringBuffer sBuffer = new StringBuffer(15);

	public JiraStoryDownloader(String jiraStoryId) {
		this.jiraStoryId = jiraStoryId;
		this.jiraStory = new JiraJbehaveStoryFinder(this.jiraStoryId);
	}

	@SuppressWarnings("unused")
	public void storeJiraStoryLocally(String storyId) {
		completeScenario = "";
		completedScenario = "";
		try {
			HttpClient client = new HttpClient();

			URL url = new URL(Constants.JIRA_URL + "/rest/cucumber/1.0/issue/" + storyId + "/scenarios?os_username="
					+ Constants.JIRA_USERNAME + "&os_password=" + Constants.JIRA_PASSWORD);
			HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
			InputStream content1 = (InputStream) connection1.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content1));
			String responseScenarios = in.readLine();

			String featureId = "";
			JSONArray scenarios = JsonPath.read(responseScenarios, "$.scenarios[*]");
			if (scenarios.size() != 0) {
				featureId = JsonPath.read(responseScenarios, "$.scenarios[0].links.view.href");
			}
			featureId = featureId.substring(featureId.indexOf("feature/") + "feature/".length(),
					featureId.indexOf("/scenario"));
			
			System.out.println("JIRA Story Id: " + jiraStoryId);
			System.out.println("Feature Id: " + featureId);
			
			URL url1 = new URL(Constants.JIRA_URL + "/rest/cucumber/1.0/project/" + Constants.JIRA_PROJECT_ID
					+ "/feature/" + featureId + "?os_username=" + Constants.JIRA_USERNAME + "&os_password="
					+ Constants.JIRA_PASSWORD);

			HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader responseFeature = new BufferedReader(new InputStreamReader(content));
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(responseFeature.readLine());
			JsonObject jsonObject = (JsonObject) obj;
			String featureHeadline = StringUtils.remove("Feature: " + jsonObject.get("name").toString(), '"');

			JSONArray scenarios_count = JsonPath.read(responseScenarios, "$.scenarios[*]");
			for (int i = 0; i < scenarios_count.size(); i++) {
				String scenariosSteps = "";
				String scenarioTitle = "";
				boolean check_outline = JsonPath.read(responseScenarios, "$.scenarios[" + i + "].outline");
				String scenarios_title = JsonPath.read(responseScenarios, "$.scenarios[" + i + "].name");
				if (check_outline == false) {
					scenarioTitle = "Scenario: " + scenarios_title;
				} else
					scenarioTitle = "Scenario Outline: " + scenarios_title;

				scenariosSteps = JsonPath.read(responseScenarios, "$.scenarios[" + i + "].steps");
				completeScenario = scenarioTitle + "\n" + scenariosSteps;
				sBuffer.append("\n" + completeScenario + "\n");
			}

			String componentAndSummary = getStoryComponentAndSummary(storyId);
			String featureFile = featureHeadline + "\n\n" + "Meta:\n@storyName " + storyId + "\n" + "@component "
					+ componentAndSummary.split(" ")[0] + "\n" + "@summary "
							+ componentAndSummary.split(" ")[1] + "\n" + sBuffer.toString();

			try {
				File filename = new File("src/test/resources/stories/" + storyId + ".story");
				FileUtils.writeStringToFile(filename, featureFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (MalformedURLException urlException) {
			urlException.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getStoryComponentAndSummary(String storyId) {
		String component = null;
		String summary= null;
		try {
			URL url = new URL(Constants.JIRA_URL + Constants.JIRA_SEARCH_ISSUE_JQL + storyId + "&fields=components,summary"
					+ "&os_username=" + Constants.JIRA_USERNAME + "&os_password=" + Constants.JIRA_PASSWORD);
			HttpClient client = new HttpClient();
			String response = client.getHttpResponse(url.toString()).getEntity(String.class);
			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse(response);
                        summary = StringUtils.remove(obj.get("issues").getAsJsonArray().get(0).getAsJsonObject()
					.get("fields").getAsJsonObject().get("summary").toString(), '"').toLowerCase();
			component = StringUtils
					.remove(obj.get("issues").getAsJsonArray().get(0).getAsJsonObject().get("fields").getAsJsonObject()
							.get("components").getAsJsonArray().get(0).getAsJsonObject().get("name").toString(), '"');
			component = component.split(" ")[1].toLowerCase();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Component is not defined in the story");
		}

		return component + " " + summary;
	}

	public void storeJiraStoryLocally() {
		try {
			if (!getJiraStory().contains("[]")) {
				storeJiraStoryLocally(jiraStoryId);
			}
		} catch (JSONException ex) {
			try {
				new Emailer(this.jiraStoryId, "nojbehavestory", "qa-engineer").sendResultsMail();
				ex.printStackTrace();
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[INFO: ]" + jiraStoryId + " does not have BEHAVE story written yet. Mail sent to "
					+ ConfigPropertyReader.getProperty("qa-engineer"));
		}
	}

	/*
	 * public void storeJiraStoryLocally(String jiraStory) { File storiesDir =
	 * new File(storyLoc); if (!storiesDir.exists()) { storiesDir.mkdir(); }
	 * 
	 * try (Writer writer = new BufferedWriter(new OutputStreamWriter( new
	 * FileOutputStream(storyLoc + this.jiraStoryId + ".story"), "utf-8"))) {
	 * writer.write(jiraStory); System.out.println(
	 * "Loading Test Story for Testing:- " + this.jiraStoryId); } catch
	 * (UnsupportedEncodingException ex) { ex.printStackTrace();
	 * Logger.getLogger(JiraStoryDownloader.class.getName()).log( Level.SEVERE,
	 * null, ex); } catch (FileNotFoundException ex) { ex.printStackTrace();
	 * Logger.getLogger(JiraStoryDownloader.class.getName()).log( Level.SEVERE,
	 * null, ex); } catch (IOException ex) { ex.printStackTrace();
	 * Logger.getLogger(JiraStoryDownloader.class.getName()).log( Level.SEVERE,
	 * null, ex); }
	 * 
	 * }
	 */

	private String getJiraStory() throws IOException, JSONException {
		return this.jiraStory.getStory();
	}

	@SuppressWarnings("hiding")
	public static Map<String, String> getStoryDetails(String storyName) {
		Map<String, String> properties = new HashMap<String, String>();
		try {
			HttpClient client = new HttpClient();
			URL FieldsUrl = new URL(Constants.JIRA_URL + Constants.JIRA_SEARCH_ISSUE_JQL + storyName + "&os_username="
					+ Constants.JIRA_USERNAME + "&os_password=" + Constants.JIRA_PASSWORD);
			System.out.println(FieldsUrl);
			String responseStoryDetails = client.getHttpResponse(FieldsUrl.toString()).getEntity(String.class);
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(responseStoryDetails);
//			properties.put("env", StringUtils.remove(jsonObject.getAsJsonArray("issues").get(0).getAsJsonObject()
//					.get("fields").getAsJsonObject().get("env").toString(), '"'));
			properties.put("jiraBrowser", StringUtils.remove(jsonObject.getAsJsonArray("issues").get(0)
					.getAsJsonObject().get("fields").getAsJsonObject().get("description").toString(), '"'));
			String taskId = StringUtils.remove(
					jsonObject.getAsJsonArray("issues").get(0).getAsJsonObject().get("fields").getAsJsonObject()
							.get("parent").getAsJsonObject().get("fields").getAsJsonObject().get("summary").toString(),
					'"');
			if (taskId.contains("POC")) {
				properties.put("taskId", taskId.substring(taskId.lastIndexOf('-') - 5, taskId.lastIndexOf('-') - 1));
			} else {
				properties.put("taskId", taskId.split(" ")[0]);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return properties;
	}	
}
