package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

/**
 *
 * @author prashantshukla
 */
public class HomePageActions extends GetPage {
    
    public HomePageActions(WebDriver driver) {
        super(driver, "HomePage");
    }

    public void verify_user_is_on_home_page() {
        verifyPageTitleContains();
    }

    public void navigate_to_account_page() {
        
        element("btn_Account").click();
    }
}
