package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

/**
 *
 * @author prashantshukla
 */
public class HomePageActions extends GetPage {
    
    public HomePageActions(WebDriver driver) {
        super(driver, "HomePage");
    }

    public void navigateToSpecificCountrySite(String countryName){
    	wait.waitForPageToLoadCompletely();
    	
    	element("lnk_country",countryName).click();
    	
    	logMessage("User clicked on '"+countryName+"' in the country list");
    }
    
    public void verify_user_is_on_home_page() {
    	
        verifyPageTitleContains();
    }

    public void navigate_to_account_page() {
        wait.waitForPageToLoadCompletely();
        element("btn_Account").click();
        logMessage("User clicked on Registration link");
    }
    
    public void verifyUserName(String name){
    	wait.waitForPageToLoadCompletely();
    	
    	isElementDisplayed("txt_userName");
    	
    	Assert.assertTrue(element("txt_userName").getText().contains(name), "Assertion Failed :: user name displayed on top panel is not the one just created");
    	
    	logMessage("Correct user name is displayed on header of the application");
    }
    
    public void logOut(){
    	hover(element("lnk_accountDropdown"));
    	isElementDisplayed("lnk_signOut");
    	element("lnk_signOut").click();
    	logMessage("User has logged out successfully");
    }
    
}
