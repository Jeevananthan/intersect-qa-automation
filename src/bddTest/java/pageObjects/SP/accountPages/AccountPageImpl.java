package pageObjects.SP.accountPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.InterfaceImplementation;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        selectMonth.selectByVisibleText(month);
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
    private WebElement getCalender(){ return driver.findElement(By.xpath("//div[@role='application']")); }


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

    public void selectVariousDateFilterInLogHistory(String Option)
    {
       //selects the filter dropdown with the given option like today , yesterday ...etc in log history table.
        WebElement DateDrpdown = getDriver().findElement(By.xpath("//div[@class='ui compact selection _19k4ypXU6Xw9uZfY55mEYk dropdown']"));
        DateDrpdown.click();
        getDriver().findElement(By.xpath("//span[text()='" + Option + "']")).click();
    }

    public void verifySelectedDateInLogHistory(String Option)
    {
        //verifying the selected option in the filter dropdown in Log History page.
        String SelectedOption = getDriver().findElement(By.xpath("//div[@class='text']")).getText();
        Assert.assertTrue("Selected option "+Option+" is not displayed in the dropdown",SelectedOption.contains(Option));
    }

    public void verifyCustomOptionFieldsInLogHistory(){

       //verifying the ''start date ,end date'' fields are displaying after choosing "custom" as my search option in log history page
        Assert.assertTrue("Start Date Field is not diaplyed",textbox(By.name("start-date-input")).isDisplayed());
        Assert.assertTrue("End Date Field is not diaplyed",textbox(By.name("end-date-input")).isDisplayed());
    }

    public String returnSubtractedDteWithGivenDays(Integer MonthDifference,Integer DayDifference,Integer YearDifference){

        //this function returns the ''past date'' after subtraction of given days.
        LocalDate LocalDateForManipulation = LocalDate.now().minusDays(DayDifference).minusMonths(MonthDifference).minusYears(YearDifference);
        Date LocaldateConversionToDate = Date.from(LocalDateForManipulation.atStartOfDay(ZoneId.systemDefault()).toInstant());

        SimpleDateFormat USDateFormat = new SimpleDateFormat("M/d/yyyy");
        String SubtractedDate =  USDateFormat.format(LocaldateConversionToDate);

        return SubtractedDate;

    }

    public void verifyLogHistoryResultsTable(String option)
    {
        //verifies the log history table is showng the related log records.
        switch (option)
        {
            case "Today":

                    if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){

                        logger.info("No ''log Results'' are displayed for the selection 'today' option");

                    } else {
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr[1]/td/span[1]")).getText();
                        String ExpectedDate = returnTodayDate();

                        if (Actualdate.contains(ExpectedDate)) {

                            logger.info("Log History results is verified for 'Today' option");
                        }
                    }
                    break;

            case "Yesterday":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Yesterday' option");

                } else {
                    String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr[1]/td/span[1]")).getText();
                    String ExpectedDate = returnSubtractedDteWithGivenDays(0,1,0);

                    if (Actualdate.contains(ExpectedDate)) {
                        logger.info("Log History results is verified for 'Yesterday' option");
                    }
                }
                break;

            case "Last 7 Days":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Last 7 Days' option");

                } else {
                    String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr[1]/td/span[1]")).getText();
                    String ExpectedDate = returnSubtractedDteWithGivenDays(0,7,0);

                    if (Actualdate.contains(ExpectedDate)) {
                        logger.info("Log History results is verified for 'Last 7 Days' option");
                    }
                }
                break;

            case "Last 30 Days":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Last 30 Days' option");

                } else {
                    String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr[1]/td/span[1]")).getText();
                    String ExpectedDate = returnSubtractedDteWithGivenDays(0,30,0);

                    if (Actualdate.contains(ExpectedDate)) {
                        logger.info("Log History results is verified for 'Last 30 Days' option");
                    }
                }
                break;
        }

        }


    public String returnTodayDate(){

        //returns today's date with U.S date format
        Date date = new Date();

        SimpleDateFormat TodayDate = new SimpleDateFormat("M/d/yyy");
        String TodayDateWithFormat = TodayDate.format(date);
        return TodayDateWithFormat;

    }



}
