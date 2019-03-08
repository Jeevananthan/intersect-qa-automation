package pageObjects.SM.superMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.CM.groupsPage.GroupsPageImpl;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FCSuperMatchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSMTooltipsContent%sSMTooltipsContent.properties",fs ,fs ,fs ,fs ,fs);
    private static String startOverPopupPropertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sStartOverPopupContent%sStartOverPopupContent.properties",fs ,fs ,fs ,fs ,fs);

    public FCSuperMatchPageImpl() {
        logger = Logger.getLogger(FCSuperMatchPageImpl.class);
    }
    private NewSuperMatchPageImpl newSuperMatch = new NewSuperMatchPageImpl();
    private SearchPageImpl searchPage = new SearchPageImpl();
    private GroupsPageImpl groupsPage = new GroupsPageImpl();
    static String saveSearchName;

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
                verifyTooltips("admission.your.gpa.table");
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
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(spinnerLocator), 0));
        List<WebElement> gpaTooltipsIconsInResults;
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
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + searchHeaderTooltipTitle().getText(),
                            searchHeaderTooltipTitle().getText().equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    athleticsTooltipsList.get(i).click();
                }
                break;
            case "scores.tooltips.titles" :
                getWhyButtonByPosition("1").sendKeys(Keys.RETURN);
                List<WebElement> scoresTooltipsList = driver.findElements(By.cssSelector(tooltipsInWhyDrawerLocator));
                for (int i = 0; i < scoresTooltipsList.size(); i++) {
                    scoresTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + searchHeaderTooltipTitle().getText(),
                            searchHeaderTooltipTitle().getText().equals(getListFromPropFile(propertiesFilePath, ";",
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
                    Assert.assertTrue("The title is not displayed in Degree Type", searchHeaderTooltipTitle().getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    academicsTitleTooltipList.get(i).click();
                }
                break;
            case "admission.tooltips.titles.list" :
                List<WebElement> admissionTooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < admissionTooltipsList.size(); i++) {
                    admissionTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + searchHeaderTooltipTitle().getText(), searchHeaderTooltipTitle().getText().
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
                        Assert.assertTrue("The tooltip title is incorrect. UI:" + searchHeaderTooltipText().getText(), searchHeaderTooltipText().getText().
                                contains(getListFromPropFile(propertiesFilePath, ";",
                                        propertiesEntry).get(i)));
                        admissionTooltipsListForText.get(i).click();
                    } else {
                        admissionTooltipsListForText.get(i).click();
                        Assert.assertTrue("The tooltip title is incorrect.", searchHeaderTooltipText().getText().
                                equals(getListFromPropFile(propertiesFilePath, ";",
                                        propertiesEntry).get(i)));
                        admissionTooltipsListForText.get(i).click();
                    }
                }
                break;
            case "admission.your.gpa.table" :
                WebElement yourGPAIcon = driver.findElements(By.cssSelector(tooltipsInTabListLocator)).get(0);
                yourGPAIcon.click();

                List<WebElement> letterGrade = driver.findElements(By.cssSelector(letterGradeListLocator));
                List<WebElement> percentGrade = driver.findElements(By.cssSelector(percentGradeListLocator));
                List<WebElement> fourScale = driver.findElements(By.cssSelector(fourScaleListLocator));

                List<String> letterGradeStrings = new ArrayList<>();
                List<String> percentGradeStrings = new ArrayList<>();
                List<String> fourScaleStrings = new ArrayList<>();

                for (int i = 0; i < letterGrade.size(); i++) {
                    letterGradeStrings.add(letterGrade.get(i).getText());
                    percentGradeStrings.add(percentGrade.get(i).getText());
                    fourScaleStrings.add(fourScale.get(i).getText());
                }

                Assert.assertTrue("The values in the GPA scores table are not correct.", letterGradeStrings.equals
                        (getListFromPropFile(propertiesFilePath, ";", "admission.gpa.letter.grade")));
                Assert.assertTrue("The values in the GPA scores table are not correct.", percentGradeStrings.equals
                        (getListFromPropFile(propertiesFilePath, ";", "admission.gpa.percent.grade")));
                Assert.assertTrue("The values in the GPA scores table are not correct.", fourScaleStrings.equals
                        (getListFromPropFile(propertiesFilePath, ";", "admission.gpa.4.scale")));

                break;

            case "gpa.results.table.title" :
                waitUntil(ExpectedConditions.elementToBeClickable(searchPage.firstWhyButton()));
                gpaTooltipsIconsInResults = driver.findElements(By.cssSelector(gpaTooltipIconInResultsLocator));
                gpaTooltipsIconsInResults.get(0).sendKeys(Keys.RETURN);
                Assert.assertTrue("The tooltip title is incorrect.", searchHeaderTooltipTitle().getText().equals(getStringFromPropFile(propertiesFilePath, propertiesEntry)));
                gpaTooltipsIconsInResults.get(0).sendKeys(Keys.RETURN);
                break;
            case "gpa.results.table.content" :
                gpaTooltipsIconsInResults = driver.findElements(By.cssSelector(gpaTooltipIconInResultsLocator));
                gpaTooltipsIconsInResults.get(0).click();
                Assert.assertTrue("The tooltip text is incorrect.", searchHeaderTooltipText().getText().contains(getStringFromPropFile(propertiesFilePath, propertiesEntry)));
                gpaTooltipsIconsInResults.get(0).click();
                break;
            case "diversity.tooltips.titles.list" :
                List<WebElement> diversityTooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < diversityTooltipsList.size(); i++) {
                    diversityTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + searchHeaderTooltipTitle().getText(), searchHeaderTooltipTitle().getText().
                            equals(getListFromPropFile(propertiesFilePath, ";",
                                    propertiesEntry).get(i)));
                    diversityTooltipsList.get(i).click();
                }
                break;
            default:
                List<WebElement> genericTooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
                for (int i = 0; i < genericTooltipsList.size(); i++) {
                    genericTooltipsList.get(i).click();
                    Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + searchHeaderTooltipTitle().getText(), searchHeaderTooltipTitle().getText().
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
            case "GPA" :
                verifyTooltips("gpa.results.table.title");
                verifyTooltips("gpa.results.table.content");
                break;
            case "Scores" : verifyTooltips("scores.tooltips.titles");
                break;
        }
    }

    private void verifyTooltipsInSearchHeader(WebElement button, String titleEntry, String textEntry) {
        waitUntilPageFinishLoading();
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

    public void skipModalPopups() {
        skipModals();
    }

    public static void skipModals() {
        if (driver.findElements(By.cssSelector(onboardingHeaderLocator)).size() > 0) {
            superMatchCollegeSearchHeader().click();
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
        //chooseFitCriteria().click();
        saveSearchButton().sendKeys(Keys.RETURN);
    }

    public void verifySaveSearchButtonDisabled() {
        if (!(searchPage.getAllPillsCloseIcon().size()>0)) {
            System.out.println("Control: " + saveSearchButton().getAttribute("tabindex").equals("-1"));
            Assert.assertTrue("The Save Search button is enabled when it shouldn't", saveSearchButton().getAttribute("tabindex").equals("-1"));
        }
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

    public void verifyStartOverButtonDisabled() {
        Assert.assertTrue("The Start Over button is not disabled", disabledStartOverButton().isDisplayed());
    }

    public void verifyStartOverPopupContent() {
        waitUntilPageFinishLoading();
        enabledStartOverButton().click();
        Assert.assertTrue("The title in the Start Over popup is incorrect", startOverPopupTitle().getText().
                equals(getStringFromPropFile(startOverPopupPropertiesFilePath, "start.over.popup.title")));
        Assert.assertTrue("The text body in the Start Over popup is incorrect", startOverTextBody().getText().
                equals(getStringFromPropFile(startOverPopupPropertiesFilePath, "start.over.popup.text.body")));
        Assert.assertTrue("The text in the 'Yes' button is incorrect", startOverYesButton().getText().
                equals(getStringFromPropFile(startOverPopupPropertiesFilePath, "start.over.popup.yes.button")));
        Assert.assertTrue("The text in the 'No' button is incorrect", startOverNoButton().getText().
                equals(getStringFromPropFile(startOverPopupPropertiesFilePath, "start.over.popup.no.button")));
    }

    public void verifyResultsAfterCancelButton() {
        startOverNoButton().click();
        Assert.assertTrue("The results are not displayed after clicking 'No, Cancel', in the Start Over popup",
                yourResultsHeader().isDisplayed());
    }

    public void clickStartOverButton() {
        enabledStartOverButton().click();
    }

    public void verifyFitCriteriaRemovedAfterStartOverButton() {
        startOverYesButton().click();
        Assert.assertTrue("The results are not removed after clicking the 'TYes, start over' button",
                noResultsYetHeader().isDisplayed());
    }

    public void selectOptionInDropdown(String maxCost, String costOption) {
        tabOption("Cost").click();
        costOption(costOption).click();
        waitUntil(ExpectedConditions.elementToBeClickable(maxCostDropdown()));
        maxCostDropdown().click();
        maxCostOption(maxCost).click();
        tabOption("Cost").click();
    }

    public void createTotalFifteenSaveSearch(){
        String chooseOne = getChooseOneWebElement().getAttribute("aria-disabled");
        int totalSaveSearchExist=0;
        if (chooseOne.equals("true")) {
            createSpecificSaveSearch(totalSaveSearchExist);
        }else {
            getChooseOneWebElement().click();
            totalSaveSearchExist = allSaveSearchOptions().size()-1;
            createSpecificSaveSearch(totalSaveSearchExist);
        }
    }

    public void createSpecificSaveSearch(int totalSaveSearchExist){
        String resourcesOptions[] = {"Learning Differences Support","Academic/Career Counseling","Counseling Services",
                "Tutoring Services","Remedial Services","ESL/ELL Services","Physical Accessibility",
                "Services for the Blind or Visually Impaired","Services for the Deaf and Hard of Hearing",
                "Asperger's/Autism Support","Day Care Services"};
        int counter = 1;
        int saveSearchNeedToCreate = 15-totalSaveSearchExist;
        for (String res: resourcesOptions) {
            if (saveSearchNeedToCreate==0)
                break;
            searchPage.setResourcesCriteria(res);
            clickSaveSearchButton();
            String randomSearchName = counter + "Search" + Integer.toString(new Random().nextInt(9999));
            searchPage.saveSearchWithName(randomSearchName);
            searchPage.verifyConfirmationMessage();
            verifySavedSearchInDropdown(randomSearchName);
            counter++;
            searchPage.unsetResourcesCriteria(res);
            saveSearchNeedToCreate--;
        }
        searchPage.setResourcesCriteria("Learning Differences Support");
        String resourcesOptionTwo[] = {"Academic/Career Counseling", "Counseling Services", "Tutoring Services", "Remedial Services"};
        for (String resTwo : resourcesOptionTwo) {
            if (saveSearchNeedToCreate==0)
                break;
            searchPage.setResourcesCriteria(resTwo);
            clickSaveSearchButton();
            String randomSearchName = counter + "Search" + Integer.toString(new Random().nextInt(9999));
            searchPage.saveSearchWithName(randomSearchName);
            searchPage.verifyConfirmationMessage();
            verifySavedSearchInDropdown(randomSearchName);
            counter++;
            saveSearchNeedToCreate--;
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

    public void createOneSaveSearch(String resourceFitCriteriaOption) {
        savedSearchesDropdown().click();
        int saveSearchCount = allSaveSearchOptions().size();
        if (saveSearchCount >= 15) {
            logger.info("We already have 15 save search, to check the delete save search functionality, we need to delete one of the save search....");
        } else {
            createSaveSearch(resourceFitCriteriaOption);
        }
    }

    private void createSaveSearch(String resourceFitCriteriaOption){
        Random rand = new Random();
        int randomNumber = rand.nextInt(1000);
        saveSearchName = "Search" + Integer.toString(randomNumber);
        searchPage.setResourcesCriteria(resourceFitCriteriaOption);
        clickSaveSearchButton();
        searchPage.saveSearchWithName(saveSearchName);
        searchPage.verifyConfirmationMessage();
        verifySavedSearchInDropdown(saveSearchName);
        searchPage.unsetResourcesCriteria(resourceFitCriteriaOption);
    }

    public void checkSaveSearch(String fitCriteriaOption){
        try {
            savedSearchesDropdown().click();
        }catch (WebDriverException ex){
            createSaveSearch(fitCriteriaOption);
            savedSearchesDropdown().click();
        }
        int saveSearchCount = allSaveSearchOptions().size();
        if (saveSearchCount>0){
            saveSearchName = getChooseOneWebElement().findElement(By.xpath("./div[2]/div[2]/span")).getText();
        }
    }

    public void checkDeleteIconInSaveSearch() {
        int saveSearchCount = allSaveSearchOptions().size();
        if (saveSearchCount >= 15) {
            saveSearchName = allSaveSearchOptions().get(1).getText();
        }
        Assert.assertTrue("For " + saveSearchName + " Save Search, delete icon is not displaying.", saveSearchDeleteIcon(saveSearchName).isDisplayed());
    }

    public void verifySaveSearchDeleteConfirmationPopup() {
        saveSearchDeleteIcon(saveSearchName).click();
        String headerText = saveSearchDeletePopupHeaderText().getText();
        Assert.assertTrue("Header text reads 'Are you sure you want to delete: " + saveSearchName + " is not displaying.", headerText.contains("Are you sure you want to delete: " + saveSearchName + "?"));
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

    public void deleteSaveSearch() {
        savedSearchesDropdown().click();
        try {
            saveSearchDeleteIcon(saveSearchName).click();
            button("YES, DELETE").click();
        } catch (Exception e) {
            logger.info("Could not find saved search with name: " + saveSearchName);
        }
        savedSearchesDropdown().click();
    }

    public void deleteSavedSearchByName(String savedSearch) {
        try {
            savedSearchesDropdown().click();
            setImplicitWaitTimeout(2);
            saveSearchDeleteIcon(savedSearch).click();
            button("YES, DELETE").click();
            resetImplicitWaitTimeout();
            savedSearchesDropdown().click();
        } catch (Exception e) {
            logger.info("Could not find saved search with name: " + savedSearch);
            resetImplicitWaitTimeout();
        }
    }

    public void cancelSaveSearchPopup() {
        button("Cancel").click();
    }

    public void openSavedSearchesDropdown()
    {
        waitUntil(ExpectedConditions.attributeToBe(savedSearchesDropdown1(), "aria-disabled", "false"));

        if(savedSearchesDropdown1().getAttribute("aria-expanded").equals("false"))
            savedSearchesDropdown1().click();
    }

    public void verifyNoGPATooltipIcon() {
        waitUntil(ExpectedConditions.elementToBeClickable(getWhyButtonByPosition("1")));
        Assert.assertTrue("The tooltip icon for GPA in the results table is displayed when it should not",
                driver.findElements(By.cssSelector(gpaTooltipIconInResultsLocator)).size() == 0);
    }

    /**
     * Verifies there is no text (String text)  on the Page
     */
    public void verifyThereIsNoTextOnThePage(String text) {

        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(), '"+text+"'")));
    }

    public void deleteAllSavedSearches() {
        if (driver.findElements(By.cssSelector(disabledSavedSearchesButtonLocator)).size() == 0) {
            savedSearchesDropdown().click();
            List<WebElement> deleteIconList = driver.findElements(By.cssSelector(savedSearchesListLocator + " + i"));
            savedSearchesDropdown().click();
            for (WebElement deleteIcon : deleteIconList) {
                savedSearchesDropdown().click();
                deleteIcon.click();
                yesDeleteButton().click();
            }
        }
    }

    /**
     * Verifies the text of a link in the "More" link inside the SuperMatch footer, as well as the location the user is sent to,
     * @param link Name of the link to navigate to in the "More" menu in the SM footer.
     */
    public void verifyFooterLink(String link) {
        // Check if the More menu is already open, and if not, click it to open.
        try {
            setImplicitWaitTimeout(1);
            getDriver().findElement(By.xpath("//div[@class='supermatch-footer-popup']"));
            resetImplicitWaitTimeout();
        } catch (NoSuchElementException nsee) {
            jsClick(footerMoreButton());
            resetImplicitWaitTimeout();
        }
        waitUntilPageFinishLoading();
        switch(link) {
            case "Upcoming Visits":
                footerUpcomingVisitsLink().click();
                waitUntilPageFinishLoading();
                switchToNewestWindow();
                softly().assertThat(getDriver().getCurrentUrl()).contains("/colleges/visits");
                getDriver().close();
                switchToParentWindow();
                break;
            case "Events":
                footerEventsAppLink().click();
                waitUntilPageFinishLoading();
                switchToNewestWindow();
                waitUntilPageFinishLoading();
                softly().assertThat(getDriver().getCurrentUrl()).contains("college-events");
                getDriver().close();
                switchToParentWindow();
                break;
        }
    }

    public void verifyInfoMessage(DataTable dataTable) {
        List<String> messageContents = dataTable.asList(String.class);
        for (String element : messageContents) {
            if(element.contains("Not all fields are required. Remember to only")) {
                Assert.assertTrue("This piece of text was not found: " + element, infoBarExtraWording().getText().contains(element));
            } else {
                Assert.assertTrue("This piece of text was not found: " + element, infoBarMainWording().getText().contains(element));
            }
        }
    }

    public void goToPageFromSMMainMenu() {
        waitUntilPageFinishLoading();
        getMainMenuTab("Colleges").click();
        button("Apply to College").click();
        link("College Events").click();
    }

    public void gotToCollegesImThinkingAboutList() {
        waitUntilPageFinishLoading();
        getMainMenuTab("Colleges").click();
        button("Research Colleges").click();
        link("I'm Thinking About").click();
    }

    public void addCollegeToImThinkingAboutList(String collegeName) {
        gotToCollegesImThinkingAboutList();
        try {
            link("Add Colleges to List").click();
        } catch (WebDriverException e) {
            if (driver.findElements(By.cssSelector(connectorCloseIconLocator)).size() > 0) {
            driver.findElement(By.cssSelector(connectorCloseIconLocator)).click();
                link("Add Colleges to List").click();
            }
        }

        Select lookByDropdown = new Select(driver.findElement(By.xpath(lookByDropdownLocator)));
        lookByDropdown.selectByVisibleText("Keyword");
        lookupByNameField().sendKeys(collegeName);
        button("Go").click();
        if (heartIconInList(collegeName).getAttribute("aria-label").equals("Favorite")) {
            heartIconInList(collegeName).click();
            waitUntil(ExpectedConditions.attributeToBe(heartIconInList(collegeName), "aria-label", "Unfavorite"));
        }
    }

    public void goToCollegesLookingForStudentsLikeYou() {
        link("/colleges").click();
        button("Find Your Fit").click();
        link("College Match").click();
        waitUntilPageFinishLoading();
        getCollegeMatchTab("Colleges Looking For Students Like You").click();
        waitUntilPageFinishLoading();
    }

    // Locators Below

    private WebElement footerMoreButton() {return getDriver().findElement(By.xpath("//span[@class='supermatch-footer-item-text'][text()='More']"));}
    private WebElement footerUpcomingVisitsLink() {return getDriver().findElement(By.xpath("//a/span[text()='Upcoming Visits']"));}
    private WebElement footerEventsAppLink() {return getDriver().findElement(By.xpath("//a/span[text()='Events']"));}
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
    private WebElement searchHeaderTooltipTitle() { return driver.findElement(By.cssSelector("div[id *= 'supermatch-tooltip'] div.header")); }
    private WebElement searchHeaderTooltipText() {
        WebElement tooltip = getDriver().findElement(By.xpath("//div[@role='tooltip']"));
        return tooltip.findElement(By.cssSelector("div.content"));
    }
    private WebElement onboardingModalTitle() { return driver.findElement(By.cssSelector(onboardingHeaderLocator)); }
    private static String onboardingHeaderLocator = "div.header";
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
    private WebElement disabledStartOverButton() { return driver.findElement(By.cssSelector("div.supermatch-save-search-buttons button.supermatch-start-over-button[disabled]")); }
    private WebElement enabledStartOverButton() { return driver.findElement(By.cssSelector("button.ui.teal.basic.button.supermatch-start-over-button")); }
    private WebElement startOverPopupTitle() { return driver.findElement(By.cssSelector("div.header")); }
    private WebElement startOverTextBody() { return driver.findElement(By.cssSelector("div.header + div.content")); }
    private WebElement startOverYesButton() { return driver.findElement(By.cssSelector("div.header + div.content + div button:nth-of-type(1)")); }
    private WebElement startOverNoButton() { return driver.findElement(By.cssSelector("div.header + div.content + div button:nth-of-type(2)")); }
    private WebElement yourResultsHeader() { return driver.findElement(By.cssSelector("span.csr-your-results-header")); }
    private WebElement noResultsYetHeader() { return driver.findElement(By.cssSelector("h3.heading")); }
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
    private WebElement getChooseOneWebElement(){ return driver.findElement(By.xpath("//b[contains(text(),'Saved Searches')]/../div"));}
    private List<WebElement> allSaveSearchOptions(){ return driver.findElements(By.xpath("//div[@class='menu transition visible']/div[@role='option']"));}
    private String gpaTooltipIconInResultsLocator = "td.you-column button.supermatch-tooltip-trigger";
    private static WebElement superMatchCollegeSearchHeader() { return driver.findElement(By.xpath("//h1[text()='SuperMatch College Search']")); }
    private WebElement yesDeleteButton() { return driver.findElement(By.cssSelector("div.actions button.ui.teal.basic.button:nth-of-type(1)")); }
    private String disabledSavedSearchesButtonLocator = "div[aria-disabled='true']";
    private String letterGradeListLocator = "div[id *= 'supermatch-tooltip-'] tbody td:nth-of-type(1)";
    private String percentGradeListLocator = "div[id *= 'supermatch-tooltip-'] tbody td:nth-of-type(2)";
    private String fourScaleListLocator = "div[id *= 'supermatch-tooltip-'] tbody td:nth-of-type(3)";
    private WebElement infoBarMainWording() { return driver.findElement(By.xpath("//div[contains(@class, 'message')]/div/span[3]")); }
    private WebElement infoBarExtraWording() { return driver.findElement(By.xpath("//div[contains(@class, 'message')]/div/span[3]/span[3]")); }
    private WebElement getMainMenuTab(String tabName) { return driver.findElement(By.xpath("//ul//div[text() = '" + tabName + "']")); }
    private String lookByDropdownLocator = "//label[text() = 'Lookup by:']//select";
    private WebElement lookupByNameField() { return driver.findElement(By.cssSelector("input[name='name']")); }
    private WebElement heartIconInList(String collegeName) { return driver.findElement(By.xpath("//a[text() = '" + collegeName + "']/preceding-sibling::button")); }
    private WebElement getCollegeMatchTab(String tabName) { return driver.findElement(By.xpath("//div//a[text() = '" + tabName + "']")); }
    private String connectorCloseIconLocator = "i.close.icon";
}
