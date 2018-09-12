package pageObjects.SM.studentLife;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class StudentLifeImpl extends PageObjectFacadeImpl {

    public StudentLifeImpl() {
        logger = Logger.getLogger(StudentLifeImpl.class);
    }
    SearchPageImpl searchPage = new SearchPageImpl();

    private Logger logger;

    public void verifyGreekLife(String option){
        getGreekLife().click();
        Assert.assertTrue("Greek Life default option ie Select Preference is not displaying.", getGreekLifeDefaultOption().isDisplayed());
        switch (option){
            case "Fraternities/Sororities Available" :
                Assert.assertTrue("Fraternities/Sororities Available option is not displaying.", getGreekLikeOptionOneYes().isDisplayed());
                getGreekLikeOptionOneYes().click();
                verifyPillForGreekLifeInMustHaveBox("Greek Life");
                break;
            case "No Fraternities/Sororities" :
                Assert.assertTrue("No Fraternities/Sororities option is not displaying.", getGreekLifeOptionNo().isDisplayed());
                getGreekLifeOptionNo().click();
                verifyPillForGreekLifeInMustHaveBox("No Greek Life");
                break;
            default:
                    logger.info("Invaild option : "+option  );
        }
    }

    private void  verifyPillForGreekLifeInMustHaveBox(String option){
        searchPage.verifyMustHaveBoxContains(option);
    }

    public void pickOptionFromDropdownInStudentLife(String option, String dropdown) {
        switch(dropdown) {
            case "ORGANIZATIONS AND CLUBS" :
                organizationsAndClubsDropdown().click();
                orgsAndClubsOption(option).click();
                break;
            case "GREEK LIFE" :
                greekLifeDropdown().click();
                greekLifeOption(option).click();
                break;
        }
    }

    //Locator

    private WebElement getGreekLife(){ return driver.findElement(By.id("greeklife-dropdown")); }

    private WebElement getGreekLikeOptionOneYes(){ return driver.findElement(By.id("greek-life-selection-yes")); }

    private WebElement getGreekLifeOptionNo(){return driver.findElement(By.id("greek-life-selection-no")); }

    private WebElement getGreekLifeDefaultOption(){ return driver.findElement(By.id("greeklife-dropdown-option-close")); }

    private WebElement organizationsAndClubsDropdown() { return driver.findElement(By.cssSelector("input.search + span + div")); }

    private WebElement orgsAndClubsOption(String optionName) { return driver.findElement(By.xpath("//div[@class = 'visible menu transition']/div/span[text() = '" + optionName + "']")); }

    private WebElement greekLifeDropdown() { return driver.findElement(By.cssSelector("div#greeklife-dropdown")); }

    private WebElement greekLifeOption(String optionName) { return driver.findElement(By.cssSelector("//div[@class = 'visible menu transition']/div/span[text() = '" + optionName + "']")); }
}
