package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class MyAccountPage extends GetPage {

	long currentTimeStamp = 0;
	public MyAccountPage(WebDriver driver) {
		super(driver, "MyAccountPage");
	}

	public void verify_User_Is_On_My_Account_Page() {
		verifyPageTitleContains();
		isElementDisplayed("txt_Welcome");
		logMessage("User has succesfully logged in");
	}

}
