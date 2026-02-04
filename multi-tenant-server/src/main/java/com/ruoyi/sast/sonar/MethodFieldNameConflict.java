package com.ruoyi.sast.sonar;

/**
 * 【规则10】异味 - 阻断
 * Methods and field names should not be the same or differ only by capitalization
 * 方法名和字段名不应相同或仅大小写不同
 *
 * 问题：容易造成混淆，降低代码可读性，可能导致调用错误
 */
public class MethodFieldNameConflict {

    // 🚨 违规：字段名和方法名相同
    private String name;
    
    public String name() {  // 🚨 方法名与字段名相同
        return this.name;
    }

    // 🚨 违规：字段名和方法名仅大小写不同
    private int count;
    private int Count;  // 🚨 与count仅大小写不同
    
    public int Count() {  // 🚨 方法名与字段名大小写混淆
        return count;
    }

    // 🚨 违规：getter风格但命名不规范
    private String value;
    
    public String value() {  // 🚨 应该是 getValue()
        return value;
    }
    
    public void value(String v) {  // 🚨 应该是 setValue()
        this.value = v;
    }

    // 🚨 违规：多个相似命名
    private double Price;
    private double price;
    
    public double price() {  // 🚨 混淆：哪个price？
        return price;
    }
    
    public double Price() {  // 🚨 混淆：哪个Price？
        return Price;
    }

    // 🚨 违规：静态字段与方法名冲突
    private static String instance;
    
    public static String instance() {  // 🚨 与静态字段同名
        return instance;
    }
}
