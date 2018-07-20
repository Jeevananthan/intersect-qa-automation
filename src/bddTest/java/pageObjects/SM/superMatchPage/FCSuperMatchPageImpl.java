package pageObjects.SM.superMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
            case "Location" : verifyTooltips("location.tooltips.titles.list");
                break;
            case "Academics" :
                bachelorsOption().click();
                verifyTooltips("academics.tooltips.titles.list");
                verifyTooltips("academics.degree.type.labels.list");
                verifyTooltips("academics.degree.type.text.list");
                break;
            case "Admission" :
                verifyTooltips("admission.tooltips.titles.list");
                verifyTooltips("admission.tooltips.text.list");
                break;
            case "Diversity" : verifyTooltips("diversity.tooltips.titles.list");
                break;
            case "Institution Characteristics" : verifyTooltips("institution.characteristics.titles.list");
                break;
            case "Cost" : verifyTooltips("cost.tooltips.titles.list");
                break;
            case "Student Life" : verifyTooltips("student.life.tooltips.titles.list");
                break;
            case "Athletics" : verifyTooltips("athletics.tooltips.titles.list", tabName.split(":")[1]);
                break;
            case "Resources" : verifyTooltips("resources.tooltips.titles.list");
                break;
        }
    }

    private void verifyTooltips(String propertiesEntry, String... sportOption) {
        switch(propertiesEntry) {
            case "resources.tooltips.text.list" :
                List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < tooltipsList.size(); i++) {
                    tooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipText().getText(),
                            tooltipText().getText().equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    tooltipsList.get(i).click();
                }
                break;
            case "athletics.tooltips.titles.list" :
                addSportButton().click();
                sportField().click();
                sportOption(sportOption[0]).click();
                List<WebElement> athleticsTooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < athleticsTooltipsList.size(); i++) {
                    athleticsTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(),
                            tooltipTitle().getText().equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    athleticsTooltipsList.get(i).click();
                }
                break;
            case "scores.tooltips.titles" :
                getWhyButtonByPosition("1").sendKeys(Keys.RETURN);
                List<WebElement> scoresTooltipsList = driver.findElements(By.cssSelector(tooltipsInWhyDrawerLocator));
                for (int i = 0; i < scoresTooltipsList.size(); i++) {
                    scoresTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(),
                            tooltipTitle().getText().equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    scoresTooltipsList.get(i).click();
                }
                break;
            case "academics.degree.type.labels.list" :
                List<WebElement> tooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                List<WebElement> labels = driver.findElements(By.cssSelector(tooltipLabelsLocator));
                tooltipList.get(0).click();
                for(int i = 0; i < labels.size(); i++) {
                    Assert.assertTrue("The label is not displayed in Degree Type", labels.get(i).getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                }
                tooltipList.get(0).click();
                break;
            case "academics.degree.type.text.list" :
                List<WebElement> academicsTooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                List<WebElement> textBlocks = driver.findElements(By.cssSelector(tooltipTextBlocksLocator));
                academicsTooltipList.get(0).click();
                for(int i = 0; i < textBlocks.size(); i++) {
                    Assert.assertTrue("The text block is not displayed in Degree Type", textBlocks.get(i).getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                }
                academicsTooltipList.get(0).click();
                break;
            case "academics.tooltips.titles.list" :
                List<WebElement> academicsTitleTooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for(int i = 0; i < 2; i++) {
                    academicsTitleTooltipList.get(i).click();
                    Assert.assertTrue("The title is not displayed in Degree Type", tooltipTitle().getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    academicsTitleTooltipList.get(i).click();
                }
                break;
            case "admission.tooltips.titles.list" :
                List<WebElement> admissionTooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < admissionTooltipsList.size(); i++) {
                    admissionTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    admissionTooltipsList.get(i).click();
                }
                break;
            case "admission.tooltips.text.list" :
                List<WebElement> admissionTooltipsListForText = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < admissionTooltipsListForText.size(); i++) {
                    if(i == 0) {
                        admissionTooltipsListForText.get(i).click();
                        Assert.assertTrue("The tooltip title is incorrect.", gpaTextBlock().getText().
                                contains(getListFromPropFile(propertiesFilePath, ";",
                                        propertiesEntry).get(i)));
                        admissionTooltipsListForText.get(i).click();
                    } else {
                        admissionTooltipsListForText.get(i).click();
                        Assert.assertTrue("The tooltip title is incorrect.", admissionTooltipText().getText().
                                equals(getListFromPropFile(propertiesFilePath, ";",
                                        propertiesEntry).get(i)));
                        admissionTooltipsListForText.get(i).click();
                    }
                }
                break;
            default:
                List<WebElement> genericTooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < genericTooltipsList.size(); i++) {
                    genericTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    genericTooltipsList.get(i).click();
                }
                break;
        }
    }

    public void verifyTooltipsInSection(String sectionName) {
        switch (sectionName) {
            case "Fit Score" : verifyTooltipsInSearchHeader(fitScoreTooltipButton(), "fit.score.title", "fit.score.text");
                break;
            case "Academic Match" : verifyTooltipsInSearchHeader(academicMatchTooltipButton(), "academic.match.title", "academic.match.text");
                break;
            case "Scores" : verifyTooltips("scores.tooltips.titles");
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
        openSavedSearchesDropdown();
        getSavedSearchesDropdownOption(optionName).click();
    }

    public void verifySelectedOption(String selectedOption) {
        Assert.assertTrue("The option was not selected", selectedOption().getText().equals(selectedOption));
    }

    public void selectOptionInDropdown(String maxCost, String costOption) {
        tabOption("Cost").click();
        costOption(costOption).click();
        waitUntil(ExpectedConditions.elementToBeClickable(maxCostDropdown()));
        maxCostDropdown().click();
        maxCostOption(maxCost).click();
        tabOption("Cost").click();
    }

    public void createFifteenSaveSearch(){

        String resourcesOptions[] = {"Learning Differences Support","Academic/Career Counseling","Counseling Services",
                "Tutoring Services","Remedial Services","ESL/ELL Services","Physical Accessibility",
                "Services for the Blind or Visually Impaired","Services for the Deaf and Hard of Hearing",
                "Asperger's/Autism Support","Day Care Services"};
        int counter = 1;
        if (getSaveSearchDisableMenu().isDisplayed()){
            for (String res: resourcesOptions) {
                searchPage.setResourcesCriteria(res);
                clickSaveSearchButton();
                searchPage.saveSearchWithName("Search" + counter);
                searchPage.verifyConfirmationMessage();
                verifySavedSearchInDropdown("Search" + counter);
                counter++;
                searchPage.unsetResourcesCriteria(res);
            }
            searchPage.setResourcesCriteria("Learning Differences Support");
            String resourcesOptionTwo[] = {"Academic/Career Counseling", "Counseling Services", "Tutoring Services", "Remedial Services"};
            for (String resTwo : resourcesOptionTwo) {
                searchPage.setResourcesCriteria(resTwo);
                clickSaveSearchButton();
                searchPage.saveSearchWithName("Search" + counter);
                searchPage.verifyConfirmationMessage();
                verifySavedSearchInDropdown("Search" + counter);
                counter++;
            }
        }
    }

    public void verifySaveSearchMessage(String ExpMessage) {
        String ActMessage = driver.findElement(By.xpath("//body")).getText();
        Assert.assertTrue("Error message for adding the 16th save search is not displaying.", ActMessage.contains(ExpMessage));
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

    public void createOneSaveSearch(String saveSearchName, String resourceFitCriteriaOption){
        searchPage.setResourcesCriteria(resourceFitCriteriaOption);
        clickSaveSearchButton();
        searchPage.saveSearchWithName(saveSearchName);
        searchPage.verifyConfirmationMessage();
        verifySavedSearchInDropdown(saveSearchName);
        searchPage.unsetResourcesCriteria(resourceFitCriteriaOption);
    }

    public void checkDeleteIconInSaveSearch(String saveSearchName){
        savedSearchesDropdown().click();
        Assert.assertTrue("For "+saveSearchName+" Save Search, delete icon is not displaying.",saveSearchDeleteIcon(saveSearchName).isDisplayed());
    }

    public void verifySaveSearchDeleteConfirmationPopup(String saveSearchName){
        saveSearchDeleteIcon(saveSearchName).click();
        String headerText = saveSearchDeletePopupHeaderText().getText();
        Assert.assertTrue("Header text reads 'Are you sure you want to delete: "+saveSearchName+" is not displaying.", headerText.contains("Are you sure you want to delete: "+saveSearchName+"?"));
        Assert.assertTrue("This will permanently remove this search. message is not displaying.", text("This will permanently remove this search.").isDisplayed());
        Assert.assertTrue("'YES, DELETE button is not displaying.", button("YES, DELETE").isDisplayed());
        Assert.assertTrue("''NO, CANCEL button is not displaying.", button("NO, CANCEL").isDisplayed());
        button("NO, CANCEL").click();
    }

    public void verifyClickOutsideClosePopup(String saveSearchName){
        savedSearchesDropdown().click();
        saveSearchDeleteIcon(saveSearchName).click();
        new Actions(driver).moveToElement(savedSearchesDropdown()).click().perform();
    }

    public void deleteSaveSearch(String saveSearchName){
        savedSearchesDropdown().click();
        saveSearchDeleteIcon(saveSearchName).click();
        button("YES, DELETE").click();
        waitForUITransition();
        Assert.assertTrue(saveSearchName+ "save search is not deleted.", getDisableChosseOneDropdown().isDisplayed());
    }

    public void openSavedSearchesDropdown()
    {
        waitUntil(ExpectedConditions.attributeToBe(savedSearchesDropdown1(), "aria-disabled", "false"));

        if(savedSearchesDropdown1().getAttribute("aria-expanded").equals("false"))
            savedSearchesDropdown1().click();
    }

    // Locators Below

    private WebElement getDisableChosseOneDropdown(){return driver.findElement(By.xpath("//div[@class='ui disabled scrolling pointing dropdown']"));}
    private WebElement saveSearchDeletePopupHeaderText(){return driver.findElement(By.xpath("//div[@class='header']"));}
    private WebElement saveSearchDeleteIcon(String saveSearchName) { return driver.findElement(By.xpath("//span[text()='"+saveSearchName+"']/../i[//i[@class='trash large icon right floated']]"));}
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
    private WebElement savedSearchesDropdown1() {return driver.findElement(By.xpath("//div[@class='supermatch-saved-searches']/div[@role='listbox']"));}
    private String savedSearchesListLocator = "div.menu.transition.visible div span";
    private WebElement getSavedSearchesDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@role='option']/span[text()='" + optionName + "']")); }
    private WebElement selectedOption() { return driver.findElement(By.cssSelector("div.ui.pointing.dropdown div.text")); }
    private WebElement gpaTextBlock() { return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] div.content div")); }
    private WebElement admissionTooltipText() { return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] div.content")); }
    private WebElement getSaveSearchDisableMenu(){ return driver.findElement(By.xpath("//div[@class='ui disabled scrolling pointing dropdown']"));}
    private WebElement tabOption(String optionName) { return driver.findElement(By.xpath("//li[text() = '" + optionName + "']")); }
    private WebElement costOption(String optionName) {
        return driver.findElement(By.xpath("//label[text() = '" + optionName + "']"));
    }
    private WebElement maxCostDropdown() { return driver.findElement(By.cssSelector("div#cost-maximum div[role=\"alert\"]")); }
    private WebElement maxCostOption(String option) { return driver.findElement(By.xpath("//div[@class = 'visible menu transition']/div/span[text() = '" + option + "']")); }
    private WebElement maximumTuitionAndFeesOption() { return driver.findElement(By.cssSelector("label[for=\"radio-tuition-0\"]")); }
    private WebElement maximumTotalCostOption() { return driver.findElement(By.cssSelector("label[for=\"radio-includeRoomAndBoardKey-1\"]")); }
    private WebElement defaultLabelInDropdown() { return driver.findElement(By.cssSelector("div#cost-maximum div.default.text")); }
    private WebElement labelAtRightOfDropdown() { return driver.findElement(By.cssSelector("div.five.wide.column")); }
    private WebElement mustHaveMatchLegend() { return driver.findElement(By.cssSelector("div.column.supermatch-sidebar-criteria-list-column h3.supermatch-sidebar-criteria-list-title + div p")); }
    private WebElement niceToHaveMatchLegend() { return driver.findElement(By.cssSelector("div.row.supermatch-sidebar-criteria-list-row div:nth-of-type(2).column p")); }
    private String spinnerLocator = "div.ui.active.loader";

}
