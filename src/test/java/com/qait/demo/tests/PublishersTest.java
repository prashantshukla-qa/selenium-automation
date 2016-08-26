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

public class PublishersTest extends TestBase {

String breadcrumbTitle = getYamlValue("publisherPage.breadcrumbTitle"),
       defaultNumberOfItems = getYamlValue("publisherPage.defaultNumberOfItemsPerPage"),
       changedNumberOfItems =  getYamlValue("publisherPage.changedNumberOfItemsPerPage");

@Test
public void Step01_Launch_Application() {
        test.launchApplication();
        test.homePage.verifyUserIsOnHomePage();
}

@Test(dependsOnMethods = "Step01_Launch_Application")
public void Step02_Login_To_The_Application() {
        test.homePage.clickOnSignInLink();
        test.loginPage.enterLoginCredentials(getYamlValue("username"), getYamlValue("password"));
        test.homePage.verifyUserIsOnHomePage();
}

@Test(dependsOnMethods = "Step02_Login_To_The_Application")
public void Step03_Click_On_Publishers_Tab() {
        test.homePage.clickOnPublishersTab();
        test.publishersPage.verifyUserIsOnPublishersPage(breadcrumbTitle);
}

@Test(dependsOnMethods = "Step03_Click_On_Publishers_Tab")
public void Step04_Browse_By_Letter() {
        String letter = test.publishersPage.selectAnyRandomLetter();
        test.publishersPage.verifyResults(letter);
}

@Test(dependsOnMethods = "Step04_Browse_By_Letter")
public void Step05_Verify_Default_Number_Of_Items_Per_Page() {
        test.publishersPage.verifyNumberOfItems(defaultNumberOfItems);
}

@Test(dependsOnMethods = "Step05_Verify_Default_Number_Of_Items_Per_Page")
public void Step06_Change_Number_Of_Items_Per_Page_And_Verify() {
        test.publishersPage.changeItemsPerPage(changedNumberOfItems);
        test.publishersPage.verifyNumberOfItems(changedNumberOfItems);
}

}
