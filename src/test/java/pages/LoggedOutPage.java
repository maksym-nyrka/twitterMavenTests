package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created on 21/11/2016 at 15:37.
 */
public class LoggedOutPage extends BasePage{

    @FindBy(id = "signin-link")
    WebElement loginButton;

    public LoggedOutPage(WebDriver driver)
    {
       super(driver);
    }

    public WebElement getLoginButton()
    {
        return loginButton;
    }
}
