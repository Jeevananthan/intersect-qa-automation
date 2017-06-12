package pageObjects.SP.accountPages;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
    }

    public void verifyEndDateFeasibility() {
        String startDate = getHubModuleRow().findElement(By.className("_1dCEyx-42op_-Pf0-ie2T")).getText();
        String month = startDate.substring(0, 3);
        String dateNo = startDate.substring(4, 6);
        String year = startDate.substring(8, 12);
        getEndDateButton().click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);
        int dateNumber = Integer.parseInt(dateNo);
        --dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='" + dateNo + "']"));
        Assert.assertTrue("End date which is earlier then start date is enabled", Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getEndDateButton().click();
    }

    public void verifyStartDateFeasibility() {
        String endDate = getHubModuleRow().findElement(By.xpath("(//td[@class='_1dCEyx-42op_-Pf0-ie2T'])[2]")).getText();
        String month = endDate.substring(0, 3);
        String dateNo = endDate.substring(4, 6);
        String year = endDate.substring(8, 12);
        getStartDateButton().click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);
        int dateNumber = Integer.parseInt(dateNo);
        ++dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='" + dateNo + "']"));
        Assert.assertTrue("Start date which is past then end date is enabled", Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getStartDateButton().click();
    }

    //Getters
    private WebElement getEndDateButton() {
        return driver.findElement(By.xpath("(//button[@role='tooltip'])[2]"));
    }

    private WebElement getStartDateButton() {
        return driver.findElement(By.xpath("(//button[@role='tooltip'])[1]"));
    }

    private WebElement getHubModuleRow() {
        return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[1]"));
    }

    private WebElement getCalender() {
        return driver.findElement(By.xpath("//div[@role='application']"));
    }


    public void verifyCreatePrimaryUser() {
        Assert.assertTrue("\"Create\" button for new primary user was not found!", button("CREATE").isDisplayed());
    }

    //Verifying Institutional information for "Docufide Institute of Technology (not a real school)"
    public void verifyInstitutuionalInformation() {
        Assert.assertTrue("Institutional Account Address1 is wrong.", text("1800 Rodeo Drive").isDisplayed());
        Assert.assertTrue("Institutional Account Address2 is wrong.", text("Suite 100").isDisplayed());
        Assert.assertTrue("Institutional Account City is wrong.", text("Beverly Hills").isDisplayed());
        Assert.assertTrue("Institutional Account postal code is wrong.", text("90210").isDisplayed());
        Assert.assertTrue("Institutional Account SCID is wrong.", text("SCID:").isDisplayed());
    }

    public void verifyAccessToLogHistory(String visible) {
        if (visible == "Yes") {
            Assert.assertTrue("View Log History is not accessible", link("View Log History").isDisplayed());
        } else if (visible == "No") {
            Assert.assertFalse("View Log History should not be accessible", link("View Log History").isDisplayed());
        } else {
            logger.error("Invalid parameter passed");
        }
    }


    public void updateInstitutionalDetailsWithConnectAndRadiusId(String connectIdVal, String radiusIdVal) {

        WebElement EditBtn = driver.findElement(By.xpath("//div[@class='ui stackable equal width grid']/div/section[1]/h2/button/span[text()='Edit']"));
        Assert.assertTrue("Edit Button is not displayed for the update", EditBtn.isDisplayed());
        EditBtn.click();

        Assert.assertTrue("Connect Id textbox is not displayed", driver.findElement(By.cssSelector("input[aria-label='connect Id']")).isDisplayed());
        textbox(By.cssSelector("input[aria-label='connect Id']")).sendKeys(connectIdVal);

        Assert.assertTrue("Radius Id textbox is not displayed", driver.findElement(By.cssSelector("input[aria-label='radius Id']")).isDisplayed());
        textbox(By.cssSelector("input[aria-label='radius Id']")).sendKeys(radiusIdVal);

        driver.findElement(By.cssSelector("button[class='ui mini primary right floated button']")).click();
    }


    public void verifyInstitutionalDetails(DataTable dataTable) {

        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> IntitutionalDetails : entities) {
            for (String key : IntitutionalDetails.keySet()) {

                switch (key) {
                    case "Connect Id":
                        String actualConnectId = driver.findElement(By.cssSelector("div[class='connect-id']")).getText();
                        Assert.assertTrue("Connect Id was not as expected.", actualConnectId.contains(IntitutionalDetails.get(key)));
                        break;

                    case "Radius Id":
                        String actualRadiusId = driver.findElement(By.cssSelector("div[class='radius-id']")).getText();
                        Assert.assertTrue("Radius Id was not as expected.", actualRadiusId.contains(IntitutionalDetails.get(key)));
                        break;


                    }
                 }

            }
        }

    }


