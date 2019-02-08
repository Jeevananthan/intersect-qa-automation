package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class FCCollegesPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public FCCollegesPageImpl() {
        logger = Logger.getLogger(FCCollegesPageImpl.class);
    }

    public void enterSearchString(String searchString) {
        searchTextbox().sendKeys(searchString);
    }

    public void clickGoButton() {
        goButton().click();
    }

    public void clickSingleResult(String collegeName) {
        waitUntilElementExists(singleResult(collegeName));
        singleResult(collegeName).click();
        waitUntilPageFinishLoading();
    }

    public void searchAndOpenCollege(String searchString) {
        waitUntilPageFinishLoading();
        enterSearchString(searchString.toLowerCase());
        clickGoButton();
        clickSingleResult(searchString);
    }

    //Locators

    private WebElement searchTextbox() {
        return getDriver().findElement(By.xpath("//input[@class='text']"));
    }
    private WebElement goButton() {
        return button("Go");
    }
    private WebElement singleResult(String collegeName) {
        return getDriver().findElement(By.xpath("//a[text()='" + collegeName + "']"));
    }
    private WebElement collegeInList(String collegeName) { return driver.findElement(By.xpath("//a[text() = '" + collegeName + "']")); }
}
