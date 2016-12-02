package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import utility.SendEmail;
import org.apache.log4j.PropertyConfigurator;

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
        PropertyConfigurator.configure("C:\\Users\\evilplane\\IdeaProjects\\twitterMavenTests2\\log4j.properties");
        if (browser.equalsIgnoreCase("chrome"))
        {
            //File file = new File(".\\driver\\chromedriver.exe");
            //System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
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
