package pageObjects.SM.pinnedSchoolsComparePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;
import pageObjects.SM.superMatchPage.NewSuperMatchPageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PinnedSchoolsComparePageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSMTooltipsContent%sSMTooltipsContent.properties",fs ,fs ,fs ,fs ,fs);

    public PinnedSchoolsComparePageImpl() {
        logger = Logger.getLogger(PinnedSchoolsComparePageImpl.class);
    }

    public void verifyTooltipInComparePage(String sectionName) {
        switch (sectionName) {
            case "Academic Match" :
                verifyTooltipsInCompareScreen("academic.match.in.compare.schools.title");
                verifyTooltipsInCompareScreen("academic.match.in.compare.schools.labels");
                verifyTooltipsInCompareScreen("academic.match.in.compare.schools.text.blocks");
                break;
            case "Admission Info" :
                verifyTooltipsInCompareScreen("compare.schools.admission.info.titles");
                verifyTooltipsInCompareScreen("compare.schools.admission.info.text");
                break;
            case "Institution Characteristics" :
                verifyTooltipsInCompareScreen("compare.schools.institution.characteristics.titles");
                verifyTooltipsInCompareScreen("compare.schools.institution.characteristics.text");
                break;
            case "Diversity" :
                verifyTooltipsInCompareScreen("compare.schools.diversity.titles");
                verifyTooltipsInCompareScreen("compare.schools.diversity.text");
                break;
        }
    }

    private void verifyTooltipsInCompareScreen(String propertiesEntry) {
        List<WebElement> admissionInfoTooltipIcons = driver.findElements(By.xpath(admissionInfoTooltipIconsLocator));
        List<WebElement> institutionCharacteristicsIcons = driver.findElements(By.xpath(institutionCharacteristicsLocator));
        List<WebElement> diversityIcons = driver.findElements(By.xpath(diversityTooltipIconsLocator));
        switch (propertiesEntry) {
            case "academic.match.in.compare.schools.title" :
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                Assert.assertTrue("The title for Academic Match in the Schools Compare screen is not correct",
                        tooltipTitle().getText().trim().equals(getStringFromPropFile(propertiesFilePath, propertiesEntry)));
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                break;
            case "academic.match.in.compare.schools.labels" :
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                List<WebElement> labels = driver.findElements(By.cssSelector(academicMatchLabelsLocator));
                for(int i = 0; i < labels.size(); i++) {
                    Assert.assertTrue("The label for the Academic Match tooltip is not correct",
                            labels.get(i).getText().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                }
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                break;
            case "academic.match.in.compare.schools.text.blocks" :
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                for(int i = 0; i < getListFromPropFile(propertiesFilePath, "*", propertiesEntry).size(); i++) {
                    Assert.assertTrue("The text content in the Academic Match tooltip is not correct",
                            academicMatchTextContent().getText().contains(getListFromPropFile(propertiesFilePath, "*", propertiesEntry).get(i)));
                }
                academicMatchTooltipIcon().sendKeys(Keys.RETURN);
                break;
            case "compare.schools.admission.info.titles" :
                for(int i = 0; i < admissionInfoTooltipIcons.size(); i++) {
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The title for the tooltip in Admission Info is incorrect",
                            tooltipTitle().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.admission.info.text" :
                for(int i = 0; i < admissionInfoTooltipIcons.size(); i++) {
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The text for the tooltip in Admission Info is incorrect",
                            tooltipText().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    admissionInfoTooltipIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.institution.characteristics.titles" :
                for(int i = 0; i < institutionCharacteristicsIcons.size(); i++) {
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The title of the tooltip in Institution Characteristics is incorrect",
                            tooltipTitle().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.institution.characteristics.text" :
                for(int i = 0; i < institutionCharacteristicsIcons.size(); i++) {
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The text of the tooltip in Institution Characteristics is incorrect. UI: " + tooltipText().getText() + " Data: "
                            + getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i),
                            tooltipText().getText().trim().replace("“", "'").replace("”", "'").equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    institutionCharacteristicsIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
            case "compare.schools.diversity.titles" :
                for(int i = 0; i < diversityIcons.size(); i++) {
                    diversityIcons.get(i).sendKeys(Keys.RETURN);
                    Assert.assertTrue("The title of the tooltip in Diversity is incorrect",
                            tooltipTitle().getText().trim().equals(getListFromPropFile(propertiesFilePath, ";", propertiesEntry).get(i)));
                    diversityIcons.get(i).sendKeys(Keys.RETURN);
                }
                break;
        }
    }

    // Locators Below

    private String admissionInfoTooltipIconsLocator = "//th[text() = 'Admission Info']/../../../tbody/tr/td/div/div/button";
    private String institutionCharacteristicsLocator = "//th[text() = 'Institution Characteristics']/../../../tbody/tr/td/div/div/button";
    private String diversityTooltipIconsLocator = "//th[text() = 'Diversity']/../../../tbody/tr/td/div/div/button";
    private WebElement tooltipTitle() { return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] div.header")); }
    private WebElement tooltipText() { return driver.findElement(By.cssSelector("div[class$='very wide inverted popup transition visible'] div.content")); }
    private String academicMatchLabelsLocator = "div[id *= 'supermatch-tooltip-'] div.content b";
    private WebElement academicMatchTextContent() { return driver.findElement(By.cssSelector("div[id *= 'supermatch-tooltip-'] div.content")); }
    private WebElement academicMatchTooltipIcon() { return driver.findElement(By.cssSelector("td[aria-label=\" Academic Match\"] button")); }
}
