package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.UpdateTimeSheetPage;
import utils.DriverFactory;

public class UpdateTimeSheetTest {

    private UpdateTimeSheetPage updateTimeSheetPage;

    @BeforeClass
    public void setUp() {
        System.out.println("========================================");
        System.out.println("TEST SETUP STARTED");
        System.out.println("========================================");

        // DriverFactory se driver initialize kar rahe hain
        DriverFactory.initializeDriver();

        // Application login perform karein
        LoginPage loginPage = new LoginPage(DriverFactory.driver);
        loginPage.login("harshalpatilgov@gmail.com", "Harshal@512");
        System.out.println("Login executed successfully");
    }

    @Test
    public void updateTimeSheetTest() {
        System.out.println("========================================");
        System.out.println("UPDATE TIMESHEET TEST STARTED");
        System.out.println("========================================");

        updateTimeSheetPage = new UpdateTimeSheetPage(DriverFactory.driver);

        // Step 1 : Navigation to Attendance
        updateTimeSheetPage.clickAttendance();
        System.out.println("Step 1 : Opening Attendance");

        // Step 2 : Navigation to Team Claims
        updateTimeSheetPage.clickTeamClaims();
        System.out.println("Step 2 : Opening Team Claims");

        // Step 3 : Open Add New Claims Dialog
        updateTimeSheetPage.clickAddNewClaims();
        System.out.println("Step 3 : Clicking Add New Claims");

        Assert.assertTrue(
                updateTimeSheetPage.isUpdateTimeSheetDisplayed(),
                "Update TimeSheet dialog box failed to open!"
        );
        System.out.println("Update TimeSheet dialog opened successfully");

        // Step 4 : Select Punch In Date (Format: DD/MM/YYYY)
        String punchInDate = "04/09/2026";
        updateTimeSheetPage.enterPunchInDate(punchInDate);
        System.out.println("Step 4 : Punch In Date = " + punchInDate);

        // Step 5 : Enter Punch In Time (24-Hour format: "10:00")
        String punchInTime = "10:00";
        updateTimeSheetPage.enterPunchInTime(punchInTime);
        System.out.println("Step 5 : Punch In Time = " + punchInTime);

        System.out.println("Punch Out Date : SKIPPED (Automatically filled)");

        // Step 6 : Enter Punch Out Time (24-Hour format: "19:00" -> 07:00 PM)
        String punchOutTime = "19:00";
        updateTimeSheetPage.enterPunchOutTime(punchOutTime);
        System.out.println("Step 6 : Punch Out Time = " + punchOutTime);

        // Step 7 : Enter Reason for Claim
        String reason = "Attendance correction";
        updateTimeSheetPage.enterReason(reason);
        System.out.println("Step 7 : Reason entered = " + reason);

        // Step 8 : Submit Form (Click Save)
        updateTimeSheetPage.clickSave();
        System.out.println("Step 8 : Save clicked");

        // Step 9 & 10 : Handle optional secondary popups if present
        updateTimeSheetPage.clickAccept();
        System.out.println("Step 9 : Accept clicked/skipped");

        updateTimeSheetPage.clickYes();
        System.out.println("Step 10 : Yes clicked/skipped");

        // TOAST / DIALOG VALIDATION
        String toast = updateTimeSheetPage.getToastMessage();
        System.out.println("Final Toast/Status Message = " + toast);

        Assert.assertTrue(
                !toast.isEmpty() || toast.equals("SUCCESS_DIALOG_CLOSED"),
                "Neither Toast message appeared nor Dialog closed after clicking Save."
        );

        if (!toast.isEmpty() && !toast.equals("SUCCESS_DIALOG_CLOSED")) {
            Assert.assertFalse(toast.toLowerCase().contains("error"), "Error toast displayed: " + toast);
            Assert.assertFalse(toast.toLowerCase().contains("failed"), "Failed toast displayed: " + toast);
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Closing browser...");
        DriverFactory.quitDriver();
        System.out.println("Browser closed");
    }
}