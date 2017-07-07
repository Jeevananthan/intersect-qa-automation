package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class CMSInstitutionPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public CMSInstitutionPageImpl() {
        logger = Logger.getLogger(CMSInstitutionPageImpl.class);
    }

    public void clickUndergradAdmissionsButton() {
        waitUntilPageFinishLoading();
        undergraduateAdmissionsButton().click();
    }

    public void clickUndergradNode() {
        waitUntil(ExpectedConditions.elementToBeClickable(underGradNodeLink()));
        underGradNodeLink().click();
        waitUntilPageFinishLoading();
    }

    public void clickStudentbodyButton() {
        waitUntilPageFinishLoading();
        studentBodyButton().click();
    }

    public void clickStudentbodyNode() {
        waitUntil(ExpectedConditions.elementToBeClickable(studentBodyNodeLink()));
        studentBodyNodeLink().click();
        waitUntilPageFinishLoading();
    }

    public void openStudentBodyNode() {
        clickStudentbodyButton();
        clickStudentbodyNode();
    }

    public void openUndergradAdmissionsNode() {
        clickUndergradAdmissionsButton();
        clickUndergradNode();
    }

    //Locators

    private WebElement undergraduateAdmissionsButton() {
        return getDriver().findElement(By.xpath("//div[@class='field-group-htabs-wrapper']/div[@class='horizontal-tabs " +
                "clearfix']/ul[@class='horizontal-tabs-list']/li/a/strong[text()='Undergraduate admissions']/.."));
    }
    private WebElement underGradNodeLink() {
        return getDriver().findElement(By.xpath("//div[@class='node node-he-undergraduate-admissions-us " +
                "contextual-links-region clearfix']/h2/a"));
    }
    private WebElement studentBodyButton() {
        return getDriver().findElement(By.xpath("//div[@class='field-group-htabs-wrapper']/div[@class='horizontal-tabs " +
                "clearfix']/ul[@class='horizontal-tabs-list']/li/a/strong[text()='Studentbody']/.."));
    }
    private WebElement studentBodyNodeLink() {
        return getDriver().findElement(By.cssSelector("fieldset.group-studentbody-id-ref.field-group-htab.form-" +
                "wrapper.horizontal-tabs-pane div.field-item h2 a"));
    }
}
