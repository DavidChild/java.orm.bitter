package io.github.davidchild.bitter.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

    /** Days of the week */
    public static final int WEEK_DAYS = 7;
    /** Number of months in a year */
    public static final int YEAR_MONTHS = 12;
    /** Hours in a day */
    public static final int DAY_HOURS = 24;
    /** Minutes per hour */
    public static final int HOUR_MINUTES = 60;
    /** Minutes per day (24 * 60) */
    public static final int DAY_MINUTES = 1440;
    /** Seconds in a minute */
    public static final int MINUTE_SECONDS = 60;
    /** Seconds in an hour (60 * 60) */
    public static final int HOUR_SECONDS = 3600;
    /** Seconds of the day (24 * 60 * 60) */
    public static final int DAY_SECONDS = 86400;
    /** The number of milliseconds in a second */
    public static final long SECOND_MILLISECONDS = 1000L;
    /** Milliseconds per minute (60 * 1000) */
    public static final long MINUTE_MILLISECONDS = 60000L;
    /** Milliseconds per hour (60 * 60 * 1000) */
    public static final long HOUR_MILLISECONDS = 3600000L;
    /** Number of milliseconds in a day (24 * 60 * 60 * 1000) */
    public static final long DAY_MILLISECONDS = 86400000L;
    /** Monday */
    public static final int WEEK_1_MONDAY = 1;
    /** Tuesday */
    public static final int WEEK_2_TUESDAY = 2;
    /** Wednesday */
    public static final int WEEK_3_WEDNESDAY = 3;
    /** Thursday */
    public static final int WEEK_4_THURSDAY = 4;
    /** Friday */
    public static final int WEEK_5_FRIDAY = 5;
    /** Saturday */
    public static final int WEEK_6_SATURDAY = 6;
    /** Sunday */
    public static final int WEEK_7_SUNDAY = 7;
    /** January */
    public static final int MONTH_1_JANUARY = 1;
    /** February */
    public static final int MONTH_2_FEBRUARY = 2;
    /** March */
    public static final int MONTH_3_MARCH = 3;
    /** April */
    public static final int MONTH_4_APRIL = 4;
    /** May */
    public static final int MONTH_5_MAY = 5;
    /** June */
    public static final int MONTH_6_JUNE = 6;
    /** July */
    public static final int MONTH_7_JULY = 7;
    /** August */
    public static final int MONTH_8_AUGUST = 8;
    /** September */
    public static final int MONTH_9_SEPTEMBER = 9;
    /** October */
    public static final int MONTH_10_OCTOBER = 10;
    /** November */
    public static final int MONTH_11_NOVEMBER = 11;
    /** December */
    public static final int MONTH_12_DECEMBER = 12;
    /** Show to date */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /** Show to hour */
    public static final String FORMAT_HOUR = "yyyy-MM-dd HH";
    /** Display to minute */
    public static final String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
    /** Display to seconds */
    public static final String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    /** Show in milliseconds */
    public static final String FORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss:SSS";
    /** Display to date (numeric format) */
    public static final String FORMAT_NO_DATE = "yyyyMMdd";
    /** Display to hour (numeric format) */
    public static final String FORMAT_NO_HOUR = "yyyyMMddHH";
    /** Display to minute (numerical format) */
    public static final String FORMAT_NO_MINUTE = "yyyyMMddHHmm";
    /** Display to seconds (numeric format) */
    public static final String FORMAT_NO_SECOND = "yyyyMMddHHmmss";
    /** Display to ms (numeric format) */
    public static final String FORMAT_NO_MILLISECOND = "yyyyMMddHHmmssSSS";
    /** Time Formatter Collection */
    private static final Map<String, SimpleDateFormat> simpleDateFormatMap = new HashMap<String, SimpleDateFormat>();
    static {
        simpleDateFormatMap.put(FORMAT_DATE, new SimpleDateFormat(FORMAT_DATE));
        simpleDateFormatMap.put(FORMAT_HOUR, new SimpleDateFormat(FORMAT_HOUR));
        simpleDateFormatMap.put(FORMAT_MINUTE, new SimpleDateFormat(FORMAT_MINUTE));
        simpleDateFormatMap.put(FORMAT_SECOND, new SimpleDateFormat(FORMAT_SECOND));
        simpleDateFormatMap.put(FORMAT_MILLISECOND, new SimpleDateFormat(FORMAT_MILLISECOND));
        simpleDateFormatMap.put(FORMAT_NO_DATE, new SimpleDateFormat(FORMAT_NO_DATE));
        simpleDateFormatMap.put(FORMAT_NO_HOUR, new SimpleDateFormat(FORMAT_NO_HOUR));
        simpleDateFormatMap.put(FORMAT_NO_MINUTE, new SimpleDateFormat(FORMAT_NO_MINUTE));
        simpleDateFormatMap.put(FORMAT_NO_SECOND, new SimpleDateFormat(FORMAT_NO_SECOND));
        simpleDateFormatMap.put(FORMAT_NO_MILLISECOND, new SimpleDateFormat(FORMAT_NO_MILLISECOND));
    }

    /**
     * Get the specified time formatter
     *
     * @ param formatStyle Time format @ return Time Formatter
     */
    private static SimpleDateFormat getSimpleDateFormat(String formatStyle) {
        SimpleDateFormat dateFormat = simpleDateFormatMap.get(formatStyle);
        if (Objects.nonNull(dateFormat)) {
            return dateFormat;
        }
        return new SimpleDateFormat(formatStyle);
    }

    /**
     * Convert Date format time to specified format time @ param date Date @ param formatStyle converts the specified
     * format (such as: yyyy MM dd HH: mm: ss) @ return Conversion time
     */
    public static String format(Date date, String formatStyle) {
        if (Objects.isNull(date)) {
            return "";
        }
        return getSimpleDateFormat(formatStyle).format(date);
    }

    /**
     * Convert Date format time to yyyy MM dd format time
     *
     * @ param date Date @ return yyyy MM dd format time (e.g. 2022-06-17)
     */
    public static String formatDate(Date date) {
        return format(date, FORMAT_DATE);
    }

    /**
     * Convert Date format time to yyyy MM dd HH: mm: ss format time
     *
     * @ param date Date @ return yyyy MM dd HH: mm: ss format time (e.g. 2022-06-17 16:06:17)
     */
    public static String formatDateTime(Date date) {
        return format(date, FORMAT_SECOND);
    }

    /**
     * Convert Date format time to yyyy MM dd HH: mm: ss: SSS format time
     *
     * @ param date Date @ return yyyy MM dd HH: mm: ss: SSS format time (such as: 2022-06-17 16:06:17:325)
     */
    public static String formatDateTimeStamp(Date date) {
        return format(date, FORMAT_MILLISECOND);
    }

    /**
     * Convert the time in yyyy MM dd format to the time in Date format
     *
     * @ param dateString yyyy MM dd format time (such as 2022-06-17) @ return Date
     */
    public static Date parseDate(String dateString) {
        return parse(dateString, FORMAT_DATE);
    }

    /**
     * Convert yyyy MM dd HH: mm: ss format time to Date format time
     *
     * @ param dateTimeStr yyyy MM dd HH: mm: ss format time (e.g. 2022-06-17 16:06:17) @ return Date
     */
    public static Date parseDateTime(String dateTimeStr) {
        return parse(dateTimeStr, FORMAT_SECOND);
    }

    /**
     * Convert yyyy MM dd HH: mm: ss: SSS format time to Date format time
     *
     * @ param dateTimeStr yyyy MM dd HH: mm: ss: SSS format time (e.g. 2022-06-17 16:06:17) @ return Date
     */
    public static Date parseDateTimeStamp(String dateTimeStampStr) {
        return parse(dateTimeStampStr, FORMAT_MILLISECOND);
    }

    /**
     * Convert string format time to Date format time
     *
     * @ param dateString string time (for example: 2022-06-17 16:06:17) @ return formatStyle @ return Date
     */
    public static Date parse(String dateString, String formatStyle) {
        String s = getString(dateString);
        if (s.isEmpty()) {
            return null;
        }
        try {
            return getSimpleDateFormat(formatStyle).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get valid contents of string
     *
     * @ param s string @ return Valid content
     */
    private static String getString(String s) {
        return Objects.isNull(s) ? "" : s.trim();
    }

    /**
     * Get the start time of the day (i.e.: 0:00, 0:00, 0sec, 0ms)
     *
     * @ param date Specify the time @ return Start time of the day
     */
    public static Date getDateStart(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get the deadline of a day (i.e.: 23:59:59 seconds 999 milliseconds)
     *
     * @ param date Specify the time @ return Start time of the day
     */
    public static Date getDateEnd(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Get date number
     *
     * @ param date @ return Date number
     */
    public static int getDateNo(Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        return Integer.valueOf(format(date, FORMAT_NO_DATE));
    }

    /**
     * Get date time number (to seconds)
     *
     * @ param date @ return Date number
     */
    public static long getDateTimeNo(Date date) {
        if (Objects.isNull(date)) {
            return 0L;
        }
        return Long.valueOf(format(date, FORMAT_NO_SECOND));
    }

    /**
     * Get date time number (to milliseconds)
     *
     * @ param date @ return Date number
     */
    public static long getDateTimeStampNo(Date date) {
        if (Objects.isNull(date)) {
            return 0L;
        }
        return Long.valueOf(format(date, FORMAT_NO_MILLISECOND));
    }

    /**
     * 获取星期几
     *
     * @param date 时间
     * @return 0（时间为空）， 1（周一）， 2（周二），3（周三），4（周四），5（周五），6（周六），7（周日）
     */
    public static int getWeek(Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getWeek(calendar);
    }

    /**
     * 获取星期几
     *
     * @param calendar 时间
     * @return 0（时间为空）， 1（周一）， 2（周二），3（周三），4（周四），5（周五），6（周六），7（周日）
     */
    private static int getWeek(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    /**
     * 获取该日期是今年的第几周（以本年的周一为第1周，详见下面说明）<br>
     *
     * 【说明】<br>
     * 比如 2022-01-01（周六）和 2022-01-02（周日）虽然在 2022 年里，但他们两天则属于 2021 年最后一周，<br>
     * 那么这两天不会算在 2022 年第 1 周里，此时会返回 0 ；而 2022 年第 1 周将从 2022-01-03（周一） 开始计算。<br>
     *
     * @param date 时间
     * @return -1（时间为空）， 0（为上个年的最后一周），其他数字（今年的第几周）
     */
    public static int getWeekOfYear(Date date) {
        if (Objects.isNull(date)) {
            return -1;
        }
        int weeks = getWeekOfYearIgnoreLastYear(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int week = getWeek(calendar);
        if (week == 1) {
            return weeks;
        }
        return weeks - 1;
    }

    /**
     * 获取今年的第几周（以本年的1月1日为第1周第1天）<br>
     *
     * @param date 时间
     * @return -1（时间为空），其他数字（今年的第几周）
     */
    public static int getWeekOfYearIgnoreLastYear(Date date) {
        if (Objects.isNull(date)) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int days = calendar.get(Calendar.DAY_OF_YEAR);
        int weeks = days / 7;
        // 如果是 7 的倍数，则表示恰好是多少周
        if (days % 7 == 0) {
            return weeks;
        }
        // 如果有余数，则需要再加 1
        return weeks + 1;
    }

    /**
     * 获取时间节点对象
     *
     * @param date 时间对象
     * @return DateNode
     */
    public static DateNode getDateNode(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateNode node = new DateNode();
        node.setTime(format(date, FORMAT_MILLISECOND));
        node.setYear(calendar.get(Calendar.YEAR));
        node.setMonth(calendar.get(Calendar.MONTH) + 1);
        node.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        node.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        node.setMinute(calendar.get(Calendar.MINUTE));
        node.setSecond(calendar.get(Calendar.SECOND));
        node.setMillisecond(calendar.get(Calendar.MILLISECOND));
        node.setWeek(getWeek(calendar));
        node.setDayOfYear(calendar.get(Calendar.DAY_OF_YEAR));
        node.setWeekOfYear(getWeekOfYear(date));
        node.setWeekOfYearIgnoreLastYear(getWeekOfYearIgnoreLastYear(date));
        node.setMillisecondStamp(date.getTime());
        node.setSecondStamp(node.getMillisecondStamp() / 1000);
        return node;
    }

    /**
     * 日期变更
     *
     * @param date 指定日期
     * @param field 变更属性（如变更年份，则该值为 Calendar.DAY_OF_YEAR）
     * @param amount 变更大小（大于 0 时增加，小于 0 时减少）
     * @return 变更后的日期时间
     */
    public static Date add(Date date, int field, int amount) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 指定日期加减年份
     *
     * @param date 指定日期
     * @param year 变更年份（大于 0 时增加，小于 0 时减少）
     * @return 变更年份后的日期
     */
    public static Date addYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 指定日期加减月份
     *
     * @param date 指定日期
     * @param month 变更月份（大于 0 时增加，小于 0 时减少）
     * @return 变更月份后的日期
     */
    public static Date addMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 指定日期加减天数
     *
     * @param date 指定日期
     * @param day 变更天数（大于 0 时增加，小于 0 时减少）
     * @return 变更天数后的日期
     */
    public static Date addDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 指定日期加减星期
     *
     * @param date 指定日期
     * @param week 变更星期数（大于 0 时增加，小于 0 时减少）
     * @return 变更星期数后的日期
     */
    public static Date addWeek(Date date, int week) {
        return add(date, Calendar.WEEK_OF_YEAR, week);
    }

    /**
     * 指定日期加减小时
     *
     * @param date 指定日期时间
     * @param hour 变更小时数（大于 0 时增加，小于 0 时减少）
     * @return 变更小时数后的日期时间
     */
    public static Date addHour(Date date, int hour) {
        return add(date, Calendar.HOUR_OF_DAY, hour);
    }

    /**
     * 指定日期加减分钟
     *
     * @param date 指定日期时间
     * @param minute 变更分钟数（大于 0 时增加，小于 0 时减少）
     * @return 变更分钟数后的日期时间
     */
    public static Date addMinute(Date date, int minute) {
        return add(date, Calendar.MINUTE, minute);
    }

    /**
     * 指定日期加减秒
     *
     * @param date 指定日期时间
     * @param second 变更秒数（大于 0 时增加，小于 0 时减少）
     * @return 变更秒数后的日期时间
     */
    public static Date addSecond(Date date, int second) {
        return add(date, Calendar.SECOND, second);
    }

    /**
     * 指定日期加减秒
     *
     * @param date 指定日期时间
     * @param millisecond 变更毫秒数（大于 0 时增加，小于 0 时减少）
     * @return 变更毫秒数后的日期时间
     */
    public static Date addMillisecond(Date date, int millisecond) {
        return add(date, Calendar.MILLISECOND, millisecond);
    }

    /**
     * 获取该日期所在周指定星期的日期
     *
     * @param date 日期所在时间
     * @return index 指定星期（1 - 7 分别对应星期一到星期天）
     */
    public static Date getWeekDate(Date date, int index) {
        if (index < WEEK_1_MONDAY || index > WEEK_7_SUNDAY) {
            return null;
        }
        int week = getWeek(date);
        return addDay(date, index - week);
    }

    /**
     * 获取该日期所在周开始日期
     *
     * @param date 日期所在时间
     * @return 所在周开始日期
     */
    public static Date getWeekDateStart(Date date) {
        return getDateStart(getWeekDate(date, WEEK_1_MONDAY));
    }

    /**
     * 获取该日期所在周开始日期
     *
     * @param date 日期所在时间
     * @return 所在周开始日期
     */
    public static Date getWeekDateEnd(Date date) {
        return getWeekDateEnd(getWeekDate(date, WEEK_7_SUNDAY));
    }

    /**
     * 获取该日期所在周的所有日期（周一到周日）
     *
     * @param date 日期
     * @return 该日照所在周的所有日期
     */
    public static List<Date> getWeekDateList(Date date) {
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        // 获取本周开始时间
        Date weekFromDate = getWeekDateStart(date);
        // 获取本周截止时间
        Date weekeEndDate = getWeekDateEnd(date);
        return getBetweenDateList(weekFromDate, weekeEndDate, true);
    }

    /**
     * 获取该日期所在周的所有日期（周一到周日）
     *
     * @param dateString
     * @return 该日照所在周的所有日期
     */
    public static List<String> getWeekDateList(String dateString) {
        Date date = parseDate(dateString);
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        return getDateStrList(getWeekDateList(date));
    }

    /**
     * 获取该日期所在月的所有日期
     *
     * @param date
     * @return 该日照所月的所有日期
     */
    public static List<Date> getMonthDateList(Date date) {
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        Date monthDateStart = getMonthDateStart(date);
        Date monthDateEnd = getMonthDateEnd(date);
        return getBetweenDateList(monthDateStart, monthDateEnd, true);
    }

    /**
     * 获取该日期所在月的所有日期
     *
     * @param dateString
     * @return 该日照所月的所有日期
     */
    public static List<String> getMonthDateList(String dateString) {
        Date date = parseDate(dateString);
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        return getDateStrList(getMonthDateList(date));
    }

    /**
     * 获取本日期所在月第一天
     *
     * @param date 日期
     * @return 本日期所在月第一天
     */
    public static Date getMonthDateStart(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDateStart(calendar.getTime());
    }

    /**
     * 获取本日期所在月最后一天
     *
     * @param date 日期
     * @return 本日期所在月最后一天
     */
    public static Date getMonthDateEnd(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Date monthDateStart = getMonthDateStart(date);
        Date nextMonthDateStart = getMonthDateStart(addMonth(monthDateStart, 1));
        return getDateEnd(addDay(nextMonthDateStart, -1));
    }

    /**
     * 获取两个日期相差的天数（以日期为单位计算，不以24小时制计算，详见下面说明）<br>
     *
     * 【说明】比如 2022-06-17 23:00:00 和 2022-06-17 01:00:00，两者虽然只相差 2 个小时，但也算相差 1 天 <br>
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差天数（若返回 -1，则至少有一个日期存在为空，此时不能进行比较）
     */
    public static int countBetweenDays(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return -1;
        }
        // 获取两个日期 0 点 0 时 0 分 0 秒 0 毫秒时的时间戳（毫秒级）
        long t1 = getDateStart(date1).getTime();
        long t2 = getDateStart(date2).getTime();
        // 相差天数 = 相差的毫秒数 / 一天的毫秒数
        return (int)(Math.abs(t1 - t2) / DAY_MILLISECONDS);
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 两个日期之间的所有日期的开始时间
     */
    public static List<Date> getBetweenDateList(Date date1, Date date2) {
        return getBetweenDateList(date1, date2, false);
    }

    /**
     * Get all dates between two dates
     *
     * @ param date1 Date 1 @ param date2 Date 2 @ return Start time of all dates between two dates
     */
    public static List<Date> getBetweenDateList(Date date1, Date date2, boolean isContainParams) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return Collections.emptyList();
        }
        // 确定前后日期
        Date fromDate = date1;
        Date toDate = date2;
        if (date2.before(date1)) {
            fromDate = date2;
            toDate = date1;
        }
        // 获取两个日期每天的开始时间
        Date from = getDateStart(fromDate);
        Date to = getDateStart(toDate);
        // 获取日期，开始循环
        List<Date> dates = new ArrayList<Date>();
        if (isContainParams) {
            dates.add(from);
        }
        Date date = from;
        boolean isBefore = true;
        while (isBefore) {
            date = addDay(date, 1);
            isBefore = date.before(to);
            if (isBefore) {
                dates.add(getDateStart(date));
            }
        }
        if (isContainParams) {
            dates.add(to);
        }
        return dates;
    }

    /**
     * Get all dates between two dates
     *
     * @ param dateString1 Date 1 (for example: 2022-06-20) @ param dateString2 Date 2 (e.g. 2022-07-15) @ return All
     * dates between two dates (excluding parameter dates)
     */
    public static List<String> getBetweenDateList(String dateString1, String dateString2) {
        return getBetweenDateList(dateString1, dateString2, false);
    }

    /**
     * Get all dates between two dates
     *
     * @ param dateString1 Date 1 (for example: 2022-06-20) @ param dateString2 Date 2 (e.g. 2022-07-15) @ param
     * isContainParams Whether the parameter contains two dates @ return Start time of all dates between two dates
     */
    public static List<String> getBetweenDateList(String dateString1, String dateString2, boolean isContainParams) {
        Date date1 = parseDate(dateString1);
        Date date2 = parseDate(dateString2);
        List<Date> dates = getBetweenDateList(date1, date2, isContainParams);
        return getDateStrList(dates);
    }

    /**
     * List<Date>to List<String>
     *
     * @ param dates date set @ return Date string collection
     */
    public static List<String> getDateStrList(List<Date> dates) {
        if (dates.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> dateList = new ArrayList<String>();
        for (Date date : dates) {
            dateList.add(formatDate(date));
        }
        return dateList;
    }

    static class DateNode {
        /** 年 */
        private int year;
        /** 月 */
        private int month;
        /** 日 */
        private int day;
        /** 时 */
        private int hour;
        /** 分 */
        private int minute;
        /** 秒 */
        private int second;
        /** 毫秒 */
        private int millisecond;
        /** 星期几（ 1 - 7 对应周一到周日） */
        private int week;
        /** 当年第几天 */
        private int dayOfYear;
        /** 当年第几周（本年周 1 为第 1 周，0 则表示属于去年最后一周） */
        private int weekOfYear;
        /** 当年第几周（本年周 1 为第 1 周，0 则表示属于去年最后一周） */
        private int weekOfYearIgnoreLastYear;
        /** 时间戳（秒级） */
        private long secondStamp;
        /** 时间戳（毫秒级） */
        private long millisecondStamp;
        /** 显示时间 */
        private String time;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public int getMillisecond() {
            return millisecond;
        }

        public void setMillisecond(int millisecond) {
            this.millisecond = millisecond;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public int getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(int dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public int getWeekOfYear() {
            return weekOfYear;
        }

        public void setWeekOfYear(int weekOfYear) {
            this.weekOfYear = weekOfYear;
        }

        public int getWeekOfYearIgnoreLastYear() {
            return weekOfYearIgnoreLastYear;
        }

        public void setWeekOfYearIgnoreLastYear(int weekOfYearIgnoreLastYear) {
            this.weekOfYearIgnoreLastYear = weekOfYearIgnoreLastYear;
        }

        public long getSecondStamp() {
            return secondStamp;
        }

        public void setSecondStamp(long secondStamp) {
            this.secondStamp = secondStamp;
        }

        public long getMillisecondStamp() {
            return millisecondStamp;
        }

        public void setMillisecondStamp(long millisecondStamp) {
            this.millisecondStamp = millisecondStamp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

}
