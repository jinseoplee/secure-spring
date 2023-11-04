package com.ljs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("ljs")
                .password("1234")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("cyj")
                .password("1234")
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                // HTTP GET 방식으로 /a 경로를 요청하면 앱이 사용자를 인증해야 한다.
                .mvcMatchers(HttpMethod.GET, "/a")
                .authenticated()
                // HTTP POST 방식으로 /a 경로를 요청하면 모두 허용
                .mvcMatchers(HttpMethod.POST, "/a")
                .permitAll()
                // 다른 경로에 대한 모든 요청 거부
                .anyRequest()
                .denyAll();

        // HTTP POST 방식으로 /a 경로를 호출할 수 있게 CSRF 비활성화
        http.csrf().disable();

        return http.build();
    }
}