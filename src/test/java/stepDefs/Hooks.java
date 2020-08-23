package stepDefs;

import com.util.actions.init.Initializer;

import cucumber.api.java.After;

public class Hooks {
	
	@After("@WebTest")
	public void tearDown() {
		Initializer.driver.close();
	}

}
