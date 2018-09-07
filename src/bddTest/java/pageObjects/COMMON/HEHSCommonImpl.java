package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HEHSCommonImpl extends PageObjectFacadeImpl {

    public void verifySubtabsInNotificationsPage(String tab,String option,String user,DataTable dataTable){
        List<String> tabs = dataTable.asList(String.class);
        navigationBar.goToRepVisits();
        waitUntilPageFinishLoading();
        if(user.equals("premium")||user.equals("limited")){
            notification().click();
        }else if(user.equals("naviance")||user.equals("non-naviance")){
            notificationsAndTasks().click();
        }
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='menu-link active']/span[text()='Requests']"),1));
        if(user.equals("premium")||user.equals("limited")||user.equals("naviance")||user.equals("non-naviance")){
            for(String subtab:tabs){
                Assert.assertTrue("Requests subtab is not displayed",driver.findElement(By.xpath("//a[@class='menu-link active']/span[text()='"+subtab+"'] | //a[@class='menu-link']/span[text()='" + subtab + "']")).isDisplayed());
            }
        }else {
            Assert.fail("Invalid option");
        }
        List<WebElement> Tab = driver.findElements(By.xpath("//a[@class='menu-link']/span[text()='" + tab + "']"));
        if(option.equals("displaying")){
            Assert.assertTrue("SubTab is not displayed",Tab.size()==1);
        }else if(option.equals("not displaying")){
            Assert.assertTrue("SubTab is displayed",Tab.size()==0);
        }else {
            Assert.fail("Invalid option");
        }
    }


//locators
    private WebElement notification(){
        return driver.findElement(By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum']/span[text()='Notifications']"));
    }
    private WebElement notificationsAndTasks () {
        return driver.findElement(By.xpath("//a[@class='menu-link']/span[text()='Notifications & Tasks']"));
    }
}
