package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigatePOM extends BaseTest{
    WebDriver driver;



    public  By humanResources = By.cssSelector(".group-items > :nth-child(5)");
    public  By employees = By.xpath("//*[text()='Employees']");
    public  By plusButton = By.cssSelector("ms-table-toolbar > div ms-add-button");
    public  By deleteButton = By.xpath("//span[text()='Delete']");
    public  By confirmDeleteButton = By.xpath("//span[text()=' Yes ']");

    public void setDeleteButton(){
        driver.findElement(this.deleteButton).click();
    }
    public void setConfirmDeleteButton(){
        driver.findElement(this.confirmDeleteButton).click();
    }


    public NavigatePOM(WebDriver driver) {
        this.driver = driver;
    }

    public void clickingHumanResources(){
        driver.findElement(this.humanResources).click();
    }
    public void clickingEmployees(){
        driver.findElement(this.employees).click();
    }
    public void clickingPlusButton(){
        driver.findElement(this.plusButton).click();
    }
}
