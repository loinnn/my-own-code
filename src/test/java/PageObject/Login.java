package PageObject;

public class Login {

    public static void login(String account, String password) {
        TestCase.driver.findElement(LoginPage.account).sendKeys(account);
        TestCase.driver.findElement(LoginPage.passowrd).sendKeys(password);
        TestCase.driver.findElement(LoginPage.loginButton).click();
    }
}
