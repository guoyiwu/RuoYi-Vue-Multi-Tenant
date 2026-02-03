package com.ruoyi.rule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Configuration
public class DatabaseConfigGood {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUser;

  @Value("${spring.datasource.password}")
  private String dbPassword;  // 从配置文件/环境变量/密钥管理服务读取

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
  }

  public void login(String username, String password) {
    // 只记录脱敏信息
    log.info("用户登录: username={}", username);
  }
}
