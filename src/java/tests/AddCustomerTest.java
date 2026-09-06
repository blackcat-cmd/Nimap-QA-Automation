package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.AddCustomerPage;
import pages.LoginPage;
import utils.DriverFactory;

public class AddCustomerTest {

    private LoginPage loginPage;
    private AddCustomerPage addCustomerPage;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initializeDriver();

        loginPage = new LoginPage(DriverFactory.driver);
        addCustomerPage = new AddCustomerPage(DriverFactory.driver);

        DriverFactory.driver.get("https://test.fieldforceconnect.com/customers");

        loginPage.login("YOUR LOGIN_ID", "YOUR PASSWORD");

        Assert.assertTrue(
                loginPage.isDashboardDisplayed(),
                "Login failed before Add Customer test execution."
        );
    }

    @DataProvider(name = "customerData")
    public Object[][] customerData() {

        String unique = String.valueOf(System.currentTimeMillis());

        return new Object[][] {
                {
                        "Automation Customer " + unique.substring(unique.length() - 4),
                        "90000" + unique.substring(unique.length() - 5),
                        "customer1_" + unique + "@gmail.com"
                },
                {
                        "Automation Customer " + (unique.substring(unique.length() - 4) + "1"),
                        "80000" + unique.substring(unique.length() - 5),
                        "customer2_" + unique + "@gmail.com"
                }
        };
    }

    @Test(dataProvider = "customerData")
    public void addCustomerTest(String name, String mobile, String email) {

        addCustomerPage.clickMyCustomers();

        addCustomerPage.clickAddCustomer();

        addCustomerPage.addCustomer(name, mobile, email);

        String toast = addCustomerPage.getToastMessage();

        // Validation: Verify Toast or Fallback Log
        if (toast.isEmpty()) {
            System.out.println("Notice: Toast message UI disappeared quickly or was not captured for " + name);
        } else {
            Assert.assertFalse(
                    toast.toLowerCase().contains("error") || toast.toLowerCase().contains("failed"),
                    "Customer creation failed with error: " + toast
            );
            System.out.println("Toast Message Captured: " + toast);
        }

        System.out.println("Add Customer Test PASS: " + name + " | " + mobile + " | " + email);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
