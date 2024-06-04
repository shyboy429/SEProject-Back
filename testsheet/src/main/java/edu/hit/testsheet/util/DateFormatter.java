package edu.hit.testsheet.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ClassName:DateFormatter
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/4 23:22
 * @author:shyboy
 */
public class DateFormatter {
    /**
     * 格式化日期时间对象为指定格式的字符串。
     * @param dateTime 要格式化的日期时间对象
     * @return 格式化后的日期时间字符串
     */
    public static String formatDate(LocalDateTime dateTime) {
        // 创建DateTimeFormatter对象并设置自定义的日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm");
        // 使用DateTimeFormatter格式化LocalDateTime对象
        return dateTime.format(formatter);
    }
}
