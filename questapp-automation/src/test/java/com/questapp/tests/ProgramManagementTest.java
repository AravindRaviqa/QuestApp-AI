package com.questapp.tests;

import org.testng.annotations.Test;
import com.questapp.pages.ProgramManagementPage;

public class ProgramManagementTest extends BaseTest {
    
    @Test
    public void testCreateNewProgram() throws Exception {
        // Navigate to program management
        ProgramManagementPage programPage = new ProgramManagementPage(driver);
        programPage.navigateToInstitutions();
        programPage.navigateToPrograms();
        programPage.clickAddNewProgram();
        
        // Create new program
        String programName = "Test Program " + System.currentTimeMillis();
        programPage.createNewProgram(programName);
        
        // Search and verify program creation
        programPage.searchProgram(programName);
        org.testng.Assert.assertTrue(programPage.isProgramVisible(programName), 
            "Created program should be visible in the program list");
    }

    @Test
    public void testEditProgram() throws Exception {
        // Navigate to program management
        ProgramManagementPage programPage = new ProgramManagementPage(driver);
        programPage.navigateToInstitutions();
        programPage.navigateToPrograms();
        
        // Create a program to edit
        String originalProgramName = "Edit Test Program " + System.currentTimeMillis();
        programPage.clickAddNewProgram();
        programPage.createNewProgram(originalProgramName);
        
        // Search for the created program
        programPage.searchProgram(originalProgramName);
        org.testng.Assert.assertTrue(programPage.isProgramVisible(originalProgramName), 
            "Created program should be visible in the program list");
        
        // Edit the program
        String updatedProgramName = "Updated " + originalProgramName;
        programPage.clickEditProgram(originalProgramName);
        programPage.updateProgramName(updatedProgramName);
        
        // Search and verify program update
        programPage.searchProgram(updatedProgramName);
        org.testng.Assert.assertTrue(programPage.isProgramVisible(updatedProgramName), 
            "Updated program should be visible in the program list");
    }
}