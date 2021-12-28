package com.onchall.onchall.config;

import com.onchall.onchall.argumentResolver.LoginMemberArgumentResolver;
import com.onchall.onchall.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    // 인터셉터 등록
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) //낮을 수록 먼저 호출
                .addPathPatterns("/**") //인터셉터를 적용할 url 패턴
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/login", "/signup",
                        "/test/**", "/findPassword", "/changePassword", "/email/duplication", "/image/**");
    }

    @Override
    // resolveArgument 등록
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}