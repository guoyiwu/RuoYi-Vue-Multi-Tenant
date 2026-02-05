package com.ruoyi.sast.coderabbit2;

import java.util.Date;

/**
 * 【规则7】Bug - 阻断
 * Printf-style format strings should not lead to unexpected behavior at runtime
 * Printf风格的格式化字符串不应导致运行时意外行为
 *
 * 问题：格式化字符串与参数不匹配会导致运行时异常或错误输出
 */
public class PrintfFormatString {

    // 🚨 违规：参数数量不匹配
    public void mismatchedArgumentCount() {
        String name = "John";
        int age = 25;
        String city = "Beijing";
        
        // 🚨 违规：格式化占位符是3个，但只提供了2个参数
        System.out.printf("Name: %s, Age: %d, City: %s%n", name, age);
        
        // 🚨 违规：格式化占位符是2个，但提供了3个参数
        System.out.printf("Name: %s, Age: %d%n", name, age, city);
    }

    // 🚨 违规：参数类型不匹配
    public void mismatchedArgumentType() {
        String text = "Hello";
        int number = 42;
        
        // 🚨 违规：%d期望整数，但传入了字符串
        System.out.printf("Value: %d%n", text);
        
        // 🚨 违规：%s期望字符串，但用%d格式化
        System.out.printf("Number: %s%n", number); // 这个可以工作但不规范
        
        // 🚨 违规：%f期望浮点数，但传入了整数
        System.out.printf("Float: %f%n", number);
    }

    // 🚨 违规：日期格式化错误
    public void wrongDateFormat() {
        Date now = new Date();
        
        // 🚨 违规：%t需要后跟日期/时间转换字符
        System.out.printf("Date: %t%n", now);
        
        // 🚨 违规：格式字符串语法错误
        System.out.printf("Time: %tZ%n"); // 缺少参数
    }

    // 🚨 违规：String.format中的同样问题
    public String formatError() {
        // 🚨 违规：参数顺序与格式化字符串不匹配
        return String.format("User %s has %d points and rank %s", 
            100,      // 应该是String，实际是int
            "John",   // 应该是int，实际是String  
            "#1");
    }
}
