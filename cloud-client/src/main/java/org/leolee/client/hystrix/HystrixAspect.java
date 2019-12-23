package org.leolee.client.hystrix;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Semaphore;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月22 21时25分
 */
@Component
@Aspect
public class HystrixAspect {

    public final String POINT_CUT = "execution(public * org.leolee.client.controller.*.hystrix(..))";

    private Semaphore semaphore = null;

    public final String SEAMPHORE_POINT_CUT = "execution(public * org.leolee.client.controller.*.*(..))" +
            "&& @annotation(org.leolee.client.hystrix.CircuitBreakerSeamphore)";

    @Pointcut(POINT_CUT)
    public void pointCut(){}


    @Around(value = POINT_CUT)
    public Object advanced(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("拦截......");
        joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return joinPoint.proceed(joinPoint.getArgs());


    }


    @Around(value = SEAMPHORE_POINT_CUT)
    public Object advancedHystrixSeamphore(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("拦截......");
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CircuitBreakerSeamphore annotation = method.getAnnotation(CircuitBreakerSeamphore.class);
        if(semaphore == null){
            semaphore = new Semaphore(annotation.value());
        }
        System.out.println("信号量:" + semaphore);
        Object returnValue = null;
        try{
            semaphore.tryAcquire(1);
            joinPoint.getArgs();
            returnValue = joinPoint.proceed(joinPoint.getArgs());
        }finally {
            semaphore.release();
        }

        return returnValue;


    }





    @Before(value = POINT_CUT)
    public void doBeforeAdvice(){

    }


    @After(value = POINT_CUT)
    public void doAfterAdvice(){

    }

    @AfterReturning(value = POINT_CUT)
    public void doAfterReturningAdvice(){

    }


    @AfterThrowing(value = POINT_CUT)
    public void doAfterThrowingAdvice(){

    }
}
