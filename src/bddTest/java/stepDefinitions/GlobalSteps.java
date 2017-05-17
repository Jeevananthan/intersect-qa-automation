package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import utilities.Hooks;

public class GlobalSteps {

    @After
    public void afterScenario(Scenario scenario) {
        new Hooks(scenario).testTearDown();
    }

}
