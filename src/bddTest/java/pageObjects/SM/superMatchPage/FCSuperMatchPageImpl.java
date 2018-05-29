package pageObjects.SM.superMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FCSuperMatchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSMTooltipsContent%sSMTooltipsContent.properties",fs ,fs ,fs ,fs ,fs);

    public FCSuperMatchPageImpl() {
        logger = Logger.getLogger(FCSuperMatchPageImpl.class);
    }
    private NewSuperMatchPageImpl newSuperMatch = new NewSuperMatchPageImpl();
    private SearchPageImpl searchPage = new SearchPageImpl();

    public void verifySuperMatchBanner() {
        driver.switchTo().frame("supermatch");
        Assert.assertTrue("The banner for SuperMatch is not displayed", superMatchBanner().getText().equals(superMatchBannerMessage));
        driver.switchTo().defaultContent();
    }

    public void verifySuperMatchBannerLink() {
        driver.switchTo().frame("supermatch");
        superMatchBannerLink().click();
        String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntilElementExists(newSuperMatch.collegeSearchHeader());
        Assert.assertTrue("The new SuperMatch is not disaplyed", newSuperMatch.collegeSearchHeader().isDisplayed());
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
        }
        driver.switchTo().window(winHandleBefore);
        driver.switchTo().defaultContent();
    }

    public void verifyTooltipsInFitCriteria(String tabName) {

        searchPage.chooseFitCriteriaTab(tabName);
        switch (tabName.split(":")[0]) {
            case "Location" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "location.tooltips.titles.list"));
                break;
            case "Academics" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "academics.tooltips.titles.list"));
                break;
            case "Admission" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "admission.tooltips.titles.list"));
                break;
            case "Diversity" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "diversity.tooltips.titles.list"));
                break;
            case "Institution Characteristics" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "institution.characteristics.titles.list"));
                break;
            case "Cost" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "cost.tooltips.titles.list"));
                break;
            case "Student Life" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "student.life.tooltips.titles.list"));
                break;
            case "Athletics" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "athletics.tooltips.titles.list"), tabName.split(":")[1]);
                break;
            case "Resources" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "resources.tooltips.text.list"));
                break;
        }
    }

    private void verifyTooltipsInTab(List<String> tooltipsTitlesList, String... sportOption) {
        if (tooltipsTitlesList.equals(getListFromPropFile(propertiesFilePath, ";", "resources.tooltips.text.list"))) {
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipText().getText(),
                        tooltipText().getText().equals(getListFromPropFile(propertiesFilePath, ";",
                                "resources.tooltips.text.list").get(i)));
                tooltipsList.get(i).click();
            }
        } else if (tooltipsTitlesList.equals(getListFromPropFile(propertiesFilePath, ";", "athletics.tooltips.titles.list"))) {
            addSportButton().click();
            sportField().click();
            sportOption(sportOption[0]).click();
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsTitlesList.get(i)));
                tooltipsList.get(i).click();
            }
        } else if (tooltipsTitlesList.equals(getListFromPropFile(propertiesFilePath, ";", "scores.tooltips.titles"))) {
            getWhyButtonByPosition("1").sendKeys(Keys.RETURN);
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInWhyDrawerLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsTitlesList.get(i)));
                tooltipsList.get(i).click();
            }
        }

        else {
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsTitlesList.get(i)));
                tooltipsList.get(i).click();
            }
        }
    }

    public void verifyTooltipsInSection(String sectionName) {
        switch (sectionName) {
            case "Fit Score" : verifyTooltipsInSearchHeader(fitScoreTooltipButton(), "fit.score.title", "fit.score.text");
                break;
            case "Academic Match" : verifyTooltipsInSearchHeader(academicMatchTooltipButton(), "academic.match.title", "academic.match.text");
                break;
            case "Scores" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "scores.tooltips.titles"));
                break;
        }
    }

    private void verifyTooltipsInSearchHeader(WebElement button, String titleEntry, String textEntry) {
        button.sendKeys(Keys.RETURN);
        Assert.assertTrue("The title of the tooltip is incorrect", getStringFromPropFile(propertiesFilePath,
                titleEntry).equals(searchHeaderTooltipTitle().getText()));
        Assert.assertTrue("The content of the tooltip is incorrect", getStringFromPropFile(propertiesFilePath,
                textEntry).equals(searchHeaderTooltipText().getText()));
        button.sendKeys(Keys.RETURN);
    }

    public void verifyOnboardingModals(DataTable dataTable) {
        List<String> dataList = dataTable.asList(String.class);
        for (String element : dataList) {
            verifyOnboardingTitle(element);
        }
    }

    private void verifyOnboardingTitle(String title) {
        WebElement onboardingTitle = driver.findElement(By.cssSelector("div.header"));
        String control = onboardingTitle.getText();
        Assert.assertTrue("The title of the onboarding modal is not correct. UI: " + onboardingTitle.getText() +
                " Data: " + title,
                onboardingTitle.getText().equals(title));
        nextButton().click();
        waitUntilPageFinishLoading();
    }

    // Locators Below

    private WebElement superMatchBanner() { return driver.findElement(By.cssSelector("div#reBannerContent")); }
    private WebElement superMatchBannerLink() { return driver.findElement(By.cssSelector("div#reBannerContent strong a")); }
    private String superMatchBannerMessage = "Check it out!  We've been working on an updated SuperMatch experience  Click here to try it out";
    private String tooltipsInTabListLocator = "div.ui.bottom.left.basic.popup.transition.visible.supermatch-menuitem-popup button.supermatch-tooltip-trigger";
    private String tooltipsInWhyDrawerLocator = "table.ui.unstackable.very.basic.center.aligned.table.supermatch-sidebar-academic-qualifications button.supermatch-tooltip-trigger";
    private WebElement tooltipTitle() { return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] div.header")); }
    private WebElement tooltipText() { return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] span")); }
    private WebElement addSportButton() { return driver.findElement(By.cssSelector("button[title=\"Add a Sport\"]")); }
    private WebElement sportField() { return driver.findElement(By.cssSelector("div.default.text")); }
    private WebElement sportOption(String sport) { return driver.findElement(By.xpath("//div[@class='menu transition visible']/div/span[text()='" + sport + "']")); }
    private WebElement fitScoreTooltipButton() { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table csr-header-table']/thead/tr/th[@class='one wide']/button"));}
    private WebElement academicMatchTooltipButton() { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table csr-header-table']/thead/tr/th[@class='two wide']/button"));}
    private WebElement getWhyButtonByPosition(String position) { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table']/tbody/tr["+ Integer.parseInt(position) +"]/td/div/button")); }
    private WebElement searchHeaderTooltipTitle() { return driver.findElement(By.cssSelector("div.header")); }
    private WebElement searchHeaderTooltipText() { return driver.findElement(By.cssSelector("div.content")); }
    private WebElement onboardingModalTitle() { return driver.findElement(By.cssSelector("div.header")); }
    private WebElement nextButton() { return driver.findElement(By.cssSelector("div.onboarding-popup-footer button")); }
}
