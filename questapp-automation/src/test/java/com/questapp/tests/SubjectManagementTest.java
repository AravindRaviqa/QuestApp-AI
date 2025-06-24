package com.questapp.tests;

import com.questapp.pages.SubjectPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class SubjectManagementTest extends BaseTest {
    private SubjectPage subjectPage;
    private static final String TEST_IMAGE_PATH = "src/test/resources/test-images/test-subject-image.jpg";

    @BeforeMethod
    public void setup() {
        subjectPage = new SubjectPage(driver);
    }

    @Test
    public void testAddNewSubject() {
        // Navigate to content and subjects
        subjectPage.navigateToContent();
        subjectPage.navigateToSubjects();

        // Create a new subject with same data for name, description and tag
        String testData = "Test Subject " + System.currentTimeMillis();
        subjectPage.createNewSubject(testData, testData, TEST_IMAGE_PATH);
    }

    @Test
    public void testEditSubject() {
        // Navigate to content and subjects
        subjectPage.navigateToContent();
        subjectPage.navigateToSubjects();

        // Create a subject to edit with same data for name, description and tag
        String originalData = "Original Subject " + System.currentTimeMillis();
        subjectPage.createNewSubject(originalData, originalData, TEST_IMAGE_PATH);

        // Search for the created subject
        subjectPage.searchSubject(originalData);
        assertTrue(subjectPage.isSubjectVisible(originalData), "Created subject should be visible before editing");

        // Edit the subject with new same data for name, description and tag
        String newData = "Edited Subject " + System.currentTimeMillis();
        
        // Click edit icon for the subject
        subjectPage.clickEditSubject(originalData);
        
        // Update the subject details
        subjectPage.enterSubjectName(newData);
        subjectPage.enterSubjectDescription(newData);
        subjectPage.enterSubjectTag(newData);
        subjectPage.uploadSubjectImage(TEST_IMAGE_PATH);
        subjectPage.clickSubmit();

        // Search and verify the edited subject
        subjectPage.searchSubject(newData);
        assertTrue(subjectPage.isSubjectVisible(newData), "Edited subject should be visible after update");
    }
} 