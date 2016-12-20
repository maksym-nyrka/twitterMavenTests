package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created on 22/11/2016 at 10:15.
 */
abstract public class BasePage {

    WebDriver driver;

    public BasePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void type(String msg, WebElement webElement)
    {
        webElement.sendKeys(msg);
    }
    public void click(WebElement webElement)
    {
        webElement.click();
    }
    public String getText(WebElement webElement)
    {
        return webElement.getText();
    }
    public WebElement waitForVisibility(By by)
    {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public WebElement waitForElementVisible(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver, 5, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.className("loader")));
        return element;
    }
    public boolean waitForInvisibility(By by)
    {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    public void waitForPageIsLoaded()
    {
        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }
    public void refreshPage()
    {
        driver.navigate().refresh();
    }

    public void scrollToElement(WebElement el) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", el);
        }
    }
}
