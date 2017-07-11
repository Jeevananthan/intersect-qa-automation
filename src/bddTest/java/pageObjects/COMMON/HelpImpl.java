package pageObjects.COMMON;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class HelpImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public HelpImpl() {
        logger = Logger.getLogger(HelpImpl.class);
    }

    public void verifyHelpExistAndSecure(String highSchool){
        System.out.println();
        logger.info("I verify that the help content is secure and matches the correct URL.");
        ArrayList<String> windows = null;
        String url;
        if(!highSchool.equalsIgnoreCase("Naviance HS Users")) {
            button(By.id("js-main-nav-help-menu-link")).click();
            windows = new ArrayList<>(driver.getWindowHandles());
        }
        switch (highSchool){
            case "Naviance HS Users":
                Assert.assertFalse("The Help link is available for Naviance user, but shouldn't be.", button(By.id("js-main-nav-help-menu-link")).isDisplayed());
                break;
            case "Non-Naviance HS Users":
                driver.switchTo().window(windows.get(1));
                url = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(windows.get(0));
                Assert.assertEquals("The Help link is not secure or is not the correct web address.","https://helpsite.hobsons.com/RepVisits/Content/Getting%20Started%20HS.htm", url);
                break;
            case "HE Users":
                driver.switchTo().window(windows.get(1));
                url = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(windows.get(0));
                Assert.assertEquals("The Help link is not secure or is not the correct web address.", "https://helpsite.hobsons.com/Intersect/Content/Getting%20Started.htm", url);
                break;
            case "SP Users":
                driver.switchTo().window(windows.get(1));
                url = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(windows.get(0));
                Assert.assertEquals("The Help link is not secure or is not the correct web address.", "https://helpsite.hobsons.com/Intersect/Content/Getting%20Started.htm", url);
                break;
        }
    }

}
