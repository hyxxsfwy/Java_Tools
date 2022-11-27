package com.jzetech.util;

import java.util.LinkedList;

/**
 * 固定长度List
 * 如果List里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
 *
 * @author 0703
 */
public class FixSizeLinkedList<T> extends LinkedList<T> {

    private static final long serialVersionUID = 3292612616327532364L;

    /**
     * 定义列表缓存的容量
     */
    private int capacity = 8;

    public FixSizeLinkedList(int capacity) {
        super();
        this.capacity = capacity;
    }

    @Override
    public boolean add(T e) {
        // 超过缓存长度时，移除最先加入的元素
        if (size() + 1 > capacity) {
            super.removeFirst();
        }
        return super.add(e);
    }

}
