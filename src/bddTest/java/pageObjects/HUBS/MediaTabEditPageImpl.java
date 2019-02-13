package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MediaTabEditPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public MediaTabEditPageImpl() {
        logger = Logger.getLogger(MediaTabEditPageImpl.class);
    }


    public void clickOnHEMTab(String tabName) {
        waitUntilPageFinishLoading();
        switch (tabName) {
            case "MEDIA" : mediaTab().click();
                break;
            case "INTRO" : introTab().click();
                break;
            case "LINKS & PROFILES" : linksAndProfilesTab().click();
                break;
        }
    }

    public void clickOnLink(String link) {
        waitUntilPageFinishLoading();
        switch (link) {
            case "Links" : clickOnLinks().click();
                break;
        }
    }

    public void verifyContents(String contents, String link) {
        waitUntilPageFinishLoading();
        switch (link) {
            case "Links" : verifyLinksContent(contents);
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

    public void verifyContentsMediaTab(String title, String addUpText) {
        waitUntilPageFinishLoading();
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + title + "')]")).getText()).as("Elements verifications").isEqualTo(title);
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + addUpText + "')]")).getText()).as("Elements verifications").isEqualTo(addUpText);
    }

    public void verifyContentsIntroTab(String content) {
        waitUntilPageFinishLoading();
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + content + "')]")).getText()).as("Elements verifications").isEqualTo(content);
    }

    public void verifyPremiumFeatureLock(String premiumText) {
        waitUntilPageFinishLoading();
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + premiumText + "')]")).getText()).as("Elements verifications").isEqualTo(premiumText);
    }

    public void verifyArrowsAndSlotsInPhotosAndVideos() {
        waitUntilPageFinishLoading();
        softly().assertThat(rightArrow().isDisplayed());
        rightArrow().click();
        softly().assertThat(leftArrow().isDisplayed());
        softly().assertThat(photosAndVideoPanel().size() == 16);
    }

    public void clickOnEditButton() {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Hubs View']")));
        editButton().click();
    }

    public void verifyFunctionalityForPublishingModalConfirmation() {
        cancelAndContinueEditingButton().click();
        softly().assertThat(publishMyMediaChangesButton().getText()).as("Publish my media changes button verifications").contains("Publish my media changes");
        publishMyMediaChangesButton().click();
        submitChangesButton().click();
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), 'updated your college profile')]")).getText()).as("New modal verifications").contains("updated your college profile");
        softly().assertThat(continueEditingButton().getText()).as("Continue Editing verifications").contains("Publish my media changes");
        continueEditingButton().click();
        softly().assertThat(publishMyMediaChangesButton().getText()).as("Publish mu media changes button verifications").contains("Publish my media changes");
    }

    public void verifyUpgradeModal() {
        premiumFeaturesButton().click();
        softly().assertThat(getDriver().findElement(By.xpath("//*[@id='upgrade-form']/div[1][contains(text(), 'Interested in learning more about Intersect?')]")).getText()).as("New modal verifications").contains("Interested in learning more about Intersect?");
}



    //Locators
    private WebElement mediaTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'MEDIA')]"));
    }

    private WebElement introTab() {
        return getDriver().findElement(By.xpath("//a[contains(text(), 'INTRO')]"));
    }

    private WebElement linksAndProfilesTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'LINKS & PROFILES')]"));
    }

    private WebElement clickOnLinks() {
        return getDriver().findElement(By.cssSelector("li[id='links-accordion-title"));
    }

    private void verifyLinksContent(String contents) {
        softly().assertThat(getDriver().findElement(By.xpath("//h3[contains(text(), '" + contents +"')]")).getText()).as("Verify Contents").contains(contents);
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

    private WebElement rightArrow() {
        return getDriver().findElement(By.cssSelector("i[class='arrow right icon"));
    }

    private WebElement leftArrow() {
        return getDriver().findElement(By.cssSelector("i[class='arrow left icon"));
    }

    private List<WebElement> photosAndVideoPanel() {
        return getDriver().findElements(By.xpath("//*[@class='_4MiKKhtvn8oXpH1abXjg0']/div"));
    }


    private WebElement premiumFeaturesButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Premium Features')]"));
    }
}
