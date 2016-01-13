package com.qait.automation.coach.behavedemo.stepdefs;


import static com.qait.automation.utils.YamlReader.getData;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class CountryHomePage_Stepdef extends BaseStepTest {


	@Given("I am on country home page")
	public void givenIAmOnCountryHomePage() {
		test.homepage.verifySignInLink();
  	}

	@When("I navigate to account page")
	public void whenINavigateToAccountPage() {
		test.homepage.navigate_to_account_page();
	}


	
}