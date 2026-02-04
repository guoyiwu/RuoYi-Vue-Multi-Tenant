package com.ruoyi.sast.sonar;

/**
 * 【规则5】Bug - 阻断
 * Double-checked locking should not be used
 * 不应该使用双重检查锁定
 *
 * 问题：在Java中，双重检查锁定模式在没有volatile的情况下是不安全的，
 *       可能导致获取到部分初始化的对象
 */
public class DoubleCheckedLocking {

    // 🚨 违规：没有使用volatile修饰
    private static DoubleCheckedLocking instance;

    private DoubleCheckedLocking() {
        // 私有构造函数
    }

    // 🚨 违规：典型的错误双重检查锁定实现
    public static DoubleCheckedLocking getInstance() {
        if (instance == null) {                     // 第一次检查（无锁）
            synchronized (DoubleCheckedLocking.class) {
                if (instance == null) {             // 第二次检查（有锁）
                    instance = new DoubleCheckedLocking();
                    // 🚨 问题：由于指令重排序，另一个线程可能看到
                    // 一个非null但未完全初始化的instance
                }
            }
        }
        return instance;
    }

    // 🚨 违规：另一个错误的双重检查锁定示例
    private static Object resource;
    private static boolean initialized = false;

    public static Object getResource() {
        if (!initialized) {
            synchronized (DoubleCheckedLocking.class) {
                if (!initialized) {
                    resource = new Object();
                    initialized = true;
                    // 🚨 问题：initialized可能在resource完全初始化前被设为true
                }
            }
        }
        return resource;
    }
}
