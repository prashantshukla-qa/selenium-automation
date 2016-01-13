package com.qait.automation.coach.behavedemo.stepdefs;

import static com.qait.automation.utils.ConfigPropertyReader.checkIfValueIsNull;
import static com.qait.automation.utils.YamlReader.getData;

import java.util.Map;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Named;

import com.qait.automation.coach.behavedemo.getstory.JiraStoryDownloader;

public class LoginSteps extends BaseStepTest {

//	@BeforeScenario
//	public static void Setup() {
//		if (test == null)
//			BaseStepTest.baseStepTest();
//	}

	@AfterScenario
	public static void TeardownScenario() {
		if (checkIfValueIsNull(System.getProperty("env")) || System.getProperty("env").equals("qa1")) {
			test.closeBrowserSession();
			test = null;
		} 
	}

	@AfterStory
	public static void TeardownStory() {
		if (!checkIfValueIsNull(System.getProperty("env")) && !System.getProperty("env").equals("qa1"))
			test.closeBrowserSession();
	}

	@BeforeStory
	public void test(@Named("storyName") String storyName) {
		System.out.println("==================================");
		System.out.println("Fetching Environment and Browser and taskid");
		System.out.println("==================================");
		Map<String, String> properties = JiraStoryDownloader.getStoryDetails(storyName);
		for (String key : properties.keySet()) {
			System.setProperty(key, properties.get(key));
		}
		System.setProperty("storyIdValue", storyName);
		if (!checkIfValueIsNull(System.getProperty("env")) && !System.getProperty("env").equals("qa1")) {
			BaseStepTest.baseStepTest();
			test.launchApplication(getData("base_url"));
			test.homepage.verify_user_is_on_home_page();
	        test.homepage.navigate_to_country_specific_site(getData("country.area"), getData("country.name"));
		}
	}
}
