package pageObjects.HUBS.FamilyConnection.Login;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by csackrider on 9/22/2015.
 */
public class Login extends PageObjectFacadeImpl {
    //public static WebDriver driver;
    private Logger logger;

    public Login() {
        logger = Logger.getLogger(Login.class);
    }

    public void DoFCLogin(String fcAccount, String strUserName, String strPassword) throws InterruptedException {
        PageFactory.initElements(driver, loginPage.class);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(GetProperties.get("hubs.base.url") + "/family-connection/" + fcAccount);

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("forgot your password")));

        loginPage.UserName.sendKeys(strUserName);

        loginPage.password.sendKeys(strPassword);

        loginPage.fc_signin_button.click();

        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.linkText("log out")));
    }
}
