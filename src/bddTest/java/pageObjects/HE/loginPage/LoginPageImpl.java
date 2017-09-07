package pageObjects.HE.loginPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    public void enterDataInRequestUserForm(DataTable dataTable){

        List<Map<String,String>> fieldCollections = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> individualField : fieldCollections ) {
            for (String key : individualField.keySet()) {
                switch (key) {
                    case "firstName":
                         driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                    case "lastName":
                        driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                    case "email":
                        driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                    case "verifyEmail":
                        driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                    case "jobTitle":
                        driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                    case "authorizedToPostPublicInformation":
                        driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                    case "schedulesVisits":
                        driver.findElement(By.name(key)).sendKeys(individualField.get(key));
                        break;
                }
            }
        }

        Actions ob = new Actions(driver);
        ob.click(iamNotRobotChk());

        //driver.executeScript("arguments[0].click();",iamNotRobotChk());

        button("Request User").click();
        if(text("Your request has been submitted.").isDisplayed()){
                button("OK").click();
        }


    }

    public void verifyFields(DataTable dataTable)
    {
        //validating header of this page
        Assert.assertTrue("Header of this page doesnot contains 'Request User Account' text",text("Request User Account").isDisplayed());
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

        List<Map<String,String>> fieldCollections = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> individualField : fieldCollections ) {
            for (String key : individualField.keySet()) {
                switch (key) {
                    case "firstName":
                        String actualFirstName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("First Name was not as expected.", actualFirstName.contains(individualField.get(key)));
                        break;
                    case "lastName":
                        String actualLastName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Last Name was not as expected.", actualLastName.equals(individualField.get(key)));
                        break;
                    case "email":
                        String actualEmailAddress = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Work Email Address was not as expected.", actualEmailAddress.equals(individualField.get(key)));
                        break;
                    case "verifyEmail":
                        String actualPhone = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Phone was not as expected.", actualPhone.equals(individualField.get(key)));
                        break;
                    case "institutionName":
                        String actualInstitutionName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("InstitutionName was not as expected",actualInstitutionName.equals(individualField.get(key)));
                        break;
                    case "jobTitle":
                        String actualJobTitle = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("JobTitle was not as expected.", actualJobTitle.equals(individualField.get(key)));
                        break;
                    case "authorizedToPostPublicInformation":
                        String actualMessage = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Messages was not as expected.", actualMessage.equals(individualField.get(key)));
                        break;
                    case "schedulesVisits":
                        String actualSchedule = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("ActualSchedule was not as expected.", actualSchedule.contains(individualField.get(key)));
                        break;

                }
            }
        }

    }

    public void verifyCaptcha()
    {
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='recaptcha widget']")));
        Assert.assertTrue("I'm not a robot text is not displayed",driver.findElement(By.xpath("//label[@id='recaptcha-anchor-label']")).isDisplayed());
        Assert.assertTrue("inline block is not displayed",driver.findElement(By.xpath("//div[@class='rc-inline-block']")).isDisplayed());
        Assert.assertTrue("reCAPTCHA text is not displayed",driver.findElement(By.xpath("//div[text()='reCAPTCHA']")).isDisplayed());
        driver.switchTo().defaultContent();
    }

    public void searchForHEInstitution(String institutionName,String institutionType){

        if(institutionType.contains("High School")){
            button("High School Staff Member").click();
        }
        else{
            button("Higher Education Staff Member").click();
        }

        driver.findElement(By.cssSelector("input[class='prompt']")).sendKeys(institutionName);
        button("Search").click();

        while(button("More Institutions").isDisplayed()){
            button("More Institutions").click();
        }

        link(institutionName).click();
        Assert.assertTrue("Institution Page is not loaded",text(institutionName).isDisplayed());

        //link("Back to search").click();

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

    public void clickLinkInRegisterationPage(String linkToClick){
        link(linkToClick).click();
    }

    public void validateFieldsInRequestUserForm(DataTable dataTable){

        //validating header of this page
        Assert.assertTrue("Header of this page doesnot contains 'Request User Account' text",text("Request User Account").isDisplayed());
        //back - link validation
        Assert.assertTrue("Back option is not displayed",link("Back").isDisplayed());
        //Already have an account? -text validation
        Assert.assertTrue("'Already have an account?' text is not displayed",text("Already have an account?").isDisplayed());
        //Cancel -button validation
        Assert.assertTrue("'Cancel' button is not displayed",button("Cancel").isDisplayed());
        //Request User -button validation
        Assert.assertTrue("'Request User' button is not displayed",button("Request User").isDisplayed());

        List<Map<String,String>> fieldCollections = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> individualField : fieldCollections ) {
            for (String key : individualField.keySet()) {
                switch (key) {
                    case "firstName":
                        String actualFirstName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("First Name was not as expected.", actualFirstName.contains(individualField.get(key)));
                        break;
                    case "lastName":
                        String actualLastName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Last Name was not as expected.", actualLastName.equals(individualField.get(key)));
                        break;
                    case "email":
                        String actualEmailAddress = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Work Email Address was not as expected.", actualEmailAddress.equals(individualField.get(key)));
                        break;
                    case "verifyEmail":
                        String actualPhone = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Phone was not as expected.", actualPhone.equals(individualField.get(key)));
                        break;
                    case "jobTitle":
                        String actualSchoolInstitutionName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("School / Institution Name was not as expected.", actualSchoolInstitutionName.equals(individualField.get(key)));
                        break;
                    case "authorizedToPostPublicInformation":
                        String actualMessage = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Messages was not as expected.", actualMessage.equals(individualField.get(key)));
                        break;
                    case "schedulesVisits":
                        String actualSchedule = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("First Name was not as expected.", actualSchedule.contains(individualField.get(key)));
                        break;

                }
            }
        }

    }
    private WebElement iamNotRobotChk() {
        return driver.findElement(By.xpath("//span[@id='recaptcha-anchor']"));
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
