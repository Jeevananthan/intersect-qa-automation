package pageObjects.SM.collegesImThinkingAbout;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class CollegesImThinkingAboutPageImpl extends PageObjectFacadeImpl {

    public CollegesImThinkingAboutPageImpl() {
        logger = Logger.getLogger(CollegesImThinkingAboutPageImpl.class);
    }

    private Logger logger;
    FCSuperMatchPageImpl fcSuperMatchPage = new FCSuperMatchPageImpl();

    public void removeCollegeFromImThinkingAboutList(String collegeName) {
        waitUntilPageFinishLoading();
        link("/colleges").click();
        button("Research Colleges").click();
        link("I'm Thinking About").click();
        waitForUITransition();
        if (driver.findElements(By.xpath(collegeCheckBoxInListLocator(collegeName))).size() > 0) {
            try {
                collegeCheckBoxInList(collegeName).click();
                removeButton().click();
                removeButtonInModal().click();
                waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(confirmationBanner), 0));
            } catch (WebDriverException e) {
                if (driver.findElements(By.cssSelector(fcSuperMatchPage.connectorCloseIconLocator)).size() > 0) {
                    driver.findElement(By.cssSelector(fcSuperMatchPage.connectorCloseIconLocator)).click();
                }
                collegeCheckBoxInList(collegeName).click();
                removeButton().click();
                removeButtonInModal().click();
                waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(confirmationBanner), 0));
            }
        }
        if (driver.findElements(By.cssSelector(fcSuperMatchPage.connectorCloseIconLocator)).size() > 0) {
            driver.findElement(By.cssSelector(fcSuperMatchPage.connectorCloseIconLocator)).click();
        }
    }

    public void addCollegeToCIATListFromCITA(String college) {
        waitUntilPageFinishLoading();
        link("/colleges").click();
        button("Research Colleges").click();
        link("I'm Thinking About").click();
        waitForUITransition();
        try {
            collegeCheckBoxInList(college).click();
            moveToApplicationListLink().click();
            waitUntilPageFinishLoading();
            addToApplicationsButton().click();
            waitUntilPageFinishLoading();
        } catch (WebDriverException e) {
            if (driver.findElements(By.cssSelector(fcSuperMatchPage.connectorCloseIconLocator)).size() > 0) {
                driver.findElement(By.cssSelector(fcSuperMatchPage.connectorCloseIconLocator)).click();
            }
            collegeCheckBoxInList(college).click();
            moveToApplicationListLink().click();
            waitUntilPageFinishLoading();
            addToApplicationsButton().click();
            waitUntilPageFinishLoading();
        }

    }

    //Locators
    private WebElement collegeCheckBoxInList(String collegeName) { return driver.findElement(By.xpath(collegeCheckBoxInListLocator(collegeName))); }
    private String collegeCheckBoxInListLocator(String collegeName) { return "//td[@title = 'College']//a[text() = '" + collegeName + "']/../../..//label"; }
    private WebElement removeButton() { return driver.findElement(By.xpath("//button[contains(text(), 'REMOVE')]")); }
    private WebElement removeButtonInModal() { return driver.findElement(By.cssSelector("div.ReactModal__Content button:nth-of-type(1)")); }
    private String confirmationBanner = "//strong[text() = 'Confirmation']";
    private WebElement moveToApplicationListLink() { return driver.findElement(By.xpath("//button[contains(text(), 'MOVE TO APPLICATION LIST')]")); }
    private WebElement addToApplicationsButton() { return driver.findElement(By.xpath("//button[text() = 'Add Applications']")); }
}
