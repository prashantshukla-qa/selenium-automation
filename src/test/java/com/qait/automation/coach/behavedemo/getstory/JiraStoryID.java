package com.qait.automation.coach.behavedemo.getstory;

import static com.qait.automation.utils.ConfigPropertyReader.checkIfValueIsNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qait.automation.utils.HttpClient;

public class JiraStoryID {

    /**
     * This function gets the list of Story Id based on the following criteria:
     * 1. Component 2. Task type
     *
     * @author QA InfoTech
     * @return {@code ArrayList<String>}
     *
     */
    @SuppressWarnings("deprecation")
    public static ArrayList<String> getAllStoryIds() {
        ArrayList<String> storyIds = new ArrayList<String>();
        String taskComponent = null;
        URL url = null;
        taskComponent = System.getProperty("component");
        String taskType = System.getProperty("taskType");
        String jqlQuery = null;
        String[] taskComponentList = {""};
        if (!checkIfValueIsNull(taskType)) {
            taskComponentList = taskComponent.split(",");
        }
        try {
            if (!checkIfValueIsNull(taskType)) {
                jqlQuery = Constants.JQL_STORY_OF_TYPE + "\"" + taskType + "\"";
            } else {
                jqlQuery = Constants.JQL_DEFAULT_STORY;
            }
            for (int i = 0; i < taskComponentList.length; i++) {

                if (checkIfValueIsNull(taskComponent)) {
                    url = new URL(Constants.JIRA_URL + Constants.JIRA_PATH_SPECIFIC_STORY
                            + URLEncoder.encode(jqlQuery).replace("%3D", "=").replace("+", "%20"));
                } else {
                    url = new URL(Constants.JIRA_URL + Constants.JIRA_PATH_SPECIFIC_STORY
                            + URLEncoder
                            .encode((jqlQuery + " " + Constants.JQL_STORY_COMPONENT + " = \"ms "
                                    + taskComponentList[i] + " 2016\""))
                            .replace("%3D", "=").replace("+", "%20"));

                }
                String response = HttpClient.class.newInstance().getHttpResponse(url.toString())
                        .getEntity(String.class);
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject) parser.parse(response);
                JsonArray issues = jsonObject.get("issues").getAsJsonArray();

                for (JsonElement obj : issues) {
                    String id = StringUtils.remove(((JsonObject) obj).get("key").toString(), '"');
                    storyIds.add(id);
                }
            }
            return storyIds;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function gets the list of Story Id based on the following criteria:
     * 1. Task Id 2. Task type
     *
     * @author QA InfoTech
     * @return {@code ArrayList<String>}
     *
     */
    @SuppressWarnings("deprecation")
    public static ArrayList<String> getAllStoryIdsForTask() {

        String taskIdList = System.getProperty("taskId");

        ArrayList<String> storyIds = new ArrayList<String>();
        URL url = null;
        String taskType = System.getProperty("taskType");
        String jqlQuery = null;

        try {
            String[] taskList = taskIdList.split(",");

            for (int i = 0; i < taskList.length; i++) {
                boolean flag = true;
                jqlQuery = Constants.JQL_TASK + "\"" + taskList[i] + "\"";

                if (!checkIfValueIsNull(taskType)) {
                    url = new URL(Constants.JIRA_URL + Constants.JIRA_PATH_SPECIFIC_STORY
                            + URLEncoder.encode(jqlQuery).replace("%3D", "=").replace("+", "%20"));

                    String response = HttpClient.class.newInstance().getHttpResponse(url.toString())
                            .getEntity(String.class);
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) parser.parse(response);
                    JsonArray subTasks = jsonObject.get("issues").getAsJsonArray().get(0).getAsJsonObject()
                            .get("fields").getAsJsonObject().get("subtasks").getAsJsonArray();

                    for (JsonElement obj : subTasks) {
                        String taskOfType = StringUtils.remove(
                                ((JsonObject) obj).get("fields").getAsJsonObject().get("summary").toString(), '"');
                        if (taskOfType.equalsIgnoreCase(taskType)) {
                            String id = StringUtils.remove(((JsonObject) obj).get("key").toString(), '"');
                            storyIds.add(id);
                        }
                    }
                } else {
                    url = new URL(Constants.JIRA_URL + Constants.JIRA_PATH_SPECIFIC_STORY
                            + URLEncoder.encode(jqlQuery).replace("%3D", "=").replace("+", "%20"));

                    String response = HttpClient.class.newInstance().getHttpResponse(url.toString())
                            .getEntity(String.class);

                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) parser.parse(response);
                    JsonArray subTasks = null;
                    if (jsonObject.get("issues").getAsJsonArray().size() > 1) {
                        for (int k = 0; k < jsonObject.get("issues").getAsJsonArray().size(); k++) {
                            if (jsonObject.get("issues").getAsJsonArray().get(k).toString().contains("POC")) {
                                subTasks = jsonObject.get("issues").getAsJsonArray().get(k).getAsJsonObject()
                                        .get("fields").getAsJsonObject().get("subtasks").getAsJsonArray();
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        subTasks = jsonObject.get("issues").getAsJsonArray().get(0).getAsJsonObject()
                                .get("fields").getAsJsonObject().get("subtasks").getAsJsonArray();
                    }

                    for (JsonElement obj : subTasks) {
                        String id = StringUtils.remove(((JsonObject) obj).get("key").toString(), '"');
                        storyIds.add(id);
                    }
                }
            }
            return storyIds;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
