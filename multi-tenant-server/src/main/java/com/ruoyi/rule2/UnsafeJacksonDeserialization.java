package com.ruoyi.rule2;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

/**
 * 【规则3】安全热点 - 严重
 * Using unsafe Jackson deserialization configuration is security-sensitive
 * 使用不安全的Jackson反序列化配置是安全敏感的
 *
 * 问题：启用默认类型会导致远程代码执行漏洞
 */
public class UnsafeJacksonDeserialization {

    public ObjectMapper createUnsafeMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 🚨 违规：启用默认类型，允许任意类反序列化
        // 这可能导致远程代码执行(RCE)漏洞
        mapper.enableDefaultTyping();

        return mapper;
    }

    public ObjectMapper createAnotherUnsafeMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 🚨 违规：使用不安全的默认类型配置
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        return mapper;
    }

    public ObjectMapper createDeprecatedUnsafeMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 🚨 违规：使用已废弃的不安全方法
        mapper.enableDefaultTypingAsProperty(
            ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, 
            "@class"
        );

        return mapper;
    }
}
