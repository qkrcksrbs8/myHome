package com.example.myhome.comm;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        Object result = joinPoint.proceed();
        return result;
    }

}
