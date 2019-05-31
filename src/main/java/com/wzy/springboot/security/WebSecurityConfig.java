package com.wzy.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 设置后，如果没有登录成功则不能跳转到其他页面
 * @author wzy
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //注册CustomerUserService的bean
    @Bean
    UserDetailsService customerUserService() {
        return new CustomerService();
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中分别配置两个用户wzy和zpc,密码和用户名一致，角色是USER，用户来自于谷歌用户
        //点击google浏览器用户头像可以创建用户。
        /*auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("wzy").password("wzy").roles("USER")
                .and()
                .withUser("zpc").password("zpc").roles("USER")
                .and().withUser("a1945261381@gmail.com").password("myGoogle5261").roles("USER");*/

        //添加自定义的user detail service认证
        auth.userDetailsService(customerUserService()).passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //resources/static目录下的静态资源不拦截
        web.ignoring().antMatchers("/resources/static/**")
                .antMatchers("/com/wzy/springboot/rest/**");
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()/*.antMatchers("/", "/login").permitAll()*/ //设置Spring Security对“/”和“login”路径不拦截,如果设置了，则html里hasRole()方法就没意义了，因为默认为true了
                .anyRequest().authenticated() //所有请求需要认证登录后才能访问
                .and()
                .formLogin()
                .loginPage("/login") //设置Spring Security的登录页面访问的路径为/login
                /*.defaultSuccessUrl("/chat") *///登录成功后转向/chat路径
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error")
                .permitAll()  //定制登录行为，登录页面可任意访问
                .and()
                .logout().permitAll();  //定制注销行为，注销请求可任意访问
    }
}
