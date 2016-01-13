package com.qait.automation.coach.behavedemo.stepdefs;


import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getMapValue;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AccountPage_Stepdef extends BaseStepTest {
	
    
    @When("I open login form")
	public void whenIOpenLoginForm() {
    	test.accountpage.open_login_form();
	}
    
    
    @When("I login to the application with invalid username and valid password")
    public void whenILoginWithInvalidUsernameAndValidPassword(){
    	test.accountpage.login_to_the_application_as("Wrong@UserName.com", "Password1");
    }
    
    @Then("I see the error message for wrong username as $errorMessage")
    public void thenISeeErrorMessageForWrongUsername(@Named("errorMessage") String errorMessage){
    	test.accountpage.verify_login_error_message_is_displayed(errorMessage);
    }
    
    @Then("I check the layout of this page with browser size $resolution and layout tag as $tab")
    public void thenICheckLayoutofAPage(@Named("resolution") String resolution, 
    		@Named("tab") String tab){
    	String[] browser = {resolution};
    	String[] layoutTags = {tab};
    	test.accountpage.testPageLayout(browser, layoutTags);
    }
    
    @When("I login to the application with blank username and valid password")
    public void whenILoginWithBlankUsernameAndValidPassword(){
    	 test.accountpage.login_to_the_application_as("", "wrongpassword1");
    }

    @Then("I see the error message for blank username as $errorMessage")
    public void thenISeeTheErrorMessageForBlankusername(@Named("errorMessage") String errorMessage){
    	test.accountpage.verify_email_error_message_is_displayed(errorMessage);
    }
    
    @When("I login to the application with valid username and blank password")
    public void whenILoginWithValidUsernameAndBlankPassword(){
    	test.accountpage.login_to_the_application_as("wrongusername", "");
        test.accountpage.verify_user_is_on_login_page();
    }
    
    @Then("I see the error message for blank password as $errorMessage")
    public void thenISeeErrorMessageForBlankPassword(@Named("errorMessage") String errorMessage){
    	test.accountpage.verify_password_error_message_is_displayed(errorMessage);
    }
    
    @When("I login to the application with valid username and valid password")
    public void whenILoginWithValidUsernameAndValidPassword(){
    	test.accountpage.login_to_the_application_as("q234@qainfotech.com", "12345678e");
    }
    
    @Then("I see the my account page")
    public void thenISeeMyAccountPage(){
    	test.myAccountpage.verify_user_is_on_my_account_page("q234@qainfotech.com");
    }
    
	@When("I open registration form")
	public void whenIOpenRegistrationForm() {
		test.accountpage.open_registration_form();
	}

	@When("I fill all mandatory fields in registration form")
	public void whenIFillAllMandatoryFieldsInRegistrationForm() {
		Map<String, Object> inputs = getYamlValues("registration.inputs");

		/**
		 * This fills in all the fields of the registration page
		 */
		for (int i = 1; i <= inputs.size(); i++) {

			String inputField = getMapValue(inputs, "field_" + i + ".label");
			String inputValue = getMapValue(inputs, "field_" + i + ".value");

			test.accountpage.fill_registration_rnput_rields(inputField, inputValue);
		}

	}

	@When("I click sign up button")
	public void whenIClickSignUpButton() {
		test.accountpage.clickSignUpButton();
	}

	@Then("I should see the success message for account creation")
	public void thenIShouldSeeTheSuccessMessageForAccountCreation() {
		test.accountpage.verifyNewAccountIsCreated(getData("registration.successMessage"));
	}

	@Then("I see the address fields pop up")
	public void thenISeeTheAddressFieldsPopUp() {
	  test.accountpage.verifyAddressPanelAppears();
	}

	@Then("I click skip button")
	public void thenIClickSkipButton() {
	 test.accountpage.clickSkipButton();
	}

	

}
