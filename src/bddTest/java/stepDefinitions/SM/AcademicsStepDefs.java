package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.academics.AcademicsPageImpl;

public class AcademicsStepDefs implements En {

    public AcademicsStepDefs() {

        AcademicsPageImpl academicsPage = new AcademicsPageImpl();

        And("^SM I search the keyword \"([^\"]*)\" in Majors$", academicsPage::searchKeyWordInMajors);

    }
}
