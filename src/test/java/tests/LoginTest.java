package tests;

import actions.TwitterActions;
import data_providers.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoggedOutPage;
import pages.LoginPage;
import utility.Logger;


/**
 * Created on 21/11/2016 at 14:09.
 */
public class LoginTest extends BaseTest{

    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private LoggedOutPage objLoggedOutPage;

    private final String twitterLink = "https://twitter.com/";

    @Test(dataProvider = "logins", dataProviderClass = DataProviders.class)
    public void testLogin(String tUsername, String tPassword)
    {
        driver.get(twitterLink);

        objLoginPage = new LoginPage(driver);
        Logger.log("Login to twitter account");
        TwitterActions.loginToTwitter(tUsername,tPassword,objLoginPage);

        Logger.log("Home page is opened");
        objHomePage = new HomePage(driver);
        Logger.log("Compare actual username with expected");
        Assert.assertEquals(objHomePage.getYourUsername(),tUsername);
        Logger.log("Check the presence of news feed");
        Assert.assertTrue(objHomePage.getNewsFeed().isDisplayed());

        Logger.log("Logging out from twitter");
        TwitterActions.logoutFromTwitter(objHomePage);

        Logger.log("Logout page is opened");
        objLoggedOutPage = new LoggedOutPage(driver);

        Logger.log("Check the presence of login button");
        Assert.assertTrue(objLoggedOutPage.getLoginButton().isDisplayed());

    }
}

