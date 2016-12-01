package tests;

import actions.TwitterActions;
import data_providers.DataProviders;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utility.Logger;

import java.sql.Timestamp;

/**
 * Created on 23/11/2016 at 15:22.
 */
public class RetweetTest extends BaseTest {
    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private FollowingPage objFollowingPage;
    private UserPage objUserPage;
    private ProfilePage objProfilePage;


    private final String twitterLink = "https://twitter.com/";

    @Test(dataProvider = "logins", dataProviderClass = DataProviders.class)
    public void testRetweet(String tUsername, String tPassword)
    {
        driver.get(twitterLink);

        objLoginPage = new LoginPage(driver);
        Logger.log("Login to twitter account");
        TwitterActions.loginToTwitter(tUsername,tPassword,objLoginPage);

        Logger.log("Home page is opened");
        objHomePage = new HomePage(driver);
        Logger.log("Compare actual username with expected");
        Assert.assertEquals(objHomePage.getYourUsername(),tUsername);
        Logger.log("Check the presence of news feed");
        Assert.assertTrue(objHomePage.getNewsFeed().isDisplayed());
        int tweetsCountBefore = Integer.parseInt(objHomePage.getTweetCounter().getText());

        Logger.log("Go to Following page");
        TwitterActions.goToFollowingPage(objHomePage);
        objFollowingPage = new FollowingPage(driver);
        Logger.log("Go to Sean Hannity page");
        objFollowingPage.click(objFollowingPage.getSeanHannityProfileLink());
        objFollowingPage.click(objFollowingPage.getSeanHannityProfileLink());
        objUserPage = new UserPage(driver);

        Logger.log("Search for a tweet that older than given time");
        WebElement tweet = TwitterActions.doRetweetIfTweetOlderThan(24,objUserPage);
        String tweetContent = objUserPage.getTweetContent(tweet);
        Timestamp tweetTimestamp = objUserPage.getTweetTimestamp(tweet);
        Logger.log("Go to Home Page");
        driver.get(twitterLink);
        int tweetsCounterAfter = Integer.parseInt(objHomePage.getTweetCounter().getText());
        Logger.log("Compare tweets counter before and after retweeting");
        Assert.assertEquals(tweetsCounterAfter,tweetsCountBefore+1);

        Logger.log("Go to Tweets Page");
        //String tweetCounterXpath = "//div[@class='DashboardProfileCard-content']/div[3]//li[1]//span[2]";
        //objHomePage.waitForVisibility(By.xpath(tweetCounterXpath));
        objHomePage.click(objHomePage.getTweetCounter());
        objHomePage.click(objHomePage.getTweetCounter());
        objProfilePage = new ProfilePage(driver);

        Logger.log("Get First Tweet content");
        Logger.log("Compare content of reposted tweet and first profile tweet");
        Assert.assertTrue(objProfilePage.getTweetContent(TwitterActions.getProfileTweet(1,objProfilePage)).equals(tweetContent));
        System.out.println(objProfilePage.getTweetContent(TwitterActions.getProfileTweet(1,objProfilePage)));
        Logger.log("Compare timestamp of reposted tweet and first profile tweet");
        Assert.assertEquals(objProfilePage.getTweetTimestamp(TwitterActions.getProfileTweet(1,objProfilePage)),tweetTimestamp);
        System.out.println(objProfilePage.getTweetTimestamp(TwitterActions.getProfileTweet(1,objProfilePage)));
    }
}