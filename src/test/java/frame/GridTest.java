package frame;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class GridTest {

    @Test
    public void grid() throws MalformedURLException, InterruptedException {
        DesiredCapabilities  dc = DesiredCapabilities.firefox();
        System.setProperty("webdriver.firefox.bin", "E:/Firefox/Firefox/firefox.exe");
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.12.1:4445/wd/hub"), dc);
        driver.get("http:www.baidu.com");
        Thread.sleep(1000);
        driver.quit();
    }
}
