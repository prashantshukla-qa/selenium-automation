package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class AccountPage extends GetPage {

    public AccountPage(WebDriver driver) {
        super(driver, "AccountPage");
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
        verifyElementText(element("txt_ErrorMessage").getText(), errMsg);
    }

    public void verify_User_Is_On_Login_Page() {
        verifyPageTitleExact();
    }

    public void verify_Email_Error_Message_Is_Displayed(String errMsg) {
        verifyElementText(element("txt_emailErrorMsg").getText(), errMsg);
    }

    public void verify_Password_Error_Message_Is_Displayed(String errMsg) {
        verifyElementText(element("txt_passwordErrorMsg").getText(), errMsg);
    }
}
