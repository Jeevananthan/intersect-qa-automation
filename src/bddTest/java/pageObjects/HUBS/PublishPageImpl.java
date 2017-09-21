package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class PublishPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public PublishPageImpl() {
        logger = Logger.getLogger(PublishPageImpl.class);
    }

    public void clickPublishButton() {
        publishButton().click();
    }

    public void clickSubmitChangesButton() {
        submitChangesButton().click();
        new WebDriverWait(getDriver(), 40).until(ExpectedConditions.elementToBeClickable(By.linkText("Continue editing")));
    }

    public void enterPublishReasonsText(String publishReason) {
        publishReasonsTextArea().sendKeys(publishReason);
    }

    public void clickContinueEditingLink() {
        continueEditingLink().click();
    }

    //Locators

    private WebElement publishReasonsTextArea() {
        return textbox(By.id("submit-overlay__rationale"));
    }
    private WebElement submitChangesButton() {
        return button("Submit Changes");
    }
    private WebElement continueEditingLink() {
        return link("Continue editing");
    }
    private WebElement publishButton() {
        return getDriver().findElement(By.xpath("//span[@class='intersect-btn intersect-btn--fuschia']"));
    }
}
