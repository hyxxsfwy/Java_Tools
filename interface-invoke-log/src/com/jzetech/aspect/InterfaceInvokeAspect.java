package com.jzetech.aspect;

import com.jzetech.entity.InterfaceInvokeLog;
import com.jzetech.util.ConcurrentCountMap;
import com.jzetech.util.JacksonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @author WenChao
 * @Description 接口调用切面定义
 */
@Component
@Aspect
public class InterfaceInvokeAspect {
    /**
     * 公用的线程管理 THREAD_MX_BEAN 对象
     */
    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

    /**
     * 切点
     * 修正 InterfaceInvokeRecord 注解的全局唯一限定符
     */
    @Pointcut("@annotation(com.jzetech.aspect.InterfaceInvokeRecord)")
    public void pointcut() {
    }

    /**
     * 定义环绕通知，实现统计目标类带参方法运行时长、执行次数等
     *
     * @param proceedingJoinPoint
     * @return 目标方法的执行结果
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // 获取目标Logger
        Logger logger = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());

        // 获取目标类名称
        String clazzName = proceedingJoinPoint.getTarget().getClass().getName();

        // 获取目标类方法名称
        String methodName = proceedingJoinPoint.getSignature().getName();

        // 获取连接点方法的实参
        Object[] args = proceedingJoinPoint.getArgs();

        Object result;
        InterfaceInvokeLog interfaceInvokeLog;
        String interfaceInvokeLogKey = clazzName + "-" + methodName;
        try {
            logger.debug("====== 调用方法 {}.{} 的前置通知 ======", clazzName, methodName);
            logger.debug("====== 传入方法 {} 的实参为：{} ======", methodName, JacksonUtil.argsToString(args));

            if (ConcurrentCountMap.take().containsKey(interfaceInvokeLogKey)) {
                interfaceInvokeLog = ConcurrentCountMap.take().get(interfaceInvokeLogKey);
            } else {
                interfaceInvokeLog = new InterfaceInvokeLog();
                ConcurrentCountMap.take().put(interfaceInvokeLogKey, interfaceInvokeLog);
            }

            // 执行目标方法之前的时间戳
            long startTimestamp = System.nanoTime();
            long startCpuTime = THREAD_MX_BEAN.getCurrentThreadCpuTime();

            //执行连接点的方法 获取返回值
            result = proceedingJoinPoint.proceed(args);

            // 方法执行完成消耗的时间
            long execDuration = System.nanoTime() - startTimestamp;
            long execCpuTime = THREAD_MX_BEAN.getCurrentThreadCpuTime() - startCpuTime;

            logger.debug("====== 调用方法 {}.{} 返回的结果为 {} ======", clazzName, methodName, JacksonUtil.toJsonString(result));

            long currentExecTimes = interfaceInvokeLog.getExecTimes() + 1;
            interfaceInvokeLog.setExecTimes(currentExecTimes);

        } catch (Throwable e) {
            System.out.println("异常通知===");
            throw e;
        } finally {
            System.out.println("最终通知===");
        }

        return result;

    }


}
