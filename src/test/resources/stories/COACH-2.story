Feature: Login

Meta:
@storyName COACH-2
@component null
@summary user

Scenario: Login attempt with Wrong Username
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with invalid username and valid password
Then I see the error message for wrong username as No matching record was found. Check your spelling and try again.

Scenario: Login attempt with Blank Username
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with blank username and valid password
Then I see the error message for blank username as 'ENTER YOUR EMAIL ADDRESS'

Scenario: Login attempt with Blank Password
Given I am on country home page
When I navigate to account page
And I open login form 
When I login to the application with valid username and blank password
Then I see the error message for blank password 'ENTER YOUR PASSWORD'
