package com.yejin.portfolio.admin.interceptor

//사이드바에 보여지는 메뉴 중 대메뉴
data class MenuDTO(
    val name: String,
    val pages: List<PageDTO>
)
