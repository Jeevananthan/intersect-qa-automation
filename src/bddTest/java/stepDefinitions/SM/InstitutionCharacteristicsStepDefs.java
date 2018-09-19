package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.institutionCharacteristicsPage.InstitutionCharacteristicsImpl;

public class InstitutionCharacteristicsStepDefs implements En {

    public InstitutionCharacteristicsStepDefs() {

        InstitutionCharacteristicsImpl iCObj = new InstitutionCharacteristicsImpl();

        And("^SM I verify the tootip for \"([^\"]*)\" in \"([^\"]*)\"$", iCObj::verifyTooltipForAverageClassSize);

    }
}
