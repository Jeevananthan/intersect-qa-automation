package pageObjects.SM.pinnedSchoolsComparePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HS.repVisitsPage.RepVisitsPageImpl;
import pageObjects.SM.surveyPage.SurveyPageImpl;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PinnedSchoolsComparePageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSaveSearchPopupContent%sSaveSearchPopupContent.properties",fs ,fs ,fs ,fs ,fs);

    public PinnedSchoolsComparePageImpl() {
        logger = Logger.getLogger(PinnedSchoolsComparePageImpl.class);
    }

    public void verifyNumberOfDrawersDisplayed(String numberOfDrawers) {
        waitUntilPageFinishLoading();
        List<WebElement> drawersList = driver.findElements(By.cssSelector(drawersListLocator));
        Assert.assertTrue("The number of drawers displayed is incorrect. UI:" + drawersList.size(), Integer.parseInt(numberOfDrawers) == drawersList.size());
    }

    public void verifyAllDrawersExpanded() {
        List<WebElement> drawersList = driver.findElements(By.cssSelector(drawersListLocator));
        List<WebElement> openDrawersList = driver.findElements(By.cssSelector(drawersListLocator + " + tbody"));
        Assert.assertTrue("Not all the drawers are expanded by default", drawersList.size() == openDrawersList.size());
    }

    public void verifyDrawerArrowDirection(String expandedOrCollapsed, String drawerPosition, String arrowDirection) {
        List<WebElement> drawersArrowsList = driver.findElements(By.cssSelector(drawersArrowsLocator));
        Assert.assertTrue("The arrow for " + expandedOrCollapsed + " drawer is not facing " + arrowDirection,
                drawersArrowsList.get(Integer.parseInt(drawerPosition)).getAttribute("class").contains(arrowDirection));
    }

    public void verifyCollapseAllButtonCollapsesDrawers() {
        waitUntilPageFinishLoading();
        List<WebElement> openDrawersList = driver.findElements(By.cssSelector(drawersListLocator + " + tbody"));
        Assert.assertTrue("Not all the drawers are collapsed by the Collapse All button: " + openDrawersList.size(), openDrawersList.size() == 0);
    }

    public void verifyCollapseExpandButtonText(String buttonText) {
        Assert.assertTrue("The text in the Collapse/Expand All button is not correct",
                collapseExpandAllButton().getText().equals(buttonText));
    }

    public void expandDrawerInPosition(String position) {
        List<WebElement> drawerArrowsList = driver.findElements(By.cssSelector(drawersArrowsLocator));
        if(drawerArrowsList.get(Integer.parseInt(position)).getAttribute("class").contains("right")) {
            drawerArrowsList.get(Integer.parseInt(position)).click();
        }
    }

    public void verifyCollapseExpandButtonTextWithExpandedDrawers(String buttonText, String numberOfExpandedDrawers) {
        List<WebElement> openDrawersList = driver.findElements(By.cssSelector(drawersListLocator + " + tbody"));
        if(openDrawersList.size() >= Integer.parseInt(numberOfExpandedDrawers)) {
            Assert.assertTrue("The text in the Collapse/Expand All button is incorrect", collapseExpandAllButton().getText().equals(buttonText));
        }
    }

    public void clickCollapseExpandButton() {
        collapseExpandAllButton().click();
    }

    // Locators Below

    private String drawersListLocator = "thead.toggle-enabled";
    private String drawersArrowsLocator = "div.ui.segment.supermatch-compare-content table.ui.definition.fixed.unstackable.five.column.table.supermatch-expandable-table i:not(.circle)";
    private WebElement collapseExpandAllButton() { return driver.findElement(By.cssSelector("button.ui.teal.basic.button[role='button']:not(.icon)")); }
}
