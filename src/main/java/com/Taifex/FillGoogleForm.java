package com.Taifex;

import com.Taifex.entity.Players;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FillGoogleForm {

    public static void fillForm(WebDriver driver, String formUrl, Players players) {
        driver.get(formUrl);

        try {
            Thread.sleep(2000);

            // 點選 紀錄電子郵件

            // 點選 繼續

            // 填 申請人姓名

            // 填 身分證後五碼

            // 填 申請人手機

            // 選 申請場地

            // 選 使用星期

            // 選 使用時段

            // 文字欄位
//            WebElement textInput = driver.findElement(By.cssSelector("input[type='text']"));
//            textInput.sendKeys("這是自動填寫的內容");

            // 單選按鈕（選第一個）
//            WebElement radio = driver.findElement(By.cssSelector("div[role='radio']"));
//            radio.click();

//            Thread.sleep(1000);

            // 點擊送出
            WebElement submit = driver.findElement(By.cssSelector("div[role='button'][aria-label='提交']"));
            submit.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
