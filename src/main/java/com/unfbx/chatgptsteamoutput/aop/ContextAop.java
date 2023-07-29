package com.unfbx.chatgptsteamoutput.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * @author Jack
 * @Desc
 * @Date 2023/7/28/17:07:54
 **/
@Slf4j
//@Aspect
//@Component
public class ContextAop {

    @Around("execution(* com.unfbx.chatgptsteamoutput.websocket.*.*(..))")
    public Object cut(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().getName();
        log.info(name);
        return pjp.proceed(pjp.getArgs());
    }
}
