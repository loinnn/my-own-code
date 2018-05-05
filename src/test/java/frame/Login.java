package frame;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {
        WebDriver driver;
        String baseURL = "https://passport.tuniu.com/login?origin=http://www.tuniu.com/ssoConnect";

        @BeforeMethod
        public void beforeMethod() {
            System.setProperty("webdriver.chrome.driver","F:\\chromedriver\\chromedriver_win32\\chromedriver.exe");//chromedriver服务地址
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        @AfterMethod
        public void afterMethod() {
            driver.quit();
        }

        @Test
        public void test() throws InterruptedException {
            driver.get(baseURL);
            Boolean flag = loginTest("18682558330", "1234");
            while(flag == false) {
                loginTest("18682558330", "940703lxl");
            }
        }

        public boolean loginTest(String account, String pwd) {
            driver.findElement(By.id("normal_tel")).sendKeys(account);
            driver.findElement( By.name("password")).sendKeys(pwd);
            driver.findElement(By.name("submit_login")).click();
            try {
                driver.findElement(By.className("fl")).isDisplayed();
                return true;
            } catch (NoSuchElementException e) {
                e.getStackTrace();
                System.out.println("元素不存在");
                return false;
            }
        }
}
