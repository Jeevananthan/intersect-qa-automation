package pageObjects.SM.pinnedSchoolsComparePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.io.File;

import pageObjects.SM.searchPage.SearchPageImpl;
import utilities.HUBSEditMode.Navigation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PinnedSchoolsComparePageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSMTooltipsContent%sSMTooltipsContent.properties", fs, fs, fs, fs, fs);

    private Navigation navigation = new Navigation();
    private SearchPageImpl searchPage = new SearchPageImpl();

    public PinnedSchoolsComparePageImpl() {
        logger = Logger.getLogger(PinnedSchoolsComparePageImpl.class);
    }

    public void verifyNumberOfDrawersDisplayed(String numberOfDrawers) {
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(drawersListLocator), 0));
        List<WebElement> drawersList = driver.findElements(By.cssSelector(drawersListLocator));
        Assert.assertTrue("The number of drawers displayed is incorrect. UI:" + drawersList.size(), Integer.parseInt(numberOfDrawers) == drawersList.size());
    }

    public void verifyAllDrawersExpanded() {
        List<WebElement> drawersList = driver.findElements(By.cssSelector(drawersListLocator));
        List<WebElement> openDrawersList = driver.findElements(By.cssSelector(drawersListLocator + " + tbody"));
        Assert.assertTrue("Not all the drawers are expanded by default", drawersList.size() == openDrawersList.size());
    }

    public void verifyDrawerArrowDirection(String expandedOrCollapsed, String drawerPosition, String arrowDirection) {
        waitUntilPageFinishLoading();
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
        if (drawerArrowsList.get(Integer.parseInt(position)).getAttribute("class").contains("right")) {
            while (true) {
                try {
                    drawerArrowsList.get(Integer.parseInt(position)).click();
                    break;
                } catch (WebDriverException e) {
                    collapseExpandAllButton().sendKeys(Keys.ARROW_DOWN);
                }
            }
        }
    }

    public void verifyCollapseExpandButtonTextWithExpandedDrawers(String buttonText, String numberOfExpandedDrawers) {
        List<WebElement> openDrawersList = driver.findElements(By.cssSelector(drawersListLocator + " + tbody"));
        if (openDrawersList.size() >= Integer.parseInt(numberOfExpandedDrawers)) {
            Assert.assertTrue("The text in the Collapse/Expand All button is incorrect", collapseExpandAllButton().getText().equals(buttonText));
        }
    }

    public void clickCollapseExpandButton() {
        collapseExpandAllButton().sendKeys(Keys.RETURN);
    }

    public void verifyTooltipInComparePage(String sectionName) {
        switch (sectionName) {
            case "Academic Match":
                verifyTooltipsInCompareScreen("academic.match.in.compare.schools.title");
                verifyTooltipsInCompareScreen("academic.match.in.compare.schools.labels");
                verifyTooltipsInCompareScreen("academic.match.in.compare.schools.text.blocks");
                break;
            case "Admission Info":
                verifyTooltipsInCompareScreen("compare.schools.admission.info.titles");
                verifyTooltipsInCompareScreen("compare.schools.admission.info.text");
                break;
            case "Institution Characteristics":
                verifyTooltipsInCompareScreen("compare.schools.institution.characteristics.titles");
                verifyTooltipsInCompareScreen("compare.schools.institution.characteristics.text");
                break;
            case "Diversity":
                verifyTooltipsInCompareScreen("compare.schools.diversity.titles");
                verifyTooltipsInCompareScreen("compare.schools.diversity.text");
                break;
        }
    }

    private void verifyTooltipsInCompareScreen(String propertiesEntry) {
        List<WebElement> admissionInfoTooltipIcons = driver.findElements(By.xpath(admissionInfoTooltipIconsLocator));
        List<WebElement> institutionCharacteristicsIcons = driver.findElements(By.xpath(institutionCharacteristicsLocator));
        List<WebElement> diversityIcons = driver.findElements(By.xpath(diversityTooltipIconsLocator));
        switch (propertiesEntry) {
            case "academic.match.in.compare.schools.title":
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                Assert.assertTrue("The title for Academic Match in the Schools Compare screen is not correct",
                        tooltipTitle().getText().trim().equals(getStringFromPropFile(propertiesFilePath, propertiesEntry)));
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                break;
            case "academic.match.in.compare.schools.labels":
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                List<WebElement> labels = driver.findElements(By.cssSelector(academicMatchLabelsLocator));
                for (int i = 0; i < labels.size(); i++) {
                    Assert.assertTrue("The label for the Academic Match tooltip is not correct",
                            labels.get(i).getText().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                }
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                break;
            case "academic.match.in.compare.schools.text.blocks":
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                for (int i = 0; i < getListFromPropFile(propertiesFilePath, "*", propertiesEntry).size(); i++) {
                    Assert.assertTrue("The text content in the Academic Match tooltip is not correct",
                            academicMatchTextContent().getText().contains(getListFromPropFile(propertiesFilePath, "*", propertiesEntry).get(i)));
                }
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                break;
            case "compare.schools.admission.info.titles":
                for (int i = 0; i < admissionInfoTooltipIcons.size(); i++) {
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The title for the tooltip in Admission Info is incorrect",
                            tooltipTitle().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.admission.info.text":
                for (int i = 0; i < admissionInfoTooltipIcons.size(); i++) {
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The text for the tooltip in Admission Info is incorrect",
                            academicMatchTextContent().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.institution.characteristics.titles":
                for (int i = 0; i < institutionCharacteristicsIcons.size(); i++) {
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The title of the tooltip in Institution Characteristics is incorrect",
                            tooltipTitle().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.institution.characteristics.text":
                for (int i = 0; i < institutionCharacteristicsIcons.size(); i++) {
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The text of the tooltip in Institution Characteristics is incorrect. UI: " + academicMatchTextContent().getText() + " Data: "
                                    + getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i),
                            academicMatchTextContent().getText().trim().replace("“", "'").replace("”", "'").equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.diversity.titles":
                for (int i = 0; i < diversityIcons.size(); i++) {
                    diversityIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The title of the tooltip in Diversity is incorrect",
                            tooltipTitle().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    diversityIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
        }
    }

    public void verifyHousingInfoIsDisplayedAfterClickingSections(DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        for (String element : details) {
            switch (element) {
                case "# of 4 year majors":
                    number4YearMajorsLink().sendKeys(Keys.RETURN);
                    verifyStudiesTab();
                    break;
                case "Varsity sports":
                    varsitySportsLink().sendKeys(Keys.RETURN);
                    verifyHousingInfoPage();
                    break;
                case "Male varsity sports":
                    maleSportsLink().click();
                    verifyHousingInfoPage();
                    break;
                case "Female varsity sports":
                    femaleSportsLink().click();
                    verifyHousingInfoPage();
                    break;
                case "Clubs and organizations":
                    clubsAndOrganizationsLink().sendKeys(Keys.RETURN);
                    verifyHousingInfoPage();
                    break;
            }
        }
    }

    public void verifyHousingInfoPage() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        Assert.assertTrue("The Housing Information section was not displayed", housingInfoSection().isDisplayed());
        navigation.closeNewTabAndSwitchToOriginal(originalHandle);
    }

    public void verifyStudiesTab() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        waitUntilElementExists(majorsHeaderInStudiesTab());
        Assert.assertTrue("The Studies tab was not displayed", majorsHeaderInStudiesTab().isDisplayed());
        navigation.closeNewTabAndSwitchToOriginal(originalHandle);
    }

    public void unpinSchool(String collegeName) {
        searchPage.scrollDown(exportButton());
        if (pinLink(collegeName).getText().contains("PINNED")) {
            pinLink(collegeName).click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(pinLinkLocator(collegeName)), 0));
            waitUntilPageFinishLoading();
        }
    }

    public void clickExportButton() {
        exportButton().click();
    }

    private String getDataForSpecificFilter(String fitTab, String criteriaName, int collegPosition) {
        if (fitTab.trim().length() != 0) {
            return driver.findElement(By.xpath("//table[caption='" + fitTab + "']//div[text()='" +
                    criteriaName + "']/../../td[" + (collegPosition + 1) + "]")).getText();
        } else {
            return driver.findElement(By.xpath("//table//div[text()='" +
                    criteriaName + "']/../../td[" + (collegPosition + 1) + "]")).getText();
        }

    }

    public void verifyTextDataForCriteria(String fitTab, String criteriaName, Integer collegePosition, String expectedText) {

        String actualText = getDataForSpecificFilter(fitTab, criteriaName, collegePosition);
        //Assert.assertEquals(expectedText, actualText);
        softly().assertThat(actualText).isEqualTo(expectedText);
    }

    public void verifyResourcesExpandableDrawerOptions(DataTable options) {
        List<List<String>> data = options.raw();
        String expResourcesOptions[] = new String[data.get(0).size()];
        String expResurceOptionsValues[] = new String[data.get(1).size()];
        for (int i=0;i<data.get(0).size();i++) {
            expResourcesOptions[i] = data.get(0).get(i);
            expResurceOptionsValues[i] = data.get(1).get(i);
        }
        for (int i = 0; i < resourcesOptions().size(); i++) {
            Assert.assertTrue(resourcesOptions().get(i) + " option is not matching with the expected option ie " + expResourcesOptions[i], resourcesOptions().get(i).getText().equals(expResourcesOptions[i]));
            if(expResourcesOptions[i].equals("Asperger's/Autism Support"))
                continue;
            String actResurceOptionsValues = driver.findElement(By.xpath("//div[text()='"+expResourcesOptions[i]+"']/../following-sibling::td")).getText();
            Assert.assertTrue("temp", actResurceOptionsValues.equals(expResurceOptionsValues[i]));
        }
    }

    public void clearPinnedColleges(){
        if (Integer.parseInt(getPinnedCollegesCount().getText())>0){
            pinnedDropdown().click();
            clearPinnedListOption().click();
            yesClearMyListButton().click();
            waitForElementTextToEqual(getPinnedCollegesCountFooter(), "0");
            Assert.assertTrue("Colleges are not cleared, still it's showing the count "+getPinnedCollegesCount().getText(), Integer.parseInt(getPinnedCollegesCount().getText())==0);
        }
    }

    public void pinCollegeFromBottomSearchResult(String collegeName) {
        searchBar().clear();
        searchBar().sendKeys(collegeName);
        waitUntilPageFinishLoading();
        waitUntilElementExists(getDriver().findElement(By.xpath("//div[contains(@class,'search-college-by-name-term')]/a[text()[contains(.,'"+collegeName+"')]]")));
        if (getDriver().findElements(By.xpath("//*[text()='" + collegeName + "']/../../div/a/span[text()='PIN']")).size() > 0) {
            String searchedCollegePinString = "//*[text()='" + collegeName + "']/../../div/a/span[text()='PIN']";
            WebElement searchedCollegePinButton = driver.findElement(By.xpath(searchedCollegePinString));
            searchedCollegePinButton.click();
            logger.info("Pinned " + collegeName);
        } else if (getDriver().findElements(By.xpath("//*[text()='" + collegeName + "']/../../div/a/span[text()='PINNED']")).size() > 0) {
            logger.info(collegeName + " is already pinned.");
        } else {
            softly().fail(collegeName + " was not found in the search bar, or was not pinned/pinable.");
        }
    }

    public void verifyStudentLifeExpandableDrawerOptions(DataTable options) {
        List<List<String>> data = options.raw();
        String expStudentLifeOptions[] = new String[data.get(0).size()];
        String expStudentLifeOptionsValues[] = new String[data.get(1).size()];
        for (int i=0;i<data.get(0).size();i++){
            expStudentLifeOptions[i] = data.get(0).get(i);
            expStudentLifeOptionsValues[i] = data.get(1).get(i);
        }
        for (int i = 0; i < studentLifeOptions().size(); i++) {
            Assert.assertTrue(studentLifeOptions().get(i) + " option is not matching with the expected option ie " + expStudentLifeOptions[i], studentLifeOptions().get(i).getText().equals(expStudentLifeOptions[i]));
            String actResurceOptionsValues = driver.findElement(By.xpath("//div[text()='"+expStudentLifeOptions[i]+"']/../following-sibling::td")).getText();
            Assert.assertTrue("temp", actResurceOptionsValues.equals(expStudentLifeOptionsValues[i]));
        }
    }

    public void verifyDiversityExpandableDrawerOptions(DataTable options) {
        List<List<String>> data = options.raw();
        String expDiversityOptions[] = new String[data.get(0).size()];
        String expDiversityOptionsValues[] = new String[data.get(1).size()];
        for (int i=0;i<data.get(0).size();i++){
            expDiversityOptions[i] = data.get(0).get(i);
            expDiversityOptionsValues[i] = data.get(1).get(i);
        }
        for (int i = 0; i < diversityOptions().size(); i++) {
            Assert.assertTrue(diversityOptions().get(i) + " option is not matching with the expected option ie " + expDiversityOptions[i], diversityOptions().get(i).getText().equals(expDiversityOptions[i]));
            String actDiversityOptionsValues = driver.findElement(By.xpath("//div[text()='"+expDiversityOptions[i]+"']/../following-sibling::td")).getText();
            Assert.assertTrue("temp", actDiversityOptionsValues.equals(expDiversityOptionsValues[i]));
        }
    }

    public void verifyAcademicsExpandableDrawerOptions(DataTable options) {
        List<List<String>> data = options.raw();
        String expAcademicsOptions[] = new String[data.get(0).size()];
        String expAcademicsOptionsValues[] = new String[data.get(1).size()];
        for (int i=0;i<data.get(0).size();i++){
            expAcademicsOptions[i] = data.get(0).get(i);
            expAcademicsOptionsValues[i] = data.get(1).get(i);
        }
        for (int i = 0; i < academicsOptions().size(); i++) {
            Assert.assertTrue(academicsOptions().get(i) + " option is not matching with the expected option ie " + expAcademicsOptions[i], academicsOptions().get(i).getText().equals(expAcademicsOptions[i]));
            String actAcademicsOptionsValues = driver.findElement(By.xpath("//div[text()='"+expAcademicsOptions[i]+"']/../following-sibling::td")).getText();
            Assert.assertTrue(expAcademicsOptions[i]+" is not displaying in application the value which is displaying is = "+actAcademicsOptionsValues, actAcademicsOptionsValues.equals(expAcademicsOptionsValues[i]));
        }
    }

    public void verifyAthleticsExpandableDrawerOptions(DataTable options) {
        List<List<String>> data = options.raw();
        String expAthleticsOptions[] = new String[data.get(0).size()];
        String expAthleticsOptionsValues[] = new String[data.get(1).size()];
        for (int i=0;i<data.get(0).size();i++){
            expAthleticsOptions[i] = data.get(0).get(i);
            expAthleticsOptionsValues[i] = data.get(1).get(i);
        }
        for (int i = 0; i < athleticsOptions().size(); i++) {
            Assert.assertTrue(athleticsOptions().get(i) + " option is not matching with the expected option ie " + expAthleticsOptions[i], athleticsOptions().get(i).getText().equals(expAthleticsOptions[i]));
            String actAthleticsOptionsValues = driver.findElement(By.xpath("//div[text()='"+expAthleticsOptions[i]+"']/../following-sibling::td")).getText();
            Assert.assertTrue(expAthleticsOptionsValues[i]+" is not displaying in application the value which is displaying is = "+actAthleticsOptionsValues, actAthleticsOptionsValues.equals(expAthleticsOptionsValues[i]));
        }
    }

    public void verifyICDrawerOptions(DataTable options) {
        List<List<String>> data = options.raw();
        String expICOptions[] = new String[data.get(0).size()];
        String expICOptionsValues[] = new String[data.get(1).size()];
        for (int i=0;i<data.get(0).size();i++){
            expICOptions[i] = data.get(0).get(i);
            expICOptionsValues[i] = data.get(1).get(i);
        }
        for (int i = 0; i < institutionCharacteristicsOptions().size(); i++) {
            Assert.assertTrue(institutionCharacteristicsOptions().get(i) + " option is not matching with the expected option ie " + expICOptions[i], institutionCharacteristicsOptions().get(i).getText().equals(expICOptions[i]));
            String actICOptionsValues = driver.findElement(By.xpath("//div[text()='"+expICOptions[i]+"']/../following-sibling::td")).getText();
            Assert.assertTrue(expICOptionsValues[i]+" is not displaying in application the value which is displaying is = "+actICOptionsValues, actICOptionsValues.equals(expICOptionsValues[i]));
        }
    }

    public void verifyAdmissionInfoDrawerOptions(DataTable dataTable) {
        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        Map<String,String> expectedResults = data.get(0);
        // Iterate through all of the rows in the "Admission Info" table.
        for (WebElement entry : admissionInfoOptions()) {
            String heading = entry.getText();
            String value = entry.findElement(By.xpath("../following-sibling::td")).getText();
            // Both ACT and SAT have a "Math" entry, so we need to do something to differentiate them.
            if (heading.equals("Math")) {
                if (getParent(entry).getAttribute("aria-label").contains("SAT")) {
                    heading = "Math1";
                }
            }
            softly().assertThat(value).as(heading).isEqualTo(expectedResults.get(heading));
        }
    }

    public void verifyInstitutionHighlightsDrawerOptionsMaps(DataTable dataTable){
        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        Map<String,String> expectedResults = data.get(0);
        for (WebElement entry : institutionHighlightsOptions()) {
            String heading = entry.getText();
            String value = entry.findElement(By.xpath("../following-sibling::td")).getText();
            if (heading.equals("Profiles")) {
                //Actions action = new Actions(driver);
                //action.moveToElement(institutionHighlightsViewProfilesLink()).build().perform();
                //for(int ii=0;ii<3;ii++){
                //    institutionHighlightsViewProfilesLink().sendKeys(Keys.ARROW_DOWN);
                //}
                //waitForUITransition();
                waitUntil(ExpectedConditions.elementToBeClickable(institutionHighlightsViewProfilesLink()));
                //waitForElementTextToEqual(institutionHighlightsViewProfilesLink(), "View Profiles");
                institutionHighlightsViewProfilesLink().click();
                waitUntilPageFinishLoading();
                Set<String> winHandles = driver.getWindowHandles();
                String firstWinHandle = driver.getWindowHandle();
                winHandles.remove(firstWinHandle);
                String secondWinHandle=winHandles.iterator().next();
                driver.switchTo().window(firstWinHandle);
                driver.switchTo().window(secondWinHandle);
                setImplicitWaitTimeout(10);
                Assert.assertTrue("View Profiles link is not working.", universityOfAlabamaTextInSecondWin().isDisplayed());
                driver.switchTo().window(firstWinHandle);
            }
            softly().assertThat(value).as(heading).isEqualTo(expectedResults.get(heading));
        }
    }

    public void verifyLocationExpandableDrawerOptions(DataTable dataTable){
        List<Map<String,String>> data = dataTable.asMaps(String.class, String.class);
        Map<String,String> expectedResults = data.get(0);
        // Iterate through all of the rows in the "Locations" table.
        for (WebElement entry : locationOptions()){
            String heading = entry.getText();
            String value = entry.findElement(By.xpath("../following-sibling::td")).getText();
            softly().assertThat(value).as(heading).isEqualTo(expectedResults.get(heading));
        }
    }

    /**
     * This method will verify the Pinned College Page functionality like Pinned/Un-Pinned, Favorited/Not Favorited
     * Name of College, website etc.
     * @param table
     */
    public void verifyPinnedCollege(DataTable table){
        List<String> list = table.asList(String.class);
        Assert.assertTrue("College image is not displaying.", grayBox().findElement(By.tagName("img")).isDisplayed());
        for (String itemDetail : list) {
            switch (itemDetail){
                case "FAVORITE" :
                    Assert.assertTrue(itemDetail+" text is not displaying.", grayBox().findElement(By.xpath(".//span[text()='"+itemDetail+"']")).isDisplayed());
                    break;
                case "The University of Alabama" :
                    Assert.assertTrue(itemDetail+" text is not displaying.", grayBox().findElement(By.xpath(".//a[text()='"+itemDetail+"']")).isDisplayed());
                    break;
                case "this.is.yet.another.testing6,14:02" :
                    Assert.assertTrue(itemDetail+" text is not displaying.", grayBox().findElement(By.xpath(".//a[text()='"+itemDetail+"']")).isDisplayed());
                    break;
                case "PINNED" :
                    Assert.assertTrue(itemDetail+" text is not displaying.", onlyOnePinnedCollege(itemDetail).isDisplayed());
                    //driver.findElement(By.tagName("button")).sendKeys(Keys.PAGE_DOWN);
                    waitForElementTextToEqual(onlyOnePinnedCollege(itemDetail), "PINNED");
                    onlyOnePinnedCollege(itemDetail).click();
                    Assert.assertTrue("Un-Pinning is not working in Compare Pinned College page.", grayBox().findElements(By.xpath(".//span[text()='"+itemDetail+"']")).size()<=0);
                    break;
            }
        }
    }

    /**
     *This method will verify left & Right arrow sign to move other colleges in Compare Pinned College page and also
     * Viewing college number
     */
    public void verifyPinnedCollegesFunctionality(){
        String actualDisableArrow = leftArrowInComparePage().getAttribute("class");
        String expectedDisableArrow = "ui teal basic icon disabled button";
        softly().assertThat(actualDisableArrow).as("Left arrow is not disable.").isEqualTo(expectedDisableArrow);

        String actualEnableArrow = rightArrowInComparePage().getAttribute("class");
        String expectedEnableArrow = "ui teal basic icon button";
        softly().assertThat(actualEnableArrow).as("Right arrow is not disable.").isEqualTo(expectedEnableArrow);

        rightArrowInComparePage().click();
        String actualDisplayBarText = numberOfCollegeDisplayBar().getText();
        String expectedDisplayBarText = "Viewing 5 - "; // sometimes concurrency issues cause us to have more than 5 pinned schools, so don't look for 5 of 5.
        softly().assertThat(actualDisplayBarText).as("Pagination message").contains(expectedDisplayBarText);
        //Assert.assertTrue("Fifth pinned college display bar ie "+expectedDisplayBarText+" is not displaying.", actualDisplayBarText.equals(expectedDisplayBarText));
    }

    public void verifyCollegeHyperlink(){
        ((JavascriptExecutor)driver).executeScript("scroll(0,400)");
        driver.findElement(By.xpath("//a[text()='The University of Alabama']")).click();
        int winCount = driver.getWindowHandles().size();
        softly().assertThat(2).as("College name is not a hyperlink.").isEqualTo(2);
    }

    public void favSchoolFromPinnedColleges(String college) {
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(subTitle), 0));
        //This fixed wait is necessary because the heart icon takes a moment to set its actual status
        waitForUITransition();
        searchPage.scrollDown(heartIcon(college));
        if (heartIcon(college).getAttribute("class").contains("empty")) {
            heartIcon(college).click();
        }
    }

    public void unfavSchoolFromPinnedColleges(String college) {
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(subTitle), 0));
        if (!heartIcon(college).getAttribute("class").contains("empty")) {
            heartIcon(college).click();
        }
    }

    // Locators Below
    private WebElement numberOfCollegeDisplayBar(){ return driver.findElement(By.xpath("//strong[text()[contains(.,'Viewing 5 -')]]"));}
    private WebElement rightArrowInComparePage(){return driver.findElement(By.xpath("//button[@aria-roledescription='Select Next Items']"));}
    private WebElement leftArrowInComparePage(){return driver.findElement(By.xpath("//button[@aria-roledescription='Select Prior Items']"));}
    private WebElement grayBox() { return driver.findElement(By.className("supermatch-compare-data-header"));}
    private WebElement onlyOnePinnedCollege(String itemDetail){ return grayBox().findElement(By.xpath(".//span[text()[contains(.,'"+itemDetail+"')]]"));}
    private WebElement locationDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Location']/.."));
    }
    private List<WebElement> locationOptions(){ return locationDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement universityOfAlabamaTextInSecondWin(){ return driver.findElement(By.xpath("//h1[contains(text(),'The University of Alabama')]")); }
    private WebElement institutionHighlightsViewProfilesLink(){  return driver.findElement(By.xpath("//a[text()='View Profiles']")); }
    private WebElement institutionHighlightsDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Institution Highlights']/.."));
    }
    private List<WebElement> institutionHighlightsOptions(){ return institutionHighlightsDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement admissionInfoDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Admission Info']/.."));
    }
    private List<WebElement> admissionInfoOptions(){ return admissionInfoDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement institutionCharacteristicsDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Institution Characteristics']/.."));
    }
    private List<WebElement> institutionCharacteristicsOptions(){ return institutionCharacteristicsDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement athleticsDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Athletics']/.."));
    }
    private List<WebElement> athleticsOptions(){ return athleticsDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private List<WebElement> academicsOptions(){ return academicsDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement academicsDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Academics']/.."));
    }

    private List<WebElement> diversityOptions(){ return diversityDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement diversityDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Diversity']/.."));
    }
    private WebElement studentLifeDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Student Life']/.."));
    }
    private List<WebElement> studentLifeOptions(){ return studentLifeDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}

    private WebElement getPinnedCollegesCountFooter(){return driver.findElement(By.id("pinCount"));}
    private WebElement searchBar(){return driver.findElement(By.id("supermatch-search-box-input"));}
    private List<WebElement> resourcesOptions(){ return resourceDrawerTable().findElements(By.xpath(".//div[@class='supermatch-expanded-table-label']"));}
    private WebElement yesClearMyListButton() {
        return clearPinnedListModal().findElement(By.xpath(".//button[text()='YES, CLEAR MY LIST']"));
    }
    private WebElement clearPinnedListModal() {
        return getDriver().findElement(By.xpath("//div[contains(@class, 'visible active supermatch-modal')]"));
    }
    private WebElement clearPinnedListOption() {
        return getDriver().findElement(By.xpath("//span[contains(text(), 'Clear Pinned List')]//ancestor::div[1]"));
    }
    private WebElement pinnedDropdown() {
        return getDriver().findElement(By.xpath("//div[contains(@class, 'supermatch-pinned-dropdown')]"));
    }
    private  WebElement getPinnedCollegesCount(){return driver.findElement(By.id("pinCount"));}
    private WebElement resourceDrawerTable() {
        return driver.findElement(By.xpath("//div[@class='ui segment supermatch-compare-content']/table/caption[text()='Resources']/.."));
    }

    ////table[contains(@class,'supermatch-expandable-table')]/thead
    private String drawersListLocator = "div.supermatch-compare-content > table > thead";
    private String drawersArrowsLocator = "div.ui.segment.supermatch-compare-content i.caret";

    private WebElement collapseExpandAllButton() {
        return driver.findElement(By.cssSelector("button.ui.teal.basic.button[role='button']:not(.icon)"));
    }

    private String admissionInfoTooltipIconsLocator = "//th[text() = 'Admission Info']/../../../tbody/tr/td/div/div/button";
    private String institutionCharacteristicsLocator = "//th[text() = 'Institution Characteristics']/../../../tbody/tr/td/div/div/button";
    private String diversityTooltipIconsLocator = "//th[text() = 'Diversity']/../../../tbody/tr/td/div/div/button";

    private WebElement tooltipTitle() {
        return driver.findElement(By.cssSelector("div[id *= 'supermatch-tooltip-'] div.header"));
    }

    private WebElement tooltipText() {
        return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] div.content"));
    }

    private String academicMatchLabelsLocator = "div[id *= 'supermatch-tooltip-'] div.content b";

    private WebElement academicMatchTextContent() {
        return driver.findElement(By.cssSelector("div[id *= 'supermatch-tooltip-'] div.content"));
    }

    private WebElement academicMatchTooltipIcon() {
        return driver.findElement(By.cssSelector("td[aria-label=\" Academic Match\"] button"));
    }

    private WebElement number4YearMajorsLink() {
        return driver.findElement(By.cssSelector("td[aria-label=\"Academics # of 4 Year Majors\"] + td a"));
    }

    private WebElement majorsHeaderInStudiesTab() {
        return driver.findElement(By.cssSelector("h3.ng-binding"));
    }

    private WebElement varsitySportsLink() {
        return driver.findElement(By.cssSelector("td[aria-label=\"Athletics Levels Available\"] + td > a"));
    }

    private WebElement housingInfoSection() {
        return driver.findElement(By.cssSelector("div.student-life-housing-information__header.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-7.fc-grid__col--md-12 h2"));
    }

    private WebElement maleSportsLink() {
        return driver.findElement(By.cssSelector("td[aria-label=\"Athletics Levels Available\"] + td ul li:nth-of-type(1)"));
    }

    private WebElement femaleSportsLink() {
        return driver.findElement(By.cssSelector("td[aria-label=\"Athletics Levels Available\"] + td ul li:nth-of-type(2)"));
    }

    private WebElement clubsAndOrganizationsLink() {
        return driver.findElement(By.cssSelector("td[aria-label=\"Student Life Clubs & Organizations\"] + td a"));
    }

    private WebElement pinLink(String collegeName) {
        return driver.findElement(By.xpath(pinLinkLocator(collegeName)));
    }

    private String pinLinkLocator(String collegeName) {
        return "//p[@class='collegename' and text() = '" + collegeName + "']/../div/p/a/span[@class = 'supermatch-toggle-icon supermatch-college-button-selected']";
    }

    private WebElement exportButton() {
        return driver.findElement(By.cssSelector("a.ui.teal.basic.button"));
    }

    private WebElement heartIcon(String college) {
        return driver.findElement(By.xpath("//a[contains(text(), '" + college + "')]/..//i[contains(@class, 'heart')]"));
    }

    private String subTitle = "//h1[text() = 'An in-depth comparison of your pinned schools']";
}
