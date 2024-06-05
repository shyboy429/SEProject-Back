package edu.hit.testsheet.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName:DateFormatter
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/4 23:22
 * @author:shyboy
 */
public class DateFormatterUtil {
    public static String formatDate(LocalDateTime dateTime) {
        // 创建DateTimeFormatter对象并设置自定义的日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm");
        // 使用DateTimeFormatter格式化LocalDateTime对象
        return dateTime.format(formatter);
    }
}
