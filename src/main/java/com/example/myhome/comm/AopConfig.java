package com.example.myhome.comm;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component

public class AopConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    String sessGuid = "E6F9B4F2A2465CE76D991BF5807AD9E0";

    @Around("execution(* com.example.myhome..controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = joinPoint.getSignature().toShortString();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getParameterMap();
        logger.info("SESS_GUID = {}, ===================START===================", sessGuid);
        logger.info("SESS_GUID = {}, @Around : {} ", sessGuid, type);
        logger.info("SESS_GUID = {}, @Around : {}, param : {} ", sessGuid, type, mapToStr(request.getParameterMap()));
        return joinPoint.proceed();
    }

    @Before("execution(* com.example.myhome..controller.*.*(..)) || execution(* com.example.myhome..service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        String type = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (args.length == 0 || args[0] instanceof HttpServletRequest){
            logger.info("SESS_GUID = {}, @Before : {}, param : {}", sessGuid, type, mapToStr(request.getParameterMap()));
        }else{
            logger.info("SESS_GUID = {}, @Before : {}, param : {}", sessGuid, type, setParamParse(args[0].toString(), "="));
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.myhome..controller.*.*(..)) || execution(* com.example.myhome..service.*.*(..))", returning="retValue")
    public void after(JoinPoint joinPoint, Object retValue) {
        String type = joinPoint.getSignature().toShortString();
        logger.info("SESS_GUID = {}, @After : {}, result : {}", sessGuid, type, retValue);
        logger.info("SESS_GUID = {}, ===================E N D===================", sessGuid);
    }

    @AfterThrowing(pointcut = "execution(* com.example.myhome..controller.*.*(..)) || execution(* com.example.myhome..service.*.*(..))", throwing = "ex")
    public void afterThrowingAnException(JoinPoint joinPoint, Exception ex) {
        logger.info("SESS_GUID = {}, ===================E N D===================", sessGuid);
    }

    private boolean isNull(String str) {
        return (str == null && str == "");
    }

    public String mapToStr(Map<String, String[]> map){
        return (map == null || map.keySet().size() == 0)? "{null}":appendMapToStr(map);
    }

    private String appendMapToStr(Map<String, String[]> map){
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (String key : map.keySet()) sb.append(strType(map.get(key), key)).append(", ");
        sb.append("}");
        return sb.toString().replace(", }", "}");
    }

    private String strType (Object val, String key) {
        boolean chkKey = false;
        if (val == null) return key + "null";
        if (val instanceof String) return keyVal(key, val, chkKey);
        if (val instanceof String[]){
            String[] arr = (String[]) val;
            if(arr.length == 1) return keyVal(key, arr[0], chkKey);
            StringBuilder sb = new StringBuilder();
            Arrays.stream(arr).forEach(a -> sb.append(keyVal(key, a, chkKey)));
        }
        return keyVal(key, val, chkKey);
    }

    private String keyVal(String key, Object obj, boolean chkKey) {
        return (chkKey)? key+":"+obj.toString().replaceAll(".*", "****") : key+":"+obj;
    }

    public String setParamParse(String str, String gubun) {
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

    private String keyVal2(String key, Object obj, boolean chkKey, String gubun) {
        return (chkKey)? key+gubun+obj.toString().replaceAll(".*", "****") : key+gubun+obj;
    }

    private String loopVal(String key, Object val, boolean chkKey, String gubun){
        if (val == null) return key + "null";
        if (val instanceof String) return keyVal2(key, val, chkKey, gubun);
        if (val instanceof String[]) {
            String[] arr = (String[]) val;
            if (arr.length == 1)  return keyVal2(key, arr[0], chkKey, gubun);
            StringBuilder sb = new StringBuilder();
            Arrays.stream(arr).forEach(a -> sb.append(keyVal2(key, a, chkKey, gubun)));
            return sb.toString();
        }
        return null;
    }
}
