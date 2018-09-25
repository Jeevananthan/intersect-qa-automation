package pageObjects.SM.diversity;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class DiversityPageImpl extends PageObjectFacadeImpl {

    public DiversityPageImpl() {
        logger = Logger.getLogger(DiversityPageImpl.class);
    }
    SearchPageImpl searchPage = new SearchPageImpl();

    private Logger logger;

    public void verifyDefaultTextInElement(String locator, String expectedText){
        String actualText = "";
        try {
            actualText = driver.findElement(By.cssSelector(locator)).getText();
        } catch (InvalidSelectorException e) {
            actualText = driver.findElement(By.xpath(locator)).getText();
        }
        Assert.assertTrue("The default text in the element is incorrect",
                actualText.equals(expectedText));
    }

    public void scrollToBottom(String locator) {
        WebElement textField = getDriver().findElement(By.xpath(locator));
        JavascriptExecutor js=driver;
        js.executeScript("arguments[0].scrollIntoView()", textField);
    }

    //Locators
}
