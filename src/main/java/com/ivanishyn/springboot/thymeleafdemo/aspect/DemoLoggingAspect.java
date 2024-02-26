package com.ivanishyn.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.ivanishyn.springboot.thymeleafdemo.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.ivanishyn.springboot.thymeleafdemo.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("execution(* com.ivanishyn.springboot.thymeleafdemo.dao.*.*(..))")
    private void forDapPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDapPackage()")
    private void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        String theMethod = joinPoint.getSignature().toShortString();
        logger.info("====> in @Before: calling method: " + theMethod);

        Object[] args = joinPoint.getArgs();

        for (Object object : args) {
            logger.info("====> Argument: " + object);
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String theMethod = joinPoint.getSignature().toShortString();
        logger.info("====> in @AfterReturning: calling method: " + theMethod);

        logger.info("====> Result: " + result);
    }
}
