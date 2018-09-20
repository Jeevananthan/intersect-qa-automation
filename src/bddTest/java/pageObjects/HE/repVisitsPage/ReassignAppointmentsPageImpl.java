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
        link("Calendar").click();
        waitUntil(ExpectedConditions.visibilityOf(link("Re-assign appointments")));
        link("Re-assign appointments").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("" +
                "//div[@class='_1gOm2VKVcyiA-N5sbcuEvj header']/span[text()='Re-assign Appointments']")));
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
        link("Calendar").click();
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
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + alertMessage + "']")));
        Assert.assertTrue("Alert message is not displayed", driver.findElement(By.xpath("//span[text()='" + alertMessage + "']")).isDisplayed());
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
        List<WebElement> user = driver.findElements(By.xpath("//div[text()='"+currentUser+"']"));
        Assert.assertTrue("User is not displayed",user.size()>0);
        Assert.assertTrue("Users are not displayed",userList.size()>0);
        WebElement userInSelectStaffMember = driver.findElement(By.xpath("//div/div/div[text()='Select staff member']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+selectUser+"']"));
        jsClick(userInSelectStaffMember);
        jsClick(newAssigneeButton());
        waitUntilPageFinishLoading();
        user = driver.findElements(By.xpath("//div[text()='"+currentUser+"']"));
        userList = getUsers();
        Assert.assertTrue("User is not displayed",user.size()>0);
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
        WebElement excludedUser = driver.findElement(By.xpath("//div[text()= '" + user + "']"));
        Assert.assertTrue("Selected user is displayed",!excludedUser.isDisplayed());
        buttonGoBack().click();
        waitUntilPageFinishLoading();
    }

    public void selectStaffMember(String staffMember){
        jsClick(selectStaffMemberDropdown());
        WebElement userInSelectStaffMember = driver.findElement(By.xpath("//div/div/div[text()='Select staff member']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+staffMember+"']"));
        jsClick(userInSelectStaffMember);
    }

    public void selectNewAssignee(String newAssignee){
        jsClick(newAssigneeButton());
        WebElement userInSelectNewAssignee = driver.findElement(By.xpath("//div/div/div[text()='Select new assignee']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+newAssignee+"']"));
        jsClick(userInSelectNewAssignee);
    }

    public void selectUserFromUserListDropdown(String user,String dropdown){
        if(dropdown.equals("Select staff member")){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Select staff member']")));
            selectStaffMemberButton().click();
            driver.findElement(By.xpath("//div/div/div[text()='Select staff member']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+user+"']")).click();
            waitUntilPageFinishLoading();
        }else if (dropdown.equals("Select new assignee")){
            jsClick(newAssigneeButton());
            driver.findElement(By.xpath("//div/div/div[text()='Select new assignee']/following-sibling::div[@class='menu transition visible']/div/div[text()='"+user+"']")).click();
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
        WebElement appointmentCheckbox = driver.findElement(By.xpath("//div/span[text()='"+fairsDate+"']/parent::div/following-sibling::" +
                "div/span[text()='College Fair']/ancestor::div/following-sibling::div[@class='twelve wide column']" +
                "/div/div//div[text()='"+school+"']/ancestor::div/following-sibling::div/div/span[text()='Number of Expected Students']" +
                "/following-sibling::div[text()='"+noOfStudents+"']/ancestor::div/div/div/input[@type='checkbox']"));
        appointmentCheckbox.click();
    }

    public void clickReAssignAppointmentsButton(String appointmentsCount){
        int count = Integer.parseInt(appointmentsCount);
        if(count>0){
            driver.findElement(By.xpath("//button[text()='Reassign "+count+" Appointments']")).click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='content']>span")));
            waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='content']>span")));
        }else {
            reAssignAppointmentsButton().click();
        }
    }

    public void verifyErrorMessageInReAssignAppointments(String errorMessage,String option,String staff){
        navigationBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
        reAssignAppointments().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='Reassign  Appointments']"),1));
        switch (option){
            case "Select staff member":
                reAssignAppointmentsButton().click();
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/span[text()='"+errorMessage+"']"),1));
                Assert.assertTrue("Error message is not displayed",driver.findElement(By.xpath("//div/span[text()='"+errorMessage+"']")).isDisplayed());
                break;
            case "Select new assignee":
                selectStaffMemberDropdown().click();
                jsClick(driver.findElement(By.xpath("//div[text()='"+staff+"']")));
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//label[@for='selectAllCheckBox']"),1));
                reAssignAppointmentsButton().click();
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/span[text()='"+errorMessage+"']"),1));
                Assert.assertTrue("Error message is not displayed",driver.findElement(By.xpath("//div/span[text()='"+errorMessage+"']")).isDisplayed());
                break;
            case "No appointments":
                selectStaffMemberDropdown().click();
                jsClick(driver.findElement(By.xpath("//div[text()='"+staff+"']")));
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//label[@for='selectAllCheckBox']"),1));
                selectNewAssigneeDropdown().click();
                jsClick(driver.findElement(By.xpath("//div[text()='Select new assignee']/parent::div//div[text()='HE, Purple']")));
                reAssignAppointmentsButton().click();
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/span[text()='"+errorMessage+"']"),1));
                Assert.assertTrue("Error message is not displayed",driver.findElement(By.xpath("//div/span[text()='"+errorMessage+"']")).isDisplayed());
                break;
            case "Select staff member, no associated visits or fairs":
                selectStaffMemberDropdown().click();
                jsClick(driver.findElement(By.xpath("//div[text()='"+staff+"']")));
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//p[@class='_118YtPAz_wuAU_t1i9SSRo']/span"),1));
                String actualMessage = driver.findElement(By.xpath("//p[@class='_118YtPAz_wuAU_t1i9SSRo']/span")).getText();
                Assert.assertTrue("Error message is not displayed",actualMessage.equals(errorMessage));
                break;
            default:
                Assert.fail("Invalid option");
                break;
        }
        buttonGoBack().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum _3GCGVUzheyMFBFnbzJUu6J']/span[text()='Calendar']"),1));
    }

    public void verifyDisappearingErrorMessageInReAssignAppointments(String disappearingErrorMessage,String errorMessage,String staff){
        navigationBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
        reAssignAppointments().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='Reassign  Appointments']"),1),5);
        reAssignAppointmentsButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/span[text()='"+disappearingErrorMessage+"']"),1),5);
        Assert.assertTrue("Error message is not displayed",driver.findElement(By.xpath("//div/span[text()='"+disappearingErrorMessage+"']")).isDisplayed());
        selectStaffMemberDropdown().click();
        driver.findElement(By.xpath("//div[text()='"+staff+"']")).click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//p[@class='_118YtPAz_wuAU_t1i9SSRo']/span"),1),5);
        String actualMessage = driver.findElement(By.xpath("//p[@class='_118YtPAz_wuAU_t1i9SSRo']/span")).getText();
        Assert.assertTrue("Error message is not displayed",actualMessage.equals(errorMessage));
        buttonGoBack().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum _3GCGVUzheyMFBFnbzJUu6J']/span[text()='Calendar']"),1),5);
    }

    //locators
    private WebElement staffForReassign(){
    waitUntilPageFinishLoading();
    return  driver.findElement(By.cssSelector("div[role='alert']"));
    }
    /**
     * Get the re assign link
     * @return
     */
    private List<WebElement> getReAssignLink(){
        return driver.findElements(By.xpath("//span[text()='Re-assign appointments']"));
    }
    private WebElement newAssigneeButton(){
        return driver.findElement(By.xpath("//div[text()='Select new assignee']"));
    }
    private WebElement disabledNewAssigneeDropdown(){
        return driver.findElement(By.xpath("//div[@class='ui disabled selection dropdown staffSelect _1fyAdfnHhLDFoE1OCXnbCC' and @aria-disabled='true']"));
    }
    public List<WebElement> getUsers() {
        return driver.findElements(By.xpath("//div[@class='menu transition visible']/div"));
    }
    private WebElement selectStaffMemberButton(){
        return driver.findElement(By.xpath("//div[text()='Select staff member']"));
    }
    private WebElement showMoreButtonInReassignAppointments(){
        return button("Show More");
    }
    private WebElement reAssignAppointments(){
        return link("Re-assign appointments");
    }
    private WebElement reAssignAppointmentsButton(){
        return driver.findElement(By.xpath("//button[text()='Reassign  Appointments']"));
    }
    private WebElement selectStaffMemberDropdown(){
        return driver.findElement(By.xpath("//div[text()='Select staff member']"));
    }

    /**
     * Gets the select new asignee dropdown
     * @return
     */
    private WebElement selectNewAssigneeDropdown(){
        return driver.findElement(By.xpath("//div[text()='Select new assignee']"));
    }
    private WebElement getCalendarBtn() {
        return link("Calendar");
    }
    private WebElement buttonShowMore(){
        return button("SHOW MORE");
    }
    private WebElement buttonGoBack(){
        return  button("GO BACK");
    }
    private WebElement agendaIsDisplayed(){
        return driver.findElement(By.cssSelector("div[class='_2gJHeLgeouIqly4xt-Bv2C']"));
    }
}
