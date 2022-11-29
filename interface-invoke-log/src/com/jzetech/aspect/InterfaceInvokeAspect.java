package com.jzetech.aspect;

import com.jzetech.entity.InterfaceInvokeLog;
import com.jzetech.util.ConcurrentCountMap;
import com.jzetech.util.FixSizeLinkedList;
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
@Aspect
@Component
public class InterfaceInvokeAspect {
    /**
     * 公用的线程管理 THREAD_MX_BEAN 对象
     */
    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

    /**
     * 创建接口调用日志对象
     *
     * @param clazzName             调用的类名
     * @param methodName            调用的方法名
     * @param interfaceInvokeLogKey 对象在 ConcurrentHashMap 中的键名
     * @return InterfaceInvokeLog
     */
    private InterfaceInvokeLog getInterfaceInvokeLog(String clazzName, String methodName, String interfaceInvokeLogKey) {
        InterfaceInvokeLog interfaceInvokeLog;
        if (ConcurrentCountMap.take().containsKey(interfaceInvokeLogKey)) {
            interfaceInvokeLog = ConcurrentCountMap.take().get(interfaceInvokeLogKey);
        } else {
            // 执行日志列表
            FixSizeLinkedList<String> execLogList = new FixSizeLinkedList<>(256);

            // 执行结果列表
            FixSizeLinkedList<Object> execResultList = new FixSizeLinkedList<>(256);

            // 执行状态列表
            FixSizeLinkedList<Boolean> execStatusList = new FixSizeLinkedList<>(256);

            // 执行时间列表
            FixSizeLinkedList<Long> execDurationList = new FixSizeLinkedList<>(256);

            // 执行消耗 CPU 时间列表
            FixSizeLinkedList<Long> execCpuTimeList = new FixSizeLinkedList<>(256);

            interfaceInvokeLog = new InterfaceInvokeLog(clazzName, methodName, execLogList, execResultList, execStatusList, execDurationList, execCpuTimeList);
            ConcurrentCountMap.take().put(interfaceInvokeLogKey, interfaceInvokeLog);
        }
        return interfaceInvokeLog;
    }

    /**
     * 切点
     * 修正 InterfaceInvokeRecord 注解的全局唯一限定符
     */
    @Pointcut("@annotation(com.jzetech.aspect.InterfaceInvokeRecord)")
    public void pointcut() {
        System.out.println("++++++++++++++++++++++++++++++++++++++");
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

        interfaceInvokeLog = getInterfaceInvokeLog(clazzName, methodName, interfaceInvokeLogKey);

        try {
            logger.debug("====== 调用方法 {}.{} 的前置通知 ======", clazzName, methodName);
            logger.debug("====== 传入方法 {} 的实参为：{} ======", methodName, JacksonUtil.argsToString(args));

            // 执行目标方法之前的时间戳
            long startTimestamp = System.nanoTime();
            long startCpuTime = THREAD_MX_BEAN.getCurrentThreadCpuTime();

            //执行连接点的方法 获取返回值
            result = proceedingJoinPoint.proceed(args);

            // 方法执行完成消耗的时间
            long execCpuTime = THREAD_MX_BEAN.getCurrentThreadCpuTime() - startCpuTime;
            long execDuration = System.nanoTime() - startTimestamp;

            // 方法被执行的累计次数
            long execTimes = interfaceInvokeLog.getExecTimes() + 1;
            interfaceInvokeLog.setExecTimes(execTimes);

            // 方法被执行的累计时间
            long execDurationSum = interfaceInvokeLog.getExecDurationSum() + execDuration;
            interfaceInvokeLog.setExecDurationSum(execDurationSum);

            // 方法被执行的累计 CPU 时间
            long execCpuTimeSum = interfaceInvokeLog.getExecCpuTimeSum() + execCpuTime;
            interfaceInvokeLog.setExecCpuTimeSum(execCpuTimeSum);

            // 方法执行日志
            String execLog = "调用方法 " + clazzName + "." + methodName + " 完成！";
            interfaceInvokeLog.getExecLogList().add(execLog);

            // 方法执行结果
            interfaceInvokeLog.getExecResultList().add(result);

            // 方法执行状态
            interfaceInvokeLog.getExecStatusList().add(true);

            // 方法执行时间
            interfaceInvokeLog.getExecDurationList().add(execDuration);

            // 方法执行消耗 CPU 时间
            interfaceInvokeLog.getExecCpuTimeList().add(execCpuTime);

            logger.debug("====== 调用方法 {}.{} 返回的结果为：{} ======", clazzName, methodName, JacksonUtil.toJsonString(result));
            logger.debug("====== 本次执行时间为：{} 毫秒 ======", execDuration / 1000);
            logger.debug("====== 本次执行消耗 CPU 时间为：{} 毫秒 ======", execCpuTime / 1000);
            logger.debug("====== 累计执行时间为：{} 毫秒 ======", execDurationSum / 1000);
            logger.debug("====== 累计执行消耗 CPU 时间为：{} 毫秒 ======", execCpuTimeSum / 1000);

        } catch (Throwable e) {
            logger.warn("====== 调用方法 {}.{} 时抛出异常！======", clazzName, methodName);
            logger.error("====== 异常消息为：{} ======", e.getLocalizedMessage());

            // 方法执行日志
            String execLog = "调用方法 " + clazzName + "." + methodName + " 时抛出异常，异常消息为：" + e.getLocalizedMessage();
            interfaceInvokeLog.getExecLogList().add(execLog);

            // 方法执行结果
            interfaceInvokeLog.getExecResultList().add(e);

            // 方法执行状态
            interfaceInvokeLog.getExecStatusList().add(false);

            throw e;
        } finally {
            System.out.println("最终通知===");
        }

        return result;

    }

}
