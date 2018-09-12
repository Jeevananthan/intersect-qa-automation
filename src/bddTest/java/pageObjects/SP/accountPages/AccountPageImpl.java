package pageObjects.SP.accountPages;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class AccountPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    UserListPageImpl Implobj=new UserListPageImpl();

    public AccountPageImpl() {
        logger = Logger.getLogger(AccountPageImpl.class);
    }

    public void verifyImOnAnInstitutionPage() {
        waitUntilPageFinishLoading();
        Assert.assertTrue(text("Client:").isDisplayed());
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
        selectMonth.selectByVisibleText(selectMonth(month));
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
        selectMonth.selectByVisibleText(selectMonth(month));
        int dateNumber = Integer.parseInt(dateNo);
        ++dateNumber;
        dateNo = String.valueOf(dateNumber);
        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        Assert.assertTrue("Start date which is past then end date is enabled",Boolean.parseBoolean(dateTemp.getAttribute("aria-disabled")));
        getStartDateButton().click();
    }

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
                    Assert.assertTrue("Expected Start date is not displayed",ActualStartDate.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    String ActualEndDate = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualEndDate.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;


            case "Legacy: Community":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[2]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;

            case "Intersect Awareness Subscription":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[3]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;

            case "Intersect Presence Subscription":
                if(!status.equals("")) {
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/div[@role='listbox']/div")).getText();
                    Assert.assertTrue("Expected status is not displayed",ActualStatus.equalsIgnoreCase(status));
                }
                if(!startDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
                    Assert.assertTrue("Expected Start date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(startDate)))));
                }
                if(!endDate.equals("")){
                    ActualStatus = driver.findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
                    Assert.assertTrue("Expected End date is not displayed",ActualStatus.equalsIgnoreCase(dateCompareFormatter(getDeltaDate(Integer.parseInt(endDate)))));

                }
                break;
        }
    }

    private String dateCompareFormatter(Calendar cal){
        String moduleSubscriptionDateFormat = "MMM d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(moduleSubscriptionDateFormat);
        return sdf.format(cal.getTime());
    }

    //Getters
    private WebElement getEndDateButton(){ return driver.findElement(By.xpath("//button[@aria-label='End Date Calendar']")); }
    private WebElement getStartDateButton(){ return driver.findElement(By.xpath("//button[@aria-label='Start Date Calendar']")); }
    private WebElement getHubModuleRow(){ return driver.findElement(By.xpath("//table[@class='ui celled striped table']/tbody/tr[1]")); }
    private WebElement getCalender(){ return driver.findElement(By.xpath("//div[@role='application']")); }
    private WebElement getSaveChangesButton(){ return button("Save Changes"); }

    public String selectMonth(String month)
    {
        String selectedMonth="";
        List<String> monthValue=new ArrayList<>();
        monthValue.add("January");
        monthValue.add("February");
        monthValue.add("March");
        monthValue.add("April");
        monthValue.add("May");
        monthValue.add("June");
        monthValue.add("July");
        monthValue.add("August");
        monthValue.add("September");
        monthValue.add("October");
        monthValue.add("November");
        monthValue.add("December");
        for(String Month:monthValue)
        {
           String actualMonth=Month.substring(0,3);
            if(month.contains(actualMonth))
            {
                selectedMonth=Month;
                break;
            }
        }
        return selectedMonth;
    }

    public void verifyCreatePrimaryUser() {
        Assert.assertTrue("Create User is not displayed",driver.findElement(By.xpath("//span[text()='Create User']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='Create User']")).click();
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

    public void clicksaveChangesButton(){
        if(getSaveChangesButton().isDisplayed()) {
            getSaveChangesButton().click();
        }
        waitUntilPageFinishLoading();
    }



    public void setModuleStatusAsActiveOrInActive(String moduleName, String status){
         WebElement actualStatus = getLocatorforSubscription(moduleName);
         if(!actualStatus.getText().equalsIgnoreCase(status)){
            actualStatus.click();
            WebElement dropDownItem = driver.findElement(By.xpath("//div[@class='menu transition visible']//span[text()='"+status+"']"));
            driver.executeScript("arguments[0].click();",dropDownItem);
            if(!status.equalsIgnoreCase("inactive")) {
                WebElement StartDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='Start Date Calendar']"));
                WebElement EndDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='End Date Calendar']"));


                StartDateButton.click();
                setStartDateInModulePage();
                StartDateButton.click();
                EndDateButton.click();
                setEndDateInModulePage();
                EndDateButton.click();
            }
            clicksaveChangesButton();
        }
    }

    public WebElement getLocatorforSubscription(String moduleName){
        WebElement result = null;
        List <WebElement>  listOfSubscriptions  = driver.findElements(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/div/div[@role='alert']"));

        if (listOfSubscriptions.size()== 0)
        {
            result = driver.findElement(By.xpath("//td/a/span[text()='"+moduleName+"']/../../../td/div[@class= 'ui floating inline dropdown']"));
        }
        else {
             result = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/div/div[@role='alert']"));

        }
            return  result;
    }

    public void setModuleStatusAsActiveOrInActiveWithDate(String moduleName, String status, String startDateDelta, String endDateDelta){
        WebElement actualStatus = null;
        if(moduleName.equals("Advanced Awareness")){
            actualStatus = driver.findElement(By.xpath("//td/a/span[text()='"+moduleName+"']/ancestor::td/following-sibling::td/div/div[@role='alert']"));
        }else {
             actualStatus = driver.findElement(By.xpath("//td/span[text()='" + moduleName + "']/parent::td/following-sibling::td/div/div[@role='alert']"));
        }
        if(!actualStatus.getText().equalsIgnoreCase(status)){
            actualStatus.click();
            WebElement dropDownItem = driver.findElement(By.xpath("//div[@class='menu transition visible']//span[text()='"+status+"']"));
            driver.executeScript("arguments[0].click();",dropDownItem);
            Actions actions = new Actions(getDriver());
            if(!status.equalsIgnoreCase("inactive")) {
                WebElement StartDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='Start Date Calendar']"));
                WebElement EndDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='End Date Calendar']"));

                StartDateButton.click();
                setStartDate(startDateDelta);
                actions.sendKeys(Keys.ESCAPE).build().perform();
                EndDateButton.click();
                setEndDate(endDateDelta);
                actions.sendKeys(Keys.ESCAPE).build().perform();
            }
        }
    }

    public void setStartDate(String delta){
        Calendar cal = getDeltaDate(Integer.parseInt(delta));

        String month = getMonth(cal);
        String dateNo = getDay(cal);
        if (dateNo.startsWith("0"))
            dateNo = dateNo.substring(1);
        String year = getYear(cal);
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();
    }

    public void setEndDate(String delta){
        Calendar cal = getDeltaDate(Integer.parseInt(delta));

        String month = getMonth(cal);
        String dateNo = getDay(cal);
        if (dateNo.startsWith("0"))
            dateNo = dateNo.substring(1);
        String year = getYear(cal);

        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();
    }

    public void setStartDateInModulePage(String... startDateArray){
        String startDate;
        if (startDateArray == null || startDateArray.length==0) {
            startDate = "June 13, 2017";
        } else {
            startDate = startDateArray[0];
        }
        String month = startDate.substring(0, 4);
        String dateNo = startDate.substring(5, 7);
        String year = startDate.substring(9, 13);

        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();
    }

    public void setEndDateInModulePage(String... endDateArray){
        String endDate;
        if (endDateArray == null || endDateArray.length==0) {
            endDate = "June 13, 2020";
        } else {
            endDate = endDateArray[0];
        }
        String month = endDate.substring(0, 4);
        String dateNo = endDate.substring(5, 7);
        String year = endDate.substring(9, 13);

        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();
    }

    public void addUserAccount(String firstName,String lastName,String email,String verifyEmail,String userName) {
        String value[] = email.split("@");
        String RandomValue = generateRandomNumber();
        String EMail = value[0]+"+"+RandomValue+"@"+value[1];
        if (!firstName.equals("")) {
            Assert.assertTrue("firstName textbox is not displayed", driver.findElement(By.xpath("//input[@id='create-primary-user-first-name']")).isDisplayed());
            driver.findElement(By.xpath("//input[@id='create-primary-user-first-name']")).sendKeys(firstName);
        }
        if (!lastName.equals("")) {
            Assert.assertTrue("lastName textbox is not displayed", driver.findElement(By.xpath("//input[@id='create-primary-user-last-name']")).isDisplayed());
            driver.findElement(By.xpath("//input[@id='create-primary-user-last-name']")).sendKeys(lastName);
        }
        if (!email.equals("")) {
            Assert.assertTrue("email textbox is not displayed", driver.findElement(By.xpath("//input[@id='create-primary-user-email']")).isDisplayed());
            driver.findElement(By.xpath("//input[@id='create-primary-user-email']")).sendKeys(EMail);
        }
        if (!verifyEmail.equals("")) {
            Assert.assertTrue("verify email textbox is not displayed", driver.findElement(By.xpath("//input[@id='create-primary-user-verify-email']")).isDisplayed());
            driver.findElement(By.xpath("//input[@id='create-primary-user-verify-email']")).sendKeys(EMail);
        }
        Assert.assertTrue("Administrator option is not displayed",driver.findElement(By.xpath("//span[text()='Administrator (All access)']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='Administrator (All access)']")).click();
        Assert.assertTrue("Save is not displayed",saveButtonInAddUser().isDisplayed());
        saveButtonInAddUser().click();
        waitUntilPageFinishLoading();
        WebElement seeAllUser = driver.findElement(By.xpath("//span[text()='See All Users']"));
        waitUntilElementExists(seeAllUser);
        Assert.assertTrue("See All Users is not displayed",seeAllUser.isDisplayed());
        seeAllUser.click();

        if (!userName.equals("")) {
        Implobj.verifyUserIsNotPrimary(EMail);
        Implobj.setPrimaryUser(EMail);
        Implobj.verifyUserIsPrimary(EMail);
        }

    }

    public void verifyYearInInstitutionCalendarPage(String year,String module,String startDate,String endDate){
        waitUntilPageFinishLoading();
        WebElement endDateButton = driver.findElement(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td/button[@aria-label='End Date Calendar']"));
        jsClick(endDateButton);
        waitUntilPageFinishLoading();
        selectYearInInstitutionPage().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+year+"']")).isDisplayed());
        driver.findElement(By.xpath("//select/option[@value='"+year+"']")).click();
        driver.findElement(By.xpath("//select/option[@value='"+year+"']")).click();
        WebElement selectEndDate = driver.findElement(By.xpath("//div[@class='DayPicker-Day' and text()='"+endDate+"']"));
        jsClick(selectEndDate);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td[4]/span")));
        String displayingEndDateValue = driver.findElement(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td[4]/span")).getText();
        String endDateValue[] = displayingEndDateValue.split(" ");
        String displayingEndDateYear = endDateValue[2];
        Assert.assertTrue("Year is not displayed",year.equals(displayingEndDateYear));
        jsClick(endDateButton);
        WebElement startDateButton = driver.findElement(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td/button[@aria-label='Start Date Calendar']"));
        jsClick(startDateButton);
        waitUntilPageFinishLoading();
        selectYearInInstitutionPage().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+year+"']")).isDisplayed());
        driver.findElement(By.xpath("//select/option[@value='"+year+"']")).click();
        driver.findElement(By.xpath("//select/option[@value='"+year+"']")).click();
        WebElement selectStartDate = driver.findElement(By.xpath("//div[@class='DayPicker-Day' and text()='"+startDate+"']"));
        jsClick(selectStartDate);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td/span")));
        String displayingStartDateValue = driver.findElement(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td/span")).getText();
        String startDateValue[] = displayingStartDateValue.split(" ");
        String displayingStartDateYear = startDateValue[2];
        Assert.assertTrue("Year is not displayed",year.equals(displayingStartDateYear));
        jsClick(startDateButton);
    }

    public void verifySelectedDateColorInInstitutionCalendarPage(String color,String startDate,String endDate,String module){
        WebElement startDateButton = driver.findElement(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td/button[@aria-label='Start Date Calendar']"));
        startDateButton.click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected' and text()='"+startDate+"']"),1));
        String displayingColorforStartDate = driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected' and text()='"+startDate+"']")).getCssValue("background-color");
        Assert.assertTrue("Given color is not displayed",color.equals(displayingColorforStartDate));
        startDateButton.click();
        waitUntilPageFinishLoading();
        WebElement endDateButton = driver.findElement(By.xpath("//td/span[text()='"+module+"']/parent::td/following-sibling::td/button[@aria-label='End Date Calendar']"));
        endDateButton.click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected' and text()='"+endDate+"']"),1));
        String displayingColorforEndDate = driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected' and text()='"+endDate+"']")).getCssValue("background-color");
        Assert.assertTrue("Given color is not displayed",color.equals(displayingColorforEndDate));
        endDateButton.click();
    }

    public void verifyYearsListinStartandEndDateCalendar(String moduleName){
        //End date
        List<WebElement> yearsList = getDriver().findElements(By.cssSelector("select[id='year-select']>option"));
        waitUntilPageFinishLoading();
        WebElement endDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='End Date Calendar']"));
        endDateButton.click();
        waitUntilPageFinishLoading();
        selectYearInInstitutionPage().click();
        waitUntilPageFinishLoading();
        for(WebElement year:yearsList){
            Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+year+"']")).isDisplayed());
        }
        endDateButton.click();
        //Start date
        WebElement startDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='Start Date Calendar']"));
        startDateButton.click();
        waitUntilPageFinishLoading();
        selectYearInInstitutionPage().click();
        waitUntilPageFinishLoading();
        for(WebElement year:yearsList){
            Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+year+"']")).isDisplayed());
        }
        startDateButton.click();
    }

    public void verifyModulesStartandEndDateCalendar(String previousYear,String lastYear,String moduleName){
    //End date
        WebElement endDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='End Date Calendar']"));
        endDateButton.click();
        waitUntilPageFinishLoading();
        selectYearInInstitutionPage().click();
        waitUntilPageFinishLoading();
        String getPreviousYear = previousYear.replace("-","");
        int startYear = Integer.parseInt(getPreviousYear);
        int calendarStartYear = currentYear()-startYear;
        String getLastYear = lastYear.replace("+","");
        int endYear = Integer.parseInt(getLastYear);
        int calendarEndYear = currentYear()+endYear;
        Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+calendarStartYear+"']")).isDisplayed());
        Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+calendarEndYear+"']")).isDisplayed());
        endDateButton.click();
     //Start date
        WebElement startDateButton = driver.findElement(By.xpath("//td/span[text()='"+moduleName+"']/parent::td/following-sibling::td/button[@aria-label='Start Date Calendar']"));
        startDateButton.click();
        waitUntilPageFinishLoading();
        selectYearInInstitutionPage().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+calendarStartYear+"']")).isDisplayed());
        Assert.assertTrue("Given Year is not displayed",driver.findElement(By.xpath("//select/option[@value='"+calendarEndYear+"']")).isDisplayed());
        startDateButton.click();
    }

    public String generateRandomNumber() {
        Random random = new Random();
        int value = random.nextInt(100000) + 100;
        String Randomvalue=Integer.toString(value);
        return Randomvalue;
    }
    private WebElement saveButtonInAddUser(){
        return getDriver().findElement(By.xpath("//button/span[text()='Save']"));
    }
    private int currentYear(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }
    private WebElement selectYearInInstitutionPage(){
        return getDriver().findElement(By.id("year-select"));
    }

    public void openModuleLink(String moduleName) {
        moduleLink(moduleName).click();
    }

    //Locators
    private WebElement moduleLink(String moduleName) { return driver.findElement(By.xpath("//span[text() = '" + moduleName + "']")); }
}



