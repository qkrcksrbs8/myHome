package com.example.myhome.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogUtil {

    public static boolean isNull(String str) {
        return (str == null && str == "");
    }

    public static String mapToStr(Map<String, String[]> map) {
        return (map == null || map.keySet().size() == 0) ? "{null}" : appendMapToStr(map);
    }

    public static String appendMapToStr(Map<String, String[]> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (String key : map.keySet()) sb.append(strType(map.get(key), key)).append(", ");
        sb.append("}");
        return sb.toString().replace(", }", "}");
    }

    public static String strType(Object val, String key) {
        boolean chkKey = false;
        if (val == null) return key + "null";
        if (val instanceof String) return keyVal(key, val, chkKey);
        if (val instanceof String[]) {
            String[] arr = (String[]) val;
            if (arr.length == 1) return keyVal(key, arr[0], chkKey);
            StringBuilder sb = new StringBuilder();
            Arrays.stream(arr).forEach(a -> sb.append(keyVal(key, a, chkKey)));
        }
        return keyVal(key, val, chkKey);
    }

    public static String keyVal(String key, Object obj, boolean chkKey) {
        return (chkKey) ? key + ":" + obj.toString().replaceAll(".*", "****") : key + ":" + obj;
    }

    public static String setParamParse(String str, String gubun) {
        Map<String, String> map = new HashMap<String, String>();
        if (isNull(str)) return null;
        if (str.contains("{")) return str;
        str = str
                .replace("{", "")
                .replace("}", "");
        String[] array = str.split(",");

        for (int i = 0; i < array.length; i++) {
            String[] param = array[i].split(gubun);
            if (param.length > 1) {
                map.put(param[0], param[1]);
                continue;
            }
            map.put(param[0], "");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String key : map.keySet()) {
            Object val = map.get(key);
            key = key.trim();
            boolean chkKey = false;
            sb.append(loopVal(key, val, chkKey, gubun));
            sb.append(keyVal2(key, val, chkKey, gubun));
            sb.append(", ");
        }
        sb.append("}");
        return sb.toString().replace(", }", "}");
    }

    public static String keyVal2(String key, Object obj, boolean chkKey, String gubun) {
        return (chkKey) ? key + gubun + obj.toString().replaceAll(".*", "****") : key + gubun + obj;
    }

    public static String loopVal(String key, Object val, boolean chkKey, String gubun) {
        if (val == null) return key + "null";
        if (val instanceof String) return keyVal2(key, val, chkKey, gubun);
        if (val instanceof String[]) {
            String[] arr = (String[]) val;
            if (arr.length == 1) return keyVal2(key, arr[0], chkKey, gubun);
            StringBuilder sb = new StringBuilder();
            Arrays.stream(arr).forEach(a -> sb.append(keyVal2(key, a, chkKey, gubun)));
            return sb.toString();
        }
        return null;
    }

}
