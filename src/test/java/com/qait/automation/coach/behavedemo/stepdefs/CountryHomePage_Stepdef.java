package com.qait.automation.coach.behavedemo.stepdefs;


import static com.qait.automation.utils.YamlReader.getData;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class CountryHomePage_Stepdef extends BaseStepTest {


	@Given("I am on country home page")
	public void givenIAmOnCountryHomePage() {
		test.launchApplication(getData("base_url"));
        test.homepage.verify_user_is_on_home_page();
        test.homepage.navigate_to_country_specific_site(getData("country.area"), getData("country.name"));
	}

	@When("I navigate to account page")
	public void whenINavigateToAccountPage() {
		test.homepage.navigate_to_account_page();
	}


	
}