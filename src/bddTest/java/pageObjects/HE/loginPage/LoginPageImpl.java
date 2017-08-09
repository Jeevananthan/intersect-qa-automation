package pageObjects.HE.loginPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

public class LoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

    private void openLoginPage() {
        load(GetProperties.get("he.app.url"));
        // If a previous test fails, we'll still have an open session.  Log out first.
        if (button(By.id("user-dropdown")).isDisplayed()) {
            button(By.id("user-dropdown")).click();
            button(By.id("user-dropdown-signout")).click();
            waitUntilPageFinishLoading();
        }
        //No longer needed
        //link("Go To Authorized Page").click();
    }

    public void login(String username, String password) {
        openLoginPage();

        logger.info("Login into the HE app");
        usernameTextbox().sendKeys(username);
        logger.info("Using " + username + " as username");
        passwordTextbox().sendKeys(password);
        logger.info("Using " + password + " as password");
        loginButton().click();
        logger.info("Clicked the login button");
        waitUntilPageFinishLoading();
    }

    //Log in as an HE administrator
    public void defaultLogin(String usertype) {
        openLoginPage();
        String username = GetProperties.get("he."+ usertype + ".username");
        String password = GetProperties.get("he."+ usertype + ".password");
        logger.info("Logging into the HE app");
        usernameTextbox().sendKeys(username);
        passwordTextbox().sendKeys(password);
        logger.info("Sending credentials - " + username + ":" + password);
        loginButton().click();
        waitUntilElementExists(link(By.id("user-dropdown")));
        waitUntilPageFinishLoading();
    }

    public void createNewUser() {
        openLoginPage();
        link("New User?").click();
        waitUntilPageFinishLoading();
    }

    public void failedLogin(String userType) {
        attemptLogin(userType,"Login failed. Invalid user or password.");
    }

    public void userLockedOut(String userType) {
        attemptLogin(userType,"This user has been locked due to excessive invalid password attempts.");
    }

    public void beginResetPassword(String userType) {
        openLoginPage();
        String userName = GetProperties.get("he."+ userType + ".username");
        link("Forgot Password").click();
        textbox("E-Mail Address").sendKeys(userName);
        button("RESET PASSWORD").click();


    }

    public void processResetPassword(String userType, DataTable data) {
        String emailBody = "";
        GetProperties.setGmailAPIWait(60);     //Time unit is in seconds
        try {
            List<Email> emails = getGmailApi().getMessages(data);

            boolean verified = false;
            for (Email email : emails) {
                System.out.print(email.toString());
                emailBody = email.getBody();
            }
        } catch (Exception e) {
            logger.info("Exception while retrieving Gmail messages: " + e.getMessage());
            e.printStackTrace();
            fail("There was an error retrieving the password reset email from Gmail.");
        }
        Integer codeMessageIndex = emailBody.indexOf("Your password reset verification code is ");
        String code = emailBody.substring(codeMessageIndex + 41,codeMessageIndex + 47);
        logger.info("Verification code is: " + code + "\n");
        textbox("Verification Code").sendKeys(code);
        textbox("New Password").sendKeys(GetProperties.get("he."+ userType + ".password"));
        textbox("Confirm Password").sendKeys(GetProperties.get("he."+ userType + ".password"));
        button("CHANGE PASSWORD").click();
        Assert.assertTrue("Password reset was not successful!", button("LOGIN").isDisplayed());
    }

    public void verifyLoginScreen() {
        openLoginPage();
        Assert.assertTrue("\"Sign In\" button was found, but should not be there!", !button("sign in").isDisplayed());
        Assert.assertTrue("Intersect logo is not present!",driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("\"New user?\" link was not present, but should be!",link("New User?").isDisplayed());
    }

    public void navigateToRegistrationPage(){
        load(GetProperties.get("he.registration.url"));
        Assert.assertTrue("Registration page is not displayed",text("New User? Find Your Institution").isDisplayed());
    }

    public void searchForHEInstitution(String institutionName,String institutionType){
        if(institutionType.contains("High School")){
            button("High School Staff Member").click();
        }else{
            button("Higher Education Staff Member").click();
        }
        driver.findElement(By.cssSelector("input[class='prompt']")).sendKeys(institutionName);
        button("Search").click();

        while(button("More Institutions").isDisplayed()){
            button("More Institutions").click();
        }
        link(institutionName).click();
        Assert.assertTrue("Institution Page is not loaded",text(institutionName).isDisplayed());
    }

    public void validateFieldsInRequestUserForm() {
        //validating header of this page
        Assert.assertTrue("Header of this page doesnot contains 'Request User Account' text", text("Request New Institution").isDisplayed());
        Assert.assertTrue("Message 'Answer all fields below to complete your request. You can expect a response from Hobsons within 1 business day.' is not displayed",driver.findElement(By.xpath("//div/span[text()='Answer all fields below to complete your request. You can expect a response from Hobsons within 1 business day.']")).isDisplayed());
        Assert.assertTrue("Back option is not displayed", link("Back").isDisplayed());
        //Validating the Fields
        Assert.assertTrue("First Name Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='firstName' and @type='text']")).isDisplayed());
        Assert.assertTrue("Last Name Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='lastName' and @type='text']")).isDisplayed());
        Assert.assertTrue("Email Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='email' and @type='email']")).isDisplayed());
        Assert.assertTrue("Verify Email Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='verifyEmail' and @type='email']")).isDisplayed());
        Assert.assertTrue("Institution Name Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='institutionName' and @type='text']")).isDisplayed());
        Assert.assertTrue("Institution Website Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='institutionWebsite' and @type='text']")).isDisplayed());
        Assert.assertTrue("jobTitle Textbox was not as displayed", driver.findElement(By.xpath("//input[@name='jobTitle' and @type='text']")).isDisplayed());
        Assert.assertTrue("'Cancel' button is not displayed", button("Cancel").isDisplayed());
        Assert.assertTrue("'Request User' button is not displayed", button("Request User").isDisplayed());
    }

    public void goToAppropriateRegistrationpage(String buttonName){

        Assert.assertTrue(buttonName+" button is not displayed in the registration page",button(buttonName).isDisplayed());
        button(buttonName).click();

        //verifying the text 'Already have an account'
        Assert.assertTrue("'Already have an account' text is not displayed in the registration page",text("Already have an account?").isDisplayed());
        //verifying 'Sign in' button exists in registration page
        Assert.assertTrue("'Sign in'  button is not displayed in the registration page",button("Sign In").isDisplayed());

        if(buttonName.contains("Higher Education")){
            String ActualText = driver.findElement(By.xpath("//div[@class='sixteen wide computer sixteen wide mobile sixteen wide tablet column']//p[3]")).getText();
            String line1 = "Please note there can be multiple free accounts per higher education institution but only one of these";
            String line2 = "accounts can be the primary user. The primary user account should be the main point of contact for";
            String line3 = "your admissions office.";

            Assert.assertTrue("Appropriate text is not displayed in the registration page",ActualText.contains(line1));
            Assert.assertTrue("Appropriate text is not displayed in the registration page",ActualText.contains(line2));
            Assert.assertTrue("Appropriate text is not displayed in the registration page",ActualText.contains(line3));
        }
        else{
            String ActualText = driver.findElement(By.xpath("//div[@class='sixteen wide computer sixteen wide mobile sixteen wide tablet column']//p[3]")).getText();
            String line1 = "Please note there can be multiple free accounts per high school but only one of these accounts can ";
            String line2 = "be the primary user. The primary user account should be the main point of contact for your guidance";
            String line3 = " office.";

            Assert.assertTrue("Appropriate text is not displayed in the registration page",ActualText.contains(line1));
            Assert.assertTrue("Appropriate text is not displayed in the registration page",ActualText.contains(line2));
            Assert.assertTrue("Appropriate text is not displayed in the registration page",ActualText.contains(line3));

        }
    }

    private void attemptLogin(String userType, String errorMessage) {
        openLoginPage();
        String username = GetProperties.get("he."+ userType + ".username");
        String password = GetProperties.get("he."+ userType + ".password");
        waitUntilPageFinishLoading();
        logger.info("Attempting to log into the HE app");
        usernameTextbox().sendKeys(username);
        passwordTextbox().sendKeys(password);
        logger.info("Sending credentials - " + username + ":" + password);
        loginButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Login error message is not displayed as expected!\nExpected: "+errorMessage+"\n",text(errorMessage).isDisplayed());
    }

    private WebElement usernameTextbox() {
        return textbox("E-mail Address");
    }

    private WebElement passwordTextbox() {
        return textbox("Password");
    }

    private WebElement loginButton() {
        return button("Login");
    }

    private GmailAPI getGmailApi() throws Exception { return new GmailAPI(); }
}
