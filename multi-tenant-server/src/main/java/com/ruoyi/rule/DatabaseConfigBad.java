package com.ruoyi.rule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseConfigBad {

  // 🚨 红线违规：硬编码明文密码
  private static final String DB_PASSWORD = "Admin@123456";
  private static final String DB_USER = "root";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/test";

  public Connection getConnection() throws SQLException {
    // 🚨 红线违规：密码直接写在代码中
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }

  // 🚨 红线违规：日志打印敏感信息
  public void login(String username, String password) {
    log.info("用户登录: username={}, password={}", username, password);
  }
}
