package pageObjects.SM.activeMatch;

        import cucumber.api.DataTable;
        import org.apache.log4j.Logger;
        import org.junit.Assert;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import pageObjects.COMMON.PageObjectFacadeImpl;

        import javax.swing.text.StringContent;
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

    public void checkActiveMatchNextGenNumber(Integer number){
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ui.card")));
        Assert.assertTrue(getAMNextGenMatches().findElements(By.cssSelector(".ui.card")).size() > number);
    }

    public void checkLegacyAMAreDisplayed(){
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        Assert.assertTrue(getLegacyMatches().isDisplayed());
    }

    public void checkLegacyCardDisplayes(String collegeName, DataTable dataTable){
        waitUntilPageFinishLoading();
        List<List<String>> details = dataTable.asLists(String.class);
        for (List<String> row : details) {
            Assert.assertTrue(getLegacyMatches().findElement(By.xpath("//a[text()=\"" + collegeName + "\"]//../..//*[contains(text(),\"" + row.get(0) + "\")]")).isDisplayed());
        }
    }

     public void checkActiveMatchNextGenDisplayes(String collegeName, DataTable dataTable){
             waitUntil(ExpectedConditions.visibilityOf(getAMNextGenMatches()));
             List<List<String>> details = dataTable.asLists(String.class);
             for (List<String> row : details) {
                 waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()=\"" + collegeName + "\"]//../../..//*[contains(text(),\"" + row.get(0) + "\")]")));
             }
     }

    public void verifyTextIsLinkInMatchCard(String collegeName) {
        Assert.assertTrue("The '& more link...' is not displayed or it is not a link.",
                moreLink(collegeName).isDisplayed());
    }

    //Locators
    private WebElement getCard(String card) {
        return driver.findElement(By.xpath("//*[@class = 'ui card']//a[text()='" + card + "']"));
    }

    private WebElement getAMNextGenMatches(){
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class = \"ui cards matches-group\"][1]")));
        return driver.findElement(By.xpath("//*[@class = \"ui cards matches-group\"][1]"));
    }

    private WebElement getLegacyMatches(){
        return driver.findElement(By.xpath("//*[@class = \"ui cards matches-group\"][2]"));
    }

    private By getTextFromTheCard(String text) {
        return By.xpath("//../../..//a[text()='" + text + "']");
    }

    private String spinnerLocator = "div.ui.active.loader";

    private WebElement moreLink(String college) { return driver.findElement(By.xpath("//div[@id = 'activematch-app']" +
            "/div[1]//a[text() = '" + college + "']/../../..//a[text() = '& more...']")); }


}
