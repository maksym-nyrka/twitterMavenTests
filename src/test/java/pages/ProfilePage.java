package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created on 24/11/2016 at 16:19.
 */
public class ProfilePage extends BasePage{


    @FindBy(xpath = "//li[contains(@class,'ProfileNav-item--tweets')]//span[@class='ProfileNav-value']")
    WebElement tweetCounter;

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public WebElement getTweetCounter()
    {
        return tweetCounter;
    }

    public List<WebElement> getTweets()
    {
        return driver.findElements(By.xpath("//ol[@id='stream-items-id']/li"));
    }

    public WebElement getRetweetsNumberButton(WebElement tweet)
    {
        return tweet.findElement(By.xpath(".//button[@class='ProfileTweet-actionButton  js-actionButton js-actionRetweet']/div[2]/span/span"));
    }
    public WebElement getUnretweetNumberButton(WebElement tweet)
    {
        return tweet.findElement(By.xpath(".//button[@class='ProfileTweet-actionButtonUndo js-actionButton js-actionRetweet']/div[2]/span/span"));
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
    public boolean isRetweeted(WebElement tweet)
    {
        return driver.findElement(By.xpath("//div[contains(@class,'retweeted')]")).isDisplayed();
    }

    public String getTweetId(WebElement tweet)
    {
        return tweet.getAttribute("data-item-id");
    }

    public String getRetweetedTweetUserLink(WebElement tweet)
    {
        return tweet.findElement(By.xpath("//a[contains(@class,'js-user-profile-link js-nav')]")).getAttribute("href");
    }
}
