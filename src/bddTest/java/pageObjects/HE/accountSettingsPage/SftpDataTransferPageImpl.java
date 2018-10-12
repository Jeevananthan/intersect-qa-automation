package pageObjects.HE.accountSettingsPage;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Arrays;
import java.util.List;

public class SftpDataTransferPageImpl extends PageObjectFacadeImpl {
    private AccountSettingsPageImpl accountSettings = new AccountSettingsPageImpl();

    /**
     * Verifies that the label that says: SFTP Data Transfer is displayed
     */
    public void verifySftpDataTransferText(String expectedText){
        Assert.assertTrue(String.format("The text: %s is not displayed",expectedText),text(expectedText).isDisplayed());
    }

    /**
     * Verifies that the SE UP CONNECTION BUTTON is displayed
     */
    public void verifySetupConnectionButtonIsDisplayed(){
        Assert.assertTrue("Yhe SET UP CONNECTION button is not displayed",
                setupConnectionButton().isDisplayed());
    }

    /**
     * Deletes the current sft data transfer connection
     */
    public void deleteSftpDataTransferConnection(){
        accountSettings.accessUsersPage("Account Settings", "SFTP Data Transfer");
        if(getDriver().findElements(editLinkLocator()).size()>0){
            editLink().click();
            deleteThisConfigurationLink().click();
            waitUntil(ExpectedConditions.visibilityOf(deleteSftpConfigurationButton()));
            deleteSftpConfigurationButton().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(setupConnectionButtonLocator()));
        }
    }

    /**
     * Verifies if the given text is present in the title link
     * @param expectedText
     */
    public void verifySftpDataTransferTitleLink(String expectedText){
        Assert.assertTrue(String.format("The %s link is not displayed",expectedText),sftpDataTransferTitleLink()
                .isDisplayed());
        String actualText = sftpDataTransferTitleLink().getAttribute("innerText");
        Assert.assertTrue(String.format("The sftp transfer link is incorrect, expected: %s, actual: %s"
                ,expectedText,actualText),actualText.contains(expectedText));
    }

    /**
     * Verifies that the title link send us back to the main page
     */
    public void verifySftDataTransferTitleLinkBehavior(){
        //when clicking it should send us back to the main page
        sftpDataTransferTitleLink().click();
        Assert.assertTrue("The Sftp Dta Transfer title link does not send to the main page",
                setupConnectionButton().isDisplayed());

    }

    /**
     * Verifies the host text box value
     * @param expectedValue
     */
    public void verifyHostTextBoxValue(String expectedValue){
        String actualValue = getTextBoxValue(hostTextBox());
        Assert.assertEquals(String.format("The host text box value is not correct, expected: %s, actual: %s",
                expectedValue,actualValue),expectedValue,actualValue);
    }

    /**
     * Verifies the port textbox value
     * @param portValue
     */
    public void verifyPortTextBoxValue(String portValue){
        String actualValue = getTextBoxValue(portTextBox());
        Assert.assertEquals(String.format("The port text box value is incorrect, expected: %s, actual: %s",
                portValue,actualValue), portValue, actualValue);
    }

    /**
     * Verifies the path text box value
     * @param expectedValue
     */
    public void verifyPathTextBoxValue(String expectedValue){
        String actualValue = getTextBoxValue(pathTexBox());
        Assert.assertEquals(String.format("The path text box valuer is not correct, expected: %s, actual: %s",
                expectedValue,actualValue),expectedValue,actualValue);
    }

    /**
     * Verifies the user name text box value
     * @param expectedValue
     */
    public void verifyUserNameTextBoxValue(String expectedValue){
        String actualValue = getTextBoxValue(userNameTextBox());
        Assert.assertEquals(String.format("The user name text box valuer is not correct, expected: %s, actual: %s",
                expectedValue,actualValue),expectedValue,actualValue);
    }

    /**
     * Verifies the password text box value
     * @param expectedValue
     */
    public void verifyPasswordTextBoxValue(String expectedValue){
        String actualValue = getTextBoxValue(passwordTextBox());
        Assert.assertEquals(String.format("The user password text box valuer is not correct, expected: %s, actual: %s",
                expectedValue,actualValue),expectedValue,actualValue);
    }

    /**
     * Verifies if the given values are in the ip whitelisting text box
     * @param tableValues
     */
    public void verifyIpWhiteListingValues(DataTable tableValues){
        List<List<String>> values = tableValues.raw();
        values.get(0).stream().forEach(
                value -> Assert.assertTrue(String.format("The ip : %s is not in the white listing text box",value)
                        , getTextAreaValue(ipWhiteListingTextBox()).contains(value))
        );
    }

    /**
     * Verifies that the GENERATE KEY button is displayed
     */
    public void verifyGenerateKeyButtonIsDisplayed(){
        Assert.assertTrue("The GENERATE KEY button is not displayed", generateKeyButton().isDisplayed());
    }

    /**
     * Selects the given option in the authentication method radio button
     * @param value
     */
    public void selectAuthenticationMethodRadioButton(String value){
        authenticationMethodRadioButton(value).click();
    }

    /**
     * Goes to the setup connection page
     */
    public void goToSetupConnectionPage(){
        setupConnectionButton().click();
        waitUntil(ExpectedConditions.visibilityOf(sftpDataTransferTitleLink()));
    }

    /**
     * Verifies the given transfer frequency check boxes
     * @param optionsTable
     */
    public void verifyTransferFrequencyCheckBoxes(DataTable optionsTable){
        List<List<String>> options = optionsTable.raw();
        options.get(0).stream().forEach(
                option -> Assert.assertTrue(String.format("The checkbox %s is not displayed", option),
                        transferFrequencyCheckBox(option).isEnabled())
        );
    }

    /**
     * Gets the value of a given text box
     * @return String
     */
    private String getTextBoxValue(WebElement control){
        return control.getAttribute("value");
    }

    /***
     * Gets the value of a given text area
     * @param control
     * @return
     */
    private String getTextAreaValue(WebElement control){
        return control.getAttribute("innerText");
    }

    /**
     * Verifies that the Check fingerprint to verify server check box is displayed
     */
    public void verifyCheckFingerprintToVerifyServerCheckBoxIsDisplayed(){
        Assert.assertTrue("The Check fingerprint to verify server check box is not displayed",
                checkFingerprintToVerifyServerCheckBox().isEnabled());
    }

    /**
     * Verifies that the test and save button is displayed
     */
    public void verifyTestAndSaveButtonIsDisplayed(){
        Assert.assertTrue("The test and save button is not displayed", testAndSaveButton().isEnabled());
    }

    /**
     * Generates a SSH key
     */
    public void generateSSHKey(){
        generateKeyButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(sshKeyTextBoxLocator()));
    }

    /**
     * Verifies that the given text is present in the generate ssh key button
     * @param expectedText
     */
    public void verifyGenerateKeyButtonText(String expectedText){
        String actualText = generateKeyButton().getAttribute("innerText");
        Assert.assertEquals(String.format("The Generate key button text is not correct, expected: %s, actual: %s"
                ,expectedText, actualText),expectedText,actualText);
    }

    /**
     * Verifies that the given text is present in the generate ssh key button
     * @param expectedText
     */
    public void verifyReGenerateKeyButtonText(String expectedText){
        String actualText = reGenerateKeyButton().getAttribute("innerText");
        Assert.assertEquals(String.format("The re Generate key button text is not correct, expected: %s, actual: %s"
                ,expectedText, actualText),expectedText,actualText);
    }

    /**
     * Verifies that the new ssh key was generated
     */
    public void verifySshKeyWasGenerated(){
        String key = sshKeyTextBox().getAttribute("innerText");
        Assert.assertFalse("The SSH Key was not generated",key.isEmpty());
    }

    /**
     * Verifies the message displayed after regenerating a new ssh key
     * @param expectedMessage
     */
    public void verifyGeneratedSshKeyMessage(String expectedMessage){
        String actualMessage = generatedSshKeyMessage().getAttribute("innerText");
        Assert.assertTrue(String.format("The message after generating the ssh key is not correct, expected: %s, " +
                "actual: %s",expectedMessage, actualMessage),actualMessage.matches(expectedMessage));
    }

    /**
     * Setups the ftp configuration
     * @param data with the sftp information
     */
    public void setupFtpConnection(DataTable data){
        List<List<String>> sftpInformation = data.raw();
        accountSettings.accessUsersPage("Account Settings", "SFTP Data Transfer");
        if(getDriver().findElements(editLinkLocator()).size()>0){
            editLink().click();
        } else{
            setupConnectionButton().click();
        }
        hostTextBox().clear();
        hostTextBox().sendKeys(sftpInformation.get(1).get(0));
        portTextBox().clear();
        portTextBox().sendKeys(sftpInformation.get(1).get(1));
        pathTexBox().clear();
        pathTexBox().sendKeys(sftpInformation.get(1).get(2));
        userNameTextBox().clear();
        userNameTextBox().sendKeys(sftpInformation.get(1).get(3));
        passwordTextBox().clear();
        passwordTextBox().sendKeys(sftpInformation.get(1).get(4));
        selectTransferFrequency(sftpInformation.get(1).get(5));
        selectCheckFingerprintToVerifyServerCheckBox(sftpInformation.get(1).get(6));
        testAndSaveButton().click();
        waitUntilPageFinishLoading();
        setUpNewFingerPrint(sftpInformation.get(1).get(6));
    }

    /**
     * Selects the given frequency checkboxes
     * @param frequency
     */
    private void selectTransferFrequency(String frequency){
        String[] days = frequency.split(",");
        Arrays.stream(days).forEach(
                day -> {
                    if(!transferFrequencyCheckBox(day).isSelected()){
                        transferFrequencyCheckBox(day).click();
                    }
                }
        );
    }

    /**
     * Verifies if GO BACK button cancels the deletion of the configuration
     */
    public void verifyGoBackButtonBehaviorWhenDeletingConnection(){
        editLink().click();
        deleteThisConfigurationLink().click();
        goBackButton().click();
        Assert.assertTrue("The GO back button does not send back to the edit configuration page and it does" +
                "not cancels the configuration deletion",deleteThisConfigurationLink().isDisplayed());
    }

    /**
     * Checks the check fingerprint to verify server checkboxes
     * @param selected
     */
    private void selectCheckFingerprintToVerifyServerCheckBox(String selected){
        if(selected.equalsIgnoreCase("yes") && !checkFingerprintToVerifyServerCheckBox().isSelected()){
            checkFingerprintToVerifyServerCheckBox().click();
        } else {
            if(selected.equalsIgnoreCase("no") && checkFingerprintToVerifyServerCheckBox().isSelected()){
                checkFingerprintToVerifyServerCheckBox().click();
            }
        }
    }

    /**
     * Confirms if a new fingerprint is detected
     */
    private void setUpNewFingerPrint(String action){
        if(action.equalsIgnoreCase("yes")){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(yesFingerPrintIsCorrectLinkLocator()));
            yesFingerPrintIsCorrectLink().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(sftpMainPageTitleLocator()));
        }
    }

    /**
     * Verifies the text of a success toast
     * @param expectedText
     */
    public void verifySuccessToastText(String expectedText){
        String actualText = successToast().getText();
        Assert.assertTrue(String.format("The toast text is not correct, expected: %s, actual: %s",
                expectedText,actualText), actualText.contains(expectedText));
    }

    /**
     * Gets the button: SET UP CONNECTION
     * @return WebElement
     */
    private WebElement setupConnectionButton(){
        return getDriver().findElement(setupConnectionButtonLocator());
    }

    /**
     * Gets the edit link
     * @return WebElement
     */
    private WebElement editLink(){
        return getDriver().findElement(editLinkLocator());
    }

    /**
     * Returns the delete this configuration link
     * @return WebElement
     */
    private WebElement deleteThisConfigurationLink(){
        return getDriver().findElement(By.cssSelector("a[class='_2MEy4XgNL-4iHq3oKYYQxQ']"));
    }

    /**
     * Gets the DELETE sftp configuration button
     * @return WebElement
     */
    private WebElement deleteSftpConfigurationButton(){
        return getDriver().findElement(By.cssSelector("button[class='ui teal button']"));
    }

    /**
     * Gets the setup configuration locator
     * @return By
     */
    private By setupConnectionButtonLocator(){
        return By.cssSelector("a[class='ui basic primary button _3cZsSOtK8zE89dYeekrtKo']");
    }

    /**
     * Gets the edit link locator
     * @return By
     */
    private By editLinkLocator(){
        return By.cssSelector("a[class='WNpDJtkdD8rjQ1bCvaie1'");
    }

    /**
     * Gets the sftp data transfer title link
     * @return
     */
    private WebElement sftpDataTransferTitleLink(){
        return getDriver().findElement(By.cssSelector("a[class='_3D0uXek_kVydbmkChhM1uQ']"));
    }

    /**
     * Returns the host text box
     * @return WebElement
     */
    private WebElement hostTextBox(){
        return getDriver().findElement(By.id("sftpHost"));
    }

    /**
     * Returns the port text box
     * @return WebElement
     */
    private WebElement portTextBox(){
        return getDriver().findElement(By.id("sftpPort"));
    }

    /**
     * Returns the path text box
     * @return
     */
    private WebElement pathTexBox(){
        return getDriver().findElement(By.id("sftpPath"));
    }

    /**
     * Gets the user name text box
     * @return WebElement
     */
    private WebElement userNameTextBox(){
        return getDriver().findElement(By.id("username"));
    }

    /**
     * Gets the password text box
     * @return WebElement
     */
    private WebElement passwordTextBox(){
        return getDriver().findElement(By.id("sftpPasswordText"));
    }

    /**
     * Gets the authentication method radio buttons
     * @param value of the radio button
     * @return WebElement
     */
    private WebElement authenticationMethodRadioButton(String value){
        return getDriver().findElement(By.xpath(String.format("//label[@class='nf4t6M28A37mub7uqflnI' and text()='%s']"
                ,value)));
    }

    /**
     * Gets the generate key button
     * @return WebElement
     */
    private WebElement generateKeyButton(){
        return getDriver().findElement(By.id("sftpGenerateKey"));
    }

    /**
     * Gets the transfer frequency check boxes
     * @param day to get the check box
     * @return WebElement
     */
    private WebElement transferFrequencyCheckBox(String day){
        return getDriver().findElement(By.cssSelector(String.format("input[name='%s']",day)));
    }

    /**
     * Gets the ip white listing text box
     * @return WebEElement
     */
    private WebElement ipWhiteListingTextBox(){
        return getDriver().findElement(By.cssSelector("p[class='_3cJxm0j4TQUyBiV8bKcNGN']"));
    }

    /**
     * Gets the check fingerprint to verify server checkbox
     * @return WebElement
     */
    private WebElement checkFingerprintToVerifyServerCheckBox(){
        return getDriver().findElement(By.id("fingerPrint"));
    }

    /**
     * Gets the test and save button
     * @return WebElement
     */
    private WebElement testAndSaveButton(){
        return getDriver().findElement(By.id("sftpSave"));
    }

    /**
     * Gets the ssh key text box locator
     * @return By
     */
    private By sshKeyTextBoxLocator(){
        return By.cssSelector("P[class='JJ-wa-vGPMQ0mhmOYrOrV']");
    }

    /**
     * Gets the ssh key text box
     * @return WebElement
     */
    private WebElement sshKeyTextBox(){
        return getDriver().findElement(sshKeyTextBoxLocator());
    }

    /**
     * Gets the generated ssh key message
     * @return WebElement
     */
    private WebElement generatedSshKeyMessage(){
        return getDriver().findElement(By.cssSelector("div[class='header LnXSRjbTz9l5Ksclle1N8']>div>p"));
    }

    /**
     * Gets the regenerate key button
     * @return WebElement
     */
    private WebElement reGenerateKeyButton(){
        return getDriver().findElement(By.id("sftpReGenerateKey"));
    }

    /**
     * Gets the GO BACK button
     * @return WebElement
     */
    private WebElement goBackButton(){
        return getDriver().findElement(By.xpath("//button/span[text()='Go Back']"));
    }

    /**
     * Gets the confirmation fingerprint link locator
     * @return By
     */
    private By yesFingerPrintIsCorrectLinkLocator(){
        return By.id("fingerprintConfirmationLink");
    }

    /**
     * ets the confirmation fingerprint link
     * @return WebElement
     */
    private  WebElement yesFingerPrintIsCorrectLink(){
        return getDriver().findElement(yesFingerPrintIsCorrectLinkLocator());
    }

    /**
     * Gets the success toast
     * @return WebElement
     */
    private WebElement successToast(){
        return getDriver().findElement(By.cssSelector(
                "div[class='ui small icon success message toast _2Z22tp5KKn_l5Zn5sV3zxY']"));
    }

    /**
     * Gets the sftp main page title
     * @return By
     */
    private By sftpMainPageTitleLocator(){
        return By.cssSelector("h1[class='ui header']");
    }

    /**
     * Gets the fingerprint label
     * @return WebElement
     */
    private WebElement fingeprintLabel(){
        return getDriver().findElement(By.cssSelector("p[class='_3chbcQvvoW6tHDNAT5g5gQ']"));
    }

    /**
     * Gets the disable server finger print verification link
     * @return WebElement
     */
    private WebElement disableServerFingerPrintVerificationLink(){
        return getDriver().findElement(By.id("fingerprintDisableLink"));
    }
}
