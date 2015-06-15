package com.qait.hbp.demo.tests;

import static com.qait.hbp.automation.utils.YamlReader.getData;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.hbp.automation.TestSessionInitiator;

/**
 *
 * @author prashant.shukla
 */
public class Team_Management_Test {

    TestSessionInitiator test;
    
    String[] browserSizes = {"720x360"}; 
    String [] tagToBeTested = {"all"};

    @BeforeClass
    @Parameters("browser")
    public void start_test_session(@Optional String browser) {
        test = new TestSessionInitiator("Team_Management_Test", browser);
    }

    @Test
    public void Test01_Launch_Application(){
    	test.launchApplication(getData("base_url"));
    	test.loginpage.verify_User_Is_On_Login_Page();
    	test.loginpage.testPageLayout(tagToBeTested);
    }
    
    @Test
    public void Test02_Login_To_Application_Using_Correct_Admin_Credentials() {
        test.loginpage.login_to_the_application_as("admin");
        test.homepage.verify_user_is_on_home_page();
        test.homepage.testPageLayout(tagToBeTested);
    }
    
    @Test
    public void Test03_verify_user_is_on_home_page_and_access_left_nav_bar() {
        test.homepage.verify_user_is_on_home_page();
        test.homepage.open_left_navigation_bar();
        test.leftnavcontainer.verify_left_nav_bar_is_open();
    }
    
    @Test
    public void Test04_verify_left_navigation_bar_and_access_team_management() {
        test.leftnavcontainer.verify_topic_count_is_correct();
        test.leftnavcontainer.open_team_management_topic();
    }
    
    @AfterClass
    public void stop_test_session(){
    	test.closeTestSession();
    }
    
}
