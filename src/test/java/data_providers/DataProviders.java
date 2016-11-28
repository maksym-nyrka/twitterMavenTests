package data_providers;

import org.testng.annotations.DataProvider;

/**
 * Created on 21/11/2016 at 17:02.
 */
public class DataProviders {
    @DataProvider(name = "logins")
    public static Object[][] createData()
    {
        return new Object[][]
                {
                        {"automation2005","qatestlab2005"},
                       // {"automation20052","qatestlab2005"} - этот аккаунт Твиттер заблокировал :(
                };
    }
    @DataProvider(name = "tweet-numbet-to-unretweet")
    public static Object[][] createData2()
    {
        return new Object[][]
                {
                        {1}
                };
    }

}
