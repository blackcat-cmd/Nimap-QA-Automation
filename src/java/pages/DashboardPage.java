package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardMenu =
            By.xpath("//a[@href='/' and .//span[normalize-space()='Dashboard']]");

    private final By attendanceMenu =
            By.xpath("//a[@href='/attendance' and .//span[normalize-space()='Attendance']]");

    private final By customersMenu =
            By.xpath("//a[@href='/' and .//span[normalize-space()='My Customers']]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isDashboardDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(dashboardMenu)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAttendance() {
        wait.until(
                ExpectedConditions.elementToBeClickable(attendanceMenu)
        ).click();
    }

    public void clickMyCustomers() {
        wait.until(
                ExpectedConditions.elementToBeClickable(customersMenu)
        ).click();
    }
}
