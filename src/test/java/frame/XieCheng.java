package frame;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class XieCheng {
    WebDriver driver;
    String baseURL = "http://www.ctrip.com/";

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
    public void launch() {
        driver.get(baseURL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        search(By.className("s_btn"));
        rightClick(By.id("btnSearch"));
        executeJS();
        doubleClick(By.id("btnSearch"));
        waitUntilElement(By.className("ui_tips_help"));
        moveToElement(By.id("c_ph_myhome"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement element = driver.findElement(By.id("myctripButton"));
        if (element.isDisplayed()) {
            String string = element.getText();
            String temp = element.getTagName();
            System.out.println("tagName is: " + temp);
            System.out.println("String的内容是：" + string);
            Assert.assertEquals(string, "登录");
        } else {
            System.out.println("没有获取到文本内容");
        }
    }

    public void select(By by) {
        WebElement element = driver.findElement(by);
//        System.out.println("element is: " + element);
        Select select = new Select(element); //定位到下拉框
        List<WebElement> elements = select.getOptions();
        System.out.println("下拉选项的个数为：" + elements.size());
        for (WebElement ele : elements) {
            System.out.println("下拉选项的值为：" + ele.getText()); //获取下拉选项中的值
        }
        try {
            select.selectByIndex(4);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void city(By by, String string) {
        driver.findElement(by).sendKeys(string);
    }

    public void search(By by) {
        city(By.id("HD_CityName"), "成都");
        select(By.id("J_roomCountList"));
        select(By.id("searchHotelLevelSelect"));
        driver.findElement(by).click();
    }

    public void waitUntilElement(By by) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void doubleClick(By by) {
       WebElement element = driver.findElement(by);
       Actions actions = new Actions(driver);
       actions.doubleClick(element).perform();
    }

    public void rightClick(By by) {
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();
    }

    //鼠标移动到元素上
    public void moveToElement(By by){
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    //将一个元素拖动到另一个元素
    public void dropToElement(By by1, By by2) {
    WebElement element1 = driver.findElement(by1);
    WebElement element2 = driver.findElement(by2);
    Actions actions = new Actions(driver);
    actions.clickAndHold(element1).moveToElement(element2).release(element1).perform();
    }

    public void takeScreenShot() {
        String dirName = "Xiechengscreenshot";
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

    public void executeJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById(\"txtKeyword\").setAttribute(\"value\",\"\")");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
