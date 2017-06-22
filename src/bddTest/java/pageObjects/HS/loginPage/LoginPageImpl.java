package pageObjects.HS.loginPage;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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

    private void openNavianceLoginPage() {
        load(GetProperties.get("naviance.app.url"));
        waitUntilPageFinishLoading();
    }
}
