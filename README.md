# CloudCover
This repository is created for cloud cover project. The objective of this project was to create a runnable automation for Web and API test automation.

## Usage

The Web and API test can be executed in 3 ways - 
1. Execute by running the individual runner file - 
	a. For UI Tests  : `TestRunnerUI.java`
	b. For API Tests : `TestRunnerAPI.java`
2. Execute by running the runner file `ParallelTestRunnerIT.java` which would run both Web and API tests
3. Execute by running a Maven command `mvn clean test` which would run both Web and API tests


## Prerequsites
1. Below softwares/tools need to be installed:
	* Java 
	* Eclipse
	* Apache Maven
2. API key/token from StackExchange for running API. More details on the authentication can be found [here](https://api.stackexchange.com/docs/authentication)
3. Update the value of the property name `api_token` with your API key in the `config.properties` present undet `src/test/resources` folder
4. Import -> Maven -> Existing Maven Projects

## Preparing for Execution

To perform execution follow below steps:

1. Clone repository: `git clone https://github.com/rohittupe/CloudCover.git`
2. `cd` into the repository
3. Run test `mvn clean test`
4. Upon completion of tests, a detailed report will be generated at `/target/cucumber/index.html` location
5. Open `index.html` file with a browser of your choice to view report

## Things to know

1. This project has been created using Open source tools and technologies
2. The programming language is Java and Java 1.8 is used as the Java version
3. Maven is used as a build platform
4. Cucumber is used as the testing framework for the project
5. Selenium library is used to carry out Web automation
6. Rest-assured library is used to carry out API automation
7. No need to download or maintain drivers for browser, WebDriverManager has been implemented to take care of the same
8. API key is not mandatory but good to have as StackExchange have limit the API usage when user is not authorized
 