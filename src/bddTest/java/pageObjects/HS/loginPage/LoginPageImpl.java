package pageObjects.HS.loginPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

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

    public void openNonNavianceLoginPage(){

        load(GetProperties.get("hs.app.url"));
        waitUntilPageFinishLoading();

    }

    public void searchForHSInstitution(String institutionName,String institutionType){

        if(institutionType.equalsIgnoreCase("high school")){
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

        if(!institutionName.equalsIgnoreCase("Request new institution")){
            if(driver.findElement(By.xpath("//table[@id='institution-list']")).isDisplayed() &&  link(institutionName).isDisplayed()){
                logger.info("Results are displayed after the search");
                link(institutionName).click();
            }
            else{
                logger.info("Results are not displayed after the search");
            }
        }
        else{
            link(institutionName).click();
        }

        Assert.assertTrue("Institution Page is not loaded",text(institutionName).isDisplayed());
        link("Back to search").click();

    }

    public void clickNewUserBtn(){
        getNewUserBtn().click();
        waitUntilPageFinishLoading();
    }

    private WebElement getNewUserBtn(){

        return link("New User?");
    }

    private void openNavianceLoginPage() {
        load(GetProperties.get("naviance.app.url"));
        waitUntilPageFinishLoading();

    }
}
