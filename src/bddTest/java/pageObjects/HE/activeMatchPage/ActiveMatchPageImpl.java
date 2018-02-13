package pageObjects.HE.activeMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    public void navigateToActiveMatch() {
       navBar.goToActiveMatch();
       waitUntilPageFinishLoading();
    }

    public void verfyActiveMatchPage(){
        Assert.assertTrue("Connection link is not displayed",connectionTab().isDisplayed());
        Assert.assertTrue("Dropdown button is not displayed",dropDownMenu().isDisplayed());
        Assert.assertTrue("Download button is not displayed",downloadButton().isDisplayed());

    }

    public void verifyActiveMatchDropdownMenu(String Option,DataTable dataTable){
        dropDownMenu().click();
        List<String> value=dataTable.asList(String.class);
        for(String option:value){
            Assert.assertTrue(option+"is not displayed",driver.findElement(By.xpath("//div/span[text()='"+Option+"']/parent::div/following-sibling::div/span[text()='"+option+"']")).isDisplayed());
        }
    }

    public void verifyDropdownMenuHeader(DataTable dataTable){
        List<String> value=dataTable.asList(String.class);
        for(String option:value){
            Assert.assertTrue(option+"is not displayed",selectOption(option).isDisplayed());
        }
    }

    public void verifySelectedHeader(String Option,DataTable dataTable){
        navBar.goToActiveMatch();
        waitUntilPageFinishLoading();
        List<String> value=dataTable.asList(String.class);
            for (String option : value) {
                dropDownMenu().click();
                selectOption(option).click();
                String displayingValue = Option+": "+option;
                Assert.assertTrue(option + "is not displayed", verifyDropdownHeader(displayingValue).isDisplayed());
            }
    }

    public void verifyHeader(String option){
        dropDownMenu().click();
        Assert.assertTrue("Since Last Export option is not displayed",selectOption(option).isDisplayed());
        Assert.assertTrue("Last exported connection details are not displayed",driver.findElement(By.xpath("//div/span[contains(text(),'connections since last export on')]")).isDisplayed());
    }

    public void verifyDefaultdropdownMenuSelection(String defaultMenuSelection,DataTable dataTable){
        navBar.goToRepVisits();
        navBar.goToActiveMatch();
        waitUntilPageFinishLoading();
        List<String> value=dataTable.asList(String.class);
        for (String option : value) {
            dropDownMenu().click();
            selectOption(option).click();
            navBar.goToRepVisits();
            navBar.goToActiveMatch();
            waitUntilPageFinishLoading();
        Assert.assertTrue("Since Last Export is not present as a default dropdown menu selection",driver.findElement(By.xpath("//div[@class='ui button dropdown gyhR4eL0bZuX-AtA9-Cgy']/div[text()='"+defaultMenuSelection+"']")).isDisplayed());
    }}

    //locators
    private WebElement activeMatchTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }
    private WebElement activeMatchConntectionText(){
        WebElement text=driver.findElement(By.xpath("//div[text()='Connection']/following-sibling::div[text()='ActiveMatch']"));
        return text;
    }
    private WebElement connectionTab(){
       WebElement Tab=driver.findElement(By.linkText("Connections"));
       return Tab;
    }
    private WebElement dropDownMenu() {
        WebElement button=driver.findElement(By.xpath("//div[@class='ui button dropdown gyhR4eL0bZuX-AtA9-Cgy']"));
        return button;
    }
    private WebElement downloadButton(){
        WebElement button=driver.findElement(By.xpath("//span[text()='Download']"));
        return button;
    }
    private WebElement selectOption(String option){
        WebElement value=driver.findElement(By.xpath("//div/span[text()='"+option+"']"));
        return value;
    }
    private WebElement verifyDropdownHeader(String option){
        WebElement value=driver.findElement(By.xpath("//div[text()='"+option+"']"));
        return value;
    }
}
