Feature: Stack Overflow Tags
This Feature test the stack overflow's Tags feature

  @WebTest
  Scenario Outline: Able to verify tag having max questions on the page
    Given I am a new user and access the Stackoverflow site
     When I click on "Browse Questions" section on Homepage
     And I click on "Tags" section on Questions page
      And I select "Name" filter
      And I am on page number "1" of result
      And I get the tag having maximum questions
     Then I should able to see the Tag <tag> with maximum questions
    Examples: 
      | tag|
      | htaccess|
      | a|
      | xyz|