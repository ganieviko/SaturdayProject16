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
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Project16 extends BaseTest{
    WebDriver driver;
    String departmentName;
    String departmentCode;
    int numberOfRowsBeforeSave;
    private NavigatePOM navigatePOM;
    private CreateNewEmplPOM createNewEmplPOM;
    BaseTest baseTest;


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
    @AfterClass(enabled = false)
    public void closeBrowser(){
        driver.quit();
    }
    @Test()
    public void navigate() {
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menu not visible");
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
    public void test3UpdateFirstName(String firstName, String lastName, String id, String documentNumber) throws AWTException {
        wait.until(ExpectedConditions.presenceOfElementLocated(Selectors.plusButton));
        driver.findElement(Selectors.plusButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee)).sendKeys(lastName);
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
        String nameAfterChange = "Artur2";
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).sendKeys(nameAfterChange);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Employee successfully updated"));
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Employee successfully updated");
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.humanResources), "Human resource menue not visible");
        driver.findElement(Selectors.humanResources).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees), "Employee element is not visible");
        driver.findElement(Selectors.employees).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='alertdialog']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Last Page']")));
        driver.findElement(By.cssSelector("button[aria-label='Last Page']")).click();

    }

    @Test(dependsOnMethods = "test3UpdateFirstName")
    public void test4UpdateLastName(){
        String lastNameAfterChange = "Ganiev2";
        String LastNameElement = "//td[text()='Artur2  Ganiev']//following::ms-edit-button";
        driver.findElement(By.xpath(LastNameElement)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee));
        driver.findElement(Selectors.lastNameEmployee).clear();
        driver.findElement(Selectors.lastNameEmployee).sendKeys(lastNameAfterChange);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();
        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Employee successfully updated"));
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Employee successfully updated");
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
        String documentNumberUpdate = "Document2";
        String editButtonDocumentChange = "//td[text()='Artur2  Ganiev2']//following::ms-edit-button";
        driver.findElement(By.xpath(editButtonDocumentChange)).click();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.documentNumber),"Save botton is not clikeable");
        driver.findElement(Selectors.documentNumber).clear();
        driver.findElement(Selectors.documentNumber).sendKeys(documentNumberUpdate);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save botton is not clikeable");
        driver.findElement(Selectors.saveButton).click();

        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Employee successfully updated"));
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Employee successfully updated");
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
        driver.findElement(Selectors.employeeID).sendKeys("IDChanged");
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menu not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));
        wait.until(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage));
        driver.findElement(Selectors.goToTheLastPage).click();
    }
    @Test(dependsOnMethods = "test6UpdateAnyDataChanged", dataProvider = "employeeWithSameID")
    public void test7cannotHaveSameID(String firstName, String secondName, String id, String documentType) throws AWTException {
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menu not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.plusButton));
        navigatePOM.clickingPlusButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee)).sendKeys(secondName);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.employeeID));
        createNewEmplPOM.fillingUpEmployeeID(id);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentType));
        createNewEmplPOM.fillingUpDocumentNumber(documentType);
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentType),"Document ID locator is not found");
        driver.findElement(Selectors.documentType).click();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save button is not clickable");
        driver.findElement(Selectors.saveButton).click();
        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Employee successfully created"));
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);
        boolean found = false;
        if (alertText.contains("Employee successfully created"))
            found = true;
        Assert.assertFalse(found, "Employee successfully created was created");

    }
    @Test(dependsOnMethods = "test6UpdateAnyDataChanged")
    public void test8sameDocumentNumber() throws AWTException {
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.deleteButton));
        navigatePOM.setDeleteButton();
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.getConfirmYes));
        navigatePOM.setConfirmDeleteButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).clear();
        createNewEmplPOM.fillingUpFirsName("Artur8");
        createNewEmplPOM.fillingUpLastName("Ganiev8");
        createNewEmplPOM.fillingUpEmployeeID("Document8");
        waitFor(ExpectedConditions.visibilityOfElementLocated(Selectors.documentType),"Document ID locator is not found");
        driver.findElement(Selectors.documentType).click();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        createNewEmplPOM.fillingUpDocumentNumber("Document2");
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save button is not clickable");
        driver.findElement(Selectors.saveButton).click();

        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Artur2 Ganiev2 already has such document number or PIN"));
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Artur2 Ganiev2 already has such document number or PIN");
    }
    @Test(dependsOnMethods = "test8sameDocumentNumber")
    public void test9SameFirstAndLastName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.firstNameEmployee)).sendKeys("Artur9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.lastNameEmployee)).sendKeys("Ganiev9");
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.documentNumber)).clear();
        createNewEmplPOM.fillingUpDocumentNumber("Document9");
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save button is not clickable");
        driver.findElement(Selectors.saveButton).click();

        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Employee successfully created"));
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Employee successfully created");
    }
    @Test(dependsOnMethods = "test9SameFirstAndLastName")
    public void go(){
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menu not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.plusButton));
        navigatePOM.clickingPlusButton();
        System.out.println("Navigate to test 10 to 15");
    }
    @Test(dependsOnMethods = "go",dataProvider = "test10To15")
    public void test10To15(String firstName, String lastName, String id, String documentNumber) throws AWTException, InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(Selectors.plusButtonInsideEmployee)).click();
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
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.saveButton),"Save button is not clickable");
        driver.findElement(Selectors.saveButton).click();
        Thread.sleep(2000);
        String alertText = driver.findElement(Selectors.alert).getText();
        System.out.println(alertText);

        if (alertText.contains("Invalid Employee Info!")){
            Assert.assertEquals(alertText, "Invalid Employee Info!");
        }else {
            Assert.fail();
        }
    }

    @Test(dependsOnMethods = "go")
    public void test16(){
        boolean enabled = driver.findElement(Selectors.photoButton).isEnabled();
        System.out.println(enabled);
        Assert.assertTrue(enabled);
    }

    @Test(dependsOnMethods = "go")
    public void test17AddPhoto() throws AWTException, InterruptedException {
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menu not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        wait.until(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage));
        driver.findElement(Selectors.goToTheLastPage).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Artur2  Ganiev2']")));
        driver.findElement(By.xpath("//td[text()='Artur2  Ganiev2']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.photoButton)).click();
        String path = "C:\\Users\\ganie_000\\Desktop\\IMG_0147.jpg";
        StringSelection stringSelection = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-icon='upload']"))).click();
        Thread.sleep(2000);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Upload']"))).click();
        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Photo successfully uploaded"));
        String alertText = driver.findElement(Selectors.alert).getText();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Close ']"))).click();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Photo successfully uploaded");
    }

    @Test(dependsOnMethods = "test17AddPhoto")
    public void test18photoChanged() throws InterruptedException, AWTException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.photoButton)).click();
        String path = "\"C:\\Users\\ganie_000\\Desktop\\IMG_0152.jpg\"";
        StringSelection stringSelection = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-icon='upload']"))).click();
        Thread.sleep(2000);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Upload']"))).click();
        waitFor(ExpectedConditions.textToBePresentInElementLocated(Selectors.alert, "Photo successfully uploaded"));
        String alertText = driver.findElement(Selectors.alert).getText();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Close ']"))).click();
        System.out.println(alertText);
        Assert.assertEquals(alertText, "Photo successfully uploaded");
    }
    @Test(dependsOnMethods = "test18photoChanged")
    public void test19DeletePicture() throws InterruptedException {
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Selectors.photoButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Delete ']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Yes ']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Close ']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[src='./assets/images/no-photo.jpg']")));
        WebElement pictureElement = driver.findElement(By.cssSelector("[src='./assets/images/no-photo.jpg']"));
        pictureElement.getText();
        System.out.println(pictureElement);
        System.out.println("picture was deleted");
    }
    @Test(dependsOnMethods = "test19DeletePicture")
    public void deletingAllEmployee(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Yes ']"))).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(navigatePOM.humanResources), "Human resource menu not visible");
        navigatePOM.clickingHumanResources();
        waitFor(ExpectedConditions.elementToBeClickable(Selectors.employees));
        navigatePOM.clickingEmployees();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));
        wait.until(ExpectedConditions.elementToBeClickable(Selectors.goToTheLastPage));
        driver.findElement(Selectors.goToTheLastPage).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Artur9  Ganiev9']//following::ms-delete-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Yes ']"))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='123445  Ganiev13']//following::ms-delete-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Yes ']"))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='@!#$#@  Ganiev14']//following::ms-delete-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Yes ']"))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='123445  Ganiev13']//following::ms-delete-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Yes ']"))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(Selectors.alert));

    }



    @DataProvider(name="employeeName")
    public Object[][] sectionData
            () {
        String[][] testData = {
                {"Artur", "Ganiev","IDChanged","safaf"}
        };
        return testData;
    }
    @DataProvider(name="employeeWithSameID")
    public Object[][] sectionData2() {
        String[][] testData = {
                {"Bulent", "Hassan","IDChanged","safaf2"},
        };
        return testData;
    }
    @DataProvider(name="test10To15")
    public Object[][] sectionData3() {
        String[][] testData = {
                {"", "Hassan10","Crazy10","safaf10"},
                {"Artur11", "","Tired11","safaf11"},
                {"Artur12", "Ganiev12","","safaf12"},
                {"123445", "Ganiev13","Dfggg13","safaf13"},
                {"@!#$#@", "Ganiev14","dsfsd14","safaf14"},
                {"Artur15", "#$%#@@","","safaf15"},
        };
        return testData;
    }
}
