package auxiliary.log;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import sys.GlobalConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Component
public class AspectLog {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * auxiliary.Test.aaa(..))")
    public void AspectLog(){}

    /**
     * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）
     * @param joinPoint
     * @throws Throwable
     */
/*    @Before("AspectLog()")
    public void before(JoinPoint joinPoint) throws Throwable{
        //logger.info("前置");
    }*/

    /**
     * 最终通知。当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
     * @param joinPoint
     * @throws Throwable
     */
/*    @After("AspectLog()")
    public void after(JoinPoint joinPoint) throws Throwable{
        //logger.info("后置");
    }*/

    /**
     * 后置通知：在某连接点正常完成后执行的通知，通常在一个匹配的方法返回的时候执行
     * @param ret
     */
/*    @AfterReturning(returning = "ret",pointcut = "AspectLog()")
    public void afterReturning(Object ret){
        //logger.info("正常执行-返回值：" + ret);
    }*/

    /**
     * 异常通知：在方法抛出异常退出时执行的通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "AspectLog()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {

        String methodName = joinPoint.getSignature().getName();     //得到方法名称

        String logInfo = "";
        logInfo += "\n[" + GlobalConstants.PROJECT_NAME + "] - [" + joinPoint.getTarget().getClass().getName() + "." + methodName + "]\n";
        logInfo += "[入参]      - " + Arrays.toString(joinPoint.getArgs()) + "\n";
        logInfo += "[异常信息]  - " + e.toString() + "\n";
        logger.info(logInfo);
    }

    /**
     * 环绕通知：包围一个连接点的通知，如方法调用。这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它自己的返回值或抛出异常来结束执行。
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("AspectLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{

        String methodName = joinPoint.getSignature().getName();     //得到方法名称

        String logInfo = "";
        logInfo += "\n[" + GlobalConstants.PROJECT_NAME + "] - [" + joinPoint.getTarget().getClass().getName() + "." + methodName + "]\n";
        logInfo += "[入参]      - " + Arrays.toString(joinPoint.getArgs()) + "\n";
        logInfo += "[返回值]    - " + joinPoint.proceed() + "\n";
        logInfo += "[异常信息]  - " + joinPoint.proceed() + "\n";
        logger.info(logInfo);

        return null;
    }
}
