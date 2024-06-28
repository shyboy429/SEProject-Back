package edu.hit.testsheet.util;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName:DateFormatterUtilTest
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/28 17:09
 * @author:shyboy
 */
public class DateFormatterUtilTest {

    @Test
    public void testFormatDate() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 6, 28, 14, 30);
        String formattedDate = DateFormatterUtil.formatDate(dateTime);
        assertEquals("2023年6月28日 14:30", formattedDate);
    }

    @Test
    public void testFrontFormatDate() {
        String frontTime = "2023-06-28T14:30:00.000+08:00";
        String formattedDate = DateFormatterUtil.frontFormatDate(frontTime);
        assertEquals("2023-06-28 14:30:00", formattedDate);
    }

    @Test
    public void testFrontFormatDateWithInvalidDate() {
        String frontTime = "invalid-date";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DateFormatterUtil.frontFormatDate(frontTime);
        });
        assertEquals("Invalid date format: invalid-date", exception.getMessage());
    }

    @Test
    public void testIsBeforeCurrentTime() {
        String dateTime = "2023-06-28 14:30:00";
        assertTrue(DateFormatterUtil.isBeforeCurrentTime(dateTime));
    }

    @Test
    public void testIsBeforeCurrentTimeWithInvalidDate() {
        String dateTime = "invalid-date";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DateFormatterUtil.isBeforeCurrentTime(dateTime);
        });
        assertEquals("Invalid date format: invalid-date", exception.getMessage());
    }

    @Test
    public void testCalculateDurationInMinutes() {
        String startTime = "2023-06-28 14:00:00";
        String endTime = "2023-06-28 15:00:00";
        long duration = DateFormatterUtil.calculateDurationInMinutes(startTime, endTime);
        assertEquals(60, duration);
    }

    @Test
    public void testCalculateDurationInMinutesWithInvalidDate() {
        String startTime = "invalid-date";
        String endTime = "2023-06-28 15:00:00";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DateFormatterUtil.calculateDurationInMinutes(startTime, endTime);
        });
        assertEquals("Invalid date format.", exception.getMessage());
    }

    @Test
    public void testGetCurrentTimeString() {
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String expectedTime = outputFormat.format(new Date());
        assertEquals(expectedTime, currentTime);
    }
}