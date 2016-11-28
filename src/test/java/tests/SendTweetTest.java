package tests;

import actions.TwitterActions;
import data_providers.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utility.Logger;

/**
 * Created on 22/11/2016 at 10:18.
 */
public class SendTweetTest extends BaseTest{

    private HomePage objHomePage;
    private LoginPage objLoginPage;

    private final String twitterLink = "https://twitter.com/";
    public final int tweetMaxLength = 140;
    public final String MESSAGE = "Hello, World!";

    @Test(dataProvider = "logins", dataProviderClass = DataProviders.class)
    public void test(String tUsername, String tPassword)
    {
        driver.get(twitterLink);
        objLoginPage=new LoginPage(driver);
        Logger.log("Login to twitter account");
        TwitterActions.loginToTwitter(tUsername,tPassword,objLoginPage);

        Logger.log("Home page is opened");
        objHomePage = new HomePage(driver);
        Logger.log("Compare actual username with expected");
        Assert.assertEquals(objHomePage.getYourUsername(),tUsername);
        Logger.log("Check the presence of news feed");
        Assert.assertTrue(objHomePage.getNewsFeed().isDisplayed());

        Logger.log("Start writing new tweet");
        TwitterActions.startWritingNewTweet(objHomePage);
        Logger.log("Check that Tweet form is opened");
        Assert.assertTrue(objHomePage.getNewTweetInfo().isDisplayed());
        Logger.log("Compare actual twitter characters left with expected");
        Assert.assertEquals(objHomePage.getTweetCharactersCount(), tweetMaxLength);
        Logger.log("Check that Tweet button is disabled");
        Assert.assertFalse(objHomePage.getSendTweetButton().isEnabled());
        Logger.log("Check that Add Media button is present");
        Assert.assertTrue(objHomePage.getNewTweetAddMediaButton().isDisplayed());
        Logger.log("Check that Add Poll button is present");
        Assert.assertTrue(objHomePage.getNewTweetAddPollButton().isDisplayed());
        Logger.log("Check that Add Location button is present");
        Assert.assertTrue(objHomePage.getNewTweetAddLocationButton().isDisplayed());

        Logger.log("Add a MESSAGE to the tweet");
        TwitterActions.addTextToTweet(MESSAGE,objHomePage);
        Logger.log("Check that twitter characters left are reduced by lenght of the MESSAGE");
        Assert.assertEquals(objHomePage.getTweetCharactersCount(),(tweetMaxLength-MESSAGE.length()));
        Logger.log("Check that Tweet button is enabled");
        Assert.assertTrue(objHomePage.getSendTweetButton().isEnabled());

        Logger.log("Send Tweet");
        TwitterActions.sendTweet(objHomePage);
        Logger.log("Wait for New Tweet form is disappeared");
        objHomePage.waitTweetFormInvisibility();
        Logger.log("Check that New Tweet from is not visible");
        Assert.assertFalse(objHomePage.getSendTweetButton().isDisplayed());
        Logger.log("Compare actual tweet message with expected");
        Assert.assertEquals(objHomePage.getFirstTimelineTweet().getText(),MESSAGE);
        int tweetCount = Integer.parseInt(objHomePage.getTweetCounter().getText());
        Logger.log("Reload page");
        objHomePage.refreshPage();
        Logger.log("Check that tweet counter is increased by 1");
        Assert.assertEquals(Integer.parseInt(objHomePage.getTweetCounter().getText()),(tweetCount+1));
    }

}
