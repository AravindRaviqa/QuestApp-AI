package com.questapp.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.questapp.config.Configuration;
import com.questapp.pages.LoginPage;
import com.questapp.pages.PhasePage;
import com.questapp.pages.ProgramManagementPage;

public class PhaseManagementTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProgramManagementPage programManagementPage;
    private PhasePage phasePage;
    
    @BeforeMethod
    public void setup() {
        driver = Configuration.initializeDriver();
        loginPage = new LoginPage(driver);
        programManagementPage = new ProgramManagementPage(driver);
        phasePage = new PhasePage(driver);
    }
    
    @Test
    public void testAddNewPhase() {
        // Login
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        loginPage.login(Configuration.getUsername(), Configuration.getPassword());
        
        // Navigate to Institutions and Phases
        programManagementPage.navigateToInstitutions();
        phasePage.navigateToPhases();
        
        // Create new phase
        String phaseName = "Test Phase " + System.currentTimeMillis(); // Ensure unique phase name
        String startDate = "2025-01-01";
        String endDate = "2025-01-10";
        phasePage.createNewPhase(phaseName, startDate, endDate);
        
        try {
            Thread.sleep(2000); // Wait for phase creation to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Search for the created phase
        phasePage.searchPhase(phaseName);
        
        try {
            Thread.sleep(1000); // Wait for search results
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Click edit icon
        phasePage.clickEditIcon();
        
        // Update phase name and dates
        String updatedPhaseName = "Updated " + phaseName;
        String newStartDate = "2024-12-01";
        String newEndDate = "2025-01-15";
        phasePage.updatePhaseDates(updatedPhaseName, newStartDate, newEndDate);
        
        // Verify update was successful by searching for the updated phase name
        try {
            Thread.sleep(2000); // Wait for the update to complete
            phasePage.searchPhase(updatedPhaseName);
            Thread.sleep(1000); // Wait for search results
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}