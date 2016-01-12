package com.qait.automation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestResultsJsonParser {
	public String storyId = "";
	public static Map<String, ArrayList<String>> story_Sceanrios = new HashMap<String, ArrayList<String>>();// storyID,
																											// Scenario
																											// names
	Map<String, ArrayList<String>> scenario_steps;
	Map<String, String> steps_results;
	Map<String, Map<String, String>> scenario_steps_results;

	public void formatTestResults() throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(
				System.getProperty("user.dir") + File.separator + "target" + File.separator + "cucumber.json"));
		String storyID = "";
		JSONParser parser = new JSONParser();
		JSONArray obj = (JSONArray) parser.parse(br);

		scenario_steps_results = new HashMap<String, Map<String, String>>();
		scenario_steps = new HashMap<String, ArrayList<String>>();
		for (int i = 0; i < obj.size(); i++) {
			JSONObject jsonObject = (JSONObject) obj.get(i);
			ArrayList<String> scenarioNames = new ArrayList<String>();
						
			storyID = jsonObject.get("uri").toString().split(".feature")[0];
			JSONArray obj1 = (JSONArray) jsonObject.get("elements");

			for (int k = 0; k < obj1.size(); k++) {
				JSONObject jsonObject1 = (JSONObject) obj1.get(k);
				scenarioNames.add(jsonObject1.get("name").toString());
				steps_results = new HashMap<String, String>();
				JSONArray stepsArray = (JSONArray) jsonObject1.get("steps");

				ArrayList<String> stepsNames = new ArrayList<String>();
				for (int j = 0; j < stepsArray.size(); j++) {
					JSONObject jsonObject2 = (JSONObject) stepsArray.get(j);

					stepsNames.add(j, jsonObject2.get("name").toString());

					JSONObject jsonObject3 = (JSONObject) jsonObject2.get("result");

					steps_results.put(jsonObject2.get("name").toString(), jsonObject3.get("status").toString());
					scenario_steps.put(jsonObject1.get("name").toString(), stepsNames);
				}
				scenario_steps_results.put(jsonObject1.get("name").toString(), steps_results);
			}
			story_Sceanrios.put(storyID, scenarioNames);
		}
	}

	public int getScenarioCount(String storyId) {
		return story_Sceanrios.get(storyId).size();
	}

	public String getScenarioName(String storyId, int index) {
		return story_Sceanrios.get(storyId).get(index);
	}

	public ArrayList<String> getScenarioSteps(String title) {
		return scenario_steps.get(title);
	}

	public String getStepsStatus(String title, String step) {
		return scenario_steps_results.get(title).get(step);
	}

}
