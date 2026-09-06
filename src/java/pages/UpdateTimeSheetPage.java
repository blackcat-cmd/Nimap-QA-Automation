package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UpdateTimeSheetPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public UpdateTimeSheetPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // =========================
    // LOCATORS
    // =========================

    private final By attendanceMenu =
            By.xpath("//*[normalize-space()='Attendance']");

    private final By teamClaimsMenu =
            By.xpath("//*[normalize-space()='Team Claims']");

    private final By addNewClaimsButton =
            By.xpath("//button[contains(.,'Add New')]");

    private final By updateDialog =
            By.cssSelector("div.MuiDialog-paper");

    private final By reasonTextArea =
            By.cssSelector("textarea[name='reason']");

    private final By saveButton =
            By.xpath("//div[contains(@class,'MuiDialogActions-root')]//button[./span[text()='Save'] or normalize-space()='Save']");

    // =========================
    // NAVIGATION METHODS
    // =========================

    public void clickAttendance() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(attendanceMenu));
        scrollAndClick(element);
        System.out.println("Attendance : CLICKED");
    }

    public void clickTeamClaims() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(teamClaimsMenu));
        scrollAndClick(element);
        System.out.println("Team Claims : CLICKED");
    }

    public void clickAddNewClaims() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addNewClaimsButton));
        scrollAndClick(button);
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateDialog));
        System.out.println("Add New Claims : CLICKED");
    }

    public boolean isUpdateTimeSheetDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(updateDialog)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // =========================
    // DATE SELECTION
    // =========================

    public void enterPunchInDate(String date) {
        System.out.println("Selecting Punch In Date : " + date);
        String day = String.valueOf(Integer.parseInt(date.split("/")[0]));

        WebElement calendarBtn = driver.findElement(By.xpath("//label[contains(.,'Punch In Date')]/following-sibling::div//button"));
        scrollAndClick(calendarBtn);

        sleep(600);

        List<WebElement> dayElements = driver.findElements(
                By.xpath("//*[@role='grid']//*[normalize-space(text())='" + day + "']")
        );

        for (WebElement el : dayElements) {
            if (el.isDisplayed() && !"true".equalsIgnoreCase(el.getAttribute("aria-disabled"))) {
                scrollAndClick(el);
                System.out.println("Calendar Date Selected : " + date);
                return;
            }
        }
        throw new NoSuchElementException("Could not select date: " + date);
    }

    // =========================
    // TIME SELECTION (HH:MM + AM/PM + DONE)
    // =========================

    public void enterPunchInTime(String time) {
        System.out.println("Entering Punch In Time : " + time);
        selectTime(time, 0); // Index 0 = Punch In
    }

    public void enterPunchOutTime(String time) {
        System.out.println("Entering Punch Out Time : " + time);
        selectTime(time, 1); // Index 1 = Punch Out
    }

    private void selectTime(String time24, int index) {
        // Convert 24-hour time to 12-hour format
        String[] parts = time24.split(":");
        int rawHour = Integer.parseInt(parts[0]);
        String minute = parts[1];

        String targetAmPm = rawHour >= 12 ? "PM" : "AM";
        int hour12 = rawHour % 12;
        if (hour12 == 0) hour12 = 12;
        String hourStr = String.format("%02d", hour12);

        // 1. Open Time Picker Popover by clicking Clock Icon
        List<WebElement> clockIcons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[contains(@class,'MuiDialogContent-root')]//input[@placeholder='HH:MM']/following-sibling::div//button")
                )
        );
        scrollAndClick(clockIcons.get(index));
        sleep(500);

        // 2. Locate Popover container
        By popoverLoc = By.cssSelector("div.MuiPopover-paper");
        WebElement popover = wait.until(ExpectedConditions.visibilityOfElementLocated(popoverLoc));

        // 3. Set Hours (HH)
        WebElement hhInput = popover.findElement(By.cssSelector("input[placeholder='HH']"));
        setInputValue(hhInput, hourStr);

        // 4. Set Minutes (MM)
        WebElement mmInput = popover.findElement(By.cssSelector("input[placeholder='MM']"));
        setInputValue(mmInput, minute);

        // 5. Toggle AM/PM if required
        By amPmBtnLoc = By.xpath(".//button[normalize-space()='AM' or normalize-space()='PM']");
        List<WebElement> amPmBtns = popover.findElements(amPmBtnLoc);
        if (!amPmBtns.isEmpty()) {
            WebElement amPmBtn = amPmBtns.get(0);
            if (!amPmBtn.getText().trim().equalsIgnoreCase(targetAmPm)) {
                scrollAndClick(amPmBtn);
                sleep(200);
            }
        }

        // 6. Click "Done" Button
        By doneBtnLoc = By.xpath(".//button[normalize-space()='Done' or contains(text(),'Done')]");
        WebElement doneBtn = wait.until(ExpectedConditions.elementToBeClickable(popover.findElement(doneBtnLoc)));
        scrollAndClick(doneBtn);

        sleep(400);
        System.out.println("Time Selected Successfully: " + hourStr + ":" + minute + " " + targetAmPm);
    }

    // =========================
    // REASON FOR CLAIM
    // =========================

    public void enterReason(String reason) {
        System.out.println("Entering Reason : " + reason);
        WebElement reasonEl = wait.until(ExpectedConditions.presenceOfElementLocated(reasonTextArea));
        scrollIntoView(reasonEl);

        try {
            reasonEl.click();
            reasonEl.sendKeys(Keys.CONTROL + "a");
            reasonEl.sendKeys(Keys.BACK_SPACE);
            reasonEl.sendKeys(reason);
        } catch (Exception e) {
            setInputValue(reasonEl, reason);
        }

        System.out.println("Reason entered successfully");
    }

    // =========================
    // ACTION BUTTONS & TOAST
    // =========================

    public void clickSave() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        scrollAndClick(btn);
        System.out.println("Save : CLICKED");
    }

    public void clickAccept() {
        safeClick(By.xpath("//button[normalize-space()='Accept' or normalize-space()='Confirm']"), "Accept");
    }

    public void clickYes() {
        safeClick(By.xpath("//button[normalize-space()='Yes']"), "Yes");
    }

    public String getToastMessage() {
        By toastLoc = By.xpath(
                "//*[contains(@class,'Toastify__toast-body') or contains(@class,'MuiSnackbar') or @role='alert' or contains(@class,'toast')]"
        );

        try {
            WebDriverWait toastWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            WebElement toast = toastWait.until(ExpectedConditions.presenceOfElementLocated(toastLoc));
            toastWait.until(d -> !toast.getText().trim().isEmpty());

            String msg = toast.getText().trim();
            System.out.println("Captured Toast text: " + msg);
            return msg;
        } catch (Exception e) {
            // Backup check: If Dialog closed automatically after save, treat as success
            boolean isDialogClosed = driver.findElements(updateDialog).isEmpty()
                    || !driver.findElement(updateDialog).isDisplayed();

            if (isDialogClosed) {
                System.out.println("Dialog closed successfully after Save click.");
                return "SUCCESS_DIALOG_CLOSED";
            }
            return "";
        }
    }

    // =========================
    // HELPER UTILITIES
    // =========================

    private void setInputValue(WebElement input, String value) {
        scrollIntoView(input);
        try {
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(value);
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    input, value
            );
        }
    }

    private void safeClick(By locator, String name) {
        try {
            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            scrollAndClick(btn);
            System.out.println(name + " : CLICKED");
        } catch (Exception e) {
            System.out.println(name + " button not present, skipping step.");
        }
    }

    private void scrollAndClick(WebElement el) {
        scrollIntoView(el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}