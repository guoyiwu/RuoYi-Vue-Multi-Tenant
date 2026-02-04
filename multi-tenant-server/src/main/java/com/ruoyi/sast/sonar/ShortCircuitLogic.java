package com.ruoyi.sast.sonar;

/**
 * 【规则14】异味 - 阻断
 * Short-circuit logic should be used in boolean contexts
 * 在布尔上下文中应该使用短路逻辑
 *
 * 问题：使用 & 和 | 代替 && 和 || 会导致：
 *       1. 不必要的计算（性能问题）
 *       2. 可能的空指针异常
 *       3. 不期望的副作用执行
 */
public class ShortCircuitLogic {

    // 🚨 违规：使用 & 代替 &&
    public boolean checkUserAccess(String user, String role) {
        // 🚨 违规：即使user为null，role.equals()仍会执行，导致NPE
        if (user != null & role.equals("admin")) {
            return true;
        }
        return false;
    }

    // 🚨 违规：使用 | 代替 ||
    public boolean isValidInput(String input) {
        // 🚨 违规：即使input为null满足第一个条件，isEmpty()仍会执行
        if (input == null | input.isEmpty()) {
            return false;
        }
        return true;
    }

    // 🚨 违规：副作用函数可能被意外执行
    private int counter = 0;
    
    private boolean incrementAndCheck() {
        counter++;
        return counter > 5;
    }
    
    public boolean processWithSideEffect(boolean condition) {
        // 🚨 违规：无论condition是什么，incrementAndCheck()都会执行
        if (condition & incrementAndCheck()) {
            return true;
        }
        return false;
    }

    // 🚨 违规：在复杂条件中使用位运算符
    public boolean validateForm(String name, String email, Integer age) {
        // 🚨 违规：如果name为null，后续检查仍会执行，可能导致NPE
        return name != null & name.length() > 0 
             & email != null & email.contains("@")
             & age != null & age >= 18;
    }

    // 🚨 违规：使用位运算符进行空值检查链
    public String getDisplayName(User user) {
        // 🚨 违规：即使user为null，也会尝试调用user.getProfile()
        if (user != null & user.getProfile() != null & user.getProfile().getName() != null) {
            return user.getProfile().getName();
        }
        return "Unknown";
    }

    // 🚨 违规：循环条件中使用非短路运算符
    public void processItems(String[] items) {
        int i = 0;
        // 🚨 违规：即使i >= items.length，items[i]仍会执行，导致越界
        while (i < items.length & items[i] != null) {
            System.out.println(items[i]);
            i++;
        }
    }

    // 辅助类
    static class User {
        private Profile profile;
        public Profile getProfile() { return profile; }
    }
    
    static class Profile {
        private String name;
        public String getName() { return name; }
    }
}
