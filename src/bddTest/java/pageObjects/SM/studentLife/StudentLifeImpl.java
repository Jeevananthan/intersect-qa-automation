package pageObjects.SM.studentLife;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentLifeImpl extends PageObjectFacadeImpl {

    public StudentLifeImpl() {
        logger = Logger.getLogger(StudentLifeImpl.class);
    }

    SearchPageImpl searchPage = new SearchPageImpl();

    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sDropdownOptionsLists%sDropdownOptionsLists.properties", fs, fs, fs, fs, fs);


    private Logger logger;

    public void verifyGreekLife(String option) {
        getGreekLife().click();
        Assert.assertTrue("Greek Life default option ie Select Preference is not displaying.", getGreekLifeDefaultOption().isDisplayed());
        switch (option) {
            case "Fraternities/Sororities Available":
                Assert.assertTrue("Fraternities/Sororities Available option is not displaying.", getGreekLikeOptionOneYes().isDisplayed());
                getGreekLikeOptionOneYes().click();
                verifyPillForGreekLifeInMustHaveBox("Greek Life");
                break;
            case "No Fraternities/Sororities":
                Assert.assertTrue("No Fraternities/Sororities option is not displaying.", getGreekLifeOptionNo().isDisplayed());
                getGreekLifeOptionNo().click();
                verifyPillForGreekLifeInMustHaveBox("No Greek Life");
                break;
            default:
                logger.info("Invaild option : " + option);
        }
    }

    private void verifyPillForGreekLifeInMustHaveBox(String option) {
        searchPage.verifyMustHaveBoxContains(option);
    }

    public void pickOptionFromDropdownInStudentLife(String option, String dropdown) {
        switch (dropdown) {
            case "ORGANIZATIONS AND CLUBS":
                organizationsAndClubsDropdown().click();
                orgsAndClubsOption(option).click();
                break;
            case "GREEK LIFE":
                greekLifeDropdown().click();
                greekLifeOption(option).click();
                break;
        }
    }

    public void verifyDefaultTextInDropdown(String dropdown, String expectedText) {
        Assert.assertTrue("The default text in the " + dropdown + " is not correct",
                organizationsAndClubsDropdown().getText().equals(expectedText));
    }

    public void clickDropdown(String locator) {
        try {
            driver.findElement(By.cssSelector(locator)).click();
        } catch (Exception e) {
            driver.findElement(By.xpath(locator)).click();
        }
    }

    public void verifyListMatchesList(String originalListLocator, String expectedListEntryTitle) {
        List<String> expectedOptionsList = getListFromPropFile(propertiesFilePath, ";", expectedListEntryTitle);
        List<WebElement> originalListWebElements = driver.findElements(By.cssSelector(originalListLocator));
        List<String> originalList = new ArrayList<>();
        for (WebElement element : originalListWebElements) {
            originalList.add(element.getText());
        }
        Assert.assertTrue("The list in the dropdown does not have the expected values",
                originalList.equals(expectedOptionsList));
    }

    public void selectOptionFromList(String optionName, String listLocator) {
        List<WebElement> optionsListWebElements;
        try {
            driver.findElement(By.xpath(listLocator));
            optionsListWebElements = driver.findElements(By.xpath(listLocator));
        } catch(NoSuchElementException e) {
            optionsListWebElements = driver.findElements(By.cssSelector(listLocator));
        }
        for (WebElement element : optionsListWebElements) {
            if (element.getText().equals(optionName)) {
                element.click();
            }
        }
    }

    public void verifyAddedOption(String optionName) {
        List<WebElement> addedElements = driver.findElements(By.cssSelector(addedElementsInDropdownField));
        boolean isElementPresent = false;
        for (WebElement element : addedElements) {
            if (element.getAttribute("value").equals(optionName)) {
                isElementPresent = true;
            }
        }
        Assert.assertTrue("The option was not added to the Organizations and Clubs text field",
                isElementPresent);
    }

    public void verifyNumberOfAddedOptions(Integer numberOfAddedOptions) {
        List<WebElement> addedElements = driver.findElements(By.cssSelector(addedElementsInDropdownField));
        Assert.assertTrue("The number of added options in the text field is incorrect",
                addedElements.size() == numberOfAddedOptions);
    }

    public void removeOptionFromDropdownField(String optionName) {
        driver.findElement(By.xpath(xButtonAddedElementDropdownField(optionName))).click();
    }

    //Locator

    private WebElement getGreekLife() {
        return driver.findElement(By.id("greeklife-dropdown"));
    }

    private WebElement getGreekLikeOptionOneYes() {
        return driver.findElement(By.id("greek-life-selection-yes"));
    }

    private WebElement getGreekLifeOptionNo() {
        return driver.findElement(By.id("greek-life-selection-no"));
    }

    private WebElement getGreekLifeDefaultOption() {
        return driver.findElement(By.id("greeklife-dropdown-option-close"));
    }

    private WebElement organizationsAndClubsDropdown() {
        return driver.findElement(By.cssSelector("input.search + span + div"));
    }

    private WebElement orgsAndClubsOption(String optionName) {
        return driver.findElement(By.xpath("//div[@class = 'visible menu transition']/div/span[text() = '" + optionName + "']"));
    }

    private WebElement greekLifeDropdown() {
        return driver.findElement(By.cssSelector("div#greeklife-dropdown"));
    }

    private WebElement greekLifeOption(String optionName) {
        return driver.findElement(By.cssSelector("//div[@class = 'visible menu transition']/div/span[text() = '" + optionName + "']"));
    }

    private String addedElementsInDropdownField = "a.ui.label";

    private String xButtonAddedElementDropdownField(String optionName) { return "//a[@value = '" + optionName + "']/i";}
}
