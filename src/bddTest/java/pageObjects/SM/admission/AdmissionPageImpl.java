package pageObjects.SM.admission;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class AdmissionPageImpl extends PageObjectFacadeImpl {

    public AdmissionPageImpl() {
        logger = Logger.getLogger(AdmissionPageImpl.class);
    }
    public SearchPageImpl searchPage = new SearchPageImpl();

    private Logger logger;

    public void verifyLabelInActiveMatchCell(String collegeName, String label) {
        searchPage.goToCollegeInSearchResults(collegeName);
        try {
            Assert.assertTrue("The text in the label is incorrect", testOptionalLabel(collegeName).getText().equals(label));
        } catch (Exception e) {
            logger.error("The label is not displayed");
        }
    }

    //Locators
    private WebElement testOptionalLabel(String collegeName) { return driver.findElement(By.xpath("//a[text() = '" + collegeName + "']/ancestor::tr/td[3]//p")); }

}
