package com.ljs.config;

import com.ljs.filter.AuthenticationLoggingFilter;
import com.ljs.filter.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
        http
                // 필터 체인에서 인증 필터 앞에 맞춤형 필터의 인스턴스를 추가한다.
                .addFilterBefore(
                        new RequestValidationFilter(),
                        BasicAuthenticationFilter.class)
                // 필터 체인에서 인증 필터 다음에 AuthenticationLoggingFilter 인스턴스를 추가한다.
                .addFilterAfter(
                        new AuthenticationLoggingFilter(),
                        BasicAuthenticationFilter.class
                )
                .authorizeRequests()
                .anyRequest().permitAll();
        return http.build();
    }
}