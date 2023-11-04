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

        // 첫 번째 사용자 ljs는 READ 권한을 가짐
        var user1 = User.withUsername("ljs")
                .password("1234")
                .authorities("READ")
                .build();

        // 두 번째 사용자 cyj은 WRITE 권한을 가짐
        var user2 = User.withUsername("cyj")
                .password("1234")
                .authorities("WRITE")
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
        http.authorizeRequests()
                .anyRequest()
//                .permitAll(); // 모든 요청에 대해 액세스 허용
//                .hasAuthority("WRITE"); // 사용자가 엔드포인트에 접근하기 위한 조건 지정
                .hasAnyAuthority("READ", "WRITE"); // READ 및 WRITE 권한이 있는 사용자의 요청을 모두 허용
        return http.build();
    }
}