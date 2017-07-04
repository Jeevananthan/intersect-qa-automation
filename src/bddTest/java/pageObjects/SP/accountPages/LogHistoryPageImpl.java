package pageObjects.SP.accountPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class LogHistoryPageImpl extends PageObjectFacadeImpl{
    private Logger logger;

    public LogHistoryPageImpl() {
        logger = Logger.getLogger(LogHistoryPageImpl.class);
    }

    public void selectVariousDateFilterInLogHistory(String Option)
    {
        //selects the filter dropdown with the given option like today , yesterday ...etc in log history table.
        //WebElement DateDropdown = getDriver().findElement(By.className("_19k4ypXU6Xw9uZfY55mEYk "));
        WebElement DateDropdown = getDriver().findElement(By.xpath("//div[@aria-label='Timeframe Switcher']"));

        DateDropdown.click();
        DateDropdown.findElement(By.xpath("//span[text()='" + Option + "']")).click();

        while(button("More Log Entries").isDisplayed()){
            button("More Log Entries").click();
        }
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

    private String returnSubtractedDteWithGivenDays(Integer MonthDifference,Integer DayDifference,Integer YearDifference){

        //this function returns the ''past date'' after subtraction of given days.
        LocalDate LocalDateForManipulation = LocalDate.now().minusDays(DayDifference).minusMonths(MonthDifference).minusYears(YearDifference);
        Date LocaldateConversionToDate = Date.from(LocalDateForManipulation.atStartOfDay(ZoneId.systemDefault()).toInstant());

        SimpleDateFormat USDateFormat = new SimpleDateFormat("M/d/yyyy");

        return USDateFormat.format(LocaldateConversionToDate);
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
                    List<WebElement> rows = driver.findElements(By.xpath("//table[@class='ui table']/tbody/tr"));
                    int  rowCount = rows.size();
                    for(int row =1;row<=rowCount;row++){
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr["+row+"]/td/span[1]")).getText();
                        String ExpectedDate = returnTodayDate();
                        if (Actualdate.contains(ExpectedDate)) {
                            logger.info("Log History results is verified for 'Today' option");
                        }
                    }
                }
                break;

            case "Yesterday":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Yesterday' option");

                } else {

                    List<WebElement> rows = driver.findElements(By.xpath("//table[@class='ui table']/tbody/tr"));
                    int  rowCount = rows.size();
                    for(int row =1;row<=rowCount;row++){
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr["+row+"]/td/span[1]")).getText();
                        String ExpectedDate = returnSubtractedDteWithGivenDays(0,1,0);
                        if (Actualdate.contains(ExpectedDate)) {
                            logger.info("Log History results is verified for 'Yesterday' option");
                        }
                    }
                }
                break;

            case "Last 7 Days":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Last 7 Days' option");

                } else {
                    List<WebElement> rows = driver.findElements(By.xpath("//table[@class='ui table']/tbody/tr"));
                    int  rowCount = rows.size();
                    for(int row =1;row<=rowCount;row++){
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr["+row+"]/td/span[1]")).getText();
                        String ExpectedDate = returnSubtractedDteWithGivenDays(0,7,0);
                        if (Actualdate.contains(ExpectedDate)) {
                            logger.info("Log History results is verified for 'Last 7 Days' option");
                        }
                    }
                }
                break;

            case "Last 30 Days":
                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Last 30 Days' option");

                } else {
                    List<WebElement> rows = driver.findElements(By.xpath("//table[@class='ui table']/tbody/tr"));
                    int rowCount = rows.size();
                    for (int row = 1; row <= rowCount; row++) {
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr[" + row + "]/td/span[1]")).getText();
                        String ExpectedDate = returnSubtractedDteWithGivenDays(0, 30, 0);
                        if (Actualdate.contains(ExpectedDate)) {
                            logger.info("Log History results is verified for 'Last 30 Days' option");
                        }
                    }
                }
                break;

            case "Last Week":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Last Week' option");

                } else {
                    List<WebElement> rows = driver.findElements(By.xpath("//table[@class='ui table']/tbody/tr"));
                    int  rowCount = rows.size();
                    for(int row =1;row<=rowCount;row++){
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr["+row+"]/td/span[1]")).getText();

                        for (int daysBetweenAWeek= 6;daysBetweenAWeek>=0;daysBetweenAWeek--){
                            String ExpectedDate = returnSubtractedDteWithGivenDays(0,daysBetweenAWeek,0);
                            if (Actualdate.contains(ExpectedDate)) {
                                logger.info("Log History results is verified for 'Last Week' option");
                            }

                        }

                    }
                }
                break;

            case "Last Month":

                if(driver.findElements(By.cssSelector("div[class='ui info message']>span")).size() !=0){
                    logger.info("No ''log Results'' are displayed for the selection 'Last Month' option");

                } else {
                    List<WebElement> rows = driver.findElements(By.xpath("//table[@class='ui table']/tbody/tr"));
                    int  rowCount = rows.size();
                    for(int row =1;row<=rowCount;row++){
                        String Actualdate = driver.findElement(By.xpath("//table[@class='ui table']/tbody/tr["+row+"]/td/span[1]")).getText();

                        for (int daysBetweenAMonth= 29;daysBetweenAMonth>=0;daysBetweenAMonth--){
                            String ExpectedDate = returnSubtractedDteWithGivenDays(0,daysBetweenAMonth,0);
                            if (Actualdate.contains(ExpectedDate)) {
                                logger.info("Log History results is verified for 'Last Month' option");
                            }

                        }

                    }
                }




        }

    }


    private String returnTodayDate(){

        //returns today's date with U.S date format
        Date date = new Date();

        SimpleDateFormat TodayDate = new SimpleDateFormat("M/d/yyy");

        return TodayDate.format(date);
    }


}
