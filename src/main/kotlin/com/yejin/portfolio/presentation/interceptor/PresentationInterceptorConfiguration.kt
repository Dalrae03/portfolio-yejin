package com.yejin.portfolio.presentation.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


//spring에서 제공하는 interceptor 기능을 사용할 거라서 스프링 빈으로 등록이 되어야하기 때문에 component 선언
//Configuration 안에 component가 붙어있다.
//Configuration - 새롭게 사용자가 정의한 빈을 등록을 하거나 어떤 설정에 대한 값을 처리를 할 때 사용하는 Spring 빈. (스프링빈 주입 가능)
@Configuration
class PresentationInterceptorConfiguration(
    private val presentationInterceptor: PresentationInterceptor
) : WebMvcConfigurer {  //필요한 기능들 오버라이딩 - ctrl + O로 오버라이드 할 수 있는 창이 나온다.
    override fun addInterceptors(registry: InterceptorRegistry) {  //addInterceptors - 핸들러 인터셉터를 받도록 내부에 되어있음
        registry.addInterceptor(presentationInterceptor)  //presentationInterceptor - 만든 인터셉터
            .addPathPatterns("/**") // /** - 모든 경로에서 넣어주겠다
            .excludePathPatterns(
                "/assets/**",
                "/css/**",
                "/js/**",
                "/admin/**",
                "/h2**",
                "/favicon.ico",
                "/error"
            )  //제외할 것들
    }
//일케하면 프레젠테이션 인터셉터 등록이 된다.
}