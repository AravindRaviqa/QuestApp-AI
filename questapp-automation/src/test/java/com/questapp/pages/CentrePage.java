package com.questapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CentrePage extends BasePage {
    private final By institutionsMenuLocator = By.xpath("((//span[@class='mat-button-wrapper'])[2])");
    private final By centresMenuLocator = By.xpath("//button[normalize-space()='Centres']");
    private final By addNewCentreButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final String dialogRoot = "//mat-dialog-container";
    private final By organisationDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='organisation']//div[contains(@class,'p-dropdown')]");
    private final By centreTypeDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='centre_type']//div[contains(@class,'p-dropdown')]");
    private final By workingModeDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='working_mode']//div[contains(@class,'p-dropdown')]");
    private final By partnershipTypeDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='partnership_type']//div[contains(@class,'p-dropdown')]");
    private final By stateDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='state']//div[contains(@class,'p-dropdown')]");
    private final By districtDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='district']//div[contains(@class,'p-dropdown')]");
    private final By cityInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='city']");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    private final By centreNameInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='name']");
    private final By searchInputLocator = By.xpath("//input[@formcontrolname='name']");

    public CentrePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToInstitutions() {
        WebElement institutionsMenu = wait.until(ExpectedConditions.elementToBeClickable(institutionsMenuLocator));
        institutionsMenu.click();
    }

    public void navigateToCentres() {
        WebElement centresMenu = wait.until(ExpectedConditions.elementToBeClickable(centresMenuLocator));
        centresMenu.click();
    }

    public void clickAddNewCentre() {
        // Add a delay before clicking Add New Centre
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement addNewCentreButton = wait.until(ExpectedConditions.elementToBeClickable(addNewCentreButtonLocator));
        addNewCentreButton.click();
    }

    public void enterCentreName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(centreNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void selectOrganisation(String orgName) {
        selectFromPrimeNgDropdown(organisationDropdownLocator, orgName);
    }

    public void selectCentreType(String centreType) {
        selectFromPrimeNgDropdown(centreTypeDropdownLocator, centreType);
    }

    public void selectWorkingMode(String workingMode) {
        selectFromPrimeNgDropdown(workingModeDropdownLocator, workingMode);
    }

    public void selectPartnershipType(String partnershipType) {
        selectFromPrimeNgDropdown(partnershipTypeDropdownLocator, partnershipType);
    }

    public void selectState(String state) {
        selectFromPrimeNgDropdown(stateDropdownLocator, state);
    }

    public void selectDistrict(String district) {
        selectFromPrimeNgDropdown(districtDropdownLocator, district);
    }

    public void enterCity(String city) {
        WebElement cityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(cityInputLocator));
        cityInput.clear();
        cityInput.sendKeys(city);
    }

    public void clickSubmit() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void createNewCentre(String centreName, String orgName, String centreType, String workingMode, String partnershipType, String state, String district, String city) {
        clickAddNewCentre();
        enterCentreName(centreName);
        selectOrganisation(orgName);
        selectCentreType(centreType);
        selectWorkingMode(workingMode);
        selectPartnershipType(partnershipType);
        selectState(state);
        selectDistrict(district);
        enterCity(city);
        clickSubmit();
    }

    public By editIconLocatorForCentre(String centreName) {
        return By.xpath("//tbody/tr[1]/td[last()]/button[1]");
    }

    public void searchCentre(String centreName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        searchInput.clear();
        searchInput.sendKeys(centreName);
        // Optionally, add a small wait for search results to update
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void clickEditCentre(String centreName) {
        WebElement editIcon = wait.until(ExpectedConditions.elementToBeClickable(editIconLocatorForCentre(centreName)));
        editIcon.click();
        // Wait for the dialog to appear
        By dialogLocator = By.xpath("//mat-dialog-container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));
    }

    public void editCentre(String newCentreName, String newCentreType, String newWorkingMode, String newPartnershipType, String newCity) {
        enterCentreName(newCentreName);
        selectCentreType(newCentreType);
        selectWorkingMode(newWorkingMode);
        selectPartnershipType(newPartnershipType);
        enterCity(newCity);
        clickSubmit();
    }
}