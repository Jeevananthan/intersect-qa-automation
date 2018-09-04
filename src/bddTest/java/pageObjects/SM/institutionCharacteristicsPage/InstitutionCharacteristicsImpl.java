package pageObjects.SM.institutionCharacteristicsPage;

import org.apache.log4j.Logger;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.superMatchPage.FCSuperMatchPageImpl;

public class InstitutionCharacteristicsImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public InstitutionCharacteristicsImpl() {
        logger = Logger.getLogger(FCSuperMatchPageImpl.class);
    }

    public void verifyTooltipForAverageClassSize(String fitCriteriaOption){

    }
}
