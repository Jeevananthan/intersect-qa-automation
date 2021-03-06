package pageObjects.HE.requestPrimaryUserPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.loginPage.LoginPageImpl;

import java.util.Map;

public class RequestPrimaryUserPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    private LoginPageImpl loginPage = new LoginPageImpl();

    public RequestPrimaryUserPageImpl() {
        logger = Logger.getLogger(RequestPrimaryUserPageImpl.class);
    }

    public void requestNewFreemiumUser(String institution) {
        button("Higher Education Staff Member").click();
        searchForInstitution(institution);
    }

    public void requestNewInstitution(String institution) {
        textbox("Search Institutions...").sendKeys(institution);
        getSearchButton().click();
        waitUntilPageFinishLoading();
        link("Request new institution").click();
    }

    private void searchForInstitution(String institution){
        waitUntilPageFinishLoading();
        textbox("Search Institutions...").sendKeys(institution);
        WebElement results = getDriver().findElement(By.id("global-search-box-results"));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("search-box-item-0")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'"+institution+"')]")));
        results.findElement(By.id("search-box-item-0")).click();
        waitUntilPageFinishLoading();
        if (driver.findElements(By.className("primary-user")).size() > 0) {
            Assert.assertTrue("\"To request an account for your institution\" Text is displaying.", text("To request an account for your institution").isDisplayed());
        } else {
            Assert.assertTrue("\"Review the details above to confirm this is your institution. To become the primary user for your institution, please complete this form..\" Text is displaying.", text("Review the details above to confirm this is your institution").isDisplayed());
        }
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText("please complete this form.")));
        link("please complete this form.").click();
    }

    public void fillFormAndVerifyMessaging(DataTable dataTable){
        //validating header of this page
        String test = text("Institution Name").findElement(By.xpath(".//parent::label/following-sibling::*"))
                .getAttribute("class");
        if (!text("Institution Name").findElement(By.xpath(".//parent::label/following-sibling::*"))
                .getAttribute("class").contains("disabled")) {
            Assert.assertTrue("Header of this page does not contain 'Request New Institution' text",
                    text("Request New Institution").isDisplayed());
        }
        if (text("Institution Name").findElement(By.xpath(".//parent::label/following-sibling::*"))
                .getAttribute("class").contains("disabled")) {
            Assert.assertTrue("Header of this page does not contain 'Request User Account' text",
                    text("Request User Account").isDisplayed());
        }
        //back - link validation
        Assert.assertTrue("Back option is not displayed",link("Back").isDisplayed());
        //Already have an account? -text validation
        Assert.assertTrue("'Already have an account?' text is not displayed",text("Already have an account?").isDisplayed());
        //Sign In - button validation
        Assert.assertTrue("Sign In is not displayed",button("Sign In").isDisplayed());
        //Cancel -button validation
        Assert.assertTrue("'Cancel' button is not displayed",button("Cancel").isDisplayed());
        //Request User -button validation
        Assert.assertTrue("'Request User' button is not displayed",button("Request User").isDisplayed());
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for (String field : data.keySet()){
            textbox(field).sendKeys(data.get(field));
        }
        button("REQUEST USER").click();
        Assert.assertTrue("\"Please complete all required fields.\" message is not displayed",text("Please complete all required fields.").isDisplayed());
        Assert.assertTrue("\"Email must match\" message is not displayed",text("Email must match").isDisplayed());
        Assert.assertTrue("\"Please enter a value\" message is not displayed",text("Please enter a value").isDisplayed());
        Assert.assertTrue("\"Reason Field : Please enter a value\" message is not displayed",text("Please enter a value").isDisplayed());
        Assert.assertTrue("\"Please verify that you are not a robot\" message is not displayed",text("Please verify that you are not a robot").isDisplayed());
    }

    public void fillFormAndSubmit(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String field : data.keySet()) {
            switch (field) {
                case "Are you authorized to post public information about your institution?":
                    if (data.get(field).equalsIgnoreCase("Yes")) {
                        WebElement hiddenWebElement = driver.findElement(By.cssSelector("[name='authorizedToPostPublicInformation']"));
                        driver.executeScript("arguments[0].click()",hiddenWebElement);
                    }
                    break;
                case "Do you schedule visits to high schools?":
                    if (data.get(field).equalsIgnoreCase("Yes")) {
                        WebElement hiddenWebElement = driver.findElement(By.cssSelector("[name='schedulesVisits']"));
                        driver.executeScript("arguments[0].click()",hiddenWebElement);
                    }
                    break;
                case "EU Citizen":
                    setEuCitizen(data.get(field));
                    break;
                default:
                    textbox(field).sendKeys(data.get(field));
            }
        }
        agreeIntersectPolicy();
        agreeIntersectTermsOfUse();
        clickReCaptcha();
        button("REQUEST USER").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(requestConfirmation()));
        Assert.assertTrue("Confirmation message was not displayed!", text("Your request has been successfully submitted. You can expect a response from Hobsons within 1 business day.").isDisplayed());
        button("OK").click();
        loginPage.verifyLoginPage();
    }

    private void clickReCaptcha(){
        getDriver().switchTo().frame(driver.findElement(By.tagName("iframe")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.className("recaptcha-checkbox")));
        waitUntil(ExpectedConditions.elementToBeClickable(By.className("recaptcha-checkbox")));
        checkbox(By.className("recaptcha-checkbox")).click();
        waitForUITransition();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='recaptcha-checkbox-checkmark']")));
        getDriver().switchTo().defaultContent();
        waitUntilPageFinishLoading();
    }

    /**
     * Clicks on the agree Intersect policy checkbox
     */
    public void agreeIntersectPolicy() {
        WebElement hiddenWebElement = driver.findElement(By.id("request-he-user-agree-privacy"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",hiddenWebElement);
    }

    /**
     * Clicks on the agree Intersect terms of use checkbox
     */
    public void agreeIntersectTermsOfUse() {
        WebElement hiddenWebElement = driver.findElement(By.id("request-he-user-agree-termsofuse"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",hiddenWebElement);
    }


    /***
     * Sets the eu citizen radio button
     * @param euCitizen, to be set, it can be Yes or No
     */
    public void setEuCitizen(String euCitizen){
        WebElement euCitizenRadioButton = driver.findElement(By.xpath(String.format(
                "//div[@id='request-he-user-isEuCitizen']//input[contains(@id,'%s')]",
                euCitizen.toLowerCase())));
        jsClick(euCitizenRadioButton);
    }

    private WebElement getSearchButton() {
        return button("SEARCH");
    }

    private By requestConfirmation(){
        return By.xpath("//span[text()='Your request has been submitted.']");
    }
}
