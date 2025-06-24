package com.questapp.tests;

import com.questapp.pages.CoursePage;
import com.questapp.pages.SubjectPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class CourseManagementTest extends BaseTest {
    private CoursePage coursePage;
    private SubjectPage subjectPage;
    private static final String TEST_IMAGE_PATH = "src/test/resources/test-images/test-subject-image.jpg";
    private String subjectName;

    @BeforeMethod
    public void setup() {
        coursePage = new CoursePage(driver);
        subjectPage = new SubjectPage(driver);
        
        // Create a subject first
        subjectPage.navigateToContent();
        subjectPage.navigateToSubjects();
        subjectName = "Test Subject " + System.currentTimeMillis();
        subjectPage.createNewSubject(subjectName, subjectName, TEST_IMAGE_PATH);
    }

    @Test
    public void testAddNewCourse() {
        // Navigate to content and courses
        coursePage.navigateToContent();
        coursePage.navigateToCourses();

        // Create a new course with same data for name, description and tag
        String courseData = "Test Course " + System.currentTimeMillis();
        coursePage.createNewCourse(courseData, courseData, subjectName, TEST_IMAGE_PATH);
    }

    @Test
    public void testEditCourse() {
        // Navigate to content and courses
        coursePage.navigateToContent();
        coursePage.navigateToCourses();

        // Create a course to edit
        String originalData = "Original Course " + System.currentTimeMillis();
        coursePage.createNewCourse(originalData, originalData, subjectName, TEST_IMAGE_PATH);

        // Search for the created course
        coursePage.searchCourse(originalData);
        assertTrue(coursePage.isCourseVisible(originalData), "Created course should be visible before editing");

        // Edit the course with new same data for name, description and tag
        String newData = "Edited Course " + System.currentTimeMillis();
        
        // Click edit icon for the course
        coursePage.clickEditCourse(originalData);
        
        // Update the course details
        coursePage.enterCourseName(newData);
        coursePage.enterCourseDescription(newData);
        coursePage.enterCourseTag(newData);
        coursePage.selectSubject(subjectName);
        coursePage.uploadCourseImage(TEST_IMAGE_PATH);
        coursePage.clickSubmit();

        // Search and verify the edited course
        coursePage.searchCourse(newData);
        assertTrue(coursePage.isCourseVisible(newData), "Edited course should be visible after update");
    }
} 