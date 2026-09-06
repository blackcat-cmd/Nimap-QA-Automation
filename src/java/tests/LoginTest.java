package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import utils.DriverFactory;

public class LoginTest {

    private LoginPage loginPage;

    // =========================
    // Setup
    // =========================

    @BeforeMethod
    public void setUp() {
        DriverFactory.initializeDriver();

        // Page load delay guard (Page Not Found fix)
        try {
            DriverFactory.driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(20));
        } catch (Exception ignored) {}

        loginPage = new LoginPage(DriverFactory.driver);
    }

    // =========================
    // Test Data
    // =========================

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                // Valid Login
                { "harshalpatilgov@gmail.com", "Harshal@512", true },

                // Invalid Username
                { "harshal@gmail.com", "Harshal@512", false },

                // Invalid Password
                { "harshalpatilgov@gmail.com", "Password123", false },

                // Invalid Username + Password
                { "123@gmail.com", "Password123", false }
        };
    }

    // =========================
    // Login Test
    // =========================

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, boolean expectedSuccess) {

        // Perform Login
        loginPage.login(username, password);

        // =========================
        // Valid Login Validation
        // =========================
        if (expectedSuccess) {
            Assert.assertTrue(
                    loginPage.isDashboardDisplayed(),
                    "Valid login failed - Dashboard is not displayed"
            );
            System.out.println("VALID LOGIN : PASS");
        }
        // =========================
        // Invalid Login Validation
        // =========================
        else {
            Assert.assertFalse(
                    loginPage.isDashboardDisplayed(),
                    "Invalid login passed unexpectedly for user: " + username
            );
            System.out.println("INVALID LOGIN EXECUTED : " + username);
        }
    }

    // =========================
    // Tear Down
    // =========================

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}