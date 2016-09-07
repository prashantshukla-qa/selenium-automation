package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;


public class SearchTest extends TestBase {

    String username = getYamlValue("username"),
           password = getYamlValue("password"),
           searchTerm = getYamlValue("homePage.searchTerm");

    @Test
    public void Test01_Launch_Application() {
        test.launchApplication();
        //test.homePage.verifyUserIsOnHomePage();
    }

    @Test(dependsOnMethods = "Test01_Launch_Application")
    public void Test02_Login_To_The_Application() {
        test.homePage.clickOnSignInLink();
        test.loginPage.enterLoginCredentials(username, password);
        test.homePage.verifyUserIsOnHomePage();
    }

    @Test(dependsOnMethods = "Test02_Login_To_The_Application")
    public void Test03_Search_All_Content() {
        test.homePage.enterSearchTerm(searchTerm);
        test.resultPage.verifySearchTermAppearsInBreadCrumb(searchTerm);
        test.resultPage.verifyResults(searchTerm);
    }
}
