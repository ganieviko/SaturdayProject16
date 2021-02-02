import org.openqa.selenium.By;

public class Selectors {
    String alertCreated = "Employee successfully created";
    public static By popupMessage =By.id("toast-container");
    public static By username = By.cssSelector("input[formcontrolname='username']");
    public static By password = By.cssSelector("input[formcontrolname='password']");
    public static By loginButton = By.cssSelector("button[aria-label='LOGIN']");
    public static By menu = By.cssSelector("svg[data-icon=\"bars\"]");
    public static By setupMenu = By.cssSelector(".group-items > :nth-child(1)");
    public static By humanResources = By.cssSelector(".group-items > :nth-child(5)");
    public static By employees = By.xpath("//*[text()='Employees']");
    public static By schoolSetupMenu = By.cssSelector(".group-items > :nth-child(1) fuse-nav-vertical-collapsable:nth-child(2)");
    public static By departmentMenu = By.cssSelector(".group-items > :nth-child(1) fuse-nav-vertical-collapsable:nth-child(2) > div > fuse-nav-vertical-item:nth-child(6) > a");
    public static By plusButton = By.cssSelector("ms-table-toolbar > div ms-add-button");
    public static By plusButtonOverlay = By.cssSelector(".cdk-overlay-pane ms-table-toolbar > div ms-add-button");
    public static By nameInput = By.cssSelector("[placeholder='GENERAL.FIELD.NAME']>input");
    public static By codeInput = By.cssSelector("[placeholder='GENERAL.FIELD.CODE']>input");
    public static By shortNameInput = By.cssSelector("[placeholder='GENERAL.FIELD.SHORTNAME']>input");
    public static By sectionTab = By.xpath("//div[text()='Section']");
    public static By addSectionButton = By.cssSelector("school-department-section ms-button");
    public static By sectionRows = By.cssSelector("school-department-section tr");
    public static By saveButton = By.cssSelector("ms-save-button");
    public static By departmentRows = By.cssSelector("ms-browse-table tr");
    public static By trashButton = By.cssSelector("ms-delete-button");
    public static By confirmYes = By.cssSelector("button[type='submit']");
    public static By alert = By.cssSelector("div[role='alertdialog']");
    public static By cookieButton = By.cssSelector("a[aria-label='dismiss cookie message']");
    public static By plusButtonEmployee = By.cssSelector("ms-add-button button span>fa-icon");
    public static By deleteButton = By.xpath("//span[text()='Delete']");
    public static By getConfirmYes = By.xpath("//span[text()=' Yes ']");
    public static By photoButton = By.cssSelector(" div > div > div:nth-child(2) > button:nth-child(2)");


    //////////////////////////////////          Creating new employee          ////////////////////////////////
    public static By firstNameEmployee = By.cssSelector("ms-text-field[formcontrolname='firstName']>input");
    public static By lastNameEmployee = By.cssSelector("ms-text-field[formcontrolname='lastName']>input");
    public static By employeeID = By.cssSelector("mat-form-field[class]:nth-child(1) input[data-placeholder='Employee ID']");
    public static By documentType = By.xpath("//span[text()='Document Type']");
    public static By documentNumber = By.cssSelector("[data-placeholder='Document Number']");
    public static By rowWithName = By.cssSelector("tbody[role='rowgroup']");
    public static By plusButtonInsideEmployee = By.cssSelector("button-bar [caption='GENERAL.BUTTON.NEW']");
    public static By goToTheLastPage = By.cssSelector("button[aria-label='Last Page']");




    //div[class='mat-form-field-flex ng-tns-c149-68'] div ms-text-field button span:nth-child(3)
    //public static By lastNameEmployee = By.cssSelector("ms-text-field[formcontrolname='firstName']");
    //public static By lastNameEmployee = By.xpath("//span[text()='First Name']");


    //.group-items>fuse-nav-vertical-collapsable:nth-child(5) >div fuse-nav-vertical-item:nth-child(2) span
    //thead[role='rowgroup'] this is a row with names
}
