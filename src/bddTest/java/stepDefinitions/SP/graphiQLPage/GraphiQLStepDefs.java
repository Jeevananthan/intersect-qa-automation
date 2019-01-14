package stepDefinitions.SP.graphiQLPage;

import cucumber.api.java8.En;
import pageObjects.SP.graphiQLPage.GraphiQLPageImpl;

public class GraphiQLStepDefs implements En {

    public GraphiQLStepDefs() {

        GraphiQLPageImpl graphiQLPage = new GraphiQLPageImpl();

        And("^SP I create a new subscription via GraphiQL with the data in \"([^\"]*)\" and the following settings:$", graphiQLPage::createSubscriptionViaGraphiQL);
    }
}

