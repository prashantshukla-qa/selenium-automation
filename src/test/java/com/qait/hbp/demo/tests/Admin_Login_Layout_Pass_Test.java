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
public class Admin_Login_Layout_Pass_Test {

    TestSessionInitiator test;
    
    String[] browserSizes = {"720x360"}; 
    String [] layoutTags = {"all"};

    @BeforeClass
    @Parameters("browser")
    public void start_test_session(@Optional String browser) {
        test = new TestSessionInitiator("Admin_Login_Tests", browser);
    }

    @Test
    public void Test01_Launch_Application(){
    	test.launchApplication(getData("base_url"));
    	test.loginpage.verify_User_Is_On_Login_Page();
    }
    
    @Test
    public void Test02_Login_To_Application_Using_Wrong_UserName() {
        test.loginpage.login_to_the_application_as("wrongusername", "wrongpassword");
        test.loginpage.verify_User_Is_On_Login_Page();
		test.loginpage.verify_Login_Error_Message_Is_Displayed("Your Username or Password is incorrect.");
        test.loginpage.testPageLayout(browserSizes, layoutTags);
    }

    @Test
    public void Test03_Login_To_Application_Using_Blank_UserName() {
        test.loginpage.login_to_the_application_as("", "wrongpassword");
        test.loginpage.verify_User_Is_On_Login_Page();
        test.loginpage.verify_submit_button_is_inactive();
    }
    
    @Test
    public void Test04_Login_To_Application_Using_Blank_Password() {
        test.loginpage.login_to_the_application_as("wrongusername", "");
        test.loginpage.verify_User_Is_On_Login_Page();
        test.loginpage.verify_submit_button_is_inactive();
    }
    
    @Test
    public void Test05_Login_To_Application_Using_Correct_Admin_Credentials() {
        test.loginpage.login_to_the_application_as("admin");
        test.homepage.verify_user_is_on_home_page();
    }
    
    @AfterClass
    public void stop_test_session(){
    	test.closeTestSession();
    }
    
}
