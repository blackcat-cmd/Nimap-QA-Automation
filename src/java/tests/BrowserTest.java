package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.DriverFactory;

public class BrowserTest {

    @Test
    public void browserLaunchTest() {

        DriverFactory.initializeDriver();

        String currentUrl =
                DriverFactory.driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains(
                        "test.fieldforceconnect.com"
                ),
                "Field Force Connect website did not open."
        );

        System.out.println(
                "Browser launch test PASS: " + currentUrl
        );
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
