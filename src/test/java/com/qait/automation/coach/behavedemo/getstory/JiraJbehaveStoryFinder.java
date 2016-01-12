package com.qait.automation.coach.behavedemo.getstory;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;

import com.qait.automation.SAM.assessment.behavedemo.utils.HttpClient;
import com.qait.automation.SAM.assessment.behavedemo.utils.JsonParser;
import com.sun.jersey.api.client.ClientResponse;

/**
 *
 * @author prashantshukla
 */
public class JiraJbehaveStoryFinder {

    private final String jiraStoryResource;
    private static final String STORY_JSON_KEY = "scenarios";

    public JiraJbehaveStoryFinder(String jiraStoryId) {
        this.jiraStoryResource = Constants.JIRA_URL + Constants.JIRA_PATH_BEHAVE_STORY + jiraStoryId+ Constants.Scenario +"os_username="+Constants.JIRA_USERNAME+"&os_password="+Constants.JIRA_PASSWORD;
    }

    public String getStoryUrl() {
        return this.jiraStoryResource;
    }

    public String getStory() throws IOException,JSONException{
//        try{
    	HttpClient httpclient = new HttpClient();
        ClientResponse response = httpclient.getHttpResponse(this.jiraStoryResource);
        String entity = response.getEntity(String.class);
        return new JsonParser().getJsonValue(entity, STORY_JSON_KEY);
   
    }
}
