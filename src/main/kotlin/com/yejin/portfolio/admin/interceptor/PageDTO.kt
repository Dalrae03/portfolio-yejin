package com.yejin.portfolio.admin.interceptor

//대메뉴가 포함하고있는 소메뉴
data class PageDTO(
    val name: String,  //introduction, link같은 이름가지고 있음
    val url: String  //각 소메뉴의 페이지 링크를 가지고있음.
)