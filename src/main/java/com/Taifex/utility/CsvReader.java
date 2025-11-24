package com.Taifex.utility;

import com.Taifex.entity.Players;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<Players> readCsvToEntity(String filePath) throws IOException {
        List<Players> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // 跳過 Header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] tokens = line.split(",", -1);   // 保留空欄位
                if (tokens.length < 3) continue;         // 欄位不足跳過

                Players entity = new Players();
                entity.setName(tokens[0].trim());
                entity.setWeekNO(convertWeekdayToNumber(tokens[2].trim()));
                entity.setTimeString(tokens[3].trim());
                entity.setId5(tokens[4].trim());
                list.add(entity);
            }
        }

        return list;
    }


    public static Integer convertWeekdayToNumber(String weekday) {
        if (weekday == null) return null;

        switch (weekday.trim()) {
            case "星期一": return 1;
            case "星期二": return 2;
            case "星期三": return 3;
            case "星期四": return 4;
            case "星期五": return 5;
            case "星期六": return 6;
            case "星期日":
            case "星期天": return 7;
            default: return null; // 不認得就回傳 null
        }
    }


}
