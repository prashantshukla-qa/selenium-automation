package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qait.automation.TestSessionInitiator;

public class TestBase {

String baseUrl = getYamlValue("baseUrl");

TestSessionInitiator test;

@BeforeClass
@Parameters("browser")
public void Start_Test_Session(@Optional String browser) {
        test = new TestSessionInitiator(this.getClass().getSimpleName(), browser);
}

@AfterMethod
public void take_screenshot_on_failure(ITestResult result) {
        test.takescreenshot.takeScreenShotOnException(result);
}

@AfterClass
public void close_Test_Session() {
      //  test.closeBrowserSession();
}

}
