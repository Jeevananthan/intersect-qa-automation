package stepDefinitions.HUBS.Login;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.HUBS.FamilyConnection.Login.Login;

import java.util.List;

/**
 * Created by csackrider on 10/9/2015.
 */
public class Login_StepDefs {

    Login login = new Login();

    @Given("^I log in to Family Connection with the following user details:$")
    public void I_log_in_to_Family_Connection_with_the_following_user_details(List<String> usercreds) throws Throwable {

        login.DoFCLogin(usercreds.get(0), usercreds.get(1), usercreds.get(2));

    }
}
