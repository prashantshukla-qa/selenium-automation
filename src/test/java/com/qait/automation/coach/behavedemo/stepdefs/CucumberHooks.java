package com.qait.automation.coach.behavedemo.stepdefs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriverException;

import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;

//import org.testng.annotations.AfterSuite;
import cucumber.api.Scenario;
//import cucumber.api.StepDefinitionReporter;
//import cucumber.runtime.StepDefinition;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CucumberHooks {
	//static TestSessionInitiator test;
	static String appUrl = null;
	
	public static StringBuffer failedScenario = new StringBuffer(15);
	public static StringBuffer passedScenario= new StringBuffer(15);
	public static String testResults="comment";
	public static String storyID="DEMO-3";
	
	public static Map<String,String> SceanrioResults=new HashMap<String,String>();

	@Before
	public void printClassName(){
		String className = this.getClass().getCanonicalName();
		System.out.println("Running Test:" + className);
			}
	@Before
	public void printScenarioName(Scenario scenario){
		System.out.println("********************************");
		System.out.println("Running scenario:" + scenario.getName());
		System.out.println("********************************");
	}
	
//	@After  
//    public void embedScreenshot(Scenario scenario) {  
//		System.out.println("scenario==="+ scenario.getName());
//        if (scenario.isFailed()) {  
//        	System.out.println("embedScreenshot");
//        	byte[] screenshot = ((TakesScreenshot)
//        			driver).getScreenshotAs(OutputType.BYTES);
//        	File screenshot1 = ((TakesScreenshot)
//        			driver).getScreenshotAs(OutputType.FILE);
//        	scenario.embed(screenshot, "image/jpeg");
//        	       	
//    		Calendar calendar = Calendar.getInstance();
//    		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
//    			try {
//    				FileUtils.copyFile(screenshot1, new File("screenshots/"+scenario.getName()+"_"+formater.format(calendar.getTime())+".jpg"));
//    			} catch (IOException e1) {
//    				e1.printStackTrace();
//    			}
//    		}
//	}  
	@After  
    public void embedJIRAComment(Scenario scenario) throws IOException, ParseException {  
		
        if (scenario.isFailed()) {  
            try {
            	
            	PropFileHandler.writeToFile(scenario.getName(), scenario.getStatus(),
     			YamlReader.getYamlValue("propertyfilepath"));
              	SceanrioResults.put(scenario.getName(), scenario.getStatus());
            	failedScenario=failedScenario.append("Scenario: "+scenario.getName()+" : - {color: red}* Failed *{color}"+"\n");
            } catch (WebDriverException wde) {  
                System.err.println(wde.getMessage());  
            } catch (ClassCastException cce) {  
                cce.printStackTrace();  
            }  
        } else{
        	
             SceanrioResults.put(scenario.getName(), scenario.getStatus());
             PropFileHandler.writeToFile(scenario.getName(), scenario.getStatus(),
 					YamlReader.getYamlValue("propertyfilepath"));
             
        	passedScenario=passedScenario.append("Scenario: "+scenario.getName()+" : - {color: green}* Passed *{color}"+"\n");
         }
        testResults=passedScenario.toString()+failedScenario.toString();

    }
	
//	@Test
//	public void publishResults() throws IOException, ParseException{
//		TestResultsJsonParser tj=new TestResultsJsonParser();
//		tj.formatTestResults();
//		System.out.println("publishResults");
//		//JIRAScenarioResultPublisher.addScenarioStatusInJIRAComment(storyID, testResults);
//		PublishJiraReport pb=new PublishJiraReport();
//		pb.pushJiraComment(getSceanrioResults());
//		//Map<String, String> a=new HashMap<String,String>();
//		//a.put("DEMO-4", "fail");
//		//pb.moveJiraTicket("DEMO-4",a);
//	}
	
	public Map<String,String> getSceanrioResults(){
		for (Map.Entry<String,String> entry : SceanrioResults.entrySet()) {
			  String key = entry.getKey();
			  System.out.println("sceanrio name==="+ key);
			  System.out.println("sceanrio value==="+ entry.getValue());
			  

			}
		return SceanrioResults;
		
	}
	
}