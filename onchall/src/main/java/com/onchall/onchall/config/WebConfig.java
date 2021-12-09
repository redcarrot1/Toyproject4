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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()) //인터셉터 등록. 여기서 LoginCheckInterceptor()은 내가 구현한 클래스 이름이다.
                .order(1) //낮을 수록 먼저 호출
                .addPathPatterns("/**") //인터셉터를 적용할 url 패턴
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/login", "/signup",
                        "/test/**", "/findPassword", "/changePassword", "/email/duplication", "/image/**"); //인터셉터에서 제외할 패턴 지정
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}