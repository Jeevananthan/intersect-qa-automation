package stepDefinitions.HE.RepVisits;

import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.ReassignAppointmentsPageImpl;

public class ReassignAppointmentsPageStepDefs implements En {

    public ReassignAppointmentsPageStepDefs() {

        ReassignAppointmentsPageImpl reassignAppointmentsPage = new ReassignAppointmentsPageImpl();

        When("^HE I go to re assign appointments$", reassignAppointmentsPage::goToReassignAppointment);

        Then("^HE I verify that Re-assign link is visible$",reassignAppointmentsPage::verifyReAssignLinkIsVisible);

        Then("^HE I verify that Re-assign link is not visible",reassignAppointmentsPage::verifyReAssignLinkIsNotVisible);

        And("^HE I verify the users are displaying including \"([^\"]*)\" in Select staff member dropdown$",reassignAppointmentsPage::verifyUsersInSelectStaffMemberDropdown);

        And("^HE I verify the users are displaying including \"([^\"]*)\" in Select new assignee dropdown using \"([^\"]*)\"$",reassignAppointmentsPage::verifyUsersInSelectNewAssigneeDropdown);

        Then("^HE I verify the user \"([^\"]*)\" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown$",reassignAppointmentsPage::verifyUserIsExcludedInSelectNewAssignee);

        Then("^HE I verify the blue Note alert \"([^\"]*)\" is displaying when changing the Select staff member dropdown for the users \"([^\"]*)\",\"([^\"]*)\" (?:with no appointments|with appointments) in Select new assignee dropdown$",reassignAppointmentsPage::verifyBlueNoteAlertIsDisplayed);

        Then("^HE I select the user \"([^\"]*)\" from \"([^\"]*)\" dropdown$",reassignAppointmentsPage::selectUserFromUserListDropdown);

        Then("^HE I select the fair to reassign using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",reassignAppointmentsPage::selectFairsToReAssign);

        Then("^HE I click Reassign Appointments button \"([^\"]*)\"$",reassignAppointmentsPage::clickReAssignAppointmentsButton);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when Select staff member is not selected$",reassignAppointmentsPage::verifyErrorMessageInSelectStaffMember);

        Then("^HE I verify the error Message \"([^\"]*)\" is disappearing when the error message \"([^\"]*)\" is displayed for \"([^\"]*)\"$",reassignAppointmentsPage::verifyDisappearingErrorMessageInReAssignAppointments);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when Select new assignee is not selected using \"([^\"]*)\"$",reassignAppointmentsPage::verifyErrorMessageInSelectNewAssignee);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when appointments is not selected using \"([^\"]*)\",\"([^\"]*)\"$",reassignAppointmentsPage::verifyErrrorMessageForNoAppointmentsSelected);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when the user \"([^\"]*)\" is selected$",reassignAppointmentsPage::verifyErrrorMessageForNoAppointmentsUser);

        Then("^HE I verify show more button displaying when 26 or more appointments are returned for the user \"([^\"]*)\" in reassignAppointments Page$",reassignAppointmentsPage::verifyShowMoreButton);

        Then("^HE I verify Select all check box in reAssignAppointments page using \"([^\"]*)\"$",reassignAppointmentsPage::verifySelectAllCheckBox);

        Then("^HE I verify the appointments count in reAssignAppointments page for the user \"([^\"]*)\"$",reassignAppointmentsPage::verifyAppointmentsCount);

        Then("^HE I verify the user \"([^\"]*)\" is displaying in select staff member dropdown$",reassignAppointmentsPage::verifyUserInSelectStaffMemberDropdown);

        Then("^HE I verify the text 'Showing all' is displaying in reassignAppointments Page for the user \"([^\"]*)\"$",reassignAppointmentsPage::verifyShowingAllText);

        Then("^HE I verify the appointments displaying in agenda view$",reassignAppointmentsPage::verifyAppointmentsAreInAgendaView);

        Then("^HE I click Go Back button$",reassignAppointmentsPage::clickGoBackButton);

        Then("^HE I verify the user \"([^\"]*)\" is displaying in Select new assignee dropdown$",reassignAppointmentsPage::verifyUserInSelectNewAssigneeDropdown);

        Then("^HE I verify no appointment text is displaying for the user \"([^\"]*)\"$",reassignAppointmentsPage::verifyUserWithNoAppointments);
    }
}
