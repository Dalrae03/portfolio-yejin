package com.yejin.portfolio.admin.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AdminInterceptorConfiguration(
    val adminInterceptor: AdminInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(adminInterceptor)  //어떤 경로에서 이 인터셉터(adminInterceptor)가 동작을 할 지 그 경로 지정
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/assets/**", "/css/**", "/js/**", "/h2**")
    }
}