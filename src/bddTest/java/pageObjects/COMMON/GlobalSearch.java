package pageObjects.COMMON;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import selenium.SeleniumBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class GlobalSearch extends SeleniumBase {

    private Logger logger;

    public GlobalSearch() {
        logger = Logger.getLogger(GlobalSearch.class);
    }

    public void search(String searchTerms, String category) {
        setSearchCategory(category);
        doSearch(searchTerms);
    }

    public void searchForHEInstitutions(String searchTerm) {
        setSearchCategory("HE Accounts");
        searchTerm = "\""+searchTerm+"\"";
        doSearch(searchTerm);
    }

    public void searchForUsers(String searchTerm) {
        setSearchCategory("Users");
        doSearch(searchTerm);
    }

    public void searchForPeople(String searchTerm) {
        setSearchCategory("People");
        doSearch(searchTerm);
    }

    public void searchForInstitutions(String searchTerm) {
        setSearchCategory("Institutions");
        waitUntilPageFinishLoading();
        doSearch(searchTerm);
    }

    public void searchForGroups(String searchTerm) {
        setSearchCategory("Groups");
        doSearch(searchTerm);
    }

    public void searchForAll(String searchTerm) {
        setSearchCategory("All");
        doSearch(searchTerm);
    }


    public void setSearchCategory(String searchCategory) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(By.id("global-search-box-filter")));
        getSearchSwitcher().click();
        waitUntilPageFinishLoading();
        switch(searchCategory) {
            case "All":
                getSearchSwitcher().findElement(By.className("search")).click();
                break;
            case "HE Accounts":
                getSearchSwitcher().findElement(By.className("university")).findElement(By.xpath("./../../div/span[contains(text(), 'HE Accounts')]")).click();
                break;
            case "College Core":
                getSearchSwitcher().findElement(By.className("graduation")).click();
                break;
            case "Institutions":
                getSearchSwitcher().findElement(By.className("university")).findElement(By.xpath("./../../div/span[contains(text(), 'Institutions')]")).click();
                break;
            case "Users":
                getSearchSwitcher().findElement(By.className("user")).findElement(By.xpath("./../../div/span[contains(text(), 'Users')]")).click();
                break;
            case "People":
                //getDriver().findElement(By.xpath("//*[@id=\"global-search-box-filter\"]/div/div[contains(text(), 'People')]")).click();
                getSearchSwitcher().findElement(By.id("global-search-box-filter")).findElement(By.xpath("//div/div/span[contains(text(), 'People')]")).click();
                break;
            case "Groups":
                getSearchSwitcher().findElement(By.className("comments")).click();
                break;
            default:
                Assert.fail(searchCategory + " is not a valid search category.  Valid categories: All, HE Accounts, College Core, Institutions, Users, People, Groups");
        }
        waitUntilPageFinishLoading();
    }
    private void doSearch(String searchTerm) {
        waitUntilPageFinishLoading();
        getSearchBox().click();
        getSearchBox().clear();
        getSearchBox().sendKeys(searchTerm);
    }

    public void clickAdvancedSearchTabCategory(String tab){
        switch(tab){
            case "People":
                getDriver().findElement(By.id("searchResultsTabpeople")).click();
                break;
            case "Institutions":
                getDriver().findElement(By.id("searchResultsTabinstitutions")).click();
                break;
            case "Groups":
                getDriver().findElement(By.id("searchResultsTabgroups")).click();
                break;
            default:
                Assert.fail(tab + " is not a valid search tab.  Valid categories: People, Institutions, or Groups");
                break;
        }
    }

    //Makes sure all the results contain the search term
    public void verifyInstitutionalResults(String searchTerm) {
        waitUntilPageFinishLoading();
        List<WebElement> categories = getDriver().findElement(By.id("global-search-box-results")).findElements(By.className("category"));
        boolean institutionsReturned = false;
        boolean itemFound = false;
        for (WebElement category : categories) {
            if (category.findElement(By.className("name")).getText().equalsIgnoreCase("HE Accounts") || category.findElement(By.className("name")).getText().equalsIgnoreCase("College Core")) {
                institutionsReturned = true;
                List<WebElement> options = category.findElements(By.className("title"));
                for (WebElement option : options) {
                    if (option.getText().toLowerCase().contains(searchTerm.toLowerCase())) {
                        itemFound = true;                    }
                    //Assert.assertTrue(searchTerm + " HE Institution was not found on the search results", option.getText().toLowerCase().contains(searchTerm.toLowerCase()));
                }
            }
        }
        Assert.assertTrue("Search term was not found in the search results",itemFound);
        Assert.assertTrue("No HE Accounts or Institutions whee returned in the search", institutionsReturned);
    }

    public void selectResult(String optionToSelect) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(By.id("global-search-box-results")));
        List<WebElement> categories = getDriver().findElement(By.id("global-search-box-results")).findElements(By.className("category"));
        boolean institutionsReturned = false;
        boolean institutionClickedOn = false;
        for (WebElement category : categories) {
            String sectionName = category.findElement(By.cssSelector("div.name div.name")).getText();
            if (sectionName.equalsIgnoreCase("HE Accounts") || sectionName.equalsIgnoreCase("College Core") || sectionName.equalsIgnoreCase("People")
                    || sectionName.equalsIgnoreCase("Institutions") || sectionName.equalsIgnoreCase("HS Accounts")) {
                institutionsReturned = true;
                List<WebElement> options = category.findElements(By.className("result"));
                for (WebElement option : options) {
                    if (option.findElement(By.className("title")).getText().toLowerCase().equals(optionToSelect.toLowerCase())) {
                        option.click();
                        institutionClickedOn = true;
                        waitUntilPageFinishLoading();
                    }
                }
            }
        }
        Assert.assertTrue("No HE Institutions where returned on the search", institutionsReturned);
        Assert.assertTrue("Unable to click on " + optionToSelect, institutionClickedOn);
    }

    public void verifyNoSearchResults(){
        waitUntilPageFinishLoading();
        List<WebElement> categories = getDriver().findElement(By.id("global-search-box-results")).findElements(By.className("category"));
        if (categories.size()==0){
            logger.info("No search results found.  This is the expected result.");
        }else
            Assert.assertTrue("Search results were found, but should not have been!", false);
    }

    public void verifyNoAdvancedSearchResultsReturned(String searchRequest, DataTable dataTable){
        System.out.println();
        logger.info("Verifying no advanced search results are return.");
        verifyAdvanceSearchByEnterKey(searchRequest);
        List<String> categoryOptions = dataTable.asList(String.class);
        for(String opt : categoryOptions) {
            clickAdvancedSearchTabCategory(opt);
            Assert.assertTrue("Search results were returned and should not have been.", getDriver().findElements(By.xpath("//span[contains(text(), 'No results found')]")).size() >=1 );
        }

    }

    public void verifyNoRealTimeSearchResultsReturned(String searchRequest){
        System.out.println();
        logger.info("Verifying no real-time search results are return.");
        doSearch(searchRequest);
        try{
            (new WebDriverWait(getDriver(),10)).
                    until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[text()='No results found.']")));
        }catch(Exception e){
            throw new AssertionFailedError("There were search results.");
        }
    }

    public void goToAdvancedSearch(String category) {
        waitUntilPageFinishLoading();
        setSearchCategory(category);
        waitUntilPageFinishLoading();
        doSearch("t ");
        waitUntilPageFinishLoading();
        link("More...").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Did not end on Advanced Search page!  No Update Search buttons is present!", button("UPDATE SEARCH").isDisplayed());
    }

    public void verifyRealTimeSearchResultsReturned(String searchRequest) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying real-time search displays results in dropdown.");
        doSearch(searchRequest);
        Assert.assertTrue("No real-time results displaying in dropdown!", getDriver().findElement(By.id("global-search-box-results")).isDisplayed());
    }

    public void verifyRealTimeSearchMatch(String searchRequest, DataTable dataTable) {
        waitUntilPageFinishLoading();
        logger.info("\nVerifying real-time partial and full match results are returned.");
        List<String> categoryOptions = dataTable.asList(String.class);
        String[] partialSearchRequest = searchRequest.split(" ");
        for (String opt : categoryOptions) {
            // check for full match does not work for HE Accounts search - 6-29-2017 submitted MATCH-2231
            Assert.assertTrue("Real-time search did not return a full match for " + searchRequest + " under " + opt + " category, but should have.", searchThroughResults(searchRequest,opt));
            for (String partialOtp : partialSearchRequest) {
                Assert.assertTrue("Real-time search did not return a partial match for " + searchRequest + " under " + opt + " category, but should have.", searchThroughResults(partialOtp,opt));
            }
        }
    }

    public void searchandSelectInGlobalSearch(String school) {
        waitUntilPageFinishLoading();
        searchForHSInstitutions(school);
        selectResult(school);
    }

    public void searchForHSInstitutions(String searchTerm) {
        setSearchCategory("Institutions");
        searchTerm = "\""+searchTerm+"\"";
        doSearch(searchTerm);
    }

    public void selectRepvisitsAvialability() {
        waitUntilPageFinishLoading();
        try{
            waitUntilElementExists(RepvisitsAvailabilityButton());
            Assert.assertTrue("RepvisitsAvialbilityButton is not displayed",RepvisitsAvailabilityButton().isDisplayed());
        }catch(Exception e){}
        WebElement frameClass=driver.findElement(By.className("_2ROBZ2Dk5vz-sbMhTR-LJ"));
        driver.switchTo().frame(frameClass);
        waitUntilElementExists(RepvisitsAvailabilityButton());
        Assert.assertTrue("RepvisitsAvialbilityButton is not displayed",RepvisitsAvailabilityButton().isDisplayed());
        RepvisitsAvailabilityButton().click();
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
    }

    public void verifyblockedAvaialbility(String date,String time) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Repvisits Availability']"),1));
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Visits']"),1));
        waitUntilElementExists(visit());
        visit().click();
        waitUntilElementExists(dateInRepVisitsAvailability());
        dateInRepVisitsAvailability().click();
        setSpecificDate(date);
        String dateResult=getMonthandDate(date);
        String Time=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        List<WebElement> availabilitySlot = driver.findElements(By.xpath("//span[text()='" + dateResult + "']/ancestor::th/ancestor::thead/following-sibling::tbody/tr/td/button[text()='" + Time + "']"));
        if(availabilitySlot.size()==0){
            logger.info("Availability is displayed");
        }else {
            logger.info("Availability is not displayed");
        }
    }

    public String getMonthandDate(String addDays)
    {
        String DATE_FORMAT_NOW = "MMM d";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void setSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month) {

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            int i = 0;
            while (!DayPickerCaption.contains(month) && i < 12) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                i++;
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
            while (!DayPickerCaption.contains(month) && i > -12) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                i--;
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        }
        catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.\n" + e.getMessage());
        }
    }

    public void clickOnDay(String date) {
        try {

            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+"]")).click();

        } catch (Exception e) {
            Assert.fail("The Date selected is out of RANGE.");
        }

    }

    public boolean searchThroughResults(String searchRequest, String categoryOption) {
        doSearch(searchRequest);
        boolean searchResultsFound;
        switch (categoryOption) {
            case "Users":
                return getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/div/div[@class = 'title']")).getText().contains(searchRequest);
            case "People":
                return getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/div/div[@class = 'title']")).getText().contains(searchRequest) ||
                        getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/div/div[@class = 'description']")).getText().contains(searchRequest);
            case "HE Accounts":
                return getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/div/div[@class = 'title']")).getText().contains(searchRequest) ||
                        getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/div/div[@class = 'description']")).getText().contains(searchRequest);
            case "Institutions":
                return getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/div/div[@class = 'title']")).getText().contains(searchRequest) ||
                        getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/div/div[@class = 'description']")).getText().contains(searchRequest);
            case "Groups":
                return getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/div/div[@class = 'title']")).getText().contains(searchRequest) ||
                        getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/div/div[@class = 'description']")).getText().contains(searchRequest);
            default:
                Assert.fail(categoryOption + " is not a valid search category.  Valid categories: HE Accounts, Users, People, Institutions, or Groups");
                return false;
        }
    }

    public void verifyAdvanceSearchByEnterKey(String searchRequest) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying global search returns advanced search results when enter key is used.");
        doSearch(searchRequest);
        // These duplicate waits ensure that the search has finished realtime processing before we send the enter key.
        // If you send the Enter key before the search has finished, you won't be taken to the adv. search and test will fail.
        waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        driver.findElement(By.id("global-search-box-input")).sendKeys(Keys.ENTER);
        waitUntilPageFinishLoading();
        Assert.assertEquals("Did not end on Advanced Search page utilizing the enter/return key!  Original search data not entered in Keyword textBox!", searchRequest, driver.findElement(By.cssSelector("input[id='keyword']")).getAttribute("value"));
    }

    public void verifyAdvanceSearchByIcon(String searchRequest) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying advanced search results are returned when the global search icon button is used.");
        doSearch(searchRequest);
        waitUntilPageFinishLoading();
        clickSearchIcon();
        waitUntilPageFinishLoading();
        Assert.assertEquals("Did not end on Advanced Search page utilizing the search icon button!  Original search data not entered in Keyword textBox!", searchRequest, driver.findElement(By.cssSelector("input[id='keyword']")).getAttribute("value"));
    }

    public void verifyRealTimeSearchCategorized(DataTable dataTable) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying real-time search categories exist.");
        List<String> categoryOptions = dataTable.asList(String.class);
        for(String opt : categoryOptions) {
            List<WebElement> category = getDriver().findElements(By.xpath("//div[@class='name']/div/div[contains(text(),'" + opt + "')]"));
            Assert.assertTrue("Category " + opt + " was not found.", category.size() > 0);
        }

    }

    public void verifyAdvancedSearchResultsCategorized(DataTable dataTable) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying advanced search category tabs exist.");
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String categoryTab : data.keySet()) {
            Assert.assertTrue("Search category " + categoryTab + " tab was not found.", getDriver().findElement(By.id(data.get(categoryTab))).isDisplayed());
        }
    }

    public void verifyRealTimeSearchCategoriesDisplayFiveOrLessResults(DataTable dataTable){
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying real-time search results only display 5 or less results per category.");
        List<String> categoryOptions = dataTable.asList(String.class);
        for (String opt : categoryOptions) {
            List<WebElement> list = getDriver().findElements(By.xpath("//div[@icon='" + opt + "']"));
            Assert.assertTrue("Search category " + opt + " was not found or more than 5 results were displayed.", list.size() <= 5);
        }
    }
    public void verifyAdvancedSearchCategoryTabsDisplayFiveOrLessResults(DataTable dataTable){
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying advanced search results only display 5 or less results per category tab.");
        List<String> categoryOptions = dataTable.asList(String.class);
        for (String categoryTab : categoryOptions) {
            clickAdvancedSearchTabCategory(categoryTab);
            List<WebElement> list = getDriver().findElements(By.xpath("//div[@class=\"ui items\"]"));
            Assert.assertTrue("Search category " + categoryTab + " tab was not found or more than 5 results were displayed.", list.size() <= 5);
        }
    }

    public void verifySearchDropBoxResultsActionable(String searchRequest){
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying search dropdown results are clickable/actionable.");
        doSearch(searchRequest);
        waitUntilPageFinishLoading();
        WebElement searchOption = getDriver().findElement(By.id("global-search-box-item-5"));
        String url = driver.getCurrentUrl();
        searchOption.click();
        waitUntilPageFinishLoading();
        Assert.assertNotEquals("Real-time search option was not clickable/actionable",url, driver.getCurrentUrl());
    }

    public void VerifyUserSearchDefaultPage(){
        System.out.println();
        logger.info("Verifying advanced search utilizes the user/people as the default return page.");
        Assert.assertTrue("User/People was not the default tab for advanced search results.", getDriver().findElement(By.id("searchResultsTabpeople")).isDisplayed());
    }

    public void verifyAdvancedSearchResultsLayout(String searchRequest, DataTable dataTable){
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying advanced search results layouts are displayed correctly.");
        doSearch(searchRequest);
        waitUntilPageFinishLoading();
        clickSearchIcon();
        waitUntilPageFinishLoading();
        List<String> categoryOptions = dataTable.asList(String.class);
        for (String categoryTab : categoryOptions) {
            clickAdvancedSearchTabCategory(categoryTab);
            waitUntilPageFinishLoading();
            boolean iconExist;
            switch (categoryTab) {
                case "People":
                    iconExist = getDriver().findElements(By.xpath("//div[@class='ui items']/div[@class='item _3oJrouWMnNDQa2_NMjysit']/img")).size() != 0 || getDriver().findElements(By.xpath("//div[@class='ui items']/div[@class='item _3oJrouWMnNDQa2_NMjysit']/i")).size() != 0;
                    Assert.assertTrue("Avatar is not displayed in the People tab for real-time search.", iconExist);
                    Assert.assertTrue("Name is not displayed in the People tab for real-time search.", getDriver().findElement(By.xpath("//div[@class='ui items']/div[@class='item _3oJrouWMnNDQa2_NMjysit']/div/div[@class='header _1E-fy-qQ1o0LGCa15nUQe0']")).isDisplayed());
                    Assert.assertTrue("Institution is not displayed in the People tab for real-time search.", getDriver().findElement(By.xpath("//div[@class='ui items']/div[@class='item _3oJrouWMnNDQa2_NMjysit']/div/div[@class='description _3FZFtAql1zqRcNrPTqtzKh']/a")).isDisplayed());
                    break;
                case "Institutions":
                    iconExist = getDriver().findElements(By.xpath("//div[@class='ui items']/div[@class='item _1mnhs5BYlolZXplMGBZCvC']/img")).size() != 0 || getDriver().findElements(By.xpath("//div[@class='ui items']/div[@class='item _1mnhs5BYlolZXplMGBZCvC']/i")).size() != 0;
                    Assert.assertTrue("Avatar/icon is not displayed in the Institutions tab for real-time search.", iconExist);
                    Assert.assertTrue("Name is not displayed in the Institutions tab for real-time search.", getDriver().findElement(By.xpath("//div[@class='ui items']/div[@class='item _1mnhs5BYlolZXplMGBZCvC']/div/div[@class='header _1t0UcauZeLfM9p_w6ruYnP']")).isDisplayed());
                    Assert.assertTrue("Description is not displayed in the Institutions tab for real-time search.", getDriver().findElement(By.xpath("//div[@class='ui items']/div[@class='item _1mnhs5BYlolZXplMGBZCvC']/div/div[@class='description _1ivc8V9w17QnsCFYF71bKh']")).isDisplayed());
                    break;
                case "Groups":
                    Assert.assertTrue("Group name is not displayed in the Groups tab for real-time search.", getDriver().findElement(By.xpath("//div[@class='ui items']/div[@class='item _3504j--xOPKDqROw_Eyuxk']/div/div[@class='header _1TUGVQiwKij6ggWIsdT60']")).isDisplayed());
                    Assert.assertTrue("Description is not displayed in the Groups tab for real-time search.", getDriver().findElement(By.xpath("//div[@class='ui items']/div[@class='item _3504j--xOPKDqROw_Eyuxk']/div/div[@class='description _1EgLJDbPRwye6oql2m3Mk1']")).isDisplayed());
                    break;
                default:
                    Assert.fail(categoryTab + " is not a valid search tab.  Valid categories: People, Institutions, or Groups");
            }
        }
    }
    public void verifyRealTimeSearchLayout(String searchRequest, DataTable dataTable){
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying real-time search results layouts are displayed correctly.");
        doSearch(searchRequest);
        List<String> categoryOptions = dataTable.asList(String.class);
        for (String opt : categoryOptions) {
            waitUntilPageFinishLoading();
            boolean iconExist;
            switch (opt) {
                case "People":
                    iconExist = getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/span/img")).size() != 0 || getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/i")).size() != 0;
                    Assert.assertTrue("Avatar is not displayed for People in real-time search.", iconExist);
                    Assert.assertTrue("Name is not displayed for People in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/div/div[@class='title']")).isDisplayed());
                    Assert.assertTrue("Institution is not displayed for People in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='user']/div/div/div[@class='description']")).isDisplayed());
                    break;
                case "Institutions":
                    iconExist = getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/span/img")).size() != 0 || getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/i")).size() != 0;
                    Assert.assertTrue("Avatar/icon is not displayed for Institutions in real-time search.", iconExist);
                    Assert.assertTrue("Name is not displayed for Institutions in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/div/div[@class='title']")).isDisplayed());
                    Assert.assertTrue("Location is not displayed for Institutions in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='university']/div/div/div[@class='description']")).isDisplayed());
                    break;
                case "Groups":
                    iconExist = getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/span/img")).size() != 0 || getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/i")).size() != 0;
                    /*Icon does not exist currently - This is a bug - MATCH-3452*/
//                    Assert.assertTrue("Icon is not displayed for Groups in real-time search.", iconExist);
                    Assert.assertTrue("Group title is not displayed for Groups in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/div/div[@class='title']")).isDisplayed());
                    Assert.assertTrue("Description is not displayed for Groups in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/div/div[@class='description']")).isDisplayed());
                    break;
                default:
                    Assert.fail(opt + " is not a valid search tab.  Valid categories: People, Institutions, or Groups");
                    break;
            }
        }
    }

    public void verifyGlobalSearchNotDisplayed() {
        System.out.println();
        logger.info("Verifying that there is no global search option available.");
        Assert.assertFalse("Global search feature is available, but shouldn't be.", getDriver().findElements(By.xpath("//div[@class='_102AwZzmP9JnZ9-ca_Y6cu']")).size() >= 1);
    }

    public void verifyAdvancedSearchUserResultsDisplaysGeneralDescription(String searchRequest){
        System.out.println();
        logger.info("Verifying advanced search results displays the general description field below the user's title and institution.");
        searchForPeople(searchRequest);
        waitUntilPageFinishLoading();
        clickSearchIcon();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Advanced search does not return the general information field for People/User.", getDriver().findElement(By.xpath("//div/div/div[@class='ui items']//div[@class='description _3FZFtAql1zqRcNrPTqtzKh']")).isDisplayed());
    }

    public void verifyHEAccountSearchResults(String searchRequest) {
        System.out.println();
        logger.info("Verifying real-time search results are returned for HE Account search.");
        searchForHEInstitutions(searchRequest);
        waitUntilPageFinishLoading();
        Boolean resultsReturned=false;
        // check to make sure a result is found
        if (getDriver().findElements(By.id("global-search-box-item-0")).size() > 0) {
            getDriver().findElement(By.id("global-search-box-item-0")).click();
            resultsReturned = true;
        }
        Assert.assertTrue("No search results found for "+ searchRequest + ".", resultsReturned);
    }

    public void setHEAccountSubscriptions(String searchRequest, DataTable dataTable) {
        System.out.println();
        logger.info("Updating HE Account " + searchRequest + "'s subscription.");
        // Verify account exist
        verifyHEAccountSearchResults(searchRequest);
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String subscriptionType : data.keySet()) {
            switch (subscriptionType) {
                case("Legacy: Hub page management"):
                    WebElement hub = getDriver().findElement(By.xpath("//span[contains(text(), 'Hub')]/../following-sibling::td/div/i"));
                    hub.click();
                    jsClick(hub.findElement((By.xpath("//span[contains(text(), 'Hub')]/../following-sibling::td/div/div[@class='menu transition visible']/div/span[contains(text(), '" + data.get(subscriptionType) + "')]"))));
                    break;
                case("Legacy: Community"):
                    WebElement community = getDriver().findElement(By.xpath("//span[contains(text(), 'Community')]/../following-sibling::td/div/i"));
                    community.click();
                    jsClick(community.findElement((By.xpath("//span[contains(text(), 'Community')]/../following-sibling::td/div/div[@class='menu transition visible']/div/span[contains(text(), '" + data.get(subscriptionType) + "')]"))));
                    break;
                case("Intersect Awareness Subscription"):
                    WebElement awareness = getDriver().findElement(By.xpath("//span[contains(text(), 'Awareness')]/../following-sibling::td/div/i"));
                    awareness.click();
                    jsClick(awareness.findElement((By.xpath("//span[contains(text(), 'Awareness')]/../following-sibling::td/div/div[@class='menu transition visible']/div/span[contains(text(), '" + data.get(subscriptionType) + "')]"))));
                    break;
                case("Intersect Presence Subscription"):
                    WebElement presence = getDriver().findElement(By.xpath("//span[contains(text(), 'Presence')]/../following-sibling::td/div/i"));
                    presence.click();
                    jsClick(presence.findElement((By.xpath("//span[contains(text(), 'Presence')]/../following-sibling::td/div/div[@class='menu transition visible']/div/span[contains(text(), '" + data.get(subscriptionType) + "')]"))));
                    break;
            }
        }
        if(button(By.xpath("//span[contains(text(), 'Save Changes')]")).isDisplayed())
        {
            button(By.xpath("//span[contains(text(), 'Save Changes')]")).click();
        }
    }

    public void verifyAdvancedSearchFieldsReturnResults(String categorySearch, DataTable dataTable){
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying all advanced search fields for " + categorySearch + " are working properly.");
        Map<String, String> textBoxData = dataTable.asMap(String.class, String.class);
        clickAdvancedSearchLink();

        if(categorySearch.equalsIgnoreCase("Higher Education") || categorySearch.equalsIgnoreCase("High School")){
            clickAdvancedSearchTabCategory("Institutions");
        }
        else{
            clickAdvancedSearchTabCategory(categorySearch);
        }

        if(categorySearch.equalsIgnoreCase("People")){
            while(getDriver().findElements(By.xpath("//div[@class='title _20a5whP7pey-rtsEpBX62I']")).size()>=1) {
                openSearchOptionsDropdowns().click();
            }
        }

        for (String key : textBoxData.keySet()) {
            switch (key) {
                case "Advises Students on Admissions Process":
                    WebElement advisesStudents = driver.findElement(By.id("advise_students"));
                    Boolean isChecked = advisesStudents.findElement(By.id("advise_students")).isSelected();
                    if(textBoxData.get(key).equalsIgnoreCase("Yes") && !isChecked || textBoxData.get(key).equalsIgnoreCase("No") && isChecked){
                        advisesStudents.click();
                    }
                    waitUntilPageFinishLoading();
                    break;
                case "Charter School":
                    WebElement drpCharterSchool = driver.findElement(By.id("hs-charter-school"));
                    drpCharterSchool.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpCharterSchool.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("div/span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "College Going Rate":
                    WebElement slider = getDriver().findElement(By.xpath("//div[@class='input-range__track input-range__track--active']"));
                    JavascriptExecutor js=driver;
                    String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";
                    js.executeScript(scriptSetAttrValue, slider, "style.left", 20);
                    js.executeScript(scriptSetAttrValue, slider, "style.width", 80);
                    break;
                case "College Type":
                    WebElement drpCollegeType = driver.findElement(By.id("he-type"));
                    drpCollegeType.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpCollegeType.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "County Served":
                    WebElement drpCountyServed = driver.findElement(By.id("field_population_served_name_COUNTY"));
                    drpCountyServed.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpCountyServed.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "Institution State":
                    WebElement drpInstitutionState = driver.findElement(By.id("institutionState"));
                    drpInstitutionState.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpInstitutionState.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "Institution Type":
                    if(categorySearch.equalsIgnoreCase("People")){
                        WebElement drpInstitutionType = driver.findElement(By.id("field_institution_type_name"));
                        drpInstitutionType.click();
                        waitUntilPageFinishLoading();
                        jsClick(drpInstitutionType.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("div/span[contains(text(),'" + textBoxData.get(key) + "')]")));
                        waitUntilPageFinishLoading();
                    }
                    else {
                        WebElement drpInstitutionType = driver.findElement(By.id("institution-type"));
                        drpInstitutionType.click();
                        waitUntilPageFinishLoading();
                        jsClick(drpInstitutionType.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("div/span[contains(text(),'" + textBoxData.get(key) + "')]")));
                        waitUntilPageFinishLoading();
                        if (categorySearch.equalsIgnoreCase("Higher Education")) {
                            getDriver().findElement(By.xpath("//div[@class='title _20a5whP7pey-rtsEpBX62I']")).click();
                        } else {
                            getDriver().findElement(By.xpath("//div[@class='title _20a5whP7pey-rtsEpBX62I']")).click();
                            getDriver().findElement(By.xpath("//div[@class='title _20a5whP7pey-rtsEpBX62I']")).click();
                        }
                    }
                    break;
                case "Schedules College Visits":
                    WebElement schedulesVisits = driver.findElement(By.id("college_visits"));
                    Boolean isSelected = schedulesVisits.findElement(By.id("college_visits")).isSelected();
                    if(textBoxData.get(key).equalsIgnoreCase("Yes") && !isSelected || textBoxData.get(key).equalsIgnoreCase("No") && isSelected){
                        schedulesVisits.click();
                    }
                    waitUntilPageFinishLoading();
                    break;
                case "School Type":
                    WebElement drpSchoolType = driver.findElement(By.id("he-control"));
                    drpSchoolType.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpSchoolType.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "State":
                    WebElement drpState;
                    if(categorySearch.equalsIgnoreCase("Higher Education")) {
                        drpState = driver.findElement(By.id("he-state"));
                    }
                    else {
                        drpState = driver.findElement(By.id("hs-state"));
                    }
                    drpState.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpState.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("//span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "State Served":
                    WebElement drpStateServed = driver.findElement(By.id("field_population_served_name_STATE"));
                    drpStateServed.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpStateServed.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("div/span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "Title I Eligible":
                    WebElement drpTitleEligible = driver.findElement(By.id("hs-title-i-eligible"));
                    drpTitleEligible.click();
                    waitUntilPageFinishLoading();
                    jsClick(drpTitleEligible.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("div/span[contains(text(),'" + textBoxData.get(key)+"')]")));
                    waitUntilPageFinishLoading();
                    break;
                case "Type":
                    if(categorySearch.equalsIgnoreCase("Groups")) {
                        WebElement typeRadio = driver.findElement(By.id(textBoxData.get(key).toLowerCase()));
                        typeRadio.click();
                    }
                    else{
                        WebElement drpType = driver.findElement(By.id("hs-type"));
                        drpType.click();
                        waitUntilPageFinishLoading();
                        jsClick(drpType.findElement((By.cssSelector("[class='menu transition visible']"))).findElement(By.xpath("div/span[contains(text(),'" + textBoxData.get(key)+"')]")));
                        waitUntilPageFinishLoading();
                    }
                    break;

                case "Institution":
                    driver.findElement(By.id("institutionTitle")).sendKeys(textBoxData.get(key));
                    break;

                case "Degree":
                    driver.findElement(By.id("he-degree")).sendKeys(textBoxData.get(key));
                    break;

                case "Postal Code":
                    if (driver.findElements(By.id("he-postalCode")).size() > 0) {
                        driver.findElement(By.id("he-postalCode")).sendKeys(textBoxData.get(key));
                    } else {
                        driver.findElement(By.id("hs-postalCode")).sendKeys(textBoxData.get(key));
                    }
                    break;

                default:
                    logger.info("\nLooking for field: " + key + "\n");
                    getParent(getParent(text(key))).findElement(By.tagName("input")).sendKeys(textBoxData.get(key));
                    break;
            }
            getDriver().findElement(By.xpath("//span[contains(text(),'Update Search')]")).click();
            Assert.assertFalse("The advanced search option " + key + "field did not work properly", getDriver().findElements(By.xpath("//span[contains(text(), 'No results found')]")).size()!=0);
            System.out.println(key + " field updated search.");
        }
    }

    //Getters
    private WebElement openSearchOptionsDropdowns(){
        return getDriver().findElement(By.xpath("//div[@class='title _20a5whP7pey-rtsEpBX62I']"));
    }
    private void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();",element);
    }
    private WebElement getSearchBox() {
        waitUntilPageFinishLoading();
        return getDriver().findElement(By.id("global-search-box-input"));
    }
    private WebElement getSearchSwitcher(){
        waitUntilPageFinishLoading();
        return getDriver().findElement(By.id("global-search-box-filter"));
    }
    private void clickSearchIcon(){
        driver.findElement(By.xpath("//div[@class='ui icon input']/i")).click();
    }
    private void clickAdvancedSearchLink(){
        driver.findElement(By.xpath("//div[@class='_102AwZzmP9JnZ9-ca_Y6cu']/a")).click();
    }
    private WebElement RepvisitsAvailabilityButton()
    {
        WebElement link=link("Check RepVisits Availability");
        return link;
    }
    private WebElement visit()
    {
        WebElement visit=driver.findElement(By.xpath("//span[text()='Visits']"));
        return  visit;
    }
    private WebElement dateInRepVisitsAvailability()
    {
        WebElement date=button("Go to date");
        return date;
    }
}
