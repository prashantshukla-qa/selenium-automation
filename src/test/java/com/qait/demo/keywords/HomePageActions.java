package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

/**
 *
 * @author prashantshukla
 */
public class HomePageActions extends GetPage {

    WebDriver driver;

    public HomePageActions(WebDriver driver) {
        super(driver, "HomePage");
        this.driver = driver;
    }

    public void navigateToSpecificCountrySite(String areaName, String countryName) {
        wait.waitForPageToLoadCompletely();

        if (getProperty("./Config.properties", "browser").equalsIgnoreCase("mobile") || this.driver.manage().window().getSize().getWidth() < 678) {
            element("lnk_area", areaName).click();
        }
        element("lnk_country", countryName).click();

        logMessage("[INFO]: User clicked on '" + countryName + "' in the country list");
    }

    public void verify_user_is_on_home_page() {

        verifyPageTitleContains();
    }

    public void navigate_to_account_page() {
        wait.waitForPageToLoadCompletely();
        element("btn_Account").click();
        logMessage("[INFO]: User clicked on Registration link");
    }

    public void verifyUserName(String name) {
        wait.waitForPageToLoadCompletely();

        isElementDisplayed("txt_userName");

        Assert.assertTrue(element("txt_userName").getText().contains(name), "Assertion Failed :: user name displayed on top panel is not the one just created");

        logMessage("[INFO]: Correct user name is displayed on header of the application");
    }

    public void logOut() {
        hover(element("lnk_accountDropdown"));
        isElementDisplayed("lnk_signOut");
        element("lnk_signOut").click();
        logMessage("[INFO]: User has logged out successfully");
    }

}
