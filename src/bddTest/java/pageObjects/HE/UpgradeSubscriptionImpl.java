package pageObjects.HE;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Length;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpgradeSubscriptionImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sRequestInformationMessageContent%sRequestInformationMessage.properties",fs ,fs ,fs ,fs ,fs);
    public UpgradeSubscriptionImpl() {
        logger = Logger.getLogger(UpgradeSubscriptionImpl.class);
    }

    public void premiumLock(String subscriptionForLock){

        Assert.assertTrue("Advance Awareness logo is not available",premiumLockIcon(subscriptionForLock).isDisplayed());
        Assert.assertTrue("Advance Awareness Upgrade button is not available",upgradeSubscriptionbutton(subscriptionForLock).isDisplayed());



    }

    public void clickUpgradeSubscription(String subscriptionName){

        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//h2[text()='"+subscriptionName+"']/../div/button"),1));
        jsClick(upgradeSubscriptionbutton(subscriptionName));

    }

    public void recieveCommunications(){
        communicationCheck().click();

    }
    public void enterAdditionalMessage(String messageEntryTitle){
        subscriptionMessage().clear();
        subscriptionMessage().sendKeys(getStringFromPropFile(propertiesFilePath,messageEntryTitle));

    }

    public void requestInformationToUpgrade(){
        jsClick(requestInformation());
        Assert.assertTrue("Request Information Confirmation Message in not displayed",requestConfirmationMessage().isDisplayed());

    }

    public int messageCharCount(String message)  {

         int count = message.length();
        return count;

    }
    public void verifyMessageCount(String characterCount){
        int x = messageCharCount(subscriptionMessage().getText());
        Assert.assertTrue("Additional Message Count is not less than 500 characters",x<= Integer.parseInt(characterCount));

    }

    public void editContactDetails(DataTable ContactDataTable){
        List<List<String>> contactDetails = ContactDataTable.asLists(String.class);
        enterContactDetails(contactDetails);
    }


    public void enterContactDetails(List<List<String>> data){
        for (List<String> row : data){
            switch (row.get(0)){
                case "First Name":
                    contactFirstNameField().clear();
                    contactFirstNameField().sendKeys(row.get(1));
                    break;
                case "Last Name":
                    contactLastNameFiled().clear();
                    contactLastNameFiled().sendKeys(row.get(1));
                    break;
                case "Email":
                    ContactEmailField().clear();
                    ContactEmailField().sendKeys(row.get(1));
                    break;
                case "Phone":
                    contactPhoneField().clear();
                    contactPhoneField().sendKeys(row.get(1));
                    break;
                case "Message":
                    contactMessageField().clear();
                    contactMessageField().sendKeys(row.get(1));
                    break;
            }
        }
    }

    public void clickUpgradeFilter(String filter ){
        upgradeFilterButton(filter).click();

    }

    public void filterPremiumLock(String filterForLock){
        Assert.assertTrue("Advance Awareness logo is not available",premiumLockIconForFilter(filterForLock).isDisplayed());
        Assert.assertTrue("Advance Awareness Upgrade button is not available",upgradeFilterButton(filterForLock).isDisplayed());


    }

    public void filterUpgradeClick(String upgradeFilters){
        upgradeFilterButton(upgradeFilters).click();

    }
    
    /**
     * navigate to the community page
     */
    public void navigateToCommunityPage(){
        getNavigationBar().goToCommunity();
    }

    /**
     * clicking upgrade button
     */
    public void clickUpgradeButtonInCommunity(){
        communityFrame();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(upgradeButton()));
        communityUpgradeButton().click();
        getDriver().switchTo().defaultContent();
    }

    /**
     * verifying the upgrade form using the following values
     * @param dataTable : valid sections are : First Name ,Last Name ,Email ,Institution
     */
    public void verifyFieldsInUpgradeForm(DataTable dataTable){
        List<String> fields = dataTable.asList(String.class);
        for(String requiredFields:fields){
            String Fields =requiredFieldsInUpgradeForm(requiredFields) .getText();
            softly().assertThat(Fields).as("Required Fields").isEqualTo(fields);
        }

    }

    /**
     * verifying upgraded checkbox text in upgrade form
     * @param checkBoxText
     */
    public void verifyCheckBoxInUpgradeForm(String checkBoxText){
        String originalText = textInUpgradeForm(checkBoxText).getText();
        softly().assertThat(checkBoxText).as("Checkbox text").isEqualTo(originalText);
    }

    /**
     * verifying upgraded note text in upgrade form
     * @param note
     */
    public void verifyNoteInUpgradeForm(String note){
        String originalText = textInUpgradeForm(note).getText();
        softly().assertThat(note).as("Note text").isEqualTo(originalText);
    }


    /**
     * verifying url by navigate to privacy policy page
     * @param url
     */
    public void verifyPrivacyPolicy(String url) {
        jsClick(privacyPolicy());
        waitUntilPageFinishLoading();
        String currentWindow = getDriver().getWindowHandle();
        String privacyPolicy = null;
        Set<String> windows = getDriver().getWindowHandles();
        if (windows.size() > 1) {
            for (String thisWindow : windows) {
                if (!thisWindow.equals(currentWindow)) {
                    privacyPolicy = thisWindow;
                }
            }
            getDriver().switchTo().window(privacyPolicy);
            waitUntil(ExpectedConditions.visibilityOfElementLocated(textInPrivacyPolicyPage()));
            String actualURl = getDriver().getCurrentUrl();
            softly().assertThat(actualURl).as("URl of the current page").isEqualTo(url);
            getDriver().close();
            getDriver().switchTo().window(currentWindow);
        }
    }

    /**
     * verifying updated text in upgraded form
     * @param text
     */
    public void verifyUpdatedTextInUpgradeForm(String text){
        String expectedText = textInUpgradeForm(text).getText();
        softly().assertThat(expectedText).as("Updated text").isEqualTo(text);
    }

    /**
     * navigating to the respective fields
     * @param fields :
     */
    public void navigateToFieldsInRepVisits(String fields){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        switch (fields){
            case "Agenda":
                getCalendarBtn().click();
                waitUntil(ExpectedConditions.visibilityOf(agendaButton()));
                agendaButton().click();
                waitUntilPageFinishLoading();
                break;
            case "Travel Plan":
                navigateToRepVisitsSection("Travel Plan");
                break;
            case "Contacts":
                navigateToRepVisitsSection("Contacts");
                break;
            case "Recommendations":
                navigateToRepVisitsSection("Recommendations");
                break;
            case "Visit Feedback":
                navigateToRepVisitsSection("Visit Feedback");
                break;
            default:
                Assert.fail("Invalid option");
        }
        waitUntilPageFinishLoading();
    }

    /**
     * click upgrade button in repvisits
     */
    public void clickUpgradeButtonInRepvisits(){
        calendarUpgradeButton().click();
    }

    /**
     *close upgrade form
     */
    public void closeUpgradeForm(){
        jsClick(closeButton());
        waitUntilPageFinishLoading();
    }

    public void navigateToRepVisitsSection(String pageName) {
        getNavigationBar().goToRepVisits();
        getDriver().findElement(By.xpath("(//a/span[text()='"+pageName+"'])[2]")).click();
        waitUntilPageFinishLoading();
    }

    private WebElement contactFirstNameField(){
        return driver.findElement(By.cssSelector("input#field13"));
    }

    private WebElement contactLastNameFiled(){
        return driver.findElement(By.cssSelector("input#field14"));
    }
    private WebElement ContactEmailField(){
        return driver.findElement(By.cssSelector("input#field12"));

    }
    private WebElement contactPhoneField(){
        return driver.findElement(By.cssSelector("input#field15"));
    }
    private WebElement contactMessageField(){
        return driver.findElement(By.cssSelector("textarea#field18"));
    }

    private WebElement subscriptionMessage(){
        return driver.findElement(By.cssSelector("textarea#field18"));
    }
    private WebElement communicationCheck(){
        return driver.findElement(By.cssSelector("input#field20.ui.checkbox"));
    }
    private WebElement premiumLockIcon(String LockIconForSubscription){
        return driver.findElement(By.xpath("//h2[text()='"+LockIconForSubscription+"']/../../img"));
    }

    private WebElement upgradeSubscriptionbutton(String upgradeSubscription){
        return driver.findElement(By.xpath("//h2[text()='"+upgradeSubscription+"']/../div/button"));
    }
    private WebElement Upgradebu(String upgradeSubscription){
        return driver.findElement(By.xpath("//div[text()='"+upgradeSubscription+"']/../div/button"));
    }
    private WebElement requestInformation(){
        return driver.findElement(By.xpath("//span[text()='Request Information']"));
    }
    private WebElement requestConfirmationMessage(){
        return driver.findElement((By.xpath("//span[contains(text(), 'be reaching out to you soon.')]")));
    }
    private WebElement premiumLockIconForFilter(String filterName){
        return driver.findElement(By.xpath("//span[text()='" + filterName + "']/../../..//img"));

    }
    private WebElement upgradeFilterButton(String filterUpgrade){
        return driver.findElement(By.cssSelector("button#locked-upgrade-button.ui.secondary.button._1xXwDP5X-gB37o7xxX7f5k"));

    }

    /**
     * return calendar button
     * @return : Web element
     */
    private WebElement getCalendarBtn() { return link("Calendar"); }

    /**
     * return close icon button
     * @return : Web element
     */
    private WebElement closeButton(){return getDriver().findElement(By.cssSelector("div>i[class='close icon']"));}

    /**
     * return agenda button
     * @return : Web element
     */
    private WebElement agendaButton(){return getDriver().findElement(By.cssSelector("button[title='Agenda']"));}

    /**
     * return upgrade button
     * @return Web element
     */
    private WebElement calendarUpgradeButton(){
        return getDriver().findElement(By.xpath("//button/span[text()='UPGRADE']"));
    }

    /**
     * verify text in upgrade form
     * @param text : text to return
     * @return
     */
    private WebElement textInUpgradeForm(String text){
        return getDriver().findElement(By.xpath("//span[text()='"+text+"']"));
    }

    /**
     * return privacy policy link
     * @return : Web element
     */
    private WebElement privacyPolicy(){
        return getDriver().findElement(By.linkText("Privacy Policy"));
    }

    /**
     *
     * @param requiredFields : fields to return
     * @return : Web element
     */
    private WebElement requiredFieldsInUpgradeForm(String requiredFields){
        return getDriver().findElement(By.xpath("//div[@class='required field']/label/span[text()='"+requiredFields+"']"));
    }

    /**
     * return community upgrade button
     * @return : Web element
     */
    private WebElement communityUpgradeButton(){
        return getDriver().findElement(By.xpath("//div/a[text()='Upgrade']"));
    }

    /**
     * return community upgrade button
     * @return Web element
     */
    private By upgradeButton(){return By.xpath("//div/a[text()='Upgrade']");}

    /**
     * return text in privacy policy page
     * @return Web element
     */
    private By textInPrivacyPolicyPage(){
        return By.xpath("//span[text()='User Provided Information: ']");
    }
}
