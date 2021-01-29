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
import pom.BaseTest;
import pom.CreateNewEmplPOM;
import pom.NavigatePOM;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DemoVersion extends BaseTest {
    WebDriver driver;
    String departmentName;
    String departmentCode;
    int numberOfRowsBeforeSave;
    private NavigatePOM navigatePOM;
    private CreateNewEmplPOM createNewEmplPOM;


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
        navigatePOM = new NavigatePOM(driver);
        createNewEmplPOM = new CreateNewEmplPOM(driver);
    }

    @Test
    public void navigate() {
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menue not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.plusButton));
        navigatePOM.clickingPlusButton();
    }

    @Test(dependsOnMethods = "navigate", dataProvider = "employeeName")
    public void test1CreateNewEmployee(String firstName, String lastName, String id, String documentNumber) throws AWTException {
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee), "");
        createNewEmplPOM.fillingUpFirsName(firstName);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee), "");
        createNewEmplPOM.fillingUpLastName(lastName);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.employeeID), "");
        createNewEmplPOM.fillingUpEmployeeID(id);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentNumber), "");
        createNewEmplPOM.fillingUpDocumentNumber(documentNumber);

        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentType),"Document ID locator is not found");
        driver.findElement(Selectors.documentType).click();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();

        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menue not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        waitFor(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert), "Did not work");
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage),"go to the last page element was not found");
        createNewEmplPOM.clickGoToLastPage();

        List<WebElement> rows = driver.findElements(Selectors.rowWithName);
        boolean found = false;
        for (WebElement row : rows) {
            if (row.getText().contains(firstName)&& row.getText().contains(lastName))
                found = true;
            System.out.println("The section with  first name "  + firstName +  " and last name " + lastName + " was created");
        }
        Assert.assertTrue(found, "The section with " + firstName + " and " + lastName + " was not  created");
    }

    @Test(dependsOnMethods = "test1CreateNewEmployee")
    public void test2DeletingEmployee() throws InterruptedException {
        String deleteButtonEmployee = "//td[text()='Artur  Ganiev']//following::ms-delete-button";
        waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(deleteButtonEmployee)));
        driver.findElement(By.xpath(deleteButtonEmployee)).click();
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

    @Test(dependsOnMethods = "test2DeletingEmployee", dataProvider = "employeeName")
    public void test3UpdateFirstName(@Optional("daulet2030@gmail.com")String firstName, @Optional("TechnoStudy123@")String lastName, @Optional("Study123@") String id, @Optional("TessssschnoStudy123@") String documentNumber) throws AWTException {
        wait.until(ExpectedConditions.presenceOfElementLocated(Selectors.plusButton));
        driver.findElement(Selectors.plusButton).click();
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

        waitFor(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert), "Did not work");
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage),"go to the last page element was not found");
        createNewEmplPOM.clickGoToLastPage(); //TODO: change it after !!!!!!

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

    @Test(dependsOnMethods = "test3UpdateFirstName")
    public void test4UpdateLastName(){
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

    @Test(dependsOnMethods = "test4UpdateLastName")
    public void test5UpdateDocumentNumber(){
        String editButtonDocumentChange = "//td[text()='ArturTechnoStudy  GanievTechnoStudy']//following::ms-edit-button";
        driver.findElement(By.xpath(editButtonDocumentChange)).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.documentNumber),"Save botton is not clikeable");
        driver.findElement(Selectors.documentNumber).clear();
        driver.findElement(Selectors.documentNumber).sendKeys("changedDN130389");
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
    }
    @Test(dependsOnMethods = "test5UpdateDocumentNumber")
    public void test6UpdateAnyDataChanged(){
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menue not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage),"go to the last page element was not found");
        createNewEmplPOM.clickGoToLastPage();
        String editButtonForAnyUpdate = "//*[contains(text(),'Artur')]//following::ms-edit-button";
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(editButtonForAnyUpdate)));
        driver.findElement(By.xpath(editButtonForAnyUpdate)).click();
        driver.findElement(Selectors.employeeID).clear();
        driver.findElement(Selectors.employeeID).sendKeys("Changed2727");
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menue not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));
        wait.until(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage));
        driver.findElement(Selectors.goToTheLastPage).click();
    }
    @Test(dependsOnMethods = "test6UpdateAnyDataChanged", dataProvider = "employeeWithSameID")
    public void test7cannotHaveSameID(String firstName, String secondName, String id, String documentType){
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menue not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.plusButton));
        navigatePOM.clickingPlusButton();

    }

    @DataProvider(name="employeeName")
    public Object[][] sectionData() {
        String[][] testData = {
                {"Artur", "Ganiev","WNY234WER","safaf"}
        };
        return testData;
    }
    @DataProvider(name="employeeWithSameID")
    public Object[][] sectionData2() {
        String[][] testData = {
                {"Bulent", "Karim","WNY234WER","safaf2rr"},
        };
        return testData;
    }
}
