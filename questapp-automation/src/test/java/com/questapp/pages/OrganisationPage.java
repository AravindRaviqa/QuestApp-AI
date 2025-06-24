package com.questapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrganisationPage extends BasePage {
    private final By organisationsMenuLocator = By.xpath("//button[normalize-space()='Organisations']");
    private final By addNewOrganisationButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final By organisationNameInputLocator = By.xpath("//input[@formcontrolname='name']");
    private final By organisationCodeInputLocator = By.xpath("//input[@formcontrolname='organisationCode']");
    private final By addressInputLocator = By.xpath("//textarea[@formcontrolname='address']");
    private final By phoneInputLocator = By.xpath("//input[@formcontrolname='mobile']");
    private final By emailInputLocator = By.xpath("//input[@formcontrolname='email']");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    private final By searchInputLocator = By.xpath("//input[@formcontrolname='search_value']");
    private final By editButtonLocator = By.xpath("//td[contains(text(), '%s')]/..//button[.//mat-icon[normalize-space()='edit']]");
    private final By editOrganisationNameInputLocator = organisationNameInputLocator;
    private final By editAddressInputLocator = addressInputLocator;
    private final By editPhoneInputLocator = phoneInputLocator;
    private final By editEmailInputLocator = emailInputLocator;
    private final By editSubmitButtonLocator = submitButtonLocator;

    public OrganisationPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOrganisations() {
        WebElement organisationsMenu = wait.until(ExpectedConditions.elementToBeClickable(organisationsMenuLocator));
        organisationsMenu.click();
    }

    public void clickAddNewOrganisation() {
        WebElement addNewOrganisationButton = wait.until(ExpectedConditions.elementToBeClickable(addNewOrganisationButtonLocator));
        addNewOrganisationButton.click();
    }

    public void enterOrganisationName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(organisationNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterOrganisationCode(String code) {
        WebElement codeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(organisationCodeInputLocator));
        codeInput.clear();
        codeInput.sendKeys(code);
    }

    public void enterAddress(String address) {
        WebElement addressInput = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInputLocator));
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public void enterPhone(String phone) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInputLocator));
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void enterEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
        emailInput.clear();
        emailInput.sendKeys(email);
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

    public void createNewOrganisation(String name, String code, String address, String phone, String email) {
        clickAddNewOrganisation();
        enterOrganisationName(name);
        enterAddress(address);
        enterPhone(phone);
        enterEmail(email);
        clickSubmit();
    }

    public void searchOrganisation(String orgName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        searchInput.clear();
        searchInput.sendKeys(orgName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickOrganisationName(String orgName) {
        String xpath = String.format("//a[normalize-space()='%s']", orgName);
        By locator = By.xpath(xpath);
        WebElement orgNameLink = wait.until(ExpectedConditions.elementToBeClickable(locator));
        orgNameLink.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickEditButtonOnDetailsPage() {
        By editButtonOnDetailsLocator = By.xpath("//button[.//mat-icon[normalize-space()='edit']]");
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(editButtonOnDetailsLocator));
        editButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickEditOrganisation(String orgName) {
        String xpath = String.format(editButtonLocator.toString().replace("By.xpath: ", ""), orgName);
        By locator = By.xpath(xpath);
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
        editButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickGoBackButton() {
        By goBackButtonLocator = By.xpath("//span[@class='go-back']");
        WebElement goBackButton = wait.until(ExpectedConditions.elementToBeClickable(goBackButtonLocator));
        goBackButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void updateOrganisationDetails(String name,String address, String phone, String email) {
        // Wait for the edit form to be visible before interacting
        wait.until(ExpectedConditions.visibilityOfElementLocated(editOrganisationNameInputLocator));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(editOrganisationNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
        WebElement addressInput = wait.until(ExpectedConditions.visibilityOfElementLocated(editAddressInputLocator));
        addressInput.clear();
        addressInput.sendKeys(address);
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(editPhoneInputLocator));
        phoneInput.clear();
        phoneInput.sendKeys(phone);
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(editEmailInputLocator));
        emailInput.clear();
        emailInput.sendKeys(email);
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(editSubmitButtonLocator));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}