package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.qait.automation.TestSessionInitiator;

/**
 *
 * @author prashant.shukla
 */
public class Login_Layout_Test {

    TestSessionInitiator test;

    String[] browserSizes = {"720x360"};
    String[] layoutTags = {"mobile"};

    @BeforeClass
    @Parameters("browser")
    public void start_test_session(@Optional String browser) {
       // test = new TestSessionInitiator("Login_Layout_Tests", browser);
    }

    @Test
    public void Test01_Launch_Application() {
        test.launchApplication(getData("base_url"));
        test.homepage.verify_user_is_on_home_page();
        test.homepage.navigate_to_country_specific_site(getData("country.area"), getData("country.name"));
    }

    @Test
    public void Test02_Login_To_Application_Using_Wrong_UserName() {
        test.homepage.navigate_to_account_page();
        test.accountpage.open_login_form();
        test.accountpage.login_to_the_application_as("Wrong@UserName.com", "Password1");
        test.accountpage.verify_login_error_message_is_displayed("No matching record was found. Check your spelling and try again.");
        test.accountpage.testPageLayout(browserSizes, layoutTags);
    }

    @Test
    public void Test03_Login_To_Application_Using_Blank_UserName() {
        test.accountpage.login_to_the_application_as("", "wrongpassword1");
        test.accountpage.verify_email_error_message_is_displayed("WRONG MESSAGE: ENTER YOUR EMAIL ADDRESS");
    }

    @Test
    public void Test04_Login_To_Application_Using_Blank_Password() {
        test.accountpage.login_to_the_application_as("wrongusername", "");
        test.accountpage.verify_user_is_on_login_page();
        test.accountpage.verify_password_error_message_is_displayed("ENTER YOUR PASSWORD");
    }

    @Test
    public void Test05_Login_To_Application_Using_Correct_Credentials() {
        test.accountpage.login_to_the_application_as("q234@qainfotech.com", "12345678e");
        test.myAccountpage.verify_user_is_on_my_account_page("q234@qainfotech.com");
    }
    

    @AfterMethod
    public void take_screenshot_on_failure(ITestResult result) {
      test.takescreenshot.takeScreenShotOnException(result);
    }


    @AfterClass
    public void stop_test_session() {
        test.closeTestSession();
    }

}
