package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import static com.qait.automation.utils.YamlReader.getMapValue;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import java.util.Map;

/**
 *
 * @author prashant.shukla
 */


public class Account_Creation_Test extends TestBase {

@Test
public void Test01_Launch_Application() {
        test.launchApplication(getData("base_url"));
}

}
