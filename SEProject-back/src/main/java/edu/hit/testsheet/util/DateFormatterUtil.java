package edu.hit.testsheet.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;
import java.util.List;
import java.util.TimeZone;

/**
 * ClassName:DateFormatter
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/4 23:22
 * @author:shyboy
 */
@Component
public class DateFormatterUtil {

    // 定义可能的日期格式
    private static final List<SimpleDateFormat> dateFormats = Arrays.asList(
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
            new SimpleDateFormat("yyyy-M-d H:m:s")
    );

    static {
        // 设置所有格式的时区为 UTC
        for (SimpleDateFormat format : dateFormats) {
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }
    public static String formatDate(LocalDateTime dateTime) {
        // 创建DateTimeFormatter对象并设置自定义的日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm");
        // 使用DateTimeFormatter格式化LocalDateTime对象
        return dateTime.format(formatter);
    }

    public static String frontFormatDate(String frontTime) {
        // 输入格式
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // 输出格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-M-d H:m:s");

        String formattedDate = "";
        try {
            // 解析输入时间字符串
            Date date = inputFormat.parse(frontTime);
            // 格式化日期为目标格式
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static boolean isBeforeCurrentTime(String dateTime) {
        for (SimpleDateFormat format : dateFormats) {
            try {
                // 尝试解析输入时间字符串
                Date parsedDate = format.parse(dateTime);
                // 获取当前时间
                Date currentDate = new Date();
                // 比较两个日期
                return parsedDate.before(currentDate);
            } catch (ParseException e) {
                // 忽略异常并尝试下一个格式
            }
        }
        // 如果所有格式都无法解析，抛出异常或返回默认值
        throw new IllegalArgumentException("Invalid date format: " + dateTime);
    }

    public static String getCurrentTimeString() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFormat.format(new Date());
    }
}
