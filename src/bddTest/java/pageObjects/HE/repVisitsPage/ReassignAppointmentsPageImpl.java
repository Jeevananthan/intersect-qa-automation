package pageObjects.HE.repVisitsPage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReassignAppointmentsPageImpl extends RepVisitsPageImpl {

    public void goToReassignAppointment(){
        navigationBar.goToRepVisits();
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.visibilityOf(reAssignAppointments()));
        reAssignAppointments().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(reAssignAppointmentsText()));
    }

    public void reassignAppointmentsVerification(String option){

        if (option.contains("Coordinator, PurpleHE"))
        {
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
            staffForReassign().click();
            //Verify item of the Staff Member
            Assert.assertTrue("Item was not displayed!", driver.findElement(By.xpath("//div[contains(text(), '" + option + "')]")).isDisplayed());
            //Select the item
            driver.findElement(By.xpath("//div[contains(text(), '" + option + "')]")).click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), ' have any appointments scheduled.')]")));
            //Verify item of the Staff Member
            Assert.assertTrue("No message was displayed for the appointment", driver.findElement(By.xpath("//span[contains(text(), ' have any appointments scheduled.')]")).isDisplayed());
            buttonGoBack().click();
        }
        else {

            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
            staffForReassign().click();
            //Verify item of the Staff Member
            Assert.assertTrue("Item was not displayed!", driver.findElement(By.xpath("//div[contains(text(), '" + option + "')]")).isDisplayed());
            //Select the item
            driver.findElement(By.xpath("//div[contains(text(), '" + option + "')]")).click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(), 'Select all')]")));
            //Capturing the counter
            String items= driver.findElement(By.xpath("//label[contains(text(), 'Select all')]")).getText();
            String[] parts = items.split(" ");
            String count = parts[2];
            String countNumber = count.replaceAll("[^a-zA-Z0-9\\\\s+]", "");
            int counter = Integer.parseInt(countNumber);
            //Verify that agenda is displayed below to Select staff label area
            waitUntilElementExists(agendaIsDisplayed());
            Assert.assertTrue("The Agenda was not displayed!", agendaIsDisplayed().isDisplayed());
            //Verify "Showing all of" appointments
            Assert.assertTrue("Showing all was not displayed", driver.findElement(By.xpath("//p[contains(text(), 'Showing all of')]")).isDisplayed());
            //Verify "Select all" count
            Assert.assertTrue("Select all count was not displayed", driver.findElement(By.xpath("//label[contains(text(), 'Select all (" + counter +")')]")).isDisplayed());
            //Verify "SHOW MORE" button
            if (counter > 25) {
                Assert.assertTrue("Button SHOW MORE was not displayed", buttonShowMore().isDisplayed());
                //Verify "Showing" word in the UI
                Assert.assertTrue("Showing was not displayed", driver.findElement(By.xpath("//p[contains(text(), 'Showing')]")).isDisplayed());
            }

            waitUntilPageFinishLoading();
            waitUntilElementExists( staffForReassign());
            staffForReassign().click();
            //Select the item
            driver.findElement(By.xpath("//div[contains(text(), '" + option + "')]")).click();
            //Verify Select all is possible to do
            driver.findElement(By.xpath("//div[@class='Je6ekRe044BthZWPPfS1z']//preceding-sibling::input[@type='checkbox']")).click();
            //Un selecting  action
            driver.findElement(By.xpath("//div[@class='Je6ekRe044BthZWPPfS1z']//preceding-sibling::input[@type='checkbox']")).click();
            //Selecting one item and verifying the counter increased
            driver.findElement(By.xpath("(//input[@type='checkbox'])[last()-1]")).click();
            //Un selecting  action
            Assert.assertTrue("No changed the number of items in the button",  driver.findElement(By.xpath("//button[contains(text(), 'Reassign 1 Appointments')]")).isDisplayed());
        }
    }

    /**
     * Verifies the status of the re-assign link, it could be visible or not visible
     * @param status
     */
    public void verifyReAssignLinkStatus(String status){
        navigationBar.goToRepVisits();
        getCalendarBtn().click();
        switch (status.toLowerCase()){
            case "visible":
                Assert.assertTrue("The Re-assign link is not displayed",getReAssignLink().size()==1);
                break;
            case "not visible":
                Assert.assertTrue("The Re-assign link is displayed",getReAssignLink().size()==0);
                break;
            default:
                Assert.fail("The status of the Re-assign link to be verified is not correct");
                break;
        }
    }

    public void verifyBlueNoteAlert(String alertMessage,String staffMember,String newAssignee) {
        goToReassignAppointment();
        selectStaffMember(staffMember);
        selectNewAssignee(newAssignee);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(alert(alertMessage)));
        Assert.assertTrue("Alert message is not displayed", alertMessage(alertMessage).isDisplayed());
        buttonGoBack().click();
        waitUntilPageFinishLoading();
    }

    public void verifyUsersInReAssignAppointments(String currentUser,String selectUser){
        goToReassignAppointment();
        jsClick(selectStaffMemberDropdown());
        waitUntilPageFinishLoading();
        //verify Select new assignee dropdown is disabled
        Assert.assertTrue("Select new assignee dropdown is enabled",disabledNewAssigneeDropdown().isDisplayed());
        List<WebElement> userList = getUsers();
        Assert.assertTrue("User is not displayed",currentUserList(currentUser).size()>0);
        Assert.assertTrue("Users are not displayed",userList.size()>0);
        jsClick(userInSelectStaffMember(selectUser));
        jsClick(newAssigneeButton());
        waitUntilPageFinishLoading();
        userList = getUsers();
        Assert.assertTrue("User is not displayed",currentUserList(currentUser).size()>0);
        Assert.assertTrue("Users are not displayed",userList.size()>0);
        newAssigneeButton().click();
        waitUntilPageFinishLoading();
        buttonGoBack().click();
        waitUntilPageFinishLoading();
    }

    public void verifyUserIsExcludedInSelectNewAssignee(String user){
        goToReassignAppointment();
        selectStaffMember(user);
        jsClick(newAssigneeButton());
        waitUntilPageFinishLoading();
        Assert.assertTrue("Selected user is displayed",!excludedUser(user).isDisplayed());
        buttonGoBack().click();
        waitUntilPageFinishLoading();
    }

    public void selectStaffMember(String staffMember){
        jsClick(selectStaffMemberDropdown());
        jsClick(userInSelectStaffMember(staffMember));
    }

    public void selectNewAssignee(String newAssignee){
        jsClick(newAssigneeButton());
        jsClick(userInNewAssignee(newAssignee));
    }

    public void selectUserFromUserListDropdown(String user,String dropdown){
        if(dropdown.equals("Select staff member")){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(selectStaffMemberText()));
            selectStaffMemberButton().click();
            userInSelectStaffMember(user).click();
            waitUntilPageFinishLoading();
        }else if (dropdown.equals("Select new assignee")){
            jsClick(newAssigneeButton());
            userInNewAssignee(user).click();
            waitUntilPageFinishLoading();
        }else {
            Assert.fail("Invalid option");
        }
    }

    public void selectFairsToReAssign(String date,String school,String noOfStudents){
        while(showMoreButtonInReassignAppointments().isDisplayed()){
            showMoreButtonInReassignAppointments().click();
            waitUntilPageFinishLoading();
        }
        String fairsDate = getSpecificDateforCalendar(date);
        selectFairsAppointment(fairsDate,school,noOfStudents).click();
    }

    public void clickReAssignAppointmentsButton(String appointmentsCount){
        int count = Integer.parseInt(appointmentsCount);
        if(count>0){
            selectReAssignAppointmentsButton(appointmentsCount).click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
            waitUntil(ExpectedConditions.invisibilityOfElementLocated(successMessage()));
        }else {
            reAssignAppointmentsButton().click();
        }
    }

    public void verifyErrorMessageInSelectStaffMember(String errorMessage){
        goToReassignAppointment();
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(errorText(errorMessage)));
        Assert.assertTrue("Error message is not displayed",errorMessage(errorMessage).isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyErrorMessageInSelectNewAssignee(String errorMessage,String staffMember){
        goToReassignAppointment();
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(errorText(errorMessage)));
        selectStaffMemberDropdown().click();
        jsClick(selectStaff(staffMember));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(selectAllCheckBox()));
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(errorText(errorMessage)));
        Assert.assertTrue("Error message is not displayed",errorMessage(errorMessage).isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyErrrorMessageForNoAppointmentsSelected(String errorMessage,String staffMember,String newAssignee){
        goToReassignAppointment();
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(errorText(errorMessage)));
        selectStaffMemberDropdown().click();
        jsClick(selectStaff(staffMember));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(selectAllCheckBox()));
        selectNewAssigneeDropdown().click();
        jsClick(selectNewAssigneeStaff(newAssignee));
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(errorText(errorMessage)));
        Assert.assertTrue("Error message is not displayed",errorMessage(errorMessage).isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyErrrorMessageForNoAppointmentsUser(String errorMessage,String staffMember){
        goToReassignAppointment();
        selectStaffMemberDropdown().click();
        jsClick(selectStaff(staffMember));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(errorText(errorMessage)));
        Assert.assertTrue("Error message is not displayed",errorMessage(errorMessage).isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyDisappearingErrorMessageInReAssignAppointments(String disappearingErrorMessage,String errorMessage,String staff){
        goToReassignAppointment();
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(disappearingErrorMessage(disappearingErrorMessage)));
        Assert.assertTrue("Error message is not displayed",verifyDisappearingErrorMessage(disappearingErrorMessage).isDisplayed());
        selectStaffMemberDropdown().click();
        selectStaff(staff).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(noAppointmentMessage()));
        String actualMessage = noAppointment().getText();
        Assert.assertTrue("Error message is not displayed",actualMessage.equals(errorMessage));
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public WebElement alertMessage(String alertMessage){return driver.findElement(By.xpath("//span[text()='" + alertMessage + "']"));}
    public By alert(String alertMessage){return By.xpath("//span[text()='" + alertMessage + "']");}
    public List<WebElement> currentUserList(String currentUser){return driver.findElements(By.xpath("//div[text()='"+currentUser+"']"));}
    public WebElement userInSelectStaffMember(String selectUser){return driver.findElement(By.xpath("//div/div/div[text()='Select staff member']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+selectUser+"']"));}
    public WebElement excludedUser(String user){return driver.findElement(By.xpath("//div[text()= '" + user + "']"));}
    public WebElement userInNewAssignee(String newAssignee){return driver.findElement(By.xpath("//div/div/div[text()='Select new assignee']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+newAssignee+"']"));}
    public WebElement selectFairsAppointment(String fairsDate,String school,String noOfStudents){return driver.findElement(By.xpath("//div/span[text()='"+fairsDate+"']/parent::div/following-sibling::" +
            "div/span[text()='College Fair']/ancestor::div/following-sibling::div[@class='twelve wide column']" +
            "/div/div//div[text()='"+school+"']/ancestor::div/following-sibling::div/div/span[text()='Number of Expected Students']" +
            "/following-sibling::div[text()='"+noOfStudents+"']/ancestor::div/div/div/input[@type='checkbox']"));}
    public WebElement selectReAssignAppointmentsButton(String count){return driver.findElement(By.xpath("//button[text()='Reassign "+count+" Appointments']"));}
    public WebElement selectStaff(String staff){return driver.findElement(By.xpath("//div[text()='"+staff+"']"));}
    public By disappearingErrorMessage(String disappearingErrorMessage){return By.xpath("//div/span[text()='"+disappearingErrorMessage+"']");}
    public WebElement verifyDisappearingErrorMessage(String disappearingErrorMessage){return driver.findElement(By.xpath("//div/span[text()='"+disappearingErrorMessage+"']"));}
    public WebElement errorMessage(String errorMessage){return driver.findElement(By.xpath("//div/span[text()='"+errorMessage+"']"));}
    public By errorText(String errorMessage){return By.xpath("//div/span[text()='"+errorMessage+"']");}
    public WebElement selectNewAssigneeStaff(String newAssignee){return driver.findElement(By.xpath("//div[text()='Select new assignee']/parent::div//div[text()='"+newAssignee+"']"));}

    //locators
    private WebElement staffForReassign(){ return  driver.findElement(By.cssSelector("div[role='alert']")); }
    private List<WebElement> getReAssignLink(){ return driver.findElements(By.xpath("//span[text()='Re-assign appointments']")); }
    private WebElement newAssigneeButton(){ return driver.findElement(By.xpath("//div[text()='Select new assignee']")); }
    private WebElement disabledNewAssigneeDropdown(){ return driver.findElement(By.xpath("//div[@class='ui disabled selection dropdown staffSelect _1fyAdfnHhLDFoE1OCXnbCC' and @aria-disabled='true']")); }
    public List<WebElement> getUsers() { return driver.findElements(By.xpath("//div[@class='menu transition visible']/div")); }
    private WebElement selectStaffMemberButton(){ return driver.findElement(By.xpath("//div[text()='Select staff member']")); }
    private WebElement showMoreButtonInReassignAppointments(){ return button("Show More"); }
    private WebElement reAssignAppointments(){ return link("Re-assign appointments"); }
    private WebElement reAssignAppointmentsButton(){ return driver.findElement(By.xpath("//button[text()='Reassign  Appointments']")); }
    private WebElement selectStaffMemberDropdown(){ return driver.findElement(By.xpath("//div[text()='Select staff member']")); }
    private WebElement selectNewAssigneeDropdown(){ return driver.findElement(By.xpath("//div[text()='Select new assignee']")); }
    private WebElement getCalendarBtn() { return link("Calendar"); }
    private WebElement buttonShowMore(){ return button("SHOW MORE"); }
    private WebElement buttonGoBack(){ return  button("GO BACK"); }
    private WebElement agendaIsDisplayed(){ return driver.findElement(By.cssSelector("div[class='_2gJHeLgeouIqly4xt-Bv2C']")); }
    private By reAssignAppointmentsText(){ return By.xpath("//div/span[text()='Re-assign Appointments']"); }
    private By selectStaffMemberText(){return By.xpath("//div[text()='Select staff member']");}
    private By successMessage(){return By.cssSelector("div[class='content']>span");}
    private By noAppointmentMessage(){return By.xpath("//p[@class='_118YtPAz_wuAU_t1i9SSRo']/span");}
    private WebElement noAppointment(){return driver.findElement(By.xpath("//p[@class='_118YtPAz_wuAU_t1i9SSRo']/span"));}
    private By calendarText(){return By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum _3GCGVUzheyMFBFnbzJUu6J']/span[text()='Calendar']");}
    private By selectAllCheckBox(){return By.xpath("//label[@for='selectAllCheckBox']");}
    }
