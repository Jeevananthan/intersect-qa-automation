package pageObjects.CM.userConnectionsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pageObjects.CM.commonPages.PageObjectFacadeImpl;
import pageObjects.CM.homePage.HomePageImpl;
import pageObjects.CM.loginPage.LoginPageImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bojan on 6/2/17.
 */
public class userConnectionsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    LoginPageImpl lp;
    HomePageImpl hp;

    public userConnectionsPageImpl() {

        logger = Logger.getLogger(userConnectionsPageImpl.class);
    }


    public void iframeEnter()  {
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title=Community]")));
    }

    public void iframeExit() {
        driver.switchTo().defaultContent();
    }

    public void goToUserConnectionsPage() {
        logger.info("Going to user connections page.");
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        iframeEnter();
        link(By.cssSelector("a[href='/connections']")).click();
    }

    public void goToInstituionFollowingPage() {
        logger.info("Going to instituions I'm following page.");
        link(By.cssSelector("a[href='/connections/following']")).click();
    }


    public void searchForUser(String user){
        textbox(By.id("global-search-box-input")).sendKeys(user);
        logger.info("Searching for user.");
        waitUntilElementExists(link(By.id("global-search-box-item-0")));
        link(By.id("global-search-box-item-0")).click();
        waitUntilPageFinishLoading();
    }

    public void checkIfConnectionButtonExists(){
        logger.info("Checking if connect to user button exists.");
        iframeEnter();
        Assert.assertTrue(connectToUserButton().isDisplayed());
    }

    public void connectToUser() {
        logger.info("Clicking on connect to user button.");
        iframeEnter();
        connectToUserButton().click();
    }

    public void checkIfConfirmationMsgDisplayed() {
        logger.info("Checking if connect confirmation box is displayed.");
        Assert.assertTrue(driver.findElement(By.id("modal-content")).isDisplayed());
    }

    public void checkIfConfirmationMsgIncludesMsgBox() {
        logger.info("Checking if connect confirmation box includes message box and Send Invite button.");
        Assert.assertTrue(confirmationMsgBox().isDisplayed());
        Assert.assertTrue(sendInvitationToUserButton().isDisplayed());
    }

    public void exportConnections() {
        logger.info("Clicking on Export Connections button.");
        exportConnectionsBtn().click();
    }

    public void assertConnectionsCSVDownloaded(String getfileName, String getdownloadPath) {
        logger.info("Checking if CSV file is downloaded.");
        Assert.assertTrue(checkConnectionsCSVFile(getfileName, getdownloadPath));
    }

    public boolean checkConnectionsCSVFile(String fileName, String downloadPath) {
        waitUntilPageFinishLoading();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdf.format(now);

        String fullFileName = fileName + strDate + ".csv";
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();


        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fullFileName)) {
                // File has been found, it can now be deleted:
                logger.info("The CSV file has been downloaded sucessfuly.");
                logger.info("Deleting file: " + fullFileName);
                dirContents[i].delete();
                return true;
            }
        }
        return false;

    }

    public void enterConnectionRequestMsg(String msg) {
        confirmationMsgBox().sendKeys(msg);
    }

    private WebElement searchBox(){
        return driver.findElement(By.id("global-search-box-input"));
    }

    private WebElement connectToUserButton() {
        return driver.findElement(By.id("block-cp-ur-tools-cp-ur-conn-link"));
    }

    private WebElement sendInvitationToUserButton() {
        return driver.findElement(By.id("edit-send"));
    }

    private WebElement confirmationMsgBox() {
        return driver.findElement(By.id("edit-message"));
    }

    private WebElement exportConnectionsBtn() {return driver.findElement(By.cssSelector("a[href='/connections/my-connections/export']"));}

    private WebElement pendingRequestsLink() {return driver.findElement(By.cssSelector("a[href='/connections/my-connections/pending-requests']"));}

    private WebElement approveNewConnection() {return driver.findElement(By.xpath("//*[@id='cp-ur-5457']/a[1]"));}
}
