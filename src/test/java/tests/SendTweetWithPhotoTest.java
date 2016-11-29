package tests;

/**
 * Created on 28/11/2016 at 15:38.
 */

import actions.TwitterActions;
import data_providers.DataProviders;
import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import utility.Logger;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


public class SendTweetWithPhotoTest extends BaseTest{

    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private ProfilePage objProfilePage;

    private final String twitterLink = "https://twitter.com/";
    public final int tweetMaxLength = 140;
    public final int OK_SERVER_STATUS = 200;

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
    @Test(dependsOnMethods = "testLogin", dataProvider = "tweet-message-with-picture", dataProviderClass = DataProviders.class)
    public void testUndoRetweet(String MESSAGE, String PICTURE_NAME)
    {
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

        objHomePage.click(objHomePage.getAddPhotoButton());
        //try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        String pictureThumbnailXPath = "//div[@class='ComposerThumbnail-imageContainer']//img[1]";
        StringSelection ss = new StringSelection(PICTURE_NAME);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        try {
            Robot robot = new Robot();
            robot.setAutoDelay(500);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (AWTException e) {
            e.printStackTrace();
        }
        objHomePage.waitForVisibility(By.xpath(pictureThumbnailXPath));
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

        Logger.log("Go to Profile page");
        objHomePage.click(objHomePage.getTweetCounter());
        objProfilePage = new ProfilePage(driver);
        Logger.log("Get first tweet photo URL");
        WebElement tweet = objProfilePage.getTweetByNumber(1);
        Logger.log("Compare actual photo URL server response code with expected");
        Assert.assertEquals(TwitterActions.linkResponseCode(objProfilePage.getTweetPhotoUrl(tweet)),OK_SERVER_STATUS);
    }

}

