package pageObjects.SM.footerPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class FooterPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    static int pinnedCollegesCount=0;

    public FooterPageImpl() {
        logger = Logger.getLogger(FooterPageImpl.class);
    }

    public void verifyPinnedCollegeCountInFooter(){
        pinnedCollegesCount = Integer.parseInt(getPinnedCollegesCountFooter().getText());
        if (pinnedCollegesCount==0){
            logger.info("There is no college pinned and count is showing correctly ie 0");
        }else {
            getPinnedCollegesCountFooter().click();
            getClearPinnedListButton().click();
            getYesClearMyListButton().click();
            WebElement element = getPinnedCollegesCountFooter();
            waitForElementTextToEqual(element, "0");
            pinnedCollegesCount = Integer.parseInt(getPinnedCollegesCountFooter().getText());
            Assert.assertTrue("After removing all pinned college, count is not showing 0", pinnedCollegesCount==0);
        }
    }


    //Locators
    private WebElement getPinnedCollegesCountFooter(){return driver.findElement(By.id("pinCount"));}
    private WebElement getClearPinnedListButton(){return driver.findElement(By.xpath("//span[contains(text(),' Clear Pinned List')]"));}
    private WebElement getYesClearMyListButton(){return driver.findElement(By.xpath("//div[@class='actions']/button[contains(text(),'YES, CLEAR MY LIST')]"));}
    private WebElement getPinnedCollegeCount(){return driver.findElement(By.xpath("//div[@id='pinCount']"));}

}
