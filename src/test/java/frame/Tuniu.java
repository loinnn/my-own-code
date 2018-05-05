package frame;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Tuniu {
    WebDriver driver;
//    String baseURL = "http://www.tuniu.com/";
    String baseURL = "https://i.tuniu.com/changepicture";//上传头像页面

    @BeforeMethod
    public void beforeMethod() {
        System.setProperty("webdriver.firefox.bin", "E:/Firefox/Firefox/firefox.exe");
        driver = new FirefoxDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test
    public void launch() throws InterruptedException {
        driver.get(baseURL);
        getWindowHandle();
 //       login(By.linkText("登录"), "18682558330" , "940703lxl");
        login(By.className("line_2"), "18682558330" , "940703lxl");
        Thread.sleep(2000);
        String username = driver.findElement(By.className("fl")).getText();
        System.out.println("username is: " + username);
//        select(By.className("sc_name"));
        driver.findElement(By.id("head_img")).sendKeys(
                "C:\\Users\\mayn\\Pictures\\Saved Pictures\\1973139586841820628.jpg");
        Thread.sleep(3000);
        takeScreenShot();
    }

    private void takeScreenShot() {
        String dirName = "Tuniuscreenshot";
        if(! (new File(dirName).isDirectory())) {
            new File(dirName).mkdir();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = format.format(new Date());
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(dirName + File.separator + time + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getAbsolutePath());
    }

    public void login(By by, String username, String password) throws InterruptedException {
        driver.findElement(by).click();
        Set<String> all_handles = driver.getWindowHandles();
        Iterator<String> it = all_handles.iterator();
        String handle = null;
        while(it.hasNext()){
            handle = it.next();
            if(getWindowHandle().equals(handle))
                continue;
//跳入新窗口,并获得新窗口的driver - newWindow
            driver.switchTo().window(handle);
        }
        Thread.sleep(1000);

        String string = driver.findElement(By.id("login-tab-user")).getText();
        System.out.println("string is: " + string);
        if (string.equals("账户登录")) {
            driver.findElement(By.id("normal_tel")).sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("submit_login")).click();
        } else {
            System.out.println("没有进入登录页面");
            driver.quit();
        }
    }

    public String getWindowHandle() {
        String current_handle = driver.getWindowHandle();
        System.out.println("handle is: " + current_handle);
        return current_handle;
    }

    public void select(By by) {
        WebElement element = driver.findElement(by);
        Select select = new Select(element);
        System.out.println(select);
    }


}
