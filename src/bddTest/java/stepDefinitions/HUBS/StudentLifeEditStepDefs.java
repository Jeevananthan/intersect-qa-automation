package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.StudentLifeEditPageImpl;

import java.util.HashMap;

public class StudentLifeEditStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();

    public StudentLifeEditStepDefs() {
        StudentLifeEditPageImpl studentLifeEdit = new StudentLifeEditPageImpl();

        Then("^HUBS I should be able to edit the following fields for Student Life in real time:$", studentLifeEdit::verifyFieldsInRealTime);
    }

}
