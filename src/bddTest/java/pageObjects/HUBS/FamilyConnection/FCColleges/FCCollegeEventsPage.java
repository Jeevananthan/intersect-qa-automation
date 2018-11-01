package pageObjects.HUBS.FamilyConnection.FCColleges;

import org.apache.bcel.generic.RETURN;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static Selenium.SeleniumLibrary.driver;

/**
 * Created by mbhangu on 12/13/2016.
 */
public class FCCollegeEventsPage {
    @FindBy(how = How.CSS, using = ".fc-grid__col--xs >strong")
    public static WebElement labelCollegeEventsTitle;
    @FindBy(how = How.CSS, using = ".fc-fieldset__legend")
    public static WebElement CollegeEventsDetailsSignUpHere;
    @FindBy(how = How.CSS, using = ".fc-button--primary")
    public static WebElement clickSignUpButton;
    @FindBy(how = How.CSS, using = "[ng-switch-when=\"REGISTERED\"]:not(.event-summary__status) h2:not([ng-if=\"vm.userCanSeeSignup\"])")
    public static WebElement labelConfirmationMessage;

    @FindBy(how = How.CSS, using = ".event-summary__title.ng-binding")
    public static WebElement buttonSignUponCollegeEvents;

    @FindBy(how = How.CSS, using = ".event-summary__status-column span.fc-button.fc-button--call-to-action.ng-binding")
    public static WebElement SignUpforCollegeEvent;

    @FindBy(how = How.CSS, using = "a[href='#/']")
    public static WebElement linkReturntoAllEvents;

    @FindBy(how = How.CSS, using = ".fc-button.fc-button--ghost-light.fc-button--size-small")
    public static WebElement recommendedEvents;

    @FindBy(how = How.CSS, using = ".fc-info-tag.fc-info-tag--recommended.ng-scope")
    public static WebElement verifyRecommendedEventsText;

    @FindBy(how = How.CSS, using = "div#main-content div:nth-of-type(1).block div.block-r ul:nth-of-type(1) a")
    public static WebElement UpcomingCollegeEvents;

    @FindBy(how = How.CSS, using = ".fc-button.fc-button--primary[ng-click=\"vm.toggleForm()\"]")
    public static WebElement eventsMoreOptions;

    @FindBy(how = How.CSS, using = "div.search-form__fields.search-form__fields--open div.fc-central-content > strong")
    public static WebElement VerifyFindEventsByOptionsText;

    @FindBy(how = How.CSS, using = "select[ng-change='vm.updateZip()']")
    public static WebElement selectDistanceandEnterZipCodeValue;

    @FindBy(how = How.CSS, using = "[ng-required=\"vm.isZipRequired()\"]")
    public static WebElement eventsTextBoxForZipCode;

    @FindBy(how = How.CSS, using = ".fc-button.fc-button--primary")
    public static WebElement clickShowResults;

    @FindBy(how = How.CSS, using = "h3")
    public static WebElement verifyFilteredEventsData;

    @FindBy(how = How.CSS, using = ".fc-tooltip__close.fc-tooltip__close--event-message svg")
    public static WebElement closeEventToolTip;

    public static String eventsList = "h3";

    @FindBy(how = How.CSS, using = "a[href*='/colleges']")
    public static WebElement oldCollegesTab;
    @FindBy(how = How.CSS, using = "div.events-list event-summary:nth-of-type(1) label")
    public static WebElement firstEventRecommendedLabel;

   /* @FindBy(how = How.CSS, using = ".fc-button.ng-binding")
    public static WebElement clickUpdateButton;*/

    @FindBy(how = How.CSS, using = "label.fc-label.fc-label--inline")
    public static WebElement clickYesToCancelRegistration;

    @FindBy(how = How.CSS, using = "button.fc-button.fc-button--danger")
    public static WebElement clickSaveChangesButton;

    @FindBy(how = How.CSS, using = "h2:nth-child(2)")
    public static WebElement ConfirmationRegistrationCancelledMessage;

    @FindBy(how = How.XPATH, using = "//h3[text()='Deadline Passed For Event']")
    public static WebElement signUpMessage;

    public static String eventsListLocator = ".events-list event-summary";

    @FindBy(how = How.CSS, using = "input[placeholder=\"Type a college name here\"]")
    public static WebElement hostedByTextBox;

    public static String eventNamesList = "h3.event-summary__title.ng-binding";

    public static String nextArrowsList = "a.fc-pagination__link.ng-scope";

    public static WebElement getSignUpButton(String eventName) {
        return driver.findElement(By.xpath("//h3[text()='" + eventName + "']/../../../div[@class='event-summary__status-column']/div/div/span[@class='ng-scope']"));
    }

    public static String welcomeTooltipLocator = "div[aria-hidden=\"false\"]";

    @FindBy(how = How.CSS, using = "div[aria-hidden=\"false\"] span.fc-tooltip__close.fc-tooltip__close--event-message")
    public static WebElement welcomeTooltipCloseButton;

    public static WebElement clickEventInformationlink()
    {
        return driver.findElement(By.cssSelector(".fc-icon.fc-icon--header.fc-icon--size-2x"));
        //@FindBy(how = How.CSS, using = ".fc-icon.fc-icon--header.fc-icon--size-2x") ;
    }
    public static WebElement EventToolTipMessage(){
        return  driver.findElement(By.cssSelector("div.fc-tooltip.fc-tooltip--right.fc-tooltip--event-message"));

    }
    public static WebElement signupForEvent(String eventName){
        return driver.findElement((By.xpath("//h3[text()='" + eventName + "']/../../..//div[@ng-switch-when='SIGNUP']")));
    }
    public  static WebElement registerEvent(){
        return  driver.findElement(By.cssSelector("button.fc-button.fc-button--primary"));

    }
    public static WebElement clickUpdateButton (String updateEvent){
        return driver.findElement((By.xpath("//h3[text()='" + updateEvent + "']/../../../div[@class='event-summary__status-column']")));
    }

    public static WebElement registeredEventSaveChangesButton() { return driver.findElement(By.cssSelector("button[ng-disabled='!vm.cancellationAffirmed']")); }
}



