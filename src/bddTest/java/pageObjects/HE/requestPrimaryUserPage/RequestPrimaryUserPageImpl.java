package pageObjects.HE.requestPrimaryUserPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        searchForInstitution(institution);
    }

    public void updatePrimaryFreemiumUser(String institution) {
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
        results.findElement(By.id("search-box-item-0")).click();
        waitUntilPageFinishLoading();
        if (driver.findElements(By.className("primary-user")).size() > 0) {
            Assert.assertTrue("\"To request an account for your institution\" Text is displaying.", text("To request an account for your institution").isDisplayed());
        } else {
            Assert.assertTrue("\"Review the details above to confirm this is your institution. To become the primary user for your institution, please complete this form..\" Text is displaying.", text("Review the details above to confirm this is your institution").isDisplayed());
        }
        link("please complete this form.").click();
    }

    public void fillFormAndVerifyMessaging(DataTable dataTable){
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

    public void fillFormAndSubmit(DataTable dataTable){
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for (String field : data.keySet()){
            if(field.equals("Reason")){
                String actPlaceHolderText = getDriver().findElement(By.id("change-primary-user-reason")).getAttribute("placeholder");
                String expPlaceHolderText = "Provide a brief explanation why your institution's primary user needs updated.";
                Assert.assertTrue("Reason field Place Holder Text is not proper", expPlaceHolderText.equals(actPlaceHolderText));
            }
            textbox(field).sendKeys(data.get(field));
        }
        clickReCaptcha();
        button("REQUEST USER").click();
        Assert.assertTrue("Confirmation message was not displayed!",text("Your request has been successfully submitted. You can expect a response from Hobsons within 1 business day.").isDisplayed());
        button("OK").click();
        loginPage.verifyLoginPage();
    }

    private void clickReCaptcha(){
        getDriver().switchTo().frame(driver.findElement(By.tagName("iframe")));
        checkbox(By.className("recaptcha-checkbox")).click();
        getDriver().switchTo().defaultContent();
        waitUntilPageFinishLoading();
    }

    private WebElement getSearchButton() {
        return button("SEARCH");
    }
}
