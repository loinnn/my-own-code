package frame;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Mail {

    WebDriver driver;
    String baseURL = "https://mail.163.com/";

    @BeforeMethod
    public void beforeMethod() {
//        System.setProperty("webdriver.firefox.bin", "E:/Firefox/Firefox/firefox.exe");
//        driver = new FirefoxDriver();

        System.setProperty("webdriver.chrome.driver","F:\\chromedriver\\chromedriver_win32\\chromedriver.exe");//chromedriver服务地址
        driver = new ChromeDriver();
//        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test(dataProvider = "data")
    public void launch(String browser) throws InterruptedException {
        driver.get(baseURL);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        switchToFrame("x-URS-iframe");
//        driver.findElement(By.id("changepage")).click();
//        register(By.id("nameIpt"), "123");
        login(By.name("email"), "18682558330", By.name("password"), "940703", By.id("dologin"));
        if(elementExist(By.id("spnUid"))) {
            System.out.println("登录成功");
            Thread.sleep(5000);
            String account = driver.findElement(By.id("spnUid")).getText();
            System.out.println("account是:" + account);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            sendEmail("18682558330" , "测试");
        } else {
            System.out.println("登录失败");
            driver.navigate().refresh();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            switchToFrame("x-URS-iframe");
            login(By.name("email"), "18682558330", By.name("password"), "940703lxl", By.id("dologin"));
                if(elementExist(By.id("spnUid"))) {
                    System.out.println("登录成功");
                    Thread.sleep(5000);
                    String account = driver.findElement(By.id("spnUid")).getText();
                    System.out.println("account是:" + account);
                    sendEmail("18682558330" , "邮件测试");
                } else {
                    driver.quit();
                }
        }
    }


    public boolean elementExist(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            e.getStackTrace();
            System.out.println("元素不存在");
            return false;
        }
    }

    public void switchToFrame(String string) {
        driver.switchTo().frame(string);
    }

    public void switchToWindow() {
        String handle = driver.getWindowHandle();
        for(String handles : driver.getWindowHandles()) {
            if (handles.equals(handle)) {
                continue;
            }
            driver.switchTo().window(handles);
        }
    }

    public void register(By addr, String address) {
        switchToWindow();
        driver.findElement(addr).sendKeys(address);
    }

    public void login(By by1,String account, By by2, String pwd, By by3) {
        driver.findElement(by1).sendKeys(account);
        driver.findElement(by2).sendKeys(pwd);
        driver.findElement(by3).click();
    }

    public void loginClear(By by1, By by2) {
        driver.findElement(by1).clear();
        driver.findElement(by2).clear();
    }

    public void sendEmail(String user, String title) {
        driver.findElement(By.className("//*[@id=\"_mail_component_70_70\"]/span[2]")).click();
        driver.findElement(By.cssSelector("[class='js-component-emailtips.nui-ipt-placeholder']")).sendKeys(user);
        driver.findElement(By.className("nui-ipt-input")).sendKeys(title);
        driver.findElement(By.className("nui-btn-text")).click();
    }

    @DataProvider(name = "data")
    public Object[][] test1() {
        return new Object[][] {
                {"firefox"},
                {"chrome"}};
        }
    }

