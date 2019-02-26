package com.feng.Lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangxl6 on 2018/8/24.
 */
public enum LockEnum {
    LOCK;
    private ReentrantLock lock = null;
    private LockEnum() {
        lock = new ReentrantLock();
    }
    public ReentrantLock getLock() {
        return lock;
    }
}
