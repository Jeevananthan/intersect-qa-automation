package pageObjects.COMMON;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.SeleniumBase;

import java.util.List;

public class    GlobalSearch extends SeleniumBase {

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
        getSearchBox().clear();
        getSearchBox().sendKeys(searchTerm);
        waitUntilPageFinishLoading();
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
        setSearchCategory(category);
        doSearch("t ");
        link("More...").click();
        Assert.assertTrue("Did not end on Advanced Search page!  No Update Search buttons is present!", button("UPDATE SEARCH").isDisplayed());
    }

    //Getters
    private WebElement getSearchBox() {
        return getDriver().findElement(By.id("global-search-box-input"));
    }
    private WebElement getSearchSwitcher(){
        return getDriver().findElement(By.id("global-search-box-filter"));
    }
}
