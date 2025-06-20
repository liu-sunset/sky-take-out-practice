package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Slf4j
@Aspect
public class AutoFillAspect {
    /*
     * 切入点表达式
     * */
    @Pointcut("execution(com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段的自动填充");
//        获得方法上的注解，确认方法类型
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();//获得方法的操作类型

        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            return ;
        }

        Object entity = args[0];//默认需要的形参放在方法形参的首位

        LocalDateTime now = LocalDateTime.now();
        long id = BaseContext.getCurrentId();


    }
}
