package stepDefinitions.SP.FooterLinks;

import cucumber.api.java8.En;
import pageObjects.SP.FooterLinks.FooterLinksImpl;

public class FooterLinksStepDefs implements En {
    public FooterLinksStepDefs() {
        FooterLinksImpl footerLinksObj = new FooterLinksImpl();

        And("^SP I verify \"([^\"]*)\" is present in the footer$", footerLinksObj::verifyLinksInFooter);

        And("^SP I navigate to each page and verify the unique URL is present in the \"([^\"]*)\" page$",footerLinksObj::switchToWindowAndVerifyURL);

    }
}
