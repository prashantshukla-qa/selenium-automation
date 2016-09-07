package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;


public class PublishersTest extends TestBase {

String breadcrumbTitle = getYamlValue("publisherPage.breadcrumbTitle"),
       defaultNumberOfItems = getYamlValue("publisherPage.defaultNumberOfItemsPerPage"),
       changedNumberOfItems =  getYamlValue("publisherPage.changedNumberOfItemsPerPage");

@Test
public void Test01_Launch_Application() {
        test.launchApplication();
        //test.homePage.verifyUserIsOnHomePage();
}

@Test(dependsOnMethods = "Test01_Launch_Application")
public void Test02_Login_To_The_Application() {
      //  test.homePage.clickOnSignInLink();
        test.loginPage.enterLoginCredentials(getYamlValue("username"), getYamlValue("password"));
        test.homePage.verifyUserIsOnHomePage();
}

@Test(dependsOnMethods = "Test02_Login_To_The_Application")
public void Test03_Click_On_Publishers_Tab() {
        test.homePage.clickOnPublishersTab();
        test.publishersPage.verifyUserIsOnPublishersPage(breadcrumbTitle);
}

@Test(dependsOnMethods = "Test03_Click_On_Publishers_Tab")
public void Test04_Browse_By_Letter() {
        String letter = test.publishersPage.selectAnyRandomLetter();
        test.publishersPage.verifyResults(letter);
}

@Test(dependsOnMethods = "Test04_Browse_By_Letter")
public void Test05_Verify_Default_Number_Of_Items_Per_Page() {
        test.publishersPage.verifyNumberOfItems(defaultNumberOfItems);
}

@Test(dependsOnMethods = "Test05_Verify_Default_Number_Of_Items_Per_Page")
public void Test06_Change_Number_Of_Items_Per_Page_And_Verify() {
        test.publishersPage.changeItemsPerPage(changedNumberOfItems);
        test.publishersPage.verifyNumberOfItems(changedNumberOfItems);
}

}
