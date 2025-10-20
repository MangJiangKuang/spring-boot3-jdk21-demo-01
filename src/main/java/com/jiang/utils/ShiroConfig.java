package com.jiang.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

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
        UserRealm realm = new UserRealm(){
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
                    throws AuthenticationException {
                UsernamePasswordToken upToken = (UsernamePasswordToken) token;
                // 预检查令牌格式
                if (upToken.getPassword().length  == 0) {
                    throw new IllegalArgumentException("令牌格式错误：密码不能为空");
                }
                // 继续正常的认证流程
                return super.doGetAuthenticationInfo(token);
            }
        };

        return realm;
    }

    // 交由 Shiro Starter 自动装配 SecurityManager

    // 让 starter 自动管理 SecurityUtils 绑定与 Filter 注册
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录页面URL
        shiroFilterFactoryBean.setLoginUrl("/test.html"); // 或者你自己的登录页面

        // 使用 DefaultShiroFilterChainDefinition
        // 使用 DefaultShiroFilterChainDefinition
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/auth/login", "anon");
        chainDefinition.addPathDefinition("/auth/logout", "anon");
        chainDefinition.addPathDefinition("/user/login", "anon");
        chainDefinition.addPathDefinition("/test.html", "anon"); // 登录页面允许匿名访问
        chainDefinition.addPathDefinition("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinition.getFilterChainMap());
        return shiroFilterFactoryBean;
    }

  /*  @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> shiroFilterRegistration() {
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        // 这里使用 Bean 的方法名作为引用名称
        DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilterFactoryBean");
        proxy.setTargetFilterLifecycle(true);
        registration.setFilter(proxy);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }*/
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        // 设置登录页面URL
//        shiroFilterFactoryBean.setLoginUrl("/login.html"); // 或者你自己的登录页面
//
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/auth/login", "anon");
//        filterChainDefinitionMap.put("/auth/logout", "anon");
//        filterChainDefinitionMap.put("/**", "authc");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
    // 提供过滤链定义（可选，但推荐显式声明）
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition def = new DefaultShiroFilterChainDefinition();
        // 其他配置保持不变...
        def.addPathDefinition("/", "anon"); // 确保根路径允许匿名访问
        def.addPathDefinition("/index.html", "anon");
        def.addPathDefinition("/v3/api-docs/", "anon");
        def.addPathDefinition("/swagger-ui/", "anon");
        def.addPathDefinition("/swagger-ui.html", "anon");
        def.addPathDefinition("/", "anon");
        def.addPathDefinition("/index.html", "anon");
        def.addPathDefinition("/test.html", "anon");
        def.addPathDefinition("/webjars/", "anon");
        def.addPathDefinition("/favicon.ico", "anon");
        def.addPathDefinition("/public/", "anon");
        def.addPathDefinition("/user/login", "anon");
        def.addPathDefinition("/user/register", "anon");
        def.addPathDefinition("/auth/login", "anon");
        def.addPathDefinition("/auth/logout", "anon");
        def.addPathDefinition("/logout", "logout");
        def.addPathDefinition("/", "authc");
        return def;
    }

    // 移除手动创建的 ShiroFilterFactoryBean，改用 starter 的自动装配。

      // 注册 Shiro Filter 到 Servlet 容器（使用 Spring 的 DelegatingFilterProxy）

/*      @Bean
      public FilterRegistrationBean<DelegatingFilterProxy>
      shiroFilterRegistration() {
      FilterRegistrationBean<DelegatingFilterProxy> registration = new
      FilterRegistrationBean<>();
      DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilter");
      proxy.setTargetFilterLifecycle(true);
      registration.setFilter(proxy);
      registration.addUrlPatterns("/*");
      registration.setOrder(1);
      return registration;
      }*/
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> shiroFilterRegistration() {
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilterFactoryBean");
        proxy.setTargetFilterLifecycle(true);
        registration.setFilter(proxy);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    // 注解与 AOP 顾问交由 Shiro Starter 自动装配
}
