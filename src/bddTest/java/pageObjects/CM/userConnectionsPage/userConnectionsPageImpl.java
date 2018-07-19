package pageObjects.CM.userConnectionsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.CM.homePage.HomePageImpl;
import pageObjects.CM.loginPage.LoginPageImpl;
//import sun.rmi.runtime.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bojan on 6/2/17.
 */
public class userConnectionsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

//    private LoginPageImpl lp;
//    private HomePageImpl hp;
    HomePageImpl hp = new HomePageImpl();
    LoginPageImpl lp = new LoginPageImpl();

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
//        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        iframeExit();
        link(By.partialLinkText("Counselor Community")).click();
        communityFrame();
        link(By.cssSelector("a[href='/connections']")).click();
    }

    public void clickConnectionsTab() {
        logger.info("Going to connections tab.");
        link(By.cssSelector("a[href='/connections']")).click();
    }

    public void goToUserCOnenctionsList() {
        logger.info("Going to user's connections list.");
        communityFrame();
        link(By.xpath("//ul[@class='tabs primary']/li[2]/a[contains(text(), 'Connections')]")).click();
    }

    public void checkMutualConnectionsDisplayed() {
        logger.info("Checking if there are mutual connection section displayed.");
        Assert.assertTrue("There are no mutual connections displayed!", checkItemVisibleByCssSelector("div", "class", "mutual-wrapper"));
    }

    public void checkConnectionsDisplayed() {
        logger.info("Checking if I see user's connections.");
        Assert.assertTrue("There are no mutual connections displayed!", checkItemVisibleByCssSelector("div", "class", "connections-wrapper"));
    }

    public void goToHSUserConnectionsPage() {
        logger.info("Going to user connections page.");
        iframeExit();
        link(By.id("js-main-nav-home-menu-link")).click();
        communityFrame();
        link(By.cssSelector("a[href='/connections']")).click();
    }

    public void goToInstituionFollowingPage() {
        logger.info("Going to instituions I'm following page.");
        link(By.cssSelector("a[href='/connections/following']")).click();
    }

    public void checkIfNotFollowingLebanonHighSchool() {
        logger.info("Checking if I'm not following Lebanon High School.");
        try {
            driver.findElement(By.xpath("//*[contains(text(), 'LEBANON HIGH SCHOOL')]"));
            driver.findElement(By.id("unfollow-10226")).click();
            waitUntilPageFinishLoading();

        } catch (NoSuchElementException ex) {
            logger.info("Not following Lebanon High School.");
        }
//        Assert.assertFalse("The user is following HS user's institution.", checkItemVisible("LEBANON HIGH SCHOOL"));
        iframeExit();
    }

    public void checkIfFollowingLebanonHighSchool() {
        logger.info("Checking if I'm not following Lebanon High School.");
        Assert.assertTrue("The user is following HS user's institution.", checkItemVisible("LEBANON HIGH SCHOOL"));
    }


    public void searchForUser(String user){
        iframeExit();
        textbox(By.id("global-search-box-input")).clear();
        textbox(By.id("global-search-box-input")).sendKeys(user);
        logger.info("Searching for user.");
        waitUntilElementExists(link(By.id("global-search-box-item-0")));
        //link(By.id("global-search-box-item-0")).click();
        waitUntilPageFinishLoading();
        link(By.xpath("//*[@id=\"global-search-box-item-0\"]/i")).click();
        //link(By.id("global-search-box-item-0")).click();
        waitUntilPageFinishLoading();
    }

    public void checkIfConnectionButtonExists(){
        logger.info("Checking if connect to user button exists.");
        communityFrame();
        Assert.assertTrue(connectToUserButton().isDisplayed());
    }

    public void connectToUser() {
        logger.info("Clicking on connect to user button.");
        communityFrame();
        connectToUserButton().click();
    }

    public void connectToHSUser() {
        logger.info("Making sure that I am connected to PurpleHS user");
        iframeExit();
        searchForUser("PurpleHS User");
        communityFrame();
        try {
            connectToUserButton().click();
            sendInvitationToUserButton().click();
            waitUntilPageFinishLoading();
            acceptConnectionRequestByHSUser();
            waitUntilPageFinishLoading();
        } catch (NoSuchElementException ex) {
            logger.info("The PurpleHS user is already a connection.");
        }
    }

    public void disconnectFromUser(String user) {
        logger.info("Making sure that I am not connected to PurpleHS user");
        iframeExit();
        searchForUser(user);
        communityFrame();
        try {
            clickDisconnectBtn();
        } catch (NoSuchElementException ex) {
            logger.info("The user is not a connection.");
            driver.findElement(By.className("close")).click();
            waitUntilPageFinishLoading();
        }
    }

    public void clickDisconnectBtn() {
        communityFrame();
        connectToUserButton().click();
        waitUntilPageFinishLoading();
        disconnectFromUserBtn().click();
        waitUntilPageFinishLoading();
    }

    public void disconnectFromUserFromManageConnectionsPage(String user) {
        logger.info("Clicking on Disconnect button.");
        driver.findElement(By.xpath("//div[contains(text(), '"+user+"')]/../../../../../td[2]/div[1]/div[@class='ctools-dropdown ctools-dropdown-processed']")).click();
        waitUntilPageFinishLoading();
//        disconnectFromUserBtn().click();
        driver.findElement(By.xpath("//div[@style='display: block;']/ul[1]/li[2]/a[@title='Remove Connection']")).click();
        waitUntilPageFinishLoading();
    }

    public void acceptConnectionRequestByHSUser() {
        logger.info("Accepting user connection request by PurpleHS user.");
        hp.logoutHEDefault();
        waitUntilPageFinishLoading();
        lp.defaultLoginHS();
        //Next 3 lines we have to use because there are some session problems when we are logged in both as HS and HE users in the same browser
        link(By.id("js-main-nav-home-menu-link")).click();
        communityFrame();
        link(By.cssSelector("a[href='/profile']")).click();
        iframeExit();
        driver.navigate().refresh();
        goToHSUserConnectionsPage();
        pendingRequestsLink().click();
        driver.findElement(By.xpath("//div[contains(text(), 'PurpleHE Automation')]/../../../../../td[2]/div/a[@title='Accept Connect Request']")).click();
        waitUntilPageFinishLoading();
        hp.logoutHSDefault();
        lp.defaultLoginHE();
    }

    public void acceptConnectionRequestByHEUser() {
        logger.info("Accepting user connection request by PurpleHS user.");
        hp.logoutHSDefault();
        waitUntilPageFinishLoading();
        lp.defaultLoginHE();
        //Next 3 lines we have to use because there are some session problems when we are logged in both as HS and HE users in the same browser
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        communityFrame();
        link(By.cssSelector("a[href='/profile']")).click();
        iframeExit();
        driver.navigate().refresh();
        goToUserConnectionsPage();
        pendingRequestsLink().click();
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//div[contains(text(), 'PurpleHS User')]/../../../../../td[2]/div/a[@title='Accept Connect Request']")).click();
        waitUntilPageFinishLoading();
        hp.logoutHEDefault();
        lp.defaultLoginHS();
    }

    public void ignoreConnectionRequestByHEUser() {
        logger.info("Accepting user connection request by PurpleHS user.");
        hp.logoutHSDefault();
        waitUntilPageFinishLoading();
        lp.defaultLoginHE();
        //Next 3 lines we have to use because there are some session problems when we are logged in both as HS and HE users in the same browser
        link(By.id("js-main-nav-counselor-community-menu-link")).click();
        communityFrame();
        link(By.cssSelector("a[href='/profile']")).click();
        iframeExit();
        driver.navigate().refresh();
        goToUserConnectionsPage();
        pendingRequestsLink().click();
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//div[contains(text(), 'PurpleHS User')]/../../../../../td[2]/div/a[@title='Ignore Connect Request']")).click();
        waitUntilPageFinishLoading();
        hp.logoutHEDefault();
        lp.defaultLoginHS();
    }

    public void ignoreConnectionRequestByHSUser() {
        logger.info("Accepting user connection request by PurpleHS user.");
        hp.logoutHEDefault();
        waitUntilPageFinishLoading();
        lp.defaultLoginHS();
        //Next 3 lines we have to use because there are some session problems when we are logged in both as HS and HE users in the same browser
        link(By.id("js-main-nav-home-menu-link")).click();
        communityFrame();
        link(By.cssSelector("a[href='/profile']")).click();
        iframeExit();
        driver.navigate().refresh();
        goToHSUserConnectionsPage();
        pendingRequestsLink().click();
        driver.findElement(By.xpath("//div[contains(text(), 'PurpleHE Automation')]/../../../../../td[2]/div/a[@title='Ignore Connect Request']")).click();
        waitUntilPageFinishLoading();
        hp.logoutHSDefault();
        lp.defaultLoginHE();
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

    public void addNewConnectionsCategory(String catName) {
        logger.info("Adding new connections category.");
        addCategoryBtn().click();
        newCategoryNameField().sendKeys(catName);
        createCategoryBtn().click();
        waitUntilPageFinishLoading();
    }

    public void checkIfNewConnectionsCategoryIsAdded(String catName) {
        logger.info("Asserting if new connections category is added.");
        Assert.assertTrue("The category is not being added.", driver.findElement(By.xpath("//li/a[contains(text(), '" + catName + "')]")).isDisplayed());
//        deleteCreatedConenctionsCategory(catName);
    }

    public void deleteCreatedConenctionsCategory(String catToDelete) {
        logger.info("Deleting "+catToDelete+" category.");
        driver.findElement(By.xpath("//li/a[contains(text(), '" + catToDelete + "')]/../a[@title='Edit category']")).click();
        deleteCategoryBtn().click();
//        logger.info("Checking if category is deleted.");
//        Assert.assertFalse("The category is still present.", checkIfConnectionsCategoryIsDeleted(catToDelete));
    }

    private boolean checkIfConnectionsCategoryIsDeleted(String category) {
        try {
            driver.findElement(By.xpath("//li/a[contains(text(), '" + category + "')]")).isDisplayed();
            return true;

        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void userHasAtLeastOneConnection() {
        logger.info("Making sure user has at least one connection.");
        try {
            driver.findElement(By.xpath("//div[contains(text(), 'MatchSupportUIQA3')]"));


        } catch (NoSuchElementException ex) {
            driver.get("https://qa-he.intersect.hobsons.com/counselor-community/profile/5539");
            communityFrame();
            connectToUserButton().click();
            sendInvitationToUserButton().click();
            waitUntilPageFinishLoading();
            hp.logoutHEDefault();
            waitUntilPageFinishLoading();
            lp.loginAsMatchSupportUIQA3();
            goToUserConnectionsPage();
            pendingRequestsLink().click();
            driver.findElement(By.xpath("//div[contains(text(), 'PurpleHE Automation')]/../../../../../td[2]/div/a[@title='Accept Connect Request']")).click();
            waitUntilPageFinishLoading();
            hp.logoutSupport();
            lp.defaultLoginHE();
            waitUntilPageFinishLoading();
            goToUserConnectionsPage();
        }
    }

    private boolean checkItemVisible(String item) {
        try {
            driver.findElement(By.xpath("//*[contains(text(), '" + item + "')]"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    private boolean checkItemVisibleByCssSelector(String tag, String cssselector, String selectorvalue) {
        try {
            driver.findElement(By.cssSelector(""+tag+"["+cssselector+"='"+selectorvalue+"']"));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    private boolean checkItemVisibleById(String id) {
        try {
            driver.findElement(By.id(id));
            return true;
        } catch (NoSuchElementException ex)  {
            return false;
        }
    }

    public void checkIfMyConnectionsPageOpened() {
        logger.info("Checking if My Connections page is opened");
        Assert.assertTrue("My Connections page is not opened.", checkItemVisible("My Connections"));
    }

    public void navigateToIndividualUser() {
        userHasAtLeastOneConnection();
        logger.info("Navigating to MatchSupportUIQA3 user.");
        driver.findElement(By.xpath("//div[contains(text(), 'MatchSupportUIQA3')]/../../../div[1]")).click();
        Assert.assertTrue("User MatchSupportUIQA3 profile page not opened.", checkItemVisible("MatchSupportUIQA3"));
    }

    public void sendConnectionInvitation() {
        logger.info("Sending invitation to connect with user.");
        sendInvitationToUserButton().click();
        waitUntilPageFinishLoading();
    }

    public void checkIfuserIsConencted(String user) {
        logger.info("Checking if users are connected.");
        goToUserConnectionsPage();
        Assert.assertTrue("Cannot find the user in users connections list!!", checkItemVisible(user));
    }
    public void checkIfHeIsNotConnected(String user) {
        logger.info("Checking if users are not connected.");
        goToUserConnectionsPage();
        Assert.assertFalse("HS user can be found in HE user's connections list!!", checkItemVisible(user));
    }


    public void statusInvitedVisible( String status) {
        logger.info("Checking if correct button status is displayed.");
        iframeExit();
        communityFrame();
        Assert.assertTrue("The status is not displayed correctly!", checkItemVisibleByCssSelector("a", "title", status));
    }

    public void clickMessageLink() {
        logger.info("Clicking on Message link.");
        //iframeEnter();
        waitUntilPageFinishLoading();
        messageLink().click();
    }


    public void checkNewMsgForm() {
        logger.info("Checking if new message form elements are displayed.");
        Assert.assertTrue("Element for message subject not visible!", checkItemVisibleById("edit-subject"));
        Assert.assertTrue("Element for message body not visible!", checkItemVisibleById("edit-message"));
    }

    public void fillNewMessageForm(String subject, String msgbody) {
        logger.info("Populating new message form.");
        msgSubject().sendKeys(subject);
        msgBody().sendKeys(msgbody);
    }

    public void sendMessageToUser() {
        logger.info("Sending the message to user.");
        sendInvitationToUserButton().click();
        waitUntilPageFinishLoading();
    }

    public void clickOnMessageButton() {
        logger.info("Clicking on Message button.");
        button(By.cssSelector("a[href='/message?destination=connections/my-connections']")).click();
        waitUntilPageFinishLoading();
    }

    public void addUserToSandMessageTo(String user) {
        logger.info("Adding "+user+" to selected conenctions list.");
        searchUserField().sendKeys(user);
        searchBtn().click();
        driver.findElement(By.cssSelector("label[class='option']")).click();
        driver.findElement(By.cssSelector("input[value='Add']")).click();
        waitUntilPageFinishLoading();
    }


    public void checkMessageActionNotification() {
        logger.info("Checking if there is a message action notification on the page.");
        Assert.assertTrue("The message action notification is not displayed!", checkItemVisibleByCssSelector("div", "class", "messages status"));
    }

    public void checkIfICanConnectToUsersConnections() {
        logger.info("Checking when viewing a connection's connections if I am able to request to connect with them");
        Assert.assertTrue("When viewing a connection's connections it is not possible to request to connect with them!", checkItemVisibleByCssSelector("a","title","Send Connect Request"));
    }

    public void addUserToCustomCategory(String user, String category) {
        logger.info("Adding "+user+" to the category "+category+".");
        connectToSpecificUserBtn(user).click();
        waitUntilPageFinishLoading();
        addToSpecificCategory(category).click();
        waitUntilPageFinishLoading();
    }

    public void goToCategory(String category){
        logger.info("Going to the category "+category+".");
        goToUserConnectionsPage();
        driver.findElement(By.xpath("//a[contains(text(), '"+category+"')]")).click();
        waitUntilPageFinishLoading();
    }

    public void checkIfUserIsInCategory(String user){
        logger.info("Checking if "+user+" is added to the category.");
        Assert.assertTrue("The user is not in the cattegory!", checkItemVisible(user));
    }

    public void checkIfUserIsNotInCategory(String user){
        logger.info("Checking if "+user+" is not displayed to the category.");
        Assert.assertFalse("The user is displayed in the category!", checkItemVisible(user));
    }

    public void removeAllConnectionsFromCategory(String category) {
        logger.info("Removing all users from the "+category+" category.");
//        driver.findElement(By.cssSelector("a[class='ctools-dropdown-link ctools-dropdown-text-link']"));
        List<WebElement> connections = driver.findElements(By.cssSelector("a[class='ctools-dropdown-link ctools-dropdown-text-link']"));

        for (int i=0; i<connections.size(); i++) {
            driver.findElement(By.cssSelector("a[class='ctools-dropdown-link ctools-dropdown-text-link']")).click();
            removeFromSpecificCategory(category).click();
            driver.findElement(By.xpath("//a[contains(text(), '"+category+"')]")).click();
            waitUntilPageFinishLoading();
        }
    }

    public void removeUserFromCustomCategory(String user, String category) {
        logger.info("Removing "+user+" from the category "+category+".");
        connectToSpecificUserBtn(user).click();
        waitUntilPageFinishLoading();
        removeFromSpecificCategory(category).click();
        waitUntilPageFinishLoading();
    }

    public void changeConenctionCategoryName(String catNameCurrent, String catNameNew) {
        logger.info("Changing name of "+catNameCurrent+" category.");
        editCustomCategory(catNameCurrent).click();
        newCategoryNameField().clear();
        newCategoryNameField().sendKeys(catNameNew);
        saveBtn().click();
        waitUntilPageFinishLoading();

    }

    public void checkIfCategoriesAreDisplayed(String cat1, String cat2) {
        logger.info("Checking if categories are displayed.");
        Assert.assertTrue(""+cat1+" category not visible!", checkItemVisible(cat1));
        Assert.assertTrue(""+cat2+" category not visible!", checkItemVisible(cat2));
    }

    public void checkRedIndicatorVisible() {
        logger.info("Checking if red indicator is visible.");
        Assert.assertTrue("", checkItemVisibleByCssSelector("span", "class", "pending-conn-count"));
    }

    public void clickOnPendingRequestsLink() {
        logger.info("Clicking on Pending requests link.");
        pendingRequestsLink().click();
    }

    public void clickApproveBtn(String user) {
        logger.info("Clicking on Approve button.");
        specificApproveNewConnectionBtn(user).click();
        waitUntilPageFinishLoading();
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

    private WebElement disconnectFromUserBtn() {return driver.findElement(By.cssSelector("a[title='Remove Connection']"));}

    private WebElement sendInvitationToUserButton() {
        return driver.findElement(By.id("edit-send"));
    }

    private WebElement confirmationMsgBox() {
        return driver.findElement(By.id("edit-message"));
    }

    private WebElement exportConnectionsBtn() {return driver.findElement(By.cssSelector("a[href='/connections/my-connections/export']"));}

    private WebElement pendingRequestsLink() {return driver.findElement(By.cssSelector("a[href='/connections/my-connections/pending-requests']"));}

    private WebElement approveNewConnection() {return driver.findElement(By.xpath("//*[@id='cp-ur-5457']/a[1]"));}

    private WebElement addCategoryBtn() {return driver.findElement(By.cssSelector("a[title='Add New Connections Category']"));}
    private WebElement newCategoryNameField() {return driver.findElement(By.id("edit-name"));}
    private WebElement createCategoryBtn() {return driver.findElement(By.id("edit-submit"));}
    private WebElement deleteCategoryBtn() {return driver.findElement(By.id("edit-del"));}
    private WebElement messageLink() {return driver.findElement(By.cssSelector("a[title='Send Message']"));}
    private WebElement msgSubject() {return driver.findElement(By.id("edit-subject"));}
    private WebElement msgBody() {return driver.findElement(By.id("edit-message"));}
    private WebElement searchUserField() {return driver.findElement(By.id("edit-search"));}
    private WebElement searchBtn() {return driver.findElement(By.id("edit-search-btn"));}
    private WebElement connectToSpecificUserBtn(String user){return driver.findElement(By.xpath("//div[contains(text(), '"+user+"')]/../../../../..//div[@class='ctools-dropdown-link-wrapper']"));}
    private WebElement addToSpecificCategory(String category) {return driver.findElement(By.xpath("//div[@style='display: block;']//a[contains(text(), '+ "+category+"')]"));}
    private WebElement removeFromSpecificCategory(String category) {return driver.findElement(By.xpath("//div[@style='display: block;']//a[contains(text(), '- "+category+"')]"));}
    private WebElement specificApproveNewConnectionBtn(String user){return driver.findElement(By.xpath("//div[contains(text(), '"+user+"')]/../../../../..//a[@title='Accept Connect Request']"));}

    private WebElement saveBtn(){return driver.findElement(By.id("edit-save"));}
    private WebElement editCustomCategory(String category) {return driver.findElement(By.xpath("//a[contains(text(), '"+category+"')]/..//a[@title='Edit category']"));}

}
