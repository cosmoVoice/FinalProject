package Blog;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class BlogWebTest {
    WebDriver driver;
    ChromeOptions options;
    BlogPage blogPage;

    private final String URL = "https://test-stand.gb.ru/login";
    private final String LOGIN = "2763";
    private final String PASSWORD = "f804d21145";
    private final String LOGINFIELD = "//input[@type = \"text\"]";
    private final String PASSWORDFIELD = "//input[@type = \"password\"]";
    private final String LOGINBUTTON = "//button";
    private final String NEXTBUTTON = "//a[text() = \"Next Page\"]";
    private final String POSTCREATIONBUTTON = "//div[contains(@class, \"button-block\")]";
    private final String HOMEBUTTON = "//nav/a";
    private final String ABOUTBUTTON = "//a[@href=\"/about\"]";
    private final String SUBMITPOSTBUTTON = "//div[@class=\"submit\"]//button";

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setOptions () {
        options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        blogPage = new BlogPage(driver);
        driver.get(URL);
    }
    @AfterEach
    void tearDown() {
        driver.quit();
    }
    @Test
    void logInValidDataTest() {
        blogPage.
                logIn(LOGINFIELD, PASSWORDFIELD, LOGIN, PASSWORD, LOGINBUTTON);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h1[text() = \"Blog\"]")));
    }
    @Test
    void logInNotValidDataTest() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD,"", "439f9697ba", LOGINBUTTON);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h2[text() = \"401\"]")));
    }
    @Test
    void logInValidDataCorner1Test() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, "Iu1", "74700c7697", LOGINBUTTON);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h1[text() = \"Blog\"]")));
    }
    @Test
    void logInValidDataCorner2Test() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, "Iuliaqwert1234567890", "f8a2434f9e", LOGINBUTTON);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h1[text() = \"Blog\"]")));
    }


    @Test
    void prevButtonIsDisabledTest() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, LOGIN, PASSWORD, LOGINBUTTON);
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[contains(@class, \"disabled\")]")));
    }
    @Test
    void nextButtonIsDisabledTest() throws InterruptedException {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, LOGIN, PASSWORD, LOGINBUTTON)
                .buttonClick(NEXTBUTTON);
        Thread.sleep(5000);
        driver
                .findElement(By.xpath(NEXTBUTTON))
                .click();
        Thread.sleep(5000);
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[contains(@class, \"disabled\")]")));
    }
    @Test
    void newPostCreatingTest() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, LOGIN, PASSWORD, LOGINBUTTON)
                .buttonClick(POSTCREATIONBUTTON)
                .newPostForm(
                        "//input[@type = \"text\"]",
                        "New test title.",
                        "//textarea[@maxlength=\"100\"]",
                        "New test description",
                        "//span[text() = \"Content\"]/parent::div/parent::div/parent::label",
                        "New test content"
                )
                .buttonClick(SUBMITPOSTBUTTON);
        Assertions.assertNotNull(By.xpath("//h1[text()= \"New test title.\"]"));
    }
    @Test
    void homeButtonTest() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, LOGIN, PASSWORD, LOGINBUTTON)
                .buttonClick(HOMEBUTTON);
        Assertions.assertNotNull(LOGINBUTTON);
    }
    @Test
    void aboutButtonTest() {
        blogPage
                .logIn(LOGINFIELD, PASSWORDFIELD, LOGIN, PASSWORD, LOGINBUTTON)
                .buttonClick(ABOUTBUTTON);
        Assertions.assertNotNull(driver.findElement(By.xpath("//h1[text()=\"About Page\"]")));
    }
}
