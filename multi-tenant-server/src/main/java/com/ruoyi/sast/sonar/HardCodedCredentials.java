package com.ruoyi.sast.sonar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 【规则1】安全热点 - 阻断
 * Hard-coded credentials are security-sensitive
 * 硬编码凭证是安全敏感的
 *
 * 问题：密码、密钥等敏感信息直接写在代码中，容易泄露
 */
public class HardCodedCredentials {

    // 🚨 违规：硬编码数据库密码
    private static final String DB_PASSWORD = "Admin@123456";
    private static final String DB_USER = "root";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/production";

    // 🚨 违规：硬编码API密钥
    private static final String API_KEY = "sk-1234567890abcdef";
    private static final String SECRET_KEY = "my-secret-key-12345";

    public Connection getConnection() throws SQLException {
        // 🚨 违规：使用硬编码的凭证连接数据库
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void callExternalApi() {
        // 🚨 违规：硬编码的认证信息
        String authHeader = "Bearer " + API_KEY;
        System.out.println("Using auth: " + authHeader);
    }

    public boolean authenticate(String username, String password) {
        // 🚨 违规：硬编码的用户名和密码比较
        return "admin".equals(username) && "password123".equals(password);
    }
}
