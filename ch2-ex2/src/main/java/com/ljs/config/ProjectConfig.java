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

@Configuration // 구성 클래스
public class ProjectConfig {

    @Bean // 반환된 값을 스프링 컨텍스트에 빈으로 추가
    public UserDetailsService userDetailsService() {
        var userDetailService = new InMemoryUserDetailsManager(); // var 키워드는 구문을 간소하게 만들어주고 세부 정보를 감춘다.
        var user = User.withUsername("ljs") // 주어진 사용자 이름, 암호, 권한 목록으로 사용자 생성
                .password("1234")
                .authorities("user")
                .build();
        userDetailService.createUser(user); // UserDetailService에서 관리하도록 사용자 추가
        return userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated(); // 모든 요청에 인증이 필요하다.
        return http.build();
    }
}
