package edu.hit.testsheet.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
    public static String formatDate(LocalDateTime dateTime) {
        // 创建DateTimeFormatter对象并设置自定义的日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm");
        // 使用DateTimeFormatter格式化LocalDateTime对象
        return dateTime.format(formatter);
    }

    public static String frontFormatDate(String frontTime) {
        // 输入格式
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        inputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 输出格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String formattedDate = "";
        try {
            // 解析输入时间字符串
            Date date = inputFormat.parse(frontTime);
            // 格式化日期为目标格式
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + frontTime);
        }
        return formattedDate;
    }

    public static boolean isBeforeCurrentTime(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            // 尝试解析输入时间字符串
            Date parsedDate = format.parse(dateTime);
            // 获取当前时间
            Date currentDate = new Date();
            // 比较两个日期
            return parsedDate.before(currentDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateTime);
        }
    }

    public static long calculateDurationInMinutes(String startTime, String endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        try {
            Date startDate = dateFormat.parse(startTime);
            Date endDate = dateFormat.parse(endTime);

            long durationInMillis = endDate.getTime() - startDate.getTime();
            return TimeUnit.MILLISECONDS.toMinutes(durationInMillis);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }
    public static String getCurrentTimeString() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return outputFormat.format(new Date());
    }
}
