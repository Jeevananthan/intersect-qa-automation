package stepDefinitions.SP.logHistory;

import cucumber.api.java8.En;
import pageObjects.SP.logHistoryPage.LogHistoryPageImpl;

public class LogHistoryStepDef implements En {
    LogHistoryPageImpl logHistoryPage = new LogHistoryPageImpl();
    public LogHistoryStepDef(){
        And("^SP I go to the log history page$", logHistoryPage::goToLogHistory);
        Then("^SP I verify that it is displayed an entry with action \"([^\"]*)\" and the following keys$", logHistoryPage::verifyChangesKeys);
    }
}
