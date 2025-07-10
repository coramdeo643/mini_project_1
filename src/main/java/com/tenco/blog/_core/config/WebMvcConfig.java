package com.tenco.blog._core.config;

import com.tenco.blog._core.interceptor.CompanyInterceptor;
import com.tenco.blog._core.interceptor.IfLoggedInInterceptor;
import com.tenco.blog._core.interceptor.LoginInterceptor;
import com.tenco.blog._core.interceptor.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // IoC 처리 (싱글톤 패턴 관리)
public class WebMvcConfig implements WebMvcConfigurer {

    // DI 처리 (생성자 의존 주입)
    private final UserInterceptor userInterceptor;
    private final CompanyInterceptor companyInterceptor;
    private final IfLoggedInInterceptor ifLoggedInInterceptor;
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(
                        "/board/{id}", "/board/detail/**", "/ppost/{id}", "/ppost/detail/**"
                );

        registry.addInterceptor(ifLoggedInInterceptor)
                .addPathPatterns("/user/login-form", "/user/join-form", "/user/login", "/join",
                        "/company/login-form", "/company/join-form", "/company/login", "/company/join",
                        "/board/list", "/board/detail/**", "/ppost/list", "/ppost/detail/**"
                );

        registry.addInterceptor(userInterceptor)
                // 인터셉터가 동작할 URI 패턴을 지정
                .addPathPatterns("/user/**", "/ppost/save-form", "/ppost/save", "/ppost/update-form/**", "/ppost/update/**", "/ppost/delete/**")
                // 인터셉터에서 제외할 URI 패턴 설정
                .excludePathPatterns("/user/join-form", "/user/join", "/user/login-form", "/user/login"
                );

        registry.addInterceptor(companyInterceptor)
                .addPathPatterns("/company/**", "/board/save-form", "/board/save", "/board/update-form/**", "/board/update/**", "/board/delete/**")
                .excludePathPatterns(
                        "/company/join-form", "/company/join", "/company/login-form", "/company/login"
                );
    }
}
