package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigatePOM {
    WebDriver driver;


    public  By humanResources = By.cssSelector(".group-items > :nth-child(5)");
    public  By employees = By.xpath("//*[text()='Employees']");
    public  By plusButton = By.cssSelector("ms-table-toolbar > div ms-add-button");


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
