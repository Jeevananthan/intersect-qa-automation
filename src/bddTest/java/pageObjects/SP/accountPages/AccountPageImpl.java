package pageObjects.SP.accountPages;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.NavBarImpl;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
    }

    public void verifyCreatePrimaryUser() {
        Assert.assertTrue("\"Create\" button for new primary user was not found!", button("CREATE").isDisplayed());
    }

    //Verifying Institutional information for "Docufide Institute of Technology (not a real school)"
    public void verifyInstitutuionalInformation() {
        Assert.assertTrue("Institutional Account Address1 is wrong.",text("1800 Rodeo Drive").isDisplayed());
        Assert.assertTrue("Institutional Account Address2 is wrong.",text("Suite 100").isDisplayed());
        Assert.assertTrue("Institutional Account City is wrong.",text("Beverly Hills").isDisplayed());
        Assert.assertTrue("Institutional Account postal code is wrong.",text("90210").isDisplayed());
        Assert.assertTrue("Institutional Account SCID is wrong.",text("SCID:").isDisplayed());
    }

    public void verifyAccessToLogHistory(String visible){
        if(visible == "Yes"){
            Assert.assertTrue("View Log History is not accessible", link("View Log History").isDisplayed());
        }
        else if (visible == "No"){
            Assert.assertFalse("View Log History should not be accessible", link("View Log History").isDisplayed());
        }
        else {
            logger.error("Invalid parameter passed");
        }
    }

    //The below method is to verify the Breadcrumbs along with corresponding heading.
    public void verifyLeftNavigationBar(DataTable dataTable){
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        for (Map.Entry pair : map.entrySet()){
            String heading = pair.getKey().toString();
            String[] content = pair.getValue().toString().split(",");
            for (String subMenu : content) {
                driver.findElement(By.xpath("(//span[contains(text(),'"+subMenu+"')])[2]")).click();
                Assert.assertTrue(heading+ " is not correct in Breadcrumbs", heading.equalsIgnoreCase(getHeadingBreadcrumbs().getText()));
                Assert.assertTrue(subMenu+ " is not correct in Breadcrumbs", subMenu.equals(getSubMeunBreadcrumbs().getText()));
            }
        }
    }

    //Getters
    public WebElement getHeadingBreadcrumbs(){ return driver.findElement(By.className("_2QGqPPgUAifsnRhFCwxMD7")); }
    public WebElement getSubMeunBreadcrumbs(){ return driver.findElement(By.className("UDWEBAWmyRe5Hb8kD2Yoc")); }

}
