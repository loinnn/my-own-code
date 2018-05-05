package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestCase {

    static WebDriver driver;
    String baseURL = "https://passport.tuniu.com/login?origin=http://www.tuniu.com/ssoConnect";

    @BeforeMethod
    public void beforeMethod() {
        System.setProperty("webdriver.firefox.bin", "E:/Firefox/Firefox/firefox.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test
    public void loginTestCase() throws InterruptedException {
        driver.get(baseURL);
        Thread.sleep(3000);
        Login.login("18682558330", "940703lxl");
    }
}
