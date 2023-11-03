package com.ljs.service;

import com.ljs.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService userDetailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // UserDetailsService로 데이터베이스에서 사용자 세부 정보 조회
        CustomUserDetails user = userDetailService.loadUserByUsername(username);

        // 해당 사용자에 맞는 해싱 알고리즘으로 암호 검증
        switch (user.getUser().getAlgorithm()) {
            // 사용자의 암호가 bcrypt로 해시 됐으면 BCryptPasswordEncoder로 암호 검증
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            // 사용자의 암호가 scrypt로 해시 됐으면 SCryptPasswordEncoder로 암호 검증
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication checkPassword(CustomUserDetails user, String rawPassword, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
