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
    /**
     * Verifies the status of the re-assign link, it could be visible or not visible
     * @param status
     */
    public void verifyReAssignLinkIsVisible(){
        navigationBar.goToRepVisits();
        getCalendarBtn().click();
        Assert.assertTrue("The Re-assign link is not displayed",getReAssignLink().size()==1);
    }

    public void verifyReAssignLinkIsNotVisible() {
        navigationBar.goToRepVisits();
        getCalendarBtn().click();
        Assert.assertTrue("The Re-assign link is displayed",getReAssignLink().size()==0);
    }

    public void verifyBlueNoteAlertIsDisplayed(String alertMessage,String staffMember,String newAssignee) {
        goToReassignAppointment();
        selectStaffMember(staffMember);
        selectNewAssignee(newAssignee);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(alert()));
        String actualMessage = alertMessage().getText();
        Assert.assertTrue("Alert message is not displayed", actualMessage.equals(alertMessage));
        buttonGoBack().click();
        waitUntilPageFinishLoading();
    }

    public void verifyUsersInSelectStaffMemberDropdown(String currentUser){
        goToReassignAppointment();
        jsClick(selectStaffMemberDropdown());
        waitUntilPageFinishLoading();
        //verify user
        List<WebElement> userList = getUsers();
        Assert.assertTrue("User is not displayed",currentUserList(currentUser).size()>0);
        Assert.assertTrue("Users are not displayed",userList.size()>0);
        jsClick(buttonGoBack());
        waitUntilPageFinishLoading();
    }

    public void verifyUsersInSelectNewAssigneeDropdown(String currentUser,String selectUser){
        goToReassignAppointment();
        jsClick(selectStaffMemberDropdown());
        waitUntilPageFinishLoading();
        //verify Select new assignee dropdown is disabled
        Assert.assertTrue("Select new assignee dropdown is enabled",disabledNewAssigneeDropdown().isDisplayed());
        jsClick(userInSelectStaffMember(selectUser));
        jsClick(newAssigneeButton());
        waitUntilPageFinishLoading();
        //verify user
        List<WebElement> userList = getUsers();
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

    public void verifyUIComponent(String user){
        goToReassignAppointment();
        waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
        staffForReassign().click();
        Assert.assertTrue("User was not displayed!", selectStaff(user).isDisplayed());
        selectStaff(user).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(selectAllCheckBox()));
        Assert.assertTrue("The Agenda was not displayed!", agendaIsDisplayed().isDisplayed());
        Assert.assertTrue("Showing all was not displayed",showingAllText().isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyCheckBox(String user){
        goToReassignAppointment();
        waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
        staffForReassign().click();
        selectStaff(user).click();
        selectAllCheckBoxText().click();
        //Un selecting  action
        selectAllCheckBoxText().click();
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyAppointmentsCount(String user){
        goToReassignAppointment();
        waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
        staffForReassign().click();
        selectStaff(user).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(selectAllCheckBox()));
        String count = getAppointmentsCount();
        selectAllCheckBoxText().click();
        Assert.assertTrue("No changed the number of items in the button",  selectReAssignAppointmentsButton(count).isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyShowMoreButton(String user){
        goToReassignAppointment();
        waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
        staffForReassign().click();
        Assert.assertTrue("User was not displayed!", selectStaff(user).isDisplayed());
        selectStaff(user).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(selectAllCheckBox()));
        String count = getAppointmentsCount();
        int finalCount = Integer.parseInt(count);
        Assert.assertTrue("Select all count was not displayed",selectAllCount(finalCount).isDisplayed());
        //Verify "SHOW MORE" button
        if (finalCount > 25) {
            Assert.assertTrue("Button SHOW MORE was not displayed", buttonShowMore().isDisplayed());
            Assert.assertTrue("Showing was not displayed",showingText() .isDisplayed());
        }
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

    public void verifyUserWithoutAppointments(String user){
        goToReassignAppointment();
        waitUntil(ExpectedConditions.visibilityOf(staffForReassign()));
        staffForReassign().click();
        Assert.assertTrue("User was not displayed!", selectStaff(user).isDisplayed());
        selectStaff(user).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(noAppointmentMessage()));
        Assert.assertTrue("No message was displayed for the appointment", noAppointment().isDisplayed());
        buttonGoBack().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarText()));
    }

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
    public WebElement selectAllCount(int count){return driver.findElement(By.xpath("//label[contains(text(), 'Select all (" + count +")')]"));}
    private String getAppointmentsCount(){
        String items= selectAllCheckBoxText().getText();
        String[] parts = items.split(" ");
        String count = parts[2];
        String countNumber = count.replaceAll("[^a-zA-Z0-9\\\\s+]", "");
        return countNumber;
    }
    //locators
    private WebElement staffForReassign(){ return  driver.findElement(By.cssSelector("div[role='alert']")); }
    private List<WebElement> getReAssignLink(){ return driver.findElements(By.xpath("//span[text()='Re-assign appointments']")); }
    private WebElement newAssigneeButton(){ return driver.findElement(By.xpath("//div[text()='Select new assignee']")); }
    private WebElement disabledNewAssigneeDropdown(){ return driver.findElement(By.cssSelector("div[class='ui disabled selection dropdown staffSelect _1fyAdfnHhLDFoE1OCXnbCC'][aria-disabled='true']")); }
    public List<WebElement> getUsers() { return driver.findElements(By.cssSelector("div[class='menu transition visible']>div")); }
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
    private By noAppointmentMessage(){return By.cssSelector("p[class='_118YtPAz_wuAU_t1i9SSRo']>span");}
    private WebElement noAppointment(){return driver.findElement(By.cssSelector("p[class='_118YtPAz_wuAU_t1i9SSRo']>span"));}
    private By calendarText(){return By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum _3GCGVUzheyMFBFnbzJUu6J']/span[text()='Calendar']");}
    private By selectAllCheckBox(){return By.cssSelector("label[for='selectAllCheckBox']");}
    private WebElement selectAllCheckBoxText(){ return driver.findElement(By.xpath("//label[contains(text(), 'Select all')]"));}
    private WebElement showingAllText(){return driver.findElement(By.xpath("//p[contains(text(), 'Showing all of')]"));}
    private WebElement showingText(){return driver.findElement(By.xpath("//p[contains(text(), 'Showing')]"));}
    private WebElement alertMessage(){return driver.findElement(By.cssSelector("strong+span>span"));}
    private By alert(){return By.cssSelector("strong+span>span");}
    }
