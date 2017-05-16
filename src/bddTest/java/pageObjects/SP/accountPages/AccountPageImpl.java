package pageObjects.SP.accountPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
    }
}
