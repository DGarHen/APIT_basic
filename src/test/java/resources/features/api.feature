Feature: Api

  Scenario api example
    Given params "Diego" and Headers ""
    When consume "get" endpoint as a "GET"
    Then the service response with status 200
    And verify the response with the data
    |args.user|Diego|
    |args.text|test |