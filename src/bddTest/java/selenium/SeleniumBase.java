package selenium;

import Selenium.Driver.Query.Query;
import Selenium.SeleniumLibrary;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.elements.Table;

public class SeleniumBase extends SeleniumLibrary {

    @Override
    protected Table table(By by) {
        return new Table(by);
    }
    @Override
    protected Table table(WebElement element) {
        return new Table(element);
    }
    @Override
    protected Table table(Query query) {
        return new Table(query);
    }
    @Override
    protected Table table(String visibleText) {
        return new Table(visibleText);
    }

    protected WebElement getParent(WebElement childElement) {
        return childElement.findElement(By.xpath("./.."));
    }

}
