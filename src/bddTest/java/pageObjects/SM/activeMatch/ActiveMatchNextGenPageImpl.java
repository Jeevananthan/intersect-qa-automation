package pageObjects.SM.activeMatch;

        import cucumber.api.DataTable;
        import org.apache.log4j.Logger;
        import org.junit.Assert;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import pageObjects.COMMON.PageObjectFacadeImpl;

        import java.util.List;

public class ActiveMatchNextGenPageImpl extends PageObjectFacadeImpl {

    public ActiveMatchNextGenPageImpl() {
        logger = Logger.getLogger(ActiveMatchNextGenPageImpl.class);
    }

    private Logger logger;

    public void clickOnForTheCard(String text, String card) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        getCard(card).findElement(getTextFromTheCard(text)).click();
    }

    public void checkActiveMatchNextGenAreDisplayed(){
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        Assert.assertTrue(getAMNextGenMatches().isDisplayed());
    }

    public void checkLegacyAMAreDisplayed(){
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        Assert.assertTrue(getLegacyMatches().isDisplayed());
    }

    public void checkLegacyCardDisplayes(String collegeName, DataTable dataTable){
        List<List<String>> details = dataTable.asLists(String.class);
        for (List<String> row : details) {
            Assert.assertTrue(getLegacyMatches().findElement(By.xpath("//a[text()=\"" + collegeName + "\"]//../..//*[contains(text(),\"" + row.get(0) + "\")]")).isDisplayed());
        }
    }

    //Locators
    private WebElement getCard(String card) {
        return driver.findElement(By.xpath("//*[@class = 'ui card']//a[text()='" + card + "']"));
    }

    private WebElement getAMNextGenMatches(){
        return driver.findElement(By.xpath("//*[@class = \"ui cards matches-group\"][1]"));
    }

    private WebElement getLegacyMatches(){
        return driver.findElement(By.xpath("//*[@class = \"ui cards matches-group\"][2]"));
    }

    private By getTextFromTheCard(String text) {
        return By.xpath("//../../..//a[text()='" + text + "']");
    }

    private String spinnerLocator = "div.ui.active.loader";


}
