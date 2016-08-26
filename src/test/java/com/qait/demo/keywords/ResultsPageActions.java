package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ResultsPageActions extends GetPage {
	
	WebDriver driver;

    public ResultsPageActions(WebDriver driver) {
        super(driver, "ResultsPage");
        this.driver = driver;
    }

	
	public void verifySearchTermAppearsInBreadCrumb(String searchTerm) {
		isElementDisplayed("breadcrumb_search");
		Assert.assertTrue(element("breadcrumb_search").getText().trim().contains(searchTerm), "Assert Fail : Breadcrumb section contains search term");
		logMessage("Assert Pass : Breadcrumb section contains search term");
	}

	public void verifyResults(String resultText) {
		for(WebElement result : elements("list_results")){
			Assert.assertTrue(result.getText().trim().contains(resultText),"Assert Fail : All search results contain search term");
		}
		logMessage("Assert Pass : All search results contain search term");
	}


	public String clickOnTextFormatFilter() {
		String numberOfResultsForFilter = element("txt_numberOfResults").getText();
		element("link_textFilter").click();
		logMessage("[INFO] Select Text Format Filter");
		return numberOfResultsForFilter.substring(1, numberOfResultsForFilter.length()-1);
	}


	public void verifyResultsAfterFilter(String expectedNumberOfResults) {
		Assert.assertTrue(element("txt_totalResults").getText().replace(",", "").contains(expectedNumberOfResults), "Assert Fail : Number of results is correct after applying filter");
		logMessage("Assert Pass : Number of results is correct after applying filter");
	}    
}
