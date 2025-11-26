package com.Taifex;

import com.Taifex.entity.Players;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class FillGoogleForm {

    private static final String[] WEEKDAYS_CHN = {
            "", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
    };

    private static final String[] WEEKDAYS_INT = {
            "", "星期1", "星期2", "星期3", "星期4", "星期5", "星期6", "星期天"
    };

    public static void fillForm(WebDriver driver, Players player) {
//        driver.get(formUrl);

        try {
            Thread.sleep(500);

            // 點選 紀錄電子郵件
            System.out.println("1-1.紀錄電子郵件");
            WebElement checkbox = driver.findElement(By.cssSelector("div[role='checkbox']"));
            boolean checked = Boolean.parseBoolean(checkbox.getAttribute("aria-checked"));

            if (!checked) {
                checkbox.click();
            }

            // 點選 繼續
            System.out.println("1-2.繼續");
            driver.findElement(By.xpath("//span[contains(text(), '繼續')]/ancestor::div[@role='button']")).click();

            Thread.sleep(500);

            // 第二頁各個問題的Element，第一個為標題所以要跳過
            List<WebElement> questions = driver.findElements(By.cssSelector("div[role='listitem']"));

            // 填 申請人姓名
            System.out.println("2-1.申請人姓名");
            questions.get(1).findElement(By.cssSelector("input[type='text']")).sendKeys(player.getName());

            // 填 身分證後五碼
            System.out.println("2-2.身分證後五碼");
            questions.get(2).findElement(By.cssSelector("input[type='text']")).sendKeys(player.getId5());

            // 填 申請人手機
            System.out.println("2-3.申請人手機");
            questions.get(3).findElement(By.cssSelector("input[type='text']")).sendKeys(player.getPhone());

            // 選 申請場地
            System.out.println("2-4.申請場地");
            List<WebElement> radiosPlace = questions.get(4).findElements(By.cssSelector("div[role='radio']"));

            for (WebElement r : radiosPlace) {
                String label = r.getText().trim();
                if (label.contains("活動中心2樓羽球場")) {
                    r.click();
                    break;
                }
            }

            // 選 使用星期
            System.out.println("2-5.使用星期");
//            List<WebElement> radiosWeekNo = questions.get(5).findElements(By.cssSelector("div[role='radio']"));

            String weekChinese = "星期" + WEEKDAYS_CHN[player.getWeekNO()];
            String weekNum = "星期" + player.getWeekNO();

            WebElement radiosWeekNo = questions.get(5).findElement(By.cssSelector(
                    "div[role='radio'][aria-label='" + weekChinese + "'], " +
                            "div[role='radio'][aria-label='" + weekNum + "']"
            ));
            radiosWeekNo.click();


            // 選 使用時段
            System.out.println("2-6.使用時段");
//            List<WebElement> radiosTime = questions.get(5).findElements(By.cssSelector("div[role='radio']"));

            WebElement selectedRadio = null;
            String begTimeString = player.getTimeString().split("~")[0];
            String endTimeString = player.getTimeString().split("~")[1];
            try {
                // 嘗試完全匹配
                String xpath = String.format(
                        ".//div[@role='radio' and contains(@aria-label, '%s') and contains(@aria-label, '%s') and contains(@aria-label, '羽球')]",
                        begTimeString,endTimeString
                );
                selectedRadio = questions.get(6).findElement(By.xpath(xpath));

            } catch (NoSuchElementException e) {
                // 降級為只匹配時間
                String xpath = String.format(
                        ".//div[@role='radio' and contains(@aria-label, '%s') and contains(@aria-label, '%s')]",
                        begTimeString,endTimeString
                );
                selectedRadio = questions.get(6).findElement(By.xpath(xpath));
            }

            selectedRadio.click();
//            String actualLabel = selectedRadio.getAttribute("aria-label");
//            System.out.println("    已選擇: " + actualLabel);

            // 點擊送出
            System.out.println("2-7.點擊送出");
            WebElement submit = null;
            try {
                submit = driver.findElement(By.cssSelector("div[role='button'][aria-label='Submit']"));

            } catch (NoSuchElementException e) {
                submit = driver.findElement(By.xpath("//div[@role='button']//span[contains(text(), '提交')]"));

            }
            submit.click();

            System.out.println("...END...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
