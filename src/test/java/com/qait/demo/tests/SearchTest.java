package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

public class SearchTest extends TestBase {

String username = getYamlValue("username"),
       password = getYamlValue("password"),
       searchTerm = getYamlValue("homePage.searchTerm");

@Test
public void Step01_Launch_Application() {
        test.launchApplication();
        test.homePage.verifyUserIsOnHomePage();
}

@Test(dependsOnMethods = "Step01_Launch_Application")
public void Step02_Login_To_The_Application() {
        test.homePage.clickOnSignInLink();
        test.loginPage.enterLoginCredentials(username, password);
        test.homePage.verifyUserIsOnHomePage();
}

@Test(dependsOnMethods = "Step02_Login_To_The_Application")
public void Step03_Search_All_Content() {
        test.homePage.enterSearchTerm(searchTerm);
        test.resultPage.verifySearchTermAppearsInBreadCrumb(searchTerm);
        test.resultPage.verifyResults(searchTerm);
}

}
