package tests;

import actions.TwitterActions;
import data_providers.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoggedOutPage;
import pages.LoginPage;
import ru.yandex.qatools.allure.annotations.Step;
import utility.Logger;

/**
 * Created on 23/11/2016 at 10:10.
 */
@Listeners({utility.UtilityListener.class})
public class FailLoginTest extends BaseTest{

    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private LoggedOutPage objLoggedOutPage;

    private final String twitterLink = "https://twitter.com/";

    @Test(dataProvider = "logins", dataProviderClass = DataProviders.class)
    public void testFailLogin(String tUsername, String tPassword)
    {
        driver.get(twitterLink);

        loginToTwitterAccount(tUsername, tPassword+"fail");
        openHomePage();
        compareUsername(tUsername);
        checkPresenceOfNewsFeed();
        logoutFromTwitter();
        logoutPageIsLoaded();
        assertPresenceOfLoginButton();
    }
    @Step("Login to twitter account")
    public void loginToTwitterAccount(String tUsername, String tPassword)
    {
        Logger.log("Login to twitter account");
        objLoginPage = new LoginPage(driver);
        TwitterActions.loginToTwitter(tUsername,tPassword,objLoginPage);
    }
    @Step("Home page is opened")
    public void openHomePage()
    {
        Logger.log("Home page is opened");
        objHomePage = new HomePage(driver);
    }
    @Step("Compare actual username with expected")
    public void compareUsername(String tUsername)
    {
        Logger.log("Compare actual username with expected");
        Assert.assertEquals(objHomePage.getYourUsername(),tUsername);
    }
    @Step("Check the presence of news feed")
    public void checkPresenceOfNewsFeed()
    {
        Logger.log("Check the presence of news feed");
        Assert.assertTrue(objHomePage.getNewsFeed().isDisplayed());
    }
    @Step("Logging out from twitter")
    public void logoutFromTwitter()
    {
        Logger.log("Logging out from twitter");
        TwitterActions.logoutFromTwitter(objHomePage);
    }
    @Step("Logout page is opened")
    public void logoutPageIsLoaded()
    {
        Logger.log("Logout page is opened");
        objLoggedOutPage = new LoggedOutPage(driver);
    }
    @Step("Check the presence of login button")
    public void assertPresenceOfLoginButton()
    {
        Logger.log("Check the presence of login button");
        Assert.assertTrue(objLoggedOutPage.getLoginButton().isDisplayed());
    }
}