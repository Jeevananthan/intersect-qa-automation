package pageObjects.SP.AdminDashboard;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductAnnouncementsImpl extends PageObjectFacadeImpl {
    public ProductAnnouncementsImpl(){}
    /**
     * Goes to the Product Announcements menu
     */
    public void goToProductAnnouncements(){
        navBar.goToAdminDashboard();
        getProductAnnouncementsMenu().click();
        waitUntil(ExpectedConditions.visibilityOf(getProductAnnouncementsLabel()));
    }

    /**
     * Adds a new product announcement
     * @param title
     * @param content
     * @param audience
     * @param status
     */
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

    /**
     * Verifies the error message when adding a product announcement with errors
     * @param title
     * @param message
     */
    public void verifyProductAnnouncementErrorMessage(String title, String message){
        String actualTitle = getProductAnnouncementErrorMessage().findElement(
                By.xpath("//div[@class='header _2YR9-dFGlIIisedNH5Jeu3']/span")).getAttribute("innerText");
        String actualMessage = getProductAnnouncementErrorMessage().findElement(
                By.xpath("//ancestor::div[@class='header _2YR9-dFGlIIisedNH5Jeu3']/following-sibling::span"))
                .getAttribute("innerText");
        Assert.assertEquals(String.format("The title of the error message is incorrect, expected: %s, actual: %s",
                title,actualTitle),title,actualTitle);
        Assert.assertEquals(String.format("The message of the error message is incorrect, expected: %s, actual: %s",
                message,actualMessage),message,actualMessage);
    }

    /**
     * Selects a given audience in the Audience dropdown
     * @param audiences
     */
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

    /**
     * Verifies the required tooltip in the given field
     * @param field
     * @param message
     */
    public void verifyErrorFieldTooltipForProductAnnouncementField(String field, String message){
        String actualMessage;
        switch (field.toLowerCase()){

            case "content":
                actualMessage = getProductAnnouncementContentField().findElement(By.xpath(
                        "//following-sibling::div[@role='tooltip']/span")).getAttribute("innerText");
                Assert.assertEquals(String.format("The tooltip message is not correct, expected: %s, actual: %s"
                        ,message,actualMessage), message, actualMessage);
                break;
            case "audience":
                actualMessage = getProductAnnouncementAudienceDropdown().findElement(By.xpath(
                        "//following-sibling::div[@role='tooltip']/span")).getAttribute("innerText");
                Assert.assertEquals(String.format("The tooltip message is not correct, expected: %s, actual: %s"
                        ,message,actualMessage), message, actualMessage);
                break;
            case "status":
                actualMessage = driver.findElement(By.xpath(
                        "//div[@class='ui checked radio checkbox yqtg9wJj2OswMfKalbNut']/ancestor::div[@class='field']/following-sibling::div/span"))
                        .getAttribute("innerText").replace("\"","");
                Assert.assertEquals(String.format("The tooltip message is not correct, expected: %s, actual: %s"
                        ,message,actualMessage), message, actualMessage);
        }
    }

    /**k
     * Verifies that the save/update message is according to the given text
     * @param text to ve verified
     */
    public void verifySaveUpdateConfirmationToast(String text){
        String actualMessage = getSaveUpdateConfirmationToast().getAttribute("innerText");
        Assert.assertEquals(String.format("The save/updated confirmation message is not correct, expected: %s, actual: %s"
                ,text,actualMessage),text,actualMessage);
    }

    /**
     * Verifies all the data of a given announcement, in product announcements list
     * @param title
     * @param content
     * @param visibility
     * @param date
     * @param user
     * @param status
     */
    public void verifyProductAnnouncementInList(String title, String content, String visibility, String date,
                                                String user, String status){
        if(date.equalsIgnoreCase("today")){
            Format formatter = new SimpleDateFormat("MMM d");
            date = formatter.format(new Date());
        }
        WebElement announcement = driver.findElement(By.xpath(String.format(
                "//div[@class='twelve wide column']/a[text()='%s']/ancestor::div[@class='ui grid _3E9gDLAhuHSRcSf2bDO-Q8']"
                ,title)));
        String actualContent = announcement.findElement(By.xpath(
                "descendant::div[@class='thirteen wide column _2YyAyZ3Fea-tgSqFGIhqfH']")).getAttribute("innerText");
        String actualVisibility =  announcement.findElement(By.xpath(
                "descendant::div[@class='fifteen wide column _1debTVMmEqqRfth9ob_Ahd _3U5Yi5eIC11jRw7Fq3hyIl']"))
                .getAttribute("innerText");
        String actualUser = announcement.findElement(By.xpath(
                "descendant::div[@class='fifteen wide column _1debTVMmEqqRfth9ob_Ahd _3U5Yi5eIC11jRw7Fq3hyIl']/span[not(@class)]/" +
                        "ancestor::div[@class='fifteen wide column _1debTVMmEqqRfth9ob_Ahd _3U5Yi5eIC11jRw7Fq3hyIl']"))
                .getAttribute("innerText");
        String actualDate = announcement.findElement(By.xpath(
                "descendant::div[@class='fifteen wide column _1debTVMmEqqRfth9ob_Ahd _3U5Yi5eIC11jRw7Fq3hyIl']/span[not(@class)]"))
                .getAttribute("innerText");
        String actualStatus = announcement.findElement(By.xpath(
                "descendant::div[@class='three wide column _2KojA-0mnkfX2NNlHlJw8R _3U5Yi5eIC11jRw7Fq3hyIl']"))
                .getAttribute("innerText");
        Assert.assertEquals(String.format("The content is not correct, expected: %s, actual: %s",content,actualContent)
                ,content, actualContent);
        Assert.assertTrue(String.format("The visibility is not correct, expected: %s, actual: %s",visibility,actualVisibility)
                ,actualVisibility.contains(visibility));
        Assert.assertTrue(String.format("The user is not correct, expected: %s, actual: %s",user,actualUser)
                ,actualUser.contains(user));
        Assert.assertEquals(String.format("The date is not correct, expected: %s, actual: %s",date,actualDate)
                ,date, actualDate);
        Assert.assertEquals(String.format("The status is not correct, expected: %s, actual: %s",status,actualStatus)
                ,status, actualStatus);
    }

    /**
     * Verifies if a label with the given text exists
     * @param text
     */
    public void verifyLabel(String text){
        try{
            driver.findElement(By.xpath(String.format("//span[text()='%s']",text)));
        } catch (Exception e){
            Assert.fail(String.format("The label with text: %s is not displayed",text));
        }
    }

    /**
     * Edits a product announcemet
     * @param oldTitle
     * @param title
     * @param content
     * @param audience
     * @param status
     */
    public void editProductAnnouncement(String oldTitle, String title, String content,String audience, String status){
        goToProductAnnouncements();
        driver.findElement(By.xpath(String.format("//a[text()='%s']",oldTitle))).click();
        waitUntil(ExpectedConditions.visibilityOf(getAddEditNewAnnouncementLabel()));
        getProductAnnouncementTitleField().clear();
        getProductAnnouncementTitleField().sendKeys(title);
        getProductAnnouncementContentField().clear();
        getProductAnnouncementContentField().sendKeys(content);
        selectProductAnnouncementAudience(audience);
        selectProductAnnouncementStatus(status);
        getSaveProductAnnouncementButton().click();
    }

    /**
     * Un publishes all the published announcements
     */
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

    /**
     * Selects the given status in the "Status" radio button
     * @param status
     */
    private void selectProductAnnouncementStatus(String status){
        jsClick(driver.findElement(By.xpath(String.format("//input[@value='%s']",status))));
    }

    /**
     * Gets the product announcements menu
     * @return WebElement
     */
    private WebElement getProductAnnouncementsMenu(){
        return driver.findElement(By.xpath("//a/span[text()='Product Announcements']"));
    }

    /**
     * Gets the product announcements label
     * @return WebElement
     */
    private WebElement getProductAnnouncementsLabel(){
        return driver.findElement(By.cssSelector("span._1uigi5uYceibu47RJkqX7x"));
    }

    /**
     * Gets the add new announcement button
     * @return WebElement
     */
    private WebElement getAddNewProductAnnouncementButton(){
        return driver.findElement(By.cssSelector("button[class='ui button y7CoXm2yv7vck-Sl-DeDD']"));
    }

    /**
     * Gets the add new announcement label
     * @return
     */
    private WebElement getAddEditNewAnnouncementLabel(){
        return driver.findElement(By.cssSelector("div._3HZ9v_s6tN986jefmuL2NL"));
    }

    /**
     * Gets the product announcement title text box
     * @return WebElement
     */
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

    /**
     * Gets the product announcement error message
     * @return WebElement
     */
    private WebElement getProductAnnouncementErrorMessage(){
        return driver.findElement(By.cssSelector("div[class='content']"));
    }

    /**
     * Gets the save update confirmation toast
     * @return WebElement
     */
    private WebElement getSaveUpdateConfirmationToast(){
        return driver.findElement(By.xpath(
                "//div[@class='ui small icon success message toast _2Z22tp5KKn_l5Zn5sV3zxY']/div/span[not(@class)]"));
    }
}
