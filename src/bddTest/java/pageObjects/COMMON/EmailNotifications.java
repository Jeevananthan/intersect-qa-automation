package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.junit.Assert;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import java.util.List;

public class EmailNotifications {
    /**
     * Verifies taht the given body is present in the email notification
     * @param expectedEmailBody to be verified
     * @param dataTable with the mail information
     */
    public static void verifyEmailBody(String expectedEmailBody, DataTable dataTable){
        String emailBody = "";
        GetProperties.setGmailAPIWait(120);
        try {
            List<Email> emails = getGmailApi().getMessages(dataTable);
            for (Email email : emails) {
                emailBody = email.getBody();
            }
            emailBody = emailBody.replace("\r\n"," ");
            Assert.assertTrue(String.format("The email body is not correct, actual: %s, expected to contain: %s",
                    emailBody, expectedEmailBody),emailBody.matches(expectedEmailBody));
        } catch (Exception e) {
            Assert.fail(String.format("There was an error retrieving the email from Gmail, error: %s", e.toString()));
        }
    }
    private static GmailAPI getGmailApi() throws Exception { return new GmailAPI(); }
}
