package com.Taifex;

import com.Taifex.entity.Players;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FillGoogleForm {

    public static void fillForm(WebDriver driver, String formUrl, List<Players> players) {
        driver.get(formUrl);

        try {
            Thread.sleep(2000);

            // 文字欄位
            WebElement textInput = driver.findElement(By.cssSelector("input[type='text']"));
            textInput.sendKeys("這是自動填寫的內容");

            // 單選按鈕（選第一個）
            WebElement radio = driver.findElement(By.cssSelector("div[role='radio']"));
            radio.click();

            Thread.sleep(1000);

            // 點擊送出
            WebElement submit = driver.findElement(By.cssSelector("div[role='button'][aria-label='提交']"));
            submit.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
