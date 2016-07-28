/**
 * Created by tshur on 28.07.2016.
 */


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;
@RunWith(value = Parameterized.class)
public class MailSearch {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String Word;
    public MailSearch(String Word){
        this.Word=Word;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        BufferedReader reader = new BufferedReader
                (new FileReader("C:\\Users\\tshur\\IdeaProjects\\MailTest\\src\\test\\java\\TestDataSearch"));
        List<Object[]> res = new ArrayList<Object[]>();
        String s;
        while((s = reader.readLine()) != null) {
            String[] params = s.split(" ");
            Object[] curData = new Object[1];
            curData[0] = params[0];
            res.add(curData);
        }
        return res;
    }
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://mail.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testMailSearch() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("q")).clear();
        driver.findElement(By.id("q")).sendKeys(Word);
        driver.findElement(By.id("search__button__wrapper__field")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
