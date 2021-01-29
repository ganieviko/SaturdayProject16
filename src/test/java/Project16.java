import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Project16 {
    WebDriver driver;
    WebDriverWait wait;
    String departmentName;
     String departmentCode;
     int numberOfRowsBeforeSave;



    @Parameters({"username", "password"})
    @BeforeClass
    public void setUpAndLogin(@Optional("daulet2030@gmail.com")String uname, @Optional("TechnoStudy123@")String upassword) {
        System.setProperty("webdriver.chrome.driver", MyConstance.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://test.campus.techno.study/");
        driver.findElement(Selectors.username).sendKeys(uname);
        driver.findElement(Selectors.password).sendKeys(upassword);
        driver.findElement(Selectors.loginButton).click();
        WebElement cookieButton = driver.findElement(By.cssSelector("a[aria-label='dismiss cookie message']"));
        cookieButton.click();

    }
    @Test
    public void navigate() {
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.humanResources), "Human resource menue not visible");
        driver.findElement(Selectors.humanResources).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        driver.findElement(Selectors.employees).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.plusButton));
        driver.findElement(Selectors.plusButton).click();

    }
    @Test(dependsOnMethods = "navigate", dataProvider = "employeeName")
    public void createNewEmployee (String firstName, String lastName, String id, String documentNumber) throws AWTException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee)).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.employeeID)).sendKeys(id);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.documentNumber)).sendKeys(documentNumber);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentType),"Document ID locator is not found");
        driver.findElement(Selectors.documentType).click();



        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();

        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.humanResources), "Human resource menue not visible");
        driver.findElement(Selectors.humanResources).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        driver.findElement(Selectors.employees).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alertdialog']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Last Page']")));
        driver.findElement(By.cssSelector("button[aria-label='Last Page']")).click();

        List<WebElement> rows = driver.findElements(Selectors.rowWithName);
        boolean found = false;
        for (WebElement row : rows) {
            if (row.getText().contains(firstName)&& row.getText().contains(lastName))
                found = true;
            System.out.println("The section with  first name "  + firstName +  " and last name " + lastName + " was created");
        }
        Assert.assertTrue(found, "The section with " + firstName + " and " + lastName + " was not  created");
    }


    @Test(dependsOnMethods = "createNewEmployee")
    public void deletingEmployee () throws InterruptedException {
        waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='Artur  Ganiev']//following::ms-delete-button")));
        driver.findElement(By.xpath("//td[text()='Artur  Ganiev']//following::ms-delete-button")).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='submit']")));
        driver.findElement(By.cssSelector("[type='submit']")).click();

        Thread.sleep(2000);
        List<WebElement> rows = driver.findElements(Selectors.rowWithName);
        for (WebElement row : rows){
            System.out.println(row.getText());
        }
        boolean found = true;
        for (WebElement row : rows) {
            if (row.getText().contains("Artur Ganiev"))
                found = false;
        }
        Assert.assertTrue(found, "The employee with name Artur and last name  Ganiev was not deleted");
    }

    @Test(dependsOnMethods = "deletingEmployee", dataProvider = "employeeName")
    public void updateFirstName(@Optional("daulet2030@gmail.com")String firstName, @Optional("TechnoStudy123@")String lastName,@Optional("TechndddddoStudy123@") String id,@Optional("TessssschnoStudy123@") String documentNumber) throws AWTException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ms-table-toolbar > div ms-add-button")));
        driver.findElement(By.cssSelector("ms-table-toolbar > div ms-add-button")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ms-text-field-7>input"))).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ms-text-field-8>input"))).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-placeholder='Employee ID']"))).sendKeys(id);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.documentNumber)).sendKeys(documentNumber);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentType),"Document ID locator is not found");
        driver.findElement(Selectors.documentType).click();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.humanResources), "Human resource menue not visible");
        driver.findElement(Selectors.humanResources).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees), "Employee element is not visible");
        driver.findElement(Selectors.employees).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alertdialog']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Last Page']")));
        driver.findElement(By.cssSelector("button[aria-label='Last Page']")).click();
        driver.findElement(By.xpath("//td[text()='Artur  Ganiev']//following::ms-edit-button")).click();
        String nameAfterChange = "ArturTechnoStudy";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ms-text-field-12>input"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ms-text-field-12>input"))).sendKeys(nameAfterChange);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.humanResources), "Human resource menue not visible");
        driver.findElement(Selectors.humanResources).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees), "Employee element is not visible");
        driver.findElement(Selectors.employees).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alertdialog']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Last Page']")));
        driver.findElement(By.cssSelector("button[aria-label='Last Page']")).click();
        Assert.assertEquals(nameAfterChange, "ArturTechnoStudy");

    }
    @Test(dependsOnMethods = "updateFirstName")
    public void UpdateLastName(){
        String lastNameAfterChange = "GanievTechnoStudy";
        String LastNameElement = "//td[text()='ArturTechnoStudy  Ganiev']//following::ms-edit-button";
        driver.findElement(By.xpath(LastNameElement)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ms-text-field-18>input"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ms-text-field-18>input"))).sendKeys(lastNameAfterChange);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.humanResources), "Human resource menue not visible");
        driver.findElement(Selectors.humanResources).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees), "Employee element is not visible");
        driver.findElement(Selectors.employees).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));
        wait.until(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage));
        driver.findElement(Selectors.goToTheLastPage).click();
    }
    @Test(dependsOnMethods = "UpdateLastName")
    public void updateDocumentNumber (){
        String editButtonDocumentChange = "//td[text()='ArturTechnoStudy  GanievTechnoStudy']//following::ms-edit-button";
        driver.findElement(By.xpath(editButtonDocumentChange)).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.documentNumber),"Save botton is not clikeable");
        driver.findElement(Selectors.documentNumber).clear();
        driver.findElement(Selectors.documentNumber).sendKeys("changedDN130389");
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
    }


    @DataProvider(name="employeeName")
    public Object[][] sectionData() {
        String[][] testData = {
                {"Artur", "Ganiev","WNY234WER","safaf"}
        };
        return testData;
    }

    private <T> void waitFor(ExpectedCondition<T> condition) {
        waitFor(condition, condition.toString());
    }

    private <T> void waitFor(ExpectedCondition<T> condition, String errorMessage) {
        try {
            wait.until(condition);
        } catch (TimeoutException e) {
            Assert.fail(errorMessage);
        }
    }
}

