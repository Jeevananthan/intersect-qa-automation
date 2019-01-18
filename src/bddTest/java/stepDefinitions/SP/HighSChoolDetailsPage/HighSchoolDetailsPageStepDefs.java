package stepDefinitions.SP.HighSChoolDetailsPage;

import cucumber.api.java8.En;
import pageObjects.SP.HighSchoolDetailsPage.HighSchoolDetailsPageImpl;
import pageObjects.SP.subscriptionsPage.SubscriptionsPageImpl;

public class HighSchoolDetailsPageStepDefs implements En {

    public HighSchoolDetailsPageStepDefs() {

        HighSchoolDetailsPageImpl HighSchoolDetailsPage = new HighSchoolDetailsPageImpl();



        And("^SP I verify High School Client Detials with following data:$", HighSchoolDetailsPage::verifyHighSchoolDetails);



    }
}