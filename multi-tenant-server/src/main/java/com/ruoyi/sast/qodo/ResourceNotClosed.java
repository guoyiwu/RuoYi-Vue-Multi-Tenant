package com.ruoyi.sast.qodo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 【规则4】Bug - 阻断
 * Resources should be closed
 * 资源应该被关闭
 *
 * 问题：未关闭的资源会导致内存泄漏、连接池耗尽等问题
 */
public class ResourceNotClosed {

    // 🚨 违规：文件流未关闭
    public String readFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        // 🚨 违规：reader 和 fis 都没有关闭
        return content.toString();
    }

    // 🚨 违规：数据库连接未关闭
    public void queryDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "user", "pass");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
        // 🚨 违规：rs, stmt, conn 都没有关闭
    }

    // 🚨 违规：输出流未关闭
    public void writeFile(String path, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
        
        writer.write(content);
        // 🚨 违规：没有flush也没有close，数据可能丢失
    }

    // 🚨 违规：异常情况下资源未关闭
    public void processWithException(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        
        if (path.contains("error")) {
            throw new IOException("Error occurred");
            // 🚨 违规：抛出异常时 fis 未关闭
        }
        
        fis.close(); // 只有正常流程才会执行
    }
}
