package pageObjects.HE.repVisitsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Month;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import static pageObjects.HS.repVisitsPage.RepVisitsPageImpl.CollegeFairName;

public class RepvisitsSideBarPage extends PageObjectFacadeImpl {
    protected Logger logger;

    public RepvisitsSideBarPage() {
        logger = Logger.getLogger(RepvisitsSideBarPage.class);
    }

    /*
      @params SchoolName ,which has to be verified on the repvisits sidebar
     */
    public void assertRepvisitSidebar(String actualSchoolName) {
        logger.info("assertRepvisitSidebar started");
        String expSchoolName = schoolNameOnRepVSidebar().getText();
        Assert.assertTrue("FAIL:Should display the " + actualSchoolName + "On the Side bar Header" + "Displayed Name on Side bar Header  is" + expSchoolName, expSchoolName.equalsIgnoreCase(actualSchoolName));
        logger.info("assertRepvisitSidebar Completed ");
    }
    /*
       Verifies the default tab on the repvisits sidebar
     */
    public void assertDefaultTab() {
        Assert.assertTrue("'Visits' tab is not displayed as Default tab", fairsTab().isDisplayed());
        fairsTab().click();
        Assert.assertTrue("Unable to Toggle 'Fairs' tab", fairsTab().isEnabled());
        visitsTab().click();
        Assert.assertTrue("Unable to Toggle 'Visits' tab", visitsTab().isEnabled());
    }
    /*
       Verifies the week start and week end dates
       displayed on the side bar
     */
    public void assertWeekDays() {
        logger.info("assertWeekDays started");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String[] actualDisplayedDate = actualWeekDatesOnSideBar().getText().split(":");
        String[] arrDisplayedDate=actualDisplayedDate[1].split("–");
        String actualStartDate=arrDisplayedDate[0].trim();
        String actualEndDate=arrDisplayedDate[1].trim();
        LocalDate expMon=getWeekDays();
        LocalDate expFri=expMon.plusDays(4);
        String nextStartMon=formatter.format(expMon);
        String nextStartFri=formatter.format(expFri);
        String[] newStartDate=nextStartMon.split(" ");
        String expStartDate=newStartDate[1];
        expStartDate=expStartDate.concat(" ").concat(newStartDate[0]);
        String[] newEndDate=nextStartFri.split(" ");
        String expWeekEndDate=newEndDate[1];
        expWeekEndDate=expWeekEndDate.concat(" ").concat(newEndDate[0]).concat(", ").concat(newEndDate[2]);
        Assert.assertTrue("FAIL:Unable to verify the Week Start Date "+"\n" +"Expected :"+expStartDate+ "\n" + "Actual:"+actualStartDate,expStartDate.trim().equals(actualStartDate));
        Assert.assertTrue("FAIL:Unable to verify the Week End Date " +"Expected :"+expWeekEndDate+ "\n" +"Actual:"+actualEndDate,expWeekEndDate.trim().equals(actualEndDate));
    }
        /*
            Verifying the closed side bar
       */
        public void verifySideBarClose(){
            List<WebElement> ele =sideBarElement();
            Assert.assertTrue("FAIL:Unable to close the Side bar",ele.isEmpty());
        }

        /*
          Performing click outside (somewhere else in community) close the side bar of Repvisits
         */
        public void clickOutsideRepvisitSidebar(){
            communityFrame();
            communityElement().click();
            driver.switchTo().defaultContent();

        }


    /*
       Returns the next week (Mon) from the current system date
     */
     public LocalDate getWeekDays(){
         Calendar currentDate = Calendar.getInstance();
         SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
         String  strDate = formatter.format(currentDate.getTime());
         String[] date=strDate.split(" ");
         String month=date[1];
         int Year =Integer.parseInt(date[2]);
         int day=Integer.parseInt(date[0]);

         LocalDate nextMonday=null;

         switch(month){

             case "January":
                 nextMonday = LocalDate.of(Year, Month.JANUARY, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "February":
                 nextMonday = LocalDate.of(Year, Month.FEBRUARY, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

                 break;

             case "March":
                 nextMonday = LocalDate.of(Year, Month.MARCH, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "April":
                 nextMonday = LocalDate.of(Year, Month.APRIL, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "May":
                 nextMonday = LocalDate.of(Year, Month.MAY, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "June":
                 nextMonday = LocalDate.of(Year, Month.JUNE, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "July":
                 nextMonday = LocalDate.of(Year, Month.JULY, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "August":
                 nextMonday = LocalDate.of(Year, Month.AUGUST, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "September":
                 nextMonday = LocalDate.of(Year, Month.SEPTEMBER, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "October":
                 nextMonday = LocalDate.of(Year, Month.OCTOBER, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "November":
                 nextMonday = LocalDate.of(Year, Month.NOVEMBER, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

             case "December":
                 nextMonday = LocalDate.of(Year, Month.DECEMBER, day);
                 nextMonday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                 break;

         }
         return nextMonday;

     }
     /*
        performing click on Fairs
      */
    public void chooseFairsToggle(){
        fairsToggle().click();
    }
    /*
        Verifying the created Fair from HS in HE
     */
    public void assertFairs(String type) {
        waitForUITransition();
        List<WebElement> fairsEle = allListedFairs();
        boolean flag = true;
        ArrayList<String> allFairs = new ArrayList<String>();
        for (WebElement ele : fairsEle) {
            allFairs.add(ele.getText());
        }
        for (int i = 0; i < allFairs.size(); i++) {

            if (allFairs.get(i).equalsIgnoreCase(CollegeFairName)) {

                flag = false;
                break;
            }
        }

        if (type.equals("UnPublished")) {
            Assert.assertTrue("FAIL:Unable to verify the Un-Published Fair: " + CollegeFairName, flag);
        }
        if (type.equals("Published")){

            Assert.assertTrue("FAIL:Unable to verify the Published Fair: " + CollegeFairName, flag == false);
        }
    }
    /*
      Verifying addToTravel Plan option on Repvisits side bar
     */
    public  void verifyAddToTravelPlan(String toggle){
        if(toggle.contains("Fairs")){
            Assert.assertTrue("FAIL:Unable to verify  AddToTravelPlan on "+toggle,addToTravelPlan().isDisplayed());
        }
        else if(toggle.contains("Visits")){
            Assert.assertTrue("FAIL:Unable to verify  AddToTravelPlan on "+toggle,addToTravelPlan().isDisplayed());
        }
    }
    public void  clickOnAddToTravelPlan(){
    addToTravelPlan().click();
    }

    private WebElement schoolNameOnRepVSidebar() {return driver.findElement(By.className("_1KEXOb-pgSYxR2D-fFD4Ho"));}

    private WebElement fairsTab(){
        return driver.findElement(By.xpath("//span[text()='Fairs']"));
    }
    private WebElement visitsTab(){

         return   driver.findElement(By.xpath("//span[text()='Visits']"));
    }
    private  WebElement actualWeekDatesOnSideBar(){
        return driver.findElement(By.className("vofnkjUAfAKJqjVtC4bQY"));
    }
    private  List<WebElement> sideBarElement(){
        return driver.findElements(By.className("_3eg-EWDKKG9GY-4leG7Rcr"));
    }
    private WebElement communityElement(){
        return driver.findElement(By.xpath("//a[contains(text(),'Feed')]"));
    }
    private  List<WebElement> allListedFairs(){
        return driver.findElements(By.className("bdmGO6RgPWFfr654p02yx"));
    }

    private  WebElement fairsToggle(){
        return  driver.findElement(By.className("H9FDIo1OJvBPaXlvHPSmL"));
    }
    private WebElement addToTravelPlan(){
        return  driver.findElement(By.xpath("//span[contains(text(),'Add school to travel plan')]"));
    }
}