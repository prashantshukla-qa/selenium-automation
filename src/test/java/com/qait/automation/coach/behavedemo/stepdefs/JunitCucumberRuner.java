package com.qait.automation.coach.behavedemo.stepdefs;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/stories/", plugin = { "pretty", "html:target/cucumber",
		"json:target/cucumber.json", "junit:target/test-report.xml" })
public class JunitCucumberRuner {

}
