package pageObjects.SM.superMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            case "Academics" :
                bachelorsOption().click();
                verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "academics.tooltips.titles.list"));
                verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "academics.degree.type.labels.list"));
                verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "academics.degree.type.text.list"));
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
            case "Resources" : verifyTooltipsInTab(getListFromPropFile(propertiesFilePath, ";", "resources.tooltips.titles.list"));
                break;
        }
    }

    private void verifyTooltipsInTab(List<String> tooltipsItemsList, String... sportOption) {
        if (tooltipsItemsList.equals(getListFromPropFile(propertiesFilePath, ";", "resources.tooltips.text.list"))) {
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipText().getText(),
                        tooltipText().getText().equals(getListFromPropFile(propertiesFilePath, ";",
                                "resources.tooltips.text.list").get(i)));
                tooltipsList.get(i).click();
            }
        } else if (tooltipsItemsList.equals(getListFromPropFile(propertiesFilePath, ";", "athletics.tooltips.titles.list"))) {
            addSportButton().click();
            sportField().click();
            sportOption(sportOption[0]).click();
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsItemsList.get(i)));
                tooltipsList.get(i).click();
            }
        } else if (tooltipsItemsList.equals(getListFromPropFile(propertiesFilePath, ";", "scores.tooltips.titles"))) {
            getWhyButtonByPosition("1").sendKeys(Keys.RETURN);
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInWhyDrawerLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsItemsList.get(i)));
                tooltipsList.get(i).click();
            }
        } else if (tooltipsItemsList.equals(getListFromPropFile(propertiesFilePath, ";", "academics.degree.type.labels.list"))) {
            List<WebElement> tooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            List<WebElement> labels = driver.findElements(By.cssSelector(tooltipLabelsLocator));
            tooltipList.get(0).click();
            for(int i = 0; i < labels.size(); i++) {
                Assert.assertTrue("The label is not displayed in Degree Type", labels.get(i).getText().equals(tooltipsItemsList.get(i)));
            }
            tooltipList.get(0).click();
        } else if (tooltipsItemsList.equals(getListFromPropFile(propertiesFilePath, ";", "academics.degree.type.text.list"))) {
            List<WebElement> tooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            List<WebElement> textBlocks = driver.findElements(By.cssSelector(tooltipTextBlocksLocator));
            tooltipList.get(0).click();
            for(int i = 0; i < textBlocks.size(); i++) {
                Assert.assertTrue("The text block is not displayed in Degree Type", textBlocks.get(i).getText().equals(tooltipsItemsList.get(i)));
            }
            tooltipList.get(0).click();
        } else if (tooltipsItemsList.equals(getListFromPropFile(propertiesFilePath, ";", "academics.tooltips.titles.list"))) {
            List<WebElement> tooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for(int i = 0; i < 2; i++) {
                tooltipList.get(i).click();
                Assert.assertTrue("The title is not displayed in Degree Type", tooltipTitle().getText().equals(tooltipsItemsList.get(i)));
                tooltipList.get(i).click();
            }
        }


        else {
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsItemsList.get(i)));
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
        Assert.assertTrue("The title of the tooltip is incorrect\n\nExpected: " + getStringFromPropFile(propertiesFilePath,
                titleEntry) + "\n\nActual: " + searchHeaderTooltipTitle().getText(), getStringFromPropFile(propertiesFilePath,
                titleEntry).equals(searchHeaderTooltipTitle().getText()));
        Assert.assertTrue("The content of the tooltip is incorrect\n\nExpected: " + getStringFromPropFile(propertiesFilePath,textEntry) + "\n\nActual: " + searchHeaderTooltipText().getText(), getStringFromPropFile(propertiesFilePath,
                textEntry).equals(searchHeaderTooltipText().getText()));
        button.sendKeys(Keys.RETURN);
    }

    public void verifyLegendInWhyDrawer(String position, DataTable dataTable) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        List<String> dataList = dataTable.asList(String.class);
        waitUntilElementExists(getWhyButtonByPosition(position));
        getWhyButtonByPosition(position).sendKeys(Keys.RETURN);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        Assert.assertTrue("The 'Match' legend is not correctly displayed", matchLegend().getText().equals(dataList.get(0)));
        Assert.assertTrue("The 'Close Match' legend is not correctly displayed", closeMatchLegend().getText().equals(dataList.get(1)));
        Assert.assertTrue("The 'Data Unknown' legend is not correctly displayed", dataUnknownLegend().getText().equals(dataList.get(2)));
        Assert.assertTrue("The 'Doesn't Match' legend is not correctly displayed", doesntMatchLegend().getText().equals(dataList.get(3)));
        Assert.assertTrue("The '# out of # Must Have criteria are a match' legend is not correctly displayed.",
                mustHaveMatchLegend().getText().replaceAll("[0-9]+", "X").equals(dataList.get(4)));
        Assert.assertTrue("The '# out of # Nice to Have criteria are a match' legend is not correctly displayed.",
                niceToHaveMatchLegend().getText().replaceAll("[0-9]+", "X").equals(dataList.get(5)));
    }

    public void skipModals() {
        if (driver.findElements(By.cssSelector(onboardingHeaderLocator)).size() > 0) {
            chooseFitCriteria().click();
        }
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
                " Expected: " + title,
                onboardingTitle.getText().equals(title));
        nextButton().click();
        waitUntilPageFinishLoading();
    }

    public void enableOnboardingModalsIfDisabled() {
        if(driver.findElements(By.cssSelector(onboardingHeaderLocator)).size() < 1) {
            aboutSuperMatchLink().click();
        }
    }

    public void clickSaveSearchButton() {
        chooseFitCriteria().click();
        saveSearchButton().click();
    }

    public void verifySaveSearchButtonDisabled() {
        System.out.println("Control: " + saveSearchButton().getAttribute("tabindex").equals("-1"));
        Assert.assertTrue("The Save Search button is enabled when it shouldn't", saveSearchButton().getAttribute("tabindex").equals("-1"));
    }

    public void verifySavedSearchInDropdown(String savedSearch) {
        savedSearchesDropdown().click();
        List<WebElement> savedSearchesList = driver.findElements(By.cssSelector(savedSearchesListLocator));
        List<String> savedSearchesStringList = new ArrayList<>();
        for(WebElement savedSearchElement : savedSearchesList) {
            savedSearchesStringList.add(savedSearchElement.getText());
        }
        Assert.assertTrue("The saved search is not displayed in the Saved Searches List", savedSearchesStringList.contains(savedSearch));
    }

    public void selectOptionFromDropdown(String optionName) {
        getSavedSearchesDropdownOption(optionName).click();
    }

    public void verifySelectedOption(String selectedOption) {
        Assert.assertTrue("The option was not selected", selectedOption().getText().equals(selectedOption));
    }

    public void openTab(String tabName) {
        tabOption(tabName).click();
    }

    public void verifyDropdownsWordingInCost(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        maximumTuitionAndFeesOption().click();
        for(List<String> row : details) {
            switch (row.get(0)) {
                case "Maximum Tuition and Fees" :
                    maximumTuitionAndFeesOption().click();
                    Assert.assertTrue("The default text in the dropdown is not correct",
                            defaultLabelInDropdown().getText().equals(row.get(1)));
                    Assert.assertTrue("The label at the right side of the dropdown is incorrect",
                            labelAtRightOfDropdown().getText().equals(row.get(2)));
                    break;
                case "Maximum Total Cost (Tuition, Fees, Room & Board)" :
                    maximumTotalCostOption().click();
                    Assert.assertTrue("The default text in the dropdown is not correct",
                            defaultLabelInDropdown().getText().equals(row.get(1)));
                    Assert.assertTrue("The label at the right side of the dropdown is incorrect",
                            labelAtRightOfDropdown().getText().equals(row.get(2)));
                    break;
            }
        }

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
    private WebElement sportOption(String sport) { return driver.findElement(By.xpath("//div[@class='visible menu transition']/div/span[text()='" + sport + "']")); }
    private WebElement fitScoreTooltipButton() { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table csr-header-table']/thead/tr/th[@class='one wide']/button"));}
    private WebElement academicMatchTooltipButton() { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table csr-header-table']/thead/tr/th[@class='two wide']/button"));}
    private WebElement getWhyButtonByPosition(String position) { return driver.findElement(By.xpath("//table[@class='ui unstackable table csr-results-table']/tbody/tr["+ Integer.parseInt(position) +"]/td/div/button")); }
    private WebElement searchHeaderTooltipTitle() { return driver.findElement(By.cssSelector("div.header")); }
    private WebElement searchHeaderTooltipText() {
        WebElement tooltip = getDriver().findElement(By.xpath("//div[@role='tooltip']"));
        return tooltip.findElement(By.cssSelector("div.content"));
    }
    private WebElement onboardingModalTitle() { return driver.findElement(By.cssSelector(onboardingHeaderLocator)); }
    private String onboardingHeaderLocator = "div.header";
    private WebElement chooseFitCriteria() { return driver.findElement(By.cssSelector("span.sm-hidden-l-down")); }
    private WebElement matchLegend() { return driver.findElement(By.cssSelector("div.seven.wide.column.supermatch-sidebar-criteria-legend-match")); }
    private WebElement closeMatchLegend() { return driver.findElement(By.cssSelector("div.nine.wide.column.supermatch-sidebar-criteria-legend-icon")); }
    private WebElement dataUnknownLegend() { return driver.findElement(By.cssSelector("div.seven.wide.column.supermatch-sidebar-criteria-legend-icon")); }
    private WebElement doesntMatchLegend() { return driver.findElement(By.cssSelector("div.eight.wide.column.supermatch-sidebar-criteria-legend-icon")); }
    private WebElement nextButton() { return driver.findElement(By.cssSelector("div.onboarding-popup-footer button")); }
    private WebElement aboutSuperMatchLink() { return driver.findElement(By.cssSelector("i.question.circle.icon + span")); }
    private WebElement bachelorsOption() { return driver.findElement(By.cssSelector("input[value *=\"Bachelor\"] + label")); }
    private String tooltipLabelsLocator = "label.supermatch-label-text";
    private String tooltipTextBlocksLocator = "div[id*='supermatch-tooltip-'] li";
    private WebElement saveSearchButton() { return driver.findElement(By.cssSelector("div.supermatch-save-search button.ui.teal.basic.button")); }
    private WebElement savedSearchesDropdown() { return driver.findElement(By.cssSelector("div.supermatch-saved-searches div div.text")); }
    private String savedSearchesListLocator = "div.menu.transition.visible div span";
    private WebElement getSavedSearchesDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@role='option']/span[text()='" + optionName + "']")); }
    private WebElement selectedOption() { return driver.findElement(By.cssSelector("div.ui.pointing.dropdown div.text")); }
    private WebElement tabOption(String optionName) { return driver.findElement(By.xpath("//li[text() = '" + optionName + "']")); }
    private WebElement maximumTuitionAndFeesOption() { return driver.findElement(By.cssSelector("label[for=\"radio-tuition-0\"]")); }
    private WebElement maximumTotalCostOption() { return driver.findElement(By.cssSelector("label[for=\"radio-includeRoomAndBoardKey-1\"]")); }
    private WebElement defaultLabelInDropdown() { return driver.findElement(By.cssSelector("div#cost-maximum div.default.text")); }
    private WebElement labelAtRightOfDropdown() { return driver.findElement(By.cssSelector("div.five.wide.column")); }
    private WebElement mustHaveMatchLegend() { return driver.findElement(By.cssSelector("div.column.supermatch-sidebar-criteria-list-column h3.supermatch-sidebar-criteria-list-title + div p")); }
    private WebElement niceToHaveMatchLegend() { return driver.findElement(By.cssSelector("div.row.supermatch-sidebar-criteria-list-row div:nth-of-type(2).column p")); }
    private String spinnerLocator = "div.ui.active.loader";
}
