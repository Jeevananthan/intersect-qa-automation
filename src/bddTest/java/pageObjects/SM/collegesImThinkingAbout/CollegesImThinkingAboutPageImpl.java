package pageObjects.SM.collegesImThinkingAbout;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class CollegesImThinkingAboutPageImpl extends PageObjectFacadeImpl {

    public CollegesImThinkingAboutPageImpl() {
        logger = Logger.getLogger(CollegesImThinkingAboutPageImpl.class);
    }

    private Logger logger;

    public void removeCollegeFromImThinkingAboutList(String collegeName) {
        waitUntilPageFinishLoading();
        link("/colleges").click();
        button("Research Colleges").click();
        link("I'm Thinking About").click();
        if (driver.findElements(By.xpath(collegeCheckBoxInListLocator(collegeName))).size() > 0) {
            collegeCheckBoxInList(collegeName).click();
            removeButton().click();
            removeButtonInModal().click();
            waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(confirmationBanner), 0));
        }
    }

    //Locators
    private WebElement collegeCheckBoxInList(String collegeName) { return driver.findElement(By.xpath(collegeCheckBoxInListLocator(collegeName))); }
    private String collegeCheckBoxInListLocator(String collegeName) { return "//td[@title = 'College']//a[text() = '" + collegeName + "']/../../..//label"; }
    private WebElement removeButton() { return driver.findElement(By.xpath("//button[contains(text(), 'REMOVE')]")); }
    private WebElement removeButtonInModal() { return driver.findElement(By.cssSelector("div.ReactModal__Content button:nth-of-type(1)")); }
    private String confirmationBanner = "//strong[text() = 'Confirmation']";
}
