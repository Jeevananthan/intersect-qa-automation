package pageObjects.SP.commonPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.SeleniumBase;

public class NavBarImpl extends SeleniumBase {

    private Logger logger;

    public NavBarImpl() {
        logger = Logger.getLogger(NavBarImpl.class);
    }

    public void goToHome() {
        if (!isLinkActive(getHomeBtn())) {
            getHomeBtn().click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Unable to navigate Home", isLinkActive(getHomeBtn()));
        }
    }

    private boolean isLinkActive(WebElement link) {
        //_28hxQ33nAx_7ae3SZ4XGnj is the class that is added to indicate css active
        return link.getAttribute("class").contains("_28hxQ33nAx_7ae3SZ4XGnj");
    }

    private WebElement getHomeBtn() {
        return link(By.id("js-main-nav-home-menu-link"));
    }
}
