package pageObjects.HS.loginPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.nio.file.Watchable;
import java.util.Set;

public class LoginPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public LoginPageImpl() {
        logger = Logger.getLogger(pageObjects.HE.loginPage.LoginPageImpl.class);
    }

    public void loginThroughNaviance(String account, String username, String password) {
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
        openNavianceLoginPage();
        textbox(By.name("hsid")).sendKeys(account);
        textbox(By.name("username")).sendKeys(username);
        textbox(By.name("password")).sendKeys(password);
        button("Sign In").click();
        waitUntilPageFinishLoading();
        link(By.cssSelector("[title='Counselor Community']")).click();
        Set<String> windows = driver.getWindowHandles();
        for (String thisWindow : windows) {
            if (!thisWindow.equals(navianceWindow)){
                intersectWindow = thisWindow;
            }
        }
        driver.close();
        driver.switchTo().window(intersectWindow);
        waitUntilPageFinishLoading();
    }

    private void openNavianceLoginPage() {
        load(GetProperties.get("naviance.app.url"));
        waitUntilPageFinishLoading();

    }


    public void verifyHSAddressPage(String schoolName,String navianceOrNonnaviance,String address){

        Assert.assertTrue("High School name is not displayed", text(schoolName).isDisplayed());
        Assert.assertTrue("High School address is not displayed", driver.findElement(By.xpath("//span[text()='"+address+"']")).isDisplayed());

        if(navianceOrNonnaviance.equalsIgnoreCase("naviance")){
            Assert.assertTrue("'access counselor community' text is not displayed for naviance HS", driver.findElement(By.xpath("//span[contains(text(),'Please access the Counselor Community via')]")).isDisplayed());
            Assert.assertTrue("'naviance' link is not displayed for naviance HS", link("Naviance").isDisplayed());
            Assert.assertTrue("'sign in ' button is displayed for naviance HS", !button("Sign In").isDisplayed());
            Assert.assertTrue("",driver.findElement(By.xpath("//span[text()='Back to search']")).isDisplayed());
            try{
                if(!driver.findElement(By.xpath("//span[contains(text(),'Primary User')]")).isDisplayed())
                {
                    logger.info("Primary user details is not displayed");
                }else{logger.info("Primary user details is displayed");}
            }catch(Exception e){}
        }
        else{
            Assert.assertTrue("'sign in ' button is not displayed for non-naviance HS", button("Sign In").isDisplayed());
            Assert.assertTrue("'Already have an account? ' text is not displayed for non-naviance HS", text("Already have an account?").isDisplayed());
            Assert.assertTrue("'please complete this form' link is not displayed for non-naviance HS", link("please complete this form.").isDisplayed());
            Assert.assertTrue("Back to search is not displayed",link("Back to search").isDisplayed());
            try{
                if(!driver.findElement(By.xpath("//span[contains(text(),'Primary User')]")).isDisplayed())
                {
                    logger.info("Primary user details is not displayed");
                }else{logger.info("Primary user details is displayed");}
            }catch(Exception e){}

        }

    }

    public void navaigateToRegistration()
    {
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();
        link("New User?").click();
    }

    public void verifyInstitutionPage()
    {
        Assert.assertTrue("High School Staff Member is not displayed",button("High School Staff Member").isDisplayed());
        Assert.assertTrue("Higher Education Staff Member is not displayed",button("Higher Education Staff Member").isDisplayed());
        Assert.assertTrue("Sign In button is not displayed",button("Sign In").isDisplayed());
        Assert.assertTrue("text is not displayed",text("New User? Find Your Institution").isDisplayed());
        Assert.assertTrue("textbox is not displayed",driver.findElement(By.xpath("//input[@placeholder='Search Institutions...']")).isDisplayed());

    }
    public void searchInstitution(String school)
    {
        waitUntilElementExists(highSchoolButton());
        driver.findElement(By.xpath("//input[@placeholder='Search Institutions...']")).clear();
        driver.findElement(By.xpath("//input[@placeholder='Search Institutions...']")).sendKeys(school);
        button("Search").click();
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//a[text()='"+school+"']")).isDisplayed());
        driver.findElement(By.xpath("//a[text()='"+school+"']")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyLink(String navianceORnonNavianceLink)
    {
       Assert.assertTrue("Link is not displayed",link(navianceORnonNavianceLink).isDisplayed());
        link(navianceORnonNavianceLink).click();
        waitUntilPageFinishLoading();
    }

    private void openHSLoginPage() {
        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();
    }

    public void login(String username, String password) {
        openHSLoginPage();
        logger.info("Login into the HS app");
        textbox(By.name("username")).sendKeys(username);
        logger.info("Using " + username + " as username");
        textbox(By.name("password")).sendKeys(password);
        logger.info("Using " + password + " as password");
        button("Login").click();
        logger.info("Clicked the login button");
        waitUntilPageFinishLoading();
    }

    public void verifyHSPage() {
        Assert.assertTrue("Username field is not displayed", textbox(By.name("username")).isDisplayed());
        Assert.assertTrue("Password field is not displayed", textbox(By.name("password")).isDisplayed());
        Assert.assertTrue("Intersect logo is not displayed", driver.findElement(By.cssSelector("[src=\"https://static.intersect.hobsons.com/images/intersect-tm-by-hobsons-rgb-gray-teal.png\"]")).isDisplayed());
        Assert.assertTrue("Login button is not displayed", button("Login").isDisplayed());
    }

    private WebElement highSchoolButton()
    {
        WebElement highSchool=button("High School Staff Member");
        waitUntilElementExists(highSchool);
        return highSchool;
    }
}
