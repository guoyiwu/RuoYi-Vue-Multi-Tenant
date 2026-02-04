package com.ruoyi.sast.sonar;

/**
 * 【规则11】异味 - 阻断
 * Methods returns should not be invariant
 * 方法返回值不应是不变的
 *
 * 问题：方法总是返回相同的值，说明逻辑有问题或方法设计不合理
 */
public class InvariantMethodReturn {

    // 🚨 违规：无论输入如何，总是返回相同的值
    public boolean isValid(String input) {
        if (input == null) {
            return true;  // 返回true
        }
        if (input.isEmpty()) {
            return true;  // 返回true
        }
        if (input.length() > 100) {
            return true;  // 返回true
        }
        return true;  // 🚨 所有分支都返回true，方法无意义
    }

    // 🚨 违规：所有条件分支返回同一个值
    public int calculate(int a, int b) {
        if (a > b) {
            return 0;
        } else if (a < b) {
            return 0;
        } else {
            return 0;  // 🚨 无论a和b的关系如何，都返回0
        }
    }

    // 🚨 违规：switch语句所有case返回相同值
    public String getStatus(int code) {
        switch (code) {
            case 1:
                return "UNKNOWN";
            case 2:
                return "UNKNOWN";
            case 3:
                return "UNKNOWN";
            default:
                return "UNKNOWN";  // 🚨 所有情况都返回"UNKNOWN"
        }
    }

    // 🚨 违规：复杂逻辑但结果不变
    public boolean checkPermission(String user, String resource, String action) {
        if (user == null) {
            return false;
        }
        if (resource == null) {
            return false;
        }
        if (action == null) {
            return false;
        }
        if ("admin".equals(user)) {
            return false;  // 🚨 即使是admin也返回false
        }
        return false;  // 🚨 所有路径都返回false
    }

    // 🚨 违规：getter方法总是返回硬编码值
    private String name;
    
    public String getName() {
        return "default";  // 🚨 忽略了字段值，总是返回"default"
    }
}
