package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class AccountPage extends GetPage {

    long currentTimeStamp = 0;

    public AccountPage(WebDriver driver) {
        super(driver, "AccountPage");
        currentTimeStamp = System.currentTimeMillis();
    }

    public void verify_left_nav_bar_is_open() {
        isElementDisplayed("nav_shadowContainer");
        isElementDisplayed("txt_topic");
    }

    public void open_team_management_topic() {
        element("lnk_teamManagement").click();
    }

    public void open_login_form() {
        element("btn_login").click();
    }

    public void open_registration_form() {
        element("btn_register").click();
        isElementDisplayed("modal_registration");
        logMessage("Clicking on the Register button opens up the Sign Up modal");
    }

    public void verifyRegisrationButton() {
        isElementDisplayed("btn_register");
        logMessage("User has logged out successfully");
    }

    public void open_form(String formName) {
        element("btn_" + formName.toLowerCase().trim()).click();
    }

    public void login_to_the_application_as(String userName, String password) {
        element("inp_LoginEmail").clear();
        element("inp_LoginEmail").sendKeys(userName);
        element("inp_LoginPassword").clear();
        element("inp_LoginPassword").sendKeys(password);
        element("btn_SignIn").click();
    }

    public void verify_Login_Error_Message_Is_Displayed(String errMsg) {
        verifyElementText("txt_ErrorMessage", errMsg);
    }

    public void verify_User_Is_On_Login_Page() {
        verifyPageTitleExact();
    }

    public void verify_Email_Error_Message_Is_Displayed(String errMsg) {
        verifyElementText("txt_emailErrorMsg", errMsg);
    }

    public void verify_Password_Error_Message_Is_Displayed(String errMsg) {
        verifyElementText("txt_passwordErrorMsg", errMsg);
    }

    public void fill_Registration_Input_Fields(String labelForInput, String value) {
        element("inp_" + labelForInput).clear();
        if (labelForInput.equals("Email") || labelForInput.equals("ConfirmEmail")) {// Randomize user email 
            value = value.replace("@", "_" + currentTimeStamp + "@");
            element("inp_" + labelForInput).sendKeys(value);
        } else {
            element("inp_" + labelForInput).sendKeys(value);
        }

        logMessage("[INFO]: User entered '" + value + "' in the '" + labelForInput + "' field");
    }

    public void clickSignUpButton() {
        element("btn_SingUp").click();
        logMessage("User clicked on the Sign up button after accepting the terms while registring");
    }

    public void verifyNewAccountIsCreated(String message) {
        wait.waitForPageToLoadCompletely();
        isElementDisplayed("txt_accountCreated", message);
        isElementDisplayed("inp_address1");
        logMessage("[INFO]: Account has been successfully created and the panel for address information is displayed");

    }

    public void clickSkipButton() {
        element("btn_skip").click();
    }

}
