package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class HomePageActions extends GetPage {

    WebDriver driver;

    public HomePageActions(WebDriver driver) {
        super(driver, "HomePage");
        this.driver = driver;
    }

    public void verifyUserIsOnHomePage(){
        verifyPageTitleExact();
    }

    public void clickOnSignInLink() {
        if (verifyPageTitleExact()) {
            element("link_signIn").click();
            logMessage("[INFO] Clicked on Sign In link");
        }
    }

    public void enterSearchTerm(String searchTerm) {
        openNavigationMenu();
        element("txtbox_search").sendKeys(searchTerm);
        element("btn_search").click();
        logMessage("[INFO] User enters "+searchTerm+" as search string");
    }

    public void clickOnPublishersTab() {
        openNavigationMenu();
        element("link_publishers").click();
        logMessage("[INFO] Clicked on Publishers Tab");
    }

    protected void openNavigationMenu(){
        if (!element ("link_showNavMenu").getText().contains("Hide navigation")) {
            element("link_showNavMenu").click();
        }
    }
}
