package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tests.BaseTest;

/**
 * Created on 23/11/2016 at 15:42.
 */
public class FollowingPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Sean Hannity')]")
    WebElement seanHannityProfileLink;

    public FollowingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public WebElement getSeanHannityProfileLink() {
        return seanHannityProfileLink;
    }

}
