package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/resources/features",
		glue="stepDefs",
		//dryRun=true,
		plugin={"pretty", "html:target/cucumber", "json:target/cucumber.json", "junit:target/cukes.xml", "json:target/jsonReports/cucumber-report.json"})
public class ParallelTestRunnerIT {

}
