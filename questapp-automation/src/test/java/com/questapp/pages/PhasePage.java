package com.questapp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PhasePage extends BasePage {
    
    @FindBy(xpath = "//button[normalize-space()='Phases']")
    private WebElement phasesMenu;
    
    @FindBy(xpath = "(//mat-icon[normalize-space()='add'])[1]")
    private WebElement addNewPhaseButton;
    
    @FindBy(xpath = "//input[@class='p-inputtext p-component p-element ng-untouched ng-pristine ng-invalid']")
    private WebElement phaseNameInput;
    
    @FindBy(xpath = "//div[@class='cdk-global-overlay-wrapper']//div[2]//div[1]//span[2]//mat-datepicker-toggle[1]//button[1]")
    private WebElement startDateInput;
    
    @FindBy(xpath = "//div[@class='cdk-global-overlay-wrapper']//div[3]//div[1]//span[2]//mat-datepicker-toggle[1]//button[1]")
    private WebElement endDateInput;
    
    @FindBy(xpath = "//mat-calendar")
    private WebElement calendar;
    
    @FindBy(xpath = "//mat-calendar//button[contains(@class, 'mat-calendar-period-button')]")
    private WebElement calendarHeader;
    
    @FindBy(xpath = "//button[@aria-label='Previous month']")
    private WebElement previousMonthButton;
    
    @FindBy(xpath = "//button[@aria-label='Next month']")
    private WebElement nextMonthButton;
    
    @FindBy(xpath = "//div[contains(@class, 'mat-calendar-body-cell-content')]")
    private WebElement dateCell;
    
    @FindBy(xpath = "//span[normalize-space()='Submit']")
    private WebElement submitButton;
    
    @FindBy(xpath = "//input[@id='mat-input-0']")
    private WebElement searchInput;
    
    @FindBy(xpath = "(//*[name()='svg'])[3]")
    private WebElement editIcon;
    
    @FindBy(xpath = "//div[@class='cdk-global-overlay-wrapper']//div[2]//div[1]//span[2]//mat-datepicker-toggle[1]//button[1]")
    private WebElement editStartDateInput;
    
    @FindBy(xpath = "//div[@class='cdk-global-overlay-wrapper']//div[3]//div[1]//span[2]//mat-datepicker-toggle[1]//button[1]")
    private WebElement editEndDateInput;
    
    @FindBy(xpath = "//div[@class='cdk-global-overlay-wrapper']//div[1]//div[1]//input[1]")
    private WebElement editPhaseNameInput;
    
    @FindBy(xpath = "//span[normalize-space()='Submit']")
    private WebElement editSubmitButton;
    
    public PhasePage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateToPhases() {
        try {
            Thread.sleep(1000); // Wait for page to be fully loaded
            click(phasesMenu);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void clickAddNewPhase() {
        click(addNewPhaseButton);
    }
    
    public void enterPhaseName(String phaseName) {
        sendKeys(phaseNameInput, phaseName);
    }
    
    public void enterStartDate(String startDate) {
        click(startDateInput);
        selectDateFromCalendar(startDate);
    }
    
    public void enterEndDate(String endDate) {
        click(endDateInput);
        try {
            Thread.sleep(1000); // Wait for calendar to be fully loaded
            waitForElementToBeVisible(calendar);
            selectDateFromCalendar(endDate);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void selectDateFromCalendar(String date) {
        // Wait for calendar to be visible
        waitForElementToBeVisible(calendarHeader);
        
        // Split the date string
        String[] dateParts = date.split("-");
        int targetYear = Integer.parseInt(dateParts[0]);
        int targetMonth = Integer.parseInt(dateParts[1]);
        int targetDay = Integer.parseInt(dateParts[2]);
        
        // Get current month and year from calendar header
        String headerText = calendarHeader.getText();
        String[] headerParts = headerText.split(" ");
        int currentMonth = getMonthNumber(headerParts[0]);
        int currentYear = Integer.parseInt(headerParts[1]);
        
        // Calculate months to navigate
        int monthsToNavigate = ((targetYear - currentYear) * 12) + (targetMonth - currentMonth);
        
        // Navigate to the target month
        while (monthsToNavigate > 0) {
            click(nextMonthButton);
            monthsToNavigate--;
            try {
                Thread.sleep(500); // Small delay between clicks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        while (monthsToNavigate < 0) {
            click(previousMonthButton);
            monthsToNavigate++;
            try {
                Thread.sleep(500); // Small delay between clicks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Find and click the target date
        String dateXPath = String.format("//div[contains(@class, 'mat-calendar-body-cell-content') and normalize-space()='%d']", targetDay);
        WebElement targetDate = driver.findElement(org.openqa.selenium.By.xpath(dateXPath));
        click(targetDate);
        
        try {
            Thread.sleep(1000); // Wait for date selection to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private int getMonthNumber(String monthName) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                          "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(monthName)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Invalid month name: " + monthName);
    }
    
    public void clickSubmit() {
        click(submitButton);
        try {
            Thread.sleep(2000); // Wait for phase creation success and page refresh
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void createNewPhase(String phaseName, String startDate, String endDate) {
        clickAddNewPhase();
        enterPhaseName(phaseName);
        enterStartDate(startDate);
        enterEndDate(endDate);
        clickSubmit();
    }
    
    public void searchPhase(String phaseName) {
        sendKeys(searchInput, phaseName);
        try {
            Thread.sleep(1000); // Wait for search results
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void clickEditIcon() {
        click(editIcon);
        try {
            Thread.sleep(1000); // Wait for edit dialog to open
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void updatePhaseDates(String newStartDate, String newEndDate) {
        // Wait for the edit dialog to be fully loaded and elements to be visible
        waitForElementToBeVisible(editStartDateInput);
        waitForElementToBeClickable(editStartDateInput);
        click(editStartDateInput);
        selectDateFromCalendar(newStartDate);
        
        waitForElementToBeVisible(editEndDateInput);
        waitForElementToBeClickable(editEndDateInput);
        click(editEndDateInput);
        selectDateFromCalendar(newEndDate);
        
        // Click submit button
        waitForElementToBeClickable(editSubmitButton);
        click(editSubmitButton);
        
        // Wait for changes to be saved
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void updatePhaseDates(String newPhaseName, String newStartDate, String newEndDate) {
        try {
            // Update phase name
            Thread.sleep(1500); // Increased wait time for edit dialog to be fully loaded
            waitForElementToBeVisible(editPhaseNameInput);
            waitForElementToBeClickable(editPhaseNameInput);
            editPhaseNameInput.clear();
            Thread.sleep(800); // Increased wait after clearing
            sendKeys(editPhaseNameInput, newPhaseName);
            
            // Update dates
            waitForElementToBeVisible(editStartDateInput);
            waitForElementToBeClickable(editStartDateInput);
            click(editStartDateInput);
            selectDateFromCalendar(newStartDate);
            
            waitForElementToBeVisible(editEndDateInput);
            waitForElementToBeClickable(editEndDateInput);
            click(editEndDateInput);
            selectDateFromCalendar(newEndDate);
            
            // Click submit button
            waitForElementToBeClickable(editSubmitButton);
            click(editSubmitButton);
            
            // Wait for changes to be saved
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void updatePhaseName(String newPhaseName) {
        try {
            Thread.sleep(1000); // Wait for edit dialog to be fully loaded
            waitForElementToBeVisible(editPhaseNameInput);
            editPhaseNameInput.clear();
            Thread.sleep(500); // Wait after clearing
            sendKeys(editPhaseNameInput, newPhaseName);
            clickSubmit();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}