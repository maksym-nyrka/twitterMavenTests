package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created on 21/11/2016 at 14:07.
 */
public class LoginPage extends BasePage{

    @FindBy(xpath = "//input[@id='signin-email']")
    WebElement username;

    @FindBy(id="signin-password")
    WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    @FindBy(xpath = "//div[@id='front-container']/div[2]/div/div/h1")
    WebElement title;

    public LoginPage(WebDriver driver)
    {
        super(driver);
    }

    public void setUsername(String name)
    {
        super.type(name,username);
    }

    public void setPassword(String pass)
    {
        super.type(pass,password);
    }

    public void clickLoginButton()
    {
        super.click(loginButton);
    }

    public String getPageTitle()
    {
        return super.getText(title);
    }

}
