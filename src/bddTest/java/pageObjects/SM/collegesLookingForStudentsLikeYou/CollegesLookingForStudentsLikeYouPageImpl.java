package pageObjects.SM.collegesLookingForStudentsLikeYou;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class CollegesLookingForStudentsLikeYouPageImpl extends PageObjectFacadeImpl {

    public CollegesLookingForStudentsLikeYouPageImpl() {
        logger = Logger.getLogger(CollegesLookingForStudentsLikeYouPageImpl.class);
    }

    private Logger logger;

    public void clickHeartIcon(String collegeName) {
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(cardsGroupsLocator), 0));
        heartIcon(collegeName).click();
    }

    public void verifyVisualStepProgressIndicator(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        softly().assertThat(stepTitle().getText()).as("The title for the current step is incorrect.").isEqualTo(details.get(0).get(1));
        yesIDoButton().click();
        softly().assertThat(stepTitle2().getText()).as("The title for the current step is incorrect.").contains(details.get(1).get(1));
        submitButton().click();
        softly().assertThat(stepTitle3().getText()).as("The title for the current step is incorrect.").isEqualTo(details.get(2).get(1));
        closeButton().click();
    }

    //Locators
    private WebElement heartIcon(String collegeName) {
        return driver.findElement(By.xpath("//div[@id = 'activematch-app']/div[1]//a[text() = '" + collegeName + "']/../..//div[1]/i"));
    }
    private String modalCancelButton = "//span[text() = 'CANCEL']";
    private WebElement stepTitle() { return driver.findElement(By.cssSelector("main h2")); }
    private WebElement stepTitle2() { return driver.findElement(By.cssSelector("div.ui.header")); }
    private WebElement stepTitle3() { return driver.findElement(By.cssSelector("div.connect-message h1")); }
    private WebElement yesIDoButton() { return driver.findElement(By.cssSelector("button#yesIDoButton")); }
    private WebElement submitButton() { return driver.findElement(By.cssSelector("button#submitForm")); }
    private WebElement closeButton() { return driver.findElement(By.cssSelector("button#finishButton")); }
    private String cardsGroupsLocator = "div.ui.cards.matches-group";
}
