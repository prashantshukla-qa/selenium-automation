package com.qait.demo.keywords;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class PublishersPageActions extends GetPage{

	WebDriver driver;

	public PublishersPageActions(WebDriver driver) {
		super(driver, "PublishersPage");
		this.driver = driver;
	}

	public void verifyUserIsOnPublishersPage(String title) {
		Assert.assertEquals(element("breadcrumb_active").getText(), title);
	}

	public String selectAnyRandomLetter() {
		Random rand = new Random(); 
		int value = rand.nextInt(elements("list_letters").size()); 
		String selectedLetter = elements("list_letters").get(value).getText();
		elements("list_letters").get(value).click();
		logMessage("[INFO] User clicked on "+selectedLetter+" letter");
		return selectedLetter;
	}

	public void verifyResults(String letter) {
		for (WebElement element : elements("list_results")){
			Assert.assertTrue(element.getText().toLowerCase().startsWith(letter.toLowerCase()), "Assert Fail : All results start with "+letter+" but result "+element.getText()+" is not starting with "+letter + "\n");
		}
		logMessage("Assert Pass : All results start with "+letter);
	}

	public void verifyNumberOfItems(String numberOfResultsPerPage) {
		Select itemsPerPage =  new Select(element("select_itemsPerPage"));
		Assert.assertEquals(itemsPerPage.getAllSelectedOptions().get(0).getText(), numberOfResultsPerPage, "Assert Fail : Value of Items Per Page is "+numberOfResultsPerPage);
		logMessage("Assert Pass : Value of Items Per Page is "+numberOfResultsPerPage);
		Assert.assertTrue(elements("list_results").size()<=Integer.parseInt(numberOfResultsPerPage));
		if(elements("list_results").size()<Integer.parseInt(numberOfResultsPerPage)){
			Assert.assertTrue(elements("link_page2").size()==0, "Assert Fail : Page 2 is not displayed when result list is less than "+numberOfResultsPerPage);
			logMessage("Assert Pass : Page 2 is not displayed when result list is less than "+numberOfResultsPerPage);
		}
	}

	public void changeItemsPerPage(String changedValue) {
		selectProvidedTextFromDropDown(element("select_itemsPerPage"), changedValue);
		logMessage("[INFO] User selects "+changedValue+" from items per page dropdown");
	}

	
}
