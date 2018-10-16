package pageObjects.SP.logHistoryPage;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class LogHistoryPageImpl extends PageObjectFacadeImpl {

    /**
     * Goes to the log history page
     */
    public void goToLogHistory(){
        viewLogHistoryLink().click();
        waitUntil(ExpectedConditions.visibilityOf(logHistoryTitleLabel()));
    }

    /**
     * Verifies if the given keys are displayed in an action changes record
     * @param action
     * @param dataTableKeys
     */
    public void verifyChangesKeys(String action, DataTable dataTableKeys){
        List<List<String>> keysList = dataTableKeys.raw();
        List<String> eventKeys = getChangesKeysFromAction(action);
        keysList.get(0).stream().forEach(
                key -> {
                    Assert.assertTrue(String.format("The key: %s is not displayed in the changes record",key)
                            ,eventKeys.contains(key));
                }
        );
    }

    /**
     * Gets the event keys of a given action
     * @param action
     * @return
     */
    private List<String> getChangesKeysFromAction(String action){
        List<WebElement> eventKeys = getDriver().findElements(changesKeyValueLocator(action));
        List<String> eventChangesKeys = new ArrayList<>();
        eventKeys.stream().forEach(
                eventKey -> {
                    eventChangesKeys.add(eventKey.getAttribute("innerText"));
                }
        );
        return eventChangesKeys;
    }

    /**
     * Gets the view log history link
     * @return
     */
    private WebElement viewLogHistoryLink(){
        return link("View Log History");
    }

    /**
     * Gets the changes key value locator
     * @param action
     * @return
     */
    private By changesKeyValueLocator(String action){
        return By.xpath(String.format(
                "//td[contains(.,'%s')]/following-sibling::td/div/div/span[@class='json-markup-key']",action));

    }

    /**
     * Gets the log history title label
     * @return WebElement
     */
    private WebElement logHistoryTitleLabel(){
        return getDriver().findElement(By.cssSelector("div[class='UDWEBAWmyRe5Hb8kD2Yoc']"));
    }
}
