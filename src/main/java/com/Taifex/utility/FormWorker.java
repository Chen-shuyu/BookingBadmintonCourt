package com.Taifex.utility;

// File: FormWorker.java
import com.Taifex.FillGoogleForm;
import com.Taifex.entity.Players;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FormWorker implements Runnable {

    private final String profilePath;
    private final String formUrl;
    private final Players players;

    public FormWorker(String profilePath, String formUrl, Players players) {
        this.profilePath = profilePath;
        this.formUrl = formUrl;
        this.players = players;
    }

    @Override
    public void run() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=" + profilePath);
        options.addArguments("profile-directory=Default");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(formUrl);
            FillGoogleForm.fillForm(driver, players);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
