package pageObjects.HE.repVisitsPage;

import cucumber.api.DataTable;
import cucumber.api.java.cs.A;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import static org.junit.Assert.fail;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
    }
    public void checkHighSchoolPopUp(DataTable dataTable){
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String,String> school : entities){
            for (String key : school.keySet()) {
                switch (key) {
                    case "School Name":
                        String header = driver.findElement(By.className("_2XeJUEZaR8a0YG2vHm_ZA5")).getText();
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = driver.findElement(By.className("_2-cmgeOoondfKX-iEm8xCP")).getText();
                        Assert.assertTrue("Contact name was not found in header.",contactLink.contains(school.get(key)));
                        break;
                    default:
                        WebElement label = driver.findElement(By.xpath("//div[contains(text(),'" + key + "')]"));
                        WebElement item = getParent(label);
                        String content = item.findElement(By.className("_2WVz9-VTbgicuNzZo9Vc_o")).getText();
                        Assert.assertTrue(key + " was not found in the popup",content.contains(school.get(key)));
                }
            }
        }
        validateInfolink();
    }

    private void validateInfolink(){
        link("For more information:").click();
        Assert.assertTrue("Did not end up on Community URL!", driver.getCurrentUrl().contains("counselor-community/institution"));

    }
    public void verifySearchAndSchedulePage() {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        WebElement dateBar = driver.findElement(By.className("_2Y4XoXCJpDOFoe0UYkEn-I"));
        Assert.assertTrue("Previous Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Previous week']")).isDisplayed());
        Assert.assertTrue("Next Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Next week']")).isDisplayed());
        Assert.assertTrue("Calendar button is not present!",dateBar.findElement(By.className("calendar")).isDisplayed());
        Assert.assertTrue("Placeholder text for search box was not present!", textbox("Enter a school name or location").isDisplayed());
    }

    public void searchforHighSchool(String schoolName) {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
    }

    public void selectHighSchoolFromIntermediateSearchResults(String schoolName, String location) {
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
        waitUntilPageFinishLoading();
    }

    public void viewMapPlugin() {
        getMapButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyOverviewPage(){
        navBar.goToRepVisits();
        getOverviewBtn().click();
        Assert.assertTrue("Coming Soon message is not displaying",
                getComingSoonMessageInOverviewPage().getText().equals("The Overview Dashboard is coming soon. It will provide a quick view of your upcoming appointments and most recent notifications."));
    }

    public void verifyCheckRepVisitsAvailabilityButton(){
        driver.switchTo().frame(0);
        Assert.assertTrue("Check RepVisits Availability Button is not present", getCheckRepVisitsAvailabilityButton().isDisplayed());
        getCheckRepVisitsAvailabilityButton().click();
        driver.switchTo().defaultContent();
        Assert.assertTrue("RepVisits Availability Sidebar is not displaying.", getRepVisitsAvailabilitySidebar().isDisplayed());
    }

    //locators
    public void selectSchoolFromMap(String schoolName) {
        button(schoolName).click();
        waitUntilPageFinishLoading();
    }

    public void searchSchoolinRepVisits(String school)
    {
        navBar.goToRepVisits();
        driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).sendKeys(school);
        WebElement element1=driver.findElement(By.xpath("//button[@class='ui button']"));
        waitUntilElementExists(element1);
        driver.findElement(By.xpath("//button[@class='ui button']")).click();
        WebElement element=driver.findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));
        waitUntilElementExists(element);
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//a[contains(text(),'"+school+"')]")).isDisplayed());
        driver.findElement(By.xpath("//a[contains(text(),'"+school+"')]")).click();
    }
    public void visitsSchedule(String school,String date,String time)
    {
        driver.findElement(By.xpath("//span[text()='Visits']")).click();
        WebElement element=driver.findElement(By.xpath("//a[text()='"+school+"']"));
        waitUntilElementExists(element);
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//div/a[text()='"+school+"']")).isDisplayed());
        WebElement element1=driver.findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(element1);
        driver.findElement(By.xpath("//button[text()='Go To Date']")).click();
        setSpecificDate(7);
        driver.findElement(By.xpath("//div/div/button[text()='"+time+"']")).click();
    }

    public void selectDate(String date)
    {
        String[] value=date.split(" ");
        driver.findElement(By.xpath("//button[text()='Go To Date']")).click();
        String yearandmonth=value[0]+" "+value[2];
        String element=driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div")).getText();
        if(yearandmonth.equals(element))
        {
            driver.findElement(By.xpath("//div[text()='"+value[1]+"']")).click();
        }else{
            while(!yearandmonth.equals(element))
            {
                driver.findElement(By.xpath("//div[@class='DayPicker-NavBar']/span[2]")).click();
                element=driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div")).getText();
            }
            driver.findElement(By.xpath("//div[text()='"+value[1]+"']")).click();
        }}

    public void setDate(String inputDate) {

                        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        clickOnDay(parts[1]);
       waitUntilPageFinishLoading();
            }


    public void setSpecificDate(int addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void clickOnDay(String date) {
        driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[contains(text(), "+date+")]")).click();
            }

    public void findMonth(String month) {

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        }
        catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }
    }

    public void verifySchedulePopup(String school,String startTime,String endTime)
    {
        WebElement element1=driver.findElement(By.xpath("//button[contains(text(),'Yes, Request this time')]"));
        waitUntilElementExists(element1);
        Assert.assertTrue("SchedulePopup is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]")).isDisplayed());
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]")).isDisplayed());
        Assert.assertTrue("time is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+startTime+"-"+endTime+"')]")).isDisplayed());
        driver.findElement(By.xpath("//button[contains(text(),'Yes, Request this time')]")).click();
    }
    public void  verifyPills(String time,String school)
    {
        WebElement date=driver.findElement(By.xpath("//button[@class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']"));
        waitUntilElementExists(date);
        try {
            if(driver.findElement(By.xpath("//div[text()='"+school+"']/../../../../following-sibling::td//div/button[text()='"+time+"']")).isDisplayed())
            fail("Time slot is displayed");
        } catch (Exception e)
        {
        }
    }
    public void verifyUpgradeMessageInTravelPlanInRepVisits(){

        navBar.goToRepVisits();
        getTravelPlanBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.cssSelector(" i[class='icons']")).isDisplayed());
}

    private WebElement getOverviewBtn() {
        return link("Overview");
    }
    private WebElement getSearchAndScheduleBtn() {
        return link("Search and Schedule");
    }
    private WebElement getCalendarBtn() {
        return link("Calendar");
    }
    private WebElement getTravelPlanBtn() {
        return link("Travel Plan");
    }
    private WebElement getContactsBtn() {
        return link("Contacts");
    }
    private WebElement getRecommendationsBtn() {
        return link("Recommendations");
    }
    private WebElement getNotificationsBtn() {
        return link("Notifications");
    }
    private WebElement getSearchBox() { return textbox("Enter a school name or location");}
    private WebElement getSearchButton() { return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));}
    private WebElement getMapButton() { return driver.findElement(By.cssSelector("[class='map outline big icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return driver.findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
    private WebElement getCheckRepVisitsAvailabilityButton(){ return driver.findElement(By.className("check-repvisits-link")); }
    private WebElement getRepVisitsAvailabilitySidebar(){ return driver.findElement(By.className("_36B3QS_3-4bR8tfro5jydy")); }

}


