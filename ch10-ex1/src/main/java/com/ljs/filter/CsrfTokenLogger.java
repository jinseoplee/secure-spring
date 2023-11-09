package com.ljs.filter;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class CsrfTokenLogger implements Filter {
    private Logger logger = Logger.getLogger(CsrfTokenLogger.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // _csrf 요청 특성에서 토큰의 값을 얻고 콘솔에 출력한다.
        Object object = request.getAttribute("_csrf");
        CsrfToken token = (CsrfToken) object;
        logger.info("CSRF token " + token.getToken());

        filterChain.doFilter(request, response);
    }
}
