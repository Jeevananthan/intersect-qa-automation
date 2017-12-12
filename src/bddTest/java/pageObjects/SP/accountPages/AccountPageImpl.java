package pageObjects.SP.accountPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
    }

    public void setModuleStatusAsActiveOrInActiveWithDate(String moduleName, String status ,String startDate, String endDate){
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//input[@aria-label='quote charge id']")).sendKeys(Keys.PAGE_DOWN);
        WebElement ActualStatus=driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td//div/div"));
        waitUntilElementExists(ActualStatus);
        WebElement SelectStatus=driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td//div/div/div/span[text()='"+status+"']"));
         if(!status.equals(ActualStatus.getText())){
            waitUntilElementExists(ActualStatus);
            ActualStatus.click();
            SelectStatus.click();}

          if(!startDate.equals("")) {
              WebElement StartDateButton = driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td[3]/button/i"));
              StartDateButton.click();
              String StartDate=getSpecificDate(startDate);
              setStartDateInAccountPage(StartDate, moduleName);
              StartDateButton.click();
          }
          if(!endDate.equals("")) {
              WebElement EndDateButton = driver.findElement(By.xpath("//table[@class='ui celled striped table']//tbody//tr//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td[4]/button/i"));
              EndDateButton.click();
              String EndDate=getSpecificDate(endDate);
              setEndDateInAccountPage(EndDate, moduleName);
              EndDateButton.click();
          }
        }

    public String getSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    private void doubleClick(WebElement elementLocator) {
        Actions actions = new Actions(driver);
        actions.doubleClick(elementLocator).perform();
    }

    public void setStartDateInAccountPage(String dateToSet, String moduleName){
        String[] parts = dateToSet.split(" ");
        String month = parts[0];
        String day = parts[1];
        String year = parts[2];

        WebElement selectyear=driver.findElement(By.id("year-select"));
        doubleClick(selectyear);
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        WebElement selectmonth=driver.findElement(By.id("month-select"));
        doubleClick(selectmonth);
        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+day+"']"));
        dateTemp.click();

    }

    public void setEndDateInAccountPage(String dateToSet, String moduleName){
        String[] parts = dateToSet.split(" ");
        String month = parts[0];
        String day = parts[1];
        String year = parts[2];

        driver.findElement(By.id("year-select")).click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        driver.findElement(By.id("month-select")).click();
        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+day+"']"));
        dateTemp.click();
    }

    public void clicksaveChangesButton(){
        try{
        getSaveChangesButton().click();
        waitUntilPageFinishLoading();}catch (Exception e){}
    }

    public void verifyEndDateFeasibility(){
        String startDate = getHubModuleRow().findElement(By.className("_1dCEyx-42op_-Pf0-ie2T")).getText();
        String month = startDate.substring(0, 3);
        String dateNo = startDate.substring(4, 6);
        String year = startDate.substring(8, 12);
        getEndDateButton().click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);
        int dateNumber = Integer.parseInt(dateNo);
        --dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("End date which is earlier then start date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getEndDateButton().click();
    }

    public void verifyStartDateFeasibility(){
        String endDate = getHubModuleRow().findElement(By.xpath("(//td[@class='_1dCEyx-42op_-Pf0-ie2T'])[2]")).getText();
        String month = endDate.substring(0, 3);
        String dateNo = endDate.substring(4, 6);
        String year = endDate.substring(8, 12);
        getStartDateButton().click();
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);
        int dateNumber = Integer.parseInt(dateNo);
        ++dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("Start date which is past then end date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getStartDateButton().click();
    }

    //Getters
    private WebElement getEndDateButton(){ return driver.findElement(By.xpath("(//button[@role='tooltip'])[2]")); }
    private WebElement getStartDateButton(){ return driver.findElement(By.xpath("(//button[@role='tooltip'])[1]")); }
    private WebElement getHubModuleRow(){ return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[1]")); }
    private WebElement getCommunityModuleRow(){return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[2]")); }
    private WebElement getIntersectAwarnessModuleRow(){return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[3]")); }
    private WebElement getIntersectPresenceModuleRow(){return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[4]")); }


    public void verifyModuleDetails(String status,String startDate,String endDate,String module){
        String ActualStatus;
           switch (module){

               case "Legacy: Hub page management":
                   if(!status.equals("")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                   }
                   if(!startDate.equals("")){
                       String ActualStartDate = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                       String StartDate=specificDate(startDate);
                       Assert.assertTrue("Expected Start date is not displayed",ActualStartDate.equalsIgnoreCase(StartDate));
                   }
                   if(!endDate.equals("")){
                       String ActualEndDate = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                       String EndDate=specificDate(endDate);
                       Assert.assertTrue("Expected End date is not displayed",ActualEndDate.equalsIgnoreCase(EndDate));

                   }
                   break;


               case "Legacy: Community":
                   if(!status.equals("")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                   }
                   if(!startDate.equals("")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[4]")).getText();
                       String StartDate=specificDate(startDate);
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(StartDate));
                   }
                   if(!endDate.equals("")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[5]")).getText();
                       String EndDate=specificDate(endDate);
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(EndDate));

                   }
                   break;

               case "Intersect Awareness Subscription":
                   if(!status.equals("")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                   }
                   if(!startDate.equals("")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[4]")).getText();
                       String StartDate=specificDate(startDate);
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(StartDate));
                   }
                   if(!endDate.equals("")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[5]")).getText();
                       String EndDate=specificDate(endDate);
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(EndDate));

                   }
                   break;

               case "Intersect Presence Subscription":
                   if(!status.equals("")) {
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                       Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                   }
                   if(!startDate.equals("")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                       String StartDate=specificDate(startDate);
                       Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(StartDate));
                   }
                   if(!endDate.equals("")){
                       ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                       String EndDate=specificDate(endDate);
                       Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(EndDate));

                   }
                   break;

           }

    }

    public String specificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }


    private WebElement getCalender(){ return driver.findElement(By.xpath("//div[@role='application']")); }
    private WebElement getSaveChangesButton(){
        return button("Save Changes");
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

}
