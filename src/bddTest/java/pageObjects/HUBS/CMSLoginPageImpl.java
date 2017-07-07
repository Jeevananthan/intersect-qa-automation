package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

public class CMSLoginPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public CMSLoginPageImpl() {
        logger = Logger.getLogger(CMSLoginPageImpl.class);
    }

    public void login(final String username, final String password) {
        open();
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

    private void open(){
        load(GetProperties.get("cms.app.url"));
    }

    //Locators

    private WebElement usernameField() {
        return textbox(By.id("edit-name"));
    }
    private WebElement passwordField() {
        return textbox(By.id("edit-pass"));
    }
    private WebElement loginButton() {
        return button(By.id("edit-submit"));
    }
}
