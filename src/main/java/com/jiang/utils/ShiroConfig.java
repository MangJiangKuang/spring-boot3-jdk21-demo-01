package com.jiang.utils;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1); // 与当前 MD5Util 保持一致（md5(password+salt) 一次）
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    @Bean
    public UserRealm userRealm(HashedCredentialsMatcher matcher) {
        UserRealm realm = new UserRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 将 SecurityManager 绑定到 SecurityUtils，避免 UnavailableSecurityManagerException
    @Bean
    public MethodInvokingFactoryBean bindSecurityManager(SecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/user/login");
        shiroFilter.setUnauthorizedUrl("/unauthorized");

        Map<String, String> filterMap = new LinkedHashMap<>();
        // Swagger 放行
        filterMap.put("/v3/api-docs/**", "anon");
        filterMap.put("/swagger-ui/**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        // 登录与公共接口放行
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/register", "anon");
        filterMap.put("/auth/login", "anon");
        filterMap.put("/auth/logout", "anon");
        filterMap.put("/public/**", "anon");
        // 静态资源
        filterMap.put("/", "anon");
        filterMap.put("/index.html", "anon");
        filterMap.put("/test.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        // 登出
        filterMap.put("/logout", "logout");
        // 其他需要认证
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    // 注册 Shiro Filter 到 Servlet 容器（使用 Spring 的 DelegatingFilterProxy）
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> shiroFilterRegistration() {
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilter");
        proxy.setTargetFilterLifecycle(true);
        registration.setFilter(proxy);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    // 使 @RequiresRoles/@RequiresPermissions 生效
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
