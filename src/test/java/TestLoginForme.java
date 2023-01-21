import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/*Testirati na stranici https://vue-demo.daniel-avellaneda.com login stranicu.

Test 1: Verifikovati da se u url-u stranice javlja ruta "/login".
Verifikovati da atribut type u polju za unos email ima vrednost "email" i za password da ima atribut type "password.

Test 2: Koristeci Faker uneti nasumicno generisan email i password i verifikovati da se pojavljuje poruka "User does not exist".

Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email)
 i pogresan password (generisan faker-om), da se pojavljuje poruka "Wrong password"
*/
public class TestLoginForme {

    private WebDriver driver;
    private Faker faker;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "D:\\ITBOOTCAMP\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        faker = new Faker();
    }

    @BeforeMethod
    public void beforeTest() {
        driver.get("https://vue-demo.daniel-avellaneda.com ");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void rutaLogin() {
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]/span"));
        loginButton.click();
        String expected = "https://vue-demo.daniel-avellaneda.com/login";
        String actual = driver.getCurrentUrl();

        Assert.assertEquals(actual, expected);

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement email = driver.findElement(By.id("email"));
        String actualEmail = email.getAttribute("type");
        String expectedEmail = "email";

        Assert.assertEquals(actualEmail, expectedEmail);

        WebElement password = driver.findElement(By.id("password"));
        String actualPassword = password.getAttribute("type");
        String expectedPassword = "password";

        Assert.assertEquals(actualPassword, expectedPassword);
    }

    @Test
    public void porukaUserDoesNotExist() {
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]/span"));
        loginButton.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement emailField = driver.findElement(By.name("email"));
        String vrednostEmail = faker.internet().emailAddress();
        emailField.sendKeys(vrednostEmail);

        WebElement passwordField = driver.findElement(By.name("password"));
        String vrednostPassword = faker.internet().password();
        passwordField.sendKeys(vrednostPassword);

        WebElement loginButton1 = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button/span"));
        loginButton1.click();

        WebDriverWait webDriverWait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]")));

        String expectedPoruka = "User does not exists";
        WebElement poruka = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualPoruka = poruka.getText();

        Assert.assertEquals(expectedPoruka, actualPoruka);

    }

    @Test
    public void porukaWrongPassword() {
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]/span"));
        loginButton.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("admin@admin.com");

        WebElement passwordField = driver.findElement(By.name("password"));
        String vrednostPassword = faker.internet().password();
        passwordField.sendKeys(vrednostPassword);

        WebElement loginButton1 = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button/span"));
        loginButton1.click();

        WebDriverWait webDriverWait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]")));

        String expectedPoruka = "Wrong password";
        WebElement poruka = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualPoruka = poruka.getText();

        Assert.assertEquals(expectedPoruka, actualPoruka);

    }


}