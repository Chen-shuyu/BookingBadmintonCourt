package com.Taifex;

import com.Taifex.entity.Players;
import com.Taifex.utility.ConfigReader;
import com.Taifex.utility.CsvReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final ConfigReader config = new ConfigReader();
    private static WebDriver driver;
    private static WebDriverWait wait;
    public static void main(String[] args) throws IOException {

        String url = config.getURL();
        String csvPath = config.getCsvPath();
        String googleAccount = config.getGoogleAccount();
        String googlePassword = config.getGooglePassword();

        // 讀取所有人的場地登記資料
        List<Players> players = CsvReader.readCsvToEntity(csvPath);
//        System.out.println(players.toString());

        // 打開google chrome
        initializeDriver();

        //登入 google
        GoogleLogin googleLogin = new GoogleLogin(driver, wait,googleAccount, googlePassword );
        googleLogin.login();

        // 填表格
        FillGoogleForm.fillForm(driver,url,players);

    }

    /**
     * 初始化 WebDriver
     */
    private static void initializeDriver() {
        try {
            logger.info("開始初始化 WebDriver...");

            WebDriverManager.chromedriver().setup();
            String driverPath = config.getChromeDriverPath();
            System.setProperty("webdriver.chrome.driver", driverPath);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, 20);

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();

            logger.info("WebDriver 初始化完成");

        } catch (Exception e) {
            logger.error("WebDriver 初始化失敗: {}", e.getMessage(), e);
            throw new RuntimeException("WebDriver 初始化失敗", e);
        }
    }


}
