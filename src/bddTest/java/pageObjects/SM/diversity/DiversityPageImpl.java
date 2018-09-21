package pageObjects.SM.diversity;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.institutionCharacteristicsPage.InstitutionCharacteristicsImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class DiversityPageImpl extends PageObjectFacadeImpl {

    public DiversityPageImpl() {
        logger = Logger.getLogger(DiversityPageImpl.class);
    }
    InstitutionCharacteristicsImpl institutionCharacteristics = new InstitutionCharacteristicsImpl();

    private Logger logger;

    public void verifyDefaultTextInElement(String locator, String expectedText){
        String actualText = "";
        try {
            actualText = driver.findElement(By.cssSelector(locator)).getText();
        } catch (Exception e) {
            actualText = driver.findElement(By.xpath(locator)).getText();
        }
        Assert.assertTrue("The default text in the element is incorrect",
                actualText.equals(expectedText));
    }

    public void verifyRadioButtonIsSelected(String label, String selectedUnselected) {
        institutionCharacteristics.verifyCheckboxSelectedUnselected(label, selectedUnselected);
    }

    //Locators
}
