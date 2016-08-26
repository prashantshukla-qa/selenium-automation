package com.qait.automation;


import com.qait.demo.keywords.HomePageActions;
import com.qait.demo.keywords.LoginPageActions;
import com.qait.demo.keywords.PublishersPageActions;
import com.qait.demo.keywords.ResultsPageActions;

import org.openqa.selenium.WebDriver;

public class PageInit{
  
  public HomePageActions homePage;
  public ResultsPageActions resultPage;
  public LoginPageActions loginPage;
  public PublishersPageActions publishersPage;
  
  protected void _initPage(WebDriver driver) {
          loginPage = new LoginPageActions(driver);
          homePage = new HomePageActions(driver);
          resultPage = new ResultsPageActions(driver);
          publishersPage = new PublishersPageActions(driver);
  }
  
}
