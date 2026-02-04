package com.ruoyi.sast.coderabbit;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 【规则2】安全热点 - 严重
 * Disabling CSRF protections is security-sensitive
 * 禁用CSRF保护是安全敏感的
 *
 * 问题：禁用CSRF保护会使应用容易受到跨站请求伪造攻击
 */
@Configuration
public class DisablingCsrfProtection extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 🚨 违规：完全禁用CSRF保护
        http.csrf().disable();

        // 🚨 违规：禁用所有安全头
        http.headers().disable();

        // 🚨 违规：禁用X-Frame-Options保护
        http.headers().frameOptions().disable();

        http.authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .anyRequest().authenticated();
    }
}
