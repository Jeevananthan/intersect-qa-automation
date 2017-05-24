package utilities.HUBSEditMode;

import org.apache.log4j.Logger;
import selenium.SeleniumBase;

public class Navigation extends SeleniumBase {

    private Logger logger;

    public Navigation() {
        logger = Logger.getLogger(Navigation.class);
    }

    public String getWindowHandle() {
        return getDriver().getWindowHandle();
    }

    public void closeCurrentTabAndSwitchToAnother(String originalHandle) {
        for(String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                getDriver().switchTo().window(handle);
                getDriver().close();
            }
        }
        getDriver().switchTo().window(originalHandle);
    }
}
