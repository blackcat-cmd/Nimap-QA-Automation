package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // =========================
    // Locators
    // =========================

    // Multi-fallback locator for Username
    private final By usernameField = By.xpath(
            "//input[@name='username' or @name='email' or @type='text' or contains(@placeholder,'Email') or contains(@placeholder,'Username')]"
    );

    // Multi-fallback locator for Password
    private final By passwordField = By.xpath(
            "//input[@name='password' or @type='password']"
    );

    // Sign In button (Handles 'Sign In', 'Login', or submit types)
    private final By signInButton = By.xpath(
            "//button[@type='submit' or .//span[text()='Sign In'] or contains(.,'Sign In') or contains(.,'Login')]"
    );

    // Dashboard menu indicator
    private final By dashboardElement = By.xpath(
            "//*[normalize-space()='Dashboard']"
    );

    // =========================
    // Constructor
    // =========================

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // =========================
    // Page Actions
    // =========================

    public void enterUsername(String username) {
        WebElement userEl = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        userEl.clear();
        userEl.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passEl = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passEl.clear();
        passEl.sendKeys(password);
    }

    public void clickSignIn() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        btn.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isDashboardDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardElement)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}