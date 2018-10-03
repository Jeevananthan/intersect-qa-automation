package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.GetProperties;

import java.util.Iterator;
import java.util.List;

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
        load(GetProperties.get("he.app.url")+ URL);
    }

    public void verifyColumnHeaders(String locator, DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        List<WebElement> columns = getTable(locator).findElements(By.cssSelector("tr th"));
        for (int i=0; i < details.size(); i++) {
            Assert.assertEquals("Column names are different", details.get(i), columns.get(i).getText());
        }
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
}
