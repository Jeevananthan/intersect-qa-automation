package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.admission.AdmissionPageImpl;

public class AdmissionStepDefs implements En {

    public AdmissionStepDefs() {

        AdmissionPageImpl admissionPage = new AdmissionPageImpl();

        Then("^SM I verify that \"([^\"]*)\" contains the label \"([^\"]*)\" in Academic Match$", admissionPage::verifyLabelInActiveMatchCell);
    }
}
