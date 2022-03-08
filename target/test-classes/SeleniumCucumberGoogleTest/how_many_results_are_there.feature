Feature: Are there more than 8 search results for Selenium?
  Everybody wants to know if there are more than 8 results.
  
 Scenario: Typing Selenium in Google gives more than 8 results
   Given I search for Selenium in Google
   When I ask whether there are more than 8 results
   Then I should be told "yes"