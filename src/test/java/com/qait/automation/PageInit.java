package com.qait.automation;


import com.qait.demo.keywords.HomePageActions;
import com.qait.demo.keywords.LoginPageActions;
import com.qait.demo.keywords.PublishersPageActions;
import com.qait.demo.keywords.ResultsPageActions;

import org.openqa.selenium.WebDriver;

public class PageInit{
  
  WebDriver driver;
  
  public HomePageActions homePage;
  public ResultsPageActions resultPage;
  public LoginPageActions loginPage;
  public PublishersPageActions publishersPage;
  
  protected void _initPage() {
          loginPage = new LoginPageActions(this.driver);
          homePage = new HomePageActions(this.driver);
          resultPage = new ResultsPageActions(this.driver);
          publishersPage = new PublishersPageActions(this.driver);
  }
  
}
