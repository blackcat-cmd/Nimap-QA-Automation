Markdown
# Nimap QA Automation Framework

This repository contains an automated TestNG framework built using Selenium WebDriver and Java for testing the **Test Field Force** web application.

---

## 📁 Project Structure

```text
Nimap-QA-Automation
│
├── pom.xml
├── testng.xml
├── README.md
│
└── src
    └── test
        └── java
            │
            ├── pages
            │   ├── LoginPage.java
            │   ├── DashboardPage.java
            │   ├── UpdateTimeSheetPage.java
            │   └── AddCustomerPage.java
            │
            ├── tests
            │   ├── BrowserTest.java
            │   ├── LoginTest.java
            │   ├── UpdateTimeSheetTest.java
            │   └── AddCustomerTest.java
            │
            └── utils
                └── DriverFactory.java
🚀 Tech Stack & Dependencies
Language: Java 21+

Automation Tool: Selenium WebDriver (v4.35.0)

Test Runner Framework: TestNG (v7.11.0)

Build Tool: Maven

Design Pattern: Page Object Model (POM)

🛠️ Key Features
Page Object Model (POM): Clean separation between UI elements/actions and test logic.

Data-Driven Automation: Utilizes TestNG @DataProvider for dynamic customer data parametrization.

Driver Lifecycle Management: DriverFactory handles Chrome driver setup, explicit waits, and clean teardown.

Robust Click Handling: Safe clicks with JavaScript Executor fallbacks to avoid click-interception on dynamic dropdowns and navigation overlays.

🧪 Test Suite Summary
Login Automation (LoginTest.java)

Authenticates user credentials and verifies dashboard visibility.

Attendance Management (UpdateTimeSheetTest.java)

Automates daily punch-in actions and status verifications.
Verify the Toast/Popup message after the PunchIn

Customer Management (AddCustomerTest.java)

Navigates through My Customers side menu to the My Customer view.

Triggers the top-right Manage dropdown to open Add Customer.

Populates form fields (Name, Mobile, Email) dynamically via @DataProvider.

Validates creation status using toast notification alerts.

⚙️ Execution Guide
Method 1: IntelliJ IDEA (Directory Execution)
In the Project Explorer, expand src/test/java.

Right-click on the java folder (or tests package).

Select Run 'Tests in 'java''.

Method 2: TestNG Suite (testng.xml)
Right-click on testng.xml in the root folder and select Run 'testng.xml'.

📌 Requirements
Java JDK 17+ or JDK 21+ configured in system PATH.

Apache Maven installed.

Google Chrome browser updated.
