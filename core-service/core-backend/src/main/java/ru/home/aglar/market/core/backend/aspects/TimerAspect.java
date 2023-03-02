package ru.home.aglar.market.core.backend.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    @Pointcut("@annotation(Timer)")
    private void timerMethod() {}

    @Pointcut("@within(ru.home.aglar.market.core.backend.aspects.Timer)")
    private void timerClass() {}

    @Pointcut("timerMethod() || timerClass()")
    private void timerAnnotation() {}

    @Around("timerAnnotation()")
    public Object checkTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Class<?> pointClass = proceedingJoinPoint.getTarget().getClass();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            long deltaTime = System.currentTimeMillis() - startTime;
            log.info("Время выполнения метода {}#{} с аргументами {}: {} ms", pointClass, methodName, args, deltaTime);
        }
        return result;
    }
}