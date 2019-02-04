package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.assertj.core.api.StringAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MediaTabEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public MediaTabEditPageImpl() {
        logger = Logger.getLogger(MediaTabEditPageImpl.class);
    }


    public void clickOnMediaTab(String tabName) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + tabName + "')]")));
        switch (tabName) {
            case "MEDIA" : mediaTab().click();
                break;
        }
    }

    public void clickOnPublishMyMediaChangesButton(String buttonName) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + buttonName + "')]")));
        /*Verifying the Pink button*/
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + buttonName + "')]")).getText()).as("Publish Media button").isEqualTo(buttonName);

        switch (buttonName) {
            case "Publish my media changes" : publishMyMediaChangesButton().click();
                break;
        }
    }

    public void verifyPublishingModalConfirmation(String elementVerification) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable( getDriver().findElement(By.cssSelector("div[class='ui small modal transition visible active']"))));
        switch (elementVerification) {

            case "Publish my media changes" : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "You’re almost done!" : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "The following items you've updated need approval by Hobsons." : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "The approval process can take up to 24-48 hours." : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "Please provide a brief explanation for the changes:" : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "PCancel and continue editing" : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "Submit changes" : softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + elementVerification + "')]")).getText()).as("Elements verifications").isEqualTo(elementVerification);
                break;
            case "IE: We've moved the admissions office!" : softly().assertThat(getDriver().findElement(By.xpath("//textarea[@class='_3yrIVb-g3eMGZEbjq-jXHg']")).getAttribute("placeholder").contains(elementVerification));
                break;
        }
    }

    public void clickOnEditButton() {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Hubs View']")));
        editButton().click();
    }

    public void verifyFunctionalityForPublishingModalConfirmation() {
        cancelAndContinueEditingButton().click();
        softly().assertThat(publishMyMediaChangesButton().getText()).as("Publish mu media changes button verifications").contains("Publish my media changes");
        publishMyMediaChangesButton().click();
        submitChangesButton().click();
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), 'updated your college profile')]")).getText()).as("New modal verifications").contains("updated your college profile");
        softly().assertThat(continueEditingButton().getText()).as("Continue Editing verifications").contains("Publish my media changes");
        continueEditingButton().click();
        softly().assertThat(publishMyMediaChangesButton().getText()).as("Publish mu media changes button verifications").contains("Publish my media changes");
    }



    //Locators
    private WebElement mediaTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'MEDIA')]"));
    }

    private WebElement cancelAndContinueEditingButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Cancel and continue editing')]"));
    }

    private WebElement submitChangesButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Submit changes')]"));
    }

    private WebElement publishMyMediaChangesButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Publish my media changes')]"));
    }

    private WebElement continueEditingButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Continue editing')]"));
    }

    private WebElement editButton() {
        return getDriver().findElement(By.cssSelector("a[ng-click='vm.enableEditMode()"));
    }
}
