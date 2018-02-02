package pageObjects.CM.commonPages;

import org.apache.log4j.Logger;
import selenium.SeleniumBase;

public class PageObjectFacadeImpl extends SeleniumBase {

    private Logger logger;

    protected PageObjectFacadeImpl() {
        logger = Logger.getLogger(PageObjectFacadeImpl.class);
    }
}
