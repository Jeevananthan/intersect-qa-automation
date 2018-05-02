package pageObjects.SM.superMatchPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FCSuperMatchPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public FCSuperMatchPageImpl() {
        logger = Logger.getLogger(FCSuperMatchPageImpl.class);
    }
    private NewSuperMatchPageImpl newSuperMatch = new NewSuperMatchPageImpl();
    private SearchPageImpl searchPage = new SearchPageImpl();


    public void verifySuperMatchBanner() {
        driver.switchTo().frame("supermatch");
        Assert.assertTrue("The banner for SuperMatch is not displayed", superMatchBanner().getText().equals(superMatchBannerMessage));
        driver.switchTo().defaultContent();
    }

    public void verifySuperMatchBannerLink() {
        driver.switchTo().frame("supermatch");
        superMatchBannerLink().click();
        String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        waitForUITransition();
        Assert.assertTrue("The new SuperMatch is not disaplyed", newSuperMatch.collegeSearchHeader().isDisplayed());
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
        }
        driver.switchTo().window(winHandleBefore);
        driver.switchTo().defaultContent();
    }

    public void verifyTooltipsInTab(String tabName) {
        searchPage.chooseFitCriteriaTab(tabName);
        switch (tabName) {
            case "Location" : verifyTooltips(locationTooltipsTitlesList);
            break;
            case "Academics" : verifyTooltips(academicsTooltipsTitlesList);
            break;
            case "Admission" : verifyTooltips(admissionTooltipsTitlesList);
            break;
            case "Diversity" : verifyTooltips(diversityTooltipsTitlesList);
            break;
            case "Institution Characteristics" : verifyTooltips(instCharacteristicsTitlesList);
            break;
            case "Cost" : verifyTooltips(costTooltipsTitlesList);
            break;
            case "Student Life" : verifyTooltips(studentLifeTooltipsTitlesList);
            break;
            case "Athletics" : verifyTooltips(athleticsTooltipsTitlesList);
            break;
            case "Resources" : verifyTooltips(resourcesTooltipsTextList);
            break;
        }
    }

    private void verifyTooltips(List<String> tooltipsTitlesList) {
        if (tooltipsTitlesList.equals(resourcesTooltipsTextList)) {
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipText().getText(), tooltipText().getText().equals(resourcesTooltipsTextList.get(i)));
                tooltipsList.get(i).click();
            }
        } else if (tooltipsTitlesList.equals(athleticsTooltipsTitlesList)) {
            addSportButton().click();
            sportField().click();
            sportOption("Aerobics").click();
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsTitlesList.get(i)));
                tooltipsList.get(i).click();
            }
        }

        else {
            List<WebElement> tooltipsList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));
            for (int i = 0; i < tooltipsList.size(); i++) {
                tooltipsList.get(i).click();
                Assert.assertTrue("The tooltip title is incorrect. Value in UI: " + tooltipTitle().getText(), tooltipTitle().getText().equals(tooltipsTitlesList.get(i)));
                tooltipsList.get(i).click();
            }
        }
    }

    // Locators Below

    private WebElement superMatchBanner() { return driver.findElement(By.cssSelector("div#reBannerContent")); }
    private WebElement superMatchBannerLink() { return driver.findElement(By.cssSelector("div#reBannerContent strong a")); }
    private String superMatchBannerMessage = "Check it out!  We've been working on an updated SuperMatch experience  Click here to try it out";
    private String tooltipsInTabListLocator = "button.supermatch-tooltip-trigger";
    private List<String> locationTooltipsTitlesList = new ArrayList<String>() {{
        add("US Regions & Others");
        add("Campus Surroundings");
    }};
    private List<String> academicsTooltipsTitlesList = new ArrayList<String>() {{
        add("Degree Type");
    }};
    private List<String> admissionTooltipsTitlesList = new ArrayList<String>() {{
        add("GPA (4.0 Scale)");
        add("Your SAT Scores");
        add("Your ACT Scores");
        add("Accepts AP Credits");
        add("Accepts IB Credits");
        add("Test optional");
        add("Common App Member");
        add("Coalition App Member");
        add("No application fee");
    }};
    private List<String> diversityTooltipsTitlesList = new ArrayList<String>() {{
        add("Overall Diversity");
        add("Historically Black Institutions");
        add("Hispanic Serving Institutions");
        add("Tribal Colleges and Universities");
        add("% Male Vs Female");
        add("High International Population");
    }};
    private List<String> instCharacteristicsTitlesList = new ArrayList<String>() {{
        add("College Type (Public vs Private)");
        add("Show Only Non-Profit");
        add("High Graduation Rate");
        add("High Retention Rate");
        add("High Job Placement Rate");
    }};
    private List<String> costTooltipsTitlesList = new ArrayList<String>() {{
        add("Total Costs");
        add("Meets 100% of Need");
    }};
    private List<String> studentLifeTooltipsTitlesList = new ArrayList<String>() {{
        add("Greek Life");
        add("Internships and Co-ops");
        add("Offers Study abroad");
        add("ROTC");
    }};
    private List<String> athleticsTooltipsTitlesList = new ArrayList<String>() {{
        add("Levels");
    }};
    private List<String> resourcesTooltipsTextList = new ArrayList<String>() {{
        add("Learning Differences Support - LDS programs provide specialized assistance to help students with a wide " +
                "range of learning disabilities (ADHD, dyslexia, auditory/visual processing deficit, etc.).");
        add("Mental and behavioral health resources are available to support and help students as they navigate college life.");
        add("Remedial services provide students with courses in specific subject areas (math, reading, and writing) to " +
                "help them build the skills and confidence to succeed in their classroom studies. Students do not " +
                "typically receive credit for these courses.");
        add("English as a Second Language and English Language Learner programs provide assistance in learning English " +
                "to students who are not native English speakers.");
    }};
    private WebElement tooltipTitle() { return driver.findElement(By.cssSelector("div[id*='supermatch-toolip-'] div.header")); }
    private WebElement tooltipText() { return driver.findElement(By.cssSelector("div[id*='supermatch-toolip-'] span")); }
    private WebElement addSportButton() { return driver.findElement(By.cssSelector("button[title=\"Add a Sport\"]")); }
    private WebElement sportField() { return driver.findElement(By.cssSelector("div.default.text")); }
    private WebElement sportOption(String sport) { return driver.findElement(By.xpath("//div[@class='menu transition visible']/div/span[text()='" + sport + "']")); }
}
