Feature: Account Creation


Scenario: I want to create a new account
Given I am on country home page
When I navigate to account page
And I open registration form 
And I fill all mandatory fields in registration form
And I click sign up button
Then I should see the success message for account creation
And I see the address fields pop up
And I click skip button