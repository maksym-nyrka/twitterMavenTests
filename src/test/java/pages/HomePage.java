package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created on 21/11/2016 at 14:08.
 */
public class HomePage extends BasePage{

    @FindBy(xpath = "//div[@id='page-container']/div/div/div/div[2]/span/a/span")
    WebElement username;

    // Так норм? (Для объектов, которые нужно ждать)
    By newsFeedBy = By.id("stream-items-id");

    By logoutButtonBy = By.id("signout-button");

    By newTweetInfo = By.id("global-tweet-dialog-header");

    @FindBy(id = "user-dropdown-toggle")
    WebElement profileButton;

    @FindBy(xpath = "//button[@id='global-new-tweet-button']")
    WebElement newTweetButton;

    @FindBy(xpath = "//div[4]/form/div[2]/div[2]/span[2]")
    WebElement tweetCharactersCount;

    @FindBy(xpath = "//div[4]/form/div[2]/div[2]/button")
    WebElement sendTweetButton;

    @FindBy(xpath = "//div[@class='DashboardProfileCard-content']/div[3]//li[1]//span[2]")
    WebElement tweetCounter;

    @FindBy(xpath = "//div[@id='tweet-box-global']")
    WebElement tweetTextBox;

    @FindBy(xpath = "//div[@id='global-tweet-dialog-dialog']/div[2]/div[4]/form/div[2]/div/span[1]/div/button")
    WebElement newTweetAddMediaButton;

    @FindBy(xpath = "//div[@id='global-tweet-dialog-dialog']/div[2]/div[4]/form/div[2]/div/span[3]/div/button")
    WebElement newTweetAddPollButton;

    @FindBy(xpath = "//div[@id='global-tweet-dialog-dialog']/div[2]/div[4]/form/div[2]/div/span[4]/div/button")
    WebElement newTweetAddLocationButton;

    @FindBy(xpath = "//ol[contains(@class, 'stream-items')]/li[1]//p")
    WebElement firstTimelineTweet;

    @FindBy(xpath = "//div[3]/ul/li[2]/a/span[2]")
    WebElement followingCounterButton;


    public HomePage(WebDriver driver)
    {
        super(driver);
    }

    public String getYourUsername()
    {
        return super.getText(username);
    }

    public WebElement getNewsFeed()
    {
        return super.waitForVisibility(newsFeedBy);
    }

    public WebElement getLogoutButton()
    {
        super.waitForPageIsLoaded();
        super.click(profileButton);
        return  super.waitForVisibility(logoutButtonBy);
    }

    public WebElement getNewTweetButton()
    {
        return newTweetButton;
    }

    public int getTweetCharactersCount()
    {
        return Integer.parseInt(super.getText(tweetCharactersCount));
    }
    public WebElement getSendTweetButton()
    {
        return sendTweetButton;
    }

    public WebElement getTweetCounter()
    {
        return tweetCounter;
    }
    public WebElement getTweetTextBox()
    {
        return tweetTextBox;
    }
    public WebElement getNewTweetInfo()
    {
        return super.waitForVisibility(newTweetInfo);
    }
    public boolean waitTweetFormInvisibility()
    {
        return super.waitForInvisibility(newTweetInfo);
    }

    public WebElement getNewTweetAddMediaButton() {
        return newTweetAddMediaButton;
    }

    public WebElement getNewTweetAddPollButton() {
        return newTweetAddPollButton;
    }

    public WebElement getNewTweetAddLocationButton() {
        return newTweetAddLocationButton;
    }

    public WebElement getFirstTimelineTweet() {
        return firstTimelineTweet;
    }

    public WebElement getFollowingCounterButton()
    {
        return followingCounterButton;
    }

}

