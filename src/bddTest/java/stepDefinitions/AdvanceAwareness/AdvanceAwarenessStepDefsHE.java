package stepDefinitions.AdvanceAwareness;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.advanceawarenessPage.AdvanceAwarenessPageImpl;

/**
 * Created by mbhangu on 9/28/2018.
 */
public class AdvanceAwarenessStepDefsHE implements En {
    public AdvanceAwarenessStepDefsHE() {
        AdvanceAwarenessPageImpl advanceAwarenessPage = new AdvanceAwarenessPageImpl();

        And("^HE I click on button Configure for subscription \"([^\"]*)\"$", advanceAwarenessPage::configureSubscription);
        And("^HE I click on Advance Awareness menu option \"([^\"]*)\"$", advanceAwarenessPage:: selectAdvanceAwarenessMenuOption );
        And("^HE I select following Diversity Settings$", advanceAwarenessPage:: selectDiversityOptions );
        And("^HE I unselect following Diversity Settings$", advanceAwarenessPage:: unselectDiversityOptions );
        And("^HE I verify following options are checked$", advanceAwarenessPage:: verifyDiversityOptionschecked);
        And("^HE I verify following options are unchecked$",advanceAwarenessPage:: VerifyDiversityOptionUnChecked);

    }}
