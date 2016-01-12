package com.qait.automation.coach.behavedemo.stepdefs;


import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

public class Account_Creation_StepDef extends BaseStepTest {


	@Given("I am attempting $scenario")
	public void attempt_exam() {
	}

	@Given("I am on the kepler page")
	public void i_am_on_kepler_page() {
	
	}

	@Then("I can see $result message on the question decription panel")
	public void verify_answer(@Named("result") String message) {
		
	}

}
