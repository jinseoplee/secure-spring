package com.ljs.service;

import com.ljs.entity.User;
import com.ljs.model.CustomUserDetails;
import com.ljs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자를 포함한 Optional 인스턴스를 반환하거나
        // 사용자가 없으면 비어 있는 Optional 인스턴스를 반환한다.
        // Optional 인스턴스가 비어 있으면 예외를 발생시키고, 그렇지 않으면 User 인스턴스를 반환한다.
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Authentication failed"));

        // User 인스턴스를 CustomUserDetails 데코레이터로 래핑하고 반환한다.
        return new CustomUserDetails(user);
    }
}
