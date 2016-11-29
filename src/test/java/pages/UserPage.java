package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 23/11/2016 at 15:49.
 */
public class UserPage extends BasePage {

    //@FindBy(xpath = "//button[contains(@class, 'btn primary-btn retweet-action')]")
    @FindBy(xpath = "//form/div[2]/div[3]/button")
    WebElement dialogRetweetButton;


    public UserPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public WebElement getTweetTimestampElem(int tweetNumber)
    {
        return driver.findElement(By.xpath("//li["+tweetNumber+"]//small/a/span"));
    }

    public WebElement getDialogRetweetButton() {
        return dialogRetweetButton;
    }

    //new
    public WebElement getRetweetsNumberButton(WebElement tweet)
    {
        return tweet.findElement(By.xpath(".//button[@class='ProfileTweet-actionButton  js-actionButton js-actionRetweet']/div[2]/span/span"));
    }
    public WebElement getUnretweetNumberButton(WebElement tweet)
    {
        return tweet.findElement(By.xpath(".//button[@class='ProfileTweet-actionButtonUndo js-actionButton js-actionRetweet']/div[2]/span/span"));
    }
    public List<WebElement> getTweets()
    {
        return driver.findElements(By.xpath("//ol[@id='stream-items-id']/li"));
    }
    public Timestamp getTweetTimestamp(WebElement tweet)
    {
        WebElement timestampElement = tweet.findElement(By.xpath(".//span[contains(@class,'_timestamp')]"));
        return new Timestamp(Long.parseLong(timestampElement.getAttribute("data-time-ms")));
    }
    public WebElement getTweetByNumber(int tweetNumber)
    {
        return driver.findElement(By.xpath("//ol[@id='stream-items-id']/li["+tweetNumber+"]"));
    }
    public String getTweetContent(WebElement tweet)
    {
        return tweet.findElement(By.xpath(".//p[contains(@class,'TweetTextSize')]")).getText();
    }
    public String getTweetId(WebElement tweet)
    {
        return tweet.getAttribute("data-item-id");
    }

}
