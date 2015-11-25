package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class MyAccountPage extends GetPage {

    long currentTimeStamp = 0;

    public MyAccountPage(WebDriver driver) {
        super(driver, "MyAccountPage");
    }

    public void verify_User_Is_On_My_Account_Page() {
        verifyPageTitleContains();
        isElementDisplayed("txt_Welcome");
        logMessage("[INFO]: User has succesfully logged in");
    }

    public void verify_user_is_on_my_account_page(String userId) {
        verifyPageTitleContains();
        isElementDisplayed("txt_Welcome");
        logMessage("[INFO]: User " + userId +"has succesfully logged in");
    }

}
