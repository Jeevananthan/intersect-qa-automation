package pageObjects.SM.searchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class SearchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SearchPageImpl() {
        logger = Logger.getLogger(SearchPageImpl.class);
    }

    public void verifyStudentBodyUI() {
        driver.findElement(By.xpath("//div[@class='supermatch-searchfilter-menu-container']//li[contains(text(), 'Institution Characteristics')]")).click();
        Assert.assertTrue("'All students' radio button is not selected by default", driver.findElement(By.xpath("//label[contains(text(), 'All students')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Undergraduate students only')]")).click();
        Assert.assertTrue("'Undergraduate students only' radio button is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Undergraduate students only')]//preceding-sibling::input")).isSelected());

        driver.findElement(By.xpath("//label[contains(text(), 'Very large (Over 20,000 students)')]")).click();
        Assert.assertTrue("'Very large (Over 20,000 students)' checkbox is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Very large (Over 20,000 students)')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Very large (Over 20,000 students)')]")).click();
        Assert.assertFalse("'Very large (Over 20,000 students)' checkbox is selected", driver.findElement(By.xpath("//label[contains(text(), 'Very large (Over 20,000 students)')]//preceding-sibling::input")).isSelected());

        driver.findElement(By.xpath("//label[contains(text(), 'Large (13,001 to 20,000 students)')]")).click();
        Assert.assertTrue("'Large (13,001 to 20,000 students)' checkbox is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Large (13,001 to 20,000 students)')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Large (13,001 to 20,000 students)')]")).click();
        Assert.assertFalse("'Large (13,001 to 20,000 students)' checkbox is selected", driver.findElement(By.xpath("//label[contains(text(), 'Large (13,001 to 20,000 students)')]//preceding-sibling::input")).isSelected());

        driver.findElement(By.xpath("//label[contains(text(), 'Mid-Size (7,001 to 13,000 students)')]")).click();
        Assert.assertTrue("'Mid-Size (7,001 to 13,000 students)' checkbox is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Mid-Size (7,001 to 13,000 students)')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Mid-Size (7,001 to 13,000 students)')]")).click();
        Assert.assertFalse("'Mid-Size (7,001 to 13,000 students)' checkbox is selected", driver.findElement(By.xpath("//label[contains(text(), 'Mid-Size (7,001 to 13,000 students)')]//preceding-sibling::input")).isSelected());

        driver.findElement(By.xpath("//label[contains(text(), 'Medium (4,001 to 7,000 students)')]")).click();
        Assert.assertTrue("'Medium (4,001 to 7,000 students)' checkbox is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Medium (4,001 to 7,000 students)')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Medium (4,001 to 7,000 students)')]")).click();
        Assert.assertFalse("'Medium (4,001 to 7,000 students)' checkbox is selected", driver.findElement(By.xpath("//label[contains(text(), 'Medium (4,001 to 7,000 students)')]//preceding-sibling::input")).isSelected());

        driver.findElement(By.xpath("//label[contains(text(), 'Small (2,001 to 4,000 students)')]")).click();
        Assert.assertTrue("'Small (2,001 to 4,000 students)' checkbox is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Small (2,001 to 4,000 students)')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Small (2,001 to 4,000 students)')]")).click();
        Assert.assertFalse("'Small (2,001 to 4,000 students)' checkbox is selected", driver.findElement(By.xpath("//label[contains(text(), 'Small (2,001 to 4,000 students)')]//preceding-sibling::input")).isSelected());

        driver.findElement(By.xpath("//label[contains(text(), 'Very Small (2,000 or fewer students)')]")).click();
        Assert.assertTrue("'Very Small (2,000 or fewer students)' checkbox is not selected", driver.findElement(By.xpath("//label[contains(text(), 'Very Small (2,000 or fewer students)')]//preceding-sibling::input")).isSelected());
        driver.findElement(By.xpath("//label[contains(text(), 'Very Small (2,000 or fewer students)')]")).click();
        Assert.assertFalse("'Very Small (2,000 or fewer students)' checkbox is selected", driver.findElement(By.xpath("//label[contains(text(), 'Very Small (2,000 or fewer students)')]//preceding-sibling::input")).isSelected());

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
