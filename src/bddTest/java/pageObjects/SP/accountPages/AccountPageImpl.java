package pageObjects.SP.accountPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;


public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
    }

    public void setModuleStatusAsActiveOrInActiveWithDate(String moduleName, String status){

        WebElement subscription = driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']"));

        WebElement ActualStatus = getParent(getParent(subscription)).findElement(By.cssSelector("[aria-label='Module Status Selector'] > div"));
        if(!ActualStatus.getText().equalsIgnoreCase(status)){
            ActualStatus.click();
            getDriver().findElement(By.xpath("//span[text()='" + status + "']")).click();
        }
    }

    public void setStartDateInAccountPage(String dateToSet, String moduleName){
        String[] parts = dateToSet.split(" ");
        String month = parts[0];
        String day = parts[1];
        String year = parts[2];

        WebElement subscription = driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']"));
        WebElement StartDateButton = getParent(getParent(subscription)).findElement(By.xpath("//td[4]/button/i"));
        StartDateButton.click();

        driver.findElement(By.id("year-select")).click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        driver.findElement(By.id("month-select")).click();
        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+day+"']"));
        dateTemp.click();
        getStartDateButton().click();
    }

    public void setEndDateInAccountPage(String dateToSet, String moduleName){
        String[] parts = dateToSet.split(" ");
        String month = parts[0];
        String day = parts[1];
        String year = parts[2];

        WebElement subscription = driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']"));
        WebElement EndDateButton = getParent(getParent(subscription)).findElement(By.xpath("//td[5]/button/i"));
        EndDateButton.click();
        driver.findElement(By.id("year-select")).click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        driver.findElement(By.id("month-select")).click();
        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+day+"']"));
        dateTemp.click();
        getEndDateButton().click();
    }

    public void clicksaveChangesButton(){
        Assert.assertTrue("Save Changes button is not displayed",getSaveChangesButton().isDisplayed());
        getSaveChangesButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyEndDateFeasibility(){
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
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("End date which is earlier then start date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getEndDateButton().click();
    }

    public void quoteChargeId(String value,String option)
    {
        Assert.assertTrue("quoteChargeId textbox is not displayed",driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//span[contains(text(),'"+option+"')]/../following-sibling::td/div/input")).isDisplayed());
        driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//span[contains(text(),'"+option+"')]/../following-sibling::td/div/input']")).sendKeys(value);

    }

    public void verifyStartDateFeasibility(){
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
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("Start date which is past then end date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getStartDateButton().click();
    }

    //Getters
    private WebElement getEndDateButton(){ return driver.findElement(By.xpath("(//button[@role='tooltip'])[2]")); }
    private WebElement getStartDateButton(){ return driver.findElement(By.xpath("(//button[@role='tooltip'])[1]")); }
    private WebElement getHubModuleRow(){ return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[1]")); }
    private WebElement getCommunityModuleRow(){return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[2]")); }
    private WebElement getIntersectAwarnessModuleRow(){return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[3]")); }
    private WebElement getIntersectPresenceModuleRow(){return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[4]")); }


    public void verifyModuleDetails(String Column,String Status,String Module){

        String ActualStatus;
           switch (Module){

               case "Legacy: Hub page management":

                   if(Column.equalsIgnoreCase("Status")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("Start Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("End Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(Status));

                   }
                   break;


               case "Legacy: Community":
                   if(Column.equalsIgnoreCase("Status")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("Start Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[4]")).getText();
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("End Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[5]")).getText();
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(Status));

                   }
                   break;

               case "Intersect Awareness Subscription":
                   if(Column.equalsIgnoreCase("Status")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("Start Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[4]")).getText();
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("End Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[5]")).getText();
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(Status));

                   }
                   break;

               case "Intersect Presence Subscription":
                   if(Column.equalsIgnoreCase("Status")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("Start Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(Status));
                   }
                   else if(Column.equalsIgnoreCase("End Date")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(Status));

                   }
                   break;

                    }

            }


    private WebElement getCalender(){ return driver.findElement(By.xpath("//div[@role='application']")); }
    private WebElement getSaveChangesButton(){
        return button("Save Changes");
    }

    public void verifyCreatePrimaryUser() {
        Assert.assertTrue("\"Create\" button for new primary user was not found!", button("CREATE").isDisplayed());
    }

    //Verifying Institutional information for "Docufide Institute of Technology (not a real school)"
    public void verifyInstitutuionalInformation() {
        Assert.assertTrue("Institutional Account Address1 is wrong.",text("1800 Rodeo Drive").isDisplayed());
        Assert.assertTrue("Institutional Account Address2 is wrong.",text("Suite 100").isDisplayed());
        Assert.assertTrue("Institutional Account City is wrong.",text("Beverly Hills").isDisplayed());
        Assert.assertTrue("Institutional Account postal code is wrong.",text("90210").isDisplayed());
        Assert.assertTrue("Institutional Account SCID is wrong.",text("SCID:").isDisplayed());
    }

    public void verifyAccessToLogHistory(String visible){
        if(visible == "Yes"){
            Assert.assertTrue("View Log History is not accessible", link("View Log History").isDisplayed());
        }
        else if (visible == "No"){
            Assert.assertFalse("View Log History should not be accessible", link("View Log History").isDisplayed());
        }
        else {
            logger.error("Invalid parameter passed");
        }
    }

}
