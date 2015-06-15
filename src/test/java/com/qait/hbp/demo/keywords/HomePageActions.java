package com.qait.hbp.demo.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.hbp.automation.getpageobjects.GetPage;

/**
 *
 * @author prashantshukla
 */
public class HomePageActions extends GetPage {

	public HomePageActions(WebDriver driver) {
		super(driver, "HomePage");
	}

	public void verify_user_is_on_home_page() {
		verifyPageTitleExact();
		verifyPageUrlContains("home");
	}

	public void open_left_navigation_bar() {
		element("btn_lefnav").click();
		isElementDisplayed("nav_shadowContainer");
	}
}
