package com.qait.demo.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qait.automation.TestSessionInitiator;


/**
 * @author prashant.shukla
 **/

public class TestBase {

TestSessionInitiator test;

@BeforeClass
@Parameters("browser")
public void start_test_session(@Optional String browser) {
        test = new TestSessionInitiator(this.getClass().getSimpleName(), browser);
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