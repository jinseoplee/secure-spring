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
                .authorities("ROLE_ADMIN") // ROLE_ 접두사가 있으므로 GrantedAuthority는 역할을 나타낸다.
                .build();

        var user2 = User.withUsername("cyj")
                .password("1234")
                .authorities("ROLE_MANAGER")
                .build();

        // 사용자는 UserDetailsService에 의해 추가되고 관리된다.
        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        // hasRole() 메서드는 엔드포인트에 접근할 수 있는 역할을 지정한다.
        // 여기에 ROLE_ 접두사는 나오지 않는 것에 주의하자.
        http.authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN");

        return http.build();
    }
}