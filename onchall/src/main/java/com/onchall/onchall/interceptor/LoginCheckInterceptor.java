package com.onchall.onchall.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI() + "?";
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMemberId") == null) {
            log.info("미인증 사용자 요청");

            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                requestURI += (name + "=" + request.getParameter(name) + "&");
            }

            response.sendRedirect("/login?redirectURL=" + requestURI.substring(1, requestURI.length() - 1));
            return false;
        }
        return true;
    }
}
