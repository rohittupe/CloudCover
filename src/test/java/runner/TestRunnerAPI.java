package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/resources/features/api",
		glue="stepDefs",
		//dryRun=true,
		tags= "@APITest",
		plugin={"pretty", "html:target/cucumber", "json:target/cucumber.json", "junit:target/cukes.xml", "json:target/jsonReports/cucumber-report.json"})
public class TestRunnerAPI {

}
