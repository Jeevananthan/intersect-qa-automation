package stepDefinitions.SP.accountPages;

import cucumber.api.java8.En;
import org.apache.log4j.Logger;
import pageObjects.SP.accountPages.LogHistoryPageImpl;

public class LogHistoryPageStepDefs implements En {

    private Logger logger;

    public LogHistoryPageStepDefs() {
        logger = Logger.getLogger(LogHistoryPageStepDefs.class);

        LogHistoryPageImpl logHistoryPage = new LogHistoryPageImpl();

        Then ("SP I select \"([^\"]*)\" from the Log History filter$",logHistoryPage::selectVariousDateFilterInLogHistory);

        And ("SP I verify the Log History correctly shows records from \"([^\"]*)\"$",logHistoryPage::verifyLogHistoryResultsTable);

        And ("SP I verify that Start and End Date are displayed in the Log History filter after choosing 'Custom'$",logHistoryPage::verifyCustomOptionFieldsInLogHistory);

    }
}
