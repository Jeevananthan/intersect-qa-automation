package stepDefinitions.HE.RepVisits;

import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.ReassignAppointmentsPageImpl;

public class ReassignAppointmentsPageStepDefs implements En {

    public ReassignAppointmentsPageStepDefs() {

        ReassignAppointmentsPageImpl reassignAppointmentsPage = new ReassignAppointmentsPageImpl();

        When("^HE I go to re assign appointments$", reassignAppointmentsPage::goToReassignAppointment);

        Then("^HE I verify that Re-assign link is \"([^\"]*)\"$",reassignAppointmentsPage::verifyReAssignLinkStatus);

        And("^HE I verify the users are displaying including \"([^\"]*)\" in re assign appointments dropdown using \"([^\"]*)\"$",reassignAppointmentsPage::verifyUsersInReAssignAppointments);

        Then("^HE I verify the user \"([^\"]*)\" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown$",reassignAppointmentsPage::verifyUserIsExcludedInSelectNewAssignee);

        Then("^HE I verify the blue Note alert \"([^\"]*)\" is displaying when changing the Select staff member dropdown for the users \"([^\"]*)\",\"([^\"]*)\" (?:with no appointments|with appointments) in Select new assignee dropdown$",reassignAppointmentsPage::verifyBlueNoteAlert);

        Then("^HE I select the user \"([^\"]*)\" from \"([^\"]*)\" dropdown$",reassignAppointmentsPage::selectUserFromUserListDropdown);

        Then("^HE I select the fair to reassign using \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$",reassignAppointmentsPage::selectFairsToReAssign);

        Then("^HE I click Reassign Appointments button \"([^\"]*)\"$",reassignAppointmentsPage::clickReAssignAppointmentsButton);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when Select staff member is not selected$",reassignAppointmentsPage::verifyErrorMessageInSelectStaffMember);

        Then("^HE I verify the error Message \"([^\"]*)\" is disappearing when the error message \"([^\"]*)\" is displayed for \"([^\"]*)\"$",reassignAppointmentsPage::verifyDisappearingErrorMessageInReAssignAppointments);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when Select new assignee is not selected using \"([^\"]*)\"$",reassignAppointmentsPage::verifyErrorMessageInSelectNewAssignee);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when appointments is not selected using \"([^\"]*)\",\"([^\"]*)\"$",reassignAppointmentsPage::verifyErrrorMessageForNoAppointmentsSelected);

        Then("^HE I verify the error Message \"([^\"]*)\" is displaying when the user \"([^\"]*)\" is selected$",reassignAppointmentsPage::verifyErrrorMessageForNoAppointmentsUser);

        Then("^HE I verify UI components for the user \"([^\"]*)\" with appointments in reAssignAppointments page$",reassignAppointmentsPage::verifyUserWithAppointments);

        Then("^HE I verify UI components for the user \"([^\"]*)\" in reAssignAppointments page$",reassignAppointmentsPage::verifyUserWithoutAppointments);
    }
}
