package pageObjects.SM.superMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
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

    public void verifyTooltipsInTab(String tabName) {

        searchPage.chooseFitCriteriaTab(tabName);
        switch (tabName.split(":")[0]) {
            case "Location" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "location.tooltips.titles.list"));
            break;
            case "Academics" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "academics.tooltips.titles.list"));
            break;
            case "Admission" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "admission.tooltips.titles.list"));
            break;
            case "Diversity" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "diversity.tooltips.titles.list"));
            break;
            case "Institution Characteristics" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "institution.characteristics.titles.list"));
            break;
            case "Cost" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "cost.tooltips.titles.list"));
            break;
            case "Student Life" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "student.life.tooltips.titles.list"));
            break;
            case "Athletics" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "athletics.tooltips.titles.list"), tabName.split(":")[1]);
            break;
            case "Resources" : verifyTooltips(getListFromPropFile(propertiesFilePath, ";", "resources.tooltips.text.list"));
            break;
        }
    }

    private void verifyTooltips(List<String> tooltipsTitlesList, String... sportOption) {
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

    // Locators Below

    private WebElement superMatchBanner() { return driver.findElement(By.cssSelector("div#reBannerContent")); }
    private WebElement superMatchBannerLink() { return driver.findElement(By.cssSelector("div#reBannerContent strong a")); }
    private String superMatchBannerMessage = "Check it out!  We've been working on an updated SuperMatch experience  Click here to try it out";
    private String tooltipsInTabListLocator = "div.ui.bottom.left.basic.popup.transition.visible.supermatch-menuitem-popup button.supermatch-tooltip-trigger";
    private WebElement tooltipTitle() { return driver.findElement(By.cssSelector("div[id*='supermatch-toolip-'] div.header")); }
    private WebElement tooltipText() { return driver.findElement(By.cssSelector("div[id*='supermatch-toolip-'] span")); }
    private WebElement addSportButton() { return driver.findElement(By.cssSelector("button[title=\"Add a Sport\"]")); }
    private WebElement sportField() { return driver.findElement(By.cssSelector("div.default.text")); }
    private WebElement sportOption(String sport) { return driver.findElement(By.xpath("//div[@class='menu transition visible']/div/span[text()='" + sport + "']")); }
}
