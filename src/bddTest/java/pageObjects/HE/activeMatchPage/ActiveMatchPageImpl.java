package pageObjects.HE.activeMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class ActiveMatchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public ActiveMatchPageImpl() {
        logger = Logger.getLogger(ActiveMatchPageImpl.class);
    }

    public void verifyTitleIsPresent() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Active Match page is not correctly displayed", activeMatchTitle().isDisplayed());
    }

    public void verifyActiveMatchConnectionsHeaders(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for (String header:list){
            Assert.assertTrue(header+"is not displayed",driver.findElement(By.xpath("//th/span[text()='"+header+"']")).isDisplayed());
        }
    }

    public void verfyActiveMatchPage(){
        Assert.assertTrue("Connection link is not displayed",connectionTab().isDisplayed());
        Assert.assertTrue("Dropdown button is not displayed",exportConnectionsDropdown().isDisplayed());
        Assert.assertTrue("Download button is not displayed",downloadButton().isDisplayed());
    }

    public void verifyActiveMatchDropdownMenu(String Option,DataTable dataTable){
        exportConnectionsDropdown().click();
        List<String> value=dataTable.asList(String.class);
        for(String option:value){
            Assert.assertTrue(option+" is not displayed",driver.findElement(By.xpath("//div/span[text()='"+Option+"']/parent::div/following-sibling::div/span[text()='"+option+"']")).isDisplayed());
            waitUntilPageFinishLoading();
        }
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void verifyDropdownMenuHeader(DataTable dataTable){
        List<String> value=dataTable.asList(String.class);
        exportConnectionsDropdown().click();
        for(String option:value){
            Assert.assertTrue(option+" is not displayed",driver.findElement(By.xpath("//div/span[text()='"+option+"']")).isDisplayed());
        }
    }

    public void verifyDefaultdropdownMenuSelection(String defaultMenuSelection,DataTable dataTable){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        navBar.goToActiveMatch();
        waitUntilPageFinishLoading();
        List<String> value=dataTable.asList(String.class);
        for (String option : value) {
            exportConnectionsDropdown().click();
            driver.findElement(By.xpath("//div/span[text()='"+option+"']")).click();
            waitUntilPageFinishLoading();
            navBar.goToRepVisits();
            waitUntilPageFinishLoading();
            navBar.goToActiveMatch();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Since Last Export is not present as a default dropdown menu selection",driver.findElement(By.xpath("//div[@class='ui button dropdown gyhR4eL0bZuX-AtA9-Cgy']/div[text()='"+defaultMenuSelection+"']")).isDisplayed());
        }}

        public void verifyActiveMatchConnectionsDropdownMenu(String option,DataTable dataTable){
        exportConnectionsDropdown().click();
        List<String> list = dataTable.asList(String.class);
        for(String value:list){
            moveToElement(driver.findElement(By.xpath("//div/span[text()='"+option+"']/parent::div/following-sibling::div/span[text()='"+value+"']")));
            Assert.assertTrue(value+" is not displayed",driver.findElement(By.xpath("//div/span[text()='"+option+"']/parent::div/following-sibling::div/span[text()='"+value+"']")).isDisplayed());
        }navBar.goToActiveMatch();
        }

    //locators
    private WebElement activeMatchTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
    private WebElement connectionTab(){
        WebElement Tab=driver.findElement(By.linkText("Connections"));
        return Tab;
    }
    private WebElement exportConnectionsDropdown() {
        WebElement button=driver.findElement(By.xpath("//div[@class='ui button dropdown gyhR4eL0bZuX-AtA9-Cgy']"));
        return button;
    }
    private WebElement downloadButton(){
        WebElement button=driver.findElement(By.xpath("//span[text()='Download']"));
        return button;
    }
    private void moveToElement(WebElement element){
        Actions builder = new Actions(driver);
        builder.moveToElement(element ).build().perform();
    }
}
