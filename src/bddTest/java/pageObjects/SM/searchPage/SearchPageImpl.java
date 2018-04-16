package pageObjects.SM.searchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
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

    public void verifyWidthsOfThreeBoxes() {
        Assert.assertEquals(25.0, round((firstBox().getSize().getWidth()/(double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
        Assert.assertEquals(37.5, round((secondBox().getSize().getWidth()/(double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
        Assert.assertEquals(37.5, round((thirdBox().getSize().getWidth()/(double) threeBoxContainer().getSize().getWidth()) * 100, 1), 1);
    }

    private WebElement threeBoxContainer() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ui equal width grid box-container']")));
    }

    private WebElement firstBox() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ui equal width grid box-container']/div[contains(@class, 'column')])[1]")));
    }

    private WebElement secondBox() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ui equal width grid box-container']/div[contains(@class, 'column')])[2]")));
    }

    private WebElement thirdBox() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ui equal width grid box-container']/div[contains(@class, 'column')])[3]")));
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
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
