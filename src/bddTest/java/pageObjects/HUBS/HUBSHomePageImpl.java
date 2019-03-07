package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HUBSHomePageImpl extends PageObjectFacadeImpl {

    Logger logger = null;
    NavianceCollegeProfilePageImpl navColProPageObj = new NavianceCollegeProfilePageImpl();
    public HUBSHomePageImpl() {
        logger = Logger.getLogger(HUBSHomePageImpl.class);
    }

    /**
     * The below method will verify the presence of 3 Main menu tab ie "BASIC INFO", "MEDIA" and "LINKS & PROFILES"
     * @param basicInfo (all parameter getting from feature file
     * @param media
     * @param links
     * Author : Arun
     */
    public void verifyHubPageMainMenuTabs(String basicInfo, String intro, String media, String links){
        Assert.assertTrue(basicInfo+" tab is not displaying or by default it's not highlighted.", getBasicInfoTab(basicInfo).isDisplayed());
        Assert.assertTrue(intro+" tab is not displaying.", getIntroTab(intro).isDisplayed());
        Assert.assertTrue(media+" tab is not displaying.", getMediaTab(media).isDisplayed());
        Assert.assertTrue(links+" tab is not displaying.", getLinksTab(links).isDisplayed());
        Assert.assertTrue("PUBLISH MY INFORMATION CHANGES button is not displaying.", getPublishMyBasicInfoChangesButton().isDisplayed());
    }

    /**
     * The below method will verify the Publish button for respective main menu tab.
     * @param menuOption (menu tab for which we want to check the Publish button, parameter coming from feature file)
     * Author : Arun
     */
    public void checkHEMMainMenuTabFunctionality(String menuOption){
        switch (menuOption){
            case "MEDIA" :
                Assert.assertTrue("Publish my media changes button is not displaying.", getPublishMyMediaChangesButton().isDisplayed());
                WebElement disablePublishLinkMedia = driver.findElement(By.xpath("(//span[text()='Publish my media changes']|//button[text()='Publish my media changes'])/.."));
                String actDisablePublishLinkMedia = disablePublishLinkMedia.getAttribute("class");
                String expDisablePublishLink = "ui secondary disabled button";
                Assert.assertTrue("Publish my media changes button is not disable.", actDisablePublishLinkMedia.equals(expDisablePublishLink));
                break;
            case "LINKS & PROFILES":
                Assert.assertTrue("Publish my LINKS & PROFILES changes button is not displaying.", getPublishMyLinksAndProfilesChangesButton().isDisplayed());
                WebElement disablePublishLinkLinks = driver.findElement(By.xpath("//button[text()='Publish my links']|//span[text()='Publish my links & profiles changes']/.."));
                String actDisablePublishLinkLinks = disablePublishLinkLinks.getAttribute("class");
                String expDisablePublishLinkLinks = "ui secondary disabled button";
                Assert.assertTrue("Publish my links & profiles changes button is not disable.", actDisablePublishLinkLinks.equals(expDisablePublishLinkLinks));
                profilesLink().click();
                Assert.assertTrue("CREATE A NEW PROFILE Button is not displaying.", createNewProfileButton().isDisplayed());
                break;
            case "INTRO" :
                Assert.assertTrue("Publish intro changes button is not displaying.", getPublishIntroChangesButton().isDisplayed());
                WebElement disablePublishLinkIntro = driver.findElement(By.xpath("//button[text()='Publish intro changes']|//span[text()='Publish intro changes']/.."));
                String actDisablePublishLinkIntro = disablePublishLinkIntro.getAttribute("class");
                String expDisablePublishLinkIntro = "ui secondary disabled button";
                Assert.assertTrue("Publish my media changes button is not disable.", actDisablePublishLinkIntro.equals(expDisablePublishLinkIntro));
                break;
        }
    }

    /**
     * The method is to click the main menu tab.
     * @param menuTab
     * Author : Arun
     */
    public void clickOnHubsMenuTab(String menuTab){
        waitForUITransition();
        getMainMenuTab(menuTab).click();
        }

    /**
     * The below method to verify the Publish Media Changes model, which is mainly the text and buttons in that model.
     * Author : Arun
     */
    public void verifyPublishYourMediaChangesModel(String menuTab){
        Assert.assertTrue("Continue Editing button is not displaying.", getContinueEditingButton().isDisplayed());
        Assert.assertTrue("Disregard my changes button is not displaying.", getDisregardMyChangesButton().isDisplayed());
        Assert.assertTrue("Publish button is not displaying.", getPublishButton().isDisplayed());
        getContinueEditingButton().click();

        clickOnHubsMenuTab("BASIC INFO");
        getPublishButton().click();

        Assert.assertTrue("You’re almost done! text is not displaying", driver.findElement(
                By.xpath("//span[text()='You’re almost done!']")).isDisplayed());

        String actText1 = driver.findElement(By.xpath("//span[contains(text(), 'The following items you')]")).getText();
        String expText1 = "The following items you've updated need approval by Hobsons.";
        Assert.assertTrue(expText1+ "Text is not displaying.", actText1.equals(expText1));

        Assert.assertTrue("The approval process can take up to 24-48 hours. Text is not displaying.",
                driver.findElement(By.xpath("//span[text()= 'The approval process can take up to 24-48 hours.']")).isDisplayed());

        if (menuTab.equals("MEDIA")) {
            String actText2 = driver.findElement(By.xpath("//span[contains(text(),'Media - Photos')]")).getText();
            String expText2 = "Media - Photos & Videos, Logo";
            Assert.assertTrue(expText1+ " Text is not displaying.", actText2.equals(expText2));
        } else if (menuTab.equals("LINKS & PROFILES")){
            String actText3 = driver.findElement(By.xpath("(//span[contains(text(),'Links ')])[2]")).getText();
            String expText3 = "Links & Profiles - Links, Profiles";
            Assert.assertTrue(expText3+ " Text is not displaying.", actText3.equals(expText3));
        }


        Assert.assertTrue("Please provide a brief explanation for the changes: text is not displaying.",
                driver.findElement(By.xpath("//span[text()='Please provide a brief explanation for the changes:']")).isDisplayed());

        Assert.assertTrue("Cancel And Continue Editing is not displaying.", getCancelAndContinueEditingButton().isDisplayed());
        Assert.assertTrue("Submit changes button is not displaying.", getSubmitChangesButton().isDisplayed());

        getCancelAndContinueEditingButton().click();
        clickOnHubsMenuTab("BASIC INFO");
        getPublishButton().click();
        getSubmitChangesButton().click();
    }

    public void selectImageForLogo(){
        logo().click();
        String logoPath = System.getProperty("user.dir") + "/src/bddTest/java/utilities/Logo.jpg";
        selectAnImageToUpload().sendKeys(logoPath);
        Assert.assertTrue("Back button is not displaying in Publish model.", backButton().isDisplayed());
        previewButton().click();
    }

    public void clickOnIntersect(){
        intersectImageLink().click();
    }

    //Locators
    public WebElement profilesLink(){ return driver.findElement(By.id("profiles-accordion-title")); }
    public WebElement createNewProfileButton(){ return driver.findElement(By.xpath("//span[text()='CREATE A NEW PROFILE']"));}
    public WebElement intersectImageLink(){ return driver.findElement(By.className("_2kV1BuYBb3VKdqe1aD7Wpm")); }
    public WebElement selectAnImageToUpload(){ return driver.findElement(By.xpath("//input[@accept='image/*']"));}
    public WebElement previewButton(){ return driver.findElement(By.xpath("//span[text()='PREVIEW AND CONTINUE EDITING']"));}
    public WebElement backButton(){ return driver.findElement(By.xpath("//span[text()='Back']"));}
    public WebElement logo(){ return driver.findElement(By.xpath("//span[text()='Logo']"));}
    public WebElement continureEditingPopupLink(){return driver.findElement(By.id("confirm-close-button"));}
    public WebElement getContinueEditingButton(){return driver.findElement(By.id("publish-close-button"));}
    public WebElement getDisregardMyChangesButton(){return driver.findElement(By.id("publish-continue-button"));}
    public WebElement getPublishButton(){return driver.findElement(By.id("publish-next-button"));}
    public WebElement getSubmitChangesButton(){return driver.findElement(By.id("confirm-submit-button"));}
    public WebElement getCancelAndContinueEditingButton(){return driver.findElement(By.id("confirm-close-button"));}
    public WebElement getPublishMyMediaChangesButton(){return driver.findElement(By.xpath("//span[text()='Publish my media changes']|//button[text()='Publish my media changes']"));}
    public WebElement getPublishMyLinksAndProfilesChangesButton(){return driver.findElement(By.xpath("//button[text()='Publish my links']|//span[text()='Publish my links & profiles changes']"));}
    public WebElement getPublishIntroChangesButton(){return driver.findElement(By.xpath("//button[text()='Publish intro changes']|//span[text()='Publish intro changes']"));}
    public WebElement getMainMenuTab(String menuTab){return driver.findElement(By.xpath("//li/span[text()='"+menuTab+"']|//a[text()='"+menuTab+"']"));}
    public WebElement getPublishMyBasicInfoChangesButton(){return driver.findElement(By.xpath("//span[text()='Publish my basic info changes']"));}
    public WebElement getBasicInfoTab(String basicInfo){return driver.findElement(By.xpath("//li/span[text()='"+basicInfo+"' and @class='active']"));}
    public WebElement getIntroTab(String intro){return driver.findElement(By.xpath("//li/span[text()='"+intro+"']"));}
    public WebElement getMediaTab(String media){return driver.findElement(By.xpath("//li/span[text()='"+media+"']"));}
    public WebElement getLinksTab(String links){return driver.findElement(By.xpath("//li/span[text()='"+links+"']"));}

}
