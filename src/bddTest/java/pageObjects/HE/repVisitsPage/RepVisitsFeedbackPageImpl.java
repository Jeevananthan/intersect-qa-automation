package pageObjects.HE.repVisitsPage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RepVisitsFeedbackPageImpl extends RepVisitsPageImpl {

    public void verifyVisitFeedbackHeading() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("Visit Feedback heading not displayed", driver.findElement(By.xpath("//h1[contains(@class, 'ui header _26ekcAlhCmjadW7ShhS7aj')]")).getText().equals("Visit Feedback"));
    }

    public void verifyStaffMembersAreDisplayedInAscendingOrderByLastName() {
        waitUntilPageFinishLoading();

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));
        ArrayList<String> listContainingFullNamesOfStaffMembers = new ArrayList<String>();

        for(int i=0; i < itemsInStaffMemberMenu.size(); i++)
        {
            String fullNameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            listContainingFullNamesOfStaffMembers.add(fullNameOfStaffMember);
        }

        List<String> listContainingStaffMembersSortedByLastName = sortByLastName(listContainingFullNamesOfStaffMembers);
        Assert.assertTrue("Staff members are NOT displayed in ascending order by last name", listContainingFullNamesOfStaffMembers.equals(listContainingStaffMembersSortedByLastName));
    }

    public void verifyCommunityAvatarIsDisplayedNextToStaffMemberName() {
        waitUntilPageFinishLoading();
        boolean isCommunityAvatarDisplayed = false;

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));

        for( int i=0; i<itemsInStaffMemberMenu.size(); i++)
        {
            try {
                isCommunityAvatarDisplayed = itemsInStaffMemberMenu.get(i).
                        findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]/img | .//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]/i")).isDisplayed();
            } catch(Exception ex) {
                isCommunityAvatarDisplayed = false;
            }

            String nameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            Assert.assertTrue("Community avatar for staff member - " + nameOfStaffMember + " - is not displayed", isCommunityAvatarDisplayed);
        }
    }

    public void verifyIndividualAvgRatingIsNotDisplayedIfStaffMemberHasNoRatings() {
        waitUntilPageFinishLoading();

        boolean isIndividualAverageRatingWithStarIconDisplayedToRightOfStaffMemberName = false;

        List<WebElement> itemsInStaffMemberMenuWithNoRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='disabled item']"));

        for(int i=0; i<itemsInStaffMemberMenuWithNoRatings.size(); i++)
        {
            try {
                isIndividualAverageRatingWithStarIconDisplayedToRightOfStaffMemberName = itemsInStaffMemberMenuWithNoRatings.get(i).findElement(By.xpath(".//div[contains(@class, 'right aligned middle aligned')]")).isDisplayed();
            } catch(Exception ex) {
                isIndividualAverageRatingWithStarIconDisplayedToRightOfStaffMemberName = false;
            }

            String nameOfStaffMember = itemsInStaffMemberMenuWithNoRatings.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            Assert.assertFalse("Average rating and star icon displayed to the right of staff member with name - " + nameOfStaffMember , isIndividualAverageRatingWithStarIconDisplayedToRightOfStaffMemberName);

        }
    }

    public void verifyIndividualAvgRatingIsDisplayedIfStaffMemberHasRatings() {
        waitUntilPageFinishLoading();
        boolean isLoadMoreLinkDisplayed = true;

        ArrayList<Integer> ratings = new ArrayList<Integer>();

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        for(int i=0; i<itemsInStaffMemberMenuHavingOneOrMoreRatings.size(); i++)
        {
            isLoadMoreLinkDisplayed = true;
            itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i).click();

            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);

            while(isLoadMoreLinkDisplayed)
            {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Load more']"))).click();
                } catch(Exception ex) {
                    isLoadMoreLinkDisplayed = false;
                }
            }

            List<WebElement> listOfRatings = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']/div/div[@class='ui vertical segment rating-segment']")));
            ratings.clear();

            for(int ratingIndex=0; ratingIndex<listOfRatings.size(); ratingIndex++) {
                List<WebElement> starElements = listOfRatings.get(ratingIndex).findElements(By.xpath(".//div[@class='ui large disabled rating']/i"));
                for(int starIndex = 1; starIndex<=5; starIndex++)
                {
                    if(starElements.get(starIndex-1).getAttribute("aria-checked").equals("true"))
                    {
                        ratings.add(starIndex);
                    }
                }
            }

            String individualAverageRating = String.format("%.1f", ratings.stream().mapToInt(a -> a).average().getAsDouble());
            Assert.assertTrue("Individual average rating is not displayed correctly next to the staff member's name",
                    itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i)
                            .findElement(By.xpath(".//div[@class='right aligned middle aligned four wide column _3w9hWiNBzvFIga3_NDh4b4']")).getText().equals(individualAverageRating));

        }
    }

    public void verifyIfAvgOfAllStaffMemberRatingsIsDisplayedInOverviewArea()
    {
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        boolean isLoadMoreLinkDisplayed = true;

        if(goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed())
            return;

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        for(int i=0; i<itemsInStaffMemberMenuHavingOneOrMoreRatings.size(); i++)
        {
            isLoadMoreLinkDisplayed = true;
            itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i).click();

            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);

            while(isLoadMoreLinkDisplayed)
            {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Load more']"))).click();
                } catch(Exception ex) {
                    isLoadMoreLinkDisplayed = false;
                }
            }

            List<WebElement> listOfRatings = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']/div/div[@class='ui vertical segment rating-segment']")));

            for(int ratingIndex=0; ratingIndex<listOfRatings.size(); ratingIndex++) {
                List<WebElement> starElements = listOfRatings.get(ratingIndex).findElements(By.xpath(".//div[@class='ui large disabled rating']/i"));
                for(int starIndex = 1; starIndex<=5; starIndex++)
                {
                    if(starElements.get(starIndex-1).getAttribute("aria-checked").equals("true"))
                    {
                        ratings.add(starIndex);
                    }
                }
            }
        }

        String totalAverageRating = String.format("%.1f", ratings.stream().mapToInt(a -> a).average().getAsDouble());

        if(goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        Assert.assertTrue("Total average rating is not displayed correctly in Overview page",
                driver.findElement(By.xpath("//div[@class='rep-visits-ratings']//div[@class='_4hUbEmlCuf1SX_1dWPa-T']")).getText().equals(totalAverageRating));

    }

    public void verifyTotalActiveStaffMembersIsDisplayedInOverviewArea()
    {
        int numberOfActiveStaffMembers = 0;

        if(goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed())
            return;


        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class, 'item')]"));

        System.out.println(itemsInStaffMemberMenu.size());
        numberOfActiveStaffMembers = itemsInStaffMemberMenu.size();
        Assert.assertTrue("Total active staff members statistic  is not displayed correctly in overview page",
                driver.findElement(By.xpath("//div[@class='rep-visits-ratings']//div[@class='_4hUbEmlCuf1SX_1dWPa-T']/following-sibling::div[1]"))
                        .getText().contains(Integer.toString(numberOfActiveStaffMembers)));

    }


    public void verifyTotalNumberOfRatingsIsDisplayedInOverviewArea()
    {
        ArrayList<Integer> numberOfRatingsSubmittedForEachStaffMemberList = new ArrayList<Integer>();
        boolean isLoadMoreLinkDisplayed = true;

        if(goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed())
            return;

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        for(int i=0; i<itemsInStaffMemberMenuHavingOneOrMoreRatings.size(); i++)
        {
            isLoadMoreLinkDisplayed = true;
            itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i).click();

            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);

            while(isLoadMoreLinkDisplayed)
            {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Load more']"))).click();
                } catch(Exception ex) {
                    isLoadMoreLinkDisplayed = false;
                }
            }

            List<WebElement> listOfRatings = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']/div/div[@class='ui vertical segment rating-segment']")));

            numberOfRatingsSubmittedForEachStaffMemberList.add(listOfRatings.size());
        }

        int totalNumberOfRatingsSubmittedForHEAccount = numberOfRatingsSubmittedForEachStaffMemberList.stream().mapToInt(value -> value).sum();

        if(goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        Assert.assertTrue("Total number of ratings submitted statistic is not displayed correctly in overview page",
                driver.findElement(By.xpath("//div[@class='rep-visits-ratings']//div[@class='_4hUbEmlCuf1SX_1dWPa-T']/following-sibling::div[2]"))
                        .getText().contains(Integer.toString(totalNumberOfRatingsSubmittedForHEAccount)));

    }

    public void verifyTotalNumberOfCommentsIsDisplayedInOverviewArea() {
        ArrayList<Integer> numberOfRatingsSubmittedPerStaffMemberList = new ArrayList<Integer>();
        boolean isLoadMoreLinkDisplayed = true;
        int totalNumberOfCommentsForHEAccount = 0;

        if (goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed())
            return;

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        for (int i = 0; i < itemsInStaffMemberMenuHavingOneOrMoreRatings.size(); i++) {
            isLoadMoreLinkDisplayed = true;
            itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i).click();

            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);

            while(isLoadMoreLinkDisplayed)
            {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Load more']"))).click();
                } catch(Exception ex) {
                    isLoadMoreLinkDisplayed = false;
                }
            }


            try {
                numberOfRatingsSubmittedPerStaffMemberList.add(wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']/div/div[@class='ui vertical segment rating-segment']//div[@class='_2RIvl1eXXjTKII4WBMfzmQ']"))).size());
            } catch(Exception ex) {
                numberOfRatingsSubmittedPerStaffMemberList.add(0);
            }

        }

        totalNumberOfCommentsForHEAccount = numberOfRatingsSubmittedPerStaffMemberList.stream().mapToInt(value -> value).sum();

        if(goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        Assert.assertTrue("Total number of comments statistic is not displayed correctly on overview page", driver.findElement(By.xpath("//div[@class='rep-visits-ratings']//div[@class='_4hUbEmlCuf1SX_1dWPa-T']/following-sibling::div[3]"))
                .getText().contains(Integer.toString(totalNumberOfCommentsForHEAccount)));

    }

    public void verifyFeedbackBreakdownInOverviewArea() {

        boolean isLoadMoreLinkDisplayed = true;

        int totalNumberOf1StarRatings = 0;
        int totalNumberOf2StarRatings = 0;
        int totalNumberOf3StarRatings = 0;
        int totalNumberOf4StarRatings = 0;
        int totalNumberOf5StarRatings = 0;

        int percentageFeedbacksRated1Star = 0;
        int percentageFeedbacksRated2Stars = 0;
        int percentageFeedbacksRated3Stars = 0;
        int percentageFeedbacksRated4Stars = 0;
        int percentageFeedbacksRated5Stars = 0;

        if (goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed())
            return;

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        for (int i = 0; i < itemsInStaffMemberMenuHavingOneOrMoreRatings.size(); i++) {

            isLoadMoreLinkDisplayed = true;
            itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i).click();

            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);

            while(isLoadMoreLinkDisplayed)
            {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Load more']"))).click();
                } catch(Exception ex) {
                    isLoadMoreLinkDisplayed = false;
                }
            }

            List<WebElement> listOfRatings = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']/div/div[@class='ui vertical segment rating-segment']")));

            for(int ratingIndex=0; ratingIndex<listOfRatings.size(); ratingIndex++) {
                List<WebElement> starElements = listOfRatings.get(ratingIndex).findElements(By.xpath(".//div[@class='ui large disabled rating']/i"));
                for(int starIndex = 1; starIndex<=5; starIndex++)
                {
                    if(starElements.get(starIndex-1).getAttribute("aria-checked").equals("true"))
                    {
                        if(starIndex == 1)
                            totalNumberOf1StarRatings++;

                        if(starIndex == 2)
                            totalNumberOf2StarRatings++;

                        if(starIndex == 3)
                            totalNumberOf3StarRatings++;

                        if(starIndex == 4)
                            totalNumberOf4StarRatings++;

                        if(starIndex == 5)
                            totalNumberOf5StarRatings++;

                    }
                }
            }
        }

        int totalNumberOfRatings = totalNumberOf1StarRatings + totalNumberOf2StarRatings + totalNumberOf3StarRatings + totalNumberOf4StarRatings + totalNumberOf5StarRatings;

        percentageFeedbacksRated1Star = (int) Math.round((((double) totalNumberOf1StarRatings)/((double) totalNumberOfRatings)) * 100);
        percentageFeedbacksRated2Stars = (int) Math.round((((double) totalNumberOf2StarRatings)/((double) totalNumberOfRatings)) * 100);
        percentageFeedbacksRated3Stars = (int) Math.round((((double) totalNumberOf3StarRatings)/((double) totalNumberOfRatings)) * 100);
        percentageFeedbacksRated4Stars = (int) Math.round((((double) totalNumberOf4StarRatings)/((double) totalNumberOfRatings)) * 100);
        percentageFeedbacksRated5Stars = (int) Math.round((((double) totalNumberOf5StarRatings)/((double) totalNumberOfRatings)) * 100);

        if (goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ui equal width grid stars']//div[@class='row'])[1]//div[@class='right aligned column']"))
                .getText().contains(Integer.toString(percentageFeedbacksRated5Stars)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ui equal width grid stars']//div[@class='row'])[2]//div[@class='right aligned column']"))
                .getText().contains(Integer.toString(percentageFeedbacksRated4Stars)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ui equal width grid stars']//div[@class='row'])[3]//div[@class='right aligned column']"))
                .getText().contains(Integer.toString(percentageFeedbacksRated3Stars)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ui equal width grid stars']//div[@class='row'])[4]//div[@class='right aligned column']"))
                .getText().contains(Integer.toString(percentageFeedbacksRated2Stars)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ui equal width grid stars']//div[@class='row'])[5]//div[@class='right aligned column']"))
                .getText().contains(Integer.toString(percentageFeedbacksRated1Star)));

    }

    public void verifyTopAreasToImproveBreakdownInOverviewArea() {

        boolean isLoadMoreLinkDisplayed = true;

        int totalNumberOfThumbsDownProfessionalismTags = 0;
        int totalNumberOfThumbsDownKnowledgeTags = 0;
        int totalNumberOfThumbsDownMaterialsTags = 0;
        int totalNumberOfThumbsDownOtherTags = 0;

        int percentageOfThumbsDownProfessionalismTags = 0;
        int percentageOfThumbsDownKnowledgeTags = 0;
        int percentageOfThumbsDownMaterialsTags = 0;
        int percentageOfThumbsDownOtherTags = 0;

        if (goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed())
            return;

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreRatings = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        for (int i = 0; i < itemsInStaffMemberMenuHavingOneOrMoreRatings.size(); i++) {

            isLoadMoreLinkDisplayed = true;
            itemsInStaffMemberMenuHavingOneOrMoreRatings.get(i).click();

            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 5);

            while(isLoadMoreLinkDisplayed)
            {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Load more']"))).click();
                } catch(Exception ex) {
                    isLoadMoreLinkDisplayed = false;
                }
            }

            List<WebElement> listOfATITagsWithThumbsDownIcon = new ArrayList<WebElement>();

            try {
                listOfATITagsWithThumbsDownIcon = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']/div/div[@class='ui vertical segment rating-segment']//span[@class='_3HkfzGwe9DUTIza-b_pTkn']/i[contains(@class, 'thumbs outline down')]")));
            } catch(Exception ex) {
                listOfATITagsWithThumbsDownIcon = new ArrayList<WebElement>();
            }

            for(int j = 0; j < listOfATITagsWithThumbsDownIcon.size(); j++)
            {
                switch(listOfATITagsWithThumbsDownIcon.get(j).findElement(By.xpath("./parent::span")).getText().toUpperCase())
                {
                    case "PROFESSIONALISM": totalNumberOfThumbsDownProfessionalismTags++;
                        break;
                    case "KNOWLEDGE": totalNumberOfThumbsDownKnowledgeTags++;
                        break;
                    case "MATERIALS": totalNumberOfThumbsDownMaterialsTags++;
                        break;
                    case "OTHER": totalNumberOfThumbsDownOtherTags++;
                        break;
                }
            }

        }

        if (goBackToOverviewPageLink() != null)
            goBackToOverviewPageLink().click();

        int totalNumberOfATIThumbsDownTags = totalNumberOfThumbsDownProfessionalismTags + totalNumberOfThumbsDownKnowledgeTags + totalNumberOfThumbsDownMaterialsTags + totalNumberOfThumbsDownOtherTags;

        percentageOfThumbsDownProfessionalismTags = (int) Math.round((((double) totalNumberOfThumbsDownProfessionalismTags)/((double) totalNumberOfATIThumbsDownTags)) * 100);
        percentageOfThumbsDownKnowledgeTags = (int) Math.round((((double) totalNumberOfThumbsDownKnowledgeTags)/((double) totalNumberOfATIThumbsDownTags)) * 100);
        percentageOfThumbsDownMaterialsTags = (int) Math.round((((double) totalNumberOfThumbsDownMaterialsTags)/((double) totalNumberOfATIThumbsDownTags)) * 100);
        percentageOfThumbsDownOtherTags = (int) Math.round((((double) totalNumberOfThumbsDownOtherTags)/((double) totalNumberOfATIThumbsDownTags)) * 100);

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='rep-visits-ratings']//div[@class='row'])[2]/div[@class='column'][3]//span[contains(text(), 'professionalism')]//ancestor::div[1]"))
                .getText().contains(Integer.toString(percentageOfThumbsDownProfessionalismTags)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='rep-visits-ratings']//div[@class='row'])[2]/div[@class='column'][3]//span[contains(text(), 'knowledge')]//ancestor::div[1]"))
                .getText().contains(Integer.toString(percentageOfThumbsDownKnowledgeTags)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='rep-visits-ratings']//div[@class='row'])[2]/div[@class='column'][3]//span[contains(text(), 'materials')]//ancestor::div[1]"))
                .getText().contains(Integer.toString(percentageOfThumbsDownMaterialsTags)));

        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='rep-visits-ratings']//div[@class='row'])[2]/div[@class='column'][3]//span[contains(text(), 'other')]//ancestor::div[1]"))
                .getText().contains(Integer.toString(percentageOfThumbsDownOtherTags)));
    }

    public void verifyHSUsersNameLink(){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getVisitsFeedbackBtn().click();
        waitUntilPageFinishLoading();
        if (text("Insights into your team's reputation will appear here").isDisplayed())
            logger.info("No visit feedback is submitted for this user from HS side....");
        else {
            WebElement HEUserLeftPanel = driver.findElement(By.xpath("//ul[@class='ui vertical third _345W6T1ug0RMtbb4Ez3uMz menu']"));
            HEUserLeftPanel.findElement(By.xpath("(.//i[@class='star disabled icon azDd81vj4qd4ERFjicrCo']) [2]")).click();
            WebElement sectionOne = driver.findElement(By.xpath("(//div[@class='twelve wide column _2TegQ4j_MCOlAM0RjDSzhW'])"));
            List<WebElement> links = sectionOne.findElements(By.tagName("a"));
            WebElement HSUserLink = links.get(0);
            String userNameColorHS = HSUserLink.getCssValue("color");
            Assert.assertTrue("HE user name is not showing teal in color.", userNameColorHS.contains("rgba(30, 120, 122, 1)"));
            HSUserLink.click();
            waitUntilPageFinishLoading();
            String fullURL = driver.getCurrentUrl();
            if (fullURL.contains("/counselor-community/profile/")){
                Assert.assertTrue("HS user name is not a active hyperlink for Community.", fullURL.contains("/counselor-community/profile/"));
            }

        }
    }

    /**
     * Selects a user from the Ratings Feedback list in order to view information about their visits.
     * @param username - Name of the user to view.
     */
    public void selectFeedbackUser(String username) {
        getDriver().findElement(By.xpath("//div[contains(@class,'menu-link')]/div/div[text()[contains(.,'"+username+"')]]")).click();
        waitUntil(ExpectedConditions.visibilityOf(getRatingDetails()));
    }

    /**
     * Verifies the format of the individual user Visit Feedback page
     * Name displays, ratings summary displays, at least one individual visit feedback displays,
     * and the content of the individual feedback is as expected.
     */
    public void verifyUserFeedbackPage(String username) {
        // Make sure we have at least two segments (one for the user, one for a visit rating)
        List<WebElement> segments = getDriver().findElements(By.xpath("//div[contains(@class,'rating-segment')]"));
        softly().assertThat(segments.size()).isGreaterThan(1);
        // Make sure that the contents of the user area are present
        softly().assertThat(getDriver().findElement(By.xpath("//span[contains(@class,'rating-user-name')]")).getText()).as("User's Name").isEqualTo(username);
        softly().assertThat(getUserSummary().isDisplayed()).as("User rating summary displayed").isTrue();
        softly().assertThat(getUserSummary().findElement(By.xpath("./div[@role='radiogroup']")).isDisplayed()).as("Star ratings are displayed for user").isTrue();
        // Verify the contents of an individual rating are present
        softly().assertThat(getSingleRating().findElement(By.xpath("./div[@role='radiogroup']")).isDisplayed()).as("Star ratings are displayed for visit").isTrue();
        softly().assertThat(getSingleRating().findElement(By.className("_3HkfzGwe9DUTIza-b_pTkn")).isDisplayed()).as("Area of excellence/improvement icon").isTrue();
        softly().assertThat(getSingleRating().findElement(By.xpath("./strong/span/span[text()='stars']")).isDisplayed()).as("Textual star rating").isTrue();
    }

    private ArrayList sortByLastName(ArrayList<String> al) {
        Collections.sort(al, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] split1 = o1.split(" ");
                String[] split2 = o2.split(" ");
                String lastName1;
                String lastName2;
                if (split1.length > 1)
                    lastName1 = split1[1];
                else
                    lastName1 = split1[0];
                if (split2.length > 1)
                    lastName2 = split2[1];
                else
                    lastName2 = split2[0];
                if (lastName1.compareTo(lastName2) > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return al;
    }

    private WebElement goBackToOverviewPageLink() {
        try {
            driver.manage().timeouts().implicitlyWait(-1, TimeUnit.SECONDS);
            return driver.findElement(By.xpath("//div[@class='rating-details']/a"));
        } catch(Exception ex) {
            return null;
        }
    }

    private WebElement getRatingDetails() { return getDriver().findElement(By.className("rating-details")); }
    private WebElement getUserSummary() { return getDriver().findElement(By.xpath("//div[contains(@class,'user-summary')]")); }
    private WebElement getSingleRating() { return getParent(getDriver().findElement(By.xpath("//span[text()='submitted visit feedback of']")));}
}
