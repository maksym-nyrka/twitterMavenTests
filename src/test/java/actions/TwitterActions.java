package actions;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.UserPage;
import utility.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created on 22/11/2016 at 10:27.
 */
public class TwitterActions {

    public static void loginToTwitter(String name, String pass, LoginPage loginPage) {
        Logger.log("Enter twitter username");
        loginPage.setUsername(name);
        Logger.log("Enter twitter password");
        loginPage.setPassword(pass);
        Logger.log("Click login button");
        loginPage.clickLoginButton();
    }

    public static void logoutFromTwitter(HomePage homePage) {
        homePage.click(homePage.getLogoutButton());
    }

    public static void startWritingNewTweet(HomePage homePage) {
        homePage.click(homePage.getNewTweetButton());
    }

    public static void addTextToTweet(String msg, HomePage homePage) {
        homePage.type(msg, homePage.getTweetTextBox());
    }

    public static void sendTweet(HomePage homePage) {
        homePage.click(homePage.getSendTweetButton());
    }

    public static void goToFollowingPage(HomePage homePage) {
        homePage.click(homePage.getFollowingCounterButton());
    }

    //Remember: no pain - no gain
    /*public static void foo(WebDriver driver, UserPage userPage)
    {
        boolean isFound=false;
        int i=0;
        By by;
        WebElement tweetStampElement;
        while (!isFound)
        {
            try {
                by = userPage.getTweetTimestamp(++i);
                userPage.waitForVisibility(by);
                tweetStampElement=driver.findElement(by);
                if (i==100)
                    isFound=true;
            } catch (WebDriverException e) {
                by = userPage.getTweetTimestamp(++i);
                userPage.scrollToElement(driver.findElement(by));
                userPage.waitForVisibility(by);
                tweetStampElement=driver.findElement(by);
            }
            Timestamp timestamp = new Timestamp(Long.parseLong(tweetStampElement.getAttribute("data-time-ms")));
            System.out.println(i+"  "+timestamp);
        }
    }


    public static void checkTweetOlderThan24Hours(UserPage userPage)
    {
        Timestamp timestamp;
        for (int i=1;i<100;i++) {
            timestamp = new Timestamp(System.currentTimeMillis());
            Timestamp timeMills = userPage.getTweetTimestamp(i);
            System.out.println("Cur:" + timestamp + " Tw:" + timeMills);
        }
    }
    public static void waitForElementToBeClickable(WebElement el, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5, 200);
        wait.until(ExpectedConditions.invisibilityOfElementLocated
                (By.className("loader")));
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }
    public static void scrollToElement(WebElement el, WebDriver driver) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", el);
        }
    }
    public static void waitAnd(WebElement el, WebDriver driver) {
        try {
            waitForElementToBeClickable(el,driver);
            System.out.println(el.getAttribute("data-time-ms"));
        } catch (WebDriverException e) {
            scrollToElement(el, driver);
            waitForElementToBeClickable(el, driver);
            System.out.println(el.getAttribute("data-time-ms"));
        }
    }

    public static void bar(UserPage userPage, WebDriver driver)
    {
        WebElement element;
        for (int i=1;i<100;i++)
        {
            element = driver.findElement(userPage.getTweetTimestamp(i));
            TwitterActions.waitAnd(element,driver);
        }

    }

    public static void tweetTimestamps(int hours, UserPage userPage) {
        WebElement timestamp;
        for (int i = 1; i < 100; i++) {
            timestamp = userPage.getTweetTimestampElem(i);
            userPage.scrollToElement(timestamp);
            Timestamp tweetTimestamp = new Timestamp(Long.parseLong(timestamp.getAttribute("data-time-ms")));
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            if (compareTwoTimeStamps(currentTimestamp, tweetTimestamp) > hours) {
                retweet(i, userPage);
                return;
            }
        }
    }

    public static void retweet(int tweetNumber, UserPage userPage) {
        String buttonXpath = "//div[@class='modal-content']//button[contains(@class, 'retweet-action')]";
        WebElement retweetBtn = userPage.getTweetRetweetButton(tweetNumber);
        userPage.click(retweetBtn);
        userPage.waitForVisibility(By.xpath(buttonXpath));
        int retweetCounter = Integer.parseInt(retweetBtn.getText());
        userPage.click(userPage.getDialogRetweetButton());
        userPage.waitForInvisibility(By.xpath(buttonXpath));
        WebElement retweetBtn2 = userPage.getTweetRetweetButton(tweetNumber);
        System.out.println(retweetBtn2.getText());
        System.out.println(Integer.parseInt(retweetBtn2.getText()) - retweetCounter);
    }

    */
    public static long compareTwoTimeStamps(Timestamp currentTime, Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 60 * 1000);
    }

    public static WebElement doRetweetIfTweetOlderThan(int hours, UserPage userPage)
    {
        List<WebElement> tweets = userPage.getTweets();
        boolean isRetweeted=false;
        WebElement tweet=null;
        while (!isRetweeted) {
            for (int i = 1; i < tweets.size(); i++) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                if (!tweets.get(i).getAttribute("class").contains("js-pinned")
                      &&  userPage.getRetweetsNumberButton(tweets.get(i)).isDisplayed()
                      &&  compareTwoTimeStamps(currentTimestamp, userPage.getTweetTimestamp(tweets.get(i))) > hours
                        ) {
                    tweet = retweetSequence(tweets.get(i), userPage);
                    isRetweeted = true;
                    break;
                }
                if (i==tweets.size()-1)
                {
                    userPage.scrollToElement(tweets.get(tweets.size() - 1));
                    tweets = userPage.getTweets();
                }
            }
        }
        return tweet;
    }
    public static WebElement retweetSequence(WebElement tweet, UserPage userPage)
    {
        Logger.log("Click Retweet button");
        userPage.click(userPage.getRetweetsNumberButton(tweet));
        int retwBefore = retweetsCountBefore(tweet,userPage);
        Timestamp timestampBefore = userPage.getTweetTimestamp(tweet);
        Logger.log("Do retweet");
        doRetweet(userPage);
        int retwAfter = retweetsCountAfter(tweet,userPage);
        Timestamp timestampAfter = userPage.getTweetTimestamp(tweet);
        Logger.log("Check that before retweet counter is greater by 1 than after retweet counter");
        Assert.assertEquals(retwBefore+1,retwAfter);
        Logger.log("Check that before tweet timestamp is equals to after tweet timestamp");
        Assert.assertEquals(timestampAfter,timestampBefore);
        return tweet;
    }
    public static int retweetsCountBefore(WebElement tweet, UserPage userPage)
    {
        return Integer.parseInt(userPage.getRetweetsNumberButton(tweet).getText());
    }
    public static void doRetweet(UserPage userPage)
    {
        String buttonXpath = "//div[@class='modal-content']//button[contains(@class, 'retweet-action')]";
        String retweetDialogWindow = "//div[@class='RetweetDialog modal-container tweet-showing']";

        userPage.waitForVisibility(By.xpath(buttonXpath));
        userPage.click(userPage.getDialogRetweetButton());
        userPage.waitForInvisibility(By.xpath(retweetDialogWindow));
    }
    public static int retweetsCountAfter(WebElement tweet,UserPage userPage)
    {
        return Integer.parseInt(userPage.getUnretweetNumberButton(tweet).getText());
    }
    public static WebElement getProfileTweet(int number, ProfilePage profilePage) {
        return profilePage.getTweetByNumber(number);
    }

    public static WebElement unretweet(int tweetNumber, ProfilePage profilePage)
    {
        String momentsXPath = "//li[contains(@class,'ProfileNav-item--moments')]";
        profilePage.waitForVisibility(By.xpath(momentsXPath));

        List<WebElement> tweets = profilePage.getTweets();
        boolean isUnretweeted=false;
        WebElement tweet=null;
        int counter=0;

        while (!isUnretweeted) {
            for (int i = 0; i < tweets.size(); i++) {
                tweet=tweets.get(i);
                if (profilePage.getUnretweetNumberButton(tweet).isDisplayed()
                        && ++counter == tweetNumber) {
                    profilePage.click(profilePage.getUnretweetNumberButton(tweet));
                    isUnretweeted = true;
                    break;
                }
                if (i==tweets.size()-1)
                {
                    profilePage.scrollToElement(tweets.get(tweets.size() - 1));
                    tweets = profilePage.getTweets();
                }
            }
        }
        return tweet;
    }

    public static WebElement findTweetById( String id, int tweetCount, ProfilePage profilePage)
    {
        List<WebElement> tweets = profilePage.getTweets();
        boolean isFound=false;
        WebElement tweet=null;

        while (!isFound) {
            for (int i = 0; i < tweets.size(); i++) {
                if (i==tweetCount)
                {
                    return null;
                }
                tweet=tweets.get(i);
                if (profilePage.getTweetId(tweet).equals(id)) {
                    isFound = true;
                    break;
                }
                if (i==tweets.size()-1)
                {
                    profilePage.scrollToElement(tweets.get(tweets.size() - 1));
                    tweets = profilePage.getTweets();
                }
            }
        }
        return tweet;
    }

    public static WebElement findTweetByIdUserPage( String id, UserPage userPage)
    {
        List<WebElement> tweets = userPage.getTweets();
        boolean isFound=false;
        WebElement tweet=null;

        while (!isFound) {
            for (int i = 0; i < tweets.size(); i++) {
                tweet=tweets.get(i);
                if (userPage.getTweetId(tweet).equals(id)) {
                    isFound = true;
                    break;
                }
                if (i==tweets.size()-1)
                {
                    userPage.scrollToElement(tweets.get(tweets.size() - 1));
                    tweets = userPage.getTweets();
                }
            }
        }
        return tweet;
    }

}
