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
        softly().assertThat(getBasicInfoTab(basicInfo).getText()).as(basicInfo+" is not displaying.").isEqualTo(basicInfo);
        softly().assertThat(getIntroTab(intro).getText()).as(intro+" is not displaying.").isEqualTo(intro);
        softly().assertThat(getMediaTab(media).getText()).as(media+" is not displaying.").isEqualTo(media);
        softly().assertThat(getLinksTab(links).getText()).as(links+" is not displaying.").isEqualTo(links);
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
                softly().assertThat(getPublishMyMediaChangesButton().getText()).as("Publish my media changes button is not displaying.").isEqualTo("Publish my media changes");
                WebElement disablePublishLinkMedia = driver.findElement(By.xpath("(//span[text()='Publish my media changes']|//button[text()='Publish my media changes'])/.."));
                String actDisablePublishLinkMedia = disablePublishLinkMedia.getAttribute("class");
                String expDisablePublishLink = "ui secondary disabled button";
                softly().assertThat(actDisablePublishLinkMedia).as("Publish my media changes button is not disable.").isEqualTo(expDisablePublishLink);
                break;
            case "LINKS & PROFILES":
                softly().assertThat(getPublishMyLinksAndProfilesChangesButton().getText()).as("Publish my LINKS & PROFILES changes button is not displaying.").isEqualTo("Publish intro changes");
                WebElement disablePublishLinkLinks = driver.findElement(By.xpath("//button[text()='Publish my links']|//span[text()='Publish my links & profiles changes']/.."));
                String actDisablePublishLinkLinks = disablePublishLinkLinks.getAttribute("class");
                String expDisablePublishLinkLinks = "ui secondary disabled button";
                softly().assertThat(actDisablePublishLinkLinks).as("Publish my links & profiles changes button is not disable.").isEqualTo(expDisablePublishLinkLinks);
                profilesLink().click();
                softly().assertThat(createNewProfileButton().getText()).as("CREATE A NEW PROFILE Button is not displaying.").isEqualTo("CREATE A NEW PROFILE");
                break;
            case "INTRO" :
                softly().assertThat(getPublishIntroChangesButton().getText()).as("Publish intro changes button is not displaying.").isEqualTo("Publish intro changes");
                WebElement disablePublishLinkIntro = driver.findElement(By.xpath("//button[text()='Publish intro changes']|//span[text()='Publish intro changes']/.."));
                String actDisablePublishLinkIntro = disablePublishLinkIntro.getAttribute("class");
                String expDisablePublishLinkIntro = "ui secondary disabled button";
                softly().assertThat(actDisablePublishLinkIntro).as("Publish my media changes button is not disable.").isEqualTo(expDisablePublishLinkIntro);
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
        softly().assertThat(getContinueEditingButton().getText()).as("Continue Editing button is not displaying.").isEqualTo("Continue editing");
        softly().assertThat(getDisregardMyChangesButton().getText()).as("Disregard my changes button is not displaying.").isEqualTo("Disregard my changes");
        softly().assertThat(getPublishButton().getText()).as("Publish button is not displaying.").isEqualTo("Publish");

        getContinueEditingButton().click();

        clickOnHubsMenuTab("BASIC INFO");
        getPublishButton().click();

        softly().assertThat(driver.findElement(By.xpath("//span[text()='You’re almost done!']")).getText()).as("You’re almost done! text is not displaying")
                .isEqualTo("You’re almost done!");

        String actText1 = driver.findElement(By.xpath("//span[contains(text(), 'The following items you')]")).getText();
        String expText1 = "The following items you've updated need approval by Hobsons.";
        softly().assertThat(actText1).as(expText1+ "Text is not displaying.").isEqualTo(expText1);

        softly().assertThat(driver.findElement(By.xpath("//span[text()= 'The approval process can take up to 24-48 hours.']")).getText())
                .as("The approval process can take up to 24-48 hours. Text is not displaying.").isEqualTo("The approval process can take up to 24-48 hours.");

        if (menuTab.equals("MEDIA")) {
            String actText2 = driver.findElement(By.xpath("//span[contains(text(),'Media - Photos')]")).getText();
            String expText2 = "Media - Photos & Videos, Logo";
            softly().assertThat(actText2).as(expText1+ " Text is not displaying.").isEqualTo(expText2);
        } else if (menuTab.equals("LINKS & PROFILES")){
            String actText3 = driver.findElement(By.xpath("(//span[contains(text(),'Links ')])[2]")).getText();
            String expText3 = "Links & Profiles - Links, Profiles";
            softly().assertThat(actText3).as(expText3+ " Text is not displaying.").isEqualTo(expText3);
        }

        softly().assertThat(driver.findElement(By.xpath("//span[text()='Please provide a brief explanation for the changes:']")).getText()).as("Please provide a brief explanation for the changes: text is not displaying.")
                .isEqualTo("Please provide a brief explanation for the changes:");

        softly().assertThat(getCancelAndContinueEditingButton().getText()).as("Cancel And Continue Editing is not displaying.")
                .isEqualTo("Cancel and continue editing");
        softly().assertThat(getSubmitChangesButton().getText()).as("Submit changes button is not displaying.")
                .isEqualTo("Submit changes");

        getCancelAndContinueEditingButton().click();
        clickOnHubsMenuTab("BASIC INFO");
        getPublishButton().click();
        getSubmitChangesButton().click();
    }

    public void selectImageForLogo(){
        logo().click();
        String logoPath = System.getProperty("user.dir") + "/src/bddTest/java/utilities/Logo.jpg";
        selectAnImageToUpload().sendKeys(logoPath);
        softly().assertThat(backButton().getText()).as("Back button is not displaying in Publish model.").isEqualTo("Back");
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
