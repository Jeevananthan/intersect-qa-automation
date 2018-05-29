package pageObjects.SM.superMatchPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class NewSuperMatchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public NewSuperMatchPageImpl() {
        logger = Logger.getLogger(NewSuperMatchPageImpl.class);
    }

    // Locators Below

    public WebElement collegeSearchHeader() { return driver.findElement(By.cssSelector("h1.ui.left.floated.header.title")); }
}
