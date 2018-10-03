package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.institutionCharacteristicsPage.InstitutionCharacteristicsImpl;

public class InstitutionCharacteristicsStepDefs implements En {

    public InstitutionCharacteristicsStepDefs() {

        InstitutionCharacteristicsImpl iCObj = new InstitutionCharacteristicsImpl();

        And("^SM I verify the tootip for \"([^\"]*)\" in \"([^\"]*)\"$", iCObj::verifyTooltipForAverageClassSize);

        Then("^SM I verify that the \"([^\"]*)\" check box is \"([^\"]*)\" in Institution Characteristics$", iCObj::verifyCheckboxSelectedUnselected);

    }
}
