package com.ljs.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        var auth = authorities
                .stream()
                .filter(a -> a.getAuthority().equals("read"))
                .findFirst(); // 'read' 권한이 없다면 비어 있는 Optional 객체 반환

        if (auth.isPresent()) { // 'read' 권한이 있다면 /home으로 리다이렉션
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/error");
        }
    }
}