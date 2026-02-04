package com.ruoyi.sast.qodo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 【规则8】漏洞 - 严重
 * Security - Potential SQL Injection
 * 安全 - 潜在的SQL注入
 *
 * 问题：直接拼接用户输入到SQL语句中，攻击者可以执行任意SQL命令
 */
public class SqlInjection {

    private Connection connection;

    // 🚨 违规：直接拼接用户输入到SQL语句
    public User findUserByName(String username) throws SQLException {
        // 🚨 SQL注入漏洞：如果username = "' OR '1'='1"，将返回所有用户
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()) {
            return new User(rs.getLong("id"), rs.getString("username"));
        }
        return null;
    }

    // 🚨 违规：使用字符串拼接构建动态查询
    public List<User> searchUsers(String keyword, String orderBy) throws SQLException {
        // 🚨 SQL注入漏洞：keyword和orderBy都可能被注入
        String sql = "SELECT * FROM users WHERE name LIKE '%" + keyword + "%' ORDER BY " + orderBy;
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getLong("id"), rs.getString("username")));
        }
        return users;
    }

    // 🚨 违规：登录验证中的SQL注入
    public boolean authenticate(String username, String password) throws SQLException {
        // 🚨 严重漏洞：攻击者可以绕过登录验证
        // 输入 username = "admin'--" 可以注释掉密码检查
        String sql = "SELECT COUNT(*) FROM users WHERE username = '" + username 
                   + "' AND password = '" + password + "'";
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        return rs.getInt(1) > 0;
    }

    // 🚨 违规：删除操作中的SQL注入
    public void deleteUser(String userId) throws SQLException {
        // 🚨 危险：攻击者可以输入 "1 OR 1=1" 删除所有用户
        String sql = "DELETE FROM users WHERE id = " + userId;
        
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    // 辅助类
    static class User {
        private Long id;
        private String username;
        
        public User(Long id, String username) {
            this.id = id;
            this.username = username;
        }
    }
}
