package pageObjects.HE.eventsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class EventsContactPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public EventsContactPageImpl() {
        logger = Logger.getLogger(EventsContactPageImpl.class);
    }
    private EventsPageImpl eventsPage = new EventsPageImpl();

    public void verifyEventTitleIsPresent() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Events page is not correctly displayed", eventsTitle().isDisplayed());
    }

    public void clickCreateEvent() {
        createEventButton().click();
    }

    public void newContact(String Contact) {
        driver.get(driver.getCurrentUrl());
        waitForUITransition();
        searchNewContactField().clear();
        waitForUITransition();
        searchNewContactField().sendKeys(Contact);

    }

    public void clickNewContact() {
        newContact().click();
    }

    public void addNewContact(DataTable contactDetailsData) {
        List<List<String>> eventContactDetails = contactDetailsData.asLists(String.class);
        for (List<String> row : eventContactDetails) {
            switch (row.get(0)) {
                case "Contact Name":
                    contactNameField().clear();
                    contactNameField().sendKeys(row.get(1));
                    break;
                case "Contact Email":
                    contactEmailField().clear();
                    contactEmailField().sendKeys(row.get(1));
                    break;
                case "Contact Number":
                    contactNumberField().clear();
                    contactNumberField().sendKeys(row.get(1));
                    break;
            }
        }
    }


    public void saveContact(){
        saveEContact().click();
    }

    public void returnToList(){

        returnToListLink().sendKeys(Keys.RETURN);
    }
    public void verifyContact(String contact){
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@name='eventContact']"),0));
        String results = driver.findElement(By.cssSelector("input[name='contacts-search']")).getAttribute("value");
        Assert.assertTrue("Newly created contact is not present", results.equals(contact));

    }
    public  void requiredFieldMessage(){
        String message = driver.findElement(By.cssSelector("div._1ryO_t8iwDyfipN2lExls span")).getText();
        Assert.assertTrue("Contact Name require field message is not displayed",contactMessage().isDisplayed());

    }
    public void editEventContact(){
        searchNewContactField().click();
        editContact().click();
    }


    public void deleteEventContact()
    {
        deleteContact().click();
    }

    public void confirmDeleteForEventContact(){
        confirmDeleteContact().sendKeys(Keys.RETURN);
       //eventsTitle().click();

    }
    public void warningMessages(){
        pastEventContactMessage().click();

    }


    public void unpublishedEventContactMessage()    {
        String unpublishEventMessage = driver.findElement(By.cssSelector("div.ui.warning.message")).getText();

     Assert.assertTrue("This contact is associated with an existing unpublished event message is not matching with actual message",unpublishedEContactwarningmessage().getText().equals(unpublishEventMessage));

    }
    public void publishedEventContactMessage(){
        String publishedEventMessage = driver.findElement(By.cssSelector("div.ui.warning.message")).getText();
        Assert.assertTrue("This contact can not be deleted since it is associated with a published event",publishedEContactwarningmessage().getText().equals(publishedEventMessage));
    }
    public void deleteNo()
    {
        deleteContactNo().click();
    }
    //locators
    private WebElement eventsTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
    private WebElement createEventButton() { return driver.findElement(By.xpath("//span[text()='CREATE EVENT']")); }
    private WebElement searchNewContactField() { return driver.findElement(By.cssSelector("input[name='contacts-search']")); }
    private WebElement newContact(){return driver.findElement((By.cssSelector("a._3NjlddcItI-OTbh8G7MTQQ")));}
    private WebElement contactNameField(){return  driver.findElement(By.cssSelector("div._1uAAF-6zFs7yGi-ocWs64V:nth-of-type(2) input"));}
    private WebElement contactEmailField(){return  driver.findElement(By.cssSelector("div._1uAAF-6zFs7yGi-ocWs64V:nth-of-type(3) input"));}
    private WebElement contactNumberField(){return  driver.findElement(By.cssSelector("div._1uAAF-6zFs7yGi-ocWs64V:nth-of-type(4) input"));}
    private WebElement saveEContact(){return  driver.findElement(By.cssSelector(".ui.primary.button._2H3g3LLcV4s11zFc2jOdYb span"));}
    private WebElement returnToListLink(){return driver.findElement(By.cssSelector("._25izRUHGnAwkQLgvMYYxA2 span"));}
    private WebElement contactNamePresentinSearchBox(){return driver.findElement(By.cssSelector("div.ui.icon.input._3KUN7Tb1NlCv2_RQqzKQCj"));}
    private WebElement newlyCreatedContact(){return driver.findElement(By.cssSelector("table.ui.unstackable.very.basic.left.aligned.table._1CESARq218cDE7u8vMyW3O tr._27yC02oMUpFoQeumGwitvn div._3NjlddcItI-OTbh8G7MTQQ"));}
    private WebElement primaryContactLabel(){return driver.findElement(By.xpath("//span[text()='EVENT PRIMARY CONTACT']"));}
    private WebElement contactMessage(){return  driver.findElement(By.cssSelector("div._1ryO_t8iwDyfipN2lExls span"));}
    private WebElement editContact(){return driver.findElement(By.cssSelector("table.ui.unstackable.very.basic.left.aligned.table._1CESARq218cDE7u8vMyW3O tr._27yC02oMUpFoQeumGwitvn span"));}
    private WebElement deleteContact(){return driver.findElement(By.cssSelector("a._3_90ONUp_vTq-ixilU4zN7"));}
    private WebElement confirmDeleteContact(){return driver.findElement(By.cssSelector(".ui.active.transition.visible.inverted.dimmer .ui.primary.button"));}
    private WebElement pastEventContactMessage(){ return driver.findElement(By.cssSelector("div.ui.warning.message"));}
    private WebElement deleteContactNo(){return  driver.findElement(By.cssSelector("\n" + "button.ui.black.basic.button"));}
    private WebElement deleteLocationLink() { return driver.findElement(By.cssSelector("a._2m8lgYbkXvYZuzvkh1m_Q1 span")); }
    public WebElement locationDeleteMessage() { return driver.findElement(By.cssSelector("div.ui.warning.message")); }
    private WebElement unpublishedEContactwarningmessage(){return driver.findElement(By.cssSelector("div.ui.warning.message"));}
    private WebElement publishedEContactwarningmessage(){return driver.findElement(By.cssSelector("div.ui.warning.message"));}

    }




