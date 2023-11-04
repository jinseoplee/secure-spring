package com.ljs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .mvcMatchers("/hello").hasRole("ADMIN") // 관리자 역할의 사용자만 /hello 경로를 호출할 수 있다.
                .mvcMatchers("/ciao").hasRole("MANAGER") // 운영자 역할의 사용자만 /ciao 경로를 호출할 수 있다.
//                .anyRequest().permitAll(); // 나머지 모든 엔드포인트에 대해 모든 요청을 허용한다.
                .anyRequest().authenticated(); // 인증된 사용자에게만 나머지 모든 요청을 허용한다.
        return http.build();
    }
}