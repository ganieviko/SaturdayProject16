package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateNewEmplPOM {
    WebDriver driver;


    public  By firstNameEmployee = By.cssSelector("#ms-text-field-2:nth-child(1) input");
    public  By lastNameEmployee = By.cssSelector("#ms-text-field-3:nth-child(1) input");
    public  By employeeID = By.cssSelector("mat-form-field[class]:nth-child(1) input[data-placeholder='Employee ID']");
    public  By documentType = By.xpath("//span[text()='Document Type']");
    public  By documentNumber = By.cssSelector("[data-placeholder='Document Number']");
    public  By rowWithName = By.cssSelector("tbody[role='rowgroup']");
    public  By plusButtonInsideEmployee = By.cssSelector("button-bar [caption='GENERAL.BUTTON.NEW']");
    public  By alert = By.cssSelector("div[role='alertdialog']");
    public  By goToTheLastPage = By.cssSelector("button[aria-label='Last Page']");


    public CreateNewEmplPOM(WebDriver driver) {
        this.driver = driver;
    }

    public void fillingUpFirsName(String firstName){
        driver.findElement(this.firstNameEmployee).sendKeys(firstName);
    }
    public void fillingUpLastName(String lastName){
        driver.findElement(this.lastNameEmployee).sendKeys(lastName);
    }
    public void fillingUpEmployeeID(String id){
        driver.findElement(this.employeeID).sendKeys(id);
    }
    public void fillingUpDocumentNumber(String documentNumber){
        driver.findElement(this.documentNumber).sendKeys(documentNumber);
    }
    public void clickGoToLastPage(){
        driver.findElement(this.goToTheLastPage).click();
    }
}
