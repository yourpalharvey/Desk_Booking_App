package com.bjss.desk_booking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebDriverSystemTesting {

    private WebDriver webDriver;

    @Value("${local.server.port")
    private int port;

    @Test
    public void testingPageContents() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\c1863110\\OneDrive - Cardiff University\\Documents\\MSc Work\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=42227");
        options.addArguments("--headless");
        this.webDriver = new ChromeDriver(options);

//        WebDriverManager.firefoxdriver().setup();
//        webDriver = new FirefoxDriver();

        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/");
        assertTrue(webDriver.findElement(By.id("appName")).getText().contains("BJSS Desk App"));
        this.webDriver.get("http://localhost:" + Integer.toString(port) + "/user/quickbooking");
        assertTrue(webDriver.findElement(By.id("cardTitleTest")).getText().contains("Create quick booking:"));

        webDriver.quit();

    }
}
