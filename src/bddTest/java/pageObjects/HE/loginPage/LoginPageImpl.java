package pageObjects.HE.loginPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LoginPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

    private void openLoginPage(String url) {
        load(url);
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
        openLoginPage(GetProperties.get("he.app.url"));
        loginActions(username, password);
    }

    //Log in as an HE administrator
    public void defaultLogin(String usertype) {
        String username = GetProperties.get("he."+ usertype + ".username");
        String password = GetProperties.get("he."+ usertype + ".password");
        login(username, password);
    }

    /**
     * Login actions, fill textboxes and click on login button
     * @param username
     * @param password
     */
    private void loginActions(String username, String password){
        logger.info("Login into the HE app");
        usernameTextbox().sendKeys(username);
        logger.info("Using " + username + " as username");
        passwordTextbox().sendKeys(password);
        logger.info("Using " + password + " as password");
        loginButton().click();
        logger.info("Clicked the login button");
        waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> errorMessage = driver.findElements(By.cssSelector("div[class='ui negative message']"));
        if (errorMessage.size()==1){
            logger.info("Login failed. Invalid user or password.");
        }else {
            waitUntilElementExists(link(By.id("user-dropdown")));
            waitForUITransition();
        }
    }

    /**
     * Login into HE app given user type and url
     * @param usertype
     * @param urlKey
     */
    public void loginWithUrl(String usertype, String urlKey){
        String url = GetProperties.get("he."+ urlKey + ".url");
        deleteCookies();
        openLoginPage(url);
        String username = GetProperties.get("he."+ usertype + ".username");
        String password = GetProperties.get("he."+ usertype + ".password");
        loginActions(username, password);
    }

    private void deleteCookies(){
        try {
            getDriver().manage().deleteAllCookies();
        } catch (NoSuchSessionException nsse) {
            load("http://www.google.com");
        } catch (org.openqa.selenium.WebDriverException wde) {
            load("http://www.google.com");
        }
    }

    public void createNewUser() {
        openLoginPage(GetProperties.get("he.app.url"));
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
        openLoginPage(GetProperties.get("he.app.url"));
        String userName = GetProperties.get("he."+ userType + ".username");
        link("Forgot Password").click();
        textbox("E-Mail Address").sendKeys(userName);
        button("RESET PASSWORD").click();
        waitForUITransition();
    }

    public void processResetPassword(String userType, DataTable data) {
        waitForUITransition();
        String emailBody = "";
        GetProperties.setGmailAPIWait(120);     //Time unit is in seconds
        try {
            waitForUITransition();
            List<Email> emails = getGmailApi().getMessages(data);
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

    //verify the password policy of without the Special Characters
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
        waitForUITransition();
        waitUntilElementExists(driver.findElement(By.xpath("//button/span[text()='Login']")));
        Assert.assertTrue("Password was not Reset successful!", driver.findElement(By.xpath("//button/span[text()='Login']")).isDisplayed());
    }

    public void navigateToLoginScreenAndVerify() {
        openLoginPage(GetProperties.get("he.app.url"));
        verifyLoginPage();
    }

    public void verifyLoginPage() {
        Assert.assertTrue("\"Sign In\" button was found, but should not be there!", !button("sign in").isDisplayed());
        Assert.assertTrue("Intersect logo is not present!",driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("\"New user?\" link was not present, but should be!",link("New User?").isDisplayed());
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
        iamNotRobotChk();
        button("Request User").click();
        if(text("Your request has been submitted.").isDisplayed()){
                button("OK").click();
        }


    }


    public void navigateToRegistrationPage(){
        load(GetProperties.get("he.registration.url"));
        Assert.assertTrue("Registration page is not displayed",text("New User? Find Your Institution").isDisplayed());
    }

    public void searchForHEInstitution(String institutionName){ //,String institutionType){
        // This is no longer needed, as the app automatically sends you to the right URL.
        /*if(institutionType.contains("High School")){
            //button("High School Staff Member").click();
            WebElement search = driver.findElement(By.cssSelector("input[placeholder='Search Institutions...']"));
            waitUntilElementExists(search);
        }else{
            button("Higher Education Staff Member").click();
        }*/
        driver.findElement(By.cssSelector("input[class='prompt']")).sendKeys(institutionName);
        button("Search").click();
        //Not sure What it's doing thoses steps here?
//        WebElement searchResults = driver.findElement(By.xpath("//p/span[text()='No Institutions Found']"));
//        waitUntilElementExists(searchResults);
        while(button("More Institutions").isDisplayed()){
            button("More Institutions").click();
            waitUntilPageFinishLoading();
        }
        link(institutionName).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Institution Page is not loaded",text(institutionName).isDisplayed());
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
        openLoginPage(GetProperties.get("he.app.url"));
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

    public void searchForHEInstitutionWithInvalidData(String institutionName,String institutionType){

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
        try{
        if(link(institutionName).isEnabled()){
            link(institutionName).click();
            Assert.assertTrue("Institution Page is not loaded",text(institutionName).isDisplayed());
            link("please complete this form.").click();
        }
       else{
            Assert.assertTrue("Institution Page is not loaded",text("No Institutions Found").isDisplayed());

        }}
        catch (Exception e){}
        //link("Back to search").click();
        try{
            if(driver.findElement(By.xpath("//div/a/span[text()='Request new institution']")).isDisplayed())
            {
                link("Request new institution").click();
            }
          }catch(Exception e){}
    }

    public void validateFieldsInRequestUserForm(DataTable dataTable){
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
                    case "institutionName":
                        String institutionName = driver.findElement(By.name(key)).getAttribute("type");
                        Assert.assertTrue("Phone was not as expected.", institutionName.equals(individualField.get(key)));
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


    public void verifyCaptcha()
    {
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='recaptcha widget']")));
        Assert.assertTrue("I'm not a robot text is not displayed",driver.findElement(By.xpath("//label[@id='recaptcha-anchor-label']")).isDisplayed());
        Assert.assertTrue("inline block is not displayed",driver.findElement(By.xpath("//div[@class='rc-inline-block']")).isDisplayed());
        Assert.assertTrue("reCAPTCHA text is not displayed",driver.findElement(By.xpath("//div[text()='reCAPTCHA']")).isDisplayed());
        driver.switchTo().defaultContent();
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


    public void clickLinkInRegisterationPage(String linkToClick){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkToClick)));
        link(linkToClick).click();
    }

    public void validateRequestUserForm(DataTable dataTable){

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

    public void validateFieldsPresentInRequestUserForm() {
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

    public void verifyEmailNotification(String School,String Date,String Time,DataTable data){
        String emailBody = "";
        GetProperties.setGmailAPIWait(120);     //Time unit is in seconds
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
            Assert.fail("There was an error retrieving the email from Gmail.");
        }
        int SchoolLength = School.length();
        Integer codeMessageIndex = emailBody.indexOf("We have cancelled your college fair registration with ");
        String SchoolName = emailBody.substring(codeMessageIndex + 54,codeMessageIndex + 54 + SchoolLength);
        String CurrentDate = getSpecificEmailDate(Date);
        String currentYear[] = CurrentDate.split(" ");
        Integer yearIndex = emailBody.indexOf(currentYear[0]);
        String dateandTime = emailBody.substring(yearIndex+0,yearIndex+27);
        String getTimeValue[] = dateandTime.split(" at ");
        String DateValue = getTimeValue[0];
        String TimeValue = getTimeValue[1];
        Assert.assertTrue("School is not equal",SchoolName.equals(School));
        Assert.assertTrue("Date is not equal",CurrentDate.equals(DateValue));
        Assert.assertTrue("Time is not equal",Time.contains(TimeValue));
    }

    public String getSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getSpecificEmailDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    private void iamNotRobotChk() {
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        driver.findElement(By.xpath("//span[@id='recaptcha-anchor']")).click();
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
    }
    public void updateNavianceCollegeProfile(){
        updatebutton().click();

    }
    public void counselorCommunityWelcomePge(){

        communityFrame();

        assertTrue("Counselor Community Welcome Message is not displayed", driver.findElement(By.xpath("//h1[text()='Welcome to the Counselor Community!']")).isDisplayed());
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
    private WebElement updatebutton() { return driver.findElement(By.xpath("//a[text()='Update']"));}


    }

