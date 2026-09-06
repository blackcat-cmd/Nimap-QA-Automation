package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddCustomerPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Navigation Locators
    private final By myCustomersMenu = By.xpath("//a[contains(@class,'_navLink') and .//span[text()='My Customers']] | //span[text()='My Customers']");
    private final By myCustomerSubMenu = By.xpath("//a[contains(@href,'/customers') and .//span[text()='My Customer']] | //a[contains(@href,'/customers')]");

    // Action Dropdown & Option (Exact Top-Right Manage Button)
    private final By manageDropdown = By.xpath("//button[contains(normalize-space(.),'Manage')]");
    private final By addCustomerOption = By.xpath("//*[contains(text(),'Add Customer') or contains(text(),'Create Customer') or contains(text(),'New Customer')]");

    // Modal Form Locators
    private final By leadNameField = By.xpath("//input[@name='LeadName' or contains(@placeholder,'Lead/Customer Name') or contains(@placeholder,'Name')]");
    private final By mobileNoField = By.xpath("//input[@name='MobileNo' or contains(@placeholder,'Mobile No') or contains(@placeholder,'Mobile')]");
    private final By emailField = By.xpath("//input[@name='Email' or contains(@placeholder,'Email')]");
    private final By saveButton = By.xpath("//button[contains(normalize-space(.),'Save') or contains(normalize-space(.),'Submit')]");

    // Toast Notification Locator (Comprehensive)
    private final By toast = By.xpath("//*[contains(@class,'Toastify__toast-body')] | //*[contains(@class,'Toastify__toast')] | //*[@role='alert'] | //*[contains(@class,'MuiAlert-message')] | //*[contains(@class,'toast')]");

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickMyCustomers() {
        try {
            WebElement parentMenu = wait.until(ExpectedConditions.presenceOfElementLocated(myCustomersMenu));
            safeClick(parentMenu);

            if (!driver.findElements(myCustomerSubMenu).isEmpty()) {
                WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(myCustomerSubMenu));
                safeClick(subMenu);
            }
        } catch (Exception e) {
            System.out.println("Navigation Warning: " + e.getMessage());
        }
    }

    public void clickAddCustomer() {
        try {
            // Force click Manage button
            WebElement manageBtn = wait.until(ExpectedConditions.elementToBeClickable(manageDropdown));
            safeClick(manageBtn);

            // Click Add Customer option from dropdown
            WebElement addOption = wait.until(ExpectedConditions.elementToBeClickable(addCustomerOption));
            safeClick(addOption);
        } catch (Exception e) {
            System.out.println("Could not click Manage/Add Customer dropdown: " + e.getMessage());
        }
    }

    public void enterCustomerName(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(leadNameField));
        element.clear();
        element.sendKeys(name);
    }

    public void enterMobileNumber(String mobile) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNoField));
        element.clear();
        element.sendKeys(mobile);
    }

    public void enterEmail(String customerEmail) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        element.clear();
        element.sendKeys(customerEmail);
    }

    public void clickSave() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
        safeClick(element);
    }

    public void addCustomer(String name, String mobile, String customerEmail) {
        enterCustomerName(name);
        enterMobileNumber(mobile);
        enterEmail(customerEmail);
        clickSave();
    }

    public String getToastMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement toastElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(toast));
            return toastElement.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    private void safeClick(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}