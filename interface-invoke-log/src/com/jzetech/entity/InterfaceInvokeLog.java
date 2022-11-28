package com.jzetech.entity;

import com.jzetech.util.FixSizeLinkedList;

/**
 * 接口（方法）调用日志类
 *
 * @author 0703
 */
public class InterfaceInvokeLog {

    /**
     * 类名
     */
    private final String clazzName;

    /**
     * 方法名
     */
    private final String methodName;
    /**
     * 执行日志列表
     */
    private final FixSizeLinkedList<String> execLogList;
    /**
     * 执行结果列表
     */
    private final FixSizeLinkedList<Object> execResultList;
    /**
     * 执行状态列表
     */
    private final FixSizeLinkedList<Boolean> execStatusList;
    /**
     * 执行时间列表（单位纳秒）
     */
    private final FixSizeLinkedList<Long> execDurationList;
    /**
     * 执行占用 CPU 时间列表（单位纳秒）
     */
    private final FixSizeLinkedList<Long> execCpuTimeList;
    /**
     * 累计执行次数
     */
    private long execTimes;
    /**
     * 累计执行时间（单位 ns)
     */
    private long execDurationSum;
    /**
     * 累计 CPU 占用时间（单位 ns）
     */
    private long execCpuTimeSum;

    public InterfaceInvokeLog(String clazzName, String methodName, FixSizeLinkedList<String> execLogList, FixSizeLinkedList<Object> execResultList, FixSizeLinkedList<Boolean> execStatusList, FixSizeLinkedList<Long> execDurationList, FixSizeLinkedList<Long> execCpuTimeList) {
        this.clazzName = clazzName;
        this.methodName = methodName;
        this.execLogList = execLogList;
        this.execResultList = execResultList;
        this.execStatusList = execStatusList;
        this.execDurationList = execDurationList;
        this.execCpuTimeList = execCpuTimeList;
    }

    public long getExecTimes() {
        return execTimes;
    }

    public void setExecTimes(long execTimes) {
        this.execTimes = execTimes;
    }

    public long getExecDurationSum() {
        return execDurationSum;
    }

    public void setExecDurationSum(long execDurationSum) {
        this.execDurationSum = execDurationSum;
    }

    public long getExecCpuTimeSum() {
        return execCpuTimeSum;
    }

    public void setExecCpuTimeSum(long execCpuTimeSum) {
        this.execCpuTimeSum = execCpuTimeSum;
    }

    public String getClazzName() {
        return clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public FixSizeLinkedList<String> getExecLogList() {
        return execLogList;
    }

    public FixSizeLinkedList<Object> getExecResultList() {
        return execResultList;
    }

    public FixSizeLinkedList<Boolean> getExecStatusList() {
        return execStatusList;
    }

    public FixSizeLinkedList<Long> getExecDurationList() {
        return execDurationList;
    }

    public FixSizeLinkedList<Long> getExecCpuTimeList() {
        return execCpuTimeList;
    }

    @Override
    public String toString() {
        return "InterfaceInvokeLog{" +
                "clazzName='" + clazzName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", execTimes=" + execTimes +
                ", execDurationSum=" + execDurationSum +
                ", execCpuTimeSum=" + execCpuTimeSum +
                ", execLogList=" + execLogList +
                ", execResultList=" + execResultList +
                ", execStatusList=" + execStatusList +
                ", execDurationList=" + execDurationList +
                ", execCpuTimeList=" + execCpuTimeList +
                '}';
    }

}
