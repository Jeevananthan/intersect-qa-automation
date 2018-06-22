package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

public class HUBSLoginPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HUBSLoginPageImpl() {
        logger = Logger.getLogger(HUBSLoginPageImpl.class);
    }

    public void login(final String username, final String password) {
        open();
        usernameField().sendKeys(username);
        passwordField().sendKeys(password);
        loginButton().click();
    }

    public void loginInternational(final String username, final String password) {
        openInternational();
        usernameField().sendKeys(username);
        passwordField().sendKeys(password);
        loginButton().click();
    }

    public void defaultLogIn(List<String> creds) {
        String username = creds.get(0);
        String password = creds.get(1);
        login(username,password);
        waitUntilPageFinishLoading();
    }

    public void internationalLogIn(List<String> creds) {
        String username = creds.get(0);
        String password = creds.get(1);
        loginInternational(username,password);
        waitUntilPageFinishLoading();
    }

    private void open(){
        driver.close();
        load(GetProperties.get("hubs.app.url"));
    }
    private void openInternational() { load(GetProperties.get("hubs.app.int.url")); }


    //Locators

    private WebElement usernameField() {
        return textbox(By.name("username"));
    }
    private WebElement passwordField() {
        return textbox(By.name("password"));
    }
    private WebElement loginButton() {
        return button(By.cssSelector("input.yellow-button"));
    }
}
