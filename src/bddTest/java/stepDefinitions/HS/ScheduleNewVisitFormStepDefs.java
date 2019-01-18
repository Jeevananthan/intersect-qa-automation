package stepDefinitions.HS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HS.ScheduleNewVisitForm.ScheduleNewVisitFormImpl;

public class ScheduleNewVisitFormStepDefs implements En {
    public ScheduleNewVisitFormStepDefs() {
        ScheduleNewVisitFormImpl scheduleNewVisitForm = new ScheduleNewVisitFormImpl();

        And("^HS I verify the following input validations in the Schedule New Visit form:$", scheduleNewVisitForm::verifyInputValidationsInScheduleNewVisit);
        And("^HS I open the Reschedule New Visit form$", scheduleNewVisitForm::openRescheduleVisitForm);
    }
}
