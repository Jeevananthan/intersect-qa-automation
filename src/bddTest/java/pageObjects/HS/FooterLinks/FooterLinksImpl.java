package pageObjects.HS.FooterLinks;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Set;

public class FooterLinksImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public FooterLinksImpl() {

    }
    public void verifyLinksInFooter(String footerLink){
        waitUntilPageFinishLoading();
        if(footerLink.equals("Terms of Service")){
            Assert.assertTrue("Terms of Service link is not displayed",link(footerLink).isDisplayed());
        }else if(footerLink.equals("Privacy Policy")) {
            Assert.assertTrue("privacy Policy link is not displayed", link(footerLink).isDisplayed());
        }else {
            Assert.fail("The given value is Invalid");
        }
    }

    public void switchToWindowAndVerifyURL(String navigateToPage){
        String currentWindow = driver.getWindowHandle();
        String TermsofServiceUrl = link(navigateToPage).getUrl();
        String PrivacyPolicyURL = link(navigateToPage).getUrl();
        String TermsofServiceWindow = null;
        String privacyPolicyWindow = null;
        if(navigateToPage.equals("Terms of Service")) {
            link(navigateToPage).click();
            waitUntilPageFinishLoading();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    TermsofServiceWindow = thiswindow;
                }
            }
            driver.switchTo().window(TermsofServiceWindow);
            Assert.assertTrue("Terms of Service page is not displayed",driver.findElement(By.xpath("//span[text()='Intersect Terms of Use ']")).isDisplayed());
            String TermsofServiceCurrentURL = driver.getCurrentUrl();
            Assert.assertTrue("Given URL is not present in the page", TermsofServiceUrl.equals(TermsofServiceCurrentURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilElementExists(link(navigateToPage));
            waitUntilPageFinishLoading();
        } else if(navigateToPage.equals("Privacy Policy")){
            link(navigateToPage).click();
            waitUntilPageFinishLoading();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    privacyPolicyWindow = thiswindow;
                }
            }
            driver.switchTo().window(privacyPolicyWindow);
            waitUntilPageFinishLoading();
            Assert.assertTrue("Privacy Policy page is not displayed",driver.findElement(By.xpath("//span[text()='User Provided Information: ']")).isDisplayed());
            String privacyPolicyCurrentURL = driver.getCurrentUrl();
            Assert.assertTrue("Given URL is not present in the page",PrivacyPolicyURL.equals(privacyPolicyCurrentURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilPageFinishLoading();
        } else{
            Assert.fail("The given option is not valid one");
        }
    }


}
