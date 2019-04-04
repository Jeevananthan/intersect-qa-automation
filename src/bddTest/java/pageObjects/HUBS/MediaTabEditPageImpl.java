package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.apache.xpath.operations.Bool;
import org.assertj.core.api.BooleanAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
            case "CREATE A NEW PROFILE" : createNewProfile().click();
                break;
        }
    }

    public void clickOnLink(String link) {
        waitUntilPageFinishLoading();
        switch (link) {
            case "Links" : clickOnLinks().click();
                break;
            case "Profiles" : clickOnProfilesLink().click();
                break;
        }
    }

    public void clickOnButton(String button) {
        waitUntilPageFinishLoading();
        switch (button) {
            case "READ MORE" : clickOnButtons(button).click();
                break;
        }
    }

    public void clickOnTheButton(String button) {
        waitUntilPageFinishLoading();
        switch (button) {
            case "NEXT" : clickOnNextButton(button).click();
                break;
        }
    }

    public void validateURLs(String link) {
        switch (link) {
            case "Request Information" : validateRequestInformationFormatURLs();
                break;
        }
    }

    public void verifyContents(String contents, String link) {
        waitUntilPageFinishLoading();
        switch (link) {
            case "Links" : verifyLinksContent(contents);
                break;
            case "Title Links" : verifyTitleLinksContent(contents);
                break;
            case "COMMUNICATE" : verifyDropdownContent(contents);
                break;
            case "LEARN MORE" : verifyDropdownContent(contents);
                break;
        }
    }

    public void verifyRequestInformationURL(String contents, String link) {
        waitUntilPageFinishLoading();
        switch (contents) {
            case "Request Information" : verifyURLs(link);
                break;
        }
    }

    public void verifyStudentProfile(String contents) {
        waitUntilPageFinishLoading();
        softly().assertThat(getDriver().findElement(By.xpath("//*/*[contains(text(), '"+ contents +"')]")).getText()).as("Profile verification").isEqualTo(contents);
    }

    public void verifyModalCreateNewProfile(String itemValidation) {
        waitUntilPageFinishLoading();
        switch (itemValidation) {
            case "What type of profile would you like to create?" : softly().assertThat(getDriver().findElement(By.xpath("//*/*[contains(text(), '"+itemValidation+"')]")).getText()).as("New Modal verification").isEqualTo("What type of profile would you like to create?");;
                break;
            case "Create a New Profile" : softly().assertThat(getDriver().findElement(By.xpath("//*/*[contains(text(), '"+itemValidation+"')]")).getText()).as("New Modal verification").isEqualTo("Create a New Profile");
                break;
            case "X Action" :  getDriver().findElement(By.cssSelector("i[class ='close icon")).isDisplayed();
                break;
            case "Cancel" : getDriver().findElement(By.xpath("//button[contains(text(), '"+ itemValidation +"')]"));
                break;
            case "Next" : getDriver().findElement(By.xpath("//button[contains(text(), '"+ itemValidation +"')]"));
                break;
            case "Student Profile" : getDriver().findElement(By.xpath("//span[contains(text(), '"+ itemValidation +"')]"));
                break;
            case "Alumni Profile" : getDriver().findElement(By.xpath("//span[contains(text(), '"+ itemValidation +"')]"));
                break;
            case "Faculty Profile" : getDriver().findElement(By.xpath("//span[contains(text(), '"+ itemValidation +"')]"));
                break;
            case "Program Profile" : getDriver().findElement(By.xpath("//span[contains(text(), '"+ itemValidation +"')]"));
                break;
        }

    }

    public void createANewLink(String URL, String Title) {
        waitUntilPageFinishLoading();
        createNewLink().click();
        linkURL().sendKeys(URL);
        linkTitle().sendKeys(Title);
        addButton().click();
    }

    public void verifyRemoveLink() {
        waitUntilPageFinishLoading();
        softly().assertThat(removeLink().getText()).as("Verify Remove link button").isEqualTo("Remove");
        removeLink().click();

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

    public void clickOnPublishMyLinksAndProfilesChangesButton(String buttonName) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + buttonName + "')]")));
        /*Verifying the Pink button*/
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + buttonName + "')]")).getText()).as("Publish Media button").isEqualTo(buttonName);

        switch (buttonName) {
            case "Publish my links & profiles changes" : publishMyLinksAndProfilesChangesButton().click();
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

    public void verifyNoProfilesInPremium(String premiumText) {
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

    public void clickOnSubmitChangesButton() {
        submitChangesButton().click();
    }

    public void clickOnPublishMyLinksButton(String URL) {
        clickOnPublishMyLinksAndProfile(URL);
    }

    public void clickOnContinueEditingLink() {
        continueEditingButton().click();
    }

    private void verifyLinksContent(String contents) {
        softly().assertThat(getDriver().findElement(By.xpath("//h3[contains(text(), '" + contents +"')]")).getText()).as("Verify Contents").contains(contents);
    }

    private void verifyURLs(String contents) {
        softly().assertThat(requestInformationInputBox().getAttribute("value")).as("Verify Contents").contains(contents);
    }

    private BooleanAssert validateRequestInformationFormatURLs() {
        return softly().assertThat(requestInformationInputBox().getAttribute("value").matches("(^http[s]?:\\/\\/)(.*)"));
    }

    private void clickOnPublishMyLinksAndProfile(String URL) {
        requestInfoInput(URL);
        clickOnPublishMyLinksAndProfilesChangesButton("Publish my links & profiles changes");
    }

    private void verifyTitleLinksContent(String contents) {
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(text(), '" + contents +"')]")).getText()).as("Verify Contents").contains(contents);
    }

    private void verifyDropdownContent(String contents) {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Communicate ')]")).click();
        softly().assertThat(getDriver().findElement(By.xpath("//a[contains(text(), '" + contents +"')]")).getText()).as("Verify Contents").contains(contents);
    }
    private void requestInfoInput(String URL) {
        getDriver().findElement(By.cssSelector("input[id ='request-info-link-input")).clear();
        getDriver().findElement(By.cssSelector("input[id ='request-info-link-input")).sendKeys(URL);
    }


    //Locators
    private WebElement mediaTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'MEDIA')]"));
    }

    private WebElement createNewLink() {
        linkInput().sendKeys("Text1");
        return getDriver().findElement(By.xpath("//span[contains(text(), 'CREATE A NEW LINK')]"));
    }

    /**
     * The below method is to scroll down the webpage till the specific webelement, which method is collecting in method parameter
     */
    public void scrollDown(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,350)", "");
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    private WebElement requestInformationInputBox() {
        return  getDriver().findElement(By.cssSelector("input[id ='request-info-link-input"));
    }

    private WebElement introTab() {
        return getDriver().findElement(By.xpath("//a[contains(text(), 'INTRO')]"));
    }

    private WebElement linksAndProfilesTab() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'LINKS & PROFILES')]"));
    }

    private WebElement createNewProfile() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'CREATE A NEW PROFILE')]"));
    }

    private WebElement clickOnLinks() {
        return getDriver().findElement(By.cssSelector("li[id='links-accordion-title"));
    }

    private WebElement clickOnButtons(String button) {
        return getDriver().findElement(By.xpath("//span[contains(text(), '"+ button +"')]"));
    }

    private WebElement clickOnNextButton(String button) {
        return getDriver().findElement(By.cssSelector("button[class='ui primary button']"));
    }

    private WebElement clickOnProfilesLink() {
        return getDriver().findElement(By.cssSelector("li[id='profiles-accordion-title'"));
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

    private WebElement publishMyLinksAndProfilesChangesButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Publish my links & profiles changes')]"));
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

    private WebElement linkURL() {
        return getDriver().findElement(By.cssSelector("input[id='linkURL']"));
    }

    private WebElement linkTitle() {
        return getDriver().findElement(By.cssSelector("input[id='linkTitle']"));
    }

    private WebElement linkInput() {
        return getDriver().findElement(By.cssSelector("input[aria-label='Test']"));
}

    private WebElement addButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui primary button']"));
    }

    private WebElement premiumFeaturesButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Premium Features')]"));
    }

    private WebElement removeLink() {
        return getDriver().findElement(By.cssSelector("button[id='remove-link-0']"));
    }
}
