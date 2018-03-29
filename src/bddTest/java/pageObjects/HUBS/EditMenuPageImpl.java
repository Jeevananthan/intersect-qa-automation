package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class EditMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    List<String> tabs = new ArrayList<>();

    public EditMenuPageImpl() {
        logger = Logger.getLogger(EditMenuPageImpl.class);
    }

    public void clickEditMenuButton(String label) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='editor__page ng-scope']"), 1));
        switch (label) {
            case "Studies" : studiesButton().click();
                break;
            case "Student Life" : studentLifeButton().click();
                break;
            case "Overview" : overviewButton().click();
                break;
            case "International" : internationalButton().click();
                break;
            case "Costs" : costsButton().click();
                break;
            case "Admissions" : admissionsButton().click();
                break;
        }
        logger.info(label + " button was clicked");
    }

    public void verifyTermsOfService(DataTable data) {
        List<String> dataStrings = data.asList(String.class);
        getDriver().switchTo().defaultContent();
        for (String dataElement : dataStrings) {
            link(dataElement).click();
            tabs = new ArrayList<String>(getDriver().getWindowHandles());
            getDriver().switchTo().window(tabs.get(1));
            assertTrue("The 'Terms of Service' page is not correctly displayed", termsOfUsePageLocator().isDisplayed());
            getDriver().switchTo().window(tabs.get(1));
            getDriver().close();
            getDriver().switchTo().window(tabs.get(0));
        }
    }

    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//li[text()='Studies']"));
    }
    private WebElement studentLifeButton() { return getDriver().findElement(By.xpath("//li[text()='Student Life']")); }
    private WebElement overviewButton() { return getDriver().findElement(By.xpath("//li[text()='Overview']")); }
    private WebElement internationalButton() { return getDriver().findElement(By.xpath("//li[text()='International']")); }
    private WebElement costsButton() { return getDriver().findElement(By.xpath("//li[text()='Costs']")); }
    private WebElement admissionsButton() { return getDriver().findElement(By.xpath("//li[text()='Admissions']")); }
    private WebElement termsOfUsePageLocator() { return getDriver().findElement(By.cssSelector("p:nth-of-type(1) span")); }
}
