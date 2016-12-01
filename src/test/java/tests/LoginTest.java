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
 * Created on 21/11/2016 at 14:09.
 */
@Listeners({utility.UtilityListener.class})
public class LoginTest extends BaseTest{

    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private LoggedOutPage objLoggedOutPage;

    private final String twitterLink = "https://twitter.com/";

    @Test(dataProvider = "logins", dataProviderClass = DataProviders.class)
    public void testLogin(String tUsername, String tPassword)
    {
        driver.get(twitterLink);

        loginToTwitterAccount(tUsername, tPassword);
        openHomePage();
        compareUsername(tUsername);
        checkPresenceOfNewsFeed();
        logoutFromTwitter();
        logoutPageIsLoaded();
        assertPresenceOfLoginButton();
    }
    @Step("Login to twitter account")
    private void loginToTwitterAccount(String tUsername, String tPassword)
    {
        Logger.log("Login to twitter account");
        objLoginPage = new LoginPage(driver);
        TwitterActions.loginToTwitter(tUsername,tPassword,objLoginPage);
    }
    @Step("Home page is opened")
    private void openHomePage()
    {
        Logger.log("Home page is opened");
        objHomePage = new HomePage(driver);
    }
    @Step("Compare actual username with expected")
    private void compareUsername(String tUsername)
    {
        Logger.log("Compare actual username with expected");
        Assert.assertEquals(objHomePage.getYourUsername(),tUsername);
    }
    @Step("Check the presence of news feed")
    private void checkPresenceOfNewsFeed()
    {
        Logger.log("Check the presence of news feed");
        Assert.assertTrue(objHomePage.getNewsFeed().isDisplayed());
    }
    @Step("Logging out from twitter")
    private void logoutFromTwitter()
    {
        Logger.log("Logging out from twitter");
        TwitterActions.logoutFromTwitter(objHomePage);
    }
    @Step("Logout page is opened")
    private void logoutPageIsLoaded()
    {
        Logger.log("Logout page is opened");
        objLoggedOutPage = new LoggedOutPage(driver);
    }
    @Step("Check the presence of login button")
    private void assertPresenceOfLoginButton()
    {
        Logger.log("Check the presence of login button");
        Assert.assertTrue(objLoggedOutPage.getLoginButton().isDisplayed());
    }
}

