package pageObjects.SM.collegesImApplyingTo;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class CollegesImApplyingToPageImpl extends PageObjectFacadeImpl {

    public CollegesImApplyingToPageImpl() {
        logger = Logger.getLogger(CollegesImApplyingToPageImpl.class);
    }

    private Logger logger;

    public void addCollegeToImApplyingTo(String collegeName) {

        waitUntilPageFinishLoading();
        getCollegeDropDown().click();
        getCollegeDropDown().clear();
        getCollegeDropDown().sendKeys(collegeName);
        try {
            button(collegeName).click();
            button("Add Application").click();
        }
        catch (Exception e)
        {
            logger.info("The " + collegeName + " is already added");
        }
    }

    public void clickMoreMenuElement(String option, String college) {
        waitUntil(ExpectedConditions.elementToBeClickable(moreButton(college)));
        moreButton(college).click();
        if (option.equals("CONNECT") || option.equals("DISCONNECT")) {
            waitUntilElementExists(connectDisconnectMenuItem());
            connectDisconnectMenuItem().click();
        } else {
            waitUntilElementExists(driver.findElements(By.cssSelector(commonMoreMenuItems)).get(0));
            List<WebElement> commonMoreMenuItemsList = driver.findElements(By.cssSelector(commonMoreMenuItems));
            for (WebElement element : commonMoreMenuItemsList) {
                if (element.getText().equals(option)) {
                    element.click();
                }
            }
        }
    }

    public void verifyItemInMoreMenu(String itemLabel, String college) {
        waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(transitionDimmer), 1));
        moreButton(college).click();
        if (itemLabel.equals("CONNECT") || itemLabel.equals("DISCONNECT")) {
            Assert.assertTrue("The item " + itemLabel + " is not present in the More menu.", connectDisconnectMenuItem().getText().equals(itemLabel));
        } else {
            List<WebElement> commonMoreMenuItemsList = driver.findElements(By.cssSelector(commonMoreMenuItems));
            List<String> commonMoreMenuItemsStrings = new ArrayList<>();
            for (WebElement element : commonMoreMenuItemsList) {
                commonMoreMenuItemsStrings.add(element.getText());
            }
            Assert.assertTrue("The item " + itemLabel + " is not present in the More menu.", commonMoreMenuItemsStrings.contains(itemLabel));
        }
        //close menu
        menuBigContainer(college).click();
    }

    public void removeCollegeFromList(String college) {
        if (driver.findElements(By.xpath(collegeCheckboxLocator(college))).size() > 0) {
            collegeCheckbox(college).click();
            removeButton().click();
            dialogRemoveButton().click();
            waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(confirmationBannerLocator), 0));
        }
    }


        //Locators

    private WebElement getCollegeDropDown() { return getDriver().findElement(By.xpath("//div/input")); }
    private WebElement moreButton(String college) { return driver.findElement(By.xpath("//a[text() = '" + college + "']/../../../td[last()]//button")); }
    private WebElement connectDisconnectMenuItem() { return driver.findElement(By.cssSelector("button + div a + button figure + span")); }
    private String commonMoreMenuItems = "button + div:not(.hidden) a figure";
    private WebElement collegeCheckbox(String college) { return driver.findElement(By.xpath("//a[text() = '" + college + "']/../../../td[1]//figure")); }
    private String collegeCheckboxLocator(String college) {return "//a[text() = '" + college + "']/../../../td[1]//figure";}
    private WebElement removeButton() { return driver.findElement(By.xpath("//button[contains(text(), 'REMOVE')]")); }
    private WebElement dialogRemoveButton() { return driver.findElement(By.xpath("//button[text() = 'REMOVE']")); }
    private String confirmationBannerLocator = "//strong[text() = 'Confirmation']";
    private WebElement menuBigContainer(String college) { return driver.findElement(By.xpath("//a[text() = '" + college + "']/../../../td[last()]//button/preceding-sibling::div")); }
    private String transitionDimmer = "div.ui.page.modals.dimmer.transition.visible.active";
}

