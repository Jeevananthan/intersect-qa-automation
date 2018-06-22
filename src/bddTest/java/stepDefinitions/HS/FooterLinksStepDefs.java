package stepDefinitions.HS;

import cucumber.api.java8.En;
import pageObjects.HS.loginPage.LoginPageImpl;
import pageObjects.HS.FooterLinks.FooterLinksImpl;

public class FooterLinksStepDefs implements En {
    public FooterLinksStepDefs() {
        LoginPageImpl loginPage = new LoginPageImpl();
        FooterLinksImpl footerLinksObj = new FooterLinksImpl();

        And("^HS I verify \"([^\"]*)\" is present in the footer$",footerLinksObj::verifyLinksInFooter);

        And("^HS I navigate to each page and verify the unique URL is present in the \"([^\"]*)\" page$",footerLinksObj::switchToWindowAndVerifyURL);

    }
}
