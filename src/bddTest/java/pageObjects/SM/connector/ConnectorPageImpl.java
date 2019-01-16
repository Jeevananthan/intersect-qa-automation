package pageObjects.SM.connector;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.collegesLookingForStudentsLikeYou.CollegesLookingForStudentsLikeYouPageImpl;

import java.util.List;

public class ConnectorPageImpl extends PageObjectFacadeImpl {

    public ConnectorPageImpl() {
        logger = Logger.getLogger(ConnectorPageImpl.class);
    }

    private Logger logger;
    private CollegesLookingForStudentsLikeYouPageImpl collegesLookingForStudentsLikeYouPage = new CollegesLookingForStudentsLikeYouPageImpl();

    public void verifyCheckboxesRelatedTo(String checkboxesStatus, String mainCheckBoxLabel, String mainCheckboxStatus) {
        if (connectorCheckbox(mainCheckBoxLabel).getAttribute("class").contains(mainCheckboxStatus)) {
            List<WebElement> checkBoxesList = driver.findElements(By.xpath(connectorCheckboxesLocator));
            for (WebElement checkbox : checkBoxesList) {
                softly().assertThat(checkbox.getAttribute("class")).as("The checkbox " + checkbox.getText() + " has an incorrect status").contains(checkboxesStatus);
            }
        }
    }

    public void verifyMainCheckboxRelatedTo(String mainCheckboxLabel, String sampleCheckboxLabel) {
        uncheckCheckbox(sampleCheckboxLabel);
        Assert.assertFalse("The checkbox " + mainCheckboxLabel + " is check when it should be unchecked",
                connectorCheckbox(mainCheckboxLabel).getAttribute("class").contains("checked"));
        checkCheckbox(mainCheckboxLabel);
    }

    private void uncheckCheckbox(String label) {
        if (connectorCheckbox(label).getAttribute("class").contains("checked")) {
            connectorCheckbox(label).click();
        }
    }

    private void checkCheckbox(String label) {
        if (!connectorCheckbox(label).getAttribute("class").contains("checked")) {
            connectorCheckbox(label).click();
        }
    }

    public void verifyConnectorFieldsEditable(DataTable dataTable) {
        List<String> fieldsList = dataTable.asList(String.class);
        for (String field : fieldsList) {
            switch (field) {
                case "First Name *" :
                    softly().assertThat(ExpectedConditions.visibilityOf(connectorField(field))).as(field + " is not an editable field");
                    break;
                case "Last Name *" :
                    softly().assertThat(ExpectedConditions.visibilityOf(connectorField(field))).as(field + " is not an editable field");
                    break;
                case "Email" :
                    softly().assertThat(ExpectedConditions.visibilityOf(emailField())).as(field + " is not an editable field");
                    break;
                case "Phone" :
                    softly().assertThat(ExpectedConditions.visibilityOf(phoneField())).as(field + " is not an editable field");
                    break;
                case "Street" :
                    softly().assertThat(ExpectedConditions.visibilityOf(streetField())).as("Street is not an editable field");
                    break;
                case "City" :
                    softly().assertThat(ExpectedConditions.visibilityOf(cityField())).as("City is not an editable field");
                    break;
                case "State" :
                    softly().assertThat(ExpectedConditions.visibilityOf(stateDropdown())).as("State dropdown is not an editable");
                    break;
                case "Zip Code" :
                    softly().assertThat(ExpectedConditions.visibilityOf(zipCodeField())).as("Zip Code is not an editable field");
                    break;
            }
        }
    }

    public void verifyConnectorFieldsNotEditable(DataTable dataTable) {
        List<String> fieldsList = dataTable.asList(String.class);
        for (String field : fieldsList) {
            switch (field) {
                case "Gender" :
                    softly().assertThat(nonEditableConnectorField(field).getAttribute("class")).as(field + " is editable, when it shouldn't").contains("read-only-value");
                    break;
                case "Birthday" :
                    softly().assertThat(birthdayField().getText()).as(field + " is editable, when it shouldn't").isNotEqualTo("");
                    break;
                case "Your GPA" :
                    softly().assertThat(nonEditableConnectorField(field).getAttribute("class")).as(field + " is editable, when it shouldn't").contains("read-only-value");
                    break;
                case "Ethnicity" :
                    softly().assertThat(nonEditableConnectorField(field).getAttribute("class")).as(field + " is editable, when it shouldn't").contains("read-only-value");
                    break;
            }
        }
    }

    public void clickButtonInConnectorDialog(String label) {
        connectorDialogButton(label).click();
    }

    public void verifyRequiredFieldsEmailPhoneStreet() {
        uncheckCheckbox("Email");
        uncheckCheckbox("Phone");
        uncheckCheckbox("Address");
        driver.findElement(By.cssSelector("button#submitForm")).click();
        waitUntilElementExists(driver.findElement(By.xpath(missingEmailPhoneStreetErrorMessageLocator)));
        Assert.assertTrue("No error message was displayed when the form was submited without email, phone or street",
                driver.findElements(By.xpath(missingEmailPhoneStreetErrorMessageLocator)).size() > 0);
        checkCheckbox("Share all");
        if (emailField().getAttribute("value").equals("")) {
            emailField().sendKeys("test@mail.com");
        }
    }

    public void verifyPhoneErrorMessage(String erroneousPhoneNumber) {
        String phoneOriginalValue = phoneField().getAttribute("value");
        phoneField().clear();
        phoneField().sendKeys(erroneousPhoneNumber);
        button("Submit").click();
        Assert.assertTrue("The error message for erroneous phone numbers is not displayed",
                phoneError().getText().equals(phoneErrorMessageString));
        phoneField().clear();
        if (!phoneOriginalValue.equals("")) {
            phoneField().sendKeys(phoneOriginalValue);
        } else {
            uncheckCheckbox("Phone");
        }
    }

    public void verifyBirthdayFormat() {
        Assert.assertTrue("The birthday date format is incorrect",
                birthdayValue().getText().split("\\n")[1].matches("([a-z]|[A-Z]){3}\\s(3[01]|[12][0-9]|[1-9]\\w*),\\s[0-9]{4}"));
    }

    public void verifyItsPossibleToSubmit(String value) {
        collegesLookingForStudentsLikeYouPage.yesIDoButton().click();
        waitUntilElementExists(button("Submit"));
        if (birthdayValue().getText().split("\\n")[1].equals(value)) {
            button("Submit").click();
        }
        waitUntilElementExists(collegesLookingForStudentsLikeYouPage.closeButton());
        Assert.assertTrue("It was not possible to submit the form with " + value + " as value for Birthday",
                collegesLookingForStudentsLikeYouPage.stepTitle3().getText().equals("Successfully Submitted!"));
        collegesLookingForStudentsLikeYouPage.closeButton().click();
    }

    //Locators
    private WebElement connectorCheckbox(String label) { return driver.findElement(By.xpath("//label[text() = '" + label + "']/..")); }
    private String connectorCheckboxesLocator = "//label[@class = 'form-checkbox-label']/..";
    private WebElement connectorField(String label) { return driver.findElement(By.xpath("//label[contains(text(), '" + label + "')]/following-sibling::div/input")); }
    private WebElement emailField() { return driver.findElement(By.cssSelector("input#email")); }
    private WebElement phoneField() { return driver.findElement(By.cssSelector("input#phone")); }
    private WebElement streetField() { return driver.findElement(By.cssSelector("input#street")); }
    private WebElement cityField() { return driver.findElement(By.cssSelector("input#city")); }
    private WebElement stateDropdown() { return driver.findElement(By.cssSelector("div#state")); }
    private WebElement zipCodeField() { return driver.findElement(By.cssSelector("input#zipCode")); }
    private WebElement nonEditableConnectorField(String fieldLabel) { return driver.findElement(By.xpath("//label[contains(text(), '" + fieldLabel + "')]/../../..")); }
    private WebElement birthdayField() { return driver.findElement(By.xpath("//label[contains(text(), 'Birthday')]")); }
    private WebElement birthdayValue() { return driver.findElement(By.xpath("//label[contains(text(), 'Birthday')]/..")); }
    private WebElement connectorDialogButton(String label) { return driver.findElement(By.xpath("//button[text() = '" + label + "']")); }
    private String missingEmailPhoneStreetErrorMessageLocator = "//p[text() = 'Please select one method of contact: Email, Phone, or Address.']";
    private WebElement phoneError() { return driver.findElement(By.cssSelector("div#phoneError")); }
    private String phoneErrorMessageString = "Please provide a 10 digit phone number";
}
