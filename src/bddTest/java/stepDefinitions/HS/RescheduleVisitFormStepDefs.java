package stepDefinitions.HS;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HS.RescheduleVisitForm.RescheduleVisitFormImpl;
import pageObjects.HS.ScheduleNewVisitForm.ScheduleNewVisitFormImpl;

public class RescheduleVisitFormStepDefs implements En {
    public RescheduleVisitFormStepDefs() {
        RescheduleVisitFormImpl rescheduleVisitFormPage = new RescheduleVisitFormImpl();

        And("^HS I verify the following input validations in the Reschedule Visit form:$", rescheduleVisitFormPage::verifyValidationsInRescheduleVisitForm);

        And("^HS I close the Reschedule Visit form$", rescheduleVisitFormPage::closeRescheduleVisitForm);
    }
}
