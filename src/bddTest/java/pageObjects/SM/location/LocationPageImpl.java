package pageObjects.SM.location;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.studentLife.StudentLifeImpl;

import java.util.List;

public class LocationPageImpl extends PageObjectFacadeImpl {

    public LocationPageImpl() {
        logger = Logger.getLogger(LocationPageImpl.class);
    }

    StudentLifeImpl studentLife = new StudentLifeImpl();

    private Logger logger;

    public void verifyOptionsInDropdownField(DataTable dataTable){
        List<String> details = dataTable.asList(String.class);
        for (String element : details) {
            studentLife.verifyAddedOption(element);
        }
    }

    //Locators
}
