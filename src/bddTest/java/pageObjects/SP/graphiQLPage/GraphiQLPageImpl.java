package pageObjects.SP.graphiQLPage;

import cucumber.api.DataTable;
import cucumber.api.java.gl.E;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GraphiQLPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public GraphiQLPageImpl() {
        logger = Logger.getLogger(GraphiQLPageImpl.class);
    }

    private static String fs = File.separator;
    private static String propJsonFile = String.format(".%ssrc%sbddTest%sresources%sJSON%sSubscriptions",fs ,fs ,fs ,fs ,fs);

    public void createSubscriptionViaGraphiQL(String jsonFileName, DataTable dataTable) {
        String query = getStringFromPropFile(propJsonFile,"createSubscriptionQuery");
        String variables = getStringFromPropFile(propJsonFile,jsonFileName);
        List<List<String>> details = dataTable.asLists(String.class);
        for (List<String> row : details) {
            switch (row.get(0)) {
                case "startDate":
                    long startDateEpochTime = 0;
                    if (row.get(1).contains("before")) {
                        startDateEpochTime = getEpochTime(-Integer.parseInt(row.get(1).split(" ")[0]));
                    } else if (row.get(1).contains("after")) {
                        startDateEpochTime = getEpochTime(Integer.parseInt(row.get(1).split(" ")[0]));
                    }
                    variables = variables.replaceAll("(\"startDate\")\\:[0-9]+", "$1:" + startDateEpochTime);
                    break;
                case "endDate":
                    long endDateEpochTime = 0;
                    if (row.get(1).contains("before")) {
                        endDateEpochTime = getEpochTime(-Integer.parseInt(row.get(1).split(" ")[0]));
                    } else if (row.get(1).contains("after")) {
                        endDateEpochTime = getEpochTime(Integer.parseInt(row.get(1).split(" ")[0]));
                    }
                    variables = variables.replaceAll("(\"endDate\")\\:[0-9]+", "$1:" + endDateEpochTime);
                    break;
            }
        }

        WebElement firstQueryLine = getDriver().findElements(By.cssSelector(queryLinesListLocator)).get(0);
        enterTextInGraphiQLField(firstQueryLine, query);

        if(getDriver().findElement(By.className("variable-editor")).getSize().height < 70 ) {
            graphiQLQueryVariablesHeaderLocator().click();
        }
        else {

            WebElement element = getDriver().findElements(By.cssSelector(".CodeMirror-hscrollbar > div")).get(1);

            WebElement target = getDriver().findElements(By.cssSelector(".CodeMirror-gutters")).get(1);

            (new Actions(getDriver())).dragAndDrop(element, target).perform();

            Actions actions = new Actions(getDriver());
            actions.moveToElement(getDriver().findElements(By.cssSelector(".CodeMirror-code > div:first-child  > pre")).get(1).findElements(By.cssSelector("span > span")).get(0));
            actions.click();
            actions.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), "");
            actions.sendKeys(Keys.DELETE);
            actions.build().perform();
        }
        WebElement firstVariablesLine = getDriver().findElements(By.cssSelector(variablesLinesListLocator)).get(0);
        enterTextInGraphiQLField(firstVariablesLine, variables);
        executeButton().click();
        Assert.assertFalse("There were errors when creating the subscription", isPresentInResults("errors"));
    }

    private boolean isPresentInResults(String keyword) {
        waitUntilPageFinishLoading();
        boolean result = false;
        List<WebElement> resultsLinesList = driver.findElements(By.cssSelector(resultsLinesListLocatorssSelector));
        for (WebElement resultLine : resultsLinesList) {
            if (resultLine.getText().contains(keyword)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void enterTextInGraphiQLField(WebElement fieldFirstLine, String jsonText) {
        Actions actions = new Actions(driver);
        actions.moveToElement(fieldFirstLine);
        actions.click();
        actions.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT, Keys.END), jsonText);
        actions.build().perform();
    }

    public String readWholeJSONFile(String filePath) {
        String json = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        return json;
    }

    //Locators
    private String queryLinesListLocator = "div.query-editor div.CodeMirror-code div[style='position: relative;'] span[role='presentation']";
    private WebElement executeButton() { return driver.findElement(By.cssSelector("button.execute-button")); }
    private String variablesLinesListLocator = "div.variable-editor div.CodeMirror-code div[style='position: relative;'] span[role='presentation']";
    private WebElement graphiQLQueryVariablesHeaderLocator() { return driver.findElement(By.cssSelector("div.variable-editor-title")); }
    private String resultsLinesListLocatorssSelector = "div.result-window pre.CodeMirror-line span[role='presentation']";
}
