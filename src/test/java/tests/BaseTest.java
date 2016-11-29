package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import utility.SendEmail;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created on 21/11/2016 at 14:55.
 */
public class BaseTest {

    WebDriver driver;

    public WebDriver getDriverInstance() {
        return this.driver;
    }

    @BeforeClass
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome"))
        {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox"))
        {
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }
    @AfterSuite
    public void sendMail()
    {
        SendEmail.ComposeGmail("automationqatest2005@gmail.com","automationqatest2005@gmail.com");
    }
}
