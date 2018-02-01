package pageObjects.HE.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import javafx.scene.input.DataFormat;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.ParseException;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

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
                        String header = driver.findElement(By.className("_2sidGZdt-6WEIYD6BrBvZ")).getText();
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = driver.findElement(By.className("_16fRAQOOUtmia2wftYHDhf")).getText();
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
        Assert.assertTrue("Text 'For more information' is not displayed", text("For more information:").isDisplayed());
        WebElement item = getParent(text("For more information:"));
        item.findElement(By.tagName("a")).click();
        Assert.assertTrue("Did not end up in Community!", driver.getCurrentUrl().contains("counselor-community/institution"));
    }

    public void verifySearchAndSchedulePage() {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        WebElement dateBar = driver.findElement(By.className("_2Y4XoXCJpDOFoe0UYkEn-I"));
        // These calendar controls have been moved to only appear after a search, this is covered by MATCH-2133.
        // Move these validations into that ticket when automated.
//        Assert.assertTrue("Previous Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Previous week']")).isDisplayed());
//        Assert.assertTrue("Next Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Next week']")).isDisplayed());
//        Assert.assertTrue("Calendar button is not present!",dateBar.findElement(By.className("calendar")).isDisplayed());
//        Assert.assertTrue("Placeholder text for search box was not present!", textbox("Enter a school name or location").isDisplayed());
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
        waitUntilElementExists(getComingSoonMessageInOverviewPage());
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
    public void verifyHSSpecialInstructions(String instructions)
    {
        Assert.assertTrue("High School specila instructions are not matching/available",getParent(driver.findElement(By.className("kuh1rp3g-UeGhggKqCdPA"))).findElement(By.cssSelector("div:nth-child(4)")).getText().contains(instructions));
    }
    public void selectSchoolFromMap(String schoolName) {
        button(schoolName).click();
        waitUntilPageFinishLoading();
    }

    public void clickUpgradeButton(){
        driver.findElement(By.xpath("//div[@class='seven wide column _2I5Wf1vjM_1kmY7BHT_G9k']//div/button/span[text()='UPGRADE']")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyUpgradePopupAndInformations(DataTable dataTable){

        List<Map<String,String>> entities = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> UpgradeInformationPopup : entities ) {
            for (String key : UpgradeInformationPopup.keySet()) {
                switch (key) {
                    case "First Name":
                        String actualFirstName = driver.findElement(By.id("field13")).getAttribute("value");
                        Assert.assertTrue("First Name was not as expected.", actualFirstName.contains(UpgradeInformationPopup.get(key)));
                        break;
                    case "Last Name":
                        String actualLastName = driver.findElement(By.id("field14")).getAttribute("value");
                        Assert.assertTrue("Last Name was not as expected.", actualLastName.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Work Email Address":
                        String actualEmailAddress = driver.findElement(By.id("field12")).getAttribute("value");
                        Assert.assertTrue("Work Email Address was not as expected.", actualEmailAddress.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Phone":
                        String actualPhone = driver.findElement(By.id("field15")).getAttribute("value");
                        Assert.assertTrue("Phone was not as expected.", actualPhone.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "School / Institution Name":
                        String actualSchoolInstitutionName = driver.findElement(By.id("field16")).getAttribute("value");
                        Assert.assertTrue("School / Institution Name was not as expected.", actualSchoolInstitutionName.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Message":
                        String actualMessage = driver.findElement(By.id("field18")).getText();
                        Assert.assertTrue("Messages was not as expected.", actualMessage.equals(UpgradeInformationPopup.get(key)));
                        break;
                }
            }
            button("Request Information").click();
            Assert.assertTrue("success message is not displayed",driver.findElement(By.xpath("//span[text()='Thanks!']")).isDisplayed());
            driver.findElement(By.xpath("//div[@id='upgrade-form']/i[@class='close icon']")).click();
        }

    }

    public void verifyUpgradeMessageInRecommendationspage()
      {
          navBar.goToRepVisits();
          waitUntilElementExists(getRecommendationsBtn());
          getRecommendationsBtn().click();
          waitUntilPageFinishLoading();
          Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
          Assert.assertTrue("'UPGRADE' button is not displayed",button("UPGRADE").isDisplayed());
          Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
      }


   public void verifyUpgradeMessageInContactspage()
   {
       navBar.goToRepVisits();
       waitUntilElementExists(getContactsBtn());
       getContactsBtn().click();
       waitUntilPageFinishLoading();
       Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
       Assert.assertTrue("'UPGRADE' button is not displayed",button("UPGRADE").isDisplayed());
       Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
   }
    public void verifySearchResultOfSearchAndSchedule(DataTable dataTable){
        DataTable transposedTable = dataTable.transpose();
        Map<String,String> searchMap = transposedTable.asMap(String.class, String.class);
        Set<String> searchCategory = searchMap.keySet();
        for (String category : searchCategory ) {
            searchforHighSchool(searchMap.get(category));
            try {
                Assert.assertTrue("HS data is showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//table[@class='ui very basic table']")).isDisplayed());
            } catch (NoSuchElementException nsee){
                Assert.assertTrue("HS data is not showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//span[contains(text(),'No results found')]")).isDisplayed());
            }
        }
    }

    public void verifyUpgradeMessageInTravelPlanInRepVisits(){
        navBar.goToRepVisits();
        waitUntilElementExists(getTravelPlanBtn());
        getTravelPlanBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
        Assert.assertTrue("'UPGRADE' button is not displayed",button("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
}


    public void verifyUpgradeMessageInContactsInRepVisits(){

        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
    }

    public void verifyUpgradeMessageInRecommendationsInRepVisits(){

        navBar.goToRepVisits();
        getRecommendationsBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
    }

    public void verifyCalendarViewOnRepVisits() {
        navBar.goToRepVisits();
        link("Calendar").click();

        //Verify Small Calendar
        Assert.assertTrue("Small Calendar is not displayed",driver.findElement(By.cssSelector("div[role='application']")).isDisplayed());
        Assert.assertTrue("small calendar next button is not displayed",driver.findElement(By.cssSelector("button[title='right']>i")).isDisplayed());
        Assert.assertTrue("small calendar previous button is not displayed",driver.findElement(By.cssSelector("button[title='left']>i")).isDisplayed());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement mainCalendarNextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Forwards']>i")));

        //Verify Main Calendar
        Assert.assertTrue("Main Calendar is not displayed",driver.findElement(By.cssSelector("div[class='rbc-calendar rep-visits-rbc-calendar ']")).isDisplayed());
        Assert.assertTrue("Main calendar next button is not displayed",driver.findElement(By.cssSelector("button[title='Forwards']>i")).isDisplayed());
        Assert.assertTrue("Main calendar previous button is not displayed",driver.findElement(By.cssSelector("button[title='Backwards']>i")).isDisplayed());

        //verify day view
        Assert.assertTrue(" Day view button is not displayed",button("Day").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Day']")).click();
        Assert.assertTrue("Day view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify week view
        Assert.assertTrue(" Week view button is not displayed",button("Week").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Week']")).click();
        Assert.assertTrue("Week view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify month view
        Assert.assertTrue(" Month view button is not displayed",button("Month").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Month']")).click();
        Assert.assertTrue("Month view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-month-view']")).isDisplayed());

        // Appointments are clickable
        driver.findElement(By.cssSelector("div[class='_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa']")).click();
        waitForUITransition();
        Assert.assertTrue("The Wizard contains the appointment details are not displayed",driver.findElement(By.cssSelector("div[class='ui overlay right very wide visible sidebar _1bTs4IjZQSsADQ671qHLL3']")).isDisplayed());
        driver.findElement(By.xpath("//button[@aria-label='Close']")).click();

        //verify Appointment Keys
        //Visits
        Assert.assertTrue(" visit confirmed option is not displayed",text("Visits - Confirmed").isDisplayed());
        Assert.assertTrue("Visit confirmed Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='visit' and @type='checkbox']/following::label")).isDisplayed());
        String visitColor = "rgba(0, 0, 0, 0.87)";
        String actualVisitColor = driver.findElement(By.xpath("//input[@id='visit']")).getCssValue("color");
        Assert.assertTrue("Background Color for the Visit checkbox are not displayed",actualVisitColor.equals(visitColor));
        driver.findElement(By.xpath("//input[@id='visit']/following::label")).click();

        //Fairs
        Assert.assertTrue(" Fair confirmed option is not displayed",text("College Fair - Confirmed").isDisplayed());
        Assert.assertTrue("Fair confirmed Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='fair'and @type='checkbox']/following::label")).isDisplayed());
        String fairColor = "rgba(0, 0, 0, 0.87)";
        String actualFairColor = driver.findElement(By.xpath("//input[@id='fair']")).getCssValue("color");
        Assert.assertTrue("Background Color for the fair checkbox are not displayed",actualFairColor.equals(fairColor));
        driver.findElement(By.xpath("//input[@id='fair']/following::label")).click();

        //Pending
        Assert.assertTrue("Pending option is not displayed",text("Pending").isDisplayed());
        Assert.assertTrue("Pending Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='pending'and @type='checkbox']/following::label")).isDisplayed());
        String pendingColor = "rgba(0, 0, 0, 0.87)";
        String actualPendingColor = driver.findElement(By.xpath("//input[@id='pending']")).getCssValue("color");
        driver.findElement(By.xpath("//input[@id='pending']/following::label")).click();
        Assert.assertTrue("Background Color for the Pending checkbox are not displayed",actualPendingColor.equals(pendingColor));

    }

    public void navigateToRepVisitsSection(String pageName) {
        navBar.goToRepVisits();
        if (pageName.equalsIgnoreCase("visit feedback")) {
            getVisitsFeedbackBtn().click();
        } else {
            link(pageName).click();
        }
        waitUntilPageFinishLoading();
    }

    public void verifyVisitsFeedbackNonAdminMessaging() {
        Assert.assertTrue("Non-administrator message was not displayed",text("Visit Feedback is only available to users with the Administrator role.").isDisplayed());
        Assert.assertTrue("Locked banner was not displayed",driver.findElement(By.xpath("//div[@class='centered one column row']")).findElement(By.cssSelector("[alt=locked]")).isDisplayed());
    }

    public void verifyVisitsFeedbackFreemiumMessaging() {
        Assert.assertTrue("Feature description was not displayed",text(" you get access to information on the effectiveness of your college visits. See what's working and what could be improved.").isDisplayed());
        Assert.assertTrue("Upgrade button was not displayed",driver.findElement(By.xpath("//button/span")).getText().equalsIgnoreCase("upgrade"));
        Assert.assertTrue("Locked banner was not displayed",driver.findElement(By.xpath("//div[@class='centered one column row']")).findElement(By.cssSelector("[alt=locked]")).isDisplayed());
    }

    public void verifyVisitFeedbackPage(){
        verifyVisitFeedbackHeading();
        verifyStaffMembersAreDisplayedInAscendingOrderByLastName();
        verifyCommunityAvatarIsDisplayedToTheLeftOfStaffMemberName();
        verifyNoFeedbackSubmittedYetMessageIsDisplayed();
    }

    private void verifyVisitFeedbackHeading() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("Visit Feedback heading not displayed", driver.findElement(By.xpath("//h1[contains(@class, 'ui header _26ekcAlhCmjadW7ShhS7aj')]")).getText().equals("Visit Feedback"));
    }

    private void verifyStaffMembersAreDisplayedInAscendingOrderByLastName() {
        waitUntilPageFinishLoading();

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));

        ArrayList<String> listContainingFullNamesOfStaffMembers = new ArrayList<String>();

        for(int i=0; i < itemsInStaffMemberMenu.size(); i++)
        {
            String fullNameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            listContainingFullNamesOfStaffMembers.add(fullNameOfStaffMember);
        }

        List<String> listContainingStaffMembersSortedByLastName = sortByLastName(listContainingFullNamesOfStaffMembers);
        Assert.assertTrue("Staff members are NOT displayed in ascending order by last name", listContainingFullNamesOfStaffMembers.equals(listContainingStaffMembersSortedByLastName));
    }

    private void verifyCommunityAvatarIsDisplayedToTheLeftOfStaffMemberName() {
        waitUntilPageFinishLoading();
        boolean isCommunityAvatarDisplayed = false;

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));

        for( int i=0; i<itemsInStaffMemberMenu.size(); i++)
        {
            try {
                isCommunityAvatarDisplayed = itemsInStaffMemberMenu.get(i).
                        findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]/img | .//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]/i")).isDisplayed();
            } catch(Exception ex) {
                isCommunityAvatarDisplayed = false;
            }

            String nameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            Assert.assertTrue("Community avatar for staff member - " + nameOfStaffMember + " - is not displayed", isCommunityAvatarDisplayed);
        }
    }

    private void verifyNoFeedbackSubmittedYetMessageIsDisplayed() {
        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed()) {
            logger.info("'Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.' message is displayed");
        }
        else {
            Assert.fail("'Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.' message is not displayed!");
        }
    }

    private ArrayList sortByLastName(ArrayList<String> al) {
        Collections.sort(al, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] split1 = o1.split(" ");
                String[] split2 = o2.split(" ");
                String lastName1 = split1[1];
                String lastName2 = split2[1];
                if (lastName1.compareTo(lastName2) > 0) {
                    return 1;
                } else {

                    return -1;
                }
            }
        });

        return al;
    }


    public void searchSchool(String school){
        navBar.goToRepVisits();
        searchTextBox().sendKeys(school);
        waitUntilElementExists(search());
        searchButton().click();
        waitUntilElementExists(schoolInSearchAndSchedule(school));
        Assert.assertTrue("school is not displayed",schoolInSearchAndSchedule(school).isDisplayed());
        schoolInSearchAndSchedule(school).click();
    }

    public void visitsSchedule(String school,String startDate,String time){
        visit().click();
        waitUntilElementExists(schoolInVisits(school));
        Assert.assertTrue("school is not displayed",schoolInVisits(school).isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        Assert.assertTrue("Availability is not displayed",availabilityButton(visitDate,visitTime).isDisplayed());
        availabilityButton(visitDate,visitTime).click();
    }

    public String getSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getMonthandDate(String addDays)
    {
        String DATE_FORMAT_NOW = "MMM d";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifySchedulePopup(String school,String startTime,String endTime){
        String visitTime=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("SchedulePopup is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]")).isDisplayed());
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]")).isDisplayed());
        Assert.assertTrue("time is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+visitTime+"-"+endTime+"')]")).isDisplayed());
        visitRequestButton().click();
        waitUntilElementExists(goToDate());
        navBar.goToRepVisits();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void visitFairsToRegister(String fairName,String schoolName){
        String Fair=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
        waitUntilElementExists(fairs());
        fairs().click();
        waitUntilElementExists(register());
        waitUntilElementExists(fairName(schoolName,Fair));
        Assert.assertTrue("fair is not displayed",fairName(schoolName,Fair).isDisplayed());
        Assert.assertTrue("schoolName is not displayed",schoolName(schoolName).isDisplayed());
        registerButton(Fair) .click();
        Assert.assertTrue("submit page is not displayed",text("Yes, Submit Request").isDisplayed());
        submitButton().click();
        navBar.goToRepVisits();
    }

    public void verifyNotification(String school,String date,String time)
    {
        navBar.goToRepVisits();
        link("Notifications").click();
        waitUntilElementExists(requestsubtab());
        Assert.assertTrue("Requests is not displayed",requestsubtab().isDisplayed());
        requestsubtab().click();
        Assert.assertTrue("school is not displayed",schoolInRequest(school).isDisplayed());
        String Date = selectdate(date);
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("date and time is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+visitTime+"']")).isDisplayed());
    }

    public void verifyNotificationforFairs(String school,String date,String time)
    {
        navBar.goToRepVisits();
        link("Notifications").click();
        waitUntilElementExists(requestsubtab());
        Assert.assertTrue("Requests is not displayed",requestsubtab().isDisplayed());
        requestsubtab().click();
        Assert.assertTrue("school is not displayed",schoolInRequest(school).isDisplayed());
        String Date = selectdate(date);
        Assert.assertTrue("date and time is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+time+"']")).isDisplayed());
    }

    public  void verify25Entries(String option)
    {
        int count=driver.findElements(By.xpath("//div[@class='_12QfCShNjFFA8a-x4K3-yn']/div/div")).size();
        try{   if(count>25)
        {
            logger.info("ShowMore option is displayed");
        }else
        {
            logger.info("Less than 25 notifictions are displayed");
        }}catch (Exception e){}
    }

    public void selectViewDetails(String school,String date,String time)
    {
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String Date = selectdate(date);
        Assert.assertTrue("View full details option is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+visitTime+"']/following-sibling::div/a/span[text()='View full details']")).isDisplayed());
        driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+visitTime+"']/following-sibling::div/a/span[text()='View full details']")).click();
        waitUntilElementExists(textBoxInViewDetails());
        textBoxInViewDetails().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("save button is not displayed",saveButton().isDisplayed());
        saveButton().click();
    }

    public void selectViewDetailsforFairs(String school,String date,String time)
    {
        String Date = selectdate(date);
        Assert.assertTrue("View full details option is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+time+"']/following-sibling::div/a/span[text()='View full details']")).isDisplayed());
        driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+time+"']/following-sibling::div/a/span[text()='View full details']")).click();
        waitUntilElementExists(textBoxInViewDetails());
        textBoxInViewDetails().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("save button is not displayed",saveButton().isDisplayed());
        saveButton().click();
    }

    public void verifynoNotificationMessage(String message)
    {try{
        if(text(message).isDisplayed())
        {logger.info("Notification is not displayed");}
        else{logger.info("Notification is displayed");}}
    catch(Exception e){}
    }

    public String selectdate(String addDays)
    {
        String DATE_FORMAT_NOW = "EEEE, MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void setDate(String inputDate, String startOrEndDate){

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        if (startOrEndDate.contains("Start")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("end")){
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
            findMonth(calendarHeading, startOrEndDate);
        }else{button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).click();
            findMonth(calendarHeading);}

        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month, String startOrEndDate) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month, startOrEndDate);

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {
                if (monthStatus){
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
                else {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
            }
        }
        catch (Exception e) {
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }

    public Boolean compareDate(String month, String startOrEndDate)  {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        if (startOrEndDate.contains("Start")) {
            dateCaption = driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).getText();
        } else if(startOrEndDate.contains("end")){
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        }else{button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).click();}


        //Logic to compare dates before? or not
        Date first = null;
        try {
            first = format.parse(dateCaption);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date second = null;
        try {
            second = formatDate.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean before = (first.before(second));
        return  before;

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
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }

    public void clickOnDay(String date) {
        try{
            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+"]")).click();}
        catch (Exception e){logger.info("Invalid date");}
    }



    //The below method will verify the message which will display when there is no visit/fair for the next week.
    public void verifyDefaultMessageOverviewPage(){
        if(text("You don't have any visits or fairs for the next week.").isDisplayed()){
            Assert.assertTrue("You don't have any visits or fairs for the next week. text message is not displaying.", text("You don't have any visits or fairs for the next week.").isDisplayed());
            Assert.assertTrue("You can always make appointments by using the text is not displaying.", text("You can always make appointments by using the").isDisplayed());
            Assert.assertTrue("Search and Schedule link is not displaying.", link("Search and Schedule").isDisplayed());
            link("Search and Schedule").click();
            waitUntilPageFinishLoading();
            String actURL = driver.getCurrentUrl();
            Assert.assertTrue("Search and Schedule page is not displaying.", actURL.contains("/search"));
        }else if (text("Your Upcoming Visits & Fairs").isDisplayed())
            logger.info("The HE User has upcoming visits/fair for the coming 7 days, so can't show the default text in Overview page...");
    }

    private WebElement upgradeButton(){
        WebElement button=button("UPGRADE");
        return button;
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
    private WebElement getRecommendationsBtn() {return link("Recommendations");}
    private WebElement getNotificationsBtn() {
        return link("Notifications");
    }
    private WebElement getVisitsFeedbackBtn() {return link("Visit Feedback"); }
    private WebElement getSearchBox() { return textbox("Search by school name or location...");}
    private WebElement getSearchAndScheduleSearchBox(){ return textbox("Search by school name or location..."); }
    private WebElement getSearchButton() { return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));}
    private WebElement getMapButton() { return driver.findElement(By.cssSelector("[class='map outline icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return driver.findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
    private WebElement getCheckRepVisitsAvailabilityButton(){ return driver.findElement(By.className("check-repvisits-link")); }
    private WebElement getRepVisitsAvailabilitySidebar(){ return driver.findElement(By.className("_36B3QS_3-4bR8tfro5jydy")); }
    private WebElement getVerticalStaffMembersMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'ui vertical third _345W6T1ug0RMtbb4Ez3uMz menu')]")));
    }
    private WebElement searchTextBox()
    {
        WebElement textBox= driver.findElement(By.cssSelector("input[placeholder='Search by school name or location...']"));
        return textBox;
    }

    private WebElement searchButton()
    {
        WebElement button=driver.findElement(By.cssSelector("button[class='ui button']"));
        return  button;
    }

    private WebElement visit()
    {
        WebElement visit=driver.findElement(By.xpath("//span[text()='Visits']"));
        return  visit;
    }

    private WebElement visitRequestButton()
    {
        WebElement button=driver.findElement(By.xpath("//button[contains(text(),'Yes, Request this time')]"));
        return button;
    }

    private WebElement schoolInSearchAndSchedule(String school)
    {
        WebElement schoolName=driver.findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));
        return schoolName;
    }

    private WebElement schoolInVisits(String school)
    {
        WebElement schoolName=driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        return  schoolName;
    }

    private WebElement availabilityButton(String date,String time)
    {
        WebElement button= driver.findElement(By.xpath("//span[text()='"+date+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']"));
        return button;
    }
    private WebElement fairs()
    {
        WebElement fair=button("Fairs");
        return  fair;
    }
    private WebElement fairName(String  school,String fairName)
    {
        WebElement fairname=driver.findElement(By.xpath("//a/h3[text()='"+school+"']/parent::a/following-sibling::span[text()='"+fairName+"']"));
        return  fairname;
    }
    private WebElement schoolName(String schoolName)
    {
        WebElement schoolDetails = driver.findElement(By.xpath("//a/h3[text()='"+schoolName+"']"));
        return  schoolDetails;
    }
    private WebElement registerButton(String fair)
    {
        WebElement button=  driver.findElement(By.xpath("//span[text()='"+fair+"']/parent::div/following-sibling::div/button/span[text()='Register']"));
        return  button;
    }
    private WebElement submitButton()
    {
        WebElement button=button("Yes, Submit Request");
        return  button;
    }

    private WebElement register()
    {
        WebElement registerButton=button("Register");
        waitUntilElementExists(registerButton);
        return  registerButton;
    }

    private WebElement search(){
        WebElement search=driver.findElement(By.xpath("//button[@class='ui button']"));
        waitUntilElementExists(search);
        return  search;
    }
    private WebElement goToDate()
    {
        WebElement goToDate=driver.findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return  goToDate;
    }
    private WebElement requestsubtab()
    {
        WebElement request=link("Requests");
        return  request;
    }
    private WebElement schoolInRequest(String schoolName)
    {
        WebElement school=driver.findElement(By.xpath("//b[text()='"+schoolName+"']"));
        return school;
    }
    private WebElement saveButton()
    {
        WebElement button=button("Save");
        return button;
    }
    private WebElement textBoxInViewDetails()
    {
        WebElement text= driver.findElement(By.xpath("//input[@aria-label='Internal Notes']"));
        return text;
    }

}


