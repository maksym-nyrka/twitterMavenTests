package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import ru.yandex.qatools.allure.annotations.Attachment;
import tests.BaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 23/11/2016 at 10:25.
 */
public class UtilityListener implements ITestListener{

    WebDriver driver;
    private String filePath = "test-output"+File.separatorChar+"html"+File.separatorChar+"screenshots";

    private static String fileName = "report.txt";
    List<String> lines = new ArrayList<>();

    public void onTestFailure(ITestResult iTestResult)
    {
        Reporter.setCurrentTestResult(iTestResult);
        Object currentClass = iTestResult.getInstance();
        driver = ((BaseTest) currentClass).getDriverInstance();
        writeToFile(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())+" "+iTestResult.getName()+" -- failed");
        takeScreenshot(iTestResult);

        Reporter.setCurrentTestResult(null);
    }

    public void onTestStart(ITestResult iTestResult) { }

    public void onTestSuccess(ITestResult iTestResult)
    {
        writeToFile(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())+" "+iTestResult.getName()+" -- passed");
    }

    public void onTestSkipped(ITestResult iTestResult)
    {
        writeToFile(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())+" "+iTestResult.getName()+" -- skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) { }

    public void onStart(ITestContext iTestContext) { }

    public void onFinish(ITestContext iTestContext) { }

    public void takeScreenshot(ITestResult iTestResult)
    {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        new File(filePath).mkdirs();

        String failMethodName = iTestResult.getMethod().getMethodName();
        String screenshotFileName = failMethodName+"_"+new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) + ".png";

        File file = new File("");
        try {
            FileUtils.copyFile(scrFile, new File(file.getAbsolutePath()
                    + File.separatorChar + filePath + File.separatorChar + screenshotFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.log("<br><a href=\"screenshots\\" + screenshotFileName
                + "\"><img src=\"screenshots\\" + screenshotFileName + "\" alt=\"Screenshot\" height='200' width='350'/>");

        byte[] bFile = new byte[(int) scrFile.length()];
        try(FileInputStream fileInputStream = new FileInputStream(scrFile)) {
            fileInputStream.read(bFile);
        }catch(Exception e){
            e.printStackTrace();
        }
        saveScreenshot(bFile);
    }
    public void writeToFile(String s)
    {
        lines.add(s);

        Path file = Paths.get(fileName);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }
}
