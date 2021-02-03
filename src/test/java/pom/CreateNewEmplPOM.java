package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CreateNewEmplPOM extends BaseTest{
    WebDriver driver;

    public  By firstNameEmployee = By.cssSelector("ms-text-field[formcontrolname='firstName']>input");
    public  By lastNameEmployee = By.cssSelector("ms-text-field[formcontrolname='lastName']>input");
    public  By employeeID = By.cssSelector("mat-form-field[class]:nth-child(1) input[data-placeholder='Employee ID']");
    public  By documentType = By.xpath("//span[text()='Document Type']");
    public  By documentNumber = By.cssSelector("[data-placeholder='Document Number']");
    public  By getDocumentNumber = By.cssSelector("[data-placeholder='Document Number']");
    public  By rowWithName = By.cssSelector("tbody[role='rowgroup']");
    public  By plusButtonInsideEmployee = By.cssSelector("button-bar [caption='GENERAL.BUTTON.NEW']");
    public  By alert = By.cssSelector("div[role='alertdialog']");
    public  By goToTheLastPage = By.cssSelector("button[aria-label='Last Page']");
    public  By getDocumentType = By.cssSelector("//span[text()='Document Type']");


    public CreateNewEmplPOM(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);

    }

    public void fillingUpFirsName(String firstName){
        waitFor(ExpectedConditions.visibilityOfElementLocated(firstNameEmployee), "");
        driver.findElement(this.firstNameEmployee).sendKeys(firstName);
    }
    public void fillingUpLastName(String lastName){
        waitFor(ExpectedConditions.visibilityOfElementLocated(lastNameEmployee), "");
        driver.findElement(this.lastNameEmployee).sendKeys(lastName);
    }
    public void fillingUpEmployeeID(String id){
        waitFor(ExpectedConditions.visibilityOfElementLocated(employeeID), "");
        driver.findElement(this.employeeID).sendKeys(id);
    }
    public void fillingUpDocumentNumber(String documentNumber){
        waitFor(ExpectedConditions.visibilityOfElementLocated(getDocumentNumber), "");
        driver.findElement(this.documentNumber).sendKeys(documentNumber);}
    public void clickGoToLastPage(){
        waitFor(ExpectedConditions.invisibilityOfElementLocated(alert), "Did not work");
        waitFor(ExpectedConditions.elementToBeClickable(goToTheLastPage),"go to the last page element was not found");
        driver.findElement(this.goToTheLastPage).click();
    }

    public void choseDocumentType() throws AWTException {
        driver.findElement(documentType).click();

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }


}
