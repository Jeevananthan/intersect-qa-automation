package pageObjects.SM.activeMatch;

        import org.apache.log4j.Logger;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import pageObjects.COMMON.PageObjectFacadeImpl;

public class ActiveMatchNextGenPageImpl extends PageObjectFacadeImpl {

    public ActiveMatchNextGenPageImpl() {
        logger = Logger.getLogger(ActiveMatchNextGenPageImpl.class);
    }

    private Logger logger;

    public void clickOnForTheCard(String text, String card) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        getCard(card).findElement(getTextFromTheCard(text)).click();
    }

    //Locators
    private WebElement getCard(String card) {
        return driver.findElement(By.xpath("//*[@class = 'ui card']//a[text()='" + card + "']"));
    }

    private By getTextFromTheCard(String text) {
        return By.xpath("//../../..//a[text()='" + text + "']");
    }

    private String spinnerLocator = "div.ui.active.loader";

}
