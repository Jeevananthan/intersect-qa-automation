package pageObjects.SP.accountPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
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
        selectMonth.selectByVisibleText(selectMonth(month));
        int dateNumber = Integer.parseInt(dateNo);
        --dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("End date which is earlier then start date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getEndDateButton().click();
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
        selectMonth.selectByVisibleText(selectMonth(month));
        int dateNumber = Integer.parseInt(dateNo);
        ++dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("Start date which is past then end date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getStartDateButton().click();
    }

    public void verifyModuleDetails(String status,String startDate,String endDate,String module){
        String ActualStatus;
        switch (module){

            case "Legacy: Hub page management":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    String ActualStartDate = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStartDate.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    String ActualEndDate = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualEndDate.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;


            case "Legacy: Community":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;

            case "Intersect Awareness Subscription":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;

            case "Intersect Presence Subscription":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;
        }
    }

    private String dateCompareFormatter(Calendar cal){
        String moduleSubscriptionDateFormat = "MMM d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(moduleSubscriptionDateFormat);
        return sdf.format(cal.getTime());
    }

    //Getters
    private WebElement getEndDateButton(){ return driver.findElement(By.xpath("//button[@aria-label='End Date Calendar']")); }
    private WebElement getStartDateButton(){ return driver.findElement(By.xpath("//button[@aria-label='Start Date Calendar']")); }
    private WebElement getHubModuleRow(){ return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[1]")); }
    private WebElement getCalender(){ return driver.findElement(By.xpath("//div[@role='application']")); }
    private WebElement getSaveChangesButton(){ return button("Save Changes"); }

    public String selectMonth(String month)
    {
        String selectedMonth="";
        List<String> monthValue=new ArrayList<>();
        monthValue.add("January");
        monthValue.add("February");
        monthValue.add("March");
        monthValue.add("April");
        monthValue.add("May");
        monthValue.add("June");
        monthValue.add("July");
        monthValue.add("August");
        monthValue.add("September");
        monthValue.add("October");
        monthValue.add("November");
        monthValue.add("December");
        for(String Month:monthValue)
        {
           String actualMonth=Month.substring(0,3);
            if(month.contains(actualMonth))
            {
                selectedMonth=Month;
                break;
            }
        }
        return selectedMonth;
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

    public void clicksaveChangesButton(){
        if(getSaveChangesButton().isDisplayed()) {
            getSaveChangesButton().click();
        }
        waitUntilPageFinishLoading();
    }



    public void setModuleStatusAsActiveOrInActiveWithDate(String moduleName, String status){

        WebElement subscription = driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']"));

       WebElement ActualStatus = getParent(getParent(subscription)).findElement(By.cssSelector("[aria-label='Module Status Selector'] > div"));
       if(!ActualStatus.getText().equalsIgnoreCase(status)){
           ActualStatus.click();
           WebElement selectstatusDrp = driver.findElement(By.xpath("//div[@class='menu transition visible']//span[text()='"+status+"']"));
           driver.executeScript("arguments[0].click();",selectstatusDrp);

           if(!status.equalsIgnoreCase("inactive")) {
               WebElement StartDateButton = getParent(getParent(subscription)).findElement(By.xpath("//td[4]/button/i"));
               WebElement EndDateButton = getParent(getParent(subscription)).findElement(By.xpath("//td[5]/button/i"));

               StartDateButton.click();
               setStartDateInModulePage();
               StartDateButton.click();
               EndDateButton.click();
               setEndDateInModulePage();
               EndDateButton.click();
           }
       }
    }

    public void setStartDateInModulePage(){

        String startDate = "June 13, 2017";
        String month = startDate.substring(0, 4);
        String dateNo = startDate.substring(5, 7);
        String year = startDate.substring(9, 13);

        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();


    }
    public void setEndDateInModulePage(){

        String endDate = "June 13, 2018";
        String month = endDate.substring(0, 4);
        String dateNo = endDate.substring(5, 7);
        String year = endDate.substring(9, 13);

        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();

    }



}



