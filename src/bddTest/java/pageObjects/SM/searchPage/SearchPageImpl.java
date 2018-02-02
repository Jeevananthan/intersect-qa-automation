package pageObjects.SM.searchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class SearchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SearchPageImpl() {
        logger = Logger.getLogger(SearchPageImpl.class);
    }

    public void openCollegeSearchPage() {
        driver.get("https://qa-supermatch.intersect.hobsons.com/");
    }

    public void verifyDarkBlueHeaderIsPresent() {
        Assert.assertTrue("The dark blue header is not displayed correctly",
                driver.findElement(By.xpath("//div[contains(@class, 'supermatch-custom-header')]"))
                        .getCssValue("background-color").equals("rgba(28, 29, 57, 1)")
                        && driver.findElement((By.xpath("//div[contains(@class, 'supermatch-custom-header')]/h1")))
                        .getText().equals("SuperMatch College Search"));
    }

    public void verifyChooseFitCriteriaBar() {
        List<WebElement> liElements = driver.findElements(By.xpath("//div[@class='supermatch-searchfilter-menu-container']//li"));

        Assert.assertTrue("'Choose Fit Criteria' text is not present", liElements.get(0).getText().contains("Choose Fit Criteria"));
        Assert.assertTrue("'Location' menu item is not present", liElements.get(1).getText().contains("Location"));
        Assert.assertTrue("'Academics' menu item is not present", liElements.get(2).getText().contains("Academics"));
        Assert.assertTrue("'Admission' menu item is not present", liElements.get(3).getText().contains("Admission"));
        Assert.assertTrue("'Diversity' menu item is not present", liElements.get(4).getText().contains("Diversity"));
        Assert.assertTrue("'Institution Characteristics' menu item is not present", liElements.get(5).getText().contains("Institution Characteristics"));
        Assert.assertTrue("'Cost' menu item is not present", liElements.get(6).getText().contains("Cost"));
        Assert.assertTrue("'Student Life' menu item is not present", liElements.get(7).getText().contains("Student Life"));
        Assert.assertTrue("'Athletics' menu item is not present", liElements.get(8).getText().contains("Athletics"));
        Assert.assertTrue("'Resources' menu item is not present", liElements.get(9).getText().contains("Resources"));
    }

    public void verifySelectCriteriaButtonAndInstructionalText() {

        Assert.assertTrue("'Select Criteria to Start' button is not displayed", driver.findElement(By.xpath("(//button[text()='Select Criteria To Start'])[2]")).isDisplayed());
        Assert.assertTrue("Instructional text is not displayed", driver.findElement(By.xpath("//div[@class='computer only four wide column']//p"))
                .getText().equals("To refine your results, use the arrows to move your criteria into the \"Must Have\" and \"Nice to Have\" boxes."));

    }

    public void verifyMustHaveAndNiceToHaveBoxes() {

        Assert.assertTrue("Title for Must Have box is not displayed",
                driver.findElement(By.xpath("(//div[@class='box box-selection'])[1]/p[@class='title']"))
                        .getText().equals("Must Have"));

        Assert.assertTrue("Helper Text for Must Have box is not displayed",
                driver.findElement(By.xpath("(//div[@class='box box-selection'])[1]/p[@class='helper-text']"))
                        .getText().equals("A Must Have is anything you absolutely need to be happy and successful."));

        Assert.assertTrue("Title for Nice to Have box is not displayed",
                driver.findElement(By.xpath("(//div[@class='box box-selection'])[2]/p[@class='title']"))
                        .getText().equals("Nice to Have"));

        Assert.assertTrue("Helper Text for Nice to Have box is not displayed",
                driver.findElement(By.xpath("(//div[@class='box box-selection'])[2]/p[@class='helper-text']"))
                        .getText().equals("A Nice to Have is anything that's important to you, but isn't an absolute must have."));

    }

    public void verifyEmptyResultsTable() {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'supermatch-results')]//h3")))
                .getText().equals("No Results Yet"));

        //Verify the table headers
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/thead/tr/th[2]")).getText()
                .equals("Fit Score"));

        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/thead/tr/th[3]")).getText()
                .equals("Academic Match"));

        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/thead/tr/th[4]")).getText()
                .equals("Pick what to show"));

        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/thead/tr/th[5]")).getText()
                .equals("Pick what to show"));

        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/thead/tr/th[6]")).getText()
                .equals("Pick what to show"));

        //Verify if the empty rows are present
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/tbody/tr[1]")).isDisplayed());

        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//table/tbody/tr[2]")).isDisplayed());

        //Verify if 'Select Criteria to Start' button is displayed in table
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'supermatch-results')]//button[text()='Select Criteria To Start']")).isDisplayed());

    }


    public void verifyDarkBlueFooter()
    {
        Assert.assertTrue("College Search footer is not displayed", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]")).isDisplayed());

        Assert.assertTrue("College Search footer is not dark blue", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]"))
                .getCssValue("background-color").equals("rgba(28, 29, 57, 1)"));

        Assert.assertTrue("Search box in College Search footer is not displayed", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]//input[@placeholder='Search...']"))
                .isDisplayed());

        Assert.assertTrue("'PINNED' menu is not displayed", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]//span[text()='Pinned']"))
                .isDisplayed());

        Assert.assertTrue("'THINKING ABOUT' menu is not displayed", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]//span[text()='Thinking About']"))
                .isDisplayed());

        Assert.assertTrue("'APPLYING TO' menu is not displayed", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]//span[text()='Applying To']"))
                .isDisplayed());

        Assert.assertTrue("'MORE' menu is not displayed", driver.findElement(By.xpath("//div[contains(@class, 'supermatch-footer')]//span[text()='More']"))
                .isDisplayed());
    }

    /**
     * Accepts a DataTable that describes the location fit criteria to be selected, and selects them from the dialog
     * @param dataTable - Valid sections:  Search Type, State or Province, Quick Selection: US Regions & Others, Campus Surroundings
     *                    Values for each section should be comma separated and in the format the page displays.  e.x.:  Mid-Atlantic, Northeast
     */
    public void setLocationCriteria(DataTable dataTable) {
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        getDriver().findElement(By.xpath("//li[contains(text(),'Location')]")).click();
        for (Map<String,String> criteria : entities) {
            for (String key : criteria.keySet()) {
                switch (key) {
                    // TODO - Some of this is not working yet
                    case "Search Type":
                        if (criteria.get(key).contains("distance"))
                            radioButton("searchByDistance").select();
                        else
                            radioButton("searchByStateOrRegion").select();
                        break;
                    case "State or Province":
                        String[] states = criteria.get(key).split(",");
                        for (String state : states) {
                            // TODO - Actually access this component - it's poorly labeled in the app
                        }
                        break;
                    case "Quick Selection: US Regions & Others":
                        String[] regions = criteria.get(key).split(",");
                        for (String region : regions) {
                            checkbox(region).select();
                        }
                        break;
                    case "Campus Surroundings":
                        String[] surroundings = criteria.get(key).split(",");
                        for (String surrounding : surroundings) {
                            checkbox(surrounding).select();
                        }
                        break;
                }
            }
        }
    }

}
