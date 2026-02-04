package com.ruoyi.sast.qodo;

/**
 * 【规则6】Bug - 阻断
 * Loops should not be infinite
 * 循环不应该是无限的
 *
 * 问题：无限循环会导致程序挂起、CPU占用100%、系统资源耗尽
 */
public class InfiniteLoop {

    // 🚨 违规：明显的无限循环
    public void obviousInfiniteLoop() {
        while (true) {
            System.out.println("This will run forever");
            // 没有break或return语句
        }
    }

    // 🚨 违规：条件永远为真的循环
    public void alwaysTrueCondition() {
        int i = 0;
        while (i >= 0) {
            System.out.println("Count: " + i);
            i++; // i永远不会小于0（除非溢出，但那是另一个问题）
        }
    }

    // 🚨 违规：循环变量未正确更新
    public void loopVariableNotUpdated() {
        int i = 0;
        while (i < 10) {
            System.out.println("Iteration: " + i);
            // 🚨 违规：忘记更新 i，导致无限循环
        }
    }

    // 🚨 违规：条件逻辑错误导致的无限循环
    public void wrongConditionLogic(int start) {
        int i = start;
        while (i != 0) {
            System.out.println("Value: " + i);
            i -= 2;
            // 🚨 违规：如果start是奇数，i永远不等于0
        }
    }

    // 🚨 违规：for循环的无限形式
    public void infiniteForLoop() {
        for (;;) {
            System.out.println("Infinite for loop");
            // 没有退出条件
        }
    }

    // 🚨 违规：递归导致的无限循环（无终止条件）
    public void infiniteRecursion(int n) {
        System.out.println("Value: " + n);
        infiniteRecursion(n + 1); // 🚨 没有终止条件
    }
}
