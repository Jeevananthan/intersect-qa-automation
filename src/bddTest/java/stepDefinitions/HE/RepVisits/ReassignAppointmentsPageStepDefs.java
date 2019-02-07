package stepDefinitions.HE.RepVisits;

import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.ReassignAppointmentsPageImpl;

public class ReassignAppointmentsPageStepDefs implements En {

    public ReassignAppointmentsPageStepDefs() {

        ReassignAppointmentsPageImpl reassignAppointmentsPage = new ReassignAppointmentsPageImpl();

        When("^HE I go to re assign appointments$", reassignAppointmentsPage::goToReassignAppointment);

        Then("^HE I verify that Re-assign link is visible$", reassignAppointmentsPage::verifyReAssignLinkIsVisible);

        Then("^HE I verify that Re-assign link is not visible", reassignAppointmentsPage::verifyReAssignLinkIsNotVisible);

        And("^HE I verify the users are displaying including \"([^\"]*)\" in Select staff member dropdown$", reassignAppointmentsPage::verifyUsersInSelectStaffMemberDropdown);

        And("^HE I verify the users are displaying including \"([^\"]*)\" in Select new assignee dropdown using \"([^\"]*)\"$", reassignAppointmentsPage::verifyUsersInSelectNewAssigneeDropdown);

        And("^HE I verify the users are displaying including \"([^\"]*)\" and in active user \"([^\"]*)\" in Select new assignee dropdown$", reassignAppointmentsPage::verifyUsersInSelectNewAssigneeDropdown);

        Then("^HE I verify the user \"([^\"]*)\" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown$", reassignAppointmentsPage::verifyUserIsExcludedInSelectNewAssignee);

        Then("^HE I verify the blue Note alert \"([^\"]*)\" is displaying when changing the Select staff member dropdown for the users \"([^\"]*)\",\"([^\"]*)\" (?:with no appointments|with appointments) in Select new assignee dropdown$", reassignAppointmentsPage::verifyBlueNoteAlertIsDisplayed);

        Then("^HE I select the user \"([^\"]*)\" from 'Select staff member' dropdown$", reassignAppointmentsPage::selectUserFromSelectStaffMemberDropdown);

        Then("^HE I select the user \"([^\"]*)\" from 'Select new assignee' dropdown$", reassignAppointmentsPage::selectUserFromSelectNewAssigneeDropdown);

        Then("^HE I select the fair to reassign using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$", reassignAppointmentsPage::selectFairsToReAssign);

        Then("^HE I click Reassign Appointments button \"([^\"]*)\"$", reassignAppointmentsPage::clickReAssignAppointmentsButton);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when Select staff member is not selected$", reassignAppointmentsPage::verifyErrorMessageInSelectStaffMember);

        Then("^HE I verify the error Message \"([^\"]*)\" is disappearing when the error message \"([^\"]*)\" is displayed for \"([^\"]*)\"$", reassignAppointmentsPage::verifyDisappearingErrorMessageInReAssignAppointments);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when Select new assignee is not selected using \"([^\"]*)\"$", reassignAppointmentsPage::verifyErrorMessageInSelectNewAssignee);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when appointments is not selected using \"([^\"]*)\",\"([^\"]*)\"$", reassignAppointmentsPage::verifyErrrorMessageForNoAppointmentsSelected);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when the user \"([^\"]*)\" is selected$", reassignAppointmentsPage::verifyErrrorMessageForNoAppointmentsUser);

        Then("^HE I verify show more button displaying when 26 or more appointments are returned for the user \"([^\"]*)\" in reassignAppointments Page$", reassignAppointmentsPage::verifyShowMoreButton);

        Then("^HE I verify the user \"([^\"]*)\" is displaying in select staff member dropdown$",reassignAppointmentsPage::verifyUserInSelectStaffMemberDropdown);

        Then("^HE I verify the appointments count in reAssignAppointments page for the user \"([^\"]*)\"$", reassignAppointmentsPage::verifyAppointmentsCount);

        Then("^HE I verify the user \"([^\"]*)\" is displaying in Select staff member dropdown$", reassignAppointmentsPage::verifyUserInSelectStaffMemberDropdown);

        Then("^HE I verify the text 'Showing all' is displaying in reassignAppointments Page for the user \"([^\"]*)\"$", reassignAppointmentsPage::verifyShowingAllText);

        Then("^HE I verify the appointments displaying in agenda view$", reassignAppointmentsPage::verifyAppointmentsAreInAgendaView);

        Then("^HE I click Go Back button$", reassignAppointmentsPage::clickGoBackButton);

        Then("^HE I verify the user \"([^\"]*)\" is displaying in Select new assignee dropdown$", reassignAppointmentsPage::verifyUserInSelectNewAssigneeDropdown);

        Then("^HE I verify no appointment text is displaying for the user \"([^\"]*)\"$", reassignAppointmentsPage::verifyUserWithNoAppointments);

        Then("^HE I verify the users are listed in A-Z order in 'select staff member' dropdown$", reassignAppointmentsPage::verifyUsersSortingOrderInSelectStaffMemberDropdown);

        Then("^HE I verify the users are listed in A-Z order in 'Select new assignee' dropdown using \"([^\"]*)\"$", reassignAppointmentsPage::verifyUsersSortingOrderInSelectNewAssigneeDropdown);

        Then("^HE I verify the text 'Re-assign Appointments' is displaying in re assign appointments page$", reassignAppointmentsPage::verifyReAssignAppointmentsText);

        Then("^HE I verify the text 'Select appointments to re-assign:' is displaying in re assign appointments page$", reassignAppointmentsPage::verifySelectAppointmentsToReAssignText);

        Then("^HE I verify the text 'Select a staff member above to see their appointments here' is displaying in re assign appointments page$", reassignAppointmentsPage::SelectStaffMemberToSeeTheirAppointments);

        Then("^HE I verify the dropdown 'Select staff member' is displaying in re assign appointments page$", reassignAppointmentsPage::verifySelectStaffMemberDropdown);

        Then("^HE I verify the dropdown 'Select new assignee' is displaying in re assign appointments page$", reassignAppointmentsPage::verifySelectNewAssigneeDropdown);

        Then("^HE I verify the button 'GO BACK' is displaying in re assign appointments page$",reassignAppointmentsPage::verifyGoBackButton);

        Then("^HE I verify the button 'Reassign  Appointments' is displaying in re assign appointments page$",reassignAppointmentsPage::verifyReAssignAppointmentsButton);

        Then("^HE I select the user \"([^\"]*)\" in select staff member dropdown$",reassignAppointmentsPage::selectUserInSelectStaffMemberDropdown);

        Then("^HE I verify the current user \"([^\"]*)\" is displaying in Select staff member dropdown list$",reassignAppointmentsPage::verifyCurrentUserIsDisplayingInSelectStaffMember);

        Then("^HE I verify the in active user \"([^\"]*)\" is displaying with 'Inactive User' notation in Select staff member dropdown list$",reassignAppointmentsPage::verifyInActiveUserIsDisplayingInSelectStaffMember);

        Then("^HE I verify the current user \"([^\"]*)\" is displaying in Select new assignee dropdown list$",reassignAppointmentsPage::verifyCurrentUserIsDisplayingInSelectNewAssignee);

        Then("^HE I verify the in active user \"([^\"]*)\" is displaying with 'Inactive User' notation in new assignee dropdown list$",reassignAppointmentsPage::verifyInActiveUserIsDisplayingInSelectNewAssignee);

        Then("^HE I verify the in active user \"([^\"]*)\" is not selectable in Select new assignee dropdown$",reassignAppointmentsPage::verifyInActiveUserIsNotSelectable);
   
        Then("^HE I verify Select all check box in reAssignAppointments page using \"([^\"]*)\"$", reassignAppointmentsPage::verifySelectAllCheckBox);
    }
}
