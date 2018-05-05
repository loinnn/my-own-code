package frame;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class Demo1 {
    WebDriver driver;
    String baseURL = "https://www.baidu.com/";

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
    public void test() throws InterruptedException {
        driver.get(baseURL);
        WebElement element = driver.findElement(By.id("kw"));
        String tagName = element.getTagName();
        System.out.println("tagName is: " + tagName);
        element.sendKeys("selenium");
        driver.findElement(By.id("su")).click();
        Thread.sleep(2000);
        String temp = driver.findElement(By.className("nums")).getText();
//        Assert.assertEquals(temp, "搜索工具\n" + "百度为您找到相关结果约*个");
        System.out.println(temp);
        moveToElement();
        buttonSelect();
        alert();
//        takeScreenShot();
    }

    public void moveToElement() {
        //鼠标悬停，然后再点击出现的元素
        /**
         * 鼠标移动到百度首页设置按钮，再点击搜索设置按钮
         */
        WebElement element = driver.findElement(By.className("pf"));
        new Actions(driver).moveToElement(element).perform();
        driver.findElement(By.className("setpref")).click();
    }

    public void buttonSelect() {
        //判断元素显示等
        /**
         * 进入到搜索设置页面后，查看sh_1这个元素的各种状态
         */
        WebElement element = driver.findElement(By.id("sh_1"));
        Boolean b1 = element.isDisplayed();
        System.out.println("b1 is: " + b1);
        Boolean b2 = element.isEnabled();
        System.out.println("b2 is: " + b2);
        Boolean b3 = element.isSelected();
        Assert.assertFalse(b3);
        System.out.println("b3 is: " + b3);
    }

    public void alert() throws InterruptedException {
        driver.findElement(By.className("prefpanelrestore")).click();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert(); //切换到alert
        String string = alert.getText();
        Assert.assertEquals(string, "已经恢复默认设置");
        alert.accept();//关闭alert
    }

    public void takeScreenShot() {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("F:\\selenium视频\\day2\\test1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
