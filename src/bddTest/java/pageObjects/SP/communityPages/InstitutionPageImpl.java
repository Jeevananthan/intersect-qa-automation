package pageObjects.SP.communityPages;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InstitutionPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public InstitutionPageImpl() { logger = Logger.getLogger(InstitutionPageImpl.class);    }

    public void goToHubsPage(String collegeName){
        waitUntilPageFinishLoading();
        communityFrame();
        WebElement additionalLink = link("Additional info");
        waitUntil(ExpectedConditions.visibilityOf(additionalLink));
        additionalLink.click();
        waitUntilPageFinishLoading();
        WebElement viewNavianceCollegeProfile = link("VIEW NAVIANCE COLLEGE PROFILE");
        waitUntil(ExpectedConditions.visibilityOf(viewNavianceCollegeProfile));
        viewNavianceCollegeProfile.click();
        waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        waitForUITransition();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        waitForUITransition();
        try{
            waitUntil(ExpectedConditions.visibilityOf(collageNameLabel()));
            waitUntil(ExpectedConditions.textToBePresentInElement(collageNameLabel(),collegeName));

        }catch(Exception e){
            logger.info("Caught Exception: " + e.getMessage());
            getDriver().switchTo().defaultContent();
            throw new AssertionFailedError("College Name is not displaying in Hubs View");
        }
        getDriver().switchTo().defaultContent();
    }

    public void unpublishAllAnnouncements(){
        goToProductAnnouncements();
        List<WebElement> publishedLabels = driver.findElements(
                By.xpath("//div[@class='three wide column _2KojA-0mnkfX2NNlHlJw8R _3U5Yi5eIC11jRw7Fq3hyIl' and text()='Published']"));
        List<String> announcements = new ArrayList<>();
        for(WebElement announcement : publishedLabels){
            announcements.add(announcement.findElement(By.xpath(
                    "preceding-sibling::div/a[@aria-label='Edit Announcement']")).getAttribute("innerText"));
        }
        for(String announcement : announcements){
            editProductAnnouncement(announcement,announcement,"","","Unpublished");
        }
    }
    public void goToProductAnnouncements(){
        goToAdminDashboard();
        getProductAnnouncementsMenu().click();
        waitUntil(ExpectedConditions.visibilityOf(getProductAnnouncementsLabel()));
    }

    public void editProductAnnouncement(String oldTitle, String title, String content,String audience, String status){
        goToProductAnnouncements();
        driver.findElement(By.xpath(String.format("//a[contains(text(),'%s')]",oldTitle))).click();
        waitUntil(ExpectedConditions.visibilityOf(getAddEditNewAnnouncementLabel()));
        getProductAnnouncementTitleField().clear();
        getProductAnnouncementTitleField().sendKeys(title);
        getProductAnnouncementContentField().clear();
        getProductAnnouncementContentField().sendKeys(content);
        selectProductAnnouncementAudience(audience);
        selectProductAnnouncementStatus(status);
        getSaveProductAnnouncementButton().click();
    }
    public void goToAdminDashboard(){
        getAdminDashboardLink().click();
        waitUntil(ExpectedConditions.visibilityOf(getAdminDashboardLabel()));
    }

    public void addNewProductAnnouncement(String title, String content,String audience, String status){
        goToProductAnnouncements();
        getAddNewProductAnnouncementButton().click();
        waitUntil(ExpectedConditions.visibilityOf(getAddEditNewAnnouncementLabel()));
        getProductAnnouncementTitleField().sendKeys(title);
        getProductAnnouncementContentField().sendKeys(content);
        selectProductAnnouncementAudience(audience);
        selectProductAnnouncementStatus(status);
        getSaveProductAnnouncementButton().click();
    }

    public void verifySaveUpdateConfirmationToast(String text){
        String actualMessage = getSaveUpdateConfirmationToast().getAttribute("innerText");
        Assert.assertEquals(String.format("The save/updated confirmation message is not correct, expected: %s, actual: %s"
                ,text,actualMessage),text,actualMessage);
    }

    public void verifyTitleInProductAnnouncements(String title){
        Assert.assertTrue("Announcement title is not displayed",driver.findElement(By.xpath("//div/a[contains(text(),'"+title+"')]")).isDisplayed());
    }

    public void verifyCharCountsInProductAnnouncements(String charCount){
        String displayingValue = driver.findElement(By.xpath("//div[normalize-space(@class)='thirteen wide column _2YyAyZ3Fea-tgSqFGIhqfH']")).getText();
        int length = displayingValue.length();
        String originalValue = Integer.toString(length);
        Assert.assertTrue("Content length is not equal to "+charCount,charCount.equals(originalValue));
    }

    public void verifyVisibilityInProductAnnouncements(String audience,String date){
        String currentDate = getMonthAndDate(date,"");
        Assert.assertTrue("Visibility text is not displayed",driver.findElement(By.xpath("//span[text()='Visibility: ']")).isDisplayed());
        Assert.assertTrue("Audience are not displayed",driver.findElement(By.xpath("//div/span[text()='"+currentDate+"']/ancestor::div//div[text()='"+audience+"']")).isDisplayed());
    }

    public void verifyDateFormatInProductAnnouncements(String dateFormat,String date,String user){
        String currentDate = getMonthAndDate(date,dateFormat);
        Assert.assertTrue("Date format is not displayed",driver.findElement(By.xpath("//div/span[text()='"+currentDate+"']/parent::div[text()='"+user+"']")).isDisplayed());
    }

    public void verifyStatusInProductAnnouncements(String status,String date){
        String currentDate = getMonthAndDate(date,"");
        Assert.assertTrue("",driver.findElement(By.xpath("")).isDisplayed());
    }

    private void selectProductAnnouncementAudience(String audiences){
        if(audiences!=null && !audiences.isEmpty()) {
            String[] audienceList = audiences.split(",");
            getProductAnnouncementAudienceDropdown().click();
            for (String audience : audienceList) {
                if (audience != null && !audience.isEmpty()) {
                    waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
                            "//div[@role='option']/span[text()='%s']", audience))));
                    driver.findElement(By.xpath(String.format(
                            "//div[@role='option']/span[text()='%s']", audience))).click();
                }
            }
        }
    }

    private String getMonthAndDate(String addDays,String format){
        String DATE_FORMAT_NOW = "d MMM";
        if(format != null)
            DATE_FORMAT_NOW = format;
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    private void selectProductAnnouncementStatus(String status){
        jsClick(driver.findElement(By.xpath(String.format("//input[@value='%s']",status))));
    }

    //locator
        private WebElement collageNameLabel() {
            return getDriver().findElement(By.cssSelector("h1.masthead__name"));
        }

    private WebElement getAdminDashboardLink(){
        return driver.findElement(By.id("js-main-nav-admin-menu-link"));
    }
    private WebElement getAdminDashboardLabel(){
        return driver.findElement(By.cssSelector("h1._2uZ_hMKXaU0AzfCZMfjh1t"));
    }
    private WebElement getProductAnnouncementsMenu(){
        return driver.findElement(By.xpath("//a/span[text()='Product Announcements']"));
    }
    private WebElement getProductAnnouncementTitleField(){
        return driver.findElement(By.id("create-announcement-title"));
    }

    /**
     * Gets the product announcement content field
     * @return WebElement
     */
    private WebElement getProductAnnouncementContentField(){
        return driver.findElement(By.id("create-announcement-content"));
    }

    /**
     * Gets the product audience dropdown
     * @return WebElement
     */
    private WebElement getProductAnnouncementAudienceDropdown(){
        return driver.findElement(By.cssSelector("div[name='audience']"));
    }

    /**
     * Gets the save button
     * @return WebElement
     */
    private WebElement getSaveProductAnnouncementButton(){
        return driver.findElement(By.cssSelector("button[class='ui primary button _2NItmyxMCEfAor9AvVaDKL']"));
    }
    private WebElement getAddEditNewAnnouncementLabel(){
        return driver.findElement(By.cssSelector("div._3HZ9v_s6tN986jefmuL2NL"));
    }
    private WebElement getProductAnnouncementsLabel(){
        return driver.findElement(By.cssSelector("span._1uigi5uYceibu47RJkqX7x"));
    }
    private WebElement getAddNewProductAnnouncementButton(){
        return driver.findElement(By.cssSelector("button[class='ui button y7CoXm2yv7vck-Sl-DeDD']"));
    }
    private WebElement getSaveUpdateConfirmationToast(){
        return driver.findElement(By.xpath(
                "//div[@class='ui small icon success message toast _2Z22tp5KKn_l5Zn5sV3zxY']/div/span[not(@class)]"));
    }
}
