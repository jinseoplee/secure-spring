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
                .authorities("read", "premium")
                .build();

        var user2 = User.withUsername("cyj")
                .password("1234")
                .authorities("read")
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
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*") // 사용자 인증을 위한 경로의 조건으로 정규식을 이용한다.
                    .authenticated()
                .anyRequest()
                    .hasAuthority("premium"); // 사용자가 프리미엄 액세스를 이용하는 데 필요한 다른 경로를 구성한다.

        return http.build();
    }
}