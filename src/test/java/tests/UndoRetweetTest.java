package tests;

import actions.TwitterActions;
import data_providers.DataProviders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.UserPage;
import utility.Logger;

import java.sql.Timestamp;

/**
 * Created on 25/11/2016 at 15:19.
 */
public class UndoRetweetTest extends BaseTest {

    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private ProfilePage objProfilePage;
    private UserPage objUserPage;

    private final String twitterLink = "https://twitter.com/";


    @Test(dataProvider = "logins", dataProviderClass = DataProviders.class)
    public void testLogin(String tUsername, String tPassword) {
        driver.get(twitterLink);

        objLoginPage = new LoginPage(driver);
        Logger.log("Login to twitter account");
        TwitterActions.loginToTwitter(tUsername, tPassword, objLoginPage);

        Logger.log("Home page is opened");
        objHomePage = new HomePage(driver);
        Logger.log("Compare actual username with expected");
        Assert.assertEquals(objHomePage.getYourUsername(), tUsername);
        Logger.log("Check the presence of news feed");
        Assert.assertTrue(objHomePage.getNewsFeed().isDisplayed());
    }
    @Test(dependsOnMethods = "testLogin", dataProvider = "tweet-numbet-to-unretweet", dataProviderClass = DataProviders.class)
    public void testUndoRetweet(int TWEET_NUMBER)
    {
        int TWEETS_BEFORE = Integer.parseInt(objHomePage.getTweetCounter().getText());
        System.out.println(TWEETS_BEFORE);
        Logger.log("Go to Tweets Page");
        objHomePage.click(objHomePage.getTweetCounter());
        objProfilePage = new ProfilePage(driver);

        Logger.log("Find post #TWEET_NUMBER and click unretweet");
        WebElement unretweetedTweet = TwitterActions.unretweet(TWEET_NUMBER,objProfilePage);

        String unretweetedTweetUserLink = objProfilePage.getRetweetedTweetUserLink(unretweetedTweet);
        String unretweetedTweetId = objProfilePage.getTweetId(unretweetedTweet);
        int unretweetedTweetCounter = Integer.parseInt(objProfilePage.getRetweetsNumberButton(unretweetedTweet).getText());
        Timestamp unretweetedTweetTimestamp = objProfilePage.getTweetTimestamp(unretweetedTweet);
        System.out.println(unretweetedTweetId);
        System.out.println(unretweetedTweetCounter);
        System.out.println(unretweetedTweetTimestamp);
        System.out.println(unretweetedTweetUserLink);

        Logger.log("Refresh page");
        driver.navigate().refresh();
        System.out.println(objProfilePage.getTweetCounter().getText());

        Logger.log("Try to find unretweeted post in feed");
        WebElement foundTweet = TwitterActions.findTweetById
                (unretweetedTweetId,Integer.parseInt(objProfilePage.getTweetCounter().getText()),objProfilePage);
        Logger.log("Assert tweet web element is null");
        Assert.assertNull(foundTweet);
        Logger.log("Check that profile tweet counter is reduced by 1");
        Assert.assertEquals(Integer.parseInt(objProfilePage.getTweetCounter().getText()),TWEETS_BEFORE-1);
        /*if (foundTweet!=null) {
            System.out.println("Found");
            System.out.println(objProfilePage.getTweetContent(foundTweet));
        }
        else System.out.println("Not Found");
        */
        Logger.log("Go to Unretweeted Post User Page");
        driver.get(unretweetedTweetUserLink);
        objUserPage = new UserPage(driver);
        Logger.log("Try to find unretweeted post");
        WebElement userTweet = TwitterActions.findTweetByIdUserPage(unretweetedTweetId,objUserPage);
        Logger.log("Assert tweet web element is not null");
        Assert.assertNotNull(userTweet);
        Logger.log("Compare timestamps of old post and found one");
        Assert.assertEquals(objUserPage.getTweetTimestamp(userTweet),unretweetedTweetTimestamp);
        Logger.log("Check that post retweet counter is reduced by 1");
        Assert.assertEquals(Integer.parseInt(objUserPage.getRetweetsNumberButton(userTweet).getText()),unretweetedTweetCounter-1);
        System.out.println("cont:"+ objUserPage.getTweetContent(userTweet));
    }
}
