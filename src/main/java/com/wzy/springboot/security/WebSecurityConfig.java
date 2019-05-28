package com.wzy.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author wzy
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中分别配置两个用户wyf和wisely,密码和用户名一致，角色是USER，用户来自于谷歌用户
        //点击google浏览器用户头像可以创建用户。
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("wzy").password("wzy").roles("USER")
                .and()
                .withUser("zpc").password("zpc").roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //resources/static目录下的静态资源不拦截
        web.ignoring().antMatchers("/resources/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/login").permitAll() //设置Spring Security对“/”和“login”路径不拦截
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") //设置Spring Security的登录页面访问的路径为/login
                .defaultSuccessUrl("/chat") //登录成功后转向/chat路径
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
