Feature: Login

Meta:
@storyName DEMO-4
@component Label_Test
@summary null

Scenario: Login attempt with Wrong Username
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with invalid username and valid password
Then I see the error message for wrong username as No matching record was found. Check your spelling and try again.
And I check the layout of this page with browser size 720x360 and layout tag as mobile

Scenario: Login attempt with Blank Username
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with blank username and valid password
Then I see the error message for blank username as ENTER YOUR EMAIL ADDRESS

Scenario: Login attempt with Blank Password
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with valid username and blank password
Then I see the error message for blank password as ENTER YOUR PASSWORD


Scenario: Login attempt with correct credentials
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with valid username and valid password
Then I see the my account page

