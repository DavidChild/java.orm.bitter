package io.github.davidchild.bitter.tools;

import org.springframework.util.AntPathMatcher;

import java.util.*;

public class CoreStringUtils extends org.apache.commons.lang3.StringUtils {
    /** Empty string */
    private static final String NULLSTR = "";

    /** Underline */
    private static final char SEPARATOR = '_';

    /**
     * Get parameter is not null
     *
     * @param value defaultValue alue to judge
     * @return value Return value
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * Judge whether a Collection is empty, including List, Set and Queue
     *
     * @param coll Collection to judge
     * @return true：Null false: non null
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     ** Judge whether a Collection is not empty, including List, Set and Queue
     *
     * @ param col Collection to judge @ return true: non null false: null
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     ** Judge whether an object array is empty
     *
     * @ param objects Array of objects to judge @ return true: null false: non null
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     ** Judge whether an object array is not empty
     *
     * @ param objects Array of objects to judge @ return true: non null false: null
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     ** Judge whether a Map is empty
     *
     * @ param map Map to judge @ return true: null false: non null
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     ** Judge whether a Map is empty
     *
     * @ param map Map to judge @ return true: non null false: null
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     ** Judge whether a string is empty
     *
     * @param str String @ return true: null false: non null
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim())|| "".equals(str);
    }

    /**
     **  whether a string is non empty
     *
     * @param str String @ return true: non empty string false: empty string
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * Judge whether an object is empty
     *
     * @param object Object
     * @return true：Null false: non null
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * Judge whether an object is not empty
     *
     * @param object Object
     * @return true：Non null false: null
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * Determine whether an object is an array type (array of Java primitive types)
     *
     * @param object
     * @return true：Is an array false: not an array
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * Go to space
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * Truncate string
     *
     * @param str character string
     * @param start start
     * @return result
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * Truncate string
     *
     * @param str character string
     * @param start start
     * @param end end
     * @return result
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * it starts with http (s)://
     *
     * @param link link
     * @return result
     */
    public static boolean ishttp(String link) {
        return CoreStringUtils.startsWithAny(link, "http://", "https://");
    }

    /**
     * Convert string to set
     *
     * @param str character string
     * @param sep Separator
     * @return set
     */
    public static final Set<String> str2Set(String str, String sep) {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * convert list
     *
     * @param str
     * @param sep split
     * @param filterBlank fileter empty
     * @param trim remove leading and trailing blanks
     * @return list
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<String>();
        if (CoreStringUtils.isEmpty(str)) {
            return list;
        }

        // Filter blank strings
        if (filterBlank && CoreStringUtils.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && CoreStringUtils.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * Find whether the specified string contains any string in the specified string list and ignore case
     *
     * @param cs fixed string
     * @param searchCharSequences String array to check
     * @return Whether to include any string
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences) {
        if (isEmpty(cs) || isEmpty(searchCharSequences)) {
            return false;
        }
        for (CharSequence testStr : searchCharSequences) {
            if (containsIgnoreCase(cs, testStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hump to underline naming
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // Whether the leading characters are capitalized
        boolean preCharIsUpperCase = true;
        // Whether the current character is capitalized
        boolean curreCharIsUpperCase = true;
        // Whether the next character is capitalized
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * Whether to include string
     *
     * @param str Verify String
     * @param strs String group
     * @return Include returns true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converts an underlined uppercase named string to a camel. If the string named in underscore capitalization before
     * conversion is null, an empty string is returned. Example: HELLO_ WORLD->HelloWorld
     *
     * @param name String named by underscore capitalization before conversion
     * @return Converted hump named string
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // Quick check
        if (name == null || name.isEmpty()) {
            // No conversion necessary
            return "";
        } else if (!name.contains("_")) {
            // Only capitalize the first letter without underline
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // Splits the original string with an underscore
        String[] camels = name.split("_");
        for (String camel : camels) {
            // Skip the leading or ending underscore or double underscore in the original string
            if (camel.isEmpty()) {
                continue;
            }
            //
            // title case
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * Hump style naming method, for example: user_ name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Find whether the specified string matches any string in the specified string list
     * 
     * @param str Specify string
     * @param strs String array to check
     * @return Match
     */
    public static boolean matches(String str, List<String> strs) {
        if (isEmpty(str) || isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the url is consistent with the rule configuration: ? Represents a single character; indicates
     * any string in a layer path, and cannot cross layers; * indicates any layer path; @ param pattern matching rules @
     * param url requires matching url
     * 
     * @return
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T)obj;
    }

    /**
     * Fill in 0 on the left of the number to make it reach the specified length. Note that if the number is converted
     * to a string and the length is greater than size, only the last size characters will be retained.
     *
     * @ param num numeric object @ param size string specifies the length @ return Returns the string format of a
     * number. The string is the specified length.
     */
    public static final String padl(final Number num, final int size) {
        return padl(num.toString(), size, '0');
    }

    /**
     * String left completion. If the length of the original string s is greater than size, only the last size
     * characters are retained.
     *
     * @ param s original string @ param size string specifies the length @ param c Characters used for completion @
     * return Returns a string of the specified length, which is obtained from the left complement or truncation of the
     * original string.
     */
    public static final String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                for (int i = size - len; i > 0; i--) {
                    sb.append(c);
                }
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            for (int i = size; i > 0; i--) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}