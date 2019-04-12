package com.jw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmpty(String... values) {
        for(String value : values) {
            if(value == null || value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }

        return str.trim();
    }

    public static boolean isNotNullOrEmpty(String text) {
        return null != text && !text.isEmpty();
    }

    public static boolean equals(String str1, String str2) {
        return !(null == str1 || null == str2) && str1.equals(str2);
    }


    public static boolean containDigitOnly(String content) {
        String regex = "^[\\d]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
