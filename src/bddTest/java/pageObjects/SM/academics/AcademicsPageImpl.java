package pageObjects.SM.academics;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class AcademicsPageImpl extends PageObjectFacadeImpl {

    public AcademicsPageImpl() {
        logger = Logger.getLogger(AcademicsPageImpl.class);
    }

    private Logger logger;

    public void searchKeyWordInMajors(String keyWord) {
        waitUntilPageFinishLoading();
        majorsSearchField().clear();
        majorsSearchField().sendKeys(keyWord);

    }

    //Locators
    private WebElement majorsSearchField() { return driver.findElement(By.xpath("//div[@class = 'row'][1]//input[@class = 'search']"));}

}
