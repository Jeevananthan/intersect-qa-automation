package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class LinksAndProfilesTabPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public LinksAndProfilesTabPageImpl() {
        logger = Logger.getLogger(LinksAndProfilesTabPageImpl.class);
    }

    public void clickLinks(){
        getLinksLink().click();
    }

    public void updateRequestInformationField(){
        String requestInformationFieldValue = getRequestInformationText().getAttribute("value");
        if (requestInformationFieldValue.contains("test/"))
            requestInformationFieldValue = requestInformationFieldValue.replace("test/", "");
        else
            requestInformationFieldValue = requestInformationFieldValue.concat("test/");
        getRequestInformationText().clear();
        getRequestInformationText().sendKeys(requestInformationFieldValue);
    }

    //Locators
    public WebElement getLinksLink(){ return driver.findElement(By.id("links-accordion-title")); }
    public WebElement getRequestInformationText(){ return driver.findElement(By.id("request-info-link-input")); }
}
