package utility;

import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 22/11/2016 at 16:57.
 */
public class Logger {
    private final static String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

    public static void log(String msg) {
        System.setProperty(ESCAPE_PROPERTY, "false");
        Reporter.log(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date())+" "+msg+"<br/>");
    }
}
