package utilities;

import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import selenium.SeleniumBase;

public class Hooks extends SeleniumBase {
    Logger logger = Logger.getLogger(Hooks.class);
    Scenario scenario;

    public Hooks(Scenario scenario) {
        this.scenario = scenario;
    }

    public void testTearDown() {
        takeScreenshotOnFailure();
    }

    private void takeScreenshotOnFailure() {
        if(scenario.isFailed()) {
            try {
                //Take screenshot
                final byte[] screenshot = getDriver().getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (final UnsupportedOperationException e) {
                logger.error(e.getMessage());
            } catch (final WebDriverException e) {
                scenario.write("WARNING: Failed to take screenshot with exception: " + e.getMessage());
            }
        }
    }
}
