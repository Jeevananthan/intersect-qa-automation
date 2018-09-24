package pageObjects.HE.accountSettingsPage;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class SftpDataTransferPageImpl extends PageObjectFacadeImpl {

    /**
     * Verifies that the label that says: SFTP Data Transfer is displayed
     */
    public void verifySftpDataTransferLabelIsDisplayed(){
        Assert.assertTrue("The label that says: SFTP Data Transfer is not displayed",
                sftpDataTransferLabel().isDisplayed());
    }

    /**
     * Verifies that the label that says: Establish an automated secure file transfer using SFTP for the data listed
     * below is displayed
     */
    public void verifySftpDataTransferPageDescriptionIsDisplayed(){
        Assert.assertTrue("The label that says: Establish an automated secure file transfer using SFTP " +
                "for the data listed below, is not displayed", sftpDataTransferPageDescription().isDisplayed());
    }

    /**
     * Verifies that the label that says: NAVIANCE ACTIVEMATCH is displayed
     */
    public void verifyNavianceActiveMatchLabelIsDisplayed(){
        Assert.assertTrue("The label that says: NAVIANCE ACTIVEMATCH is not displayed",
                navianceActiveMatchLabel().isDisplayed());
    }

    /**
     * Verifies that the label that says: Student Connections is displayed
     */
    public void verifyStudentConnectionsLabelIsDisplayed(){
        Assert.assertTrue("The label that says: Student Connections is not displayed",
                studentConnectionsLabel().isDisplayed());
    }

    /**
     * Verifies that the label that says: Transfer new ActiveMatch student connections to a location of your choice
     * is displayed
     */
    public void verifyTransferAMConnectionsToALocationLabelIsDisplayed(){
        Assert.assertTrue("The label that says: Transfer new ActiveMatch student connections to " +
                "a location of your choice, is not displayed", transferAMConnectionsToALocationLabel().isDisplayed());
    }

    /**
     * Verifies that the SE UP CONNECTION BUTTON is displayed
     */
    public void verifySetupConnectionButtonIsDisplayed(){
        Assert.assertTrue("Yhe SET UP CONNECTION button is not displayed",
                setupConnectionButton().isDisplayed());
    }

    /**
     * Gets the label: SFTP Data Transfer
     * @return WebEelement
     */
    public WebElement sftpDataTransferLabel(){
        return text("SFTP Data Transfer");
    }

    /**
     * Gets the label: Establish an automated secure file transfer using SFTP for the data listed below.
     * @return WebEelement
     */
    public WebElement sftpDataTransferPageDescription(){
        return text("Establish an automated secure file transfer using SFTP for the data listed below.");
    }

    /**
     * Gets the label: NAVIANCE ACTIVEMATCH
     * @return WebElement
     */
    public WebElement navianceActiveMatchLabel(){
        return text("NAVIANCE ACTIVEMATCH");
    }

    /**
     * Gets the label: Student Connections
     * @return WebElement
     */
    public WebElement studentConnectionsLabel(){
        return text("Student Connections");
    }

    /**
     * Gets the label: Transfer new ActiveMatch student connections to a location of your choice
     * @return WebElement
     */
    public WebElement transferAMConnectionsToALocationLabel(){
        return text("Transfer new ActiveMatch student connections to a location of your choice.");
    }

    /**
     * Gets the button: SET UP CONNECTION
     * @return WebElement
     */
    public WebElement setupConnectionButton(){
        return button("SETUP CONNECTION");
    }

}
