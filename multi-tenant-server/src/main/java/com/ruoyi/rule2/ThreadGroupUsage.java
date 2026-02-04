package com.ruoyi.rule2;

/**
 * 【规则15】异味 - 阻断
 * "ThreadGroup" should not be used
 * 不应该使用ThreadGroup
 *
 * 问题：ThreadGroup是过时的API，存在设计缺陷：
 *       1. stop(), suspend(), resume()等方法已废弃且不安全
 *       2. 不提供真正的线程安全隔离
 *       3. 应使用ExecutorService替代
 */
public class ThreadGroupUsage {

    // 🚨 违规：创建和使用ThreadGroup
    public void createThreadGroup() {
        // 🚨 违规：直接创建ThreadGroup
        ThreadGroup group = new ThreadGroup("WorkerGroup");
        
        Thread t1 = new Thread(group, () -> {
            System.out.println("Thread 1 running");
        }, "Worker-1");
        
        Thread t2 = new Thread(group, () -> {
            System.out.println("Thread 2 running");
        }, "Worker-2");
        
        t1.start();
        t2.start();
    }

    // 🚨 违规：使用ThreadGroup来管理线程
    public void manageThreads() {
        ThreadGroup group = new ThreadGroup("TaskGroup");
        
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            // 🚨 违规：不应使用ThreadGroup组织线程
            new Thread(group, () -> {
                System.out.println("Task " + taskId + " executing");
            }).start();
        }
        
        // 🚨 违规：使用废弃的方法
        // group.stop();  // 已废弃，不安全
        
        // 等待所有线程完成（这种方式也不可靠）
        while (group.activeCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // 🚨 违规：使用ThreadGroup进行异常处理
    public void handleExceptions() {
        // 🚨 违规：通过ThreadGroup处理未捕获异常
        ThreadGroup group = new ThreadGroup("ExceptionGroup") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.err.println("Thread " + t.getName() + " threw: " + e);
            }
        };
        
        new Thread(group, () -> {
            throw new RuntimeException("Test exception");
        }).start();
    }

    // 🚨 违规：获取当前线程的ThreadGroup
    public void inspectThreadGroup() {
        // 🚨 违规：访问ThreadGroup信息
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        
        System.out.println("Group name: " + currentGroup.getName());
        System.out.println("Active count: " + currentGroup.activeCount());
        
        // 🚨 违规：列举组中的线程
        Thread[] threads = new Thread[currentGroup.activeCount()];
        currentGroup.enumerate(threads);
        
        for (Thread t : threads) {
            if (t != null) {
                System.out.println("Thread: " + t.getName());
            }
        }
    }

    // 🚨 违规：创建嵌套的ThreadGroup
    public void nestedGroups() {
        ThreadGroup parent = new ThreadGroup("ParentGroup");
        // 🚨 违规：创建子ThreadGroup
        ThreadGroup child = new ThreadGroup(parent, "ChildGroup");
        
        new Thread(child, () -> {
            System.out.println("Running in child group");
        }).start();
    }
}
