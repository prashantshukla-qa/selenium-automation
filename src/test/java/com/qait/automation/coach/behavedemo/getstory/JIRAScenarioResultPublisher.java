package com.qait.automation.coach.behavedemo.getstory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.testng.internal.Yaml;

import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.Comment;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.Transition;
import com.atlassian.jira.rest.client.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import com.qait.automation.utils.YamlReader;
public class JIRAScenarioResultPublisher {

	public  static  String completeScenario="";
	public  static  String completedScenario="";
	public static String jiraCommentUrl="";
	public static StringBuffer sBuffer = new StringBuffer(15);

	public static void addScenarioStatusInJIRAComment(String storyID, String comment){
		final JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
		URI jiraServerUri;
		try {
			jiraServerUri = new URI(Constants.JIRA_URL);
			final com.atlassian.jira.rest.client.JiraRestClient restClient = factory
					.createWithBasicHttpAuthentication(jiraServerUri,
							Constants.JIRA_USERNAME, Constants.JIRA_PASSWORD);
			final NullProgressMonitor pm = new NullProgressMonitor();
			Issue issue = restClient.getIssueClient().getIssue(storyID, pm);
			issue.getCommentsUri(); 
			final IssueRestClient client = restClient.getIssueClient();
			jiraCommentUrl=Constants.JIRA_URL+ "/rest/api/latest/issue/"+storyID+"/comment";
			System.out.println("jiraCommentUrl=="+ jiraCommentUrl);
			client.addComment(pm, new URI(jiraCommentUrl), Comment.valueOf(comment));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void jiraFeatureFileDownloader() {
		final JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
		URI jiraServerUri;
		try {
			jiraServerUri = new URI(Constants.JIRA_URL);
			final com.atlassian.jira.rest.client.JiraRestClient restClient = factory
					.createWithBasicHttpAuthentication(jiraServerUri,
							Constants.JIRA_USERNAME, Constants.JIRA_PASSWORD);
			final NullProgressMonitor pm = new NullProgressMonitor();
			Issue issue = restClient.getIssueClient().getIssue(YamlReader.getData("storyID"), pm);
			System.out.println(issue.getAttachmentsUri());
			System.out.println(issue.getCommentsUri());

			final IssueRestClient client = restClient.getIssueClient();

			Iterator<Transition> iter = client.getTransitions(
					client.getIssue(YamlReader.getData("storyID"), pm), pm).iterator();
			System.out.println(issue.getStatus().getName());
			while (iter.hasNext()) {
				Transition transition = iter.next();
				System.out.println(transition.getName() + "-ID-"
						+ transition.getId());
				if (transition.getName().equals("Close Issue")) {
					client.transition(issue, new TransitionInput(transition.getId()), pm);
				}
			}
			System.out.println(issue.getStatus().getName());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}


}
