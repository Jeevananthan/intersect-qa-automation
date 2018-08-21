package pageObjects.HUBS.FamilyConnection.FCColleges;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.eventsPage.EventsPageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by mbhangu on 12/13/2016.
 */
public class FCCollegeEvents extends PageObjectFacadeImpl {
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sEventMessages%sEventMessages.properties", fs, fs, fs, fs, fs);


    public void verifyCollegeEventsDetails() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        assertTrue("The View/Update button is not displayed ", FCCollegeEventsPage.labelCollegeEventsTitle.isDisplayed());
    }

    public void verifySignUpHereText(String eventSignup) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        assertTrue("The College Events Details page is Not Displayed ", FCCollegeEventsPage.CollegeEventsDetailsSignUpHere.isDisplayed());
    }

    public void clickSignUpButton() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.clickSignUpButton.click();
    }

    public void verifyConfirmationMessageEvents(String confirmationMessage) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        assertTrue("The confirmation message is not correct", FCCollegeEventsPage.labelConfirmationMessage.getText().equals(confirmationMessage));
    }

    public void clickSignUpButtonForCollegeandForEvent(String nameSchool, String nameEvent) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.buttonSignUponCollegeEvents.click();
    }

    public void clickReturntoAllEventslink() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.linkReturntoAllEvents.click();
    }

    public void clickRecommendedEventslink() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.closeEventToolTip.click();
        FCCollegeEventsPage.recommendedEvents.click();
    }

    public void verifyRecommendedEventsText() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        waitForUITransition();
        assertTrue("Text is Not Displayed for Recommended Events ", FCCollegeEventsPage.verifyRecommendedEventsText.isDisplayed());
    }

    public void clicklinkUpcomingCollegeEvents() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.UpcomingCollegeEvents.click();
    }

    public void clickMoreOptions() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.eventsMoreOptions.click();
    }

    public void VerifyFindEventsByOptions() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        assertTrue("The Text is not displayed on screen", FCCollegeEventsPage.VerifyFindEventsByOptionsText.isDisplayed());
    }

    public void selectDistanceandEnterZipCode(String distance, String Zipcode) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        {
            Select distanceDropDown = new Select(FCCollegeEventsPage.selectDistanceandEnterZipCodeValue);
            distanceDropDown.selectByVisibleText(distance);
            FCCollegeEventsPage.eventsTextBoxForZipCode.sendKeys(Zipcode);
        }

        Select distanceDropDown = new Select(FCCollegeEventsPage.selectDistanceandEnterZipCodeValue);
        distanceDropDown.selectByVisibleText(distance);
        new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(FCCollegeEventsPage.eventsTextBoxForZipCode));
        FCCollegeEventsPage.eventsTextBoxForZipCode.sendKeys(Zipcode);
    }

    public void clickShowResults() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.clickShowResults.click();
    }

    public void verifyFilteredEvents(String eventName) {
        boolean result = false;
        List<WebElement> arrayOfEvents;

        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        arrayOfEvents = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventsList));
        for (int i = 0; i < arrayOfEvents.size(); i++) {
            if (arrayOfEvents.get(i).getText().equals(eventName)) {
                result = true;
            }

        }
        assertTrue("The Event Name not present or it displays incorrect data", result);
    }

    public void clickCollegesTabOld() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(FCCollegeEventsPage.oldCollegesTab));
        FCCollegeEventsPage.oldCollegesTab.click();
    }

    public void clickViewUpdatebutton() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.clickUpdateButton.click();


    }

    public void selectYesCancelRegistration() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.clickYesToCancelRegistration.click();
    }

    public void clickSaveChanges() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.clickSaveChangesButton.click();
    }

    public void cancelRegistrationVerify(String cancelRegistrationConfirmed) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        assertTrue("The confirmation message is not correct", FCCollegeEventsPage.ConfirmationRegistrationCancelledMessage.getText().equals(cancelRegistrationConfirmed));
    }

    public void signUpDealineMessage(String deadlinePassed) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        assertTrue("The signup deadline has passed message is not available", FCCollegeEventsPage.signUpMessage.getText().equals(deadlinePassed));
    }

    public void searchHost(String collegeName) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        waitForUITransition();
        FCCollegeEventsPage.hostedByTextBox.sendKeys(collegeName);
        waitForUITransition();
    }

    public void clickCollegeEventsDetails() {
        FCCollegeEventsPage.clickEventInformationlink().click();
    }

    public void verifyInformationAndWelcomeMessage() {
        String tooltipcontent = FCCollegeEventsPage.EventToolTipMessage().getText();
        List<String> eventsListfromProperties = getListFromPropFile(propertiesFilePath, ";", "event.information.message");
        for (String element : eventsListfromProperties) {
            Assert.assertTrue("Events Information Text is  Incorrect", tooltipcontent.contains(element));
        }
    }

    public void signUpToEvent() {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        waitForUITransition();
        if (driver.findElements(By.cssSelector(FCCollegeEventsPage.welcomeTooltipLocator)).size() > 0) {
            FCCollegeEventsPage.welcomeTooltipCloseButton.click();
        }
        List<WebElement> listOfEventNames;
        List<String> listOfEventNamesStrings = new ArrayList<>();
        waitForUITransition();
        waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(FCCollegeEventsPage.nextArrowsList)));
        WebElement upperNextArrow = driver.findElements(By.cssSelector(FCCollegeEventsPage.nextArrowsList)).get(0);
        listOfEventNames = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventNamesList));
        for (WebElement eventNameElement : listOfEventNames) {
            listOfEventNamesStrings.add(eventNameElement.getText());
        }

        while (!listOfEventNamesStrings.contains(EventsPageImpl.eventName)) {
            waitForUITransition();
            waitUntilPageFinishLoading();
            waitUntilElementExists(upperNextArrow);
            upperNextArrow.click();
            waitForUITransition();
            listOfEventNames = driver.findElements(By.cssSelector(FCCollegeEventsPage.eventNamesList));
            for (WebElement eventNameElement : listOfEventNames) {
                listOfEventNamesStrings.add(eventNameElement.getText());
            }
        }

        if (listOfEventNamesStrings.contains(EventsPageImpl.eventName)) {
            FCCollegeEventsPage.getSignUpButton(EventsPageImpl.eventName).click();
            clickSignUpButton();
        }
        waitForUITransition();
    }

    public void signUpForEvent(String eventForAttendee) {
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.signupForEvent(eventForAttendee).click();

    }
    public void registerForEvent(){
        PageFactory.initElements(driver, FCCollegeEventsPage.class);
        FCCollegeEventsPage.registerEvent().click();
    }
}
