package io.github.davidchild.bitter.tools;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public enum DateTimeUtils {

    /** 日期格式 <code>[yyyy-MM-dd]</code> */
    DATE("yyyy-MM-dd"),

    /** 日期格式 <code>[yyyyMMdd]</code> */
    DATE_COMPACT("yyyyMMdd"),

    /** 日期格式 <code>[yyyy_MM_dd]</code> */
    DATE_UNDERLINE("yyyy_MM_dd"),

    /** 时间格式 <code>[HH:mm:ss]</code> */
    TIME("HH:mm:ss"),

    /** 时间格式 <code>[HHmmss]</code> */
    TIME_COMPACT("HHmmss"),

    /** 时间格式 <code>[HH_mm_ss]</code> */
    TIME_UNDERLINE("HH_mm_ss"),

    /** 时间格式 <code>[HH:mm:ss.SSS]</code> */
    TIME_MILLI("HH:mm:ss.SSS"),

    /** 时间格式 <code>[HHmmssSSS]</code> */
    TIME_MILLI_COMPACT("HHmmssSSS"),

    /** 时间格式 <code>[HH_mm_ss_SSS]</code> */
    TIME_MILLI_UNDERLINE("HH_mm_ss_SSS"),

    /** 日期时间格式 <code>[yyyy-MM-dd HH:mm:ss]</code> */
    DATE_TIME("yyyy-MM-dd HH:mm:ss"),

    /** 日期时间格式 <code>[yyyyMMddHHmmss]</code> */
    DATE_TIME_COMPACT("yyyyMMddHHmmss"),

    /** 日期时间格式 <code>[yyyy_MM_dd_HH_mm_ss]</code> */
    DATE_TIME_UNDERLINE("yyyy_MM_dd_HH_mm_ss"),

    /** 日期时间格式 <code>[yyyy-MM-dd HH:mm:ss.SSS]</code> */
    DATE_TIME_MILLI("yyyy-MM-dd HH:mm:ss.SSS"),

    /** 日期时间格式 <code>[yyyyMMddHHmmssSSS]</code> */
    DATE_TIME_MILLI_COMPACT("yyyyMMddHHmmssSSS"),

    /** 日期时间格式 <code>[yyyy_MM_dd_HH_mm_ss_SSS]</code> */
    DATE_TIME_MILLI_UNDERLINE("yyyy_MM_dd_HH_mm_ss_SSS");

    // --------------------------------------------------------------------------------------------

    private final DateTimeFormatter formatter;

    private DateTimeUtils(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    /**
     * Formats a date-time object using this formatter.
     *
     * @param temporal the temporal object to format, not null
     * @return the formatted string, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public String format(TemporalAccessor temporal) {
        return formatter.format(temporal);
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Formats a date-time object using this formatter.
     *
     * @param date the date object to format, not null
     * @return the formatted string, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public String formatDate(Date date) {
        return formatter.format(date.toInstant());
    }

    /**
     * Formats a date-time object using this formatter.
     *
     * @param epochSecond the epoch seconds to format, not null
     * @return the formatted string, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public String formatEpochSecond(long epochSecond) {
        return formatter.format(Instant.ofEpochSecond(epochSecond));
    }

    /**
     * Formats a date-time object using this formatter.
     *
     * @param epochMilli the epoch milliseconds to format, not null
     * @return the formatted string, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public String formatEpochMilli(long epochMilli) {
        return formatter.format(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * Formats a date-time object using this formatter.
     *
     * @return the formatted string, not null
     * @throws DateTimeException if an error occurs during formatting
     */
    public String now() {
        return formatter.format(Instant.now());
    }

    /**
     * Fully parses the text producing an object of the specified type.
     *
     * @param date the text to parse, not null
     * @param query the query defining the type to parse to, not null
     * @return the parsed date-time, not null
     * @throws DateTimeParseException if unable to parse the requested result
     */
    public <R> R parse(String date, TemporalQuery<R> query) {
        return formatter.parse(date, query);
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Fully parses the text producing an object of the specified type.
     *
     * @param date the text to parse, not null
     * @param query the query defining the type to parse to, not null
     * @return the parsed date-time, not null
     * @throws DateTimeException if unable to parse the requested result
     */
    public <R> Instant parseInstant(String date, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof Instant) {
            return (Instant)temporal;
        }
        if (temporal instanceof LocalDateTime) {
            return ((LocalDateTime)temporal).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (temporal instanceof LocalDate) {
            return ((LocalDate)temporal).atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        if (temporal instanceof LocalTime) {
            LocalDate epoch = LocalDate.of(1970, 1, 1); // or LocalDate.now() ???
            LocalDateTime datetime = LocalDateTime.of(epoch, (LocalTime)temporal);
            return datetime.atZone(ZoneId.systemDefault()).toInstant();
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate or LocalTime or Instant ");
    }

    /**
     * Fully parses the text producing an object of the specified type.
     *
     * @param date the text to parse, not null
     * @param query the query defining the type to parse to, not null
     * @return the parsed date-time, not null
     * @throws DateTimeException if unable to parse the requested result
     */
    public <R> Date parseDate(String date, TemporalQuery<R> query) {
        return Date.from(parseInstant(date, query));
    }

    /**
     * Fully parses the text producing an object of the specified type.
     *
     * @param date the text to parse, not null
     * @param query the query defining the type to parse to, not null
     * @return the parsed time-stamp
     * @throws DateTimeException if unable to parse the requested result
     */
    public <R> long parseEpochSecond(String date, TemporalQuery<R> query) {
        return parseInstant(date, query).getEpochSecond();
    }

    /**
     * Fully parses the text producing an object of the specified type.
     *
     * @param date the text to parse, not null
     * @param query the query defining the type to parse to, not null
     * @return the parsed time-stamp
     * @throws DateTimeException if unable to parse the requested result
     */
    public <R> long parseEpochMilli(String date, TemporalQuery<R> query) {
        return parseInstant(date, query).toEpochMilli();
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of years added.
     *
     * @param date date of the String type
     * @param years the years to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusYears(String date, long years, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusYears(years));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).plusYears(years));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of months added.
     *
     * @param date date of the String type
     * @param months the months to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusMonths(String date, long months, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusMonths(months));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).plusMonths(months));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of weeks added.
     *
     * @param date date of the String type
     * @param weeks the weeks to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusWeeks(String date, long weeks, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusWeeks(weeks));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).plusWeeks(weeks));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of days added.
     *
     * @param date date of the String type
     * @param days the days to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusDays(String date, long days, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusDays(days));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).plusDays(days));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of hours added.
     *
     * @param date date of the String type
     * @param hours the hours to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusHours(String date, long hours, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusHours(hours));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).plusHours(hours));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of minutes added.
     *
     * @param date date of the String type
     * @param minutes the minutes to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusMinutes(String date, long minutes, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusMinutes(minutes));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).plusMinutes(minutes));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of seconds added.
     *
     * @param date date of the String type
     * @param seconds the seconds to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusSeconds(String date, long seconds, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusSeconds(seconds));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).plusSeconds(seconds));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of milliseconds added.
     *
     * @param date date of the String type
     * @param milliseconds the milliseconds to add, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String plusMilliseconds(String date, long milliseconds, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).plusNanos(milliseconds * 1000000L));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).plusNanos(milliseconds * 1000000L));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of years subtracted.
     *
     * @param date date of the String type
     * @param years the years to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusYears(String date, long years, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusYears(years));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).minusYears(years));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of months subtracted.
     *
     * @param date date of the String type
     * @param months the months to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusMonths(String date, long months, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusMonths(months));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).minusMonths(months));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of weeks subtracted.
     *
     * @param date date of the String type
     * @param weeks the weeks to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusWeeks(String date, long weeks, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusWeeks(weeks));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).minusWeeks(weeks));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of days subtracted.
     *
     * @param date date of the String type
     * @param days the days to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusDays(String date, long days, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusDays(days));
        }
        if (temporal instanceof LocalDate) {
            return formatter.format(((LocalDate)temporal).minusDays(days));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalDate ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of hours subtracted.
     *
     * @param date date of the String type
     * @param hours the hours to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusHours(String date, long hours, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusHours(hours));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).minusHours(hours));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of minutes subtracted.
     *
     * @param date date of the String type
     * @param minutes the minutes to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusMinutes(String date, long minutes, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusMinutes(minutes));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).minusMinutes(minutes));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of seconds subtracted.
     *
     * @param date date of the String type
     * @param seconds the seconds to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusSeconds(String date, long seconds, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusSeconds(seconds));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).minusSeconds(seconds));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Returns a copy of this <tt>date</tt> with the specified number of milliseconds subtracted.
     *
     * @param date date of the String type
     * @param milliseconds the milliseconds to subtract, may be negative
     * @param query the query defining the type to parse to, not null
     * @return based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public <R> String minusMilliseconds(String date, long milliseconds, TemporalQuery<R> query) {
        R temporal = parse(date, query);
        if (temporal instanceof LocalDateTime) {
            return formatter.format(((LocalDateTime)temporal).minusNanos(milliseconds * 1000000L));
        }
        if (temporal instanceof LocalTime) {
            return formatter.format(((LocalTime)temporal).minusNanos(milliseconds * 1000000L));
        }
        throw new DateTimeException(" Type parameter must be LocalDateTime or LocalTime ");
    }

    /**
     * Change the format of the date display
     *
     * @param date date of the String type
     * @param pattern the format of the date display
     * @param query the query defining the type to parse to, not null
     * @return
     * @throws DateTimeException if unable to parse the requested result
     */
    public String transform(String date, String pattern, TemporalQuery<?> query) {
        synchronized (CacheHolder.CACHE) {
            if (CacheHolder.CACHE.containsKey(pattern)) {
                return CacheHolder.CACHE.get(pattern).format((TemporalAccessor)formatter.parse(date, query));
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
            String result = dtf.format((TemporalAccessor)formatter.parse(date, query));
            if (pattern.length() == result.length()) {
                CacheHolder.CACHE.putIfAbsent(pattern, dtf);
            }
            return result;
        }
    }

    // --------------------------------------------------------------------------------------------

    private static class CacheHolder {
        private static final int CAPACITY = 8;
        private static final Map<String, DateTimeFormatter> CACHE =
            new LinkedHashMap<String, DateTimeFormatter>(CAPACITY, 1F, true) {
                private static final long serialVersionUID = -2972235912618490882L;

                @Override
                protected boolean removeEldestEntry(Map.Entry<String, DateTimeFormatter> eldest) {
                    return size() >= (CAPACITY - 1);
                }
            };
    }

}
