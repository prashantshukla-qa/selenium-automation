package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

/**
 *
 * @author prashant.shukla
 */
public class Account_Creation_Test {

    TestSessionInitiator test;

    String[] browserSizes = {"720x360"};
    String[] layoutTags = {"all"};

    @BeforeClass
    @Parameters("browser")
    public void start_test_session(@Optional String browser) {
        test = new TestSessionInitiator("Account_Creation_Test", browser);
    }

    @Test
    public void Test01_Launch_Application() {
        test.launchApplication(getData("base_url"));
        test.homepage.verify_user_is_on_home_page();
        test.homepage.navigateToSpecificCountrySite(getData("countryName"));
    }

    @Test
    public void Test02_Navigate_To_Registration_Page() {
        test.homepage.navigate_to_account_page();
        test.accountpage.open_registration_form();       
    }
    
    @Test
    public void Test03_Fill_Madatory_Information_On_Registration_Form_And_Verify_Account_Is_Created(){
    
    	test.accountpage.enterInformationInTextBoxForRegistration(getData("registration.inputs.field1.label"), 
        		getData("registration.inputs.field1.value"));
        test.accountpage.enterInformationInTextBoxForRegistration(getData("registration.inputs.field2.label"), 
        		getData("registration.inputs.field2.value"));
        test.accountpage.enterInformationInTextBoxForRegistration(getData("registration.inputs.field3.label"), 
        		getData("registration.inputs.field3.value"));
        test.accountpage.enterInformationInTextBoxForRegistration(getData("registration.inputs.field4.label"), 
        		getData("registration.inputs.field4.value"));
        test.accountpage.enterInformationInTextBoxForRegistration(getData("registration.inputs.field5.label"), 
        		getData("registration.inputs.field5.value"));
        test.accountpage.enterInformationInTextBoxForRegistration(getData("registration.inputs.field6.label"), 
        		getData("registration.inputs.field6.value"));
        test.accountpage.clickSignUpButton();
        
        test.accountpage.verifyNewAccountIsCreated(getData("registration.successMessage"));
        
        test.accountpage.clickSkipButton();
        
        test.homepage.logOut();
        
        test.accountpage.verifyRegisrationButton();
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
