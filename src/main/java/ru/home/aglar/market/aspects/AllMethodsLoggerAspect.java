package ru.home.aglar.market.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AllMethodsLoggerAspect {

    @AfterReturning("execution (* ru.home.aglar.market.controller.*.*(..))")
    public void afterRunningMethodsAspect(JoinPoint joinPoint) {
        Class<?> pointClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.debug("Был выполнен метод {}#{} с аргументами {}", pointClass, methodName, args);
    }
}