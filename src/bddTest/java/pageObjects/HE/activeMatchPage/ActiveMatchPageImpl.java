package pageObjects.HE.activeMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.Date.DateUtility;
import utilities.File.CsvFileUtility;
import utilities.File.FileManager;

import java.util.List;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

    public void verifyActiveMatchDropdownMenu(String Option,DataTable dataTable){
        exportConnectionsDropdown().click();
        List<String> value=dataTable.asList(String.class);
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.END).build().perform();
        for(String option:value){
            Assert.assertTrue(option+"is not displayed",driver.findElement(By.xpath("//div/span[text()='"+Option+"']/parent::div/following-sibling::div/span[contains(text(),'"+option+"')]")).isDisplayed());
            waitUntilPageFinishLoading();
        }
        action.sendKeys(Keys.UP).build().perform();
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    /**
     * Downloads the ActiveMatch connection files for the last year
     */
    public void downloadActiveMatchConnectionsForCurrentYear(){
        String currentYear = DateUtility.getCurrentYear();
        selectDownloadCriteriaForConnections(String.format("-%s",currentYear));
        waitUntil(ExpectedConditions.visibilityOf(getDownloadActiveMatchConnectionsButton()));
        getDownloadActiveMatchConnectionsButton().click();
        waitUntil(ExpectedConditions.visibilityOf(getDownloadActiveMatchConnectionsModal()));
        Assert.assertTrue("The download Active Match Connections modal was not displayed",
              getDownloadActiveMatchConnectionsModal().isDisplayed());
        getDownloadActiveMatchConnectionsModalButton().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                "//div[@class='ui modal transition visible active']//button[@class='ui primary button']/span[text()='Download']")));
    }

    /**
     * Selects a download criteria for ActiveMatch connections
     * @param filter to be selected
     */
    private void selectDownloadCriteriaForConnections(String filter){
        exportConnectionsDropdown().click();
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.END).build().perform();
        waitForUITransition();
        driver.findElement(By.xpath(String.format(
                ".//div[@class='menu transition visible _1SkJDbt7JAMZTBRW2iCO8g']/div/span[contains(text(),'%s')]",
                filter))).click();
    }

    /**
     * Verifies the header of the downloaded ActiveMatch connections csv files
     * @param csvFile to verify
     * @param headers to be verified inthe given csv file
     */
    public void verifyDownloadedActiveMatchConnectionsHeaders(String csvFile, DataTable headers){
        List<String> expectedHeaderValues=headers.asList(String.class);
        String home = System.getProperty("user.home");
        waitUntilFileExists(String.format("%s/Downloads/%s",home,csvFile));
        String[] actualHeaderValues = CsvFileUtility.getCsvHeaders(String.format("%s/Downloads/%s",home,csvFile));
        for(int i=0; i<expectedHeaderValues.size(); i++){
            Assert.assertTrue(String.format("The Csv header is not correct, actual: %s, expected: %s ",
                    actualHeaderValues[i], expectedHeaderValues.get(i)),
                    actualHeaderValues[i].contains(expectedHeaderValues.get(i)));
        }
    }

    /**
     * Deletes  the downloaded ActiveMatch connections file
     * @param filePath to be deleted
     */
    public void deleteDownloadedActiveMatchConnectionsFile(String filePath){
        String home = System.getProperty("user.home");
        FileManager.deleteFile(String.format("%s/Downloads/%s",home,filePath));
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
        waitForUITransition();
        List<String> list = dataTable.asList(String.class);
        for(String value:list){
            moveToElement(driver.findElement(By.xpath("//div/span[text()='"+option+"']/parent::div/following-sibling::div/span[text()='"+value+"']")));
            Assert.assertTrue(value+" is not displayed",driver.findElement(By.xpath("//div/span[text()='"+option+"']/parent::div/following-sibling::div/span[text()='"+value+"']")).isDisplayed());
        }navBar.goToActiveMatch();
        }

    //locators
    private WebElement activeMatchTitle() { return driver.findElement(By.cssSelector("div.five.wide.computer.seven.wide.mobile.eight.wide.tablet.column div.UDWEBAWmyRe5Hb8kD2Yoc")); }

    /**
     * Gets the ActiveMatch download connections
     * @return WebElement
     */
    private WebElement getDownloadActiveMatchConnectionsButton(){
        return button("Download");
    }

    /**
     * Gets the download download AM connections modal
     * @return
     */
    private WebElement getDownloadActiveMatchConnectionsModal(){
        return driver.findElement(By.xpath(".//div[@class='ui modal transition visible active']"));
    }

    /**
     * Gets the download button of the download AM connections modal
     * @return WebElement
     */
    private WebElement getDownloadActiveMatchConnectionsModalButton(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                ".//div[@class='ui modal transition visible active']//button[@class='ui primary button']/span[text()='Download']")));
        return driver.findElement(By.xpath(
                ".//div[@class='ui modal transition visible active']//button[@class='ui primary button']/span[text()='Download']"));
    }

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
    }}
