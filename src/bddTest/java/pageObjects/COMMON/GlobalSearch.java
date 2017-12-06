package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import selenium.SeleniumBase;

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

    private void setSearchCategory(String searchCategory) {
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
                getSearchSwitcher().findElement(By.id("global-search-box-filter")).findElement(By.xpath("./div/div/span[contains(text(), 'People')]")).click();
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
        getSearchBox().clear();
        waitUntilPageFinishLoading();
        getSearchBox().sendKeys(searchTerm);
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

    public void verifyRealTimeSearchResults(String searchRequest) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying real-time search displays results in dropdown.");
        doSearch(searchRequest);
        Assert.assertTrue("No real-time results displaying in dropdown!", getDriver().findElement(By.id("global-search-box-results")).isDisplayed());
    }

    public void verifyAdvanceSearchByEnterKey(String searchRequest) {
        waitUntilPageFinishLoading();
        System.out.println();
        logger.info("Verifying global search icon button returns advanced search results.");
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
        clickSearchIcon().click();
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
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String categoryTab : data.keySet()) {
            getDriver().findElement(By.id(data.get(categoryTab))).click();
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
        WebElement searchOption = getDriver().findElement(By.id("global-search-box-item-0"));
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
        clickSearchIcon().click();
        waitUntilPageFinishLoading();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String categoryTab : data.keySet()) {
            getDriver().findElement(By.id(data.get(categoryTab))).click();
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
//                    iconExist = getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/span/img")).size() != 0 || getDriver().findElements(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/i")).size() != 0;
                    /*Icon does not exists currently*/
//                    Assert.assertTrue("Icon is not displayed for Groups in real-time search.", iconExist);
                    Assert.assertTrue("Group title is not displayed for Groups in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/div/div[@class='title']")).isDisplayed());
                    Assert.assertTrue("Description is not displayed for Groups in real-time search.", getDriver().findElement(By.xpath("//div[@id='global-search-box-results']/div[@class='category']/div[@icon='comments outline']/div/div/div[@class='description']")).isDisplayed());
                    break;
                default:
                    Assert.fail(opt + " is not a valid search tab.  Valid categories: People, Institutions, or Groups");
            }
        }
    }

    public void verifyGlobalSearchNotDisplayed() {
        Assert.assertFalse("Global search feature is available, but shouldn't be.", getDriver().findElements(By.xpath("//div[@class='_102AwZzmP9JnZ9-ca_Y6cu']")).size() >= 1);
    }

    //Getters
    private WebElement getSearchBox() {
        waitUntilPageFinishLoading();
        return getDriver().findElement(By.id("global-search-box-input"));
    }
    private WebElement getSearchSwitcher(){
        waitUntilPageFinishLoading();
        return getDriver().findElement(By.id("global-search-box-filter"));
    }
    private WebElement clickSearchIcon(){
        return driver.findElement(By.xpath("//i[contains(@class, \"search link icon\")]"));
    }
}
