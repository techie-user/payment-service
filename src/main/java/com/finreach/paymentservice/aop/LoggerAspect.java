package com.finreach.paymentservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log all method calls on Controller, Services and Store
 */

@Aspect
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    private final String DELIMITER = ".";

    /**
     * Log before each method execution
     *
     * @param joinPoint for packages api/service/store
     */
    @Before("execution(* com.finreach.paymentservice.api.*.*(..)) " + "|| execution(* com.finreach.paymentservice.service.*.*(..)) " + "|| execution(* com.finreach.paymentservice.store.*.*(..))")
    public void before(JoinPoint joinPoint) {
        String logMessage = getLogMessage("Before method execution :: ", joinPoint);
        logger.info(logMessage);
    }

    @After("execution(* com.finreach.paymentservice.service.*.*(..)) " + "|| execution(* com.finreach.paymentservice.api.*.*(..)) " + "|| execution(* com.finreach.paymentservice.store.*.*(..))")
    public void after(JoinPoint joinPoint) {
        String logMessage = getLogMessage("After method execution :: ", joinPoint);
        logger.info(logMessage);
    }

    @AfterReturning(value = "execution(* com.finreach.paymentservice.service.*.*(..)) " + "|| execution(* com.finreach.paymentservice.api.*.*(..)) " + "|| execution(* com.finreach.paymentservice.store.*.*(..))", returning = "returningValue")
    public void afterReturning(JoinPoint joinPoint, Object returningValue) {
        logger.info("{} returned with value {}", joinPoint, returningValue);
    }

    private String getLogMessage(String state, JoinPoint joinPoint) {
        StringBuilder logMessage = new StringBuilder(state);
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(DELIMITER);
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logMessage.append(arg).append(DELIMITER);
        }
        if (args.length > 0) {
            logMessage.replace(logMessage.length() - 1, logMessage.length() - 1, ")");
        }
        return logMessage.toString();
    }
}
