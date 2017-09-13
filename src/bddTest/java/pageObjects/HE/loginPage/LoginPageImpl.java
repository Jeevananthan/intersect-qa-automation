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
    //verify the password policy of minimum of 8 characters
        textbox("New Password").sendKeys("word!1");
        textbox("Confirm Password").sendKeys("word!1");
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements']")).isDisplayed());
    //verify the password policy of maximum of 30 characters
        textbox("New Password").sendKeys("PasswordPolicyMaximum30characters#1");
        textbox("Confirm Password").sendKeys("PasswordPolicyMaximum30characters#1");
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements']")).isDisplayed());
    //verify the password policy of lowercase letter
        textbox("New Password").sendKeys("password#1");
        textbox("Confirm Password").sendKeys("password#1");
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements']")).isDisplayed());
    //verify the password policy of uppercase letter
        textbox("New Password").sendKeys("PASSWORD#1");
        textbox("Confirm Password").sendKeys("PASSWORD#1");
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements']")).isDisplayed());
    //verify the password policy of without the number
        textbox("New Password").sendKeys("Password#*");
        textbox("Confirm Password").sendKeys("Password#*");
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements']")).isDisplayed());
    //verify the password policy of without the Special Charecters
        textbox("New Password").sendKeys("Password1");
        textbox("Confirm Password").sendKeys("Password1");
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements']")).isDisplayed());
        //verify the password accepted with the password policy
        textbox("New Password").sendKeys(GetProperties.get("he."+ userType + ".password"));
        textbox("Confirm Password").sendKeys(GetProperties.get("he."+ userType + ".password"));
        button("CHANGE PASSWORD").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Password reset was not successful!", button("LOGIN").isDisplayed());
    }

    public void verifyLoginScreen() {
        openLoginPage();
        Assert.assertTrue("\"Sign In\" button was found, but should not be there!", !button("sign in").isDisplayed());
        Assert.assertTrue("Intersect logo is not present!",driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("\"New user?\" link was not present, but should be!",link("New User?").isDisplayed());
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

    public void enterDummyVerificationCode() {
//
       driver.findElement(By.xpath("//input[@name='verification']")).sendKeys("12345");


    }

    public void enterInvalidPassword(String invalidPassword){

        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(invalidPassword);
        driver.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys(invalidPassword);
        button("CHANGE PASSWORD").click();
        validatePasswordFailureText();

    }

    public void validatePasswordFailureText(){

        Assert.assertTrue("'Password Failed' warning message is not displayed ", driver.findElement(By.xpath("//span[text()='Password failed to satisfy security requirements.']")).isDisplayed());

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
