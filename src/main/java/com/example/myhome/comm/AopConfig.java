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
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Aspect
@Component

public class AopConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.myhome..controller.*.*(..)) || execution(* com.example.myhome..service.*.*(..))")
    public void checkSomethingBefore(JoinPoint joinPoint) {
        logger.info("============================= "+joinPoint.getSignature().getDeclaringType().getName()+" START ============================");
        logger.info(joinPoint.getSignature().getName());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(joinPoint.getArgs()).forEach(str -> sb.append(str));
        logger.info("param : "+ sb.toString().replace("=",":"));
    }

    @AfterReturning(pointcut = "execution(* com.example.myhome..controller.*.*(..)) || execution(* com.example.myhome..service.*.*(..))")
    public void checkSomethingAfter(JoinPoint joinPoint) {
        logger.info("============================= "+joinPoint.getSignature().getDeclaringType().getName()+" E N D ============================");
    }

    @AfterThrowing(pointcut = "execution(* com.example.myhome..controller.*.*(..)) || execution(* com.example.myhome..service.*.*(..))", throwing = "ex")
    public void checkSomethingAfterThrowingAnException(JoinPoint joinPoint, Exception ex) {
        logger.info("============================= AfterThrowing ============================");
    }

    @Around("execution(* com.example.myhome..controller.*.*(..))")
    public Object checkSomethingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = joinPoint.getSignature().toShortString();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

//        String sessGuid = isNullToString(session.getAttribute("SESS_GUID"));
//
//        if(sessGuid.equals("")){
//            session.setAttribute("SESS_GUID", key);
//            sessGuid = key;
//        }

        request.getParameterMap();
        logger.info("SESS_GUID = {}, ===================START===================", "pcg0902");
        logger.info("SESS_GUID = {}, @Around : {}", "pcg0902", type);
//        logger.info("SESS_GUID = {}, @Around : {}, param : {}", "pcg0902", type, map2str(request.getParameterMap()));

        Object result = joinPoint.proceed();
        return result;
    }

    private Object isNullToString(Object obj) {
        return (null == obj)? "" : obj;
    }

}
