package selenium.elements;

import Selenium.Driver.Query.Query;
import Selenium.WebElement.TableImpl;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Table extends TableImpl {
    Logger logger = Logger.getLogger(Table.class);

    public Table(By by) {
        super(by);
    }

    public Table(String captionOrHeader) {
        super(captionOrHeader);
    }

    public Table(Query searchArea) {
        super(searchArea);
    }

    public Table(WebElement element) {
        super(element);
    }

    public void clickOnTableElement(String value) {
        boolean clicked = false;
        //Get the table rows
        List<WebElement> tableRows = getTableRows();
        //Iterate each row to find the columns
        for (WebElement row : tableRows) {
            //Get all the columns
            List<WebElement> rowColumns = row.findElements(By.tagName("td"));
            //Iterate each column
            for (WebElement column : rowColumns) {
                //If the text for the column matches the value than click on it
                if (column.getText().equalsIgnoreCase(value)) {
                    column.findElement(By.tagName("a")).click();
                    clicked = true;
                    waitUntilPageFinishLoading();
                    break;
                }
            }
        }

        Assert.assertTrue("Unable to click on " + value, clicked);
    }

    public void verifyValueIsOnTheTable(String value) {
        boolean doesValueExist = false;
        //Get the table rows
        List<WebElement> tableRows = getTableRows();
        //Iterate each row to find the columns
        for (WebElement row : tableRows) {
            //Get all the columns
            List<WebElement> rowColumns = row.findElements(By.tagName("td"));
            //Iterate each column
            for (WebElement column : rowColumns) {
                //If the text for the column matches the value than click on it
                if (column.getText().equalsIgnoreCase(value)) {
                    doesValueExist = true;
                    break;
                }
            }
        }

        Assert.assertTrue(value + " not found on the table", doesValueExist);
    }

    public void verifyValueIsNotOnTheTable(String value) {
        boolean doesValueExist = false;
        //Get the table rows
        List<WebElement> tableRows = getTableRows();
        //Iterate each row to find the columns
        for (WebElement row : tableRows) {
            //Get all the columns
            List<WebElement> rowColumns = row.findElements(By.tagName("td"));
            //Iterate each column
            for (WebElement column : rowColumns) {
                //If the text for the column matches the value than click on it
                if (column.getText().equalsIgnoreCase(value)) {
                    doesValueExist = true;
                    break;
                }
            }
        }

        Assert.assertFalse(value + " was found on the table", doesValueExist);
    }

    public String clickOnTheFirstElementOfAColumn(String columnName) {
        //Get the first row of the table
        WebElement firstRow = getTableRows().get(0);
        //Get all the columns of the table
        List<WebElement> columns = firstRow.findElements(By.tagName("td"));
        //Click the first element of the column
        WebElement columnEl = columns.get(getColumnIndex(columnName));
        //Get the column text value to return
        String columnText = columnEl.getText();
        //Click the column
        columnEl.findElement(By.tagName("a")).click();
        waitUntilPageFinishLoading();

        return columnText;

    }

    private List<WebElement> getTableRows() {
        waitUntilPageFinishLoading();
        //Get the table body
        WebElement tableBody = this.findElement(By.tagName("tbody"));
        //Get the table rows

        return tableBody.findElements(By.tagName("tr"));
    }

    private int getColumnIndex(String columnName) {
        waitUntilPageFinishLoading();
        //Get the table head
        WebElement tableBody = this.findElement(By.tagName("thead"));
        //Get the table rows
        List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
        //Start index to locate the column
        int index = 0;
        //Iterate each row to find the columns
        for (WebElement row : tableRows) {
            //Get all the columns
            List<WebElement> rowColumns = row.findElements(By.tagName("th"));
            //Iterate each column
            for (WebElement column : rowColumns) {
                //If the text for the column matches the value than click on it
                if (column.getText().equalsIgnoreCase(columnName)) {
                    return index;
                }
                index++;
            }
        }

        throw new NotFoundException("Unable to find column " + columnName);
    }
}
