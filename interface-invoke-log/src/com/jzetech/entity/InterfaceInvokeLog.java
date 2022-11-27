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
    private String clazzName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 执行次数
     */
    private long execTimes;

    /**
     * 总计执行时间
     */
    private long execDurationSum;

    /**
     * 执行日志列表
     */
    private FixSizeLinkedList<String> execLogList;

    /**
     * 执行状态列表
     */
    private FixSizeLinkedList<Boolean> execResultList;

    /**
     * 执行时间列表（单位纳秒）
     */
    private FixSizeLinkedList<Long> execDurationList;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public FixSizeLinkedList<String> getExecLogList() {
        return execLogList;
    }

    public void setExecLogList(FixSizeLinkedList<String> execLogList) {
        this.execLogList = execLogList;
    }

    public FixSizeLinkedList<Boolean> getExecResultList() {
        return execResultList;
    }

    public void setExecResultList(FixSizeLinkedList<Boolean> execResultList) {
        this.execResultList = execResultList;
    }

    public FixSizeLinkedList<Long> getExecDurationList() {
        return execDurationList;
    }

    public void setExecDurationList(FixSizeLinkedList<Long> execDurationList) {
        this.execDurationList = execDurationList;
    }

    @Override
    public String toString() {
        return "InterfaceInvokeLog{" +
                "clazzName='" + clazzName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", execTimes=" + execTimes +
                ", execDurationSum=" + execDurationSum +
                ", execLogList=" + execLogList +
                ", execResultList=" + execResultList +
                ", execDurationList=" + execDurationList +
                '}';
    }
    
}
