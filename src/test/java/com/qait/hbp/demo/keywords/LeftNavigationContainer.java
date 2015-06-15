package com.qait.hbp.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.hbp.automation.getpageobjects.GetPage;

public class LeftNavigationContainer extends GetPage {

	public LeftNavigationContainer(WebDriver driver) {
		super(driver, "LeftNavigationContainer");
	}

	public void verify_left_nav_bar_is_open() {
		isElementDisplayed("nav_shadowContainer");
		isElementDisplayed("txt_topic");
	}

	public void verify_topic_count_is_correct() {
		int topicCount = elements("lst_topics").size();
		String topicList = element("txt_topic").getText();
		int displayedTopicCount = Integer.parseInt(topicList.substring(
				topicList.indexOf("(") + 1, topicList.indexOf(")")));
		Assert.assertEquals(
				topicCount,
				displayedTopicCount,
				"[ASSERT FAILED]: Topic list displayed  '" + displayedTopicCount + "' in the navigation bar is not equal to the actual number of topics - " + topicCount);
		logMessage("[ASSERT PASSED]: Topic List displayed number is same as actual number of topics - "
				+ topicCount);
	}

	public void open_team_management_topic() {
		element("lnk_teamManagement").click();
	}
}
