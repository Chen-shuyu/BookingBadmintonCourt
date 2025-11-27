package com.Taifex;

import com.Taifex.entity.Players;
import com.Taifex.utility.ConfigReader;
import com.Taifex.utility.CsvReader;
import com.Taifex.utility.FormWorker;
import com.Taifex.utility.ProfileCopier;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final ConfigReader config = new ConfigReader();
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) throws Exception {

//        String googleFormUrl = config.getURL();
        String csvPath = config.getCsvPath();
        String formUrlFile = "./form_url.txt";
        String googleAccount = config.getGoogleAccount();
        String googlePassword = config.getGooglePassword();
        String profileMaster = "./profiles/master/";
        String profileBase ="./profiles/user";


        // 讀 Google 表單網址
        String googleFormUrl = Files.readAllLines(Paths.get(formUrlFile)).get(0).trim();

        // 讀取所有人的場地登記資料
        List<Players> players = CsvReader.readCsvToEntity(csvPath, config);
//        System.out.println(players.toString());

        // 打開google chrome
        initializeDriver();

        //登入 google
        GoogleLogin googleLogin = new GoogleLogin(driver, wait, googleAccount, googlePassword);
        googleLogin.login();

        // 複製 profile：master → userN
        for (int i = 0; i < players.size(); i++) {
            String to = profileBase + (i + 1);
            ProfileCopier.copyProfile(profileMaster, to);
        }

        // 建立 thread pool（建議可依 CPU 調整）
        int threadCount = Math.min(6, players.size());
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);


        // TODO 分配任務
        for (int i = 0; i < players.size(); i++) {
            String profilePath = profileBase + (i + 1);
            Players p = players.get(i);
            pool.execute(new FormWorker(profilePath, googleFormUrl, p));
        }
//        for (Players player : players) {
//            driver.get(googleFormUrl);
//            // 填表格
//            FillGoogleForm.fillForm(driver, player);
//        }


        // 等待所有任務完成
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

        System.out.println("All forms submitted!");

    }

    /**
     * 初始化 WebDriver
     */
    private static void initializeDriver() {
        try {
            logger.info("開始初始化 WebDriver...");

//            WebDriverManager.chromedriver().setup();
            String driverPath = config.getChromeDriverPath();
            System.setProperty("webdriver.chrome.driver", driverPath);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-data-dir=./profiles/master/");
            options.addArguments("profile-directory=Default");

            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
//            wait = new WebDriverWait(driver, 20);

//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();

            logger.info("WebDriver 初始化完成");

        } catch (Exception e) {
            logger.error("WebDriver 初始化失敗: {}", e.getMessage(), e);
            throw new RuntimeException("WebDriver 初始化失敗", e);
        }
    }


}
