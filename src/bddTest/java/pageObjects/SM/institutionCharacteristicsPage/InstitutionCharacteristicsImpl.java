package pageObjects.SM.institutionCharacteristicsPage;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;
import java.io.File;
import java.util.List;

public class InstitutionCharacteristicsImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public InstitutionCharacteristicsImpl() {
        logger = Logger.getLogger(FCSuperMatchPageImpl.class);
    }
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sSMTooltipsContent%sSMTooltipsContent.properties",fs ,fs ,fs ,fs ,fs);

    SearchPageImpl spObj = new SearchPageImpl();

    public void verifyTooltipForAverageClassSize(String fitCriteriaOption, String fitCriteria){
        spObj.chooseFitCriteriaTab(fitCriteria);
        String propertiesEntry = "institution.characteristics.text.list";
        List<WebElement> icTooltipList = driver.findElements(By.cssSelector(tooltipsInTabListLocator));

        icTooltipList.get(5).click();
        List<WebElement> tooltipTextList = driver.findElements(By.cssSelector(tooltipText));
        for(int i = 0; i < tooltipTextList.size(); i++) {
            String actual = tooltipTextList.get(i).getText();
            String expected = getListFromPropFile(propertiesFilePath, ";",propertiesEntry).get(i);
            Assert.assertTrue("The tooltip text is not displaying for "+fitCriteriaOption, actual.equals(expected));
        }
        icTooltipList.get(5).click();
    }

    //Locators
    private String tooltipsInTabListLocator = "div.ui.bottom.left.basic.popup.transition.visible.supermatch-menuitem-popup button.supermatch-tooltip-trigger";
    private String tooltipText = "div[id*='supermatch-tooltip-'] div";
}
