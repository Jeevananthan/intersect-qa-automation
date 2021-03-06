package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.GetProperties;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HEHSCommonImpl extends PageObjectFacadeImpl {

    public void verifySubtabsInNotificationsPage(String tab,String option,String user,DataTable dataTable){
        List<String> tabs = dataTable.asList(String.class);
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        if(user.equals("premium")||user.equals("limited")){
            notification().click();
        }else if(user.equals("naviance")||user.equals("non-naviance")){
            notificationsAndTasks().click();
        }
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='menu-link active']/span[text()='Requests']"),1));
        if(user.equals("premium")||user.equals("limited")||user.equals("naviance")||user.equals("non-naviance")){
            for(String subtab:tabs){
                Assert.assertTrue("Requests subtab is not displayed",driver.findElement(By.xpath("//a[@class='menu-link active']/span[text()='"+subtab+"'] | //a[contains(@class, 'menu-link')]/span[text()='" + subtab + "']")).isDisplayed());
            }
        }else {
            Assert.fail("Invalid option");
        }
        List<WebElement> Tab = driver.findElements(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='" + tab + "']"));
        if(option.equals("displaying")){
            Assert.assertTrue("SubTab is not displayed",Tab.size()==1);
        }else if(option.equals("not displaying")){
            Assert.assertTrue("SubTab is displayed",Tab.size()==0);
        }else {
            Assert.fail("Invalid option");
        }
    }

    public void navigateToURL(String URL){
        //Commenting the below line to increase the performance
        waitUntilPageFinishLoading();
        load(GetProperties.get("he.app.url")+ URL);
        waitUntilPageFinishLoading();
    }

    public void verifyColumnHeaders(String locator, DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        List<WebElement> columns = getTable(locator).findElements(By.cssSelector("tr th"));
        for (int i=0; i < details.size(); i++) {
            Assert.assertEquals("Column names are different", details.get(i), columns.get(i).getText());
        }
    }

    public void clickMenuLink(String text) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(getMenuLinkLocator(text))));
        getMenuLink(text).click();
    }

    public void clickMenuTab(String text) {
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath(getMenuTabLocator(text))));
        getMenuTab(text).click();
    }

    public void checkCheckboxFirsRow(String checkboxText) {
        waitUntilPageFinishLoading();
        if (checkboxText.equals("Enabled")) {
            if (!getEnabledCheckbox().getAttribute("class").contains("checked")) {
                getEnabledCheckbox().click();
            }
        } else if (checkboxText.equals("Use Default Filter Values")) {
            if (!getUseDefaultFilterCheckbox().getAttribute("class").contains("checked")) {
                getUseDefaultFilterCheckbox().click();
            }
        }
    }

    public void uncheckCheckboxFirsRow(String checkboxText) {
        if (checkboxText.equals("Enabled")) {
            if (getEnabledCheckbox().getAttribute("class").contains("checked")) {
                getEnabledCheckbox().click();
            }
        } else if (checkboxText.equals("Use Default Filter Values")) {
            if (getUseDefaultFilterCheckbox().getAttribute("class").contains("checked")) {
                getUseDefaultFilterCheckbox().click();
            }
        }
    }

    public void setDefaultValue(String value, String defaultValueId) {
        getDefaultFilterValueBox(defaultValueId).sendKeys(value);
    }

    public void clearDefaultFIlterValue(String defaultValueId) {
        getDefaultFilterValueBox(defaultValueId).sendKeys("0");
        getDefaultFilterValueBox(defaultValueId).clear();
    }

    public void verifyFilterValue(String filterName, String expectedValue) {
        //Commenting the below line to increase the performance
       // waitUntilPageFinishLoading();
        softly().assertThat(getFilterValueFirstRow(filterName).getText().equals(expectedValue));
    }

    public void setValue(String value, String valueId) {
        getFilterValueFirstRow(valueId).sendKeys(value);
    }

    public void clearFilterValue(String valueId) {
        getFilterValueFirstRow(valueId).clear();
    }

    public void checkThereIsNoText(String text) {
        softly().assertThat(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[text()='" + text + "']")));
    }

    public void pickFromTHeMenuItems(String menuItem) {
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id(getMenuItemById(menuItem))));
            getDriver().findElement(By.id(getMenuItemById(menuItem))).click();
    }

    public void waitForSuccessMessage(String message){
        waitUntil(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//*[text()='" +message + "']"))));
    }

    public void submitButton(String button) {
        getDriver().findElement(By.xpath("//*[text()='" + button + "']")).submit();
        waitUntilPageFinishLoading();
    }

    public void clickOnCloseIcon(){
        getCloseIcon().click();
    }

    public void scrollTo(String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement( getDriver().findElement(By.xpath("//*[text()=\"" + text + "\"]"))).perform();
    }

    public void switchToNewWindow() {
        for(String handle:getDriver().getWindowHandles()){
            getDriver().switchTo().window(handle);
        }
    }

    public void closeCurrentWindow() {
        getDriver().close();
    }

    public void waitForLoading(String url){
        waitUntil(ExpectedConditions.urlToBe(url));
        waitUntilPageFinishLoading();
    }

    public void removeConnectionsByUserId(String userId){
        load("https://purple-mongo-qa.hesos.net/db/match/amhsstudentconnections?skip=0&key=userId&value=" + userId + "&type=S&query=&projection=");
        waitUntilPageFinishLoading();
        button("Sign In").click();
        waitUntilPageFinishLoading();
        load("https://purple-mongo-qa.hesos.net/db/match/amhsstudentconnections?skip=0&key=userId&value=" + userId + "&type=S&query=&projection=");
        setImplicitWaitTimeout(3);
        try {getBigDeleteButton().click();
            button(By.id("deleteListConfirmButton")).click();
            resetImplicitWaitTimeout();
        }
        catch (Exception e) {
            resetImplicitWaitTimeout();
        }
        waitUntilPageFinishLoading();
    }


//locators
    private WebElement notification(){
        return driver.findElement(By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum']/span[text()='Notifications']"));
    }
    private WebElement notificationsAndTasks () {
        return driver.findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Notifications & Tasks']"));
    }
    private WebElement getTable (String locator) {
        return driver.findElement(By.cssSelector("table."+locator));
    }

    private WebElement getMenuLink(String locator) {
        return getDriver().findElement(By.xpath(getMenuLinkLocator(locator)));
    }
    private WebElement getMenuTab (String locator) {
        return getDriver().findElement(By.xpath(getMenuTabLocator(locator)));
    }

    private WebElement getEnabledCheckbox(){
        return  getDriver().findElement(By.xpath("//tr/td[1]//div/div"));
    }

    private WebElement getUseDefaultFilterCheckbox(){
        return  getDriver().findElement(By.xpath("//tr/td[3]//div/div"));
    }

    private WebElement getFilterValueFirstRow(String filterName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='" + filterName + "']/input")));
        return getDriver().findElement(By.xpath("//div[@title='" + filterName + "']/input"));
    }

    private WebElement getDefaultFilterValueBox(String id){
        return  getDriver().findElement(By.id(id));
    }

    private String getMenuLinkLocator(String advancedAwarenessOption) {
        return "//div[3]//a[@class='menu-link']/span[text()=\"" + advancedAwarenessOption + "\"]";
    }
    private String getMenuTabLocator(String advancedAwarenessTab) {
        return String.format("//nav[@aria-label='Active Match Sub Menu']/ul/li/span[text()='%s'] | //nav[@aria-label='Active Match Sub Menu']/ul/li/a/span[text()='%s']",advancedAwarenessTab,advancedAwarenessTab);
    }

    private String getMenuItemById(String menuItem) {
        String lowCaseText = menuItem.toLowerCase();
        lowCaseText = lowCaseText.replace(" ", "-");
        return "js-main-nav-" + lowCaseText + "-menu-link";
    }

    private WebElement getCloseIcon(){
      return getDriver().findElement(By.cssSelector(".icon.close"));
    }

    private WebElement getBigDeleteButton(){
        return getDriver().findElement(By.xpath("//button[@data-target=\"#deleteListModal\"]"));
    }
}
